//Kacper Twardowski (16401636)
//Sabeer Bakir (16333886)
//Aidan Mac Neill (16349586)

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
	private class InnerPanel extends JPanel{
		
		// Holds the image to be drawn
		private BufferedImage imageBuffer;
		
		// Constructor that takes in the image to be painted
		public InnerPanel(String source) {
			
			super();
			this.setPreferredSize(new Dimension(640,640));
			this.setSize(640,640);
			this.setBounds(0, 0, this.getWidth(), this.getHeight());
			this.setOpaque(false);
			this.setVisible(true);
			try {
				imageBuffer = ImageIO.read(this.getClass().getResource(source));
				//imageBuffer = ImageIO.read(new File(source));
			}	
			catch(IOException exImage1) {
				System.out.print("Image Exception: " + exImage1.getMessage());
			}
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(imageBuffer, 0, 0, this);
			
		}
		
	}
	
	public class PlayerIcon extends JLabel{
		
		// Holds the icon to be drawn
		private BufferedImage imageBuffer;
			
		// Constructor that takes in the image to be painted
		public PlayerIcon(String source) {
				
			super();
			this.setPreferredSize(new Dimension(22,22));
			this.setSize(22,22);
			this.setOpaque(false);
			this.setVisible(true);
			this.setBounds(0, 0, this.getWidth(), this.getHeight());
			try {
				imageBuffer = ImageIO.read(this.getClass().getResource(source));
				//imageBuffer = ImageIO.read(new File(source));
			}	
			catch(IOException exPIcon) {
				System.out.print("Icon Exception: " + exPIcon.getMessage());
			}
							
		}
		
		@Override
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			this.setPreferredSize(new Dimension(22,22));
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(imageBuffer, 0, 0, this);
			
		}
		
		public BufferedImage getImage() {
			return imageBuffer;
		}
		
	}
	
	public UI(Player[] players){
		for(int i = 0; i < players.length; i++){
			gridPanel.add(players[i]);
		}
	}
	
	
	// Important UI JComponents
	InnerPanel boardPanel = new InnerPanel("cluedoboard.jpg");
	//InnerPanel boardPanel = new InnerPanel("cluedo board with grid.png");
	
	PlayerIcon green = new PlayerIcon("/Player Token Icon/Green.png");
	PlayerIcon mustard = new PlayerIcon("/Player Token Icon/Mustard.png");
	PlayerIcon peacock = new PlayerIcon("/Player Token Icon/Peacock.png");
	PlayerIcon plum = new PlayerIcon("/Player Token Icon/Plum.png");
	PlayerIcon scarlet = new PlayerIcon("/Player Token Icon/Scarlet.png");
	PlayerIcon white = new PlayerIcon("/Player Token Icon/White.png");
	
	JLabel rope = new JLabel("Rope");
	JLabel dagger = new JLabel("Dagger");
	JLabel wrench = new JLabel("Wrench");
	JLabel pistol = new JLabel("Pistol");
	JLabel candlestick = new JLabel("Candle Stick");
	JLabel leadPipe = new JLabel("Lead Pipe");
	
	// An array of points where the starting locations are on the board
	Point[] startingPoints = {new Point(250,25), new Point(365,25), new Point(572,162), new Point(572,462), new Point(205,576), new Point(44,415)};
	
	// An array of points where the weapon locations are on the board
	Point[] weaponPoints = {new Point(76,82), new Point(273,103), new Point(480,61), new Point(466,292), new Point(466,436), new Point(491,526), new Point(291,495), new Point(87,500), new Point(50,242)};
	
	JLayeredPane layerFrame = new JLayeredPane();
	JPanel gridPanel = new JPanel();
	JPanel textPanel = new JPanel();
	JTextArea textInfoField = new JTextArea(1,20);
	static String DEFAULTCOMD = "> ";
	//static String testText = ""
	static JTextField textComdField = new JTextField(DEFAULTCOMD); // Game commands are inputed here
	
	// Getter for the textInfoField (DANGEROUS)
	private static JTextField getComd() {
		return textComdField;
	}

	// Configure all the JComponents
	public void getMainFrame() {
		
		// Make the main frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		
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
		textInfoField.append("-------------------------------------------------------\n"
				+ "Welcome to Cluedo: Sprint 1\n"
				+ "Type in \"> help\" to see the available\ncommands\n"
				+ "-------------------------------------------------------\n"
				+ "The starting positions of players are:\n"
				+ "Green: " + startingPoints[0].x + "," + startingPoints[0].y
				+ "\nMustard: " + startingPoints[1].x + "," + startingPoints[1].y
				+ "\nPeacock: " + startingPoints[2].x + "," + startingPoints[2].y
				+ "\nPlum: " + startingPoints[3].x + "," + startingPoints[3].y
				+ "\nScarlet: " + startingPoints[4].x + "," + startingPoints[4].y
				+ "\nWhite: " + startingPoints[5].x + "," + startingPoints[5].y
				+ "\n-------------------------------------------------------\n");
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
				textInfoField.append("<test"+textComdField.getText()+"\n");
				
				if(textComdField.getText().equals("> help")) textInfoField.append("Possible commands:\n"
																			+ "> help: displays additional information\n"
																			+ "> move: will move the Green player\ntoken down one tile\n"
																			+ "> exit: will exit the game\n"
																			+ "-------------------------------------------------------\n");
				
				else if(textComdField.getText().equals("> move")) green.setLocation(green.getLocation().x, green.getLocation().y+23);
				
				else if(textComdField.getText().equals("> exit")) System.exit(0);
				
				else textInfoField.append("-------------------------------------------------------\nUnrecognised command\n-------------------------------------------------------\n");
				
				textComdField.setText(DEFAULTCOMD);
				
			}
			
		};
		
		textComdField.addActionListener(actionComd);
		textPanel.add(textComdField, BorderLayout.SOUTH);
		
		// Add the panels to main frame
		this.add(textPanel, BorderLayout.EAST);
		this.setPreferredSize(new Dimension(875,678));
		this.setResizable(false);
		
		// Set up the layered frame
		layerFrame.setVisible(true);
		layerFrame.setLayout(null);
		layerFrame.setBounds(0, 0, 640, 640);
		this.add(layerFrame, BorderLayout.CENTER);
		layerFrame.setOpaque(true);
		layerFrame.setBackground(new Color(150,0,0));
		
		// Add the images
		layerFrame.add(boardPanel, 2); 	// 2 = background layer
		layerFrame.add(green, 0);		// 1 = weapon layer
		layerFrame.add(mustard, 0);		// 0 = player layer
		layerFrame.add(peacock, 0);
		layerFrame.add(plum, 0);
		layerFrame.add(scarlet, 0);
		layerFrame.add(white, 0);
		
		// TODO
		// Place the players for the purposes of this assignment
		green.setLocation(startingPoints[0]);
		mustard.setLocation(startingPoints[1]);
		peacock.setLocation(startingPoints[2]);
		plum.setLocation(startingPoints[3]);
		scarlet.setLocation(startingPoints[4]);
		white.setLocation(startingPoints[5]);
		
		// Set up the weapons
		layerFrame.add(rope, 1);
		layerFrame.add(dagger, 1);
		layerFrame.add(wrench, 1);
		layerFrame.add(pistol, 1);
		layerFrame.add(candlestick, 1);
		layerFrame.add(leadPipe, 1);
		
		rope.setSize(100,20);
		rope.setForeground(new Color(150,0,0));
		rope.setLocation(weaponPoints[0]);
		
		dagger.setSize(100,20);
		dagger.setForeground(rope.getForeground());
		dagger.setLocation(weaponPoints[1]);
		
		wrench.setSize(100,20);
		wrench.setForeground(rope.getForeground());
		wrench.setLocation(weaponPoints[2]);
		
		pistol.setSize(100,20);
		pistol.setForeground(rope.getForeground());
		pistol.setLocation(weaponPoints[3]);
		
		candlestick.setSize(100,20);
		candlestick.setForeground(rope.getForeground());
		candlestick.setLocation(weaponPoints[4]);
		
		leadPipe.setSize(100,20);
		leadPipe.setForeground(rope.getForeground());
		leadPipe.setLocation(weaponPoints[5]);
		
		this.pack();
		
//		// Grid Panel stuff
//		//boardPanel.add(gridPanel);
//		gridPanel.setLocation(42, 24);
//		gridPanel.setSize(551, 573);
//		//gridPanel.setLayout(new GridLayout(25, 24, 2, 1));
//		gridPanel.setLayout(null);
//		gridPanel.setVisible(true);
//		gridPanel.setOpaque(false);
//		gridPanel.add(green);
//		gridPanel.revalidate();

	}
	
	public UI() {
		super("FinnaBustaJava");
		this.getMainFrame();
	}
	
	// for testing purposes only
	public static void main(String[] args) {
	
		//UI test = new UI();
		//System.out.println(UI.getComd().getText());
		
	}

}