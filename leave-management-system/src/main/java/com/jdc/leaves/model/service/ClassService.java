package com.jdc.leaves.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jdc.leaves.model.dto.input.ClassForm;
import com.jdc.leaves.model.dto.output.ClassDetailsVO;
import com.jdc.leaves.model.dto.output.ClassListVO;

@Service
public class ClassService {
	
	private final String SELECT_PROJECTION = """
				select 
				c.id id, t.id teacherId,
				a.name teacherName, t.phone teacherPhone,
				c.start_date startDate, c.months, c.description,
				count(r.student_id) studentCount
				from classes c
				join teacher t on t.id = c.teacher_id
				join account a on a.id = t.id
				left join registration r on c.id = r.classes_id
				where 1 = 1  
			""";
	
	private final String GROUP_BY_PROJECTION = """
			group by c.id, t.id, a.name, t.phone, c.start_date, c.months, c.description
			""";
	
	private NamedParameterJdbcTemplate template;
	private SimpleJdbcInsert insert;
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private LeaveService leaveService;
	
	public ClassService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
		
		insert = new SimpleJdbcInsert(dataSource);
		insert.setTableName("classes");
		insert.setGeneratedKeyName("id");
		insert.setColumnNames(List.of("teacher_id","start_date","months","description"));
	}

	public List<ClassListVO> search(Optional<String> teacher, Optional<LocalDate> from, Optional<LocalDate> to) {
		
		var sb = new StringBuffer(SELECT_PROJECTION);
		var params = new HashMap<String, Object>();
		
		sb.append(
				teacher
					.filter(StringUtils::hasLength)
					.map(name -> {
						params.put("teacher", name.toLowerCase().concat("%"));
						return " and lower(a.name) like :teacher ";
					}).orElse("")
				);
		
		sb.append(
				from
					.map(start -> {
						params.put("from", Date.valueOf(start));
						return "and c.start_date >= :from ";
					}).orElse("")
				
				);
		
		sb.append(
				to
					.map(start -> {
						params.put("to", Date.valueOf(start));
						return "and c.start_date <= :to ";
					}).orElse("")
				
				);
		
		sb.append(GROUP_BY_PROJECTION);
		
		return template.query(sb.toString(),
				params,
				new BeanPropertyRowMapper<ClassListVO>(ClassListVO.class));
	}
	
	public int save(ClassForm form) {
		if(form.getId() == 0) {
			return insert(form);
		}
		return update(form);
	}

	public ClassForm findById(int id) {
		return template.queryForObject("""
				select c.id, c.teacher_id teacher, c.start_date start, c.months, c.description
				from classes c where id = :id
				""",
				Map.of("id",id),
				new BeanPropertyRowMapper<ClassForm>(ClassForm.class));
	}

	public ClassListVO findInfoById(int id) {
		var sql = "%s %s %s".formatted(SELECT_PROJECTION, " and c.id = :id " ,GROUP_BY_PROJECTION);
		return template.queryForObject(sql,
				Map.of("id", id),
				new BeanPropertyRowMapper<ClassListVO>(ClassListVO.class));
	}

	public ClassDetailsVO findDetailsById(int id) {
		
		var classDetailVO = new ClassDetailsVO();
		
		var sql = "%s and c.id = :id %s".formatted(SELECT_PROJECTION,GROUP_BY_PROJECTION);
		var classListVO = template.queryForObject(sql, Map.of("id",id),new BeanPropertyRowMapper<ClassListVO>(ClassListVO.class));
		classDetailVO.setClassInfo(classListVO);
		
		classDetailVO.setRegistrations(registrationService.searchByClassId(id));

		classDetailVO.setLeaves(leaveService.search(
				Optional.of(id),
				Optional.empty(),
				Optional.empty()));
		
		return classDetailVO;
	}

	private int insert(ClassForm form) {
		var params = new HashMap<String, Object>();
		params.put("teacher_id", form.getTeacher());
		params.put("start_date", Date.valueOf(form.getStart()));
		params.put("months", form.getMonths());
		params.put("description", form.getDescription());
		
		return insert.executeAndReturnKey(params).intValue();
	}
	
	private int update(ClassForm form) {
		var params = new HashMap<String, Object>();
		params.put("teacher", form.getTeacher());
		params.put("start", Date.valueOf(form.getStart()));
		params.put("months", form.getMonths());
		params.put("description", form.getDescription());
		params.put("id", form.getId());
		
		template.update("""
				update classes
				set teacher_id = :teacher,  start_date = :start, months = :months, description = :description
				where id = :id
				""",
				params);
		
		return form.getId();
	}
}