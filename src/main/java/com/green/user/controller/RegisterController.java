package com.green.user.controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.user.domain.UserDto;
import com.green.user.service.UserService;

@Controller
public class RegisterController {
	@Autowired
	UserService service;
	
	@GetMapping("/register/add")
	public String register() {
		
		return "registerForm"; 
	}
	
	//회원가입 처리하기
	//BindingResult : 실행이 정상적으로 되든 안되든 
	//무조건 컨트롤러에게 결과를 넘겨주고, 컨트롤러가 처리하도록 함
	//반드시 binding 하고자하는 객체 바로 뒤에 작성
	@PostMapping("/register/save")
	public String save(UserDto dto, Model m) throws Exception {
		
		//유효성 검사
		if(!isValid(dto)) {
			String msg = URLEncoder.encode("id를 잘못입력하셨습니다.", "UTF-8");
			
			m.addAttribute("msg", msg);
			return "redirect:/register/add";
		}
		
		int result = idOverlap(dto);
		
		if(result != 0) {
			return "redirect:/register/add";
		}
		
		//회원가입 실행
		service.register(dto);
		
		m.addAttribute("msg", "회원가입이 완료되었습니다.");
		m.addAttribute("url", "/board");
		
		return "alertPrint"; 
	}
	
	//아이디 중복 검사
	@ResponseBody
	@PostMapping("/register/idOverlap")
	public int idOverlap(UserDto dto) throws Exception {
		int result = service.idOverlap(dto);
		return result;
	}
	
	//유효성 검사 메서드
	private boolean isValid(UserDto user) {
		return true;
	}
}
