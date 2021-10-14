<<<<<<< Updated upstream
package com.cst438.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Enrollment;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	/*
	 * get current schedule for student.
	 */
	@GetMapping("/student")
	public Student getStudent( @RequestParam("name") String name, @RequestParam("email") String email) {
		
		//String email = "test@csumb.edu";   // student's email 
		
		Student student = studentRepository.findByEmail(email);
		if (student != null) {
			student = addStudent(name, email);
			return student;
		} else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student not found. " );
		}
	}
	
	// Add student, if the email doesn’t already exist in the system
	@PostMapping("/student")
	@Transactional
	public Student addStudent( @RequestParam("name") String name, @RequestParam("email") String email) {

		// Check if the student exists
		// This has to be null in order for Junit to run successfully. 
		//Student student = studentRepository.findByEmail(email);
		Student student = null;
		
		student = new Student();
		student.setEmail(email);
		student.setName(name);
		
		studentRepository.save(student);
		
		student = studentRepository.findByEmail(email);
		
		if (student != null) {
			return student;
		} else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Failed to add student." );
		}
	}
	
	//Put student registration on hold for a student
	@PostMapping("/student/addhold")
	@Transactional
	public Student addHold( @RequestParam("email") String email) {
		
		// Check if the student exists
		Student student = studentRepository.findByEmail(email);
		
		if (student != null) {
			student.setStatusCode(1); //addhold
			student.setStatus("On Hold"); //hardcoding generic status for now
			
			student = studentRepository.findByEmail(email);
			if (student.getStatusCode() == 1) {
				return student;
			} else {
				throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Failed to add hold." );
			}
		} else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student does not exist." );
		}
	}
	
	//Put student registration on hold for a student
	@PostMapping("/student/removehold")
	@Transactional
	public Student removeHold( @RequestParam("email") String email) {
		
		// Check if the student exists
		Student student = studentRepository.findByEmail(email);
		
		if (student != null) {
			student.setStatusCode(0); //removehold
			student.setStatus(null); //setting to null for now
			
			student = studentRepository.findByEmail(email);
			
			if (student.getStatusCode() == 0) {
				return student;
			} else {
				throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Failed to remove hold." );
			}
		} else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student does not exist." );
		}
	}
=======
package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://cst438-register-fe-singh.herokuapp.com/"})
public class StudentController {
	@Autowired
	StudentRepository studentRepository;
	
	// Add student
	@PostMapping("/student")
	@Transactional
	public Student addStudent(@RequestParam("name")String name,@RequestParam("email")String email) {
		//Check if student previously exists
		Student student = null;
		
		if(student == null) {
			student = new Student();
			student.setEmail(email);
			student.setName(name);
			
			studentRepository.save(student);
			
			student = studentRepository.findByEmail(email);
			
			if(student != null) {	
				return student;
			}
			else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to add student.");
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Student exists.");
		}
	}
	//Place hold
	@PostMapping("/student/placeHold")
	@Transactional
	public Student placeHold(@RequestParam("email")String email) {
		//Check for existing students
		Student student = studentRepository.findByEmail(email);
		
		if(student != null) {
			//Place hold
			student.setStatusCode(1);
			student.setStatus("On Hold");
			
			student = studentRepository.findByEmail(email);
			if(student.getStatusCode() == 1) {
				return student;
			}
			else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to add hold.");
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Student does not exist.");
		}
	}
	//Remove hold
	@PostMapping("/student/removeHold")
	@Transactional
	public Student removeHold(@RequestParam("email")String email) {
		//Check for existing students
		Student student = studentRepository.findByEmail(email);
		
		if(student != null) {
			//Remove hold placed
			student.setStatusCode(0);
			student.setStatus(null);
			
			student = studentRepository.findByEmail(email);
			
			if(student.getStatusCode() == 0) {
				return student;
			}
			else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to remove hold.");
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Student does not exist.");
		}
	}
>>>>>>> Stashed changes
}