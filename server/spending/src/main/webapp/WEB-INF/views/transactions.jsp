<%@page import="ru.terra.spending.dto.TransactionDTO"%>
<%@page import="ru.terra.spending.db.entity.Transaction"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="ru.terra.spending.engine.TransactionEngine,ru.terra.spending.web.security.SessionHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Транзакции</title>
</head>
<script type="text/javascript" src="resources/js/a.js"></script>
<body>
	<%
		if (SessionHelper.getCurrentIUser() != null)
		{
			TransactionEngine te = new TransactionEngine();
			List<TransactionDTO> transactions = te.getTransactions(SessionHelper.getCurrentIUserId());
	%>
	<table>
	<%
			for (TransactionDTO t : transactions)
			{
				%>
					<tr>
					<td>
					<%=t.id %>
					</td>
					<td>
					<%=t.date %>
					</td>
					<td>
					<%=t.type %>
					</td>
					<td>
					<%=t.value %>
					</td>
					</tr>				
				<%
			}
	%>
	</table>
	<%
		}
		else
		{
	%>
	<h1>Вход не выполнен</h1>
	<%
		}
	%>

</body>
</html>