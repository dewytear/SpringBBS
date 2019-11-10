<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용 확인</title>
</head>
<body>

<div align="center">

	<h2>글내용 확인</h2>
	<hr width="500" color="tomato"/>

	<font size="2">
	<table border="1" cellpadding="1" cellspacing="0" width="500">
	<form action="modify" method="post">
		<!-- 게시물 번호 또한 전송이 되어야하기에 input설정이 되어야하나,
			수정이 되지않아야하는 값이기때문에 type을 hidden으로 설정해준다. -->
		<input type="hidden" name="bNO_BBS" value="${contentView.bNO_BBS}"/>
		<tr>
			<td align="center">번&nbsp;&nbsp;&nbsp;&nbsp;호</td>
			<td>${contentView.bNO_BBS}</td>
		</tr>
		<tr>
			<td align="center">조회수</td>
			<td>${contentView.bHIT}</td>
		</tr>
		<tr>
			<td align="center">작성자</td>
			<td><input type="text" name="bNM_BBS" value="${contentView.bNM_BBS}" size="20"/></td>
		</tr>
		<tr>
			<td align="center">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
			<td><input type="text" name="bSUBJECT" value="${contentView.bSUBJECT}" size="66"/></td>
		</tr>
		<tr>
			<td align="center">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
			<td><textarea cols="50" rows="8" name="bCONTENT">${contentView.bCONTENT}</textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<a href="list">목록보기</a>
				&nbsp;&nbsp;&nbsp;<input type="submit" value="수정하기"/>
				&nbsp;&nbsp;&nbsp;<a href="delete?bNO_BBS=${contentView.bNO_BBS}">삭제</a>
				&nbsp;&nbsp;&nbsp;<a href="replyForm?bNO_BBS=${contentView.bNO_BBS}">답변</a>
			</td>
		</tr>
	</form>
	</table>
	</font>

</div>

</body>
</html>