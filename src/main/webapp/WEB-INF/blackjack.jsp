<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>

<% Login loginUser = (Login) session.getAttribute("loginUser"); %>
	
<%
    BlackjackGame game = (BlackjackGame) session.getAttribute("game");
    if (game == null) {
        game = new BlackjackGame();
        session.setAttribute("game", game);
    }
%>

<%!
public String checkWinner(BlackjackGame game, Login loginUser) {
    int playerTotal = game.getPlayer().getHandTotal();
    int dealerTotal = game.getDealer().getHandTotal();

    if (playerTotal > 21) {
        return "プレイヤーのバーストにより、ディーラーの勝利";
    }

    if (dealerTotal > 21) {
        return "ディーラーのバーストにより、プレイヤーの勝利";
    }

    if (playerTotal > dealerTotal) {
        return "プレイヤーの勝利";
    } else if (dealerTotal > playerTotal) {
        return "ディーラーの勝利";
    } else {
        return "引き分け";
    }
    
}

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Blackjack</title>
</head>
<body>
	
	<p>ログイン中のユーザー名：<%= loginUser.getUsername() %>さん</p>
    <h1>ブラックジャック</h1>
    <pre><%= game.displayTable() %></pre>
    
    <% if (!game.isGameOver()) { %>
    	<form action="BlackjackServlet" method="post">
            <button type="submit" name="action" value="hit">ヒット</button>
            <button type="submit" name="action" value="stand">スタンド</button>
        </form>
        <a href="MenuServlet">メニューへ戻る</a>
	<% } else { %>
    	<%= checkWinner(game, loginUser) %>
        <% session.removeAttribute("game"); %>
        <a href="BlackjackServlet">再プレイ</a>
        <a href="MenuServlet">メニューへ戻る</a>
	<% } %>
</body>
</html>