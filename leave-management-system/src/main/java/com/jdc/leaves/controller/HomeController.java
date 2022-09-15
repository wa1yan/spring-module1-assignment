package com.jdc.leaves.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

	public HomeController() {
	}

	public String index(Optional<LocalDate> targetDate) {
		// TODO implement here
		return "";
	}

}