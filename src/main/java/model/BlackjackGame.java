package model;

public class BlackjackGame {
	private final Deck deck;
	private final Player player;
	private final Player dealer;
	private boolean playerStood = false; 

	public boolean isGameOver() {
		if (playerStood) {
			return true; 
		}
		if (getPlayer().getHandTotal() > 21) {
			return true; 
		}
		if (getDealer().getHandTotal() > 21) {
			return true; 
		}
		return false;
	}

	public BlackjackGame() {
		deck = new Deck();
		player = new Player();
		dealer = new Player();
		dealInitialCards();
	}

	public void dealInitialCards() {
		player.receiveCard(deck.drawCard());
		player.receiveCard(deck.drawCard());
		dealer.receiveCard(deck.drawCard());
		dealer.receiveCard(deck.drawCard());
	}

	public Player getPlayer() {
		return player;
	}

	public Player getDealer() {
		return dealer;
	}

	public String displayTable() {
		if (!this.isGameOver()) {
			return "プレイヤーの手札： " + player.toString() + "\nディーラーの手札： " + dealer.getHand().get(0) + "と非表示のカード";
		} else {
			return "プレイヤーの手札： " + player.toString() + "\nディーラーの手札： " + dealer.toString();
		}
	}

	public Card drawCard() {
		return deck.drawCard();
	}

	public void playerHit() {
		player.receiveCard(deck.drawCard());
	}
	
	public void playerStand() {
		this.playerStood = true;
	}

}
