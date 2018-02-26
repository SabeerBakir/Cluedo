import java.util.ArrayList;
import java.util.Iterator;

public class Rooms implements Iterable<Room>, Iterator<Room>{
	
	private Iterator<Room> iterator;
	String roomsArr[] = {"Kitchen", "Ball Room", "Conservatory", "Dining Room", "Billiard Room", "Library", "Lounge", "Hall", "Study"};
	private final ArrayList<Room> rooms = new ArrayList<>();
	
	public Rooms() {
		rooms.add(new Room(0, roomsArr[0], new Door(new Coordinates(6, 4), new Coordinates(7, 4), 1), null, null, null, new Coordinates(3, 2), true));
		
		rooms.add(new Room(1, roomsArr[1], new Door(new Coordinates(5, 8), new Coordinates(5, 7), 1),
				new Door(new Coordinates(7, 9), new Coordinates(8, 9), 2),
				new Door(new Coordinates(7, 14), new Coordinates(8, 14), 3),
				new Door(new Coordinates(5, 15), new Coordinates(5, 16), 4), new Coordinates(4, 11), false));
		
		rooms.add(new Room(2, roomsArr[2], new Door(new Coordinates(4, 18), new Coordinates(5, 18), 1), null, null, null, new Coordinates(3, 20),true));
		
		rooms.add(new Room(3, roomsArr[3], new Door(new Coordinates(15, 6), new Coordinates(16, 6), 1),
				new Door(new Coordinates(12, 7), new Coordinates(12, 8), 2), null, null, new Coordinates(12, 3), false));
		
		rooms.add(new Room(4, roomsArr[4], new Door(new Coordinates(9, 18), new Coordinates(9, 17), 1),
				new Door(new Coordinates(12, 22), new Coordinates(13, 22), 2), null, null, new Coordinates(10, 20), false));
		
		rooms.add(new Room(5, roomsArr[5], new Door(new Coordinates(16, 17), new Coordinates(16, 16), 1),
				new Door(new Coordinates(14, 20), new Coordinates(13, 20), 2), null, null, new Coordinates(16, 20), false));
		
		rooms.add(new Room(6, roomsArr[6], new Door(new Coordinates(19, 6), new Coordinates(18, 6), 1), null, null, null, new Coordinates(2, 21), true));
		
		rooms.add(new Room(7, roomsArr[7], new Door(new Coordinates(18, 11), new Coordinates(17, 11), 1),
				new Door(new Coordinates(18, 12), new Coordinates(17, 12), 2),
				new Door(new Coordinates(20, 14), new Coordinates(20, 15), 3), null, new Coordinates(21, 11), false));
		
		rooms.add(new Room(8, roomsArr[8], new Door(new Coordinates(21, 17), new Coordinates(20, 17), 1), null, null, null,new Coordinates(22, 19), true));
	}
	
	public Room get(int id) {
        for (Room room : rooms) {
            if (room.getID() == id) {
                return room;
            }
        }
        return null;
    }
	
	public Iterator<Room> iterator() {
		iterator = rooms.iterator();
		return iterator;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}


	public Room next() {
		return iterator.next();
	}

}
