package com.jdc.leaves.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.leaves.model.dto.input.ClassForm;
import com.jdc.leaves.model.service.ClassService;

@SpringJUnitConfig(locations = "/root-config.xml")
public class ClassServiceTest {

	@Autowired
	private ClassService classService;

	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/teacher.sql"})
	@CsvSource("0,3,2022-10-10,3,Spring fullstack online")
	void save_success_test(int id, int teacher, LocalDate startDate, int months, String description) {
		var form = new ClassForm(id, teacher, startDate, months, description);
		assertEquals(4, classService.save(form));
	}
	
	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/teacher.sql"})
	@CsvSource("3,2,2022-10-20,4,Flutter")
	void update_success_test(int id, int teacher, LocalDate startDate, int months, String description) {
		var form = new ClassForm(id, teacher, startDate, months, description);
	
		assertEquals(1, classService.save(form));
	}
	
	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/teacher.sql"})
	@CsvSource(value = {
			"1,1,2022-09-18,3,SpringFramework",
			"2,1,2022-09-18,3,SpringBoot",
			"3,2,2022-10-20,3,Flutter",
			})
	void test_find_by_id(int classId, int teacherId, LocalDate start, int months, String description) {
		var classForm = classService.findById(classId);
		System.out.println(classService.findById(classId).getTeacher());
		assertEquals(teacherId, classForm.getTeacher());
		assertEquals(start, classForm.getStart());
		assertEquals(months, classForm.getMonths());
		assertEquals(description, classForm.getDescription());
	}

}
