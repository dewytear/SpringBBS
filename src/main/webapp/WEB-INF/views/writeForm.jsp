<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기 페이지</title>
</head>
<body>
<div align="center">
	<h2>글쓰기 페이지</h2>
	<hr width="500" color="tomato"/>

	<table border="1" cellpadding="1" cellspacing="0" width="500">
	<form:form commandName="Bvo" action="writeOk" method="post">
		<tr>
			<td>작성자</td>
			<td><form:input path="bNM_BBS" size="20"/></td>
		</tr>
		<tr>
			<td>글제목</td>
			<td><form:input path="bSUBJECT" size="66"/></td>
		</tr>
		<tr>
			<td>내용</td>
			<td><form:textarea path="bCONTENT" cols="50" rows="8"/></td>
		</tr>
		<tr>
			<td align="right" conspan="2"><input type="submit" value="등록"/></td>
		</tr>
	</form:form>
	</table>

</div>
</body>
</html>