import java.util.ArrayList;
import java.util.Iterator;

public class Players implements Iterable<Player>, Iterator<Player> {

    private final ArrayList<Player> players = new ArrayList<>();
    private Iterator<Player> iterator;
    private int playerNum = 0; // holds the amount of players playing the game
    
    public Players(UI ui, Tokens tokens) {
    	String playerName;	// holds the name of the player
    	Token characterName = null; 	// holds the token for the character
    	boolean tryCatch = true;	// this is for repeating try catch blocks
    	
    	while(tryCatch){
	    	try {
				ui.displayString("Enter number of players: ");
				playerNum = Integer.parseInt(ui.getCommand());
				ui.displayString(String.valueOf(playerNum));
				
				if(playerNum < 2 || playerNum > 6){
					throw new InvalidPlayerAmountException();
				}
				
				tryCatch = false;
			} catch (NumberFormatException e) {
				ui.displayString("Please enter valid number");
			} catch (InvalidPlayerAmountException e){
				ui.displayString("Minimum 2 Players and Maximum 6 Players");
			}
    	}
    	
    	for(int i = 0; i < playerNum; i++) {
    		ui.displayString("Enter your name: ");
    		playerName = ui.getCommand();
    		ui.displayString(playerName);
    		
    		tryCatch = true; // set as true for the beginning
			while (tryCatch) {
				try {
					ui.displayString("Enter character name: ");
					characterName = tokens.get(ui.getCommand());
					if (characterName == null) {
						throw new InvalidCharacterName();
					}
					tryCatch = false;
				} catch (InvalidCharacterName e) {
					ui.displayString("Please enter a valid character name");
				} 
			}
			ui.displayString(characterName.toString());
    		
    		players.add(new Player(i, playerName, characterName));
    		
    		if(i != playerNum - 1) {
    			ui.displayString("\n------- NEXT PLAYER -------\n");
    		}
    	}
    }
    
    public Player get(String name) {
        for (Player player : players) {
            if (player.hasName(name)) {
                return player;
            }
        }
        return null;
    }
    
    public Player get(int id) {
        for (Player player : players) {
            if (player.id == id) {
                return player;
            }
        }
        return null;
    }
    
    public int getPlayerNum(){
    	return playerNum;
    }
	
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Player next() {
		return iterator.next();
	}

	@Override
	public Iterator<Player> iterator() {
		iterator = players.iterator();
		return iterator;
	}

}
