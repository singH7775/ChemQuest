package com.ChemQuest.ChemQuest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ChemQuest.ChemQuest.service.ChatModelService;
import com.ChemQuest.ChemQuest.service.StudentServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class Dashboard {
	
	private final ChatModelService chatModelService;
	
	@Autowired
	StudentServiceImpl studentServiceImpl;
	
	@Autowired
	public Dashboard(ChatModelService chatModelService) {
	    this.chatModelService = chatModelService;
	}

	
	@GetMapping("/dashboard")
	public String getDashboard(@RequestParam(required = false) Long studentId, Authentication auth, Model model, HttpSession session) {
		// If student is in session, just pull student from session attribute
		if (studentId == null) {
			studentServiceImpl.setDashboardContent(auth, model, session);
		} else {
			studentServiceImpl.setDashboardContent(session, model);
		}
		return "dashboard";
	}
	
	// Subject Controllers
	
	@PostMapping("/basic-chemistry")
	public String basicChemistryQuestion(@RequestParam String subjectNumber, HttpSession session, Model model) {
		chatModelService.createQuestion(Integer.valueOf(subjectNumber), session, model);
		return "questionPage";
	}
	
	@PostMapping("/organic-chemistry")
	public String organicChemistryQuestion(@RequestParam String subjectNumber, HttpSession session, Model model) {
		chatModelService.createQuestion(Integer.valueOf(subjectNumber), session, model);
		return "questionPage";
	}
	
	@PostMapping("/biochemistry")
	public String bioChemistryQuestion(@RequestParam String subjectNumber, HttpSession session, Model model) {
		chatModelService.createQuestion(Integer.valueOf(subjectNumber), session, model);
		return "questionPage";
	}
	
	// Handle the answer
	@GetMapping("/checkAnswer")
	public String checkAnswer(@RequestParam char answer, HttpSession session, Model model) {
		chatModelService.checkAnswer(answer, session, model);
		return "resultsPage";
	}
	
}
