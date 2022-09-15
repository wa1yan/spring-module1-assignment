package com.jdc.leaves.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Controller;

import com.jdc.leaves.model.dto.input.ClassForm;
import com.jdc.leaves.model.dto.input.RegistrationForm;

@Controller
public class ClassController {

	public ClassController() {
	}

	public String index(Optional<String> teacher, Optional<LocalDate> from, Optional<LocalDate> to) {
		// TODO implement here
		return "";
	}

	public String edit(Optional<Integer> id) {
		// TODO implement here
		return "";
	}

	public String save(ClassForm form) {
		// TODO implement here
		return "";
	}

	public String showDetails(int id) {
		// TODO implement here
		return "";
	}

	public String editRegistration(int studentId, int classId) {
		// TODO implement here
		return "";
	}

	public String saveRegistration(RegistrationForm form) {
		// TODO implement here
		return "";
	}

	public String showRegistrationDetails(int classId, int studentId) {
		// TODO implement here
		return "";
	}

}