package service;

import javax.servlet.http.HttpSession;

import dao.GameRecordsDAO;
import model.BlackjackGame;
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
            if (game.getDealer().getHand().size() == 2 && game.getDealer().getHandTotal() >= 17) {
                shouldDealerStand = true;
            }
            if (!shouldDealerStand) {
                while (game.getDealer().getHandTotal() < 17) {
                    if (game.getDealer().getHandTotal() > 21) {
                        break;
                    }
                    game.getDealer().receiveCard(game.drawCard());
                }
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
    
    public void handlePostRequest(HttpSession session, String action) {
        BlackjackGame game = getOrCreateGame(session);
        if (action != null) {
            performPlayerAction(game, action);
        }
    }
    
    public String checkWinner(BlackjackGame game, UserCredential loginUser) {
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
            GameRecord gameRecord = new GameRecord(String.valueOf(loginUser.getUserId()), win ? 1 : 0, lose ? 1 : 0, draw ? 1 : 0);
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
}



