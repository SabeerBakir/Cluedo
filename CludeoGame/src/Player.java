import java.util.ArrayList;

import javax.swing.JOptionPane;

// Kacper Twardowski (16401636), Sabeer Bakir (16333886), Aidan Mac Neill (16349586)

public class Player {

	private int id;					// Player ID
	private String name;			// Player name (not the same as token name)
	private Token character;		// Token linked to the character
	private Coordinates pos;		// Coordinates for this player's character
	private String room;			// Name of the room the player is in
	private Room occupiedRoom;		// The room th eplayer is in
	private Cards playersCards;		// The players deck of cards
	private Notes notes;			// The players notes
	
	public Player(int id, String name, Token character, Coordinates pos) {
		this.id = id;
		this.name = name;
		this.character = character;
		this.pos = pos;
		room = null;
		occupiedRoom = null;
		playersCards = new Cards();
		notes = new Notes();
	}
	
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Token getCharacter() {
		return character;
	}
	public Coordinates getPos(){
		return pos;
	}
	
    public String getRoom() {
    	return room;
    }
    
    public Room getOccupiedRoom() {
    	return occupiedRoom;
    }
    
    public Cards getCards(){
    	return playersCards;
    }
    
    public Notes getNotes(){
    	return notes;
    }
	
	public void setPos(Coordinates pos) {
		this.pos = pos;
	}
	
    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }
    
    public void setRoom(String room) {
    	this.room = room;
    }
    
    public void setOccupiedRoom(Room room) {
    	occupiedRoom = room;
    }
    
    public int askPlayer(Player player, String question) { 
    	Tokens tokens = new Tokens();
    	Weapons weapons = new Weapons();
    	Rooms rooms = new Rooms();    	
    	
    	String character = null;
    	String weapon = null;
    	String room = null;
    	
    	// Extract the key information from the question
    	for(Token s : tokens) {
    		if(question.toLowerCase().contains(s.getName().toLowerCase())) {
    			character = s.getName();
    			break;
    		}
    	}
    	
    	for(Weapon s : weapons) {
    		if(question.toLowerCase().contains(s.getName().toLowerCase())) {
    			weapon = s.getName();
    			break;
    		}
    	}
    	
    	for(Room s : rooms) {
    		if(question.toLowerCase().contains(s.getName().toLowerCase())) {
    			room = s.getName();
    			break;
    		}
    	}
    	
    	if(character == null || weapon == null || room == null) {
    		return 1; // Error, question did not contain enough info
    	}
    	
    	ArrayList<String> info = new ArrayList<>();
    	
    	// check asked players cards
    	for(Card s : player.getCards()) {
    		if(s.getCardName() == character || s.getCardName() == weapon || s.getCardName() == room) {
    			info.add(s.getCardName());
    		}
    	}
    	
    	if(info.isEmpty()) {
    		JOptionPane.showMessageDialog(null, player.getName() + " has no cards that you are asking for.");
    	}
    	else if(info.size() == 1) {
    		this.notes.recordNote(info.get(0), "X");
    		JOptionPane.showMessageDialog(null, this.name + "'s notes have been updated!");
    	}
    	else if(info.size() > 1) {
    		JOptionPane.showMessageDialog(null, "Pass game over to " + player.getName());
    		if(info.size() == 2) {
    			boolean tryCatch = true;
    			while(tryCatch) {
	    			try {
						int choice = Integer.parseInt(JOptionPane.showInputDialog(null, "Choose between revealing 1) " + info.get(0) + "\n or, 2) " + info.get(1) + "\n"));
						tryCatch = false;
						if(choice < 1 || choice > 2) {
							JOptionPane.showMessageDialog(null, "Enter Valid Number");
							tryCatch = true;
						}
						else {
							this.notes.recordNote(info.get(choice - 1), "X");
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Enter valid number");
					}
    			}
    			
        		JOptionPane.showMessageDialog(null, this.name + "'s notes have been updated!");
    			
    		}
    		else if(info.size() == 3) {
    			boolean tryCatch = true;
    			while(tryCatch) {
	    			try {
						int choice = Integer.parseInt(JOptionPane.showInputDialog(null, "Choose between revealing\n1) " + info.get(0) + "\n2) " + info.get(1) + "\n3) " + info.get(2) + "\n"));
						tryCatch = false;
						if(choice < 1 || choice > 3) {
							JOptionPane.showMessageDialog(null, "Enter Valid Number");
							tryCatch = true;
						}
						else {
							this.notes.recordNote(info.get(choice - 1), "X");
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Enter valid number");
					}
    			}
    			
        		JOptionPane.showMessageDialog(null, this.name + "'s notes have been updated!");
        		
    		}
    	}
    	
		return 0;    	
    }
}
