package com.ChemQuest.ChemQuest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ChemQuest.ChemQuest.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByUsername(String username);
}
