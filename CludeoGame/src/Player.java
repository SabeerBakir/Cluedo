
public class Player {

	private int id;					// Player ID
	private String name;			// Player name (not the same as token name)
	private Token character;		// Token linked to the character
	private Coordinates pos;		// Coordinates for this player's character
	private String room;			// Name of the room the player is in
	private Room occupiedRoom;
	
	public Player(int id, String name, Token character, Coordinates pos) {
		this.id = id;
		this.name = name;
		this.character = character;
		this.pos = pos;
		room = null;
		occupiedRoom = null;
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
