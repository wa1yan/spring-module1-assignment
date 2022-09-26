package com.jdc.leaves.model.dto.input;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class TeacherForm {

	private int id;

	@NotEmpty(message = "Please enter teacher name.")
	private String name;

	@NotEmpty(message = "Please enter phone number.")
	private String phone;

	@NotEmpty(message = "Please enter email address.")
	private String email;

	@NotNull(message = "Please enter assign date.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate assignDate;

	public TeacherForm() {
		super();
	}

	public TeacherForm(int id, String name, String phone, String email, LocalDate assignDate) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.assignDate = assignDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(LocalDate assignDate) {
		this.assignDate = assignDate;
	}

}