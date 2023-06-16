package com.green.user.service;

import com.green.user.domain.UserDto;

public interface UserService {

	int register(UserDto dto) throws Exception;

	int idOverlap(UserDto dto) throws Exception;
	
	UserDto login(UserDto dto) throws Exception;
}