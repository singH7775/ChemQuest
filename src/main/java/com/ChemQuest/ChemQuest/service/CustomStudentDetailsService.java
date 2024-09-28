package com.ChemQuest.ChemQuest.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ChemQuest.ChemQuest.model.Student;
import com.ChemQuest.ChemQuest.repository.StudentRepository;

@Service
public class CustomStudentDetailsService implements UserDetailsService {
	
	@Autowired
	StudentRepository studentRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student = studentRepository.findByUsername(username);
		if (student == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new org.springframework.security.core.userdetails.
					User(student.getUsername(), student.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))); 
	}
	
}
