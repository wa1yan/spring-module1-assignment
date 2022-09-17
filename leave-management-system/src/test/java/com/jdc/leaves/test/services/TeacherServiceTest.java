package com.jdc.leaves.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.leaves.model.dto.input.TeacherForm;
import com.jdc.leaves.model.service.TeacherService;

@SpringJUnitConfig(locations = "/root-config.xml")
public class TeacherServiceTest {

	@Autowired
	private TeacherService teacherService;
	
	@ParameterizedTest
	@Sql(scripts = "/sql/truncate.sql")
	@CsvSource("0,Maung Maung,09254295287,maung@gmail.com,2022-09-17")
	void save_insert_success(int id, String name, String phone, String email, LocalDate assignDate) {
		
		var form = new TeacherForm(id,name,phone,email,assignDate);
		var result = teacherService.save(form);
		
		assertEquals(1, result);
	}
	
	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/teacher.sql"})
	@CsvSource(value = {
			"1,Maung Maung,09254295287,maung@gmail.com,2022-09-17,2",
			"2,Aung Aung,09854715477,aung@gmail.com,2022-08-17,1",
			"3,Ba Maung,09568754214,bamaung@gmail.com,2022-07-17,0"
	})
	void test_find_by_id(int id, String name, String phone, String email, LocalDate assignDate, int classCount) {
		var teacher = teacherService.findById(id);
		
		assertEquals(name, teacher.getName());
		assertEquals(phone, teacher.getPhone());
		assertEquals(email, teacher.getEmail());
		assertEquals(assignDate, teacher.getAssignDate());
		assertEquals(classCount, teacher.getClassCount());

	}
}
