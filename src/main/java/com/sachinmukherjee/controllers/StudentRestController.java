package com.sachinmukherjee.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.HttpAccessor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sachinmukherjee.entity.ErrorResponse;
import com.sachinmukherjee.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentRestController {
	
	private List<Student> theStudents = new ArrayList<Student>();
	
	@PostConstruct
	public void loadData() {
		theStudents.add(new Student("Sachin", "Mukherjee"));
		theStudents.add(new Student("Summet", "Mukherjee"));
		theStudents.add(new Student("Sumit", "Mukherjee"));
	}
	
	//define end point for /student
	@GetMapping("/students")
	public List<Student> getStudent(){
		
		return theStudents;
	}
	
	
	@GetMapping("/students/{studentId}")
	public Student getParticularStudent(@PathVariable int studentId) throws Exception{
		
		if(studentId < 0 || theStudents.size() < studentId) {
			throw new Exception("Student not Found");
		}
		
		return theStudents.get(studentId);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception e){
		ErrorResponse error = new ErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(e.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.NOT_FOUND);
	}
	
	
}
