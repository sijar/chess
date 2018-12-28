/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.pieces;

/**
 *
 * @author Sijar.Ahmed
 */
public class ChessFactory {

   //TYPE
   public enum ChessPieceType{ROOK,NIGHT,BISHOP,QUEEN,KING,PAWNS}; //KNIGHT --> NIGHT

   /**
    * 
    */
   private ChessFactory(){       
   }

   /**
    * Method to create AbstractChessPiece
    *
    * @param piece
    * @param x
    * @param y
    * @param color 
    * @return
    */
   public static AbstractChessPiece createChessPiece(ChessPieceType piece, int x,int y ,int color){
       CoOrdinate position = new CoOrdinate(x,y);
       switch(piece.ordinal()){
           case 0: return new Rook(position,color);           
           case 1: return new Knight(position,color);
           case 2: return new Bishop(position,color);
           case 3: return new Queen(position,color);
           case 4: return new King(position,color);
           case 5: return new Pawn(position,color);
           default: throw new IllegalArgumentException("Unknown Chess Piece Type: " + piece);
       }
   }


   /**
    * 
    * @param charAt
    * @return
    */
    static AbstractChessPiece ObtainPiece(char charAt) {
        switch(charAt){
            case 'P': return new Pawn();
            case 'R': return new Rook();
            case 'N': return new Knight();
            case 'B': return new Bishop();
            case 'Q': return new Queen();
            case 'K': return new King();
        }
        throw new IllegalArgumentException(" invalid character value for chess piece :" + charAt);
    }


}
