import java.util.ArrayList;
import java.util.Iterator;

public class Weapons implements Iterable<Weapon>, Iterator<Weapon> {

    private final ArrayList<Weapon> weapons = new ArrayList<>();
    private Iterator<Weapon> iterator;

    Weapons() { // create an ArrayList of the weapon tokens with their locations
        weapons.add(new Weapon("Rope",new Coordinates(2, 2)));
        weapons.add(new Weapon("Dagger",new Coordinates(12, 3)));
        weapons.add(new Weapon("Spanner",new Coordinates(19, 1)));
        weapons.add(new Weapon("Revolver",new Coordinates(2, 12)));
        weapons.add(new Weapon("Candlestick",new Coordinates(3, 20)));
        weapons.add(new Weapon("Lead Piping",new Coordinates(12, 20)));
    }

    public Weapon get(String name) {
        for (Weapon weapon : weapons) {
            if (weapon.hasName(name)) {
                return weapon;
            }
        }
        return null;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Weapon next() {
        return iterator.next();
    }

    public Iterator<Weapon> iterator() {
        iterator = weapons.iterator();
        return iterator;
    }
    
    public String[] toStringArray(){ //returns an array of strings of the array list
    	String[] names = new String[weapons.size()];
    	int i = 0;
    	for(Weapon weapon : weapons){
    		names[i] = weapon.getName();
    		i++;
    	}
    	
		return names;   	
    }
    
    public int size() {
    	return weapons.size();
    }
}