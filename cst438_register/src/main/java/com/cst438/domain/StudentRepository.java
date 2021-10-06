package com.cst438.domain;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface StudentRepository extends CrudRepository <Student, Integer> {
	
	@Query("select s from Student s where s.email=:email")
	public Student findByEmail(@Param("email") String email);


}