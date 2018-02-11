
public interface TileInterface {
	
	public Tile getUpTile();
	public Tile getDownTile();
	public Tile getLeftTile();
	public Tile getRightTile();
	
	public boolean isEmpty();
	
	public int getType();
	public int getPosX();
	public int getPosY();
	
	public void setType(int type);
	public void setPosX(int x);
	public void setPosY(int y);

	
	
}