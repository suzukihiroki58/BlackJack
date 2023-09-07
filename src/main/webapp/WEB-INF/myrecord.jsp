<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.UserCredential"%>
<% UserCredential loginUser = (UserCredential) session.getAttribute("loginUser"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>個人戦績</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="body">
	<div class="header">
		<p class="header-link-left">
			ログイン中のユーザー：<%= loginUser.getUserName() %>さん
		</p>
		<a href="MenuServlet" class="header-link-right">メニューへ戻る</a>
	</div>
	<div class="main-wrapper">
		<div class="container">
			<table class="styled-table">
				<thead class="styled-text">
					<tr>
						<th colspan="5"><%= loginUser.getUserName() %>さんの戦績</th>
					</tr>
					<tr>
						<th>総プレイ数</th>
						<th>勝ち数</th>
						<th>負け数</th>
						<th>引き分け数</th>
						<th>勝率</th>
					</tr>
				</thead>
				<tbody class="styled-text">
					<tr>
						<td><%= request.getAttribute("totalGames") %></td>
						<td><%= request.getAttribute("wins") %></td>
						<td><%= request.getAttribute("losses") %></td>
						<td><%= request.getAttribute("draws") %></td>
						<td><%=t(request.getAttribute("winRate"%>%</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<footer class="footer">
		<p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
	</footer>
</body>
</html>

