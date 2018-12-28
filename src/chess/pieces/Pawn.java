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
class Pawn extends AbstractChessPiece {

    /**
     * 
     * @param postion
     * @param color
     */
    public Pawn(CoOrdinate postion, int color) {
        this.color = color;
        this.position = postion;
        this.type = ChessPieceType.PAWNS;
    }

    Pawn() {
        this.type = ChessPieceType.PAWNS;
    }

    @Override
    public String toString() {
        return "(P)" + position.toString();
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
     * Movement of the pawn is forward always, backward not allowed
     * also the diagonal is allowed only during the capturing time
     *
     * @param source
     * @param destination
     * @return
     */
    @Override
    public boolean isValidMove(CoOrdinate source, CoOrdinate destination) {
        printPieceInformation(source, destination);
        //WHITE pieces (@Bottom HigherX)
        if (this.color == ChessSquare.BoardColor.BLACK.ordinal()) {
            if ((destination.getY() >= 0) && (destination.getY() < IConstants.BOARD_SIZE) && (destination.getX() >= 0) && (destination.getX() < IConstants.BOARD_SIZE)) {
                //forwards
                if( (source.getX() - destination.getX() == 1) && (source.getY() == destination.getY())) {
                    return true;
                //diagonal
                } else if( (source.getX() -destination.getX() == 1) && (Math.abs(destination.getX() - source.getX()) == Math.abs(destination.getY() - source.getY()))) {
                    return true;
                }
            }
        //BLACK pieces (@Top LowerX)
        } else if (this.color == ChessSquare.BoardColor.WHITE.ordinal()) {
            if ((destination.getY() >= 0) && (destination.getY() < IConstants.BOARD_SIZE) && (destination.getX() >= 0) && (destination.getX() < IConstants.BOARD_SIZE)) {
                //forwards
                if ((destination.getX() - source.getX() == 1) && (source.getY() == destination.getY())) {
                    return true;
                //diagonal
                } else if( (destination.getX() - source.getX() == 1) && (Math.abs(destination.getX() - source.getX()) == Math.abs(destination.getY() - source.getY()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * Method to verify correct capture move
     *
     * @param source
     * @param destination
     * @return
     */
    @Override
    public boolean isCaptureMove(CoOrdinate source, CoOrdinate destination) {
        if (source != null && destination != null) {
            if ((destination.getY() >= 0) && (destination.getY() < IConstants.BOARD_SIZE) && (destination.getX() >= 0) && (destination.getX() < IConstants.BOARD_SIZE)) {
                //diagonal 1 step forward
                if ((Math.abs(destination.getX() - source.getX()) == 1) && (Math.abs(destination.getY() - source.getY()) == 1)) {
                    return true;
                }
            }
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
        final Pawn other = (Pawn) obj;
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


    private void printPieceInformation(CoOrdinate source, CoOrdinate destination) {
        //System.out.print("\n%% Source Coordinate :" + source.toString());
        //System.out.print(", Destination Coordinate :" + destination.toString());
        //System.out.print(", Color :" + this.color);
        //System.out.print(", Type :" + this.type);
    }


    @Override
    public CoOrdinate getPosition() {
        return position;
    }

}
