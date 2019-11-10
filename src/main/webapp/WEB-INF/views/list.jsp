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
	<hr width="500" color="tomato"/>
	<font size="2">
	<table border="1" cellpadding="1" cellspacing="0" width="500">
	<tr>
		<td align="center">번  호</td>
		<td align="center">제  목</td>
		<td align="center">작성자</td>
		<td align="center">날  짜</td>
		<td align="center">조회수</td>
	</tr>
	<!-- 게시글 목록 가져오기 -->
	<c:forEach items="${list}" var="vo">
	<tr>
		<td align="center">${vo.bNO_BBS}</td>
		<td>
			<c:forEach begin="1" end="${vo.bINDENT}">　</c:forEach>
			<a href="contentView?bNO_BBS=${vo.bNO_BBS}">${vo.bSUBJECT}</a>
		</td>
		<td>${vo.bNM_BBS}</td>
		<td align="center">${vo.bDT_BBS}</td>
		<td align="center">${vo.bHIT}</td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="5" align="right"><a href="writeForm">글쓰기</a></td>
	</tr>
	</table>
	</font>
</div>

</body>
</html>