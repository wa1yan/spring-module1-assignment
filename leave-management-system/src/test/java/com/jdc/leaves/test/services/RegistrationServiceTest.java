package com.jdc.leaves.test.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.leaves.model.dto.input.RegistrationForm;
import com.jdc.leaves.model.service.RegistrationService;

@SpringJUnitConfig(locations = "/root-config.xml")
public class RegistrationServiceTest {
	
	@Autowired
	private RegistrationService service;
	
	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/teacher.sql"})
	@CsvSource("1,6,,william,william@gmail.com,0955554444,University")
	void save_success_test(int classId, int studentId, LocalDate registDate, String studentName, String email, String phone, String education) {
		RegistrationForm form = new RegistrationForm(classId, 0, registDate, studentName, email, phone, education);
		
		service.save(form);
		
		assertThat(service.getFormById(classId, studentId),
				allOf(hasProperty("studentId", is(studentId)),
					  hasProperty("registDate", is(LocalDate.now())),
					  hasProperty("studentName", is(studentName)),
					  hasProperty("email", is(email)),
					  hasProperty("phone", is(phone)),
					  hasProperty("education", is(education))
					)
				);
	}
	
	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/teacher.sql"})
	@CsvSource(value = {"1"})
	void search_by_class_id_test(int id) {
		
		assertNotEquals(0, service.searchByClassId(id).size());
	}
	
	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/teacher.sql"})
	@CsvSource(value = {"1","2"})
	void search_by_student_id_test(int id) {
		
		assertNotEquals(0, service.searchByStudentId(id).size());
		assertEquals(1, service.searchByStudentId(id).size());
	}
	
	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/student.sql"})
	@CsvSource(value = {
			"1, 1, 3, Maung Maung, 09254295287, 2022-09-18, student1, 0955554444, student1@gmail.com, University, 1",
			"1, 2, 3, Maung Maung, 09254295287, 2022-09-18, student2, 0966665555, student2@gmail.com, College, 1",
	})
	void find_detail_by_id_test(int classId, int studentId, int teacherId,
			String teacherName, String teacherPhone, LocalDate startDate,
			String name, String phone, String email, String education, long classCount) {
	
		var result = service.findDetailsById(classId, studentId);
		
		assertEquals(result.getRegistDate(), LocalDate.now());
		
		assertThat(result.getClassInfo(),allOf(
				hasProperty("teacherId", is(teacherId)),
				hasProperty("teacherName", is(teacherName)),
				hasProperty("teacherPhone", is(teacherPhone)),
				hasProperty("startDate", is(startDate))));
		
		assertThat(result.getStudent(),allOf(
				hasProperty("name", is(name)),
				hasProperty("phone", is(phone)),
				hasProperty("email", is(email)),
				hasProperty("education", is(education)),
				hasProperty("classCount", is(classCount))));

	}
	
	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/student.sql"})
	@CsvSource(value = {
			"1,1,2022-08-12,student1,student1@gmail.com,0955554444,University",
			"1,2,2022-08-18,student2,student2@gmail.com,0966665555,College",
	})
	void get_form_by_id_test(int classId, int studentId, LocalDate registDate, String studentName,
			String email, String phone, String education) {
		
		var result = service.getFormById(classId, studentId);
		assertThat(result, allOf(
				hasProperty("registDate", is(registDate)),
				hasProperty("studentName", is(studentName)),
				hasProperty("email", is(email)),
				hasProperty("phone", is(phone)),
				hasProperty("education", is(education))
				));
	}

}
