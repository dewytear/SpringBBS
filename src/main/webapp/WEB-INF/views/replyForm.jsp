<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 페이지</title>
</head>
<body>

<div align="center">

	<h2>답글 페이지</h2>
	<hr width="500" color="tomato"/>

	<table border="1" cellpadding="1" cellspacing="0" width="500">
	<form action="replyOk" method="post">
		<tr>
			<td align="center">번&nbsp;&nbsp;&nbsp;&nbsp;호</td>
			<td>${replyForm.bNO_BBS}</td>
		</tr>
		<tr>
			<td align="center">조회수</td>
			<td>${replyForm.bHIT}</td>
		</tr>
		<tr>
			<td align="center">작성자</td>
			<td><input type="text" name="bNM_BBS" size="20"/></td>
		</tr>
		<tr>
			<td align="center">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
			<td><input type="text" name="bSUBJECT" value=" [RE:] ${replyForm.bSUBJECT}" size="66" readonly/></td>
		</tr>
		<tr>
			<td align="center">답&nbsp;&nbsp;&nbsp;&nbsp;변</td>
			<td><textarea cols="50" rows="16" name="bCONTENT"><pre>${replyForm.bCONTENT}</pre></textarea><p></td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<input type="submit" value="답글쓰기"/>&nbsp;&nbsp;&nbsp;
				<a href="list">목록보기</a>
			</td>
		</tr>
	</form>
	</table>

</div>

</body>
</html>