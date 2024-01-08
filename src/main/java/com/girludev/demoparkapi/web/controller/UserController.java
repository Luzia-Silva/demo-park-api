package com.girludev.demoparkapi.web.controller;

import com.girludev.demoparkapi.entity.User;
import com.girludev.demoparkapi.service.UserService;
import jakarta.transaction.Transactional;
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
	public ResponseEntity<List<User>> getAll(){
		List<User> users =  userService.finAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user){
		User userSave = userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id){
		User userById = userService.searchById(id);
		return ResponseEntity.ok(userById);
	}
	@PatchMapping("/{id}")
	public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user){
		User passwordTheUser = userService.PasswordEdit(id, user.getPassword());
		return ResponseEntity.ok(passwordTheUser);
	}
	
}

