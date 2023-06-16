<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Green</title>
<link rel="stylesheet" href="<c:url value='/css/menu.css?a'/>">
<!-- jquery 링크 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
	<%@include file="./header.jsp"%>
	
	<script>
		//model의 내용을 get방식으로 전달 받으면 모두 파라미터로 전달됨
		let msg = "${msg}"
		if(msg == "WRT_ERR") alert("게시글 등록에 실패하였습니다.")
		if(msg == "MOD_ERR") alert("게시글 수정에 실패하였습니다.")
	</script>
	
	<div style="text-align:center; width: 700px; margin: 0 auto;">
	
	<h2>게시글 ${mode=="new" ? "작성" : "읽기"}</h2>
	    <form id="form">
	        <table border="1">
	            <tr>
	                <td>번호</td>
	                <td><input type="text" name="bno" value="${boardDto.bno}" readonly="readonly"></td>
	            </tr>
	            <tr>
	                <td>작성자</td>
	                <td><input type="text" name="writer" value="${boardDto.writer}" readonly="readonly"></td>
	            </tr>
	            <tr>
	            	<!-- XSS(Cross-site scripting 공격) : 웹사이트에 스크립트 코드를 주입하는 웹 사이트 공격 방법  -->
	                <td>제목</td>
	                <td><input type="text" name="title" value="<c:out value='${boardDto.title}'/>" ${mode=='new' ? '' : 'readonly="readonly"'}></td>
	            </tr>
	            <tr>
	                <td>내용</td>
	                <td><textarea name="content" ${mode=='new' ? '' : 'readonly="readonly"'} cols="50" rows="20"><c:out value='${boardDto.content}'/></textarea></td>
	            </tr>
	            <tr>
	                <td colspan="2">
	                    <button type="button" id="writeBtn" class="btn">글쓰기</button>
	                    <button type="button" id="modifyBtn" class="btn">수정</button>
	                    <button type="button" id="removeBtn" class="btn">삭제</button>
	                    <button type="button" id="listBtn" class="btn">목록</button>
	                </td>
	            </tr>
	        </table>
	    </form>
	</div>
	
	<c:if test="${mode != 'new'}">
		comment : <input type="text" name="comments"> <br>
	
		<button id="sendBtn" type="button">SEND</button>
		<button id="modBtn" type="button">수정확인</button>
		
		<!-- 댓글 표시 -->
		<div id="commentsList"></div>
		
		<div id="replyForm" style="display:none">
			<input type="text" name="replyComment">
			<button type="button" id="wrtRepBtn">등록</button>
		</div>
	</c:if>
	
	<script>
		let bno=${boardDto.bno};
	    
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
	            
	            $("#replyForm").css("display", "none");
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
			let tmp="<ul id='comments'>";
			
			comments.forEach(function(comments){
				tmp += '<li data-cno=' + comments.cno
				tmp += ' data-pcno=' + comments.pcno
				tmp += ' data-bno=' + comments.bno + '>'
				
				if(comments.cno != comments.pcno)
					tmp += 'ㄴ'
				tmp += ' commenter=<span class="commenter">' + comments.commenter + '</span>'
				tmp += ' comments=<span class="comments">' + comments.comments + '</span>'
				tmp += ' up_date=' + dateToString(comments.up_date)
				tmp += ' <button class="delBtn">삭제</button>'
				tmp += ' <button class="modBtn">수정</button>'
				tmp += ' <button class="replyBtn">답글</button>'
				tmp += '</li>'
			})
			tmp += "</ul>"
			
			return tmp;
		}
		
		let addZero = function(value=1){
			return value > 9 ? value : "0" + value;
		}
		
		let dateToString = function(ms=0){
			let date = new Date(ms);
			
			let yyyy = date.getFullYear();
			let mm = addZero(date.getMonth()+1);
			let dd = addZero(date.getDate());
			
			let HH = addZero(date.getHours());
			let MM = addZero(date.getMinutes());
			let ss = addZero(date.getSeconds());
			
			return yyyy+"."+mm+"."+dd+":"+MM+":"+ss;
		}
	</script>
	
	    <script type="text/javascript">
	    	$(document).ready(function(){
	    		$("#listBtn").on("click", function(){
	    			location.href="<c:url value='/board/list?page=${page}&pageSize=${pageSize}'/>";
	    		})
	    		
	    		$("#removeBtn").on("click", function(){
	    			
	    			if(!confirm("정말로 삭제하시겠습니까?")) return;
	    			
	    			let form = $('#form')
	    			form.attr("action", "<c:url value='/board/remove?page=${page}&pageSize=${pageSize}'/>")
	    			form.attr("method", "post")
	    			
	    			form.submit();
	    		})
	    		
	    		$("#writeBtn").on("click", function(){
	    			let form = $('#form');
	    			form.attr("action", "<c:url value='/board/write'/>")
	    			form.attr("method", "post")
	    			
	    			form.submit();
	    		})
	    		
	    		$("#modifyBtn").on("click", function(){
	    			let form = $('#form')
	    			
	    			let isReadOnly = $("input[name=title]").attr('readonly');
	    			
	    			//1. 읽기 상태라면 수정 상태로 변경
	    			if(isReadOnly == 'readonly'){
	    				$("h2").html("게시글 수정");
	    				
	    				$("input[name=title]").attr('readonly', false);
	    				$("textarea").attr('readonly', false);
	    				$("#modifyBtn").html("수정 완료");
	    				
	    				return;
	    			}
	    			
	    			//2. 수정 상태라면 수정된 내용을 서버로 전송		
	    			form.attr("action", "<c:url value='/board/modify'/>")
	    			form.attr("method", "post")
	    			
	    			form.submit();
	    		})
	    	})
	    </script>
</body>
</html>