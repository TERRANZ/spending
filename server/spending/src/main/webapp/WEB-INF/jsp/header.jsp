<%@page import="ru.terra.spending.web.security.SessionHelper"%>
<%@page import="ru.terra.spending.web.security.SessionHelper"%>
<%@page import="ru.terra.spending.constants.URLConstants"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<!--  Version: Multiflex-3 Update-2 / Overview             -->
<!--  Date:    November 29, 2006                           -->
<!--  Author:  G. Wolfgang                                 -->
<!--  License: Fully open source without restrictions.     -->
<!--           Please keep footer credits with a link to   -->
<!--           G. Wolfgang (www.1-2-3-4.info). Thank you!  -->

<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="3600" />
<meta name="revisit-after" content="2 days" />
<meta name="robots" content="index,follow" />
<meta name="publisher" content="Your publisher infos here ..." />
<meta name="copyright" content="Your copyright infos here ..." />
<meta name="author"
	content="Design: G. Wolfgang www.1-2-3-4.info / Author: Your author infos here ..." />
<meta name="distribution" content="global" />
<meta name="description" content="Your page description here ..." />
<meta name="keywords" content="rss" />
<link rel="stylesheet" type="text/css" media="screen,projection,print"
	href="resources/css/layout4_setup.css" />
<link rel="stylesheet" type="text/css" media="screen,projection,print"
	href="resources/css/layout4_text.css" />
<link rel="icon" type="image/x-icon" href="resources/img/favicon.ico" />
<title>TAWEB</title>
</head>

<!-- Global IE fix to avoid layout crash when single word size wider than column width -->
<!--[if IE]><style type="text/css"> body {word-wrap: break-word;}</style><![endif]-->
<%@include file="/WEB-INF/jsp/jsinclude.jsp"%>
<body>
	<!-- Main Page Container -->
	<div class="page-container">

		<!-- For alternative headers START PASTE here -->

		<!-- A. HEADER -->
		<div class="header">

			<!-- A.1 HEADER TOP -->
			<div class="header-top">

				<!-- Sitelogo and sitename -->
				<a class="sitelogo" href="#" title="К началу"></a>
				<div class="sitename">
					<h1>
						<a href="/spending/" title="К началу">Веб интерфейс трат</a>
					</h1>					
				</div>

				<!-- Navigation Level 0 -->
				<div class="nav0"></div>

				<!-- Navigation Level 1 -->
				<div class="nav1">
					<ul>
						<li><a href="/spending/" title="К началу">К началу</a></li>
						<li><a href="/spending/about" title="О проекте">О проекте</a></li>
						<li><a href="/spending/settings" title="Настройки">Настройки</a></li>

					</ul>
				</div>
			</div>

			<!-- A.2 HEADER MIDDLE -->
			<div class="header-middle">

				<!-- Site message -->
				<div class="sitemessage"></div>
			</div>

			<!-- A.3 HEADER BOTTOM -->
			<div class="header-bottom">

				<!-- Navigation Level 2 (Drop-down menus) -->
				<div class="nav2">

					<!-- Navigation item -->
					<ul>
						<li><a href="/spending/">К началу</a></li>
					</ul>
					<%--           <% if (SessionHelper.isUserCurrentAuthorized()){ %> --%>
					<!--           Navigation item -->
					<!--           <ul> -->
					<!--             <li><a href="#">Фиды[if IE 7]><!</a><![endif] -->
					<!--               [if lte IE 6]><table><tr><td><![endif] -->
					<!--                 <ul id="feeds_navigation">                                     -->
					<!--                 </ul> -->
					<!--               [if lte IE 6]></td></tr></table></a><![endif] -->
					<!--             </li> -->
					<!--           </ul>                        -->
					<%--           <%}else{ %> --%>
					<!--           <ul> -->
					<!--             <li><a href="/jbrss/login">Вход</a></li> -->
					<!--           </ul> -->
					<%--           <% } %> --%>
				</div>
			</div>

			<!-- A.4 HEADER BREADCRUMBS -->

			<!-- Breadcrumbs -->
			<div class="header-breadcrumbs">

				<!-- Search form -->
				<div class="searchform">
					<form action="#" method="post" class="form">
						<fieldset>
							<input value="Поиск..." name="field" class="field" /> <input
								type="submit" value="Go" name="button" class="button" />
						</fieldset>
					</form>
				</div>
			</div>
		</div>

		<!-- For alternative headers END PASTE here -->
		<!-- B. MAIN -->
		<div class="main">

			<!-- B.1 MAIN NAVIGATION -->
			<div class="main-navigation">
				<!-- Template infos -->
				<h1>Последние запуски</h1>
				
			</div>