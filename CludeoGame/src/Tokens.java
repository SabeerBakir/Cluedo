import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;

public class Tokens implements Iterable<Token>, Iterator<Token> {

    private final HashSet<Token> suspects = new HashSet<>();
    private Iterator<Token> iterator;

    Tokens() {
        suspects.add(new Token("Plum",new Color(142, 69, 133), new Coordinates(23,19)));
        suspects.add(new Token("White", Color.WHITE, new Coordinates(9,0)));
        suspects.add(new Token("Scarlet",Color.RED, new Coordinates(7,24)));
        suspects.add(new Token("Green",Color.GREEN, new Coordinates(14,0)));
        suspects.add(new Token("Mustard",Color.YELLOW, new Coordinates(0,17)));
        suspects.add(new Token("Peacock",Color.MAGENTA, new Coordinates(23,6)));
    }

    public Token get(String name) {
        for (Token suspect : suspects) {
            if (suspect.hasName(name)) {
                return suspect;
            }
        }
        return null;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Token next() {
        return iterator.next();
    }

    public Iterator<Token> iterator() {
        iterator = suspects.iterator();
        return iterator;
    }

    public String[] toStringArray(){ //returns an array of strings of the array list
    	String[] names = new String[suspects.size()];
    	int i = 0;
    	for(Token character : suspects){
    		names[i] = character.getName();
    		i++;
    	}
    	
		return names;   	
    }
    
    public int size() {
    	return suspects.size();
    }
}
