package com.girludev.demoparkapi.web.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDTO {
	private Long id;
	
	private String username;
	
	private String role;
}
