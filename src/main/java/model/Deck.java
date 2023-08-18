package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Deck {
	private final List<Card> cards;
	private final List<String> suits = Arrays.asList("♡", "♢", "♧", "♤");
	private final List<String> values = Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K");

	public Deck() {
		cards = new ArrayList<>();
		for (String suit : suits) {
			for (String value : values) {
				cards.add(new Card(suit, value));
			}
		}
		shuffle();
	}

	public void shuffle() {
		Random random = new Random();
		for (int i = 0; i < cards.size(); i++) {
			int swapIndex = random.nextInt(cards.size());
			Card temp = cards.get(i);
			cards.set(i, cards.get(swapIndex));
			cards.set(swapIndex, temp);
		}
	}

	public Card drawCard() {
		return cards.remove(cards.size() - 1);
	}
}
