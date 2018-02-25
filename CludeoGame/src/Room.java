
public class Room {
	
	private String name;
	private Door door1;
	private Door door2;
	private Door door3;
	private Door door4;
	private boolean hasTrapdoor;
	
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
	
	public Room(String name, Door door1, Door door2, Door door3, Door door4, boolean hasTrapdoor) {
		this.name = name;
		this.door1 = door1;
		this.door2 = door2;
		this.door3 = door3;
		this.door4 = door4;
		this.hasTrapdoor = hasTrapdoor;
	}
}
