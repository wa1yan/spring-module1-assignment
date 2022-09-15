package com.jdc.leaves.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.leaves.model.dto.input.TeacherForm;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

	@GetMapping
	public String index(
			@RequestParam Optional<String> name,
			@RequestParam Optional<String> phone,
			@RequestParam Optional<String> email) {
		return "teachers";
	}

	@GetMapping("/{id}")
	public String edit(@PathVariable Optional<Integer> id) {
		return "teachers-edit";
	}

	@PostMapping
	public String save(@ModelAttribute TeacherForm form) {
		return "";
	}

}