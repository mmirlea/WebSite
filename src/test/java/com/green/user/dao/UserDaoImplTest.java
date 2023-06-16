package com.green.user.dao;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.green.user.domain.UserDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class UserDaoImplTest {

	@Autowired
	private UserDao dao;
	
	@Test
	public void test() throws Exception{
		UserDto dto = new UserDto("green1","1234","김그린","hello@naver.com",new Date("2020/1/1"),"facebook");
		assertTrue(dao.register(dto)==1);
		assertTrue(dto.getName()=="김그린");
		
		dto = new UserDto("green2","1234","이자바","wordle@naver.com",new Date("2020/3/3"),"instagram");
		assertTrue(dao.register(dto)==1);
		assertTrue(dto.getName()=="이자바");
	}

}
