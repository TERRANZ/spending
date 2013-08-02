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
	<h1 class="pagetitle">Вход</h1>
	<div>
		<!-- Content unit - One column -->
		<div class="column1-unit">
					<form action="/spending/do.login" method="post">
			<label for="j_username">Логин</label> <input type="text"
				name="j_username" id="j_username" /> <br /> <label
				for="j_password">Пароль</label> <input type="password"
				name="j_password" id="j_password" /> <br /> <input type='checkbox'
				name='_spring_security_remember_me' /> Remember me on this
			computer. <br /> <input type="submit" value="Войти" />
		</form>
		</div>
		<hr class="clear-contentunit" />
	</div>
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>
