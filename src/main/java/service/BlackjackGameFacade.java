package service;

import javax.servlet.http.HttpSession;

import model.BlackjackGame;

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
}
