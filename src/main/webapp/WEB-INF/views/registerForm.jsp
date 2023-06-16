<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.net.URLDecoder" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style>
	* {
		box-sizing: border-box;
	}
	form {
		width: 600px;
		/* height:600px; */
		display: flex;
		flex-direction: column;
		align-items: center;
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		border: 1px solid rgb(89, 117, 196);
		border-radius: 10px;
	}
	p {
		display: flex;
		margin: 5px;
	}
	.textBox {
		width: 300px;
		height: 40px;
		border: 1px solid rgb(89, 117, 196);
		/* border-radius:5px; */
		padding: 10px;
		margin-bottom: 10px;
	}
	label {
		width: 100px;
		height: 30px;
		margin: 8px 0;
	}
	button {
		background-color: rgb(89, 117, 196);
		color: white;
		width: 300px;
		height: 50px;
		font-size: 17px;
		border: none;
		border-radius: 5px;
		margin: 20px 0 30px 0;
	}
	.title {
		font-size: 50px;
		margin: 40px 0 30px 0;
	}
	.msg {
		height: 30px;
		text-align: center;
		font-size: 16px;
		color: red;
		margin-bottom: 20px;
	}
	.sns-chk {
		margin-top: 5px;
	}
	.idOverlap{
		width: 80px;
		margin: 0;
		height: 40px;
		font-size: 12px;
	}
	#id{
		width: 210px;
		margin-right: 10px
	}
</style>
<title>Register</title>
</head>
<body>
	<form action="<c:url value="/register/save"/>" method="post" onsubmit="return formCheck(this)">
		<div class="title">회원가입</div>
		<div id="msg" class="msg">${URLDecoder.decode(param.msg,"utf-8")}</div>
		<p>
			<label for="">아이디</label> <input class="textBox" type="text" id="id"
				name="id" onblur="checkId()" placeholder="5~12자리의 영소문자와 숫자, -, _ 조합">
			<button type="button" id="idOverlap" class="idOverlap" value="N" onclick="fn_idOverlap()">중복확인</button>
		</p>
		<p>
			<label for="">비밀번호</label> <input class="textBox" type="text" id="pwd"
				name="pwd" onblur="checkPwd()" placeholder="8~12자리의 영대소문자와 숫자 조합">
		</p>
		<p>
			<label for="">이름</label> <input class="textBox" type="text" id="name"
				name="name" onblur="checkName()" placeholder="김그린">
		</p>
		<p>
			<label for="">이메일</label> <input class="textBox" type="text" id="email"
				name="email" onblur="checkEmail()" placeholder="example@naver.com">
		</p>
		<p>
			<label for="">생일</label> <input class="textBox" type="text" id="birth"
				name="birth" onblur="checkBirth()" placeholder="2020/12/31">
		</p>
		
		<div class="sns-chk">
			<label><input type="checkbox" name="sns" value="facebook" />페이스북</label>
			<label><input type="checkbox" name="sns" value="kakaotalk" />카카오톡</label>
			<label><input type="checkbox" name="sns" value="instagram" />인스타그램</label>
		</div>
		<button>가입하기</button>
	</form>
	<script>
		//공백 확인
		function formCheck(frm) {
			var msg = '';

			if (frm.id.value == "") {
				setMessage('id를 입력하세요.', frm.id);
				return false;
			}
			
			if (frm.pwd.value == "") {
				setMessage('비밀번호를 입력하세요.', frm.pwd);
				return false;
			}
			
			if (frm.name.value == "") {
				setMessage('이름을 입력하세요.', frm.name);
				return false;
			}
			
			if (frm.email.value == "") {
				setMessage('이메일을 입력하세요.', frm.email);
				return false;
			}
			
			if (frm.birth.value == "") {
				setMessage('생일을 입력하세요.', frm.birth);
				return false;
			}
			
			if(frm.idOverlap.value == "N"){
				setMessage('id 중복확인 버튼을 눌러주세요.', frm.id);
				return false;
			}
			
			return true;
		}
		
		//아이디 중복
		function fn_idOverlap(){
			$.ajax({
				url : "/board/register/idOverlap",
				type : "post", 
				dataType : "json",
				data : {"id": $("#id").val()},
				success : function(data){
					if($("#id").val() == null || $("#id").val() == "")
						setMessage("id를 입력하세요.", $("#id"))
					else if (data == 0) {
						$("#idOverlap").attr("value", "Y")
						setMessage("사용 가능한 아이디입니다.", $("#pwd"))
					}
					else if (data == 1)
						setMessage("중복된 아이디입니다.", $("#id"))
				}
			})
		}
		
		//형식 검사 (정규표현식)
		let id= document.getElementById("id");
		let pwd = document.getElementById("pwd");
		let name = document.getElementById("name");
		let email = document.getElementById("email");
		let birth = document.getElementById("birth");
		
		function checkId(){
			//5~12자리의 영소문자와 숫자, -, _ 조합
			let pattern = /^[a-z0-9-_]{5,12}$/;
			
			if(!pattern.test(id.value)){
				setMessage("5~12자의 영문 소문자, 숫자, 특수문자(-,_)만 사용가능합니다.", id);
				return false;
			}
			document.getElementById("msg").innerHTML=""
			return true;
		}
		
		function checkPwd(){
			//영문자, 특수문자, 숫자 모두를 포함하여 8~12글자로 작성
			let pattern = /^(?=.*[a-zA-Z])(?=.*[~!@#$%^&*+-/=_])(?=.*[0-9]).{8,12}$/;
			
			if(!pattern.test(pwd.value)){
				setMessage("영문자, 특수문자, 숫자 모두를 포함하여 8~12글자로 작성하세요.", pwd);
				return false;
			}
			document.getElementById("msg").innerHTML=""
			return true;
		}
		
		function checkName(){
			//한글, 영어 2~10글자
			let pattern = /^[가-힣a-zA-Z]{2,10}$/;
			
			if(!pattern.test(name.value)){
				setMessage("2~10글자의 한글, 영어만 가능합니다.", name);
				return false;
			}
			document.getElementById("msg").innerHTML=""
			return true;
		}
		
		function checkEmail(){
			//한글, 영어 2~10글자
			let pattern = /^[a-zA-Z0-9]([-_.]*?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_]?[a-zA-Z0-9])*\.[a-zA-Z.]{2,10}$/;
			
			if(!pattern.test(email.value)){
				setMessage("이메일 형식이 아닙니다.", email);
				return false;
			}
			document.getElementById("msg").innerHTML=""
			return true;
		}
		
		function checkBirth(){
			//yyyy/mm/dd 형식
			let pattern = /^[0-9]{4}\/(0?[1-9]|1[0-2])\/(0?[1-9]|[12][0-9]|[3][01])$/;
			
			if(!pattern.test(birth.value)){
				setMessage("날짜 형식이 아닙니다.(예: 2020/01/01)", birth);
				return false;
			}
			
			document.getElementById("msg").innerHTML=""
			return true;
		}
		
		//알림메세지
		function setMessage(msg, element) {
			document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${'${msg}'}</i>`;

			if (element) {
				element.select();
			}
		}
	</script>
</body>
</html>


       