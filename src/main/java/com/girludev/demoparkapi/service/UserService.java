package com.girludev.demoparkapi.service;

import com.girludev.demoparkapi.entity.User;
import com.girludev.demoparkapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	@Transactional // anotação faz processo de abrir e fechar para SAVE
	public User save(User user) {
		
		//esse método save já está inclusa no JPA
		return  userRepository.save(user);
	}
	
	@Transactional
	public User searchById(Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("User not found")
		);
	}
}
