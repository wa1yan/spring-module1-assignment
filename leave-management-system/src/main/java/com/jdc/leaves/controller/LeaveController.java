package com.jdc.leaves.controller;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String edit(@RequestParam int classId, @RequestParam int studentId) {
		return "leaves-edit";
	}

	@PostMapping
	public String save(@Valid @ModelAttribute LeaveForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "leaves-edit";
		}
		return "";
	}
	
	@ModelAttribute
	LeaveForm form(@RequestParam(required = false) Integer classId, @RequestParam(required = false) Integer studentId) {
		if(null != classId || null != studentId) {
			return new LeaveForm(classId,studentId);
		}
		return null;
	}

}