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
	<!-- 게시물 번호, 그룹, 스텝, 인덴트 또한 전송이 되어야하기에 input설정이 되어야하나,
			화면에 노출이 되지않아야하는 값이기때문에 type을 hidden으로 설정해준다. -->
		<input type="hidden" name="bNO_BBS" value="${replyForm.bNO_BBS}"/>
		<input type="hidden" name="bGROUP" value="${replyForm.bGROUP}"/>
		<input type="hidden" name="bSTEP" value="${replyForm.bSTEP}"/>
		<input type="hidden" name="bINDENT" value="${replyForm.bINDENT}"/>
		
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