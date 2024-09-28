package com.ChemQuest.ChemQuest.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ChemQuest.ChemQuest.DTO.StudentDTO;
import com.ChemQuest.ChemQuest.Exceptions.UserAlreadyExistsException;
import com.ChemQuest.ChemQuest.security.JwtUtil;
import com.ChemQuest.ChemQuest.service.StudentServiceImpl;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.LoggerFactory;

@Controller
public class AccountOperations {
	
	@Autowired
	StudentServiceImpl studentServiceImpl;
	
	@Autowired
	JwtUtil jwtUtil;
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(AccountOperations.class);

	@GetMapping("/")
	public String landingPage() {
		return "landingPage";
	}
	
	@GetMapping("/signin")
	public String getSignIn(@RequestParam(required = false) String error, Model model,HttpServletRequest request) {
		if (error != null) {
			model.addAttribute("error", "Incorrect username or password!");
		}
		return checkForCookie(request);
	}
	
	@GetMapping("/signup")
	public String getSignUp(Model model) {
		model.addAttribute("StudentDTO", new StudentDTO());
		return "signup";
	}
	
	@PostMapping("/addUser")
	public String addUser(@Valid @ModelAttribute("StudentDTO") StudentDTO studentDTO, BindingResult results,
							@RequestParam String confirmPassword, RedirectAttributes redirectAtt, Model model) {
		if (results.hasErrors()) {
			return "signup";
		}
		if (!studentDTO.getPassword().equals(confirmPassword)) {
			model.addAttribute("error", "Passwords do not match!");
			return "signup";
		}
		
		try {
			
			studentServiceImpl.saveStudent(studentDTO);
			redirectAtt.addFlashAttribute("success", "Successfully created account, please sign in");
			return "redirect:/signin";
			
		} catch(UserAlreadyExistsException e) {
			model.addAttribute("error", e.getMessage());
			return "signup";
		}
	}
	
	public String checkForCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0) {
			logger.info("No cookies, please sign in!");
			return "signin";
		}
		for (Cookie cookie : cookies) {
			if ("JWT".equals(cookie.getName())) {
				String token = cookie.getValue();
				
				// Check if token found is valid
				if (jwtUtil.validateToken(token)) {
					return "redirect:/student/dashboard";
				}
				break;
			}
		}
		return "signin";
	}
	
}
