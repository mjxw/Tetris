/*
 * TCSS 305 - Winter 2016
 * Assignment 6 - Tetris
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Point;
import model.TetrisPiece;

/**
 * This class operates the queue panel. The queue panel simply
 * displays the next tetris piece in queue.
 * 
 * @author Matthew Wu
 * @version 1.0
 *
 */
public class QueuePanel extends JPanel implements Observer {

    /**
     * A block's default width.
     */
    private static final int BLOCK_WIDTH = 20;
    
    /**
     * A block's default height.
     */
    private static final int BLOCK_HEIGHT = 20;
    
    /**
     * Horizontal shifting distance.
     */
    private static final int HORIZONTAL_SHIFT = 20;
    
    /**
     * Width of this panel.
     */
    private static final int WIDTH = 120;
    
    /**
     * Height of this panel.
     */
    private static final int HEIGHT = 220;
    
    /**
     * Coordinate shifting for proper spacing.
     */
    private static final int COORDINATE_SHIFT = 10;
    
    /**
     * The width of the area displaying the queue.
     */
    private static final int QUEUE_WIDTH = 100;
    
    /**
     * The height of the area displaying the queue.
     */
    private static final int QUEUE_HEIGHT = 200;
    
    /**
     * The stroke width.
     */
    private static final int STROKE_WIDTH = 3;
    
    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -4275257640465941722L;

    /**
     * A reference to a tetris piece.
     */
    private TetrisPiece myTetrisPiece;
    
    /**
     * Constructor for the queue panel. Sets initial dimensions for the panel.
     */
    public QueuePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
    
    /**
     * Draws the next tetris piece in queue.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);  
        g2d.setColor(Color.WHITE);
        g2d.fillRect(COORDINATE_SHIFT, COORDINATE_SHIFT, QUEUE_WIDTH, QUEUE_HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Next piece:", COORDINATE_SHIFT * 2, COORDINATE_SHIFT * 2);
        if (myTetrisPiece != null) {
            for (final Point p : myTetrisPiece.getPoints()) {
                g2d.setColor(Color.BLUE);
                g2d.fillRect(p.x() * BLOCK_WIDTH + HORIZONTAL_SHIFT, 
                             WIDTH - (p.y() * BLOCK_HEIGHT), BLOCK_WIDTH, BLOCK_HEIGHT);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(STROKE_WIDTH));
                g2d.drawRect(p.x() * BLOCK_WIDTH + HORIZONTAL_SHIFT, 
                             WIDTH - (p.y() * BLOCK_HEIGHT), BLOCK_WIDTH, BLOCK_HEIGHT);
            }
        }
    }
    
    /**
     * Sets my reference to the tetris piece to the next piece in queue.
     */
    @Override
    public void update(final Observable theObservable, final Object theArg) {
        if (theArg instanceof TetrisPiece) {
            myTetrisPiece = (TetrisPiece) theArg;
            repaint(); 
        }    
    }
}
