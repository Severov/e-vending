package com.dao;

import java.util.Random;

import org.springframework.transaction.annotation.Transactional;

import com.model.TempRegModule;

public interface TempRegModulDAO {

	static String generateSecretCode(int length) {
		Random rnd = new Random();

		String code = "";

		for (int i = 0; i < length; ++i) {
			code += rnd.nextInt(9);
		}

		return code;
	}

	@Transactional(readOnly = true)
	TempRegModule findBySecretCode(String secret);

}
