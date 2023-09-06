<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="java.util.List"%>
<%@ page import="service.BlackjackGameFacade"%>

<%
BlackjackGameFacade gameFacade = new BlackjackGameFacade();
UserCredential loginUser = gameFacade.getOrCreateLoginUser(session);
BlackjackGame game = gameFacade.getOrCreateGame(session);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Blackjack</title>
<link rel="stylesheet" href="css/style.css">
<script src="js/script.js"></script>
</head>
<body>
	<div class="container">
		<p>
			ログイン中のユーザー名：<%=loginUser.getUserName()%>さん
		</p>
		<h1>ブラックジャック</h1>
		<div class="game-board">
			<div class="player-hand">
				<h2>プレイヤーの手札</h2>
				<%
				for (Card card : game.getPlayer().getHand()) {
				%>
				<img src="<%=card.getImagePath()%>"
					alt="<%=card.getSuit()%> <%=card.getValue()%> " />
				<%
				}
				%>
			</div>
			<div class="dealer-hand">
				<h2>ディーラーの手札</h2>
				<%
				List<Card> dealerHand = game.getDealer().getHand();
				%>
				<%
				for (int i = 0; i < dealerHand.size(); i++) {
				%>
				<%
				if (i == 1 && !game.isGameOver()) {
				%>
				<img src="images/card_back.jpg" alt="カードの背面" />
				<%
				} else {
				%>
				<img src="<%=dealerHand.get(i).getImagePath()%>"
					alt="<%=dealerHand.get(i).getSuit()%> <%=dealerHand.get(i).getValue()%> " />
				<%
				}
				%>
				<%
				}
				%>
			</div>
		</div>
		<%
		if (!game.isGameOver()) {
		%>
		<form action="BlackjackServlet" method="post">
			<button type="submit" name="action" value="hit">ヒット</button>
			<button type="submit" name="action" value="stand">スタンド</button>
		</form>
		<a href="MenuServlet">メニューへ戻る</a>
		<%
		} else {
		%>
		<%=gameFacade.checkWinner(game, loginUser)%>
		<%--   <% session.removeAttribute("game"); %> --%>
		<br> <br> <a href="BlackjackServlet">再プレイ</a> <a
			href="MenuServlet">メニューへ戻る</a>
		<%
		}
		%>
	</div>

</body>
</html>