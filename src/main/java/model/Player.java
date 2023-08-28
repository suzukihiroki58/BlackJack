package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private final List<Card> hand;

	public Player() {
		hand = new ArrayList<>();
	}

	public void receiveCard(Card card) {
		hand.add(card);
	}

	public List<Card> getHand() {
		return hand;
	}

	public int getHandTotal() {
		int total = 0;
		int aces = 0;
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
		//デバッグ
		System.out.println("getHandTotal: " + total);

		return total;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Card card : hand) {
			builder.append(card.toString()).append("と");
		}
		return builder.substring(0, builder.length() - 1);
	}
}
