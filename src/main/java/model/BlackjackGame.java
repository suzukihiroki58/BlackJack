package model;

public class BlackjackGame {
	private final Deck deck;
	private final Player player;
	private final Player dealer;
	private boolean playerStand = false;

	public boolean isGameOver() {
		if (playerStand) {
			//デバッグ
			System.out.println("プレイヤーの手札： " + player.toString() + "\nディーラーの手札： " + dealer.toString());
			return true;
		} else if (getPlayer().getHandTotal() > 21) {
			//デバッグ
			System.out.println("プレイヤーの手札： " + player.toString() + "\nディーラーの手札： " + dealer.toString());
			return true;
		} else if (getDealer().getHandTotal() > 21) {
			//デバッグ
			System.out.println("プレイヤーの手札： " + player.toString() + "\nディーラーの手札： " + dealer.toString());
			return true;
		}
		return false;
	}

	public BlackjackGame() {
		deck = new Deck();
		player = new Player();
		dealer = new Player();
		dealInitialCards();
		//デバッグ
		System.out.println("プレイヤーの手札： " + player.toString() + "\nディーラーの手札： " + dealer.toString());
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

	public Card drawCard() {
		return deck.drawCard();
	}

	public void playerHit() {
		player.receiveCard(deck.drawCard());
	}

	public void playerStand() {
		this.playerStand = true;
	}

}
