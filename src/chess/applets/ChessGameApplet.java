/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.applets;

import javax.swing.JApplet;

import chess.gui.BoardMatrixUI;

/**
 *
 * @author sijar
 */
public class ChessGameApplet extends JApplet {

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    public void init() {
     //Execute a job on the event-dispatching thread:
     //creating this applet's GUI.
    try {
               javax.swing.SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                    BoardMatrixUI.createAndShowGUI();
            }
        });
    } catch (Exception e) {
        System.err.println("ChessGame didn't successfully complete");
    }
    }

    // TODO overwrite start(), stop() and destroy() methods
    

}
