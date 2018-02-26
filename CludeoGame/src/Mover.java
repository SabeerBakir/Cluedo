
public class Mover {
	
	private Players players;
	private Rooms rooms;
	private Tokens tokens;
	
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
			if(moveIntoRoom(playerID, direction, curr) == 0)
				return 0;
			curr.moveBy(x, y);
			return 0;
		}
		else if(direction.equals("d") || direction.equals("down")) {	// move down
			int x = 0;
			int y = 1;
			Token curr = players.get(playerID).getCharacter();
			if(!checkBounds(curr,x,y)) return 1;
			if(moveIntoRoom(playerID, direction, curr) == 0)
				return 0;
			curr.moveBy(x,y);
			return 0;
		}
		else if(direction.equals("r") || direction.equals("right")) {	// move right
			int x = 1;
			int y = 0;
			Token curr = players.get(playerID).getCharacter();
			if(!checkBounds(curr,x,y)) return 1;
			if(moveIntoRoom(playerID, direction, curr) == 0)
				return 0;
			curr.moveBy(x,y);
			return 0;
		}
		else if(direction.equals("l") || direction.equals("left")) {	// move left
			int x = -1;
			int y = 0;
			Token curr = players.get(playerID).getCharacter();
			if(!checkBounds(curr,x,y)) return 1;
			if(moveIntoRoom(playerID, direction, curr) == 0)
				return 0;
			curr.moveBy(x,y);
			return 0;
		}
		else return 1;
	}
	
	private Boolean checkBounds(Token curr, int x, int y) {			// check if the player can move to this slot
		
		int row = curr.getPosition().getRow();
		int col = curr.getPosition().getCol();
		
		if(row+y >= 0 && row+y <= 24) {
			if(col+x >= 0 && col+x <= 24) {
				if(tileType[row+y][col+x] != 2) {
					for(Player player : players) {					// make sure that no other player occupies this slot
						if(player.getCharacter().getPosition().getRow() == row+y && player.getCharacter().getPosition().getCol() == col+x && player.getCharacter() != curr) return false;
					}
					return true;
				}
			}
		}

		return false;
	}
	
	private int moveIntoRoom(int playerID, String direction, Token curr) {
		
		int row = curr.getPosition().getRow();
		int col = curr.getPosition().getCol();
		
		if(direction.equals("u") || direction.equals("up")) { // change the row and col values to where the user wants to move to
			row--;
		}
		else if(direction.equals("d") || direction.equals("down")) {
			row++;
		}
		else if(direction.equals("l") || direction.equals("left")) {
			col--;
		}
		else if(direction.equals("r") || direction.equals("right")) {
			col++;
		}
		Coordinates coord = new Coordinates(row, col);
		
		if(coord == rooms.get(2).getDoor1().getPos()) { // edge cases for doors on the corners of rooms which could inadvertently be accessed through walls
			if(direction.equals("u") || direction.equals("up")) {
				players.get(playerID).setPos(multiplePlayersInRoom(playerID, rooms.get(2)));
				players.get(playerID).setRoom("Conservatory");
				return 0;
			}
			else
				return 1;
		}
		else if(coord == rooms.get(6).getDoor1().getPos()) {
			if(direction.equals("d") || direction.equals("down")) {
				players.get(playerID).setPos(multiplePlayersInRoom(playerID, rooms.get(6)));
				players.get(playerID).setRoom("Lounge");
				return 0;
			}
			else
				return 1;
		}
		else if(coord == rooms.get(8).getDoor1().getPos()) {
			if(direction.equals("d") || direction.equals("down")) {
				players.get(playerID).setPos(multiplePlayersInRoom(playerID, rooms.get(8)));
				players.get(playerID).setRoom("Study");
				return 0;
			}
			else
				return 1;
		}
		else if(tileType[row][col] == 1) {
			for(int i = 0; i < 9; i++) {
				if(coord == rooms.get(i).getDoor1().getPos() || coord == rooms.get(i).getDoor2().getPos() || coord == rooms.get(i).getDoor3().getPos() || coord == rooms.get(i).getDoor4().getPos()) {
					players.get(playerID).setPos(multiplePlayersInRoom(playerID, rooms.get(i)));
					players.get(playerID).setRoom(rooms.get(i).getName());
					return 0;
				}
			}
		}
		return 1;
	}
	
	private Coordinates multiplePlayersInRoom(int playerID, Room room) {
		int count = 0;
		int row = room.getCentre().getRow();
		int col = room.getCentre().getCol();
		for(Player player: players) {
			if(player.getRoom().equals(room.getName()) && player != players.get(playerID))
				count++;
		}
			if(count == 0)
				return room.getCentre();
			else if(count == 1)
				return new Coordinates(row, col++);
			else if(count == 2)
				return new Coordinates(row++, col);
			else if(count == 3)
				return new Coordinates(row++, col++);
			else if(count == 4)
				return new Coordinates(row, col+2);
			else if(count == 5)
				return new Coordinates(row++, col+2);
			else
				return null;
	}

	private int moveTrapdoor(int playerID) {
		if(players.get(playerID).getRoom().equals("Kitchen")) {
			players.get(playerID).setPos(multiplePlayersInRoom(playerID, rooms.get(8)));
			players.get(playerID).setRoom("Study");
			return 0;
		}
		else if(players.get(playerID).getRoom().equals("Conservatory")) {
			players.get(playerID).setPos(multiplePlayersInRoom(playerID, rooms.get(6)));
			players.get(playerID).setRoom("Lounge");
			return 0;
		}
		else if(players.get(playerID).getRoom().equals("Lounge")) {
			players.get(playerID).setPos(multiplePlayersInRoom(playerID, rooms.get(2)));
			players.get(playerID).setRoom("Conservatory");
			return 0;			
		}
		else if(players.get(playerID).getRoom().equals("Study")) {
			players.get(playerID).setPos(multiplePlayersInRoom(playerID, rooms.get(0)));
			players.get(playerID).setRoom("Kitchen");
			return 0;
		}
		else
			return 1;
	}
	
	private int exitRoom(int playerID, Room room) {
		int numDoors = 1, choice = 0;
		boolean loop = true;
		if(room.getDoor4() != null)
			numDoors = 4;
		else if(room.getDoor3() != null)
			numDoors = 3;
		else if(room.getDoor2() != null)
			numDoors = 2;
		
		while(loop) {
			// get the user to input what door they want to leave out of, with door 1 on the left of the room
			// input value into choice
			if(choice < 1 || choice > numDoors) {
				// tell the user they picked an invalid door number, enter again
			}
			else
				loop = false;
		}
		
		if(choice == 1) {
			players.get(playerID).setPos(room.getDoor1().getEnterPos());
			players.get(playerID).setRoom(null);
			return 0;
		}
		else if(choice == 2) {
			players.get(playerID).setPos(room.getDoor2().getEnterPos());
			players.get(playerID).setRoom(null);
			return 0;
		}
		else if(choice == 3) {
			players.get(playerID).setPos(room.getDoor3().getEnterPos());
			players.get(playerID).setRoom(null);
			return 0;
		}
		else if(choice == 4) {
			players.get(playerID).setPos(room.getDoor4().getEnterPos());
			players.get(playerID).setRoom(null);
			return 0;
		}
		else
			return 1;
	}

}
