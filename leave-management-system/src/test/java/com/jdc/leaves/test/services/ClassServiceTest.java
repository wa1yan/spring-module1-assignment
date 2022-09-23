package com.jdc.leaves.test.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Optional;

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
			"3,2,2022-09-11,3,Flutter",
			})
	void test_find_by_id(int classId, int teacherId, LocalDate start, int months, String description) {
		var classForm = classService.findById(classId);

		assertEquals(teacherId, classForm.getTeacher());
		assertEquals(start, classForm.getStart());
		assertEquals(months, classForm.getMonths());
		assertEquals(description, classForm.getDescription());
	}
	
	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/teacher.sql"})
	@CsvSource(value = {
		"1, 1, Maung Maung, 09254295287, 2022-09-18, 3, SpringFramework, 2",
		"2, 1, Maung Maung, 09254295287, 2022-09-18, 3, SpringBoot, 0",
		"3, 2, Aung Aung, 09854715477, 2022-09-11, 3, Flutter, 0"	
	})
	void test_find_detail_by_id(int id, int teacherId, String teacherName, String teacherPhone, LocalDate startDate, int months,
			String description, long studentCount) {
		
		var classDetailsVO = classService.findDetailsById(id);
		
		var classListVo = classDetailsVO.getClassInfo();
		
		assertNotNull(classDetailsVO);
		
		assertNotNull(classListVo);
		assertThat(classListVo,
				  allOf(hasProperty("teacherId", equalTo(teacherId)),
						hasProperty("teacherName", equalTo(teacherName)),
						hasProperty("teacherPhone", equalTo(teacherPhone)),
						hasProperty("startDate", equalTo(startDate)),
						hasProperty("months", equalTo(months)),
						hasProperty("description", equalTo(description)),
						hasProperty("studentCount", equalTo(studentCount))));
		
		
		
		//var registrations = classDetailsVO.getRegistrations();
		//var leaves = classDetailsVO.getLeaves();
		//assertEquals(0,registrations.size());
		//assertEquals(0,leaves.size());
		
		//assertEquals(null, null);
	}
	
	@ParameterizedTest
	@Sql(scripts = {"/sql/truncate.sql","/sql/teacher.sql"})
	@CsvSource(value = {
			"Thidar,,,0",
			"Aung,,,1",
			"Maung,,,2",
			",,,3",
			",2022-09-22,,0",
			",2022-09-18,,2",
			",2022-09-11,,3",
			",,2022-09-11,1",
			",,2022-09-18,3",
			",,2022-09-22,3",
			",2022-09-18,2022-09-21,2",
			",2022-09-11,2022-09-18,3",

			})
	void search_test(String teacher, LocalDate from, LocalDate to, int count) {
		var list = classService.search(
						Optional.ofNullable(teacher),
						Optional.ofNullable(from),
						Optional.ofNullable(to));

		assertEquals(count, list.size());

	}

}
