// Kacper Twardowski (16401636), Sabeer Bakir (16333886), Aidan Mac Neill (16349586)

public class Room {
	
	private int id;
	private String name;
	private Door door1;
	private Door door2;
	private Door door3;
	private Door door4;
	private boolean hasTrapdoor;
	private Coordinates centre;
	private int numRooms;
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean hasTrapdoor() {
		return hasTrapdoor;
	}
	
	public Door getDoor1() {
		return door1;
	}
	
	public Door getDoor2() {
		return door2;
	}
	
	public Door getDoor3() {
		return door3;
	}
	
	public Door getDoor4() {
		return door4;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public Coordinates getCentre() {
		return centre;
	}
	
	public int getNumRooms() {
		return numRooms;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDoor1(Door door) {
		door1 = door;
	}
	
	public void setDoor2(Door door) {
		door2 = door;
	}

	public void setDoor3(Door door) {
		door3 = door;
	}
	
	public void setDoor4(Door door) {
		door4 = door;
	}
	
	public void setCentre(Coordinates centre) {
		this.centre = centre;
	}
	
	public void setNumRooms(int rooms) {
		numRooms = rooms;
	}
	
    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }
    
    public String toString() {
    	return name;
    }
	
	public Room(int id, int numRooms, String name, Door door1, Door door2, Door door3, Door door4, Coordinates centre, boolean hasTrapdoor) {
		this.id = id;
		this.numRooms = numRooms;
		this.name = name;
		this.door1 = door1;
		this.door2 = door2;
		this.door3 = door3;
		this.door4 = door4;
		this.centre = centre;
		this.hasTrapdoor = hasTrapdoor;
	}
}
