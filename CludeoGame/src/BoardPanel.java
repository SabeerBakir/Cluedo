import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;

class BoardPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int FRAME_WIDTH = 750, FRAME_HEIGHT = 750;  // must be even
    private static final float COL_OFFSET = 52f, ROW_OFFSET = 29f;
    private static final float COL_SCALE = 26.9f, ROW_SCALE = 27.1f;
    private static final int TOKEN_RADIUS = 12;   // must be even

    private final Tokens tokens;
    private final Weapons weapons;
    private BufferedImage boardImage;

    BoardPanel(Tokens tokens, Weapons weapons) {
        this.tokens = tokens;
        this.weapons = weapons;
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setBackground(Color.WHITE);
        try {
            boardImage = ImageIO.read(this.getClass().getResource("cluedo board.jpg"));
        } catch (IOException ex) {
            System.out.println("Could not find the image file " + ex.toString());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 =(Graphics2D) g;
        g2.drawImage(boardImage, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, this);
        for (Token token : tokens) {
            int x = Math.round(token.getPosition().getCol()*COL_SCALE + COL_OFFSET);
            int y = Math.round(token.getPosition().getRow()*ROW_SCALE + ROW_OFFSET);
            g2.setColor(Color.BLACK);
            Ellipse2D.Double ellipseBlack = new Ellipse2D.Double(x,y,2*TOKEN_RADIUS,2*TOKEN_RADIUS);
            g2.fill(ellipseBlack);
            Ellipse2D.Double ellipseColour = new Ellipse2D.Double(x+2,y+2,2*TOKEN_RADIUS-4,2*TOKEN_RADIUS-4);
            g2.setColor(token.getColor());
            g2.fill(ellipseColour);
        }
        for (Weapon weapon : weapons) {
            int x = Math.round(weapon.getPosition().getCol()*COL_SCALE + COL_OFFSET);
            int y = Math.round(weapon.getPosition().getRow()*ROW_SCALE + ROW_OFFSET);
            g2.setColor(Color.BLACK);
            Rectangle2D.Double rectangleBlack = new Rectangle2D.Double(x,y,2*TOKEN_RADIUS,2*TOKEN_RADIUS);
            g2.fill(rectangleBlack);
            Rectangle2D.Double rectangleColor = new Rectangle2D.Double(x+2,y+2,2*TOKEN_RADIUS-4,2*TOKEN_RADIUS-4);
            g2.setColor(Color.lightGray);
            g2.fill(rectangleColor);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("default", Font.BOLD, 16));
            g2.drawString(String.valueOf(weapon.getName().charAt(0)),x+7,y+17);
        }
    }

    public void refresh() {
        revalidate();
        repaint();
    }

}
