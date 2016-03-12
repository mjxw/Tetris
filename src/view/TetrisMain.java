/*
 * TCSS 305 - Winter 2016
 * Assignment 6 - Tetris
 */
package view;

import java.awt.EventQueue;

/**
 * Runs tetris by instantiating and starting the tetris GUI.
 * 
 * @author Matthew Wu
 * @version 1.0
 */
public final class TetrisMain {

    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }
    
    /** 
     * The main method, invokes the Tetris GUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI();
            }
        });
    }
}
