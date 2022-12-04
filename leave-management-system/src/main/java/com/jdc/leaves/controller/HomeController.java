package com.jdc.leaves.controller;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.leaves.model.dto.output.StudentDetailVO;
import com.jdc.leaves.model.service.LeaveService;
import com.jdc.leaves.model.service.StudentService;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private LeaveService leaveService;

	@Autowired
	private StudentService studentService;

	@GetMapping
	public String index(
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam Optional<LocalDate> targetDate,
			ModelMap model) {
		SecurityContextHolder.getContext().getAuthentication().getAuthorities();

		Function<String, Boolean> hasAuthority = authority -> {
			return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
					.anyMatch(a -> a.getAuthority().equals(authority));
		};

		if (hasAuthority.apply("ROLE_Admin") || hasAuthority.apply("ROLE_Teacher")) {
			model.put("targetDate", targetDate.orElse(LocalDate.now()));
			model.put("list", leaveService.searchSummary(targetDate));
			return "teacher-home";
		}

		StudentDetailVO student = studentService
				.findDetailsByLoginId(SecurityContextHolder.getContext().getAuthentication().getName());
		model.put("dto", student);
		return "student-home";
	}

}