package com.jdc.leaves.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;

import com.jdc.leaves.model.dto.input.TeacherForm;

@Controller
public class TeacherController {

	public TeacherController() {
	}

	public String index(Optional<String> name, Optional<String> phone, Optional<String> email) {
		// TODO implement here
		return "";
	}

	public String edit(Optional<Integer> id) {
		// TODO implement here
		return "";
	}

	public String save(TeacherForm form) {
		// TODO implement here
		return "";
	}

}