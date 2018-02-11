
public interface PlayerInterface {
	
	public String accuse(String killerName, String killerWeapon, String killerRoom);
	public String ask(Player[] players);
	
	public void moveUp();
	public void moveDown();	
	public void moveLeft();	
	public void moveRight();
	
	public boolean isPlaying();
}
