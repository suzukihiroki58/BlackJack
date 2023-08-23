<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.GameRecord, java.util.List, dao.AccountsDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>勝率ランキング</title>
</head>
<body>

<%
AccountsDAO dao = new AccountsDAO();
List<GameRecord> records = dao.getAllUserRecords();
%>

<h1>勝率ランキング(上位5名)</h1>
<table border="10">
    <tr>
    	<th>順位</th>
        <th>ユーザー</th>
        <th>総プレイ数</th>
        <th>勝ち数</th>
        <th>負け数</th>
        <th>引き分け数</th>
        <th>勝率</th>
    </tr>
    <% int rank = 1;
    for (GameRecord record : records) { 
    if (rank <= 5){%>
    <tr>
    	<td><%= rank++ %></td>
        <td><%= record.getUsername() %></td>
        <td><%= record.getTotalGames() %></td>
        <td><%= record.getWins() %></td>
        <td><%= record.getLosses() %></td>
        <td><%= record.getDraws() %></td>
        <td><%= record.getWinrate() %> %</td>
    </tr>
    <% }
    } %>
</table>
<a href="MenuServlet">メニューへ戻る</a>
</body>
</html>
