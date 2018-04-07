import java.util.ArrayList;

public class Logbook {
	
	private ArrayList<String> log = new ArrayList<>();
	
	public Logbook(){
	}
	
	public void addQuestion(String suspectQuestion, String weaponQuestion, String roomQuestion){
		log.add("Question: \n" + "Suspect: " + suspectQuestion + "\nWeapon: " + weaponQuestion + "\nRoom: " + roomQuestion + "\n");
	}
	
	public void addAnswer(String askedPlayer, String cardName){
		log.add(askedPlayer + " revealed too you " + cardName + "\n");
	}
	
	public void addReveal(String player, String cardName){
		log.add("You revealed " + cardName + " to " + player + "\n");
	}
	
	public void add(String s){
		log.add(s + "\n");
	}
	
	public String toString(){
		if(log.isEmpty()){
			return "Log is empty\n";
		}
		else{
			StringBuffer buf = new StringBuffer();
			for(int i = 0; i < log.size(); i++){
				buf.append(log.get(i) + "\n");
			}
			
			return buf.toString();
		}
	}
}
