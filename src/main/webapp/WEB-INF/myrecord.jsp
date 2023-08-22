<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>個人戦績</title>
</head>
<body>

<h2>個人戦績</h2>

<table border="10">
    <thead>
        <tr>
            <th>総プレイ数</th>
            <th>勝ち数</th>
            <th>負け数</th>
            <th>引き分け数</th>
            <th>勝率</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><%= request.getAttribute("totalGames") %></td>
            <td><%= request.getAttribute("wins") %></td>
            <td><%= request.getAttribute("losses") %></td>
            <td><%= request.getAttribute("draws") %></td>
            <td><%= request.getAttribute("winRate") %>%</td>
        </tr>
    </tbody>
</table>

<a href="MenuServlet">メニューへ戻る</a>

</body>
</html>