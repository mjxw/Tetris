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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Block;
 import model.Board;
import model.MenuGenerator;

/**
 * This class is the panel that holds the game board. It listens for key events.
 * 
 * @author Matthew Wu
 * @version 1.2
 *
 */
public class TetrisPanel extends JPanel implements Observer {
    
    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -9006811425508213083L;

    /**
     * The height of this panel.
     */
    private static final int PANEL_HEIGHT = 440;
    /**
     * A block's height.
     */
    private static final int BLOCK_HEIGHT = 20; 
    
    /**
     * A block's width.
     */
    private static final int BLOCK_WIDTH = 20;
    
    /**
     * A size multiplier for scaling.
     */
    private static final int SIZE_MULTIPLIER = 10;
    
    /**
     * The dimension of the zone where pieces are buffered and where the game ends.
     */
    private static final int BUFFER_ZONE = 4;  
    
    /**
     * Stroke width size.
     */
    private static final int STROKE_WIDTH = 3;
    
    /**
     * A reference to the game board.
     */
    private Board myBoard;
    
    /**
     * A boolean value representing whether or not game has been paused.
     */
    private Boolean myPauseFlag;
    
    /**
     * A timer for progressing the game.
     */
    private Timer myTimer;
    
    /**
     * A collection of Block arrays.
     */
    private List<Block[]> myList;
    
    /**
     * A reference to the menu.
     */
    private MenuGenerator myMenu;
        
    /**
     * The constructor for a tetris panel.
     * 
     * @param theBoard is the game board.
     * @param theTimer is the game timer.
     * @param theMenu is the menu generator.  
     */
    public TetrisPanel(final Board theBoard, final Timer theTimer,
                       final MenuGenerator theMenu) {
        super();
        myBoard = theBoard; 
        myPauseFlag = false;
        myTimer = theTimer;
        myMenu = theMenu;
        myList = new ArrayList<Block[]>();
        setPreferredSize(new Dimension(BLOCK_WIDTH * SIZE_MULTIPLIER, 
                                       BLOCK_HEIGHT * SIZE_MULTIPLIER + BLOCK_WIDTH * 2));
        setFocusable(true);
        addKeyListener(new MyKeyListener());
    }
    
    /**
     * Disables the board.
     */
    public void disabler() {
        myTimer.stop();
    }
    
    /**
     * Enables the board.
     */
    public void enabler() {
        myTimer.start();
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);  
        g2d.setColor(Color.LIGHT_GRAY);
        if (!myList.isEmpty()) {
            
            // Fill in the game board 
            g2d.fillRect(0, BLOCK_HEIGHT * 2,
                         myList.get(0).length * BLOCK_WIDTH, 
                         (myList.size() - BUFFER_ZONE) * BLOCK_HEIGHT);              
            
            // Fill in the blast zone
            g2d.setColor(Color.RED);
            g2d.fillRect(0, 0, BLOCK_WIDTH * myBoard.getWidth(), BLOCK_HEIGHT * 2); 
            
            if (myMenu.gridChecked()) {
                g2d.setColor(Color.WHITE);
                for (int i = 2; i < BLOCK_WIDTH + 2; i++) { // draw x lines 
                    g2d.drawLine(0, i * BLOCK_WIDTH, BLOCK_WIDTH * SIZE_MULTIPLIER, 
                                 i * BLOCK_WIDTH);
                }
                for (int i = 0; i < SIZE_MULTIPLIER; i++) { // draw y lines
                    g2d.drawLine(i * BLOCK_HEIGHT, BLOCK_HEIGHT * 2, 
                                 i * BLOCK_HEIGHT, PANEL_HEIGHT);
                }    
            }

            // Draw tetris pieces
            for (int i = 0; i < myList.size(); i++) { // x
                final Block[] row = myList.get(i);
                for (int j = 0; j < row.length; j++) { // y
                    if (row[j] != null) {
                        g2d.setColor(Color.BLUE);

                        // Fill tetris blocks 
                        g2d.fillRect(j * BLOCK_WIDTH, 
                                     (BLOCK_WIDTH * BLOCK_HEIGHT + BLOCK_HEIGHT) 
                                     - (i * BLOCK_HEIGHT), BLOCK_WIDTH, BLOCK_HEIGHT); 
                        g2d.setColor(Color.BLACK);
                        g2d.setStroke(new BasicStroke(STROKE_WIDTH));
                        
                        // Outline the grid in each tetris block
                        g2d.drawRect(j * BLOCK_WIDTH, 
                                      (BLOCK_WIDTH * BLOCK_HEIGHT + BLOCK_HEIGHT) 
                                      - (i * BLOCK_HEIGHT), BLOCK_WIDTH, BLOCK_HEIGHT); 
                    }
                }
            }
        }
    }

    @Override
    public void update(final Observable theObservable, final Object theArg) {
        if (theArg instanceof ArrayList<?>) {
            myList = (ArrayList<Block[]>) theArg;
            repaint();
        }
        
        if (theArg instanceof Boolean && (boolean) theArg) {
            JOptionPane.showMessageDialog(this, "You hit the red zone",
                                          "GAME OVER", JOptionPane.PLAIN_MESSAGE);
            disabler();
        }
    } 
    
    /**
     * This class is for key events.
     * 
     * @author Matthew Wu
     * @version 1.0
     *
     */
    private class MyKeyListener extends KeyAdapter {
        
        /**
         * This method is responsible for moving tetris pieces.
         * @param theEvent is the key event.
         */
        public void keyPressed(final KeyEvent theEvent) {

            final int key = theEvent.getKeyCode();
            if (myTimer.isRunning()) {
                if (key == KeyEvent.VK_LEFT) {
                    myBoard.left();
                }
                if (key == KeyEvent.VK_RIGHT) {
                    myBoard.right();
                }
                if (key == KeyEvent.VK_SPACE) {
                    myBoard.drop();
                }
                if (key == KeyEvent.VK_DOWN) {
                    myBoard.down();
                }
                if (key == KeyEvent.VK_UP) {
                    myBoard.rotateCW();
                }   
                if (key == KeyEvent.VK_SHIFT) {
                    myBoard.rotateCCW();
                }
            }
            if (key == KeyEvent.VK_P) {
                if (!myPauseFlag) {
                    disabler();
                    myPauseFlag = true;
                } else {
                    enabler();
                    myPauseFlag = false;
                }
            }
        }
    }  
}


