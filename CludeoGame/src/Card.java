import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Card extends JPanel{

	private static final long serialVersionUID = 1L;
	
	   private static final int IMAGE_WIDTH = 122, IMAGE_HEIGHT = 200;
	
	private String cardName;
	private String imagepath;
	private BufferedImage cardImage;
	
	public Card(String name, String imagePath){
		this.cardName = name;
		this.imagepath = imagePath;
		
        setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        setBackground(Color.WHITE);
        try {
            cardImage = ImageIO.read(this.getClass().getResource(imagePath));
        } catch (IOException ex) {
            System.out.println("Could not find the image file " + ex.toString());
        }
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 =(Graphics2D) g;
        g2.drawImage(cardImage, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, this);
    }
	
	public String getCardName() {
		return cardName;
	}

	public String getImagepath() {
		return imagepath;
	}
	
    public boolean hasName(String name) {
        return this.cardName.toLowerCase().equals(name.toLowerCase().trim());
    }
}
