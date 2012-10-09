<%@page import="ru.terra.spending.web.security.SessionHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</P>
	<%
		if (SessionHelper.getCurrentIUser() == null)
		{
	%>
	<a href="/spending/login">Вход</a>
	<a href="/spending/registration">Вход</a>
	<%
		}
		else
		{
	%>
	<p>вход успешен</p>
	<%
		}
	%>
</body>
</html>