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
	private Logbook log;			// Player Logbook
	private boolean isPlaying;		// If the player is playing

	public Player(int id, String name, Token character, Coordinates pos) {
		this.id = id;
		this.name = name;
		this.character = character;
		this.pos = pos;
		room = null;
		occupiedRoom = null;
		playersCards = new Cards();
		notes = new Notes();
		log = new Logbook();
		isPlaying = true;
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
    
	public Logbook getLog() {
		return log;
	}
	
	public boolean isPlaying() {
		return isPlaying;
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
    
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
    
    public int askPlayer(Player player, String suspect, String weapon, String room, Logbook askingLog, Logbook askedLog) {
    	

    	ArrayList<String> info = new ArrayList<>();
    	
    	// check asked players cards
    	for(Card s : player.getCards()) {
    		if(s.getCardName() == suspect || s.getCardName() == weapon || s.getCardName() == room) {
    			info.add(s.getCardName());
    		}
    	}
    	
    	if(info.isEmpty()) {
    		JOptionPane.showMessageDialog(null, player.getName() + " has no cards that you are asking for.");
    		return 1;
    	}
    	else if(info.size() == 1) {
    		this.notes.recordNote(info.get(0), "X");
    		JOptionPane.showMessageDialog(null, this.name + "'s notes have been updated!");
    		this.getLog().addAnswer(player.getName(), info.get(0));
    		player.getLog().addReveal(this.name, info.get(0));
    		
    		return 0;
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
			        		this.getLog().addAnswer(player.getName(), info.get(choice - 1));
			        		player.getLog().addReveal(this.name, info.get(choice - 1));
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Enter valid number");
					}
    			}
    			
        		JOptionPane.showMessageDialog(null, this.name + "'s notes have been updated!");
        		
        		return 0;
    			
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
			        		this.getLog().addAnswer(player.getName(), info.get(choice - 1));
			        		player.getLog().addReveal(this.name, info.get(choice - 1));
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Enter valid number");
					}
    			}
    			
        		JOptionPane.showMessageDialog(null, this.name + "'s notes have been updated!");
        		
        		return 0;
        		
    		}
    	}
		return 1;    	
    }
}
