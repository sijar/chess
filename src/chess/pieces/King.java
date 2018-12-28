/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import chess.board.ChessSquare;
import chess.constants.IConstants;
import chess.pieces.ChessFactory.ChessPieceType;

/**
 *
 * @author Sijar.Ahmed
 */
class King extends AbstractChessPiece {

    /**
     * 
     */
    King() {
        this.type = ChessPieceType.KING;
    }

    /**
     * 
     * @param position
     * @param color
     */
    King(CoOrdinate position, int color) {
        this.color = color;
        this.position = position;
        this.type = ChessPieceType.KING;
    }

    @Override
    public String toString() {
        return "(K)" + position.toString();
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
     * Obtain the Coordinate for the Piece
     *
     * @return
     */
    public CoOrdinate getPosition(){
        return position;
    }

    /**
     * A king can move one empty or enemy-occupied square in any direction (horizontally, vertically, or diagonally) 
     * unless such a move would place the king in check.
     *
     * @param source
     * @param destination
     * @return
     */
    @Override
    public boolean isValidMove(CoOrdinate source, CoOrdinate destination) {
        if ((destination.getY() >= 0) && (destination.getY() < IConstants.BOARD_SIZE) && (destination.getX() >= 0) && (destination.getX() < IConstants.BOARD_SIZE)) {
            //horizontal and vertical or vertical
            if ((Math.abs(destination.getX() - source.getX()) >= 0) && (Math.abs(destination.getX() - source.getX()) <= 1) && (Math.abs(destination.getY() - source.getY()) >= 0) && (Math.abs(destination.getY() - source.getY()) <= 1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method will identify the capture move of the king,
     *
     * @param destination
     * @return
     */
    @Override
    public boolean isCaptureMove(CoOrdinate source, CoOrdinate destination) {
       return isValidMove(source, destination);
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
        final King other = (King) obj;
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


}
