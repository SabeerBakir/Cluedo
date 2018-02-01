import javax.swing.JOptionPane;

public class Player implements PlayerInterface{

	public int getPlayerID() { return playerID;	}
	public int getPosX() { return posX;	}
	public int getPosY() { return posY;	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}

	private int playerID; // a number to represent each player
	private int posX = 0; // X position on grid for player token
	private int posY = 0; // Y position on grid for player token
	
	private boolean playing = true; // If a player makes a false accusation, they are no longer in the game
	
	private String[] cards; // Contains a list of the cards a player has
	
	public Player(int playerID, int posX, int posY) { // Player Constructor
		this.playerID = playerID;
		this.posX = posX;
		this.posY = posY;
	}
	
	public void accuse(String killerName, String killerWeapon, String killerRoom) { // making an accusation
		String accusation = JOptionPane.showInputDialog(null, "Enter Accusation: ");
		
			if((accusation.contains(killerName)) && (accusation.contains(killerWeapon)) && (accusation.contains(killerRoom))){ //check accusation against killer cards, **still needs error checking and format checking
				JOptionPane.showConfirmDialog(null, "You win, the murderer was " + killerName + " with the " + killerWeapon + " in the " + killerRoom);
			}
			else {
				JOptionPane.showConfirmDialog(null, "You have lost, you are out of the game"); // if you accuse incorrectly you lose
				playing = false; // This will ensure your go is skipped
			}
		
	}

	public void ask(Player[] players) { // When you ask a player a question
		
		// Need to allow other player choice to choose what card to show the other player
		
		
		String question = JOptionPane.showInputDialog(null, "Enter Question: ");
		int j = 1;
		boolean cardFound = false;
		
		//traverse player's cards to left of you to see if they have a card
		while(!cardFound && j < players.length) {
			int i = 0;
			while(i < players[(playerID - j) % players.length].cards.length && j < players.length) {
				if(question.contains(players[(playerID - j) % players.length].cards[i])) {
					JOptionPane.showMessageDialog(null, players[(playerID - j) % players.length].cards[i]);
					cardFound = true;
				}
				else {
					i++;
				}
			}
			j++;
		}
		
		if(cardFound == false) { // If no one has the cards i.e. it's the killer
			JOptionPane.showMessageDialog(null, "Nobody has these cards");
		}
		
	}

	public void moveUp() {
		posY++;
	}
	
	public void moveDown() {
		posY--;		
	}
	
	public void moveLeft() {
		posX--;		
	}
	
	public void moveRight() {
		posX++;
	}
	
	public boolean isPlaying() {
		return playing;
	}

}
