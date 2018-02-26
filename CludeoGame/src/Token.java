import java.awt.*;

public class Token {

    private final String name;
    private final Color color;
    private Coordinates position;

    Token(String name, Color color, Coordinates position) {
        this.name = name;
        this.color = color;
        this.position = position;
    }

    public void moveBy(Coordinates move) {
        position.add(move);
    }
    
    public void moveBy(int x, int y) {
    	position.add(new Coordinates(x,y));
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Coordinates getPosition() {
        return position;
    }
    
    public void setPosition(Coordinates pos) {
    	position = pos;
    }

    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }
    
    public String toString() {
    	return name;
    }

}