<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー管理</title>
</head>
<body>

<form action="DeleteUserServlet" method="post">
    <input type="submit" value="ログイン中のユーザーを削除" />
</form>
<a href="MenuServlet">メニューへ戻る</a>
</body>
</html>