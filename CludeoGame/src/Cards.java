// Kacper Twardowski (16401636), Sabeer Bakir (16333886), Aidan Mac Neill (16349586)

import java.util.ArrayList;
import java.util.Iterator;

public class Cards implements Iterable<Card>, Iterator<Card> {

	private final ArrayList<Card> cards = new ArrayList<>();
	private Iterator<Card> iterator;
	
	public Cards(){
	}
	
	public Cards(String[] names, String folderName){
		for(String s : names){
			cards.add(new Card(s, folderName + s + ".png"));
		}
	}
	
	@Override
	public Iterator<Card> iterator() {
        iterator = cards.iterator();
        return iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Card next() {
		return iterator.next();
	}
	
    public Card get(String name) {
        for (Card card : cards) {
            if (card.hasName(name)) {
                return card;
            }
        }
        return null;
    }
    
    public ArrayList<Card> getList(){
    	return cards;
    }
}
