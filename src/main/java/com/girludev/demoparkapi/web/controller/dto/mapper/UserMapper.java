package com.girludev.demoparkapi.web.controller.dto.mapper;

import com.girludev.demoparkapi.entity.User;
import com.girludev.demoparkapi.web.controller.dto.UserCreateDTO;
import com.girludev.demoparkapi.web.controller.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
	public static User toUserCreate(UserCreateDTO createDTO){
		return new ModelMapper().map(createDTO, User.class);
	}
	
	public static UserResponseDTO toUserResponse(User user){
		String role = user.getRole().name().substring("ROLE_".length());
		PropertyMap<User, UserResponseDTO> props = new PropertyMap<User, UserResponseDTO>(){
			@Override
			protected void configure(){
				map().setRole(role);
			}
		};
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(props);
		return mapper.map(user, UserResponseDTO.class);
	}
	
	public static List<UserResponseDTO> toListResponse(List<User> users){
		return users.stream().map(user -> toUserResponse(user)).collect(Collectors.toList());
	}
}
