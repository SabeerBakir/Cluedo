import java.util.ArrayList;
import java.util.Iterator;

public class Players implements Iterable<Player>, Iterator<Player> {

    private final ArrayList<Player> players = new ArrayList<>();
    private Iterator<Player> iterator;
    
    public Players(UI ui, Tokens tokens) {
    	String num;
    	ui.displayString("Enter number of players: ");
    	num = ui.getCommand();
    	ui.displayString(num);
    	
    	String playerName;
    	Token characterName;
    	int playerNum = Integer.parseInt(num);
    	
    	for(int i = 0; i < playerNum; i++) {
    		ui.displayString("Enter your name: ");
    		playerName = ui.getCommand();
    		ui.displayString(playerName);
    		
    		ui.displayString("Enter character name: ");
    		characterName = tokens.get(ui.getCommand());
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
