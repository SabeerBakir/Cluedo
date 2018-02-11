import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class UI extends JFrame{
	
	// Custom JPanel
	private static class InnerPanel extends JPanel{
		
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
			
			this.setLayout(null);
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(imageBuffer, 0, 0, 640, 638, this);
			
		}
		
	}
	
	public UI(Player[] players){
		for(int i = 0; i < players.length; i++){
			gridPanel.add(players[i]);
		}
	}
	
	
	// Important UI JComponents
	JFrame mainFrame = new JFrame("FinnaBustaJava");
	//InnerPanel boardPanel = new InnerPanel("cluedoboard.jpg");
	InnerPanel boardPanel = new InnerPanel("cluedo board with grid.png");
	JPanel	gridPanel	= new JPanel();
	JPanel textPanel = new JPanel();
	JTextArea textInfoField = new JTextArea(1,20);
	String DEFAULTCOMD = "> ";
	JTextField textComdField = new JTextField(DEFAULTCOMD);
	
	// Configure all the JComponents
	public void getMainFrame() {
		
		// Make the main frame
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setLayout(new BorderLayout());
		
		
		// Make the appropriate panels
		Border loweredetched = BorderFactory.createLineBorder(new Color(0,153,0)); 
		TitledBorder textInfoBorder = BorderFactory.createTitledBorder(loweredetched, "Info");
		textInfoBorder.setTitleJustification(TitledBorder.RIGHT);

		// Info Panel
		textPanel.setLayout(new BorderLayout());
		textInfoField.setBorder(textInfoBorder);
		textInfoField.setBackground(textPanel.getBackground());
		textInfoField.setLineWrap(true);
		textInfoField.setEditable(false);
		textPanel.add(textInfoField, BorderLayout.CENTER);
		
		// Commands Panel
		TitledBorder textComdBorder = BorderFactory.createTitledBorder(loweredetched, "Commands");
		textComdBorder.setTitleJustification(TitledBorder.RIGHT);
		textComdField.setBorder(textComdBorder);
		textComdField.setBackground(textPanel.getBackground());
		
		// Commands Action Listener
		Action actionComd = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// TODO create a real IO
				
				//System.out.println(textComdField.getText());
				textInfoField.append(textComdField.getText()+"\n");
				textComdField.setText(DEFAULTCOMD);
				
			}
			
		};
		
		textComdField.addActionListener(actionComd);
		textPanel.add(textComdField, BorderLayout.SOUTH);
		
		// Add the panels to main frame
		mainFrame.add(boardPanel, BorderLayout.CENTER);	
		mainFrame.add(textPanel, BorderLayout.EAST);
		mainFrame.setSize(880, 680);
		mainFrame.setResizable(false);
		
		// Grid Panel stuff
		gridPanel.setLocation(42, 24);
		gridPanel.setSize(551, 573);
		//gridPanel.setLayout(new GridLayout(25, 24, 2, 1));
		gridPanel.setLayout(null);
		boardPanel.add(gridPanel);
		gridPanel.setOpaque(false);
		
	}
	
	// for testing purposes only
//	public static void main(String[] args) {
//	
//		UI test = new UI();
//		test.getMainFrame();
//		
//	}

}
