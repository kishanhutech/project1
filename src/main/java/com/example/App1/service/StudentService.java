package com.example.App1.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.App1.Entity.Student;
import com.example.App1.dto.ForgottenpasswordDto;
import com.example.App1.dto.LoginDto;
import com.example.App1.dto.LogoutDto;
import com.example.App1.dto.changepasswordDto;
import com.example.App1.repo.StudentRepo;


@Service
public class StudentService {

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private MailService mailService;

	@Autowired
	private  ModelMapper modelMapper;
	Student st;
	
	//------------------------------SAVE----------------------------------------------------------
	
	public Student save(Student student) throws Exception
	{
			studentRepo.save(student);
			String activationCode = mailService.sendEmailToVerify(student.getEmail()) ;				
			student.setActivationCode(activationCode);
			studentRepo.save(student) ;	
		return student ;
	}


	//================================FINDBYEMAIL==================================================
	public Student findByEmail(String email)
	{
		Student str = studentRepo.findByEmail(email);
		String Message ="user doesn't exist";
		if(str==null) throw new NoSuchElementException(Message);
		return str;
	}


	//;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;UPDATE;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	public Student update(Student student)
	{
		return studentRepo.save(student);
	}


	//===============================LOGIN=============================================================
	public Student login(LoginDto loginDto) throws Exception
	{
		st = findByEmail(loginDto.getEmail());
		if(st.getLogin()==1) {  loginDto.setStatus("already logged in"); return st;}
		Student student = studentRepo.findByEmailAndPassWord(loginDto.getEmail(),loginDto.getPassWord());

		if(student!=null)
		{
			loginDto.setStatus("welcome to your World");
			if(student.getStatus()==0)
			{
				loginDto.setStatus("please Activate your mail");
			}
			else
			{
				st.setCount(0);
				st.setLogin(1);
			}
		}
		else
		{
			st.setCount(st.getCount()+1);
			if(st.getCount()>=5 && st.getStatus()==1)
			{
				st.setStatus(0);
				loginDto.setStatus("your acount is deavtivated");
				String code = mailService.sendEmailToVerify(st.getEmail());
			    st.setActivationCode(code);
			}
			else if(st.getStatus()==0)
			{
				loginDto.setStatus("please activate your account");
			}
			else
			{
				loginDto.setStatus("login failed " + (5 - st.getCount())+" attempts rest");

			}

		}
		return st;
	}



	//==========================================FINDBYID======================================
	public Optional<Student> findbyid(Integer id) 
	{
	Optional<Student> st = studentRepo.findById(id);
	if (st.isEmpty())
	{
		throw new NoSuchElementException("id doesn't exist");
	}
	return st;
	}


	//==============================================CHANGEPASSWORD======================================
	public Student changepass(changepasswordDto changepasswordDto)
	{
		Student st = findByEmail(changepasswordDto.getEmail());
		if(st.getLogin()==1) {
			changepasswordDto.setMessage("old password is incorrect");
			if(changepasswordDto.getOldPassword().equals(st.getPassWord()))
			{
				if(changepasswordDto.getNewPassword().equals(changepasswordDto.getOldPassword()))
				{
					changepasswordDto.setMessage("you cant use old password try different password");
				}
				else
				{
					st.setPassWord(changepasswordDto.getNewPassword());
					changepasswordDto.setMessage("password changed successfully");
					st.setLogin(0);
				}
			}
		}
		else
		{
			changepasswordDto.setMessage("login first");
		}
		return st;
	}


	//====================LOGOUT========================================================
	public Student logout(LogoutDto logoutDto)
	{
		Student student = findByEmail(logoutDto.getEmail());
		if(student.getLogin()==1)
		{
			student.setLogin(0);
			logoutDto.setMessage("logout successfully , thankyou for visiting");
		}
		else
		{
			logoutDto.setMessage("you are not logged in , please login");
		}
		return student;
	}
//====================================	FORGOTTONPASSWORD==============================
	
	public Student forgottenpass(ForgottenpasswordDto forgottenpasswordDto) throws Exception
	{
		Student student = findByEmail(forgottenpasswordDto.getEmail());
		String code = mailService.sendEmailTOforgottenpass(forgottenpasswordDto.getEmail());
		student.setPassWord(code);
		forgottenpasswordDto.setMessage("this is your new passWord");
		update(student);
		return student;
	}
	
	public boolean isvalidpassword(String st)
	{
		String pattern = ("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
		return st.matches(pattern);
	}
	
	public Student dtoStudent(LoginDto loginDto)
	{
		Student student = this.modelMapper.map(loginDto, Student.class);
	     return student;
	}
	
	public LoginDto logindto(Student  student)
	{
	   LoginDto loginDto = this.modelMapper.map(student,LoginDto.class);
	   return loginDto;
	}
}
