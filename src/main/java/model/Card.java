package model;

public class Card {
	private final String suit;
	private final String value;
	private String imagePath;
	private static final int ACE_VALUE = 11;
	private static final int TWO_VALUE = 2;
	private static final int THREE_VALUE = 3;
	private static final int FOUR_VALUE = 4;
	private static final int FIVE_VALUE = 5;
	private static final int SIX_VALUE = 6;
	private static final int SEVEN_VALUE = 7;
	private static final int EIGHT_VALUE = 8;
	private static final int NINE_VALUE = 9;
	private static final int TEN_JACK_QUEEN_KING_VALUE = 10;
	private static final int OTHER_VALUE = 0;

	public Card(String suit, String value) {
		this.suit = suit;
		this.value = value;
		this.imagePath = "images/" + value + "of" + suit + ".png";
	}

	public String getSuit() {
		return suit;
	}

	public String getValue() {
		return value;
	}

	public String getRank() {
		return value;
	}

	public String getImagePath() {
		return imagePath;
	}

	public int getNumericValue() {
		switch (value) {
		case "ACE":
			return ACE_VALUE;
		case "TWO":
			return TWO_VALUE;
		case "THREE":
			return THREE_VALUE;
		case "FOUR":
			return FOUR_VALUE;
		case "FIVE":
			return FIVE_VALUE;
		case "SIX":
			return SIX_VALUE;
		case "SEVEN":
			return SEVEN_VALUE;
		case "EIGHT":
			return EIGHT_VALUE;
		case "NINE":
			return NINE_VALUE;
		case "TEN":
		case "JACK":
		case "QUEEN":
		case "KING":
			return TEN_JACK_QUEEN_KING_VALUE;
		default:
			return OTHER_VALUE;
		}
	}

	@Override
	public String toString() {
		return suit + value;
	}
}
