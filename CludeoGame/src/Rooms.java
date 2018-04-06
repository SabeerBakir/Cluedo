// Kacper Twardowski (16401636), Sabeer Bakir (16333886), Aidan Mac Neill (16349586)

import java.util.ArrayList;
import java.util.Iterator;

public class Rooms implements Iterable<Room>, Iterator<Room>{
	
	private Iterator<Room> iterator;
	String roomsArr[] = {"Kitchen", "Ball Room", "Conservatory", "Dining Room", "Billiard Room", "Library", "Lounge", "Hall", "Study", "Basement"};
	private ArrayList<Room> rooms = new ArrayList<>();
	
	public Rooms() { // create an ArrayList of rooms, containing all relevant data
		rooms.add(new Room(0, 1, roomsArr[0], new Door(new Coordinates(4, 6), new Coordinates(4, 7), 1), null, null, null, new Coordinates(2, 3), true));
		
		rooms.add(new Room(1, 4, roomsArr[1], new Door(new Coordinates(8, 5), new Coordinates(7, 5), 1),
				new Door(new Coordinates(9, 7), new Coordinates(9, 8), 2),
				new Door(new Coordinates(14, 7), new Coordinates(14, 8), 3),
				new Door(new Coordinates(15, 5), new Coordinates(16, 5), 4), new Coordinates(11, 4), false));
		
		rooms.add(new Room(2, 1, roomsArr[2], new Door(new Coordinates(18, 4), new Coordinates(18, 5), 1), null, null, null, new Coordinates(20, 3),true));
		
		rooms.add(new Room(3, 2, roomsArr[3], new Door(new Coordinates(6, 15), new Coordinates(6, 16), 1),
				new Door(new Coordinates(7, 12), new Coordinates(8, 12), 2), null, null, new Coordinates(3, 12), false));
		
		rooms.add(new Room(4, 2, roomsArr[4], new Door(new Coordinates(18, 9), new Coordinates(17, 9), 1),
				new Door(new Coordinates(22, 12), new Coordinates(22, 13), 2), null, null, new Coordinates(20, 10), false));
		
		rooms.add(new Room(5, 2, roomsArr[5], new Door(new Coordinates(17, 16), new Coordinates(16, 16), 1),
				new Door(new Coordinates(20, 14), new Coordinates(20, 13), 2), null, null, new Coordinates(20, 16), false));
		
		rooms.add(new Room(6, 1, roomsArr[6], new Door(new Coordinates(6, 19), new Coordinates(6, 18), 1), null, null, null, new Coordinates(21, 2), true));
		
		rooms.add(new Room(7, 3, roomsArr[7], new Door(new Coordinates(11, 18), new Coordinates(11, 17), 1),
				new Door(new Coordinates(12, 18), new Coordinates(12, 17), 2),
				new Door(new Coordinates(14, 20), new Coordinates(15, 20), 3), null, new Coordinates(11, 21), false));
		
		rooms.add(new Room(8, 1, roomsArr[8], new Door(new Coordinates(17, 21), new Coordinates(17, 20), 1), null, null, null, new Coordinates(19, 22), true));
		
		rooms.add(new Room(9, 1, roomsArr[9], new Door(new Coordinates(12, 16), new Coordinates(12, 17), 1), null, null, null, new Coordinates(12, 14), false));
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
	
    public String[] toStringArray(){ // returns an array of strings of the array list
    	String[] names = new String[rooms.size()-1];
    	int i = 0;
    	for(Room room : rooms) {
    		if(room.getID()!=9) {
        		names[i] = room.getName();
        		i++;
    		}
    	}
    	
		return names;   	
    }
    
    public int size() {
    	return rooms.size();
    }
}
