public class Coordinates {

    private int row, col;

    Coordinates(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public void add(Coordinates coordinates) {
        col = col + coordinates.getCol();
        row = row + coordinates.getRow();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    public boolean equals(Object o) {
    	if (o == this)
    		return true;
    	
    	if(!(o instanceof Coordinates))
    		return false;
    	Coordinates c = (Coordinates) o;
    	
    	return Double.compare(getCol(), c.getCol()) == 0 && Double.compare(getRow(), c.getRow()) == 0; 
    	}
}
