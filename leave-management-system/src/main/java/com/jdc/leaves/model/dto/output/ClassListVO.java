package com.jdc.leaves.model.dto.output;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ClassListVO {

	private int id;

	private int teacherId;

	private String teacherName;

	private String teacherPhone;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	private int months;

	private String description;

	private long studentCount;

	public ClassListVO() {
		super();
	}

	public ClassListVO(int id, int teacherId, String teacherName, String teacherPhone, LocalDate startDate, int months,
			String description, long studentCount) {
		super();
		this.id = id;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.teacherPhone = teacherPhone;
		this.startDate = startDate;
		this.months = months;
		this.description = description;
		this.studentCount = studentCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
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

	public long getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(long studentCount) {
		this.studentCount = studentCount;
	}

}