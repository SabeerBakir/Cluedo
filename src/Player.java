import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")

public class Player extends JPanel implements PlayerInterface{

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
	private int posX; // X position on grid for player token
	private int posY; // Y position on grid for player token
	
	private BufferedImage tokenIcon;
	
	private boolean playing = true; // If a player makes a false accusation, they are no longer in the game
	
	private String[] cards; // Contains a list of the cards a player has
	
	public Player(int playerID, int posX, int posY, String tokenName) { // Player Constructor
		
		this.playerID = playerID;
		this.posX = posX;
		this.posY = posY;
		
		try {
			tokenIcon = ImageIO.read(new File("Player Token Icon/"+ tokenName +".png"));
		}	
		catch(IOException exImage1) {
			System.out.print("Image Exception: " + exImage1.getMessage());
		}	
		
		setOpaque(false);
		setLocation(posX*23,posY*23);
		setSize(21,21);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(tokenIcon, 0, 0, this.getHeight(), this.getHeight(), this);
    }

	
		
	public String accuse(String killerName, String killerWeapon, String killerRoom) { // making an accusation
		String accusation = JOptionPane.showInputDialog(null, "Enter Accusation: ");
		
			if((accusation.contains(killerName)) && (accusation.contains(killerWeapon)) && (accusation.contains(killerRoom))){ //check accusation against killer cards, **still needs error checking and format checking
				return "You win, the murderer was " + killerName + " with the " + killerWeapon + " in the " + killerRoom;
			}
			else {
				playing = false; // This will ensure your go is skipped
				return "You have lost, you are out of the game"; // if you accuse incorrectly you lose
			}
		
	}

	public String ask(Player[] players) { // When you ask a player a question
		
		// Need to allow other player choice to choose what card to show the other player
		
		
		String question = JOptionPane.showInputDialog(null, "Enter Question: ");
		int j = 1;
		boolean cardFound = false;
		
		//traverse player's cards to left of you to see if they have a card
		while(!cardFound && j < players.length) {
			int i = 0;
			while(i < players[(playerID - j) % players.length].cards.length && j < players.length) {
				if(question.contains(players[(playerID - j) % players.length].cards[i])) {
					cardFound = true;
					return players[(playerID - j) % players.length].cards[i];
				}
				else {
					i++;
				}
			}
			j++;
		}
		
		if(cardFound == false) { // If no one has the cards i.e. it's the killer
			return "Nobody has these cards";
		}
		else{
			return null;
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
