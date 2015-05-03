package com.quiz.implementation.interfaces;

import org.springframework.http.ResponseEntity;

import com.quiz.dto.LoginDTO;

public interface IAuthInterfaceForLogin {

		public ResponseEntity login(LoginDTO loginDTO);
	
}
