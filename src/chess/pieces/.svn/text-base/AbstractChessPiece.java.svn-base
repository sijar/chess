/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.pieces;

import chess.pieces.ChessFactory.ChessPieceType;

/**
 *
 * @author Sijar.Ahmed
 */
public abstract class AbstractChessPiece {
    //COLOR
    protected int color;
    //POSITION
    protected CoOrdinate position;
    //TYPE
    protected ChessPieceType type;




   //type and color
   /**
    * 
    * @return
    */
   abstract public ChessPieceType getType();


   /**
    * 
    * @return
    */
   abstract public int getColor();


   //Movements
   /**
    * 
    * @param position
    */
   abstract public void setPosition(CoOrdinate position);

   /**
    * 
    * @return
    */
   abstract public CoOrdinate getPosition();


   public abstract boolean isValidMove(CoOrdinate source, CoOrdinate destination);

   /**
    * Method to validate the capture movement of an {@link AbstractChessPiece}<br/>
    * The capture move only differs for the {@link Pawns} and its currently implemented for {@link Pawns}<br/>
    * The method throws an UnsupportedException for an {@link AbstractChessPiece} other than{@link Pawns}
    *
    * @param source
    * @param destination
    * @return true if its a valid capture of an {@link AbstractChessPiece}; false otherwise
    */
   abstract public boolean isCaptureMove(CoOrdinate source, CoOrdinate destination);

   ////////////////////// GUI BASED API ////////////////////
   /**
    * 
    * @return
    */
   abstract public String getImageName();

}
