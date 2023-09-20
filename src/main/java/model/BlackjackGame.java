package model;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
	private final Deck deck;
	private final Player player;
	private final Player dealer;
	private int betAmount;
	private int initialChips;
	private List<Integer> betAmountList = new ArrayList<>();

	public enum PlayerState {
		STAND, BURST, PLAYING
	}

	private List<PlayerState> playerStateList = new ArrayList<>();

	public boolean isGameOver(int handIndex) {
		for (PlayerState state : playerStateList) {
			if (state == PlayerState.PLAYING) {
				return false;
			}
		}
		return true;
	}

	public BlackjackGame() {
		deck = new Deck();
		player = new Player();
		dealer = new Player();
		dealInitialCards();
		playerStateList.add(PlayerState.PLAYING);
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
		int handTotal = getPlayer().getHandTotal(handIndex);
		if (handTotal > 21) {
			playerStateList.set(handIndex, PlayerState.BURST);
		}
	}

	public void playerStand(int handIndex) {
		playerStateList.set(handIndex, PlayerState.STAND);
	}

	public void addNewHandForSplit() {
		playerStateList.add(PlayerState.PLAYING);
	}

	public void setBetAmount(int betAmount) {
		this.betAmount = betAmount;
		this.initialChips = player.getChips();
		player.removeChips(betAmount);
		betAmountList.add(betAmount);
	}

	public int getBetAmount() {
		return betAmount;
	}

	public enum GameResult {
		PLAYER_WINS, DRAW, DEALER_WINS
	}

	public void updateChipsBasedOnOutcome(GameResult result, int handIndex) {
		switch (result) {
		case PLAYER_WINS:
			if (player.hasBlackjack(handIndex)) {
				player.addChips((int) (betAmountList.get(handIndex) * 2.5));
			} else {
				player.addChips(betAmountList.get(handIndex) * 2);
			}
			break;
		case DRAW:
			player.addChips(betAmountList.get(handIndex));
			break;
		case DEALER_WINS:
			break;
		default:
			break;
		}
	}

	public String calculateChipDifference() {
		int difference = player.getChips() - initialChips;
		if (difference > 0) {
			return "+" + difference;
		}
		return String.valueOf(difference);
	}

	public void addNewBetForSplit() {
		int originalBet = betAmountList.get(0);
		player.removeChips(originalBet);
		betAmountList.add(originalBet);
	}

	public List<Integer> getBetAmountList() {
		return betAmountList;
	}

	public boolean canSplit(List<Card> hand) {
		if (hand.size() != 2) {
			return false;
		}
		Card firstCard = hand.get(0);
		Card secondCard = hand.get(1);
		return firstCard.getNumericValue() == secondCard.getNumericValue();
	}

}
