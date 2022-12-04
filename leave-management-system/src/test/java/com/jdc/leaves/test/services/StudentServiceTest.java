package com.jdc.leaves.test.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.leaves.model.dto.input.RegistrationForm;
import com.jdc.leaves.model.dto.output.StudentListVO;
import com.jdc.leaves.model.service.StudentService;

@SpringJUnitConfig(locations = "/root-config.xml")
@Sql(scripts = {"/sql/truncate.sql","/sql/student.sql"})
public class StudentServiceTest {

	@Autowired
	private StudentService service;
	
	@ParameterizedTest
	@CsvSource({
			"2,6,2022-10-02,student3, student3@gmail.com, 0977776666, University",
			"1,6,2022-10-02,student4, student4@gmail.com, 0988887777, University",
	})
	void create_success_test(int classId, int studentId, LocalDate registDate, 
			String studentName, String email, String phone,String education
			) {
		var form = new RegistrationForm(classId, 0, registDate, studentName, email, phone, education);
		var result = service.createStudent(form);
		assertEquals(studentId, result);
	}
	
	@ParameterizedTest
	@CsvSource("1,student1,0955554444,student1@gmail.com,University,1")
	void search_info_by_id_test(int studentId, String name, String phone, String email, String education, long classCount) {
		var result = service.findInfoById(studentId);
		
		assertThat(result, allOf(
							hasProperty("id", is(studentId)),
							hasProperty("name", is(name)),
							hasProperty("phone", is(phone)),
							hasProperty("email", is(email)),
							hasProperty("education", is(education)),
							hasProperty("classCount", is(classCount))
				));
		
		assertThat(result, Matchers.equalTo(new StudentListVO(studentId, name, phone, email, education, classCount)));
		
	}
	
	@ParameterizedTest
	@CsvSource(value = {
					"student1,,",
					",096666,",
					",,student2@gmail"
	})
	void student_search_test(String name, String phone, String email) {
		var result = service.search(
				Optional.ofNullable(name),
				Optional.ofNullable(phone),
				Optional.ofNullable(email)
				);
		assertEquals(1, result.size());
	}
	
	@ParameterizedTest
	@CsvSource(value = {
					"1,student1@gmail.com",
					"2,student2@gmail.com"
	})
	void find_student_by_email_test(int id, String email) {
		var result = service.findStudentIdByEmail(email);
		assertEquals(id, result);
	}
	
	@ParameterizedTest
	@CsvSource(value = {
					"1, student1, 0955554444, student1@gmail.com, University, 1",
					"2, student2, 0966665555, student2@gmail.com, College, 1"
	})
	void find_detail_by_login_id_test(int id, String name, String phone,String email, String education, long classCount) {
		//var student = new StudentListVO(id,name,phone,email, education, classCount);
		var result = service.findDetailsByLoginId(email);
		assertNotEquals(null, result);
		assertThat( result.getStudent(),
					allOf(	hasProperty("id", is(id)),
							hasProperty("name", is(name)),
							hasProperty("phone", is(phone)),
							hasProperty("email", is(email)),
							hasProperty("education", is(education)),
							hasProperty("classCount", is(classCount))
						)
				);
	}
}
