public class Cluedo {

    private final Tokens tokens = new Tokens();
    private final Weapons weapons = new Weapons();
    private final UI ui = new UI(tokens,weapons);
    private final Players players = new Players(ui, tokens);
    private final Mover mover = new Mover(players);
    private final Dice dice = new Dice(6,2);

    private void testUI() {
        String command;
        Token white = tokens.get("White");
        Weapon dagger = weapons.get("Dagger");
        do {
            command = ui.getCommand();
            ui.displayString(command);
            white.moveBy(new Coordinates(0,+1));
            dagger.moveBy(new Coordinates(+1,0));
            ui.display();
        } while (!command.equals("quit"));
    }
    
    private void testPlayers() {
        String command;
        Boolean diceRolled = false;
        int rolls = 0;
        int playerCounter = 0; // keeps track of which players is making moves
        do {
        	ui.displayString("[" + players.get(playerCounter).getName() + "] Enter Command: ");
        	command = ui.getCommand();
        	ui.displayString(command);
        	if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("roll") && !diceRolled) {
        		rolls = dice.roll();
        		diceRolled = true;
        	}
        	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("leave")) {
        		if(mover.exitRoom(playerCounter, players.get(playerCounter).getOccupiedRoom()) != 0) {
        			ui.displayString("You are not currently in a room.");
        		}
        	}
        	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("move")) {
            	mover.move(playerCounter, command);
        	}
        	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("passage")) {
        		if(players.get(playerCounter).getOccupiedRoom()==null) ui.displayString("You are not currently in a room.");
        		else mover.moveTrapdoor(playerCounter);
        	}
            ui.display();
            playerCounter = (playerCounter + 1) % players.getPlayerNum(); //moves onto the next player
        } while (!command.equals("quit"));
    }

    public static void main(String[] args) {
        Cluedo game = new Cluedo();
        //game.testUI();
        game.testPlayers();
        System.exit(0);
    }
}