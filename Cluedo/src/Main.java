import java.util.Random;

public class Main {
	
	static public int[][] spawnLocations = new int[][] {
		{0, 9},
		{0, 14},
		{6, 23},
		{17, 0},
		{19,23},
		{24,7}
	};
	
	public static void main(String[] args) {
		
		String[] suspects = {"Plum", "White", "Scarlet", "Green", "Mustard", "Peacock"};
		String[] weapons = {"Rope", "Dagger", "Wrench", "Pistol", "Candlestick", "Lead Pipe"};
		String[] rooms = {"Courtyard", "Game Room", "Study", "Dining Room", "Garage", "Living Room", "Kitchen", "Bedroom", "Bathroom"};
		
		// Killer cards
		Random rand = new Random();
		String killerName = suspects[rand.nextInt(suspects.length)];
		String killerWeapon = weapons[rand.nextInt(weapons.length)];
		String killerRoom = rooms[rand.nextInt(rooms.length)];
		
		Player[] players = new Player[6];
		createPlayers(players);
		System.out.println("Boosted Programmers");
		
		UI test = new UI();
		
	}
	
	static void createPlayers(Player players[]){
		Random rand = new Random();
		
		for(int i = 0; i < players.length; i++){
			players[i] = new Player(i, spawnLocations[i][0], spawnLocations[i][1]);
			System.out.println(players[i].getPosX() + " " + players[i].getPosY());
		}
	}
}
