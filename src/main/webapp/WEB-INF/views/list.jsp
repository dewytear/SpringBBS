<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
</head>
<body>

<div align="center">
	<h2>게시판 리스트</h2>
	<hr width="400" color="tomato"/>
	
	<table border="1" cellpadding="1" cellspacing="0" width="500">
	<tr>
		<td>번호</td>
		<td>제목</td>
		<td>작성자</td>
		<td>날짜</td>
		<td>조회수</td>
	</tr>
	<!-- 게시글 목록 가져오기 -->
	<c:forEach items="${list}" var="vo">
	<tr>
		<td>${vo.bNO_BBS}</td>
		<td><a href="contentView?bNO_BBS=${vo.bNO_BBS}">${vo.bSUBJECT}</a></td>
		<td>${vo.bNM_BBS}</td>
		<td>${vo.bDT_BBS}</td>
		<td>${vo.bHIT}</td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="5" align="center"><a href="writeForm">글쓰기</a></td>
	</tr>
	</table>

</div>

</body>
</html>