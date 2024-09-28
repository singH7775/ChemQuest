package com.ChemQuest.ChemQuest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;

import com.ChemQuest.ChemQuest.model.Points;
import com.ChemQuest.ChemQuest.model.Student;
import com.ChemQuest.ChemQuest.repository.StudentRepository;
import com.ChemQuest.ChemQuest.security.JwtUtil;
import com.ChemQuest.ChemQuest.service.ChatModelService;
import com.ChemQuest.ChemQuest.service.StudentService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
@ActiveProfiles("test")
class ChemQuestApplicationTests {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Mock
	private ChatClient chatClient;
	
	@Mock
	private ChatClient.Builder chatClientBuilder;
	
	@Mock
	private ChatResponse chatResponse;
	
	@Mock
	private ChatClient.ChatClientRequestSpec requestSpec;
	
	@Mock
	private ChatClient.CallResponseSpec callResponseSpec;
	
	@Mock
	private Model model;
	
	@Mock
	private HttpSession session;

	private ChatModelService chatModelService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void Test_Student_Save() {
		// Arrange
		Student student = new Student(null, "testUser", passwordEncoder.encode("testPassword"), new Points());
		// Act
		Student savedStudent = studentRepository.save(student);
		// Assert
		assertThat(savedStudent).isNotNull();
		assertThat(savedStudent.getId()).isNotNull();
		assertThat(savedStudent.getUsername()).isEqualTo("testUser");
		assertThat(passwordEncoder.matches("testPassword", savedStudent.getPassword())).isTrue();
		assertThat(savedStudent.getPoints()).isNotNull();
		
		Student findStudentById = studentRepository.findById(savedStudent.getId()).orElse(null);
		assertThat(findStudentById).isNotNull();
		assertThat(savedStudent).isEqualTo(findStudentById);
	}
	
	@Test
	public void Test_Student_Add_Points() {
		// Arrange
		Student student = new Student(null, "testUser", passwordEncoder.encode("testPassword"), new Points());
		
		// Act
		student.getPoints().setPoints(student.getPoints().getPoints() + 10);
		
		// Assert
		assertThat(student).isNotNull();
		assertThat(student.getUsername()).isEqualTo("testUser");
		assertThat(student.getPoints().getPoints()).isEqualTo(10);
	}
	
	@Test
	public void Test_Student_Delete_Student() {
	    // Arrange
	    Student student = new Student(null, "testUser", passwordEncoder.encode("testPassword"), new Points());

	    // Act
	    Student savedStudent = studentRepository.save(student);

	    // Assert
	    assertThat(savedStudent).isNotNull();
	    assertThat(savedStudent.getId()).isNotNull();

	    // Act
	    studentRepository.deleteById(savedStudent.getId());

	    // Assert
	    Optional<Student> deletedStudent = studentRepository.findById(savedStudent.getId());
	    assertThat(deletedStudent).isEmpty();
	}
	
	@Test
	public void Test_StudentService_HandleCorrectAnswer() {
		// Arrange
	    Student student = new Student(null, "testUser", passwordEncoder.encode("testPassword"), new Points());
	    
	    // Act
	    studentService.handleCorrectAnswer(student);
	    
	    // Assert
	    assertThat(student.getPoints().getPoints()).isEqualTo(10);
	}
	
	@Test
	public void Test_GetAllStudents() {
		// Arrange
	    Student student = new Student(null, "testUser", passwordEncoder.encode("testPassword"), new Points());
	    Student student1 = new Student(null, "testUser1", passwordEncoder.encode("testPassword"), new Points());
	    Student student2 = new Student(null, "testUser2", passwordEncoder.encode("testPassword"), new Points());
	    
	    // Act
	    studentRepository.save(student);
	    studentRepository.save(student1);
	    studentRepository.save(student2);
	    
	    List<Student> students = studentRepository.findAll();
	    
	    // Assert
	    assertThat(students).isNotNull();
	    assertThat(students).containsAnyOf(student);
	    assertThat(students).containsAnyOf(student1);
	    assertThat(students).containsAnyOf(student2);
	    assertThat(students).hasSize(3);
	    assertThat(students).containsExactlyInAnyOrder(student, student1, student2);
	    
	}
	
	@Test
	public void testGenerateToken() {
		String username = "testUser";
		String token = jwtUtil.generateToken(username);
		
		assertThat(token).isNotNull();
		assertThat(token.split("\\.")).hasSize(3);
	}
	
	@Test
	public void testExtractUsernameFromToken() {
		String username = "testUser";
		String token = jwtUtil.generateToken(username);
		String extractedUsername = jwtUtil.extractUsername(token);
		
		assertThat(token).isNotNull();
		assertThat(extractedUsername).isNotNull();
		assertThat(extractedUsername).isEqualTo("testUser");
	}
	
	@Test
	public void testTokenNotExpired() {
		String username = "testUser";
		String token = jwtUtil.generateToken(username);
		
		assertThat(token).isNotNull();
		assertThat(jwtUtil.isTokenExpired(token)).isFalse();
	}
	
	@Test
	public void testTokenExpired() throws InterruptedException {
		ReflectionTestUtils.setField(jwtUtil, "EXPIRATION_TIME", 1000);
		
		String username = "testUser";
		String token = jwtUtil.generateToken(username);
		
		Thread.sleep(1500);
		if (Thread.interrupted()) {
			throw new InterruptedException();
		}
		
		assertThat(jwtUtil.isTokenExpired(token)).isTrue();
	}
	
	@Test
	public void testAiChatResponse() {
		MockitoAnnotations.openMocks(this);
		when(chatClientBuilder.build()).thenReturn(chatClient);
		when(chatClient.prompt()).thenReturn(requestSpec);
		when(requestSpec.user(anyString())).thenReturn(requestSpec);
		when(requestSpec.call()).thenReturn(callResponseSpec);
		when(callResponseSpec.content()).thenReturn("Mocked question content");
		
		
		
		chatModelService = new ChatModelService(chatClientBuilder, studentService);
		
		chatModelService.createQuestion(1, session, model);
		verify(chatClient).prompt();
		verify(requestSpec).user(anyString());
		verify(requestSpec).call();
		verify(callResponseSpec).content();
		verify(model, atLeastOnce()).addAttribute(anyString(), any());
		verify(session).setAttribute(eq("question"), anyString());
	}

}
