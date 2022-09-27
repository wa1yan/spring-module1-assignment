package com.jdc.leaves.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import com.jdc.leaves.model.dto.input.RegistrationForm;
import com.jdc.leaves.model.dto.output.ClassListVO;
import com.jdc.leaves.model.dto.output.RegistrationDetailsVO;
import com.jdc.leaves.model.dto.output.RegistrationListVO;
import com.jdc.leaves.model.dto.output.StudentListVO;

@Service
public class RegistrationService {
	
	private NamedParameterJdbcTemplate template;
	private SimpleJdbcInsert registInsert;
	private SimpleJdbcInsert studentInsert;
	private SimpleJdbcInsert accountInsert;
	
	public RegistrationService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);

		accountInsert = new SimpleJdbcInsert(dataSource);
		accountInsert.setTableName("account");
		accountInsert.setGeneratedKeyName("id");
		accountInsert.setColumnNames(List.of("name","role","email","password"));
		
		studentInsert = new SimpleJdbcInsert(dataSource);
		studentInsert.setTableName("student");
		
		registInsert = new SimpleJdbcInsert(dataSource);
		registInsert.setTableName("registration");
		registInsert.setColumnNames(List.of("classes_id", "student_id", "registration_date"));
		
		
	}

	public int save(RegistrationForm form) {
		
		//account insert
		var studentId = accountInsert.executeAndReturnKey(Map.of(
				"name",form.getStudentName(),
				"role", "Student",
				"email", form.getEmail(),
				"password",form.getPhone()
				));
		form.setStudentId(studentId.intValue());
		
		//student insert
		studentInsert.execute(Map.of(
				"id", form.getStudentId(),
				"phone", form.getPhone(),
				"education" , form.getEducation()));
		
		return registInsert.execute(Map.of(
				"classes_id", form.getClassId(),
				"student_id", form.getStudentId(),
				"registration_date", Date.valueOf(LocalDate.now())
				));
	}

	public List<RegistrationListVO> searchByClassId(int id) {
		var sql = """
				select r.classes_id classId,
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

	public RegistrationDetailsVO findDetailsById(int classId, int studentId) {
		
		RegistrationDetailsVO registDetail = new RegistrationDetailsVO();
		
		var classListVO = template.queryForObject("""
				select c.id, teacher_id teacherId, a.name teacherName, t.phone teacherPhone, c.start_date startDate, c.months, c.description, count(r.student_id) studentCount
				from leaves_db.classes c
				join leaves_db.account a on c.teacher_id = a.id
				join leaves_db.teacher t on t.id = c.teacher_id
				join leaves_db.registration r on classes_id = c.id
				where c.id = :classId
				""",
				Map.of("classId", classId),
				new BeanPropertyRowMapper<ClassListVO>(ClassListVO.class));
		
		registDetail.setClassInfo(classListVO);
		
		var studentListVO = template.queryForObject("""
				select s.id, name, phone, email, education, count(r.classes_id) classCount
			    from leaves_db.student s
			    join leaves_db.account a on s.id = a.id
			    join leaves_db.registration r on r.student_id = s.id
			    where s.id = :studentId
				""",
				Map.of("studentId", studentId),
				new BeanPropertyRowMapper<StudentListVO>(StudentListVO.class));
		
		registDetail.setStudent(studentListVO);
		//var registDate = template.execu
		
		
		return registDetail;
	}

	public RegistrationForm getFormById(int studentId) {
		var form = new RegistrationForm();
		form.setStudentId(studentId);
		return form;
	}

}