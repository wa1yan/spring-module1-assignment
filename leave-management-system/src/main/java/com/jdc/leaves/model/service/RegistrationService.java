package com.jdc.leaves.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.leaves.model.dto.input.RegistrationForm;
import com.jdc.leaves.model.dto.output.RegistrationDetailsVO;
import com.jdc.leaves.model.dto.output.RegistrationListVO;

@Service
public class RegistrationService {
	
	private static final String SELECT_BY_CLASS = """
			select r.classes_id classId, c.teacher_id teacherId, ta.name teacher, c.description classInfo, 
			c.start_date startDate, r.student_id studentId, sa.name student, s.phone studentPhone, r.registration_date registrationDate 
			from registration r 
			join classes c on r.classes_id = c.id 
			join teacher t on c.teacher_id = t.id join account ta on t.id = ta.id 
			join student s on r.student_id = s.id join account sa on s.id = sa.id 
			where r.classes_id = :classId
			""";
	
	private static final String SELECT_BY_STUDENT = """
			select r.classes_id classId, c.teacher_id teacherId, ta.name teacher, c.description classInfo, 
			c.start_date startDate, r.student_id studentId, sa.name student, s.phone studentPhone, r.registration_date registrationDate 
			from registration r 
			join classes c on r.classes_id = c.id 
			join teacher t on c.teacher_id = t.id join account ta on t.id = ta.id 
			join student s on r.student_id = s.id join account sa on s.id = sa.id 
			where r.student_id = :studentId
			""";
	
	private NamedParameterJdbcTemplate template;
	private SimpleJdbcInsert registInsert;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private ClassService clsService;
	
	public RegistrationService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
		
		registInsert = new SimpleJdbcInsert(dataSource);
		registInsert.setTableName("registration");
		//registInsert.setColumnNames(List.of("classes_id", "student_id", "registration_date"));		
	}

	@Transactional
	public void save(RegistrationForm form) {
		if( form.getStudentId() > 0 ) {
			update(form);
		} else {
			create(form);
		}
		
	}

	public List<RegistrationListVO> searchByClassId(int id) {
		return template.query(SELECT_BY_CLASS, Map.of("classId", id),
				new BeanPropertyRowMapper<RegistrationListVO>(RegistrationListVO.class));
	}
	
	public List<RegistrationListVO> searchByStudentId(int id) {
		return template.query(SELECT_BY_STUDENT, Map.of("studentId", id),
				new BeanPropertyRowMapper<RegistrationListVO>(RegistrationListVO.class));
	}

	public RegistrationDetailsVO findDetailsById(int classId, int studentId) {
		RegistrationDetailsVO registDetail = new RegistrationDetailsVO();
		
		registDetail.setRegistDate(LocalDate.now());
		registDetail.setClassInfo(clsService.findInfoById(classId));
		registDetail.setStudent(studentService.findInfoById(studentId));		
		
		return registDetail;
	}

	public RegistrationForm getFormById(int classId, int studentId) {
		var sql = """
				select r.classes_id classId, s.id studentId, r.registration_date registDate,
				a.name studentName, a.email, s.phone, s.education
				from registration r
				join student s on s.id = r.student_id
				join account a on a.id = s.id
				where r.classes_id = :classId and r.student_id = :studentId
				""";

		return template.queryForObject(
				sql,
				Map.of("classId",classId, "studentId", studentId),
				new BeanPropertyRowMapper<RegistrationForm>(RegistrationForm.class));
	}
	
	private void create(RegistrationForm form) {
		
		var studentId = studentService.createStudent(form);
		
		if(studentId != null) {
			form.setStudentId(studentId);
		}
		
		if(form.getRegistDate() == null) {
			form.setRegistDate(LocalDate.now());
		}
		
		registInsert.execute(Map.of(
				"classes_id", form.getClassId(),
				"student_id", form.getStudentId(),
				"registration_date", Date.valueOf(form.getRegistDate())
				));
	}
	
	private void update(RegistrationForm form) {
		template.update("""
				update registration 
				set registration_date = :registDate 
				where classes_id =:classId and student_id = :studentId
				""", Map.of(
						"registDate", Date.valueOf(form.getRegistDate()),
						"classId", form.getClassId(),
						"studentId", form.getStudentId()
						));
		
		template.update("""
				update student set phone = :phone, education = :education
				where id = :id
				""", Map.of(
				"phone", form.getPhone(),
				"education", form.getEducation(),
				"id", form.getStudentId()
				));
	}
}