package com.girludev.demoparkapi.web.controller;

import com.girludev.demoparkapi.entity.User;
import com.girludev.demoparkapi.service.UserService;
import com.girludev.demoparkapi.web.controller.dto.UserCreateDTO;
import com.girludev.demoparkapi.web.controller.dto.UserPasswordDTO;
import com.girludev.demoparkapi.web.controller.dto.UserResponseDTO;
import com.girludev.demoparkapi.web.controller.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // Entende essa Class é um Bin para request do tipo REST
@RequestMapping("api/v1/users") // path de acesso aos recursos
public class UserController {
	private final UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAll(){
		List<User> users =  userService.finAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListResponse(users));
	}
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO createDTO){
		User userSave = userService.save(UserMapper.toUserCreate(createDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(userSave));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id){
		User userById = userService.searchById(id);
		return ResponseEntity.ok(UserMapper.toUserResponse(userById));
	}
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDTO passwordDTO){
		userService.PasswordEdit(id, passwordDTO.getPassword(), passwordDTO.getNewPassword(), passwordDTO.getConfirmPassword());
		return ResponseEntity.noContent().build();
	}
	
}

