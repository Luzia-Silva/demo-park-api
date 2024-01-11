package com.girludev.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDTO {
	@NotBlank
	@Size(min = 8, max = 12)
	private String password;
	@NotBlank
	@Size(min = 8, max = 12)
	private String newPassword;
	@NotBlank
	@Size(min = 8, max = 12)
	private String confirmPassword;
}
