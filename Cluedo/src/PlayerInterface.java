
public interface PlayerInterface {
	
	public void accuse(String killerName, String killerWeapon, String killerRoom);
	public void ask(Player[] players);
	
	public void moveUp();
	public void moveDown();	
	public void moveLeft();	
	public void moveRight();
	
	public boolean isPlaying();
}
