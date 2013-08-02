<<<<<<< HEAD
<%@page import="ru.terra.spending.web.security.SessionHelper"%>
<%@page import="ru.terra.spending.constants.URLConstants"%>
<%@page import="org.slf4j.LoggerFactory"%>
=======
<%@page import="ru.terra.spending.engine.TypesEngine"%>
<%@page import="java.util.Date"%>
>>>>>>> 7c10acd0d3f55b5bf1406ccc0ee034bf9c643876
<%@page import="ru.terra.spending.dto.TransactionDTO"%>
<%@page import="ru.terra.spending.db.entity.Transaction"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="ru.terra.spending.engine.TransactionEngine,ru.terra.spending.web.security.SessionHelper"%>
<<<<<<< HEAD

<%@include file="/WEB-INF/jsp/header.jsp"%>

<!-- B.2 MAIN CONTENT -->
<div class="main-content">
	<script src="resources/js/login.js"></script>
	<!-- Pagetitle -->
	<h1 class="pagetitle">Траты</h1>
	<div>
		<!-- Content unit - One column -->
		<div class="column1-unit">

			<%
				if (SessionHelper.getCurrentIUser() != null) {
					TransactionEngine te = new TransactionEngine();
					List<TransactionDTO> transactions = te.getTransactions(SessionHelper.getCurrentIUserId());
			%>
			<table>
				<%
					for (TransactionDTO t : transactions) {
				%>
				<tr>
					<td><%=t.id%></td>
					<td><%=new Date(t.date)%></td>
					<td><%=t.type%></td>
					<td><%=t.value%></td>
				</tr>
				<%
					}
				%>
			</table>
			<%
				} else {
			%>
			<h1>Вход не выполнен</h1>
			<%
				}
			%>

		</div>
		<hr class="clear-contentunit" />
	</div>
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>
=======
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Транзакции</title>
</head>
<script type="text/javascript" src="resources/js/a.js"></script>
<body>
	<%
		TypesEngine tse = new TypesEngine();
		if (SessionHelper.getCurrentIUser() != null)
		{
			TransactionEngine te = new TransactionEngine();
			List<TransactionDTO> transactions = te.getTransactions(SessionHelper.getCurrentIUserId());
			if (transactions.size() == 0)
			{
				%><p> нет транзакций</p><%
			}else
			{
	%>
	<table>
	<%
			for (TransactionDTO t : transactions)
			{
				String date = new Date(t.date).toString();
				String type = tse.getType(t.type).getName();
				%>
					<tr>
					<td>
					<%=t.id %>
					</td>
					<td>
					<%=date %>
					</td>
					<td>
					<%=type %>
					</td>
					<td>
					<%=t.value %>
					</td>
					</tr>				
				<%
			}
			}
	%>
	</table>
	<a href="/spending/transactions/add">Добавить</a>
	<%
		}
		else
		{
	%>
	<h1>Вход не выполнен</h1>
	<%
		}
	%>
>>>>>>> 7c10acd0d3f55b5bf1406ccc0ee034bf9c643876

