package service;

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

	public void performPlayerAction(BlackjackGame game, String action) {
		if ("hit".equals(action)) {
			game.playerHit();
		} else if ("stand".equals(action)) {
			game.playerStand();
			boolean shouldDealerStand = false;
			if (game.getDealer().getHand(0).size() == 2 && game.getDealer().getHandTotal(0) >= 17) {
				shouldDealerStand = true;
			}
			if (!shouldDealerStand) {
				while (game.getDealer().getHandTotal(0) < 17) {
					if (game.getDealer().getHandTotal(0) > 21) {
						break;
					}
					game.getDealer().receiveCard(game.drawCard(), 0);
				}
			}
		} else if ("split".equals(action)) {
	        performSplit(game);
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

	public void handlePostRequest(HttpSession session, String action) {
		BlackjackGame game = getOrCreateGame(session);
		if (action != null) {
			performPlayerAction(game, action);
		}
	}

	public String checkWinner(BlackjackGame game, UserCredential loginUser) {
		int playerTotal = game.getPlayer().getHandTotal(0);
		int dealerTotal = game.getDealer().getHandTotal(0);

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

	public String updateGameRecordsAndReturnMessage(UserCredential loginUser, boolean win, boolean lose, boolean draw,
			String resultMessage, int playerTotal, int dealerTotal) {
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
	}
}
