<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<body>
<%
	String id=(String)session.getAttribute("id");

	if(id !=null){
		out.print(id+" 님 환영합니다");
		out.print("<a href=\"getUser.do\">계정정보</a><br><a href=\"logout.do\">로그아웃</a>");
	}else{
		out.print("로그인 후 이용 가능합니다.");
		out.print("<a href=\"login.do\">로그인</a>");
	}
%>
</body>
</html>