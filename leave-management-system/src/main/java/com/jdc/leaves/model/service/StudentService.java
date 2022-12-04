package com.jdc.leaves.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jdc.leaves.model.dto.input.RegistrationForm;
import com.jdc.leaves.model.dto.output.StudentDetailVO;
import com.jdc.leaves.model.dto.output.StudentListVO;

@Service
public class StudentService {
	
	private static final String SELECT_PROJECTION = """
			select s.id, a.name, s.phone, a.email, s.education, count(r.classes_id) classCount 
			from student s
			join account a on a.id = s.id
			join registration r on r.student_id = s.id
			where 1 = 1 
			""";
	
	private static final String GROUP_BY = """
			group by s.id, a.name, s.phone, a.email, s.education
			""";
	
	private NamedParameterJdbcTemplate template;
	private SimpleJdbcInsert accountInsert;
	private SimpleJdbcInsert studentInsert;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RegistrationService regService;
	
	public StudentService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
		
		accountInsert = new SimpleJdbcInsert(dataSource);
		accountInsert.setTableName("account");
		accountInsert.setGeneratedKeyName("id");
		accountInsert.setColumnNames(List.of("name","role","email","password"));
		
		studentInsert = new SimpleJdbcInsert(dataSource);		
		studentInsert.setTableName("student");
		//studentInsert.setColumnNames(List.of("classes_id", "student_id","registration_date"));
	}

	public List<StudentListVO> search(Optional<String> name, Optional<String> phone, Optional<String> email) {
		var where = new StringBuffer();
		var params = new HashMap<String, Object>();
		
		where.append(email
						.filter(StringUtils::hasLength)
						.map( e -> {
							params.put("email", e.toLowerCase().concat("%"));
							return "and lower(a.email) like :email ";
						}).orElse("")
		);
		
		where.append(name
						.filter(StringUtils::hasLength)
						.map( n -> {
							params.put("name", n.toLowerCase().concat("%"));
							return "and lower(a.name) like :name ";
						}).orElse("")
				);
		
		where.append(phone
						.filter(StringUtils::hasLength)
						.map( p -> {
							params.put("phone", p.concat("%"));
							return "and s.phone like :phone ";
						}).orElse("")
		);
	
		var sql = "%s %s %s ".formatted(SELECT_PROJECTION, where.toString(),GROUP_BY);
		return template.query(sql, params, new BeanPropertyRowMapper<StudentListVO>(StudentListVO.class));
	}
	
	public StudentListVO findInfoById(Integer studentId) {
		var sql = "%s and s.id = :id  %s".formatted(SELECT_PROJECTION, GROUP_BY);
		return template.queryForObject(sql, Map.of("id", studentId), new BeanPropertyRowMapper<StudentListVO>(StudentListVO.class));
	}
	
	public Integer findStudentIdByEmail(String email) {
		return template.queryForObject("""
				select s.id
				from student s
				join account a on a.id = s.id
				where a.email = :email 	
				""", Map.of("email", email), Integer.class);
	}

	public StudentDetailVO findDetailsByLoginId(String email) {
		var result = new StudentDetailVO();
		
		var studentId = findStudentIdByEmail(email);
		
		result.setStudent(findInfoById(studentId));
		result.setRegistrations(regService.searchByStudentId(studentId));
		
		return result;
	}

	public Integer createStudent(RegistrationForm form) {
		// account insert
		var studentId = accountInsert.executeAndReturnKey(Map.of(
				"name", form.getStudentName(),
				"role", "Student",
				"email", form.getEmail(),
				"password", passwordEncoder.encode(form.getPhone())));
		form.setStudentId(studentId.intValue());

		// student insert
		studentInsert.execute(Map.of(
						"id", form.getStudentId(),
						"phone", form.getPhone(),
						"education", form.getEducation()));
		
		return form.getStudentId();
	}

}