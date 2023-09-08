<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="java.util.List"%>
<%@ page import="service.BlackjackGameFacade"%>

<%
BlackjackGameFacade gameFacade = new BlackjackGameFacade();
UserCredential loginUser = gameFacade.getOrCreateLoginUser(session);
BlackjackGame game = gameFacade.getOrCreateGame(session);
boolean canSplit = gameFacade.canSplit(game.getPlayer().getHand());
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Blackjack</title>
<link rel="stylesheet" href="css/blackjack.css">
<script src="js/script.js"></script>
</head>
<body class="body">
	<div class="header">
		<p class="header-link-left">
			ログイン中のユーザー：<%=loginUser.getUserName()%>さん
		</p>
		<a href="MenuServlet" class="header-link-right">メニューへ戻る</a>
	</div>
	<div class="dealer">
		<img src="images/dealer.png" alt="Dealer" class="dealer-character">
	</div>

	<div class="main-wrapper">
		<div class="container">
			<div class="game-board">
				<div class="dealer-hand" id="dealer-hand">
					<h2 class="large-white-text">ディーラーの手札</h2>
					<%
					List<Card> dealerHand = game.getDealer().getHand();
					%>
					<%
					for (int i = 0; i < dealerHand.size(); i++) {
					%>
					<%
					if (i == 1 && !game.isGameOver()) {
					%>
					<img src="images/card_back.png" alt="カードの背面" />
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
				<div class="player-hand">
					<h2 class="large-white-text">プレイヤーの手札</h2>
					<%
					for (Card card : game.getPlayer().getHand()) {
					%>
					<img src="<%=card.getImagePath()%>"
						alt="<%=card.getSuit()%> <%=card.getValue()%> " />
					<%
					}
					%>
				</div>
			</div>

			<%
			if (!game.isGameOver()) {
			%>
			<form action="BlackjackServlet" method="post">
				<button type="submit" name="action" value="hit" class="game-button">ヒット</button>
				<button type="submit" name="action" value="stand"
					class="game-button">スタンド</button>
				<%
				if (canSplit) {
				%>
				<button type="submit" name="action" value="split"
					class="game-button">スプリット</button>
				<%
				}
				%>
			</form>
			<%
			} else {
			%>
			<span class="large-white-text"><%=gameFacade.checkWinner(game, loginUser)%></span>
			<br> <br> <a href="BlackjackServlet"
				class="large-white-text-replay">再プレイ</a>
			<%
			}
			%>
		</div>
	</div>
	</div>

	<footer class="footer">
		<p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
	</footer>

</body>
</html>
