package com.green.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimpleRestController {
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
//	@PostMapping("/send")
//	//@ResponseBody
//	public Person test(@RequestBody Person p) {
//		System.out.println("p : " + p);
//		p.setName("abc");
//		p.setAge(p.getAge() + 10);
//		
//		return p;
//	}
	
	
}
