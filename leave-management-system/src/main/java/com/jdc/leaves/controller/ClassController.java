package com.jdc.leaves.controller;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.leaves.model.dto.input.ClassForm;
import com.jdc.leaves.model.dto.input.RegistrationForm;
import com.jdc.leaves.model.service.ClassService;
import com.jdc.leaves.model.service.RegistrationService;
import com.jdc.leaves.model.service.TeacherService;

@Controller
@RequestMapping("/classes")
public class ClassController {

	@Autowired
	private RegistrationService regService;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping
	public String index(
			@RequestParam Optional<String> teacher,
			@RequestParam Optional<LocalDate> from,
			@RequestParam Optional<LocalDate> to,
			ModelMap model) {	
		model.put("list", classService.search(teacher, from, to));
		return "classes";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam Optional<Integer> id,
			ModelMap model) {
		model.put("teachers", teacherService.getAvailableTeachers());
		return "classes-edit";
	}

	@PostMapping
	public String save(@Valid @ModelAttribute ClassForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "classes-edit";
		}
		var id = classService.save(form);
		return "redirect:/classes/%d".formatted(id);
	}

	@GetMapping("/{id}")
	public String showDetails(@PathVariable int id, ModelMap model) {
		var result = classService.findById(id);
		model.put("dto", result);
		return "classes-details";
	}

	@GetMapping("/registration")
	public String editRegistration(
			@RequestParam(required = false, defaultValue ="0" ) int registId,
			@RequestParam(required = false, defaultValue ="0" ) int classId) {
		
		return "registration-edit";
	}
	
	@PostMapping("/registration")
	public String saveRegistration(@Valid @ModelAttribute(name = "registForm") RegistrationForm form,  BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "registration-edit";
		}
		var id = regService.save(form);
		return "redirect:/classes/registration/%d".formatted(id);
	}

	@GetMapping("/registration/{id}")
	public String showRegistrationDetails(@PathVariable int id, ModelMap model) {
		var result = regService.findDetailsById(id);
		model.put("dto",result);
		return "registration-details";
	}
	
	@ModelAttribute(name = "classForm")
	ClassForm classForm(@RequestParam Optional<Integer> id) {
		return id
				.filter(a -> a > 0)
				.map(classService::findById)
				.orElse(new ClassForm());
	}
	
	@ModelAttribute(name = "registForm")
	RegistrationForm registForm(
			@RequestParam(required = false, defaultValue ="0" ) int registId,
			@RequestParam(required = false, defaultValue ="0" ) int classId) {

		if(registId > 0) {
			return regService.getFormById(registId);
		}
		
		var form = new RegistrationForm();
		form.setClassId(classId);
		return form;
	}

}