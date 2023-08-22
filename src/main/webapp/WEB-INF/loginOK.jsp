<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Login" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー</title>
</head>
<body>
<%
Login loginUser = (Login) session.getAttribute("loginUser");
if (loginUser != null) {
%>
	<p>ログイン中のユーザー名：<%= loginUser.getUsername() %>さん</p>
	<ul>
		<li><a href="BlackjackServlet">ゲーム開始</a></li>
		<li><a href="MyRecordServlet">自分の戦績</a></li>
		<li><a href="">全体ランキング</a></li>
		<li><a href="AdminServlet">ユーザー管理</a></li>
		<li><a href="LogoutServlet">ログアウト</a></li>
	</ul>
	<% }else{%>
	<p>ログインに失敗しました</p>
	<a href="WelcomeServlet">トップページへ戻る</a>
<% } %>	
</body>
</html>