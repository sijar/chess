/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import chess.board.ChessSquare;
import chess.constants.IConstants;
import chess.pieces.ChessFactory.ChessPieceType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sijar.Ahmed
 */
class Knight extends AbstractChessPiece {

    Knight() {
        this.type = ChessPieceType.NIGHT;
    }

    /**
     * 
     * @param position
     * @param color
     */
    Knight(CoOrdinate position, int color) {
        this.color = color;
        this.position = position;
        this.type = ChessPieceType.NIGHT;
    }

    @Override
    public String toString() {
        return "(N)" + position.toString();
    }

    @Override
    public ChessPieceType getType() {
        return type;
    }

    @Override
    public void setPosition(CoOrdinate position) {
        this.position = position;
    }


    /**
     * Movement of the Knight is the letter 'L'.
     *
     * @param source
     * @param destination
     * @return
     */
    @Override
    public boolean isValidMove(CoOrdinate source, CoOrdinate destination) {
        if ((destination.getY() >= 0) && (destination.getY() < IConstants.BOARD_SIZE) && (destination.getX() >= 0) && (destination.getX() < IConstants.BOARD_SIZE)) {
            int x0 = Math.abs(source.getX() - destination.getX());
            int y0 = Math.abs(source.getY() - destination.getY());
            //System.err.println(" X0 :" + x0);//System.err.println(" Y0 :" + y0);
            if( (x0 == 1 && y0 == 2) || (x0== 2 && y0 == 1) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCaptureMove(CoOrdinate source, CoOrdinate destination) {
        try {
            // grtr;
            return isValidMove(source, destination);
        } catch (Exception ex) {
            Logger.getLogger(Knight.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }

    /****************************************************************************************************************************************************
     *
     *                                         O V E R R I D D E N  equals() & hashcode()
     *
     *****************************************************************************************************************************************************/
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Knight other = (Knight) obj;
        //System.out.println(" Comparing color :" + this.color + other.color);
        //System.out.println(" Comparing type :" + this.type + other.type);
        //System.out.println(" Comparing position :" + this.position + other.position);
        if ((this.color == other.color) && ((this.type == null) ? (other.type == null) : this.type.equals(other.type)) && ((this.position == null) ? (other.position == null) : this.position.equals(other.position))) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public int getColor() {
        return color;
    }

    /**
     * Method will return a colloquial name for the chess piece
     * The colloquial name will be used as a look up for this image
     *
     * @return
     */
    @Override
    public String getImageName() {
        StringBuilder imageName = new StringBuilder();
        if (this.color == ChessSquare.BoardColor.BLACK.ordinal()) {
            imageName.append("black_");
        } else {
            imageName.append("white_");
        }
        imageName.append(this.type.name());

        return imageName.toString();
    }

    @Override
    public CoOrdinate getPosition() {
        return position;
    }


}
