package com.girludev.demoparkapi.web.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDTO {
	private String password;
	private String newPassword;
	private String confirmPassword;
}
