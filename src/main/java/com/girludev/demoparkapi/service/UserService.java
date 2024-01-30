package com.girludev.demoparkapi.service;

import com.girludev.demoparkapi.entity.User;
import com.girludev.demoparkapi.exception.EntityNotFoundException;
import com.girludev.demoparkapi.exception.PasswordInvalidException;
import com.girludev.demoparkapi.exception.UserIdEntityNotFoundException;
import com.girludev.demoparkapi.exception.UsernameUniqueViolationException;
import com.girludev.demoparkapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User save(User user) {
		try{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepository.save(user);
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
	public User passwordEdit(Long id, String password, String newPassword, String confirmPassword) {
		if(!newPassword.equals(confirmPassword)){
			throw new PasswordInvalidException("The new password is not the same as confirmation");
		}
		User user = searchById(id);
		if(!passwordEncoder.matches(password, user.getPassword())){
			throw new PasswordInvalidException("Passwords do not match");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		return user;
	}

	@Transactional
	public User deleteById(Long id) {
		User idUser = searchById(id);
		if(idUser != null) {
			userRepository.deleteById(id);
		}else {
			throw new UserIdEntityNotFoundException("User not found");
		}
        return null;
    }
	@Transactional
	public User searchByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException(String.format("User with 'username' not found", username))
		);
	}

	@Transactional
	public User.Role searchRoleByUsername(String username) {
		return userRepository.findRoleByUsername(username);
	}
}