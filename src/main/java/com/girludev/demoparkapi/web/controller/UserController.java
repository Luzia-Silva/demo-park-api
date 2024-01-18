package com.girludev.demoparkapi.web.controller;

import com.girludev.demoparkapi.entity.User;
import com.girludev.demoparkapi.service.UserService;
import com.girludev.demoparkapi.web.dto.user.UserCreateDTO;
import com.girludev.demoparkapi.web.dto.user.UserPasswordDTO;
import com.girludev.demoparkapi.web.dto.user.UserResponseDTO;
import com.girludev.demoparkapi.web.dto.mapper.UserMapper;
import com.girludev.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Operation about users")
@RequiredArgsConstructor
@RestController // Entende essa Class é um Bin para request do tipo REST
@RequestMapping("api/v1/users") // path de acesso aos recursos
public class UserController {
	private final UserService userService;

	@Operation(summary = "Search users", description="Resource search users", responses = {
			@ApiResponse(responseCode = "200",
					description = "Resource to search users.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
	})
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAll(){
		List<User> users =  userService.finAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListResponse(users));
	}
	
	@Operation(summary = "Create new user", description="Resource create new user", responses = {
			@ApiResponse(responseCode = "201",
							description = "Resource to successfully create user.",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
			@ApiResponse(responseCode = "409",
					description = "Email already exists in the database.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "422",
					description = "Resource not processed due to invalid data entry.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
	})
	@PostMapping
	public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO createDTO){
		User userSave = userService.save(UserMapper.toUserCreate(createDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(userSave));
	}
	@Operation(summary = "Search user by id", description="Resource search user by id", responses = {
			@ApiResponse(responseCode = "200",
					description = "Resource to search user.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
			@ApiResponse(responseCode = "404",
					description = "Id already exists in the database.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
	})
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id){
		User userById = userService.searchById(id);
		return ResponseEntity.ok(UserMapper.toUserResponse(userById));
	}
	@Operation(summary = "Upgrade password user", description="Resource upgrade password user", responses = {
			@ApiResponse(responseCode = "200",
					description = "Resource to new password user.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserPasswordDTO.class))),
			@ApiResponse(responseCode = "404",
					description = "Id already exists in the database.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "422",
					description = "Passwords don't match.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
	})
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UserPasswordDTO passwordDTO){
		userService.passwordEdit(id, passwordDTO.getPassword(), passwordDTO.getNewPassword(), passwordDTO.getConfirmPassword());
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Delete user", description="Resource delete user", responses = {
			@ApiResponse(responseCode = "200",
					description = "Resource to delete user.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
			@ApiResponse(responseCode = "404",
					description = "Id already exists in the database.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		userService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
}

