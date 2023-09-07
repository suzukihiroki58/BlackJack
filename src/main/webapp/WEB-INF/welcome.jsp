<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>トップページ</title>
<link rel="stylesheet" href="css/style.css">
<script src="js/script.js"></script>
</head>
<body class="body">
	<div class="header"></div>
	<div class="main-wrapper">
		<h1 class="game-title">BlackJack</h1>
		<div class="container">
			<ul class="menu">
				<li><a href="LoginServlet" class="menu-link">ログイン</a></li>
				<li><a href="RegisterServlet" class="menu-link">新規ユーザー登録</a></li>
				<%
				if (request.getParameter("message") != null && request.getParameter("message").equals("UserDeleted")) {
				%>
				<p class="message">ログインユーザーが削除されました。</p>
				<%
				}
				%>
				<%
				if (request.getParameter("message") != null && request.getParameter("message").equals("UserLogout")) {
				%>
				<p class="message">ログアウトしました。</p>
				<%
				p

				}
				%>
			</ul>
		</div>
	</div>
	<footer class="footer">
		<p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
	</footer>
</body>
</html>