package com.example.App1.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
	
	@Email(message = "check your id and write again")
	private String email;
	private String passWord;
	private String status;

}
