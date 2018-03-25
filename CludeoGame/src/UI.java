import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class UI {

    private static final int FRAME_WIDTH = 1205;
    private static final int FRAME_HEIGHT = 790;

    private final BoardPanel boardPanel;
    private final InfoPanel infoPanel = new InfoPanel();
    private final CommandPanel commandPanel = new CommandPanel();
    
    // Make the appropriate panels
 	Border loweredetched = BorderFactory.createLineBorder(commandPanel.getBackground()); 
 	TitledBorder textInfoBorder = BorderFactory.createTitledBorder(loweredetched, "Info"); 
 	TitledBorder textComdBorder = BorderFactory.createTitledBorder(loweredetched, "Commands"); 	
 	
    UI(Tokens characters, Weapons weapons) {
        JFrame frame = new JFrame();
        boardPanel = new BoardPanel(characters, weapons);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("FinnaBustaJava");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(boardPanel, BorderLayout.LINE_START);
     	textInfoBorder.setTitleJustification(TitledBorder.CENTER);
     	textComdBorder.setTitleJustification(TitledBorder.CENTER);
        infoPanel.setBorder(textInfoBorder);
        commandPanel.setBorder(textComdBorder);
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(infoPanel, BorderLayout.CENTER);
        leftPanel.add(commandPanel, BorderLayout.SOUTH);
        frame.add(leftPanel, BorderLayout.EAST);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public String getCommand() {
        return commandPanel.getCommand();
    }	

    public void display() {
        boardPanel.refresh();
    }

    public void displayString(String string) {
        infoPanel.addText(string);
    }
    
}
