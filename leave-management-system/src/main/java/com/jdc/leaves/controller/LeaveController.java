package com.jdc.leaves.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.leaves.model.dto.input.LeaveForm;

@Controller
@RequestMapping("/leaves")
public class LeaveController {

	@GetMapping
	public String index(
			@RequestParam Optional<Integer> classId,
			@RequestParam Optional<String> studentName,
			@RequestParam Optional<LocalDate> from,
			@RequestParam Optional<LocalDate> to) {
		return "leaves";
	}

	@GetMapping("/{id}")
	public String edit(@PathVariable Optional<Integer> id) {
		return "leaves-edit";
	}

	@PostMapping
	public String save(@ModelAttribute LeaveForm form) {
		return "";
	}

}