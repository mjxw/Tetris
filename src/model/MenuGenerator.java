/*
 * TCSS 305 - Winter 2016
 * Assignment 6 - Tetris
 */
package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import view.DataPanel;

/**
 * This class generates the menu and adds menu items to it.
 * 
 * @author Matthew Wu
 * @version 1.2
 *
 */
public class MenuGenerator extends JMenuBar implements Observer {

    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -1654267133529652417L;
    
    /**
     * A reference to the panel.
     */
    private final JPanel myPanel;
    
    /**
     * A reference to the game board.
     */
    private final Board myBoard;
    
    /**
     * A reference to the timer.
     */
    private final Timer myTimer;
    
    /**
     * A reference to the frame.
     */
    private final JFrame myFrame;
    
    /**
     * A reference to the controls button.
     */
    private JMenuItem myControlsButton;
    
    /**
     * A reference to the new game button.
     */
    private JMenuItem myNewGameButton;
    
    /**
     * A reference to the exit button.
     */
    private JMenuItem myExitButton;
    
    /**
     * A reference to the end game button.
     */
    private JMenuItem myEndGameButton;
    
    /**
     * A reference to the data panel.
     */
    private final DataPanel myDataPanel;
    
    /**
     * A reference to the grid checkbox item.
     */
    private JCheckBoxMenuItem myGrid;

    /**
     * Constructs the MenuGenerator which sets up a menu.
     * 
     * @param thePanel is the panel component. 
     * @param theBoard is the game board.
     * @param theTimer is the timer.
     * @param theFrame is the frame.
     * @param theDataPanel is the data panel.
     */
    public MenuGenerator(final JPanel thePanel, final Board theBoard, 
                         final Timer theTimer, final JFrame theFrame, 
                         final DataPanel theDataPanel) {
        super();
        myPanel = thePanel;
        myBoard = theBoard;
        myTimer = theTimer;
        myFrame = theFrame;
        myDataPanel = theDataPanel;
        myGrid = new JCheckBoxMenuItem(); 
        setupMenu();
    }
    
    /**
     * Checks to see if the grid option is checked.
     * @return returns a boolean of true if grid is selected and false otherwise.
     */
    public Boolean gridChecked() {
        return myGrid.isSelected();
    }
    
    /**
     * Sets up the menu. 
     */
    private void setupMenu() {
        // Add JMenus
        final JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        final JMenu options = new JMenu("Options");   
        options.setMnemonic(KeyEvent.VK_O);
        final JMenu help = new JMenu("Help");  
        help.setMnemonic(KeyEvent.VK_H);
        
        // Add JMenuItems
        myGrid = new JCheckBoxMenuItem("Grid");
        options.add(myGrid);
        myControlsButton = new JMenuItem("Controls");
        help.add(myControlsButton);
        myNewGameButton = new JMenuItem("New Game");
        myNewGameButton.setMnemonic(KeyEvent.VK_N);
        file.add(myNewGameButton);
        myEndGameButton = new JMenuItem("End Game");
        myEndGameButton.setMnemonic(KeyEvent.VK_E);
        file.add(myEndGameButton);  
        myExitButton = new JMenuItem("Exit"); 
        file.add(myExitButton);
        
        // Add everything to the panel
        this.add(file);
        this.add(options);
        this.add(help);
        
        // Add action listeners with helper method
        addListeners();
    }
    
    /**
     * Adds action listeners to the buttons.
     */
    private void addListeners() {
        
        // Action listener for exit
        myExitButton.addActionListener(new ActionListener() {
            
            /**
             * Closes program.
             */
            @Override 
            public void actionPerformed(final ActionEvent theEvent) {
                myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        // Action listener for new game
        myNewGameButton.addActionListener(new ActionListener() {
           
            /**
             * Implements new game feature.
             */
            @Override 
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.newGame();
                myTimer.start();
                myDataPanel.reset();
            }
        });
        
        // Action listener to display controls
        myControlsButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(myPanel, "Left Arrow: move left\n" 
                                + "Right Arrow: move right\n" 
                                + "Down Arrow: move down\n"
                                + "Up Arrow: rotate clockwise\n" 
                                + "Shift: rotate counter clockwise\n" 
                                + "Space: drop\n"
                                + "Pause: p\n",
                                 "CONTROL SETTINGS", JOptionPane.PLAIN_MESSAGE);
            }
        });
        
        // Action listener for  manually ending a game
        myEndGameButton.addActionListener(new ActionListener() {
            
            /**
             * Implements end game feature
             */
            @Override 
            public void actionPerformed(final ActionEvent theEvent) {
                myTimer.stop();
                JOptionPane.showMessageDialog(myPanel, "You have ended the game",
                                              "GAME OVER", JOptionPane.PLAIN_MESSAGE);
                myDataPanel.reset();
            }
        });
    }

    /**
     * Receives updated information from the observable object.
     */
    @Override
    public void update(final Observable theObservable, final Object theArg) {
   
    }
}
