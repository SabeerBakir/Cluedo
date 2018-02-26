// Kacper Twardowski (16401636), Sabeer Bakir (16333886), Aidan Mac Neill (16349586)

public class Cluedo {

    private final Tokens tokens = new Tokens();
    private final Weapons weapons = new Weapons();
    private final UI ui = new UI(tokens,weapons);
    private final Players players = new Players(ui, tokens);
    private final Mover mover = new Mover(players, ui);
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
        int playerCounter = 0; // keeps track of which players is making moves
        ui.displayString("\nComamnds:\nquit - exit the game\nroll - roll the dice\nmove - move the player\nleave - leave the room\npassage - use the passage\n");
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
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("move") && diceRolled) {
            		ui.displayString("Enter the direction.");
                	command = ui.getCommand();
                	int choice = 4;
                	choice = mover.move(playerCounter, command);
                	if(choice == 0) rolls--;
                	if(choice == 1) ui.displayString("You cannot move here.");
                	if(choice == 3) rolls = 0;
            	}
            	else if(command.replaceAll("[^a-zA-Z]","").toLowerCase().equals("passage") && diceRolled) {
            		if(players.get(playerCounter).getOccupiedRoom()==null) ui.displayString("You are not currently in a room.");
            		else{
            			mover.moveTrapdoor(playerCounter);
            			rolls = 0;
            		}
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
        //game.testUI();
        game.testPlayers();
        System.exit(0);
    }
}