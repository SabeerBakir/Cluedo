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
}
