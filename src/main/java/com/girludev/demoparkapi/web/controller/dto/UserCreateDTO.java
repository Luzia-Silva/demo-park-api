package com.girludev.demoparkapi.web.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateDTO {
	
	@NotBlank
	@Email
	private String username;
	@NotBlank
	@Size(min = 8, max = 12)
	private String password;
}
