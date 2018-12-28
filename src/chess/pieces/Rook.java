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
public class Rook extends AbstractChessPiece{

    public Rook(CoOrdinate position, int color) {
        this.position = position;
        this.color = color;
        this.type = ChessPieceType.ROOK;
    }

    Rook() {
        this.type = ChessPieceType.ROOK;
    }

    @Override
    public String toString() {
        return "(R)" + position.toString();
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
     * Movement of a Rook is horizontally or vertically, forward or backward,
     *
     * @param source
     * @param destination
     * @return
     */
    @Override
    public boolean isValidMove(CoOrdinate source, CoOrdinate destination) {
        if ((destination.getY() >= 0) && (destination.getY() < IConstants.BOARD_SIZE) && (destination.getX() >= 0) && (destination.getX() < IConstants.BOARD_SIZE)) {
            if ((source.getX() == destination.getX()) || (source.getY() == destination.getY())) {
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
        final Rook other = (Rook) obj;
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
        int hash = 3;
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
