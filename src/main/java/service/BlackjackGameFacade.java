package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import dao.GameRecordsDAO;
import model.BlackjackGame;
import model.Card;
import model.GameRecord;
import model.UserCredential;

public class BlackjackGameFacade {
	public BlackjackGame initializeGame() {
		return new BlackjackGame();
	}

	public void performPlayerAction(BlackjackGame game, String action, int handIndex) {
		if ("hit".equals(action) && game.getPlayer().getHandTotal(handIndex) >= 21) {
			return;
		}

		game.getPlayer().setCurrentHandIndex(handIndex);
		if ("hit".equals(action)) {
			Card cardToHit = game.drawCard();
			game.playerHit(cardToHit, handIndex);
		} else if ("stand".equals(action)) {
			game.playerStand(handIndex);
		} else if ("split".equals(action)) {
			performSplit(game);
			handIndex += 1;
			game.addNewHandForSplit();
		}

		if (game.isGameOver(handIndex)) {
			while (game.getDealer().getHandTotal(0) < 17) {
				if (game.getDealer().getHandTotal(0) > 21) {
					break;
				}
				game.getDealer().receiveCard(game.drawCard(), 0);
			}
		}
	}

	public BlackjackGame getOrCreateGame(HttpSession session) {
		BlackjackGame game = (BlackjackGame) session.getAttribute("game");
		if (game == null) {
			game = initializeGame();
			session.setAttribute("game", game);
		}
		return game;
	}

	public void handlePostRequest(HttpSession session, String action, int handIndex) {
		BlackjackGame game = getOrCreateGame(session);
		if (action != null) {
			performPlayerAction(game, action, handIndex);
		}
	}

	public List<String> checkWinners(BlackjackGame game, UserCredential loginUser) {
		List<String> resultMessages = new ArrayList<>();
		int dealerTotal = game.getDealer().getHandTotal(0);

		for (int handIndex = 0; handIndex < game.getPlayer().getHands().size(); handIndex++) {
			int playerTotal = game.getPlayer().getHandTotal(handIndex);
			String resultMessage = calculateResultMessage(playerTotal, dealerTotal);
			game.updateChipsBasedOnOutcome(resultMessage);
			resultMessages.add(resultMessage);
		}
		return resultMessages;
	}

	private String calculateResultMessage(int playerTotal, int dealerTotal) {
		boolean win = false;
		boolean lose = false;
		boolean draw = false;

		String resultMessage = "";

		if (playerTotal > 21) {
			lose = true;
			resultMessage = "プレイヤーのバーストにより、ディーラーの勝利";
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
		return resultMessage;
	}

	public String updateGameRecordsAndReturnMessage(UserCredential loginUser, boolean win, boolean lose, boolean draw,
			String resultMessage, int playerTotal, int dealerTotal, int handIndex) {
		try {
			GameRecordsDAO dao = new GameRecordsDAO();
			GameRecord gameRecord = new GameRecord(String.valueOf(loginUser.getUserId()), win ? 1 : 0, lose ? 1 : 0,
					draw ? 1 : 0);
			dao.updateGameRecords(gameRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMessage;
	}

	public UserCredential getOrCreateLoginUser(HttpSession session) {
		UserCredential loginUser = (UserCredential) session.getAttribute("loginUser");
		if (loginUser == null) {
			loginUser = new UserCredential();
			session.setAttribute("loginUser", loginUser);
		}
		return loginUser;
	}

	public boolean canSplit(List<Card> hand) {
		if (hand.size() != 2) {
			return false;
		}
		Card firstCard = hand.get(0);
		Card secondCard = hand.get(1);
		return firstCard.getNumericValue() == secondCard.getNumericValue();
	}

	public void performSplit(BlackjackGame game) {
		game.getPlayer().splitHand();
		game.getPlayer().receiveCard(game.drawCard(), 0);
		game.getPlayer().receiveCard(game.drawCard(), 1);
	}
}
