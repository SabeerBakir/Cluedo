// Kacper Twardowski (16401636), Sabeer Bakir (16333886), Aidan Mac Neill (16349586)

import java.awt.GridLayout;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Cluedo {

    private final Tokens tokens = new Tokens();
    private final Weapons weapons = new Weapons();
    private final UI ui = new UI(tokens,weapons);
    private final Players players = new Players(ui, tokens);
    private final Mover mover = new Mover(players, ui);
    private final Dice dice = new Dice(6,2);
    private final Rooms rooms = new Rooms();

    private final Cards weaponsCards = new Cards(weapons.toStringArray(), "Cards/Weapons/");
    private final Cards characterCards = new Cards(tokens.toStringArray(), "Cards/Characters/");
    private final Cards roomCards = new Cards(rooms.toStringArray(), "Cards/Rooms/");
    private final Cards envelope = new Cards();
    
    private void startGame() {
    	dealCards();
        String command;
        int playerCounter = players.shufflePlayers(dice); // keeps track of which players is making moves
        int playablePlayers = players.getPlayerNum(); // used to check if there is one player left after too many accusations
        ui.displayString("\nDice rolled. Player number " + (playerCounter+1) + " will start the game.");
		ui.displayString("\nCommands:\nquit - exit the game\nroll - roll the dice\nmove - move the player\nleave - leave the room\npassage - use the passage\ncards - display your cards\nnotes - display your notes\nask - Ask a question.\nlog - Display log of questions and answers.\npass - pass your turn\ncheat - show the envelope\n");
        do {
        	ui.clear(); // clear the infoPanel so other players can't see your info
        	if(playablePlayers == 1){ // if there is 1 player left
            	ui.displayString(players.get(playerCounter).getName() + " has won the game!, Press enter to exit");
    			ui.getCommand();
    			System.exit(0);
           		}
            Boolean diceRolled = false;
            int rolls = 0;
            ui.displayString("Player " + players.get(playerCounter).getName() + "'s turn, type commands for a list of commands.");
            ui.displayString("Type help followed by a command to get information on what the command does.\n");
        	do {
            	ui.displayString("[" + players.get(playerCounter).getName() + "] Enter Command: ");
            	command = ui.getCommand();
            	ui.displayString(command);
            	if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("quit")) System.exit(0);
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("roll") && !diceRolled) {
            		rolls = dice.roll();
            		diceRolled = true;
            		ui.displayString("Your roll: "+rolls);
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("roll") && diceRolled) {
            		ui.displayString("You have already rolled this turn! Your roll: "+rolls);
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("leave") && diceRolled) {
            		if(mover.exitRoom(playerCounter, players.get(playerCounter).getOccupiedRoom()) != 0) {
            			ui.displayString("You are not currently in a room.");
            		}
            		else rolls--;
            		ui.displayString("You have "+ rolls +" moves remaining.");
            		players.get(playerCounter).setAsked(false); // prevent asking multiple questions in a room without leaving
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().startsWith("move") && diceRolled) {
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("l") || command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("left")) {
                    	int choice = 4;
                    	choice = mover.move(playerCounter, "left");
                    	if(choice == 0) {
                    		rolls--;
                    		ui.displayString("You have "+ rolls + " moves remaining.");
                    	}
                    	if(choice == 1) ui.displayString("You cannot move here.");
                    	if(choice == 3) {
                    		rolls = 0;
                    		ui.display();
                    		if(players.get(playerCounter).getRoom().equals("Basement")) {
                    			ui.displayString("You can now accuse a player.");
                    			command = ui.getCommand();
                    			if(command.replaceAll("[^a-zA-Z]", "").toLowerCase().equals("accuse")) {
                    				playablePlayers = accusePlayer(players, playerCounter, ui, command, tokens, weapons, rooms, envelope, rolls, playablePlayers);
                    			}
                    		}
                    		else {
                        		ui.displayString("You can now ask a question.");
                        		command = ui.getCommand();
                        		if(command.replaceAll("[^a-zA-Z]", "").toLowerCase().equals("ask")) {
                        			askQuestion(players, playerCounter, ui, command, tokens, weapons);
                        		}
                    		}
                    	}
            		}
            		else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("r") || command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("right")) {
            			int choice = 4;
                    	choice = mover.move(playerCounter, "right");
                    	if(choice == 0) {
                    		rolls--;
                    		ui.displayString("You have "+ rolls + " moves remaining.");
                    	}
                    	if(choice == 1) ui.displayString("You cannot move here.");
                    	if(choice == 3) {
                    		rolls = 0;
                    		ui.display();
                    		if(players.get(playerCounter).getRoom().equals("Basement")) {
                    			ui.displayString("You can now accuse a player.");
                    			command = ui.getCommand();
                    			if(command.replaceAll("[^a-zA-Z]", "").toLowerCase().equals("accuse")) {
                    				playablePlayers = accusePlayer(players, playerCounter, ui, command, tokens, weapons, rooms, envelope, rolls, playablePlayers);
                    			}
                    		}
                    		else {
                        		ui.displayString("You can now ask a question.");
                        		command = ui.getCommand();
                        		if(command.replaceAll("[^a-zA-Z]", "").toLowerCase().equals("ask")) {
                        			askQuestion(players, playerCounter, ui, command, tokens, weapons);
                        		}
                    		}
                    	}
            		}
            		else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("u") || command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("up")) {
            			int choice = 4;
                    	choice = mover.move(playerCounter, "up");
                    	if(choice == 0) {
                    		rolls--;
                    		ui.displayString("You have "+ rolls + " moves remaining.");
                    	}
                    	if(choice == 1) ui.displayString("You cannot move here.");
                    	if(choice == 3) {
                    		rolls = 0;
                    		ui.display();
                    		if(players.get(playerCounter).getRoom().equals("Basement")) {
                    			ui.displayString("You can now accuse a player.");
                    			command = ui.getCommand();
                    			if(command.replaceAll("[^a-zA-Z]", "").toLowerCase().equals("accuse")) {
                    				playablePlayers = accusePlayer(players, playerCounter, ui, command, tokens, weapons, rooms, envelope, rolls, playablePlayers);
                    			}
                    		}
                    		else {
                        		ui.displayString("You can now ask a question.");
                        		command = ui.getCommand();
                        		if(command.replaceAll("[^a-zA-Z]", "").toLowerCase().equals("ask")) {
                        			askQuestion(players, playerCounter, ui, command, tokens, weapons);
                        		}
                    		}
                    	}
            		}
            		else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("d") || command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("down")) {
            			int choice = 4;
                    	choice = mover.move(playerCounter, "down");
                    	if(choice == 0) {
                    		rolls--;
                    		ui.displayString("You have "+ rolls + " moves remaining.");
                    	}
                    	if(choice == 1) ui.displayString("You cannot move here.");
                    	if(choice == 3) {
                    		rolls = 0;
                    		ui.display();
                    		if(players.get(playerCounter).getRoom().equals("Basement")) {
                    			ui.displayString("You can now accuse a player.");
                    			command = ui.getCommand();
                    			if(command.replaceAll("[^a-zA-Z]", "").toLowerCase().equals("accuse")) {
                    				playablePlayers = accusePlayer(players, playerCounter, ui, command, tokens, weapons, rooms, envelope, rolls, playablePlayers);
                    			}
                    		}
                    		else {
                        		ui.displayString("You can now ask a question.");
                        		command = ui.getCommand();
                        		if(command.replaceAll("[^a-zA-Z]", "").toLowerCase().equals("ask")) {
                        			askQuestion(players, playerCounter, ui, command, tokens, weapons);
                        		}
                    		}
                    	}
            		}
            		else {
            			ui.displayString("Enter the direction.");
                    	command = ui.getCommand();
                    	int choice = 4;
                    	choice = mover.move(playerCounter, command);
                    	if(choice == 0) {
                    		rolls--;
                    		ui.displayString("You have "+ rolls + " moves remaining.");
                    	}
                    	if(choice == 1) ui.displayString("You cannot move here.");
                    	if(choice == 3) {
                    		rolls = 0;
                    		ui.display();
                    		if(players.get(playerCounter).getRoom().equals("Basement")) {
                    			ui.displayString("You can now accuse a player.");
                    			command = ui.getCommand();
                    			if(command.replaceAll("[^a-zA-Z]", "").toLowerCase().equals("accuse")) {
                    				playablePlayers = accusePlayer(players, playerCounter, ui, command, tokens, weapons, rooms, envelope, rolls, playablePlayers);
                    			}
                    		}
                    		else {
                        		ui.displayString("You can now ask a question.");
                        		command = ui.getCommand();
                        		if(command.replaceAll("[^a-zA-Z]", "").toLowerCase().equals("ask")) {
                        			askQuestion(players, playerCounter, ui, command, tokens, weapons);
                        		}
                    		}
                    	}
            		}
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("passage") && diceRolled) {
            		if(players.get(playerCounter).getOccupiedRoom()==null) ui.displayString("You are not currently in a room.");
            		else{
            			if(mover.moveTrapdoor(playerCounter) == 1) {
            				break;
            			}
            			rolls = 0;
            		}
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("commands")) {
            		ui.displayString("\nCommands:\nquit - exit the game\nroll - roll the dice\nmove - move the player\nleave - leave the room\npassage - use the passage\ncards - display your cards\nnotes - display your notes\nask - Ask a question.\nlog - Display log of questions and answers.\npass - pass your turn\ncheat - show the envelope\n");
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("cards")) {
            		JFrame cardFrame = new JFrame("Your Cards");
            		cardFrame.setSize((122 + 10) * players.get(playerCounter).getCards().getList().size(), 250);
            		cardFrame.setLayout(new GridLayout());
            		cardFrame.setResizable(false);
            		for(Card card : players.get(playerCounter).getCards()){
            			cardFrame.add(card);
            		}
            		cardFrame.pack();
            		cardFrame.setLocationRelativeTo(null);
            		cardFrame.setVisible(true);
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("notes")) {
            		ui.displayString(players.get(playerCounter).getNotes().toString());
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("pass") && diceRolled) {
            		ui.displayString("Turn passed.");
            		rolls = 0;
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("cheat") && diceRolled) {
            		JFrame cheatFrame = new JFrame("Envelope");
            		cheatFrame.setSize((122 + 10) * 3, 250);
            		cheatFrame.setLayout(new GridLayout());
            		cheatFrame.setResizable(false);
            		for(Card card : envelope) {
            			cheatFrame.add(card);
            		}
            		cheatFrame.pack();
            		cheatFrame.setLocationRelativeTo(null);
            		cheatFrame.setVisible(true);
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("ask") && diceRolled) {
            		askQuestion(players, playerCounter, ui, command, tokens, weapons);
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("accuse") && diceRolled) {
            		accusePlayer(players, playerCounter, ui, command, tokens, weapons, rooms, envelope, rolls, playablePlayers);
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("log") && diceRolled) {
            		ui.displayString(players.get(playerCounter).getLog().toString());
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().startsWith("help")) {
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("help")) ui.displayString("Help - Show help for commands.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("quit")) ui.displayString("Quit - Ends the game.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("roll")) ui.displayString("Roll - Roll the dice.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("move")) ui.displayString("Move - Move your token.\nu for up\nd for down\nl for left\nr for right\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("leave")) ui.displayString("Leave - Leave the room.\nDoes nothing if not in a room.");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("passage")) ui.displayString("Passage - Use the passage in the room.\nDoes nothing if not in a room.");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("cards")) ui.displayString("Cards - Show your current cards.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("notes")) ui.displayString("Notes - Show known card information.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("ask")) ui.displayString("Ask - Ask a question.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("log")) ui.displayString("Log - Display log of questions and answers.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("pass")) ui.displayString("Pass - Pass your turn.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("cheat")) ui.displayString("Cheat - Show the murder envelope.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("commands")) ui.displayString("Commands - Show a list of all commands.\n");
            	}
            	if(!diceRolled) ui.displayString("You must roll first.");
                ui.display();
                if(rolls == 0 && diceRolled) {
                	ui.displayString("\nYour turn is over, press Enter to finish.");
                	ui.getCommand();
                	break;
                }
        	}while(true);
        playerCounter = (playerCounter + 1) % players.getPlayerNum(); //moves onto the next player
        if(!players.get(playerCounter).isPlaying()){
        	 playerCounter = (playerCounter + 1) % players.getPlayerNum();
        	}
        	
        } while (!command.equals("quit"));
    }
    
    private static int accusePlayer(Players players, int playerCounter, UI ui, String command, Tokens tokens, Weapons weapons, Rooms rooms, Cards envelope, int rolls, int playablePlayers) {
    	if(players.get(playerCounter).getOccupiedRoom().getName() != "Basement"){
			ui.displayString("You are currently not in the basement.");
		}
		else{
    		String suspectQuestion = null;
    		String weaponQuestion = null;
    		String roomQuestion = null;
    		
    		ui.displayString("You are accusing: ");
    		ui.displayString("Suspect: ");
    		command = ui.getCommand();
    		ui.displayString(command);
    		
    		while(suspectQuestion == null){
    			if(tokens.get(command) == null){
    				suspectQuestion = null;
    			}
    			else{
    				suspectQuestion = tokens.get(command).toString();
    			}
    			if(suspectQuestion == null){
    				ui.displayString("Enter Valid Suspect: ");
            		command = ui.getCommand();
            		ui.displayString(command);
    			}
    		}
    		
    		ui.displayString("Weapon: ");
    		command = ui.getCommand();
    		ui.displayString(command);
    		while(weaponQuestion == null){
    			if(weapons.get(command) == null){
    				weaponQuestion = null;
    			}
    			else{
    				weaponQuestion = weapons.get(command).toString();
    			}
    			if(weaponQuestion == null){
    				ui.displayString("Enter Valid Weapon: ");
            		command = ui.getCommand();
            		ui.displayString(command);
    			}
    		}
    		
    		ui.displayString("Room: ");
    		command = ui.getCommand();
    		ui.displayString(command);
    		while(roomQuestion == null){
    			if(rooms.get(command) == null){
    				roomQuestion = null;
    			}
    			else{
    				roomQuestion = rooms.get(command).toString();
    			}
    			if(roomQuestion == null){
    				ui.displayString("Enter Valid Room: ");
            		command = ui.getCommand();
            		ui.displayString(command);
    			}
    		}
    		
    		
    		// WIN CONDITION
    		if(suspectQuestion == envelope.getList().get(1).getCardName() && weaponQuestion ==  envelope.getList().get(0).getCardName() && roomQuestion == envelope.getList().get(2).getCardName()){
    			ui.displayString("Congratulations, " + players.get(playerCounter).getName() + " has won the game! Press enter to exit.");
    			ui.getCommand();
    			System.exit(0);
    		}
    		else{
    			ui.displayString(players.get(playerCounter).getName() + " made an incorrect accusation and is unable to ask or move or roll or accuse again");
    			players.get(playerCounter).setPlaying(false);
    			rolls = 0;
    			playablePlayers--;	   
    			
    			players.get(playerCounter).setOccupiedRoom(null); // move the player back to their starting location if they accuse incorrectly.
    			players.get(playerCounter).setRoom(null);
    			
    			if(players.get(playerCounter).getCharacter().getName().equals("Plum")) {
    				Coordinates start = new Coordinates(23, 19);
    				players.get(playerCounter).setPos(start);
    				players.get(playerCounter).getCharacter().setPosition(start);
    			}
    			else if(players.get(playerCounter).getCharacter().getName().equals("White")) {
    				Coordinates start = new Coordinates(9, 0);
    				players.get(playerCounter).setPos(start);
    				players.get(playerCounter).getCharacter().setPosition(start);
    			}
    			else if(players.get(playerCounter).getCharacter().getName().equals("Scarlet")) {
    				Coordinates start = new Coordinates(7, 24);
    				players.get(playerCounter).setPos(start);
    				players.get(playerCounter).getCharacter().setPosition(start);
    			}
    			else if(players.get(playerCounter).getCharacter().getName().equals("Green")) {
    				Coordinates start = new Coordinates(14, 0);
    				players.get(playerCounter).setPos(start);
    				players.get(playerCounter).getCharacter().setPosition(start);
    			}
    			else if(players.get(playerCounter).getCharacter().getName().equals("Mustard")) {
    				Coordinates start = new Coordinates(0, 17);
    				players.get(playerCounter).setPos(start);
    				players.get(playerCounter).getCharacter().setPosition(start);
    			}
    			else if(players.get(playerCounter).getCharacter().getName().equals("Peacock")) {
    				Coordinates start = new Coordinates(23, 6);
    				players.get(playerCounter).setPos(start);
    				players.get(playerCounter).getCharacter().setPosition(start);
    			}
    			return playablePlayers;
    		}
		}
		return playablePlayers;
    }
    
    private static void askQuestion(Players players, int playerCounter, UI ui, String command, Tokens tokens, Weapons weapons) {
    	if(players.get(playerCounter).getOccupiedRoom() == null){
			ui.displayString("You are currently not in a room.");
		}
    	else if(players.get(playerCounter).asked() == true) {
    		ui.displayString("You can't ask another question in this room, you must leave first.");
    	}
		else{
			players.get(playerCounter).setAsked(true);
    		int askedPlayer = Math.abs((playerCounter - 1) % players.getPlayerNum()); // player to the left
    		int i = 0; // counter for how many players asked
    		String suspectQuestion = null;
    		String weaponQuestion = null;
    		String roomQuestion = players.get(playerCounter).getRoom();
    		
    		Coordinates weaponInRoom = new Coordinates(players.get(playerCounter).getCharacter().getPosition().getCol()-2, players.get(playerCounter).getCharacter().getPosition().getRow()); // Find positions to place the accused tokens
    		Coordinates suspectInRoom = new Coordinates(players.get(playerCounter).getCharacter().getPosition().getCol()-2, players.get(playerCounter).getCharacter().getPosition().getRow()-1);
    		
    		Coordinates tempPosW = null;
    		Coordinates tempPosS = null;
    		
    		ui.displayString("You are asking a question");
    		ui.displayString("Suspect: ");
    		command = ui.getCommand();
    		ui.displayString(command);
    		
    		while(suspectQuestion == null){
    			if(tokens.get(command) == null){
    				suspectQuestion = null;
    			}
    			else{
    				suspectQuestion = tokens.get(command).toString();
    			}
    			if(suspectQuestion == null){
    				ui.displayString("Enter Valid Suspect: ");
            		command = ui.getCommand();
            		ui.displayString(command);
    			}
    		}
			tempPosS = tokens.get(suspectQuestion).getPosition(); // hold the token's location and temporarily move them
			tokens.get(suspectQuestion).setPosition(suspectInRoom);
    		
    		ui.displayString("Weapon: ");
    		command = ui.getCommand();
    		ui.displayString(command);
    		while(weaponQuestion == null){
    			if(weapons.get(command) == null){
    				weaponQuestion = null;
    			}
    			else{
    				weaponQuestion = weapons.get(command).toString();
    			}
    			if(weaponQuestion == null){
    				ui.displayString("Enter Valid Weapon: ");
            		command = ui.getCommand();
            		ui.displayString(command);
    			}	
    		}
    		tempPosW = weapons.get(weaponQuestion).getPosition();
    		weapons.get(weaponQuestion).setPosition(weaponInRoom);
    		
    		ui.display(); // repaint the board to move the tokens
    		ui.clear(); // clear the infoPanel to prevent other players from seeing your information
    		ui.displayString("Player " + players.get(playerCounter).getName() + "'s turn, type commands for a list of commands.\n");
    		
    		while(i < players.getPlayerNum() - 1) {
    			for(Player player : players){
    				player.getLog().addQuestion(suspectQuestion, weaponQuestion, roomQuestion);
    			}
    			if(players.get(playerCounter).askPlayer(players.get(askedPlayer), suspectQuestion, weaponQuestion, roomQuestion, players.get(playerCounter).getLog(), players.get(askedPlayer).getLog()) == 0){
    				break;
    			}
    			i++;
    			askedPlayer = Math.abs((askedPlayer - 1) % players.getPlayerNum()); // move to the next player
    		}
    		if(i == (players.getPlayerNum())) { // If no players have a card that was asked
    			ui.displayString("No player has a card that you have asked for.");
    			for(Player player : players){
    				player.getLog().add("No player revealed cards, " + suspectQuestion + ", " + weaponQuestion + ", " + roomQuestion);
    			}
    		}
    		weapons.get(weaponQuestion).setPosition(tempPosW); // move the tokens back to where they should be
    		tokens.get(suspectQuestion).setPosition(tempPosS);
		}
    }

    public static void main(String[] args) {
        Cluedo game = new Cluedo();
        game.startGame();
        System.exit(0);
    }
    
    public void dealCards(){
    	// Shuffle cards
    	Collections.shuffle(weaponsCards.getList());
    	Collections.shuffle(characterCards.getList());
    	Collections.shuffle(roomCards.getList());
    	
    	// Put cards in envelope
    	envelope.getList().add(weaponsCards.getList().remove(0));
    	envelope.getList().add(characterCards.getList().remove(0));
    	envelope.getList().add(roomCards.getList().remove(0));
    	
    	// Put the cards into one deck
    	Cards deck = new Cards();
    	for(Card card : weaponsCards){
    		deck.getList().add(card);
    	}
    	for(Card card : characterCards){
    		deck.getList().add(card);
    	}
    	for(Card card : roomCards){
    		deck.getList().add(card);
    	}
    	
    	// Re-shuffle the deck
    	Collections.shuffle(deck.getList());
    	
    	// Deal cards to each player and mark it on the notes
    	while(deck.getList().size() > players.getPlayerNum()){
    		for(int i = 0; i < players.getPlayerNum(); i++){
    			Card temp = deck.getList().remove(0);
    			players.get(i).getCards().getList().add(temp);
    			players.get(i).getNotes().recordNote(temp.getCardName(), "X");
    		}
    	}
    	if(deck.getList().size() != 0){ // If there are any leftover cards and add it to every players notes
    		JOptionPane.showMessageDialog(null, "There are " + deck.getList().size() + " Cards leftover");
    		JFrame cardFrame = new JFrame("Leftover Cards");
    		cardFrame.setSize((122 + 10) * deck.getList().size(), 250);
    		cardFrame.setLayout(new GridLayout());
    		cardFrame.setResizable(false);
    		for(Card card : deck){
    			cardFrame.add(card);
    			for(Player player : players){
    				player.getNotes().recordNote(card.getCardName(), "A");
    			}
    		}
    		cardFrame.pack();
    		cardFrame.setLocationRelativeTo(null);
    		cardFrame.setVisible(true);
    	}
    }
}