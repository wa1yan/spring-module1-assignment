package com.jdc.leaves.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jdc.leaves.model.dto.input.RegistrationForm;
import com.jdc.leaves.model.dto.output.RegistrationDetailsVO;
import com.jdc.leaves.model.dto.output.RegistrationListVO;

@Service
public class RegistrationService {

	public int save(RegistrationForm form) {
		// TODO implement here
		return 0;
	}

	public List<RegistrationListVO> searchByClassId(int id) {
		// TODO implement here
		return null;
	}

	public RegistrationDetailsVO findDetailsById(int classId) {
		// TODO implement here
		return null;
	}

	public RegistrationForm getFormById(int id) {
		return null;
	}

}