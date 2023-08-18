package model;

public class Card {
	private final String suit;
	private final String value;

	public Card(String suit, String value) {
		this.suit = suit;
		this.value = value;
	}

	public String getSuit() {
		return suit;
	}

	public String getValue() {
		return value;
	}

	public int getNumericValue() {
		switch (value) {
		case "A":
			return 11;
		case "2":
			return 2;
		case "3":
			return 3;
		case "4":
			return 4;
		case "5":
			return 5;
		case "6":
			return 6;
		case "7":
			return 7;
		case "8":
			return 8;
		case "9":
			return 9;
		case "10":
		case "J":
		case "Q":
		case "K":
			return 10;
		default:
			return 0;
		}
	}

	@Override
	public String toString() {
		return suit + value;
	}
}
