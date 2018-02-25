
public class Door {
	
	private Coordinates pos;
	private Coordinates enterPos;
	private int doorNum;
	
	public Door(Coordinates pos, Coordinates enterPos, int doorNum) {
		this.pos = pos;
		this.enterPos = enterPos;
		this.doorNum= doorNum;
	}
	
	public Coordinates getPos() {
		return pos;
	}
	
	public Coordinates getEnterPos() {
		return enterPos;
	}
	
	public int getDoorNum() {
		return doorNum;
	}
	
	public void setPos(Coordinates pos) {
		this.pos = pos;
	}
	
	public void setEnterPos(Coordinates enterPos) {
		this.enterPos = enterPos;
	}
	
	public void setDoorNum(int doorNum) {
		this.doorNum = doorNum;
	}
	
	

}
