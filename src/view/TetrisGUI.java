/*
 * TCSS 305 - Winter 2016
 * Assignment 6 - Tetris
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;
import model.MenuGenerator;

/**
 * This class is the GUI for my tetris implementation. 
 * The GUI handles setting up the frame and the panels. 
 * The panels contain aspects such as the game board, 
 * and other relevant information for the "tetris experience".
 * 
 * @author Matthew Wu
 * @version 1.7
 *
 */
public class TetrisGUI extends JFrame implements Observer {    
 
    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -4255365792156847047L;

    /**
     * A delay time for the timer.
     */
    private static final int DEFAULT_DELAY = 1000;
    
    /**
     * Info panel's width.
     */
    private static final int INFO_WIDTH = 120; 
    
    /**
     * Info panel's height.
     */
    private static final int INFO_HEIGHT = 440;
    
    /**
     * A reference to the game board.
     */
    private final Board myBoard; 
    
    /**
     * A reference to the timer.
     */
    private final Timer myTimer;
    
    /**
     * A reference to the overall panel that contains all the other panels.
     */
    private JPanel myOverallPanel;
    
    /**
     * A reference to the tetris panel.
     */
    private TetrisPanel myTetrisPanel;
    
    /**
     * A reference to the data panel.
     */
    private DataPanel myDataPanel;
    
    /**
     * A reference to the menu generator.
     */
    private MenuGenerator myMenuGenerator;
    
    /**
     * Tetris constructor. Initializes a new board and a timer and starts them. 
     * Sets up the frame and the panels.
     */
    public TetrisGUI() {
        super("Tetris");            
        myBoard = new Board();
        myBoard.addObserver(this);
        final ActionListener timerMotion = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.step();
            }
        };
        myTimer = new Timer(DEFAULT_DELAY, timerMotion);
        myTimer.start();
        setupPanels();
        myBoard.newGame(); 
        setupFrame();
    }
    
    /**
     * Sets up the frame. 
     */
    private void setupFrame() {
        setLocationRelativeTo(null);
        setJMenuBar(myMenuGenerator);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }

    /**
     * Sets up the panels.
     */
    private void setupPanels() {
        myOverallPanel = new JPanel(); // Overall panel to hold everything together 
        myOverallPanel.setLayout(new BorderLayout());
        add(myOverallPanel, BorderLayout.CENTER);
        
        // Set up info panel
        final JPanel infoPanel = new JPanel(); // Panel that holds general information 
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(INFO_WIDTH, INFO_HEIGHT));
        myOverallPanel.add(infoPanel, BorderLayout.EAST);
        
        // Set up data panel
        myDataPanel = new DataPanel(myTimer); // Panel for displaying level/score
        myDataPanel.setLayout(new BorderLayout());
        infoPanel.add(myDataPanel, BorderLayout.NORTH);
        
        // Create a menu generator
        myMenuGenerator = new MenuGenerator(myOverallPanel, myBoard, myTimer,
                                            this, myDataPanel);

        // Set up tetris panel 
        myTetrisPanel = new TetrisPanel(myBoard, myTimer, myMenuGenerator);            
        myTetrisPanel.setLayout(new BorderLayout());
        myTetrisPanel.setBackground(Color.WHITE);
        myOverallPanel.add(myTetrisPanel, BorderLayout.CENTER); 
              
        // Set up queue panel
        final QueuePanel queuePanel = 
                        new QueuePanel(); // Panel for displaying pieces in queue 
        queuePanel.setLayout(new BorderLayout());

        // Add components 
        infoPanel.add(queuePanel, BorderLayout.NORTH);
        infoPanel.add(myDataPanel, BorderLayout.SOUTH);
        
        // Register observers
        myBoard.addObserver(queuePanel);
        myBoard.addObserver(myTetrisPanel);
        myBoard.addObserver(myMenuGenerator);
        myBoard.addObserver(myDataPanel);
    }
 
    /**
     * Receives updated information from the observable object.
     */
    @Override
    public void update(final Observable theObservable, final Object theArg) {
      
    }
}




