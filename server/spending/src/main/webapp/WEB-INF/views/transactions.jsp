<%@page import="ru.terra.spending.web.security.SessionHelper"%>
<%@page import="ru.terra.spending.constants.URLConstants"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="ru.terra.spending.dto.TransactionDTO"%>
<%@page import="ru.terra.spending.db.entity.Transaction"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="ru.terra.spending.engine.TransactionEngine,ru.terra.spending.web.security.SessionHelper"%>

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

