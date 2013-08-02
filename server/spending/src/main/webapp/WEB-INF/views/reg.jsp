<%@page import="ru.terra.spending.web.security.SessionHelper"%>
<%@page import="ru.terra.spending.constants.URLConstants"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<!-- B.2 MAIN CONTENT -->
<div class="main-content">
	<script src="resources/js/login.js"></script>
	<!-- Pagetitle -->
	<h1 class="pagetitle">Регистрация</h1>
	<div>
		<!-- Content unit - One column -->
		<div class="column1-unit">

			<label for="user">Логин</label> <input type="text" name="user"
				id="user" /> <br /> <label for="pass">Пароль</label> <input
				type="password" name="pass" id="pass" /> <br /> <input
				type="button" value="Войти"
				onClick="register($('#user').val(), $('#pass').val());" />
		</div>
		<hr class="clear-contentunit" />
	</div>
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>
