package com.jdc.leaves.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.leaves.model.dto.input.ClassForm;
import com.jdc.leaves.model.dto.input.RegistrationForm;
import com.jdc.leaves.model.service.ClassService;

@Controller
@RequestMapping("/classes")
public class ClassController {

	@Autowired
	private ClassService classService;
	
	@GetMapping
	public String index(
			@RequestParam Optional<String> teacher,
			@RequestParam Optional<LocalDate> from,
			@RequestParam Optional<LocalDate> to,
			ModelMap model) {	
		model.put("list", classService.search(teacher, from, to));
		return "classes";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Optional<Integer> id,
			ModelMap model) {
		model.put("classes", classService.findById(id));
		return "classes-edit";
	}

	@PostMapping
	public String save(@ModelAttribute ClassForm form) {
		return "";
	}

	@GetMapping("/details/{id}")
	public String showDetails(@PathVariable int id) {
		return "classes-details";
	}

	@GetMapping("/registration/{id}")
	public String editRegistration(@PathVariable int id) {
		return "registration-edit";
	}

	@PostMapping("/registration")
	public String saveRegistration(@ModelAttribute RegistrationForm form) {
		return "";
	}

	@GetMapping("/registration/details/{id}")
	public String showRegistrationDetails(@PathVariable int id) {
		return "registration-details";
	}
	
	

}