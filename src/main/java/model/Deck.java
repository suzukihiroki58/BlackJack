package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {
	private final List<Card> cards;
	private final List<String> suits = Arrays.asList("HEARTS", "DIAMONDS", "CLUBS", "SPADES");
	private final List<String> values = Arrays.asList("ACE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT",
			"NINE", "TEN", "JACK", "QUEEN", "KING");

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
		Collections.shuffle(cards);
	}

	public Card drawCard() {
		return cards.remove(cards.size() - 1);
	}
}
