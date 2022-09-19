package com.jdc.leaves.model.dto.input;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ClassForm {

	private int id;

	private int teacher;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate start;

	private int months;

	private String description;

	public ClassForm() {
		super();
	}

	public ClassForm(int id, int teacher, LocalDate start, int months, String description) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.start = start;
		this.months = months;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeacher() {
		return teacher;
	}

	public void setTeacher(int teacher) {
		this.teacher = teacher;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}