package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private final List<List<Card>> hands;
	private int currentHandIndex = 0;
	private boolean hasSplit = false;
	private int chips;

	public Player() {
		hands = new ArrayList<>();
		hands.add(new ArrayList<>());
		this.chips = 100;
	}

	public void receiveCard(Card card, int handIndex) {
		hands.get(handIndex).add(card);
	}

	public List<Card> getHand(int handIndex) {
		return hands.get(handIndex);
	}

	public List<List<Card>> getHands() {
		return hands;
	}

	public int getHandTotal(int handIndex) {
		int total = 0;
		int aces = 0;
		List<Card> hand = hands.get(handIndex);
		for (Card card : hand) {
			total += card.getNumericValue();
			if ("ACE".equals(card.getValue())) {
				aces++;
			}
		}
		while (total > 21 && aces > 0) {
			total -= 10;
			aces--;
		}
		return total;
	}

	public String describeHand(int handIndex) {
		StringBuilder builder = new StringBuilder();
		List<Card> hand = hands.get(handIndex);
		for (Card card : hand) {
			builder.append(card.toString()).append("と");
		}
		return builder.substring(0, builder.length() - 1);
	}

	public void splitHand() {
		if (hands.get(0).size() != 2) {
			return;
		}
		List<Card> newHand = new ArrayList<>();
		newHand.add(hands.get(0).remove(1));
		hands.add(newHand);
		hasSplit = true;
	}

	public void setCurrentHandIndex(int index) {
		this.currentHandIndex = index;
	}

	public int getCurrentHandIndex() {
		return this.currentHandIndex;
	}

	public boolean hasSplit() {
		return hasSplit;
	}

	public int getChips() {
		return chips;
	}

	public void addChips(int amount) {
		this.chips += amount;
	}

	public void removeChips(int amount) {
		this.chips -= amount;
	}

}
