package com.green.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.user.dao.UserDao;
import com.green.user.domain.UserDto;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao dao;
	
	@Override
	public int register(UserDto dto) throws Exception{
		return dao.register(dto);
	}

	@Override
	public int idOverlap(UserDto dto) throws Exception {
		return dao.idOverlap(dto);
	}

	@Override
	public UserDto login(UserDto dto) throws Exception {
		return dao.login(dto);
	}
	
}
