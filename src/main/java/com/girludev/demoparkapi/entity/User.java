package com.girludev.demoparkapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "username", nullable = false, unique = true, length = 100)
	private String username;
	@Column(name = "password", nullable = false, length = 200)
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 25)
	private Role  role = Role.ROLE_CLIENTE;
	
	@Column(name = "creation_date")
	private LocalDateTime creation_date;
	@Column(name = "modification_date")
	private LocalDateTime dmodification_date;
	@Column(name = "created_by")
	private String created_by;
	@Column(name = "modified_by")
	private String modified_by;
	
	public enum Role {
		ROLE_ADMIN, ROLE_CLIENTE
	}
	
	@Override
	public boolean equals(Object o) {
		if (this==o) return true;
		if (o==null || getClass()!=o.getClass()) return false;
		User user = (User) o;
		return id.equals(user.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				'}';
	}
}
