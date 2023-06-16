package com.green.user.dao;

import com.green.user.domain.UserDto;

public interface UserDao {

	int register(UserDto dto) throws Exception;

	int idOverlap(UserDto dto) throws Exception;
	
	UserDto login(UserDto dto) throws Exception;
}