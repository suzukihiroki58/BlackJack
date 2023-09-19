package model;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
	private final Deck deck;
	private final Player player;
	private final Player dealer;
	private List<Boolean> playerStandList;
	private int betAmount;
	private int initialChips;

	public boolean isGameOver(int handIndex) {
		boolean allHandsStandOrBurst = true;
		for (int i = 0; i < playerStandList.size(); i++) {
			if (!playerStandList.get(i) && getPlayer().getHandTotal(i) <= 21) {
				allHandsStandOrBurst = false;
				break;
			}
		}
		return allHandsStandOrBurst;
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

	public void playerStand(int handIndex) {
		while (playerStandList.size() <= handIndex) {
			playerStandList.add(false);
		}
		playerStandList.set(handIndex, true);
	}

	public void addNewHandForSplit() {
		playerStandList.add(false);
	}

	public void setBetAmount(int betAmount) {
		this.betAmount = betAmount;
		this.initialChips = player.getChips();
		player.removeChips(betAmount);
	}

	public int getBetAmount() {
		return betAmount;
	}

	public void updateChipsBasedOnOutcome(String result) {
		if (result.contains("プレイヤーの勝利")) {
			player.addChips(betAmount * 2);
		} else if (result.contains("引き分け")) {
			player.addChips(betAmount);
		}
	}

	public int calculateChipDifference() {
		return player.getChips() - initialChips;
	}

}
