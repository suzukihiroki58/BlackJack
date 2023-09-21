<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="java.util.List"%>
<%@ page import="service.BlackjackGameFacade"%>

<%
BlackjackGameFacade gameFacade = new BlackjackGameFacade();
UserCredential loginUser = gameFacade.getOrCreateLoginUser(session);
BlackjackGame game = gameFacade.getOrCreateGame(session, loginUser);
boolean canSplit = !game.getPlayer().hasSplit() && game.canSplit(game.getPlayer().getHand(0));
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
			ログイン中のユーザー：<%=loginUser.getUserName()%>さん（所持チップ数：<%= game.getPlayer().getChips() %>枚）
		</p>
		<a href="MenuServlet" class="header-link-right">メニューへ戻る</a>
	</div>
	<div class="main-wrapper">
		<div class="container">
			<div class="game-board">
			<% if (game.getBetAmount() == 0) { %>
			<div class="bet-form-center">
			    <form action="BlackjackServlet" method="post" class="bet-form">
			        <label for="betAmount" class="bet-label">賭けるチップの枚数を決めてください</label>
			        <div class="bet-container">
				        <input type="number" id="betAmount" name="betAmount" min="1" max="10" class="bet-input">
				        <button type="submit" name="action" value="bet" class="bet-button">賭ける</button>
			        </div>
			    </form>
		     </div>
			<% } else { %>
				<div class="dealer-hand" id="dealer-hand">
					<%
					int handIndex = 0;
					%>
					<% if (game.isGameOver(handIndex)) { %>
        				<h2 class="large-white-text">ディーラーの手札（合計：<%= game.getDealer().getHandTotal(0) %>）</h2>
    				<% } else { %>
					<h2 class="large-white-text">ディーラーの手札（合計：？）</h2>
					<%
    				}
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
				List<List<Card>> playerHands = game.getPlayer().getHands();
				int totalHands = playerHands.size();
				for (int currentHandIndex = 0; currentHandIndex < totalHands; currentHandIndex++) {
					List<Card> hand = playerHands.get(currentHandIndex);
					int handTotal = game.getPlayer().getHandTotal(currentHandIndex);
					int currentBetAmount = betAmounts.get(currentHandIndex); 
				%>
				<div class="player-hand">
					<div class="player-info">
						<h3 class="large-white-text">プレイヤーの手札（合計：<%= handTotal %>）</h3>
						<h3 class="large-white-text">賭けたチップ：<%= currentBetAmount %>枚</h3>
					</div>
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
						if (handTotal > 21) {
						} else if (handTotal != 21 && game.getPlayerStateForHand(currentHandIndex) != BlackjackGame.PlayerState.STAND) {
				        %>
					<form action="BlackjackServlet" method="post" class="button-container">
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
					    } else if (handTotal == 21) {
					%>
					<form action="BlackjackServlet" method="post" class="button-container">
					    <input type="hidden" name="handIndex" value="<%=handIndex%>">
					    <button type="submit" name="action" value="stand" class="game-button">スタンド</button>
					    <% if (canSplit) { %>
					    <button type="submit" name="action" value="split" class="game-button">スプリット</button>
					    <% } %>  
					</form>
					<%
				            }
					handIndex += 1;
					} else {
					if (currentHandIndex == totalHands - 1) {
					%>
					<div class="result-container">
						<span class="large-white-text"> 
						<div class="game-result-label">〜ゲームの結果〜</div>
						<%
						 List<String> resultMessages = gameFacade.checkWinners(game, loginUser);
						 for (int i = 0; i < resultMessages.size(); i++) {
					 	%>
							<div>
						        <% if(resultMessages.size() > 1) { %>
						            <%=i + 1%>つ目の手札:
						        <% } %>
						        <%=resultMessages.get(i)%>
						    </div>
							<%
							 }
							 %>
						</span> 
							<h3 class="large-white-text">チップの増減：<%= game.calculateChipDifference() %>枚</h3>
							<form action="BlackjackServlet" method="post">
							    <button type="submit" name="action" value="replay">再プレイ</button>
						  	</form>
				  	</div>
					<%
					}
					}
					%>
				</div>
				<%
				}
			} 
			%>
			</div>
		</div>
	</div>
	</div>

	<footer class="footer">
		<p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
	</footer>

</body>
</html>
