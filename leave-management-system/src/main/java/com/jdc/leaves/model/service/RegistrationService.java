package com.jdc.leaves.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import com.jdc.leaves.model.dto.input.RegistrationForm;
import com.jdc.leaves.model.dto.output.RegistrationDetailsVO;
import com.jdc.leaves.model.dto.output.RegistrationListVO;

@Service
public class RegistrationService {
	
	private NamedParameterJdbcTemplate template;
	private SimpleJdbcInsert insert;
	
	public RegistrationService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);

		insert = new SimpleJdbcInsert(dataSource);
		insert.setTableName("registration");
		insert.setColumnNames(List.of("classes_id", "student_id", "registration_date"));
	}

	public int save(RegistrationForm form) {
		return insert.execute(Map.of(
				"classes_id", form.getClassId(),
				"student_id", form.getStudentId(),
				"registration_date", Date.valueOf(LocalDate.now())
				));
	}

	public List<RegistrationListVO> searchByClassId(int id) {
		var sql = """
				select r.id, r.classes_id classId,
				c.teacher_id teacherId,
				a.name teacher,c.start_date startDate, 
				r.student_id studentId, a.name student, s.phone studentPhone,
				r.registration_date registrationDate
				from registration r
				join classes c on r.classes_id = c.id
				join student s on r.student_id = s.id
				join teacher t on c.teacher_id = t.id
				join account a on t.id = a.id and s.id = a.id
				where r.classes_id = :id
				""";
		return template.query(sql, Map.of("id",id), new BeanPropertyRowMapper<RegistrationListVO>(RegistrationListVO.class));
	}

	public RegistrationDetailsVO findDetailsById(int classId) {
		return null;
	}

	public RegistrationForm getFormById(int id) {
		var form = new RegistrationForm();
		form.setId(id);
		return form;
	}

}