/*
 * TCSS 305 - Winter 2016
 * Assignment 6 - Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.TetrisPiece;

/**
 * This class operates the data panel which reports the score and level.
 * 
 * @author Matthew Wu
 * @version 1.0
 *
 */
public class DataPanel extends JPanel implements Observer {

    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -4115991425671986825L;
    
    /**
     * The amount of lines a client needs to clear before leveling up.
     */
    private static final int  REMAINDER = 5;
    
    /**
     * The number 6 for spacing.
     */
    private static final int SIX = 6; 
    
    /**
     * The number 8 for spacing.
     */
    private static final int EIGHT = 8;
    
    /**
     * The number 3 for counting.
     */
    private static final int THREE = 3;
    
    /**
     * Points for dropping a block.
     */
    private static final int DROP_POINTS = 4;
    
    /**
     * Points for clearing one line.
     */
    private static final int CLEAR1 = 40;
    
    /**
     * Points for clearing 2 lines.
     */
    private static final int CLEAR2 = 100;

    /**
     * Points for clearing 3 lines.
     */
    private static final int CLEAR3 = 300;
    
    /**
     * Points for clearing 4 lines.
     */
    private static final int CLEAR4 = 1200;


    
    /**
     * Initial delay for timer.
     */
    private static final int DELAY = 1000;
    
    /**
     * The data panel's width.
     */
    private static final int PANEL_WIDTH = 120; 
    
    /**
     * The data panel's height.
     */
    private static final int PANEL_HEIGHT = 220;
    
    /**
     * Coordinate shifting for proper spacing.
     */
    private static final int COORDINATE_SHIFT = 10;
    
    /**
     * Width of the area displaying data.
     */
    private static final int DATA_AREA_WIDTH = 100;
    
    /**
     * Height of the area displaying data.
     */
    private static final int DATA_AREA_HEIGHT = 200;
    
    /**
     * A running total for a player's score.
     */
    private int myScore; 
    
    /**
     * The level that the player is on.
     */
    private int myLevel;
    
    /**
     * A count for how many rows have been cleared.
     */
    private int myClearCount1;
    
    /**
     * The number of pieces that have been in play.
     */
    private int myPieceCount;
    
    /**
     * A count for how many rows have been cleared that gets reset.
     */
    private int myClearCount2;
    
    /**
     * Clears left until next level.
     */
    private int myRemainder;
    
    /**
     * A count for how many rows that have been cleared that gets reset.
     */
    private int myClearCount3;
    
    /**
     * A timer that controls the pace of the game.
     */
    private Timer myTimer;

    /**
     *  Constructs a data panel.
     *  @param theTimer is the game timer.
     */
    public DataPanel(final Timer theTimer) {
        super();
        myScore = 0;
        myLevel = 1; 
        myClearCount1 = 0;
        myClearCount2 = 0;
        myPieceCount = 0;
        myRemainder = REMAINDER;
        myTimer = theTimer;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }
    
    /**
     * Resets the data values.
     */
    public void reset() {
        myScore = 0;
        myLevel = 1; 
        myClearCount1 = 0;
        myClearCount2 = 0;
        myPieceCount = 1;
        myRemainder = REMAINDER;
    }
    
    /**
     * Receives updated information from the observable object.
     */
    @Override
    public void update(final Observable theObservable, final Object theArg) {
        if (theArg instanceof Integer[]) {
            final Integer[] completedRows = (Integer[]) theArg;
            myClearCount1 += completedRows.length;  
            myClearCount2 += completedRows.length;
            myClearCount3 += completedRows.length;
            
            // Calculate level display
            if (myClearCount1 / REMAINDER == 0) {
                myLevel = 1;
            } else {
                myLevel = myClearCount1 / REMAINDER;
                myTimer.setDelay(DELAY / myLevel);

            }
            
            myRemainder = REMAINDER - (myClearCount3 % REMAINDER);
     
            // Calculate scores for clearing lines at different levels
            if (myClearCount2 == 1) {
                myScore += CLEAR1 * myLevel;
            } else if (myClearCount2 == 2) {
                myScore += CLEAR2 * myLevel;
            } else if (myClearCount2 == THREE) {
                myScore += CLEAR3 * myLevel;
            } else if (myClearCount2 == DROP_POINTS) {
                myScore += CLEAR4 * myLevel;
            }
            myClearCount2 = 0;
            repaint();
        }
        if (theArg instanceof TetrisPiece) {
            myPieceCount++;
            if (myPieceCount >= 2) {
                myScore += DROP_POINTS;
            }
            repaint();
        }   
    }
    
    /**
     * Draws the panel that displays the score and level.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);  
        g2d.setColor(Color.WHITE);
        g2d.fillRect(COORDINATE_SHIFT, COORDINATE_SHIFT, DATA_AREA_WIDTH, DATA_AREA_HEIGHT);
        g2d.setColor(Color.BLACK);
        g2d.drawString("level:" + myLevel, COORDINATE_SHIFT * 2, COORDINATE_SHIFT * 2);
        g2d.drawString("cleared:" + myClearCount1, COORDINATE_SHIFT * 2,
                       COORDINATE_SHIFT * DROP_POINTS);
        g2d.drawString("score:" + myScore, COORDINATE_SHIFT * 2, COORDINATE_SHIFT * SIX);
        g2d.drawString("level up in:" + myRemainder, COORDINATE_SHIFT * 2,
                       COORDINATE_SHIFT * EIGHT);
    }
}
