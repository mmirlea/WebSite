package com.green.user.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.user.domain.UserDto;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	SqlSession session;
	
	String namespace="com.green.user.dao.UserMapper.";
	
	@Override
	public int register(UserDto dto) throws Exception{
		return session.insert(namespace + "register", dto); 
	}

	@Override
	public int idOverlap(UserDto dto) throws Exception {
		return session.selectOne(namespace + "idOverlap", dto);
	}

	@Override
	public UserDto login(UserDto dto) throws Exception {
		return session.selectOne(namespace+"login", dto);
	}
}
