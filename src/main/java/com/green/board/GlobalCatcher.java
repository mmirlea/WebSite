package com.green.board;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//모든 컨트롤러에서 발생하는 예외를 여기서 처리함
//@ControllerAdvice
public class GlobalCatcher {
	//예외 처리 메서드
	@ExceptionHandler(Exception.class)
	public String catcher(Exception ex, Model m) {
		m.addAttribute("ex", ex);
		//에러를 처리할 페이지
		return "error";
	}
	
	//예외 처리 메서드
	@ExceptionHandler({NullPointerException.class, ArithmeticException.class})
	public String catcher2(Exception ex, Model m) {
		m.addAttribute("ex", ex);
		//에러를 처리할 페이지
		return "error";
	}
}
