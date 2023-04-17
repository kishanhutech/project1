package com.example.App1.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Entity
@Data
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull(message = "userName must required")
	private String userName;
	@NotNull(message = "please enter a valid password")
	@Size(min = 6,message = "password must more than 6 character")
	private String passWord;
	@Email(message = "enter a valid mail")
	
	@Column(unique = true )
	private String email;
	private String activationCode;
	private Integer status =0;
	private Integer count =0;
	private Integer login =0;

}
