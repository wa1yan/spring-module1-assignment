package com.jdc.leaves.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jdc.leaves.model.dto.input.ClassForm;
import com.jdc.leaves.model.dto.output.ClassDetailsVO;
import com.jdc.leaves.model.dto.output.ClassListVO;

@Service
public class ClassService {

	public List<ClassListVO> search(Optional<String> teacher, Optional<LocalDate> from, Optional<LocalDate> to) {
		return null;
	}

	public ClassForm findById(int id) {
		return null;
	}

	public ClassListVO findInfoById(int id) {
		return null;
	}

	public ClassDetailsVO findDetailsById(int id) {
		return null;
	}

	public int save(ClassForm form) {
		return 0;
	}

}