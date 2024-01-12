package com.girludev.demoparkapi.service;

import com.girludev.demoparkapi.entity.User;
import com.girludev.demoparkapi.exception.PasswordInvalidException;
import com.girludev.demoparkapi.exception.UserIdEntityNotFoundException;
import com.girludev.demoparkapi.exception.UsernameUniqueViolationException;
import com.girludev.demoparkapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	
	@Transactional // anotação faz processo de abrir e fechar para SAVE
	public User save(User user) {

		try{
			return userRepository.save(user);//esse método save já está inclusa no JPA
		}catch (DataIntegrityViolationException dataIntegrityViolationException){
			throw new UsernameUniqueViolationException(String.format("already registered username {%s}", user.getUsername()));

		}


	}
	
	@Transactional
	public User searchById(Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new UserIdEntityNotFoundException("User not found")
		);
	}
	
	@Transactional
	public List<User> finAllUsers() {
		return userRepository.findAll();
	}
	
	@Transactional
	public User PasswordEdit(Long id, String password, String newPassword, String confirmPassword) {
		if(!newPassword.equals(confirmPassword)){
			throw new PasswordInvalidException("The new password is not the same as confirmation");
		}
		User passwordTheUser = searchById(id);
		if(!passwordTheUser.getPassword().equals(password)){
			throw new PasswordInvalidException("The password does not match the one created previously");
		}
		passwordTheUser.setPassword(newPassword);
		return passwordTheUser;
	}
}