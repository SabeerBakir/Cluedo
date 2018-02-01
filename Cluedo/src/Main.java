import java.util.Random;

public class Main {
	
	public String[] suspects = {"Plum", "White", "Scarlet", "Green", "Mustard", "Peacock"};
	public String[] weapons = {"Rope", "Dagger", "Wrench", "Pistol", "Candlestick", "Lead Pipe"};
	public String[] rooms = {"Courtyard", "Game Room", "Study", "Dining Room", "Garage", "Living Room", "Kitchen", "Bedroom", "Bathroom"};
	
	public int[][] spawnLocations = new int[][] {
		{0, 9},
		{0, 14},
		{6, 23},
		{17, 0},
		{19,23},
		{24,7}
	};
	
	// Killer cards
	Random rand = new Random();
	String killerName = suspects[rand.nextInt(suspects.length)];
	String killerWeapon = weapons[rand.nextInt(weapons.length)];
	String killerRoom = rooms[rand.nextInt(rooms.length)];
	
	public static void main(String[] args) {
		System.out.println("Boosted Programmers");
	}
}
