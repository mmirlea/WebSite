<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>green</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
</head>
<body>

<%@include file="./header.jsp" %>

<script>
	//삭제 성공 시 메세지 가져오기
	//model의 내용을 get방식으로 전달 받으면 모두 파라미터로 전달됨
	let msg="${msg}";
	if(msg == "DEL_OK") alert("게시글이 삭제되었습니다.");
	if(msg == "DEL_ERR") alert("게시글 삭제에 실패하였습니다.");
    if(msg=="LIST_ERR")  alert("게시물 목록을 가져오는데 실패했습니다. 다시 시도해 주세요.");
	if(msg == "WRT_OK") alert("성공적으로 등록되었습니다.");
	if (msg == "MOD_OK") alert("게시글 수정을 성공하였습니다.")
</script>

<div style="text-align:center; width: 700px; margin: 0 auto;">
    
    <!-- 검색창 추가 -->
    <div>
	    <form action="<c:url value="/board/list"/>" class="search-form" method="get">
	        <select class="search-option" name="option">
	          <option value="A" ${option=='A' ? "selected" : ""}>제목+내용</option>
	          <option value="T" ${option=='T' ? "selected" : ""}>제목만</option>
	          <option value="W" ${option=='W' ? "selected" : ""}>작성자</option>
	        </select>
	
			<input type="text" name="keyword" class="search-input" type="text" value="${param.keyword}" placeholder="검색어를 입력해주세요">
	         <input type="submit" class="search-button" value="검색">
	    </form>
    </div>
    
	<button type="button" id="writeBtn" onclick="location.href='<c:url value="/board/write"/>'"> 글쓰기</button>
	
	<table border="1" width="100%">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>이름</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		<c:forEach var="boardDto" items="${list}">
			<tr>
				<td>${boardDto.bno}</td>
				<td><a href="<c:url value='/board/read${ph.sc.getQueryString()}&bno=${boardDto.bno}'/>"><c:out value='${boardDto.title}'/></a></td>
				<td>${boardDto.writer}</td>
				<!-- 오늘 쓰면 시간만 표시 / 다른 날은 날짜까지 표시 -->
				<c:choose>
		            <c:when test="${boardDto.reg_date.time >= startOfToday}">
		              <td class="regdate"><fmt:formatDate value="${boardDto.reg_date}" pattern="HH:mm" type="time"/></td>
		            </c:when>
		            <c:otherwise>
		              <td class="regdate"><fmt:formatDate value="${boardDto.reg_date}" pattern="yyyy-MM-dd" type="date"/></td>
		            </c:otherwise>
		          </c:choose>
				<td>${boardDto.view_cnt}</td>
			</tr>
		</c:forEach>
	</table>
	<div>
		<c:if test="${ph.getTotalCnt() == null || ph.getTotalCnt() == 0}">
			<div>게시글이 없습니다.</div>
		</c:if>
	</div>
	
	<div>
        	<c:if test="${ph.showPrev}">
        		<a href="<c:url value='/board/list${ph.sc.getQueryString(ph.beginPage-1)}'/>"> &lt;</a>   
        	</c:if>
        	
        	<c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
        		<a href="<c:url value='/board/list${ph.sc.getQueryString(i)}'/>"> ${i}</a>
        	</c:forEach>
        	
        	
        	<c:if test="${ph.showNext}">
        		<a href="<c:url value='/board/list${ph.sc.getQueryString(ph.endPage+1)}'/>"> &gt;</a>   
        	</c:if>
        
        </div>
</div>
</body>
</html>