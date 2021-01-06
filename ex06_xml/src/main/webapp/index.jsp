<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 

<a href="/board/list">Main</a>

<script>
	function expireSession(){
		window.location = "board/list";
	}
	setTimeout('expireSession()', <%=request.getSession().getMaxInactiveInterval() %>);
</script>

<%
	pageContext.forward("board/list");
%>

<%
	response.sendRedirect("board/list");
>%

 --%>
<jsp:forward page="board/list"></jsp:forward>
</body>
</html>