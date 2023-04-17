package com.example.App1.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
@Data
public class LogoutDto {

	@Email   
	private String email;
	private String message;
	
}
