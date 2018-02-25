
public class Rooms {
	String rooms[] = {"Kitchen", "Ball Room", "Conservatory", "Dining Room", "Billiard Room", "Library", "Lounge", "Hall", "Study"};
	
	public Rooms() {
		Room kitchen = new Room(rooms[0], new Door(new Coordinates(6, 4), new Coordinates(7, 4), 1), null, null, null, true);
		
		Room ballroom = new Room(rooms[1], new Door(new Coordinates(5, 8), new Coordinates(5, 7), 1),
				new Door(new Coordinates(7, 9), new Coordinates(8, 9), 2),
				new Door(new Coordinates(7, 14), new Coordinates(8, 14), 3),
				new Door(new Coordinates(5, 15), new Coordinates(5, 16), 4), false);
		
		Room conservatory = new Room(rooms[2], new Door(new Coordinates(4, 18), new Coordinates(5, 18), 1), null, null, null, true);
		
		Room diningroom = new Room(rooms[3], new Door(new Coordinates(15, 6), new Coordinates(16, 6), 1),
				new Door(new Coordinates(12, 7), new Coordinates(12, 8), 2), null, null, false);
		
		Room billiardroom = new Room(rooms[4], new Door(new Coordinates(9, 18), new Coordinates(9, 17), 1),
				new Door(new Coordinates(12, 22), new Coordinates(13, 22), 2), null, null, false);
		
		Room library = new Room(rooms[5], new Door(new Coordinates(16, 17), new Coordinates(16, 16), 1),
				new Door(new Coordinates(14, 20), new Coordinates(13, 20), 2), null, null, false);
		
		Room lounge = new Room(rooms[6], new Door(new Coordinates(19, 6), new Coordinates(18, 6), 1), null, null, null, true);
		
		Room hall = new Room(rooms[7], new Door(new Coordinates(18, 11), new Coordinates(17, 11), 1),
				new Door(new Coordinates(18, 12), new Coordinates(17, 12), 2),
				new Door(new Coordinates(20, 14), new Coordinates(20, 15), 3), null, false);
		Room study = new Room(rooms[8], new Door(new Coordinates(21, 17), new Coordinates(20, 17), 1), null, null, null, true);
	}
}
