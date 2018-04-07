public class Weapon {

    private final String name;
    private final Coordinates position;

    Weapon(String name, Coordinates position) {
        this.name = name;
        this.position = position;
    }

    public void moveBy(Coordinates move) {
        position.add(move);
    }

    public String getName() {
        return name;
    }

    public Coordinates getPosition() {
        return position;
    }

    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }
    
    public String toString() {
    	return name;
    }
}
