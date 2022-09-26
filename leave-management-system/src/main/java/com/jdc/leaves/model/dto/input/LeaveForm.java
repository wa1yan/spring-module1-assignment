package com.jdc.leaves.model.dto.input;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

public class LeaveForm {

	public LeaveForm() {
	}

	private int classId;

	private int student;

	@DateTimeFormat(pattern = "yy-MM-dd")
	private LocalDate applyDate;

	@DateTimeFormat(pattern = "yy-MM-dd")
	private LocalDate startDate;

	@Min(value = 1, message = "Please enter leave days.")
	private int days;

	@NotEmpty(message = "Please enter reason for leaves.")
	private String reason;

	public int getClassId() {
		return classId;
	}

	public LeaveForm(int classId, int student) {
		super();
		this.classId = classId;
		this.student = student;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getStudent() {
		return student;
	}

	public void setStudent(int student) {
		this.student = student;
	}

	public LocalDate getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(LocalDate applyDate) {
		this.applyDate = applyDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}