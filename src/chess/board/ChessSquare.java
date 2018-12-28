/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.board;

import chess.pieces.AbstractChessPiece;

/**
 *
 * @author Sijar.Ahmed
 */
public class ChessSquare {

    /** <!-- WHITE = 0 ; BLACK = 1 --> */
    public static enum BoardColor {

        WHITE, BLACK
    };
    private int color;
    private AbstractChessPiece piece;

    /**
     * 
     * @param color
     * @param piece
     */
    ChessSquare(int color, AbstractChessPiece piece) {
        this.color = color;
        this.piece = piece;
    }


    /**
     * Private Copy Constructor
     * 
     * @param square
     */
    private ChessSquare(ChessSquare square){
        this.color = square.getColor();
        this.piece = square.getPiece();
    }

    /**
     * Method to create new ChessSqaure 
     *
     * @param square
     * @return
     */
    static ChessSquare createSquare(ChessSquare square){
        return new ChessSquare(square);
    }

    /**
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return piece.toString();
    }

    /**
     * @return the piece
     */
    public AbstractChessPiece getPiece() {
        return piece;
    }

    /**
     * @param piece the piece to set
     */
    void setPiece(AbstractChessPiece piece) {
        this.piece = piece;
    }


    /**
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChessSquare other = (ChessSquare) obj;
        if (this.color != other.color) {
            return false;
        }
        if (this.piece != other.piece && (this.piece == null || !this.piece.equals(other.piece))) {
            return false;
        }
        return true;
    }


    /**
     * 
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.color;
        hash = 67 * hash + (this.piece != null ? this.piece.hashCode() : 0);
        return hash;
    }
    


}
