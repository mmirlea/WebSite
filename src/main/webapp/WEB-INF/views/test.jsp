<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
	<h2>commentController</h2>
	
	comment : <input type="text" name="comments"> <br>
	
	<button id="sendBtn" type="button">SEND</button>
	<button id="modBtn" type="button">수정확인</button>
	
	<!-- 댓글 표시 -->
	<div id="commentsList"></div>
	
	<div id="replyForm" style="display:none">
		<input type="text" name="replyComment">
		<button type="button" id="wrtRepBtn">등록</button>
	</div>
	
	<script>
		let bno=2476;
		//댓글 리스트
		let showList = function(bno){
			$.ajax({
                type:'GET',       // 요청 메서드
                url: '/board/comments?bno=' + bno,  // 요청 URI
                //dataType : 'json', // 전송받을 데이터의 타입. 생략하면 json타입
                //data : JSON.stringify(person), json타입은 알아서해줌 // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                success : function(result){		//success : 콜백함수
                    $("#commentsList").html(toHtml(result));
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
		}
		
		//댓글 작성
		$(document).ready(function(){
			//목록 보이기
			showList(bno);
			
			$("#sendBtn").click (function() {
				
				let comments = $("input[name=comments]").val()
				
				if(comments.trim() == ''){
					alert("댓글을 입력하세요!")
					$("input[name=comments]").focus();
					return;
				}
				
				$.ajax({
	                type:'POST',       // 요청 메서드
	                url: '/board/comments?bno=' + bno,  // 요청 URI
	                headers : {"content-type" : "application/json"}, //요청헤더
	                data : JSON.stringify({bno:bno, comments:comments}), 
	                success : function(result){		
	                	alert(result);
	                	showList(bno);
	                },
	                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
	            }); // $.ajax()
			})
			
			//답글 달기
			$("#commentsList").on("click", ".replyBtn", function() {
				//appendTo : 선택한 요소의 뒤에 붙여줌
				$("#replyForm").appendTo($(this).parent());
				//답글달기 클릭하면 보이기
				$("#replyForm").css("display", "block");
			})
			
			$("#wrtRepBtn").click (function() {
				
				let comments = $("input[name=replyComment]").val()
				
				let pcno = $("#replyForm").parent().attr("data-pcno")
				
				if(comments.trim() == ''){
					alert("댓글을 입력하세요!")
					$("input[name=replyComment]").focus();
					return;
				}
				
				$.ajax({
	                type:'POST',       // 요청 메서드
	                url: '/board/comments?bno=' + bno,  // 요청 URI
	                headers : {"content-type" : "application/json"}, //요청헤더
	                data : JSON.stringify({bno:bno, pcno:pcno, comments:comments}), 
	                success : function(result){		
	                	alert(result);
	                	showList(bno);
	                },
	                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
	            }); // $.ajax()
	            
	            $("replyForm").css("display", "none");
	            $("input[name=replyComment]").val('');
	            $("#replyForm").appendTo("body");
			})
			
			//댓글 삭제
			$("#commentsList").on("click", ".delBtn", function() {
				let cno=$(this).parent().attr("data-cno");
				let bno=$(this).parent().attr("data-bno");
				
				$.ajax({
	                type:'DELETE',       // 요청 메서드
	                url: '/board/comments/' + cno + '?bno=' + bno,  // 요청 URI
	                success : function(result){		
	                	alert("result")
	                	
	                	showList(bno);
	                },
	                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
	            }); // $.ajax()
			})
			
			
			//수정 확인을 클릭하면
			$("#modBtn").click (function() {
				
				let comments = $("input[name=comments]").val()
				
				let cno = $(this).attr("data-cno");
				
				if(comments.trim() == ''){
					alert("댓글을 입력하세요!")
					$("input[name=comments]").focus();
					return;
				}
				
				$.ajax({
	                type:'PATCH',       // 요청 메서드
	                url: '/board/comments/' + cno,  // 요청 URI
	                headers : {"content-type" : "application/json"}, //요청헤더
	                data : JSON.stringify({cno:cno, comments:comments}), 
	                success : function(result){		
	                	alert(result);
	                	showList(bno);
	                },
	                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
	            }); // $.ajax()
			})
			
			//댓글 수정
			$("#commentsList").on("click", ".modBtn", function() {
				let cno=$(this).parent().attr("data-cno");
				
				//comment내용을 input에 넣어주기
				let comments = $("span.comments", $(this).parent()).text();
				
				$("input[name=comments]").val(comments);
				
				//cno 전달
				$("#modBtn").attr("data-cno", cno);
			})
		})
		
		//결과를 화면에 출력하기위한 함수를 생성
		let toHtml = function(comments){
			let tmp="<ul>";
			
			comments.forEach(function(comments){
				tmp += '<li data-cno=' + comments.cno
				tmp += ' data-pcno=' + comments.pcno
				tmp += ' data-bno=' + comments.bno + '>'
				
				if(comments.cno != comments.pcno)
					tmp += 'ㄴ'
				tmp += ' commenter=<span class="commenter">' + comments.commenter + '</span>'
				tmp += ' comments=<span class="comments">' + comments.comments + '</span>'
				tmp += ' up_date=' + comments.up_date
				tmp += ' <button class="delBtn">삭제</button>'
				tmp += ' <button class="modBtn">수정</button>'
				tmp += ' <button class="replyBtn">답글</button>'
				tmp += '</li>'
			})
			
			tmp += "</ul>"
			
			return tmp;
		}
	</script>
</body>
</html>