<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.UserCredential"%>
<%
UserCredential loginUser = (UserCredential) session.getAttribute("loginUser");
if (loginUser != null) {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="body">
	<div class="header">
		<p class="header-link-left"">
			ログイン中のユーザー：<%=loginUser.getUserName()%>さん
		</p>
		<a href="LogoutServlet" class="header-link-right-menu">ログアウト</a> <a
			href="DeleteUserServlet" class="header-link-right-menu">退会</a>
	</div>
	<div class="main-wrapper">
		<div class="container">

			<ul class="menu">
				<li><a href="BlackjackServlet" class="menu-link">ゲーム開始</a></li>
				<li><a href="RankingServlet" class="menu-link">勝率ランキング</a></li>
				<%
				if ("admin".equals(loginUser.getRole())) {
				%>
				<li><a href="AdminServlet" class="menu-link">ユーザー管理</a></li>
				<%
				}
				%>
			</ul>
			<%
			} else {
			%>
			<p class="message">ログインに失敗しました</p>
			<a href="WelcomeServlet" class="menu-link">トップページへ戻る</a>
			<%
			}
			%>
		</div>
	</div>
	<footer class="footer">
		<p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
	</footer>
</body>
</html>
