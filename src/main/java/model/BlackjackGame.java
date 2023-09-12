package model;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
	private final Deck deck;
	private final Player player;
	private final Player dealer;
	private List<Boolean> playerStandList; 

	public boolean isGameOver() {
		if (playerStandList.get(0)) {
			return true;
		} else if (getPlayer().getHandTotal(0) > 21) {
			return true;
		} else if (getDealer().getHandTotal(0) > 21) {
			return true;
		}
		return false;
	}

	public BlackjackGame() {
		deck = new Deck();
		player = new Player();
		dealer = new Player();
		dealInitialCards();
		playerStandList = new ArrayList<>();
	    playerStandList.add(false);
	}

	public void dealInitialCards() {
		player.receiveCard(deck.drawCard(), 0);
		player.receiveCard(deck.drawCard(), 0);
		dealer.receiveCard(deck.drawCard(), 0);
		dealer.receiveCard(deck.drawCard(), 0);
	}

	public Player getPlayer() {
		return player;
	}

	public Player getDealer() {
		return dealer;
	}

	public Card drawCard() {
		return deck.drawCard();
	}

	public void playerHit(Card card, int handIndex) {
		player.receiveCard(card, handIndex);
	}

	public void playerStand() {
		this.playerStandList.set(0, true);
	}

}
