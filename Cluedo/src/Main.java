import java.util.Random;

public class Main {
	
	static public int[][] spawnLocations = new int[][] {
	//	{X, Y} X-axis and Y-axis for spawns
		{9, 0},
		{14, 0},
		{23, 6},
		{0, 17},
		{23,19},
		{7,24}
	};
	
	static String[] suspects = {"Plum", "White", "Scarlet", "Green", "Mustard", "Peacock"};
	static String[] weapons = {"Rope", "Dagger", "Wrench", "Pistol", "Candlestick", "Lead Pipe"};
	static String[] rooms = {"Courtyard", "Game Room", "Study", "Dining Room", "Garage", "Living Room", "Kitchen", "Bedroom", "Bathroom"};
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Player[] players = new Player[6];
		createPlayers(players);
		UI ui = new UI(players);
		
		
		String[] suspects = {"Plum", "White", "Scarlet", "Green", "Mustard", "Peacock"};
		String[] weapons = {"Rope", "Dagger", "Wrench", "Pistol", "Candlestick", "Lead Pipe"};
		String[] rooms = {"Courtyard", "Game Room", "Study", "Dining Room", "Garage", "Living Room", "Kitchen", "Bedroom", "Bathroom"};
		
		// Killer cards
		Random rand = new Random();
		String killerName = suspects[rand.nextInt(suspects.length)];
		String killerWeapon = weapons[rand.nextInt(weapons.length)];
		String killerRoom = rooms[rand.nextInt(rooms.length)];
		
		
	
		ui.getMainFrame();
		System.out.println("Boosted Programmers");
		
	}
	
	static void createPlayers(Player players[]){
		
		for(int i = 0; i < players.length; i++){
			players[i] = new Player(i, spawnLocations[i][0], spawnLocations[i][1], suspects[i]);
			System.out.println(players[i].getPosX() + " " + players[i].getPosY());
		}
	}
}
