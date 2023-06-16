package com.green.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController2 {
	
//	//예외 처리 메서드
//	@ExceptionHandler(Exception.class)
//	public String catcher(Exception ex, Model m) {
//		m.addAttribute("ex", ex);
//		//에러를 처리할 페이지
//		return "error";
//	}
//	
//	//예외 처리 메서드
//	@ExceptionHandler({NullPointerException.class, ArithmeticException.class})
//	public String catcher2(Exception ex, Model m) {
//		m.addAttribute("ex", ex);
//		//에러를 처리할 페이지
//		return "error";
//	}
//	
	//예외 발생시키기
	@RequestMapping("/ex3")
	public String main() throws Exception{
		throw new Exception("예외 발생");
	}
	
	@RequestMapping("/ex4")
	public String main2() throws Exception{
			//throw new NullPointerException("예외 발생");
			throw new ArithmeticException("예외 발생");
	}
}
