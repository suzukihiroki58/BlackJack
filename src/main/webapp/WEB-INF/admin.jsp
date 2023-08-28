<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%@ page import="java.util.List" %>

<%
List<Account> users = (List<Account>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Adminページ</title>
</head>
<body>

<h1>ユーザー一覧</h1>

<table border="1">
    <tr>
        <th>ユーザーID</th>
        <th>ユーザー名</th>
        <th>ニックネーム</th>
        <th>操作</th>
    </tr>
    <% for(Account user : users) { %>
        <tr>
            <td><%= user.getUserId() %></td>
            <td><%= user.getUserName() %></td>
            <td><%= user.getNickname() %></td>
            <td>
                <form action="DeleteAnyUserServlet" method="post">
                    <input type="hidden" name="userId" value="<%= user.getUserId() %>" />
                    <input type="submit" value="削除" />
                </form>
            </td>
        </tr>
    <% } %>
</table>

<a href="MenuServlet">メニューへ戻る</a>
</body>
</html>