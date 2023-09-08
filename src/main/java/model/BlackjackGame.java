package model;

public class BlackjackGame {
	private final Deck deck;
	private final Player player;
	private final Player dealer;
	private boolean playerStand = false;

	public boolean isGameOver() {
		if (playerStand) {
			//デバッグ
			System.out.println("プレイヤーの手札： " + player.describeHand(0) + "\nディーラーの手札： " + dealer.describeHand(0));
			return true;
		} else if (getPlayer().getHandTotal(0) > 21) {
			//デバッグ
			System.out.println("プレイヤーの手札： " + player.describeHand(0) + "\nディーラーの手札： " + dealer.describeHand(0));
			return true;
		} else if (getDealer().getHandTotal(0) > 21) {
			//デバッグ
			System.out.println("プレイヤーの手札： " + player.describeHand(0) + "\nディーラーの手札： " + dealer.describeHand(0));
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
		System.out.println("プレイヤーの手札： " + player.describeHand(0) + "\nディーラーの手札： " + dealer.describeHand(0));
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

	public void playerHit() {
		player.receiveCard(deck.drawCard(), 0);
	}

	public void playerStand() {
		this.playerStand = true;
	}

}
