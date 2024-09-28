package com.ChemQuest.ChemQuest.service;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ChemQuest.ChemQuest.model.Student;

import jakarta.servlet.http.HttpSession;

@Service
public class ChatModelService {
	
	private final ChatClient chatClient;
	private final StudentService studentService;
	
	public ChatModelService(ChatClient.Builder chatClientBuilder, StudentService studentService) {
        this.chatClient = chatClientBuilder.build();
        this.studentService = studentService;
    }

	// ChatGPT creates a question, sets model/session
	public void createQuestion(int subjectNumber, HttpSession session, Model model) {
		String prompt = String.format("Generate me a random question about %s, "
				+ "Provide 3 options labeled A,B, and C. Only provide the question in your response", determineSubject(subjectNumber));
		
		String question = this.chatClient.prompt().user(prompt).call().content();
		session.setAttribute("question", question);
		
		question = question.replaceAll("\n", "<br>"); // Look for /n in the AI response, make those <br> , use th:utext for HTML to read as HTML
		model.addAttribute("question", question);
	}
	
	// Check the answer submitted
	public void checkAnswer(char answer, HttpSession session, Model model) {
		final String check_answer = (String)session.getAttribute("question") + " (My answer is option : " + answer + " (If my answer is correct, "
													+ "only respond with True or else if it is incorrect only respond with False))";
		Student student = (Student) session.getAttribute("currentStudent");
		
		String aiResponse = this.chatClient.prompt().user(check_answer).call().content();
		
		if ("True".equals(aiResponse)) {
			studentService.handleCorrectAnswer(student);
			model.addAttribute("correct", true);
		} else if ("False".equals(aiResponse)) {
			model.addAttribute("correct", false);
		}
	}
	
	// Helper function, determine subject
	public String determineSubject(int subjectNumber) {
		if (subjectNumber == 1) {
			return "Basic Chemistry";
		}
		if (subjectNumber == 2) {
			return "Organic Chemistry";
		}
		if (subjectNumber == 3) {
			return "Biochemistry";
		}
		return null;
	}
	
}
