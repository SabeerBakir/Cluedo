
public class Player {

	private int id;				// Player ID
	private String name;			// Player name (not the same as token name)
	private Token character;		// Token linked to the character
	private Coordinates pos;		// Coordinates for this player's character
	
	public Player(int id, String name, Token character, Coordinates pos) {
		this.id = id;
		this.name = name;
		this.character = character;
		this.pos = pos;
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
	
    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }
}
