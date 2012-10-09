<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/jsp/jsinclude.jsp"%>
<script src="resources/js/login.js"></script>
</head>
<body>
<div align="center">
		
			<label for="user">Логин</label> 
			<input type="text" name="user" id="user" /> <br />
			<label for="pass">Пароль</label>
			<input type="password" name="pass" id="pass" /> <br />
			<input type="button" value="Войти" 
				onClick="register($('#user').val(), $('#pass').val());"/>
</div>
</body>
</html>