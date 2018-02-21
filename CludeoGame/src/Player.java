
public class Player {

	int id;				// Player ID
	String name;		// Player name (not the same as token name)
	Token character;	// Token linked to the character		
	
	public Player(int id, String name, Token character) {
		this.id = id;
		this.name = name;
		this.character = character;
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
	
    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }
}
