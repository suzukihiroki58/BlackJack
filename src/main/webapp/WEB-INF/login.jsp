<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="body">
	<div class="header">
		<a href="WelcomeServlet" class="header-link-right">トップページへ戻る</a>
	</div>
	<div class="main-wrapper">
		<div class="container">
		<% if (session.getAttribute("registrationMessage") != null) { %>
		<p class="message"><%= session.getAttribute("registrationMessage") %></p>
		<%
		    session.removeAttribute("registrationMessage");
		}
		%>
			<form action="LoginServlet" method="post">
				<div class="form-group">
					<label class="label-text" for="userName">ユーザーネーム：</label> <input
						type="text" id="userName" name="userName" class="username-input">
				</div>
				<div class="form-group">
					<label class="label-text" for="password">パスワード：</label> <input
						type="password" id="password" name="password"
						class="password-input">
				</div>
				<input type="submit" value="ログイン">
			</form>
		</div>
	</div>
	<footer class="footer">
		<p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
	</footer>
</body>
</html>
