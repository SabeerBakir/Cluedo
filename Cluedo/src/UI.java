import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class UI extends JFrame{
	
	public class InnerPanel extends JPanel{
		
		// Holds the image to be drawn
		private BufferedImage imageBuffer;
		
		// Constructor that takes in the image to be painted
		public InnerPanel(String source) {
			
			super();
			try {
				//imageBuffer = ImageIO.read(this.getClass().getResource(source));
				imageBuffer = ImageIO.read(new File(source));
			}	
			catch(IOException exImage1) {
				System.out.print("Image Exception: " + exImage1.getMessage());
			}
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(imageBuffer, 0, 0, this.getHeight(), this.getHeight(), this);
			
		}
		
	}
	
	private void getMainFrame() {
		
		// Make the main frame
		JFrame mainFrame = new JFrame("Main Frame");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setLayout(new BorderLayout());
		
		// Make the appropriate panels
		InnerPanel boardPanel = new InnerPanel("cluedoboard.jpg");
		Border loweredetched = BorderFactory.createLineBorder(new Color(0,153,0)); 
		TitledBorder textBorder = BorderFactory.createTitledBorder(loweredetched, "Commands");
		textBorder.setTitleJustification(TitledBorder.RIGHT);
		
		JTextArea textField = new JTextArea(1,20);
		textField.setBorder(textBorder);
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		textPanel.add(textField, BorderLayout.CENTER);
		
		// Add the panels to main frame
		mainFrame.add(boardPanel, BorderLayout.CENTER);	
		mainFrame.add(textPanel, BorderLayout.EAST);
		mainFrame.setSize(835, 640);
		
	}
	
	// for testing purposes only
	public static void main(String[] args) {
	
		UI test = new UI();
		test.getMainFrame();
		
	}

}
