
public class Mover {
	
	private Players players;
	
	private static int [][] tileType = new int[][] { // where 0 is a regular tile, 1 is a door tile, and 2 are tiles which are not moveable to
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 1, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 2, 2, 2, 2, 1, 2, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 2, 1, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 1, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 1, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 1, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2, 2},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 1, 1, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 1, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2},
    };

	public Mover(Players players) {
		this.players = players;
	}
	
	// returns 0 if moved successfully, 1 if the move failed
	public int move(int playerID, String direction) {
		direction = direction.replaceAll("[^a-zA-Z]","").toLowerCase(); // parse the string
		if(direction.equals("u") || direction.equals("up")) {			// move up
			int x = 0;
			int y = -1;
			Token curr = players.get(playerID).getCharacter();
			if(!checkBounds(curr,x,y)) return 1;
			curr.moveBy(x, y);
			return 0;
		}
		else if(direction.equals("d") || direction.equals("down")) {	// move down
			int x = 0;
			int y = 1;
			Token curr = players.get(playerID).getCharacter();
			if(!checkBounds(curr,x,y)) return 1;
			curr.moveBy(x,y);
			return 0;
		}
		else if(direction.equals("r") || direction.equals("right")) {	// move right
			int x = 1;
			int y = 0;
			Token curr = players.get(playerID).getCharacter();
			if(!checkBounds(curr,x,y)) return 1;
			curr.moveBy(x,y);
			return 0;
		}
		else if(direction.equals("l") || direction.equals("left")) {	// move left
			int x = -1;
			int y = 0;
			Token curr = players.get(playerID).getCharacter();
			if(!checkBounds(curr,x,y)) return 1;
			curr.moveBy(x,y);
			return 0;
		}
		else return 1;
	}
	
	private Boolean checkBounds(Token curr, int x, int y) {	//TODO			// check if the player can move to this slot
		
		int row = curr.getPosition().getRow();
		int col = curr.getPosition().getCol();
		
		//System.out.println(row+y +" "+ col+x);
		
		if(row+y < 0 || row+y > 24) {//System.out.println("1st");
			if(col+x < 0 || col+x > 24) {//System.out.println("2nd");
				if(tileType[row+y][col+x] == 2) {//System.out.println("3rd");
					for(Player player : players) {
						if(player.getCharacter().getPosition().getRow() == row+y) return false;
						if(player.getCharacter().getPosition().getCol() == col+x) return false;
					}
				}
			}
		}
		//System.out.println("4th");
		return true;
	}

}
