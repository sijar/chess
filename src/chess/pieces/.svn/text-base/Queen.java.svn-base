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
class Queen extends AbstractChessPiece {

    Queen() {
        this.type = ChessPieceType.QUEEN;
    }

    /**
     * 
     * @param position
     * @param color
     */
    Queen(CoOrdinate position, int color) {
        this.color = color;
        this.position = position;
        this.type = ChessPieceType.QUEEN;
    }

    @Override
    public String toString() {
        return "(Q)" + position.toString();
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
     * Movement of the queen is a straight line vertically, horizontally, or diagonally,
     * thus combining the moves of the rook and bishop
     *
     * @param source
     * @param destination
     * @return
     */
    @Override
    public boolean isValidMove(CoOrdinate source, CoOrdinate destination) {
        if ((destination.getY() >= 0) && (destination.getY() < IConstants.BOARD_SIZE) && (destination.getX() >= 0) && (destination.getX() < IConstants.BOARD_SIZE)) {
            //horizontal and vertical or diagonal
            if (((source.getX() == destination.getX()) 
                    || (source.getY() == destination.getY()))
                    || ( Math.abs(destination.getX() - source.getX()) == Math.abs(destination.getY() - source.getY()))) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean isCaptureMove(CoOrdinate source, CoOrdinate destination) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        final Queen other = (Queen) obj;
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
        int hash = 5;
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

