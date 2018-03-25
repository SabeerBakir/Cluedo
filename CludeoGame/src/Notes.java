// Kacper Twardowski (16401636), Sabeer Bakir (16333886), Aidan Mac Neill (16349586)

public class Notes {
	private String[][] notes = new String[29][2];
	
	public Notes(){ //Constructor
		notes[0][0] = "SUSPECTS";
		notes[1][0] = "---------------";
		notes[2][0] = "Mustard\t";
		notes[3][0] = "Plum\t";
		notes[4][0] = "Green\t";
		notes[5][0] = "Peacock\t";
		notes[6][0] = "Scarlet\t";
		notes[7][0] = "White\t";
		notes[8][0] = "";
		notes[9][0] = "WEAPONS";
		notes[10][0] = "---------------";
		notes[11][0] = "Dagger\t";
		notes[12][0] = "Candlestick";
		notes[13][0] = "Revolver";
		notes[14][0] = "Rope\t";
		notes[15][0] = "Lead Piping";
		notes[16][0] = "Spanner\t";
		notes[17][0] = "";
		notes[18][0] = "ROOMS";
		notes[19][0] = "---------------";
		notes[20][0] = "Hall\t";
		notes[21][0] = "Lounge\t";
		notes[22][0] = "Dining Room";
		notes[23][0] = "Kitchen\t";
		notes[24][0] = "Ball Room";
		notes[25][0] = "Conservatory";
		notes[26][0] = "Billiard Room";
		notes[27][0] = "Library\t";
		notes[28][0] = "Study\t";
		
		for(int i = 0; i < 29; i++){
			notes[i][1] = "";
		}
	}
	
	public void recordNote(String card, String mark){
		int i = 0;
		try {
			while(!notes[i][0].trim().contains(card)){
				i++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error here:" + card);
		}
		notes[i][1] = mark;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < 29; i++){
			buf.append(notes[i][0] + "\t\t" + notes[i][1] + "\n");
		}

		return buf.toString();
	}
	
	public static void main(String[] args){
		Notes notes = new Notes();
//		for(int i = 0; i < 29; i++){
//			notes.notes[i][1] = "X";
//		}
//		System.out.println(notes.toString());
		System.out.println(!notes.notes[28][0].trim().contains("Study"));
	}
}
