<%@page import="ru.terra.spending.web.security.SessionHelper"%>
<%@page import="ru.terra.spending.constants.URLConstants"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<!-- B.2 MAIN CONTENT -->
<div class="main-content">

	<!-- Pagetitle -->
	<h1 class="pagetitle">Главная страница</h1>
	<div>
		<!-- Content unit - One column -->
		<div class="column1-unit">
			<%
				if (SessionHelper.getCurrentIUser() == null) {
			%>
			<a href="/spending/login">Вход</a>
			<p>
				<a href="/spending/registration">регистрация</a>
				<%
					} else {
				%>
			
			<p>вход успешен</p>
			<a href="/spending/transactions">Траты</a>
			<%
				}
			%>
		</div>
		<hr class="clear-contentunit" />
	</div>
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>
