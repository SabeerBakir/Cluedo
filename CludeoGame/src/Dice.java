// Kacper Twardowski (16401636), Sabeer Bakir (16333886), Aidan Mac Neill (16349586)

import java.util.Random;

public class Dice {
	
	private Random randFace = new Random();
	private int numberDice;	// Number of dice
	private int sides;		// Number of sides

	public Dice() {
		this.numberDice = 1;
		this.sides = 6;
	}
	
	public Dice(int sides) {
		this.sides = sides;
	}
	
	public Dice(int sides, int numberDice) {
		this.numberDice = numberDice;
		this.sides = sides;
	}
	
	private int generateFace() {
		return (randFace.nextInt(sides)+1);
	}
	
	public int getNumber() {
		return this.numberDice;
	}
	
	public int getSides() {
		return this.sides;
	}
	
	public int roll() {
		int total = 0;
		for(int i = 0; i < numberDice; i++) total+=generateFace();
		return total;
	}
	
	public String toString() {
		return "{ Sides: " + sides + ", Quantity: " + numberDice + " }";
	}
	
}
