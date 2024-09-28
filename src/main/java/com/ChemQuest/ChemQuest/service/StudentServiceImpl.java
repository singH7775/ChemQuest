package com.ChemQuest.ChemQuest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ChemQuest.ChemQuest.DTO.StudentDTO;
import com.ChemQuest.ChemQuest.Exceptions.UserAlreadyExistsException;
import com.ChemQuest.ChemQuest.model.Points;
import com.ChemQuest.ChemQuest.model.Student;
import com.ChemQuest.ChemQuest.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

	StudentRepository studentRepository;
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Student getStudent(Long id) {
		Optional<Student> student = studentRepository.findById(id);
		return unwrapStudent(student);
	}

	@Override
	public Student saveStudent(StudentDTO studentDTO) throws UserAlreadyExistsException {
		if (studentRepository.findByUsername(studentDTO.getUsername()) != null) {
			throw new UserAlreadyExistsException();
		}
		Student student = new Student();
		student.setUsername(studentDTO.getUsername());
		student.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
		student.setPoints(new Points());
		return studentRepository.save(student);
	}

	@Override
	public void deleteStudent(Long id) {
		studentRepository.deleteById(id);
	}
	
	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	@Override
	public void handleCorrectAnswer(Student student) {
		student.getPoints().setPoints(student.getPoints().getPoints() + 10);
		studentRepository.save(student);
	}
	
	static Student unwrapStudent(Optional<Student> student) {
		if (student.isPresent()) return student.get();
		return null;
	}
	
	public void setDashboardContent(Authentication auth, Model model, HttpSession session) {
		String username = auth.getName();
		Student student = studentRepository.findByUsername(username);
		model.addAttribute("currentPoints", student.getPoints().getPoints());
		session.setAttribute("currentStudent", student);
	}
	
	public void setDashboardContent(HttpSession session, Model model) {
		Student student = (Student) session.getAttribute("currentStudent");
		model.addAttribute("currentPoints", student.getPoints().getPoints());
		session.setAttribute("currentStudent", student);
	}

}
