package com.green.user.controller;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.user.domain.UserDto;
import com.green.user.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	UserService service;
	
	@GetMapping("/login")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(UserDto dto, String toURL, boolean rememberId, HttpServletResponse response, 
																	HttpServletRequest request) throws Exception {
		
		//1)id와 pwd를 확인
		if(!loginCheck(dto)) {
			//2)일치하지 않으면 loginForm으로 이동
			String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "utf-8");
			return "redirect:/login/login?msg="+msg;
		}
		
		System.out.println(dto.toString());
		//3)id와 pwd가 일치하면 홈으로 이동
		
		//세션 객체를 얻어옴.
		HttpSession session = request.getSession();
		
		session.setAttribute("id", dto.getId());
		
		if(rememberId) {
			//3-1)쿠키를 생성
			Cookie cookie = new Cookie("id", dto.getId());
			//3-2)응답에 저장
			response.addCookie(cookie);
		} else {
			//4-1)쿠키 삭제
			Cookie cookie = new Cookie("id", dto.getId());
			cookie.setMaxAge(0); //쿠키를 삭제하는 코드
			
			//4-2)응답에 저장
			response.addCookie(cookie);
		}
		//3-3)홈으로 이동
		//로그아웃상태에서 보드에서 다시 로그인하면 홈 말고 보드창으로 바로 가게 함.
		toURL = toURL == null || toURL.equals("") ? "/" : toURL;
		
		return "redirect:" + toURL;
	}
	
	private boolean loginCheck(UserDto dto) {
		try {
			UserDto check = service.login(dto);
			
			if(check == null) 
				throw new Exception("login Err");
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
		
		return true;
		
	}
}
