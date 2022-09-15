package com.jdc.leaves.model.dto.output;

import java.time.LocalDate;

public class RegistrationDetailsVO {

	public RegistrationDetailsVO() {
	}

	private LocalDate registDate;

	private ClassListVO classInfo;

	private StudentListVO student;

	public LocalDate getRegistDate() {
		return registDate;
	}

	public void setRegistDate(LocalDate registDate) {
		this.registDate = registDate;
	}

	public ClassListVO getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(ClassListVO classInfo) {
		this.classInfo = classInfo;
	}

	public StudentListVO getStudent() {
		return student;
	}

	public void setStudent(StudentListVO student) {
		this.student = student;
	}

}