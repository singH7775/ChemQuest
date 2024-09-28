package com.ChemQuest.ChemQuest.service;

import java.util.List;

import com.ChemQuest.ChemQuest.DTO.StudentDTO;
import com.ChemQuest.ChemQuest.model.Student;

public interface StudentService {

	Student getStudent(Long id);
	Student saveStudent(StudentDTO student);
	void deleteStudent(Long id);
	List<Student> getAllStudents();
	void handleCorrectAnswer(Student student);
}
