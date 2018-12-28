/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.constants;

import chess.utility.FileReaderUtility;
import java.io.File;

/**
 *
 * @author sijar
 */
public interface IConstants {

    public static final String SHORT_PAWNS = "P";
    public static final String SHORT_BISHOP = "B";
    public static final String SHORT_KING = "K";
    public static final String SHORT_QUEEN = "Q";
    public static final String SHORT_ROOK = "R";
    public static final String SHORT_KNIGHT = "N";
    //BOARD SIZE
    public static final int BOARD_SIZE = 8;
    public static final String IMAGE_PATH_NEW = File.separatorChar+ "image" + File.separatorChar;
    public static final String CURRENT_WORKING_DIRECTORY = System.getProperty("user.dir");
    //IMAGE LOCATION DIRECTORY    
    public static final String IMAGE_FOLDER = FileReaderUtility.locateImageFolder(CURRENT_WORKING_DIRECTORY);
    
}
