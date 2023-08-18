<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>トップページ</title>
</head>
<body>
<ul>
<li><a href="LoginServlet">ログイン</a></li>
<li><a href="RegisterServlet">新規ユーザー登録</a></li>
<% if(request.getParameter("message") != null && request.getParameter("message").equals("UserDeleted")) { %>
<p>ログインユーザーが削除されました。</p>
<% } %>
<% if(request.getParameter("message") != null && request.getParameter("message").equals("UserLogout")) { %>
<p>ログアウトしました。</p>
<% } %>
</ul>
</body>
</html>