// Kacper Twardowski (16401636), Sabeer Bakir (16333886), Aidan Mac Neill (16349586)

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
				ui.displayString("Please enter a valid number.");
			} catch (InvalidPlayerAmountException e){
				ui.displayString("Minimum 2 Players and Maximum 6 Players.");
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
					for(Player player : players){
						if(characterName == player.getCharacter()){
							throw new DuplicateCharacter();
						}
					}
					tryCatch = false;
				} catch (InvalidCharacterName e) {
					ui.displayString("Please enter a valid character name; please try again.");
				} catch (DuplicateCharacter e){
					ui.displayString("Character has already been chosen; please try again.");
				}
			}
			
			ui.displayString(characterName.toString());
    		
    		players.add(new Player(i, playerName, characterName, characterName.getPosition()));
    		
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
            if (player.getID() == id) {
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
	
	public int shufflePlayers(Dice dice) {
		
		
		ArrayList<Player> reroll = new ArrayList<>();	// list of players that need to be rerolled, if size == 1 no players need to be rerolled
		
		int highestRoll = 0;
		int index = 0;
		int[] rolls = new int[playerNum];
		
		for(Player player : players) {	// initial rolls for all players
			rolls[index] = dice.roll();
			if(rolls[index] > highestRoll) {	// find the highest roll
				reroll.add(0, player);
				highestRoll = rolls[index];
			}
			index++;
			
		}
		
		for(int i = 0; i < playerNum; i++) {	// add any other players with the highest roll
			if(rolls[i] == highestRoll && !reroll.contains(players.get(i))) reroll.add(players.get(i));
		}		
		
		int roll;
		while(reroll.size()!=1) {	// roll for the players with the highest roll until only one highest roll remains
			highestRoll = 0;
			roll = 0;
			List<Player> toRemove = new ArrayList<>();
			for(Player player : reroll) {
				roll = dice.roll();
				if(roll >= highestRoll) {
					highestRoll = roll;
				}
				else {
					toRemove.add(player);
				}
			}
			reroll.removeAll(toRemove);
		}

		return reroll.get(0).getID();
		
	}

}
