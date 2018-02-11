
public interface TileInterface {
	
	public Tile getUpTile();
	public Tile getDownTile();
	public Tile getLeftTile();
	public Tile getRightTile();
	
	public boolean isEmpty();
	
	public int getType();
	public int getCol();
	public int getRow();
	
	public void setType(int type);
	public void setCol(int col);
	public void setRow(int row);

	
	
}