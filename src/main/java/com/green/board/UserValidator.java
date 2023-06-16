package com.green.board;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.green.user.domain.UserDto;


public class UserValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
//		return User.class.equals(clazz); // 검증하려는 객체가 User타입인지 확인
		return UserDto.class.isAssignableFrom(clazz); // clazz가 User 또는 그 자손인지 확인
	}

	@Override
	public void validate(Object target, Errors errors) { 
		System.out.println("LocalValidator.validate() is called");

		UserDto user = (UserDto)target;
			
		String id = user.getId();
			
//		if(id==null || "".equals(id.trim())) {
//			errors.rejectValue("id", "required");
//		}
		//rejectIfEmptyOrWhitespace() : 값이 비었거나, 탭이거나, 공백이 들어갈 경우 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",  "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");
			
		if(id==null || id.length() <  5 || id.length() > 12) {
			errors.rejectValue("id", "invalidLength");
		}
	}
}