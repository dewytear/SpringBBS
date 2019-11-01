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
	<hr width="400" color="tomato"/>

	<table border="1" cellpadding="1" cellspacing="0" width="500">
	<form action="" method="post">
		<tr>
			<td>번호</td>
			<td>${contentView.bNO_BBS}</td>
		</tr>
		<tr>
			<td>조회수</td>
			<td>${contentView.bHIT}</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td><input type="text" name="bNM_BBS" value="${contentView.bNM_BBS}"/></td>
		</tr>
		<tr>
			<td>제목</td>
			<td><input type="text" name="bSUBJECT" value="${contentView.bSUBJECT}"/></td>
		</tr>
		<tr>
			<td>내용</td>
			<td><textarea rows="8" name="bCONTENT">${contentView.bCONTENT}</textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="수정하기"/>
				&nbsp;&nbsp;&nbsp;<a href="list">목록보기</a>
				&nbsp;&nbsp;&nbsp;<a href="#">삭제</a>
				&nbsp;&nbsp;&nbsp;<a href="#">답변</a>
			</td>
		</tr>
	</form>
	</table>

</div>

</body>
</html>