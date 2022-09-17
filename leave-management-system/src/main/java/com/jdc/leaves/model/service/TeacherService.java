package com.jdc.leaves.model.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jdc.leaves.model.dto.input.TeacherForm;
import com.jdc.leaves.model.dto.output.IdWithName;
import com.jdc.leaves.model.dto.output.TeacherListVO;

@Service
public class TeacherService {
	
	private static final String SELECT_PROJECTION = """
			select t.id, a.name, t.phone, a.email, t.assign_date assignDate, COUNT(c.id) classCount 
			from teacher t
			join account a
			on a.id = t.id
			left join classes c
			on c.teacher_id = t.id
			""";
	private static final String SELECT_GROUP_BY = "group by t.id, a.name, t.phone, a.email, t.assign_date";
	
	private NamedParameterJdbcTemplate template;
	private SimpleJdbcInsert accountInsert;
	private SimpleJdbcInsert teacherInsert;
	
	private RowMapper<TeacherListVO> rowMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public TeacherService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
		
		accountInsert = new SimpleJdbcInsert(dataSource);
		accountInsert.setTableName("account");
		accountInsert.setGeneratedKeyName("id");
		accountInsert.setColumnNames(List.of("name","role","email","password"));
		
		teacherInsert = new SimpleJdbcInsert(dataSource);
		teacherInsert.setTableName("teacher");
		
		rowMapper = new BeanPropertyRowMapper<>(TeacherListVO.class);
	}
	
	@Transactional
	public int save(TeacherForm form) {
		
		if(form.getId() == 0) {
			return insert(form);
		}
		return update(form);
	}

	public List<TeacherListVO> search(Optional<String> name, Optional<String> phone, Optional<String> email) {
		var where = new StringBuffer();
		var param = new HashMap<String, Object>();
		
		where.append(
				email
					.filter(StringUtils::hasLength)
					.map(str -> {
						param.put("email", str.concat("%"));
						return "and a.email like :email";
					}).orElse("")
			);
		
		where.append(
				phone
					.filter(StringUtils::hasLength)
					.map(str -> {
						param.put("phone", str.concat("%"));
						return "and lower(t.phone) like :phone";
					}).orElse("")
			);
		
		where.append(
					name
						.filter(StringUtils::hasLength)
						.map(str -> {
							param.put("name", str.toLowerCase().concat("%"));
							return "and lower(a.name) like :name";
						}).orElse("")
					);

		var sql = "%s where 1 = 1 %s %s".formatted(SELECT_PROJECTION, where.toString(), SELECT_GROUP_BY);
		
		return template.query(sql, param, rowMapper);
	}

	public TeacherListVO findById(int id) {
		var sql = "%s where %s %s".formatted(SELECT_PROJECTION, "t.id = :id", SELECT_GROUP_BY);
		return template.queryForObject(sql, Map.of("id",id), rowMapper);
	}

	public List<IdWithName> getAvailableTeachers() {
		return null;
	}
	
	private int insert(TeacherForm form) {
		var param = new HashMap<String, Object>();
		param.put("name", form.getName());
		param.put("role", "Teacher");
		param.put("email", form.getEmail());
		param.put("password", passwordEncoder.encode(form.getEmail()));
	
		var generatedId = accountInsert.executeAndReturnKey(param);
		
		teacherInsert.execute(Map.of(
				"id",generatedId.intValue(),
				"phone", form.getPhone(),
				"assign_date", Date.valueOf(form.getAssignDate())));
		
		return generatedId.intValue();
	}
	
	private int update(TeacherForm form) {
		//update account table
		template.update("update account set name = :name where id = :id",
				Map.of("name",form.getName(),
						"id",form.getId()));
		
		//update teacher table
		template.update("update teacher set phone = :phone, assign_date = :assign_date where id = :id",
				Map.of("phone",form.getPhone(),
						"assign_date",Date.valueOf(form.getAssignDate())));
		
		return form.getId();
	}

}