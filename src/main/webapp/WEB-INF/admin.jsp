<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Account"%>
<%@ page import="java.util.List"%>
<%
List<Account> users = (List<Account>) request.getAttribute("users");
%>
<%@ page import="model.UserCredential"%>
<%
UserCredential loginUser = (UserCredential) session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Adminページ</title>
<link rel="stylesheet" href="css/style.css">
<script>
    function confirmDelete() {
      return confirm('本当に削除していいですか？');
    }
  </script>

</head>
<body class="body">
	<div class="header">
		<p class="header-link-left"">
			ログイン中のユーザー：<%=loginUser.getUserName()%>さん
		</p>
		<a href="MenuServlet" class="header-link-right">メニューへ戻る</a>
	</div>
	<div class="main-wrapper">
		<div class="container-adjustment scrollable-container">
			<table class="styled-table">
				<thead>
					<tr>
						<th colspan="4">ユーザー一覧</th>
					</tr>
					<tr>
						<th>ユーザーID</th>
						<th>ユーザー名</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (Account user : users) {
						if (!loginUser.getUserId().equals(user.getUserId())) {
					%>
					<tr>
						<td><%=user.getUserId()%></td>
						<td><%=user.getUserName()%></td>
						<td>
							<form action="DeleteAnyUserServlet" method="post" onsubmit="return confirmDelete();">
								<input type="hidden" name="userId" value="<%=user.getUserId()%>" />
								<input type="submit" value="削除" class="delete-button" />
							</form>
						</td>
					</tr>
					<%
						}
					}
					%>
				</tbody>
			</table>
		</div>
	</div>
	<footer class="footer">
		<p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
	</footer>
</body>
</html>
