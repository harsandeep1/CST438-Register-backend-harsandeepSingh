<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
package com.cst438;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.cst438.controller.StudentController;
import com.cst438.domain.Enrollment;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.context.ContextConfiguration;

/* 
 *  Using Junit with Mockito for mock objects to test new
 *  student APIs, following the example set by JunitTestSchedule.java.
 */
@ContextConfiguration(classes = { StudentController.class })
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
public class JunitTestStudent {
	
	static final String URL = "http://localhost:8080";
	public static final String TEST_STUDENT_EMAIL = "miles@csumb.edu";
	public static final String TEST_STUDENT_NAME  = "Spiderman";

	@MockBean
	StudentRepository studentRepository;

	@Autowired
	private MockMvc mvc;

	@Test
	public void addStudent()  throws Exception {
		
		MockHttpServletResponse response;
		
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		
	    given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);
		
		// do an http post request with email and name parameters to create student
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student?name=" + TEST_STUDENT_NAME + "&email=" + TEST_STUDENT_EMAIL)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		boolean found = false;	
		
		// verify that returned data contains the added course 
		Student confirmStudent = fromJsonString(response.getContentAsString(), Student.class);
		
		if ((confirmStudent.getEmail().equals(TEST_STUDENT_EMAIL)) &&
			(confirmStudent.getName().equals(TEST_STUDENT_NAME))) {
				found = true;
		}
		
		assertTrue("Student was added.", found);
		
		// verify that repository save method was called.
		verify(studentRepository).save(any(Student.class));
		verify(studentRepository, times(1)).findByEmail(TEST_STUDENT_EMAIL);
		
	}
	
	@Test
	public void addHold()  throws Exception {
		
		MockHttpServletResponse response;
		
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		
	    given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);
		
		// do an http post request with email and name parameters to create student
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student?name=" + TEST_STUDENT_NAME + "&email=" + TEST_STUDENT_EMAIL)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		boolean found = false;	
		
		// verify that returned data contains the added course 
		Student confirmStudent = fromJsonString(response.getContentAsString(), Student.class);
		
		if ((confirmStudent.getEmail().equals(TEST_STUDENT_EMAIL)) &&
			(confirmStudent.getName().equals(TEST_STUDENT_NAME))) {
				found = true;
		}
		
		assertTrue("Student was added.", found);
		
		// verify that repository save method was called.
		verify(studentRepository).save(any(Student.class));
		
		// then do an http post request with email parameter to add hold
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student/addhold?email=" + TEST_STUDENT_EMAIL)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		Boolean holdAdded = false;	
		
		// verify that returned data contains the added course 
		confirmStudent = fromJsonString(response.getContentAsString(), Student.class);
		
		if (confirmStudent.getStatusCode() == 1) {
			holdAdded = true;
		}
		
		assertTrue("Registration hold was added.", holdAdded);
		
		verify(studentRepository, times(3)).findByEmail(TEST_STUDENT_EMAIL);
		
	}
	
	@Test
	public void removeHold()  throws Exception {
		
		MockHttpServletResponse response;
		
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		
	    given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);
		
		// do an http post request with email and name parameters to create student
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student?name=" + TEST_STUDENT_NAME + "&email=" + TEST_STUDENT_EMAIL)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		boolean found = false;	
		
		// verify that returned data contains the added course 
		Student confirmStudent = fromJsonString(response.getContentAsString(), Student.class);
		
		if ((confirmStudent.getEmail().equals(TEST_STUDENT_EMAIL)) &&
			(confirmStudent.getName().equals(TEST_STUDENT_NAME))) {
				found = true;
		}
		
		assertTrue("Student was added.", found);
		
		// verify that repository save method was called.
		verify(studentRepository).save(any(Student.class));
		
		// then do an http post request with email parameter to add hold
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student/removehold?email=" + TEST_STUDENT_EMAIL)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		Boolean holdRemoved = false;	
		
		// verify that returned data contains the added course 
		confirmStudent = fromJsonString(response.getContentAsString(), Student.class);
		
		if (confirmStudent.getStatusCode() == 0) {
			holdRemoved = true;
		}
		
		assertTrue("Registration hold was removed.", holdRemoved);
		
		verify(studentRepository, times(3)).findByEmail(TEST_STUDENT_EMAIL);
		
	}

	private static <T> T  fromJsonString(String str, Class<T> valueType ) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
package com.cst438;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.cst438.controller.StudentController;
import com.cst438.domain.Enrollment;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.context.ContextConfiguration;

/* 
 *  Using Junit with Mockito for mock objects to test new
 *  student APIs, following the example set by JunitTestSchedule.java.
 */
@ContextConfiguration(classes = { StudentController.class })
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
public class JunitTestStudent {
	
	static final String URL = "http://localhost:8080";
	public static final String TEST_STUDENT_EMAIL = "miles@csumb.edu";
	public static final String TEST_STUDENT_NAME  = "Spiderman";

	@MockBean
	StudentRepository studentRepository;

	@Autowired
	private MockMvc mvc;

	@Test
	public void addStudent()  throws Exception {
		
		MockHttpServletResponse response;
		
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		
	    given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);
		
		// do an http post request with email and name parameters to create student
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student?name=" + TEST_STUDENT_NAME + "&email=" + TEST_STUDENT_EMAIL)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		boolean found = false;	
		
		// verify that returned data contains the added course 
		Student confirmStudent = fromJsonString(response.getContentAsString(), Student.class);
		
		if ((confirmStudent.getEmail().equals(TEST_STUDENT_EMAIL)) &&
			(confirmStudent.getName().equals(TEST_STUDENT_NAME))) {
				found = true;
		}
		
		assertTrue("Student was added.", found);
		
		// verify that repository save method was called.
		verify(studentRepository).save(any(Student.class));
		verify(studentRepository, times(1)).findByEmail(TEST_STUDENT_EMAIL);
		
	}
	
	@Test
	public void addHold()  throws Exception {
		
		MockHttpServletResponse response;
		
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		
	    given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);
		
		// do an http post request with email and name parameters to create student
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student?name=" + TEST_STUDENT_NAME + "&email=" + TEST_STUDENT_EMAIL)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		boolean found = false;	
		
		// verify that returned data contains the added course 
		Student confirmStudent = fromJsonString(response.getContentAsString(), Student.class);
		
		if ((confirmStudent.getEmail().equals(TEST_STUDENT_EMAIL)) &&
			(confirmStudent.getName().equals(TEST_STUDENT_NAME))) {
				found = true;
		}
		
		assertTrue("Student was added.", found);
		
		// verify that repository save method was called.
		verify(studentRepository).save(any(Student.class));
		
		// then do an http post request with email parameter to add hold
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student/addhold?email=" + TEST_STUDENT_EMAIL)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		Boolean holdAdded = false;	
		
		// verify that returned data contains the added course 
		confirmStudent = fromJsonString(response.getContentAsString(), Student.class);
		
		if (confirmStudent.getStatusCode() == 1) {
			holdAdded = true;
		}
		
		assertTrue("Registration hold was added.", holdAdded);
		
		verify(studentRepository, times(3)).findByEmail(TEST_STUDENT_EMAIL);
		
	}
	
	@Test
	public void removeHold()  throws Exception {
		
		MockHttpServletResponse response;
		
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		
	    given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);
		
		// do an http post request with email and name parameters to create student
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student?name=" + TEST_STUDENT_NAME + "&email=" + TEST_STUDENT_EMAIL)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		boolean found = false;	
		
		// verify that returned data contains the added course 
		Student confirmStudent = fromJsonString(response.getContentAsString(), Student.class);
		
		if ((confirmStudent.getEmail().equals(TEST_STUDENT_EMAIL)) &&
			(confirmStudent.getName().equals(TEST_STUDENT_NAME))) {
				found = true;
		}
		
		assertTrue("Student was added.", found);
		
		// verify that repository save method was called.
		verify(studentRepository).save(any(Student.class));
		
		// then do an http post request with email parameter to add hold
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student/removehold?email=" + TEST_STUDENT_EMAIL)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		Boolean holdRemoved = false;	
		
		// verify that returned data contains the added course 
		confirmStudent = fromJsonString(response.getContentAsString(), Student.class);
		
		if (confirmStudent.getStatusCode() == 0) {
			holdRemoved = true;
		}
		
		assertTrue("Registration hold was removed.", holdRemoved);
		
		verify(studentRepository, times(3)).findByEmail(TEST_STUDENT_EMAIL);
		
	}

	private static <T> T  fromJsonString(String str, Class<T> valueType ) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}