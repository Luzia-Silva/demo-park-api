package com.girludev.demoparkapi.repository;

import com.girludev.demoparkapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}