<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.*"%>
<%@ page import="java.util.List"%>

<%
UserCredential loginUser = (UserCredential) session.getAttribute("loginUser");
%>

<%
BlackjackGame game = (BlackjackGame) session.getAttribute("game");
if (game == null) {
	game = new BlackjackGame();
	session.setAttribute("game", game);
}
%>

<%!public String checkWinner(BlackjackGame game, UserCredential loginUser) {
		int playerTotal = game.getPlayer().getHandTotal();
		int dealerTotal = game.getDealer().getHandTotal();

		boolean win = false;
		boolean lose = false;
		boolean draw = false;

		String resultMessage = "";

		if (playerTotal > 21 && dealerTotal > 21) {
			draw = true;
			resultMessage = "両者バーストにより、引き分け";
		} else if (playerTotal > 21) {
			lose = true;
			resultMessage = "プレイヤーのバーストにより、ディーラーの勝利";
			updateGameRecordsAndReturnMessage(loginUser, win, lose, draw, resultMessage, playerTotal, dealerTotal);
			return resultMessage;
		} else if (dealerTotal > 21) {
			win = true;
			resultMessage = "ディーラーのバーストにより、プレイヤーの勝利";
		} else if (playerTotal > dealerTotal) {
			win = true;
			resultMessage = "プレイヤーの勝利";
		} else if (dealerTotal > playerTotal) {
			lose = true;
			resultMessage = "ディーラーの勝利";
		} else {
			draw = true;
			resultMessage = "引き分け";
		}
		return updateGameRecordsAndReturnMessage(loginUser, win, lose, draw, resultMessage, playerTotal, dealerTotal);
	}

	private String updateGameRecordsAndReturnMessage(UserCredential loginUser, boolean win, boolean lose, boolean draw,
			String resultMessage, int playerTotal, int dealerTotal) {
		try {
			dao.AccountsDAO dao = new dao.AccountsDAO();
			dao.updateGameRecords(Integer.parseInt(loginUser.getUserId()), win, lose, draw);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMessage;

	}%>
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
		<%=checkWinner(game, loginUser)%>
		<%--   <% session.removeAttribute("game"); %> --%>
		<br> <br> <a href="BlackjackServlet">再プレイ</a> <a
			href="MenuServlet">メニューへ戻る</a>
		<%
		}
		%>
	</div>

</body>
</html>