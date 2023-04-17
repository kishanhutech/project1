package com.example.App1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class changepasswordDto {

	@Email(message = "check your mail")
	private String email;
	@Size(min =6,message = "atleast 6 character")
	private String oldPassword ;
	@Size(min = 6,message ="password must more than 6 character")
	private String newPassword ;
	private String message ;
	
}
