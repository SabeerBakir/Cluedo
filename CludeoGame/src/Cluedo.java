public class Cluedo {

    private final Tokens tokens = new Tokens();
    private final Weapons weapons = new Weapons();
    private final UI ui = new UI(tokens,weapons);
    private final Players players = new Players(ui, tokens);

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
        do {
        	ui.displayString("Enter number of players: ");
        	command = ui.getCommand();
        	
        	
        	ui.displayString(command);
            
            ui.display();
        } while (!command.equals("quit"));
    }

    public static void main(String[] args) {
        Cluedo game = new Cluedo();
        game.testUI();
        System.exit(0);
    }
}
