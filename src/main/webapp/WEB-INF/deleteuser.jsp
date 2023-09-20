<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン中のユーザーを削除</title>
<link rel="stylesheet" href="css/style.css">
<script>
  function confirmDeletion() {
    return confirm("本当に退会していいですか？");
  }
</script>
</head>
<body class="body">
	<div class="header">
		<a href="MenuServlet" class="header-link-right">メニューへ戻る</a>
	</div>
	<div class="main-wrapper">
		<div class="container">
			<form action="DeleteUserServlet" method="post" onsubmit="return confirmDeletion();">
				<input type="submit" value="退会する（退会するとユーザー情報が削除されます）"
					class="styled-button" />
			</form>
		</div>
	</div>
	<footer class="footer">
		<p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
	</footer>
</body>
</html>