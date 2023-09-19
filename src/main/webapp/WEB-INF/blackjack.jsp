<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="java.util.List"%>
<%@ page import="service.BlackjackGameFacade"%>

<%
BlackjackGameFacade gameFacade = new BlackjackGameFacade();
UserCredential loginUser = gameFacade.getOrCreateLoginUser(session);
BlackjackGame game = gameFacade.getOrCreateGame(session);
boolean canSplit = !game.getPlayer().hasSplit() && gameFacade.canSplit(game.getPlayer().getHand(0));
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
	<!--  <div class="dealer">
		<img src="images/dealer.png" alt="Dealer" class="dealer-character">
	</div> --> 

	<div class="main-wrapper">
		<div class="container">
			<div class="game-board">
			<% if (game.getBetAmount() == 0) { %>
    <form action="BlackjackServlet" method="post">
        <label for="betAmount">賭けるチップ: </label>
        <input type="number" id="betAmount" name="betAmount" min="1" max="<%= game.getPlayer().getChips() %>">
        <button type="submit" name="action" value="bet">賭ける</button>
    </form>
<% } else { %>
				<div class="dealer-hand" id="dealer-hand">
					<%
					int handIndex = 0;
					%>
					<h2 class="large-white-text">ディーラーの手札</h2>
					<%
					List<Card> dealerHand = game.getDealer().getHand(0);
					%>
					<%
					for (int i = 0; i < dealerHand.size(); i++) {
					%>
					<%
					if (i == 1 && !game.isGameOver(handIndex)) {
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
				<%
				List<Integer> betAmounts = game.getBetAmountList();
				for (int i = 0; i < betAmounts.size(); i++) {
				%>
				    <h3 class="large-white-text"><%= i + 1 %>つ目の手札で賭けたチップ：<%= betAmounts.get(i) %></h3>
				<%
				}
				%>
				<h3 class="large-white-text">保有チップ：<%= game.getPlayer().getChips() %></h3>
				<%
				List<List<Card>> playerHands = game.getPlayer().getHands();
				int totalHands = playerHands.size();
				for (int currentHandIndex = 0; currentHandIndex < totalHands; currentHandIndex++) {
					List<Card> hand = playerHands.get(currentHandIndex);
				%>
				<div class="player-hand">
					<h2 class="large-white-text">プレイヤーの手札</h2>
					<%
					for (Card card : hand) {
					%>
					<img src="<%=card.getImagePath()%>"
						alt="<%=card.getSuit()%> <%=card.getValue()%> " />
					<%
					}
					%>
					<%
					if (!game.isGameOver(currentHandIndex)) {
					%>
					<form action="BlackjackServlet" method="post">
						<input type="hidden" name="handIndex" value="<%=handIndex%>">
						<button type="submit" name="action" value="hit"
							class="game-button">ヒット</button>
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
					handIndex += 1;
					} else {
					if (currentHandIndex == totalHands - 1) {
					%>
					<span class="large-white-text"> 
					<%
					 List<String> resultMessages = gameFacade.checkWinners(game, loginUser);
					 for (int i = 0; i < resultMessages.size(); i++) {
				 	%>
						<div>
							<%=i + 1%>つ目の手札の結果:
							<%=resultMessages.get(i)%>
						</div> 
						<%
						 }
						 %>
					</span> 
						<h3 class="large-white-text">チップの増減：<%= game.calculateChipDifference() %></h3>
						<h3><a href="BlackjackServlet" class="large-white-text-replay">再プレイ</a></h3>
					<%
					}
					}
					%>
				</div>
				<%
				}
				%>
				<% } %>
			</div>
		</div>
	</div>
	</div>

	<footer class="footer">
		<p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
	</footer>

</body>
</html>
