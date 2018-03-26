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
        ui.displayString("\nDice rolled. Player number " + (playerCounter+1) + " will start the game.");
        ui.displayString("\nCommands:\nquit - exit the game\nroll - roll the dice\nmove - move the player\nleave - leave the room\npassage - use the passage\ncards - display your cards\nnotes - display your notes\npass - pass your turn\ncheat - show the envelope\ncommands - display a list of commands\n");
        do {
            Boolean diceRolled = false;
            int rolls = 0;
        	do {
            	ui.displayString("[" + players.get(playerCounter).getName() + "] Enter Command: ");
            	command = ui.getCommand();
            	ui.displayString(command);
            	if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("quit")) System.exit(0);
            	if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("roll") && !diceRolled) {
            		rolls = dice.roll();
            		diceRolled = true;
            		ui.displayString("Your roll: "+rolls);
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("leave") && diceRolled) {
            		if(mover.exitRoom(playerCounter, players.get(playerCounter).getOccupiedRoom()) != 0) {
            			ui.displayString("You are not currently in a room.");
            		}
            		else rolls--;
            		ui.displayString("You have "+ rolls +" moves remaining.");
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("move") && diceRolled) {
            		ui.displayString("Enter the direction.");
                	command = ui.getCommand();
                	int choice = 4;
                	choice = mover.move(playerCounter, command);
                	if(choice == 0) {
                		rolls--;
                		ui.displayString("You have "+ rolls + " moves remaining.");
                	}
                	if(choice == 1) ui.displayString("You cannot move here.");
                	if(choice == 3) rolls = 0;
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("passage") && diceRolled) {
            		if(players.get(playerCounter).getOccupiedRoom()==null) ui.displayString("You are not currently in a room.");
            		else if(mover.moveTrapdoor(playerCounter) == 1) ui.displayString("The room you are in has no trapdoor.");
            		else{
            			mover.moveTrapdoor(playerCounter);
            			rolls = 0;
            		}
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("commands") && diceRolled) {
            		ui.displayString("\nCommands:\nquit - exit the game\nroll - roll the dice\nmove - move the player\nleave - leave the room\npassage - use the passage\ncards - display your cards\nnotes - display your notes\npass - pass your turn\ncheat - show the envelope\n");
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("cards") && diceRolled) {
            		JFrame cardFrame = new JFrame("Your Cards");
            		cardFrame.setSize((122 + 10) * players.get(playerCounter).getCards().getList().size(), 250);
            		cardFrame.setLayout(new GridLayout());
            		cardFrame.setResizable(false);
            		for(Card card : players.get(playerCounter).getCards()){
            			cardFrame.add(card);
            		}
            		cardFrame.setVisible(true);
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("notes") && diceRolled) {
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
            		cheatFrame.setVisible(true);
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
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("pass")) ui.displayString("Pass - Pass your turn.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("cheat")) ui.displayString("Cheat - Show the murder envelope.\n");
            		if(command.replaceAll("[^a-zA-Z]","").toLowerCase().endsWith("commands")) ui.displayString("Commands - Show a list of all commands.\n");
            	}
            	if(!diceRolled) ui.displayString("You must roll first.");
                ui.display();
                if(rolls == 0 && diceRolled) break;
        	}while(true);
        playerCounter = (playerCounter + 1) % players.getPlayerNum(); //moves onto the next player
        } while (!command.equals("quit"));
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
    		cardFrame.setVisible(true);
    	}
    }
}