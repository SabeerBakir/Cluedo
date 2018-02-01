import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class UI extends JFrame{
	
	private BufferedImage imageBoard;
	
	private void getImage(String source) {
		
		try {
			imageBoard = ImageIO.read(new File(source));
		}
		catch(IOException exImage) {
			System.out.print("Image Exception: " + exImage);
		}
		
	}	
	
	private void getMainFrame() {
		
		GridLayout mainGL = new GridLayout(0,2);
			
		JFrame mainFrame = new JFrame("Main Frame");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setLayout(mainGL);
		getImage("cluedoboard.jpg");
		JLabel leftLabel = new JLabel(new ImageIcon(imageBoard));
		mainFrame.add(leftLabel, mainGL);
		mainFrame.setSize(1360, 690);
		
	}
	
	public static void main(String[] Args) {
	
		UI test = new UI();
		test.getMainFrame();
		
	}

}
