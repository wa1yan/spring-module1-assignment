package com.jdc.leaves.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Controller;

import com.jdc.leaves.model.dto.input.LeaveForm;

@Controller
public class LeaveController {

	public LeaveController() {
	}

	public String index(Optional<Integer> classId, Optional<String> studentName, Optional<LocalDate> from, Optional<LocalDate> to) {
		// TODO implement here
		return "";
	}

	public String edit(Optional<Integer> id) {
		// TODO implement here
		return "";
	}

	public String save(LeaveForm form) {
		// TODO implement here
		return "";
	}

}