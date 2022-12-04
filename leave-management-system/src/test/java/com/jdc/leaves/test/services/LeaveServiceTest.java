package com.jdc.leaves.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.leaves.model.service.LeaveService;

@SpringJUnitConfig(locations = "/root-config.xml")
public class LeaveServiceTest {
	
	@Autowired
	private LeaveService leaveService;

	@ParameterizedTest
	@CsvSource(value = {
			"1,,",
			",2022-10-01,",
			",,2022-10-07"
	})
	void search_success(Integer classId, LocalDate from, LocalDate to) {
		var list = leaveService.search(
				Optional.ofNullable(classId),
				Optional.ofNullable(from),
				Optional.ofNullable(to));

		assertEquals(3, list.size());
	}
}
