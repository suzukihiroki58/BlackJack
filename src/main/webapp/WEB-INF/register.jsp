<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザー登録</title>
</head>
<body>
<h1>新規ユーザー登録</h1>
<% if (request.getAttribute("errorMessage") != null) { %>
  <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
<% } %>
<form action="RegisterServlet" method="post">
ユーザーネーム：<input type="text" name="userName"><br>
パスワード：<input type="password" name="password"><br>
ニックネーム：<input type="text" name="nickname"><br>
<input type="submit" value="登録">
</form>
<a href="WelcomeServlet">トップページへ戻る</a>

</body>
</html>