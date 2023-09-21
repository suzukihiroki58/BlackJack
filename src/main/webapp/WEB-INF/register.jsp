<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザー登録</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="body">
	<div class="header">
		<a href="WelcomeServlet" class="header-link-right">トップページへ戻る</a>
	</div>
	<div class="main-wrapper">
		<div class="container">
			<% if (request.getAttribute("errorMessage") != null) { %>
			<p class="message"><%= request.getAttribute("errorMessage") %></p>
			<%
			}
			%>
			<form action="RegisterServlet" method="post">
				<div class="form-group">
					<label class="label-text" for="userName">ユーザーネーム：</label> <input
						type="text" id="userName" name="userName" class="username-input">
				</div>
				<div class="form-group">
					<label class="label-text" for="password">パスワード：</label> <input
						type="password" id="password" name="password"
						class="password-input" placeholder="英大文字・小文字・数字・記号含めた10桁以上">
				</div>
				<!-- <div class="form-group">
					<label class="label-text" for="nickname">ニックネーム：</label> <input
						type="text" id="nickname" name="nickname" class="nickname-input">
				</div> -->
				<input type="submit" value="登録">
			</form>
		</div>
	</div>
	<footer class="footer">
		<p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
	</footer>
</body>
</html>
