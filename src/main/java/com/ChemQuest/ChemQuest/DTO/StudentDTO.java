package com.ChemQuest.ChemQuest.DTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

	@Size(min = 10, message = "Username must be atleast 10 characters")
	private String username;
	
	@Size(min = 10, message = "Password must be atleast 10 characters")
	private String password;
}
