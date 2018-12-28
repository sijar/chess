/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.utility;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sijar.Ahmed
 */
public class CommandReader {

    /**
     * Method will return the input read from the console
     * 
     * @return
     */
    public static String readCommand() {
        StringBuilder input = new StringBuilder();
        try {
            DataInputStream reader = new DataInputStream(System.in);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(reader));
            char line = ' ';            
            //System.out.print("\n Please enter the input[CTRL-D to finish] :");
            while ((line = (char) bufferedReader.read()) != '#') {                
                input.append(line);
            }            
        } catch (IOException ex) {
            Logger.getLogger(CommandReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("YOUR INPUTS :" + input);
        return input.toString();
    }

    


}
