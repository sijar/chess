/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.board;

import chess.constants.IConstants;
import chess.pieces.AbstractChessPiece;
import chess.pieces.ChessFactory;
import chess.pieces.ChessFactory.ChessPieceType;
import chess.pieces.CoOrdinate;
import chess.pieces.Movement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Sijar.Ahmed
 */
public class Board {

    //////////////////////////////////////// FIELDS //////////////////////////////////////////////////
    //PRE-INITIALIZE BOARD
    private ChessSquare[][] chessSquares = new ChessSquare[IConstants.BOARD_SIZE][IConstants.BOARD_SIZE];
    private static Board singleInstance = new Board();

    /**
     * Constructor
     */
    private Board() {
        initBoard(this.chessSquares);
    }

    /**
     * 
     * @return
     */
    public static Board getInstance() {
        return singleInstance;
    }

    //////////////////////////////////////// BUISNESS LOGIC //////////////////////////////////////////////////
    /**
     * Method returns an Unmodified Chess Squares 
     * The return ChessSquare will be an copy of the original
     * 
     * @return
     */
    public ChessSquare[][] getUnmodifiableChessSquares() {

        ChessSquare[][] UnModifiedSquares = new ChessSquare[IConstants.BOARD_SIZE][IConstants.BOARD_SIZE];
        ChessSquare copySquare;

        for (int rowIndex = 0; rowIndex < chessSquares.length; ++rowIndex) {
            for (int colIndex = 0; colIndex < chessSquares[rowIndex].length; ++colIndex) {
                copySquare = new ChessSquare(chessSquares[rowIndex][colIndex].getColor(), chessSquares[rowIndex][colIndex].getPiece());
                UnModifiedSquares[rowIndex][colIndex] = copySquare;
            }
        }
        return UnModifiedSquares;
    }

    /**
     * 
     * @param piece
     * @param command
     * @param movement
     * @return
     * @throws IllegalArgumentException
     */
    private boolean advanceOrCapture(AbstractChessPiece piece, String command, Movement movement) throws IllegalArgumentException {
        if (piece.getType().toString().charAt(0) == command.charAt(0)) {
            //System.out.print("\n%%% CURRENT MOVEMENT COORDINATE :" + movement.toString());
            //NON COLLISION
            if (!isColliding(movement.getToPosition())) {
                //CHECK PAWN
                if ((piece.getType() == ChessFactory.ChessPieceType.PAWNS) && (movement.getTypeOfMovement() == Movement.movementType.DIAGONAL)) {
                    throw new IllegalArgumentException(" invalid diagonal move for PAWN at " + movement.toString());
                }
                destoryPiece(piece, movement, chessSquares);
                return true;
            } else {
                capturePiece(chessSquares, piece, movement);
                return true;
            }
        }
        return false;
    }

    /**
     * Method create a virtual ChessBoard, with updated move of the piece.
     * 
     *
     * @param squares
     * @param kingFromPosition
     * @param kingToPosition
     * @return
     */
    private ChessSquare[][] createVirtualChessBoard(ChessSquare[][] squares, CoOrdinate fromPosition, CoOrdinate toPosition) {
        //if to == from
        if (fromPosition.equals(toPosition)) {
            return squares;
        }
        //analysis the opposite pieces        
        ChessSquare[][] tempSquare = new ChessSquare[IConstants.BOARD_SIZE][IConstants.BOARD_SIZE];
        //System.arraycopy(squares, 0, tempSquare, 0, squares.length);
        for (int i = 0; i < tempSquare.length; ++i) {
            for (int j = 0; j < tempSquare[i].length; ++j) {
                tempSquare[i][j] = ChessSquare.createSquare(squares[i][j]);
            }
        }
        //printBoard(tempSquare);
        AbstractChessPiece tempPiece = chessSquares[fromPosition.getX()][fromPosition.getY()].getPiece();
        //check updating to an empty square or capturing opponents piece
        if (tempSquare[toPosition.getX()][toPosition.getY()].getPiece() == null || tempSquare[toPosition.getX()][toPosition.getY()].getPiece().getColor() != tempPiece.getColor()) {
            //update the king position
            tempSquare[fromPosition.getX()][fromPosition.getY()].setPiece(null);
            tempSquare[toPosition.getX()][toPosition.getY()].setPiece(tempPiece);
        } else {
            throw new IllegalArgumentException("%% invalid move : " + tempPiece.getType() + " can't capture its own piece type");
        }
        return tempSquare;
    }

    /***
     *
     * @param piece
     * @param movement
     * @throws IllegalArgumentException
     */
    private void handleCheckMate(AbstractChessPiece piece, Movement movement) throws IllegalArgumentException {
        if ((piece.getType() == ChessPieceType.KING) && isChecked(movement.getFromPosition(), movement.getToPosition(), piece.getColor(), chessSquares)) {
            //check for check-mate
            if (isCheckMate(movement.getFromPosition(), chessSquares)) {
                //System.out.print("\n%%  ChessEngine Report : No more moves are possible, King is in checkmate position ");
                throw new IllegalArgumentException(" ChessEngine Report : No more moves are possible, King is in checkmate position ");
            //System.exit(100);
            }
            //System.out.print("\n%% KING IS CHECKED :");
            //printBoard(chessSquares);
            throw new IllegalArgumentException(" invalid move command: King is under a check position ");
        }
    }

    /**
     * API to identify the King CheckMate status
     * The idea is to first obtain all the position that King can reach.
     * The run a check algorithm on this in-order to figure out that
     * 
     * @param movement
     * @param chessSquares
     * @return
     */
    private boolean isCheckMate(CoOrdinate currentPosition, ChessSquare[][] chessSquares) {

        //System.out.println("\n%% Computing CheckMate State :" + currentPosition.toString());
        List<CoOrdinate> reachableCoordinates = obtainEscapingPosition(currentPosition, chessSquares);
        //System.out.println("\n%% Obtained Unoccupied Coordinates are :" + reachableCoordinates.toString());
        //check whether position locked
        if (reachableCoordinates.isEmpty()) {
            return true;
        }
        //check whether
        for (CoOrdinate escapeCoOrdinate : reachableCoordinates) {
            if (!isChecked(currentPosition, escapeCoOrdinate, chessSquares[currentPosition.getX()][currentPosition.getY()].getPiece().getColor(), chessSquares)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method will return a list of unoccupied positions for the King
     * to escape the check-condition
     *
     * @param checkedPosition
     * @param chessSquares
     * @return
     */
    private List<CoOrdinate> obtainEscapingPosition(CoOrdinate currentPosition, ChessSquare[][] chessSquares) {

        List<CoOrdinate> unOccupiedCoOrdinates = new LinkedList<CoOrdinate>();
        final int currentX = currentPosition.getX();
        final int currentY = currentPosition.getY();
        //System.out.print("\n%% Source Escape Coordinates :" + currentX + currentY);

        for (int i = minimaOf(currentX); i <= maximaOf(currentX); ++i) {
            for (int j = minimaOf(currentY); j <= maximaOf(currentY); ++j) {
                //System.out.print("\n%% Searching for escape Coordinate : [ " + i + " , " + j + " ]");
                //skip computing on current position as it already checked
                if ((i == currentX) && (j == currentY)) {
                    continue;
                }
                //abort on duplication
                if (unOccupiedCoOrdinates.contains(new CoOrdinate(i, j))) {
                    return unOccupiedCoOrdinates;
                }
                //add if empty
                if (chessSquares[i][j].getPiece() == null) {
                    unOccupiedCoOrdinates.add(new CoOrdinate(i, j));
                }
            }
        }
        return unOccupiedCoOrdinates;
    }

    /**
     * 
     * @param chessSquares
     */
    private void setChessSquares(ChessSquare[][] chessSquares) {
        this.chessSquares = chessSquares;
    }

    /**
     * Initialize the board
     * @param chessSquares
     */
    private void initBoard(ChessSquare[][] chessSquares) {
        //Final Row - White
        ChessSquare.BoardColor rowStartColor = ChessSquare.BoardColor.WHITE;
        //fill up the board from left to right
        for (int i = 0; i < chessSquares.length; ++i) {
            ChessSquare.BoardColor colStartColor = rowStartColor;
            for (int j = 0; j < chessSquares[i].length; ++j) {
                chessSquares[i][j] = new ChessSquare(colStartColor.ordinal(), fillByRule(i, j));
                //change alternate color
                colStartColor = (colStartColor == colStartColor.BLACK) ? colStartColor.WHITE : colStartColor.BLACK;
            }
            //change to opposite
            rowStartColor = (rowStartColor == rowStartColor.BLACK) ? rowStartColor.WHITE : rowStartColor.BLACK;
        }
    }

    /**
     * Method will return AbstractChessPiece according to the position on the board
     * The configuration is the default setting at the start of a new game
     *
     * The 1st row/col will be filled by WHITE pieces
     * The last row/col will be filled by BLACK pieces
     * (refer http://en.wikipedia.org/wiki/Chess)
     * @param i
     * @param j
     * @return AbstractChessPiece
     */
    private AbstractChessPiece fillByRule(int row, int col) {
        int MAX = this.chessSquares.length - 1; //loop runs till MAX-1
        //return the pawns
        //TOP PIECE; WHITE COLOR
        if (row == 1) {
            return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.PAWNS, row, col, ChessSquare.BoardColor.WHITE.ordinal());
        }
        //TOP PIECE; BLACK COLOR
        if (row == MAX - 1) {
            return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.PAWNS, row, col, ChessSquare.BoardColor.BLACK.ordinal());
        }
        //return a Chess for valid row and column
        if (row == 0) {
            int color = ChessSquare.BoardColor.WHITE.ordinal();
            switch (col) {
                case 0: //flow through as ROOK for both
                case 7:
                    return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.ROOK, row, col, color);

                case 1: //flow through as NIGHT for both
                case 6:
                    return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.NIGHT, row, col, color);

                case 2: //flow through as BISHOP for both
                case 5:
                    return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.BISHOP, row, col, color);

                case 3:
                    return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.QUEEN, row, col, color);
                case 4:
                    return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.KING, row, col, color);
            }
        }
        if (row == MAX) {
            int color = ChessSquare.BoardColor.BLACK.ordinal();
            switch (col) {
                case 0: //flow through as ROOK for both
                case 7:
                    return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.ROOK, row, col, color);

                case 1: //flow through as NIGHT for both
                case 6:
                    return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.NIGHT, row, col, color);

                case 2: //flow through as BISHOP for both
                case 5:
                    return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.BISHOP, row, col, color);

                case 3:
                    return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.QUEEN, row, col, color);
                case 4:
                    return ChessFactory.createChessPiece(ChessFactory.ChessPieceType.KING, row, col, color);
            }
        }
        return null;
    }

    /**
     * PrintBoard
     *
     */
    final void printBoard(ChessSquare[][] squares) {
        //System.out.println("\n------------------------------------- CURRENT CHESS BOARD [Y AXIS] ---------------------------------------------------> ");
        //System.out.println("\n|------------|------------|------------|------------|------------|------------|------------|------------|");
        for (int i = 0; (i < squares.length); ++i) {
            //System.out.print("|");
            for (int j = 0; (j < squares[i].length); ++j) {
                //if(chessSquares[i][j] != null){
                if (squares[i][j].getColor() == ChessSquare.BoardColor.BLACK.ordinal()) {
                    //System.out.print("  B");
                    if (squares[i][j].getPiece() != null) {
                        //System.out.print(squares[i][j].getPiece().toString() + " |");
                    } else {
                        //System.out.print("         |");
                    }
                } else {
                    //System.out.print("  W");
                    if (squares[i][j].getPiece() != null) {
                        //System.out.print(squares[i][j].getPiece().toString() + " |");
                    } else {
                        //System.out.print("         |");
                    }
                }
            }
            //System.out.println("\n|------------|------------|------------|------------|------------|------------|------------|------------|");
        }
    }

    /**
     * Method will move the piece on the board
     * Chess Piece is moved only if the movement satisfy all the rules of chess game.
     * Method will move the piece on the board
     * Chess Piece is moved only if the movement satisfy all the rules of chess game.
     * 
     *
     * @param command
     * @return
     */
    public boolean movePiece(String command) throws IllegalArgumentException {
        try {
            //System.out.print("\n%% move command recieved :" + command);
            //Verify the piece move, fill up the from & to coordinate
            Movement movement = new Movement(command);
            //System.out.print("\n%% Movement Information :" + movement.toString());
            //validate existence of the piece
            AbstractChessPiece piece = chessSquares[movement.getFromPosition().getX()][movement.getFromPosition().getY()].getPiece();
            //VALIDATE MOVE
            if (!piece.isValidMove(movement.getFromPosition(), movement.getToPosition())) {
                throw new IllegalArgumentException(" Invalid move command for " + piece.getType() + " movement from-to " + movement.getFromPosition().toString() + movement.getToPosition().toString());
            }
            if (piece == null) {
                throw new IllegalArgumentException(" invalid move command: Piece does not exist ");
            }
            //verify the kings is not under threat position
            if ((ChessPieceType.KING.ordinal() != piece.getType().ordinal()) && isKingUnderThreat(piece.getColor(), chessSquares)) {
                throw new IllegalArgumentException(" invalid move command: Resolve the King's check condition");
            }
            //verify the kings won't get into threat position
            final ChessSquare[][] virtualBoard = createVirtualChessBoard(chessSquares, movement.getFromPosition(), movement.getToPosition());
            if ((ChessPieceType.KING.ordinal() != piece.getType().ordinal()) && isKingUnderThreat(piece.getColor(), virtualBoard)) {
                throw new IllegalArgumentException(" invalid move command: Movement will put King under check threat");
            }

            //handle the check & checkmate
            handleCheckMate(piece, movement);
            //System.out.print("\n%% PIECE NAME :" + piece.getType().toString());
            //Check if KING IS CHECK
            if ((piece.getType() == ChessPieceType.KING) && isChecked(movement.getFromPosition(), movement.getToPosition(), piece.getColor(), chessSquares)) {
                //System.out.print("\n%% KING IS CHECKED :");
                //return false;
                printBoard(chessSquares);
                throw new IllegalArgumentException(" invalid move command: King is under a check position ");
            }
            //System.out.print("\n%% PIECE NAME :" + piece.getType().toString());
            //check whether the piece is block in b/w the destination
            if (!isIterativelyReachable(chessSquares, piece, movement)) {
                //System.out.print("\n%% Destination can not be reached, Chess Piece getting blocked ");
                return false;
            }
            //move
            if (advanceOrCapture(piece, command, movement)) {
                return true;
            }
            if (piece.getType().toString().charAt(0) == command.charAt(0)) {
                //System.out.print("\n%% CURRENT MOVEMENT COORDINATE :" + movement.toString());
                //NON COLLISION
                if (!isColliding(movement.getToPosition())) {
                    //CHECK PAWN
                    if ((piece.getType() == ChessFactory.ChessPieceType.PAWNS) && (movement.getTypeOfMovement() == Movement.movementType.DIAGONAL)) {
                        throw new IllegalArgumentException(" invalid diagonal move for PAWN at " + movement.toString());
                    }
                    destoryPiece(piece, movement, chessSquares);
                    //printBoard(chessSquares);
                    return true;
                //COLLIDING
                } else {
                    capturePiece(chessSquares, piece, movement);
                    //printBoard(chessSquares);
                    return true;
                }
            }
        } catch (IllegalArgumentException exp) {
            //System.out.println(exp.getMessage());
            //return false;
            throw exp;
        }
        return false;
    }

    /**
     * Method to handle the colliding of the chess pieces
     *
     * @param toPosition
     * @return true if a piece already exits; false otherwise
     */
    private boolean isColliding(CoOrdinate position) {
        //System.out.print("\n%% Detecting Colloision:" + chessSquares[position.getX()][position.getY()].getPiece());
        return (chessSquares[position.getX()][position.getY()].getPiece() == null) ? false : true;
    }

    /**
     * Capture other piece
     *
     * @param chessSquares
     * @param piece
     * @param movement
     */
    private void capturePiece(ChessSquare[][] chessSquares, AbstractChessPiece piece, Movement movement) {
        //check on PAWNS
        if (piece.getType() == ChessFactory.ChessPieceType.PAWNS) {
            //forward movement
            if (movement.getTypeOfMovement() == Movement.movementType.VERTICAL) {
                throw new IllegalArgumentException(" movement is not allowed for Pawn at" + movement.toString());
            //diagonal movement
            } else if (movement.getTypeOfMovement() == Movement.movementType.DIAGONAL) {
                destoryPiece(piece, movement, chessSquares);
            }
            //check correct capture
            if (piece.isCaptureMove(movement.getFromPosition(), movement.getToPosition())) {
                destoryPiece(piece, movement, chessSquares);
            }
        } else {
            destoryPiece(piece, movement, chessSquares);
        }
    }

    /**
     * Method will the destroy the piece if only the piece does not belong to the same color/side
     * All the pieces having same color are treated as on the same side.
     *
     * @param piece
     * @param movement
     * @param chessSquares
     */
    private void destoryPiece(AbstractChessPiece piece, Movement movement, ChessSquare[][] chessSquares) {

        if ((chessSquares != null) && (movement != null) && (piece != null)) {
            //printCoordinates(movement.getFromPosition().getX(), movement.getFromPosition().getY(),movement.getToPosition().getX(),movement.getToPosition().getY());
            final AbstractChessPiece srcPiece = chessSquares[movement.getFromPosition().getX()][movement.getFromPosition().getY()].getPiece();
            final AbstractChessPiece destPiece = chessSquares[movement.getToPosition().getX()][movement.getToPosition().getY()].getPiece();
            //verify chess piece type
            if ((srcPiece != null) && (destPiece != null) && (srcPiece.getColor() == destPiece.getColor())) {
                throw new IllegalArgumentException("killing own's piece, invalid move piece at" + movement.toString() + " have the same color as " + movement.toString());
            }
            //update the piece's position
            piece.setPosition(movement.getToPosition());
            //move the piece
            chessSquares[movement.getToPosition().getX()][movement.getToPosition().getY()].setPiece(piece);
            //update old
            chessSquares[movement.getFromPosition().getX()][movement.getFromPosition().getY()].setPiece(null);
        }
    }

    /**
     * Method will iteratively check whether the piece is reachable i.e.
     * Piece is reached without any blockage
     *
     * @param chessSquares
     * @param piece
     * @param movement
     * @return
     */
    private boolean isIterativelyReachable(ChessSquare[][] chessSquares, AbstractChessPiece piece, Movement movement) {


        printCoordinates(movement.getFromPosition().getX(), movement.getFromPosition().getY(), movement.getToPosition().getX(), movement.getToPosition().getY());
        printCoordinates(movement.getFromPosition().getX(), movement.getFromPosition().getY(), movement.getToPosition().getX(), movement.getToPosition().getY());
        //parse by the movement
        if (movement == null || piece == null || chessSquares == null) {
            return false;
        }
        //System.out.print("\n%% PIECE INFORMATION :" + piece.toString());
        //VERTICAL
        if (movement.getTypeOfMovement() == Movement.movementType.VERTICAL) {
            return verticalIteration(movement, chessSquares);
        }
        //HORIZONTAL
        if (movement.getTypeOfMovement() == Movement.movementType.HORIZONTAL) {
            return horizontalIteration(movement, chessSquares);
        }
        //LSHAPED
        if (movement.getTypeOfMovement() == Movement.movementType.LSHAPED) {
            return true; //Knight can jump
        }
        //DIAGONAL
        printBoard(chessSquares);
        if (movement.getTypeOfMovement() == Movement.movementType.DIAGONAL) {
            return diagonalIteration(movement, chessSquares);
        }
        return false;
    }

    /**
     * Compare two pieces whether they are same or not
     *
     * @param chessSquare
     * @param chessSquare0
     * @return
     */
    private boolean compareCoordinates(CoOrdinate srcCoordinate , CoOrdinate dstCoordinate) {        
        if ((srcCoordinate == null) && (dstCoordinate == null)) {
            return false;
        }
        //System.out.print("\n%% Comparing :" + srcCoordinate.toString() + " VS " + dstCoordinate.toString());
        return srcCoordinate.equals(dstCoordinate);
    }

    //////////////////////////////////////////////MAIN METHOD///////////////////////////////////////////
    /**
     * Main Method
     *
     * @param args
     */
    public static void main(String[] args) {
        System.exit(1);
//        Board board = new Board();
//        //board.printBoard(board.chessSquares);
//        while (true) {
//            if (board.movePiece(CommandReader.readCommand().trim())) {
//                //board.printBoard(board.chessSquares);
//            } else {
//                System.err.println("%% Invalid Move");
//            }
//        }
//        Board board = new Board();
//        //board.printBoard(board.chessSquares);
//        while (true) {
//            if (board.movePiece(CommandReader.readCommand().trim())) {
//                //board.printBoard(board.chessSquares);
//            } else {
//                System.err.println("%% Invalid Move");
//            }
//        }
    }

    /**
     * Print the coordinates
     *
     * @param srcX
     * @param srcY
     * @param destX
     * @param destY
     */
    private void printCoordinates(int srcX, int srcY, int destX, int destY) {
        //System.out.print("\n%% COORDINATES :");
        //System.out.print(" Source :[" + srcX);
        //System.out.print(" , " + srcY);
        //System.out.print("] Destination :[" + destX);
        //System.out.print(" , " + destY);
        //System.out.print("]");
    }

    /**
     * Vertical Iteration
     *
     * @param movement
     * @param srcX
     * @param srcY
     * @param destX
     * @param destY
     * @param chessSquares
     * @param index
     * @return
     */
    private boolean horizontalIteration(Movement movement, ChessSquare[][] chessSquares) {

        int srcX = 0, srcY = 0, destX = 0, destY = 0, index = -1; //local variables
        //set max & min
        if (movement.getFromPosition().getY() > movement.getToPosition().getY()) {
            //obtain swapped coordinates
            srcX = movement.getToPosition().getX();
            srcY = movement.getToPosition().getY();
            destX = movement.getFromPosition().getX();
            destY = movement.getFromPosition().getY();
        } else {
            srcX = movement.getFromPosition().getX();
            srcY = movement.getFromPosition().getY();
            destX = movement.getToPosition().getX();
            destY = movement.getToPosition().getY();
        }
        index = 1; //starts from next one
        printCoordinates(srcX, srcY, destX, destY);
        //traverse iteratively
        while ((chessSquares[srcX][srcY + index].getPiece() == null) && (srcY + index < destY)) {
            ++index;
        }
        //System.out.print("\n%% Checking Whether Horizontal Iteratively Reachable :");
        //compare piece at index and destination
        return compareCoordinates(new CoOrdinate(srcX, srcY + index), new CoOrdinate(destX,destY));
    }

    /**
     * Horizontal Iteration
     *
     * @param movement
     * @param srcX
     * @param srcY
     * @param destX
     * @param destY
     * @param chessSquares
     * @param index
     * @return
     */
    private boolean verticalIteration(Movement movement, ChessSquare[][] chessSquares) {

        int srcX = 0, srcY = 0, destX = 0, destY = 0, index = -1; //local variables
        //set max & min
        if (movement.getFromPosition().getX() > movement.getToPosition().getX()) {
            //System.out.print("\n%%Coordinate swapped.....");
            //obtain swapped coordinates
            srcX = movement.getToPosition().getX();
            srcY = movement.getToPosition().getY();
            destX = movement.getFromPosition().getX();
            destY = movement.getFromPosition().getY();
        } else {
            srcX = movement.getFromPosition().getX();
            srcY = movement.getFromPosition().getY();
            destX = movement.getToPosition().getX();
            destY = movement.getToPosition().getY();
        }
        index = 1; //starts from next one
        printCoordinates(srcX, srcY, destX, destY);
        //printBoard(chessSquares);
        //traverse iteratively
        while ((chessSquares[srcX + index][srcY].getPiece() == null) && (srcX + index < destX)) {
            ++index;
        }
        //System.out.print("\n%% Checking Whether Vertically Iteratively Reachable :");
        //compare piece at index and destination
        return compareCoordinates(new CoOrdinate(srcX + index,srcY),new CoOrdinate(destX,destY));
    }

    /**
     * Diagonal Iteration
     *
     * @param movement
     * @param srcX
     * @param srcY
     * @param destX
     * @param destY
     * @param chessSquares
     * @param index
     * @return
     */
    private boolean diagonalIteration(Movement movement, ChessSquare[][] chessSquares) {

        int srcX = 0, srcY = 0, destX = 0, destY = 0;
        int index = 1; //starts from next one
        srcX = movement.getFromPosition().getX();
        srcY = movement.getFromPosition().getY();
        destX = movement.getToPosition().getX();
        destY = movement.getToPosition().getY();

        //System.out.print("\n%% Computing diagonal movement iteration ...");
        //traverse diagonally iteratively
        /////////////////////////////////////(+)(+)/////////////////////////////////////////////
        if ((srcY < destY) && (srcX < destX)) {
            //System.out.print("\n%% Checking diagonally ... (+)(+):");
            while ((chessSquares[srcX + index][srcY + index].getPiece() == null) && (srcX + index < destX) && (srcY + index < destY)) {                
                ++index;
            }            
            return compareCoordinates(new CoOrdinate(srcX + index,srcY + index), new CoOrdinate(destX,destY));//compare piece at index and destination
        //////////////////////////////////(-)(+) ////////////////////////////////////////////
        } else if ((srcY < destY) && (srcX > destX)) {
            //System.out.print("\n%% Checking diagonally ... (-)(+):");
            while ((chessSquares[srcX - index][srcY + index].getPiece() == null) && (srcX - index > destX) && (srcY + index < destY)) {                
                ++index;
            }            
            return compareCoordinates(new CoOrdinate(srcX - index,srcY + index),new CoOrdinate(destX,destY));
        ////////////////////////////////// (-)(-) ////////////////////////////////////////////
        } else if ((srcY > destY) && (srcX > destX)) {
            //System.out.print("\n%% Checking diagonally ... (-)(-):");
            while ((chessSquares[srcX - index][srcY - index].getPiece() == null) && (srcX - index > destX) && (srcY - index > destY)) {
                printCoordinates(srcX, srcY, destX, destY);
                ++index;
            }            
            return compareCoordinates(new CoOrdinate(srcX - index,srcY - index), new CoOrdinate(destX,destY));
        //////////////////////////////////(+)(-) ////////////////////////////////////////////
        } else if ((srcY > destY) && (srcX < destX)) {
            //System.out.print("\n%% Checking diagonally ... (+)(-):");
            while ((chessSquares[srcX + index][srcY - index].getPiece() == null) && (srcX + index < destX) && (srcY - index > destY)) {                
                ++index;
            }            
            return compareCoordinates(new CoOrdinate(srcX + index,srcY - index),new CoOrdinate(destX,destY));
        }
        return false;
    }

    /**
     * Obtain the Chess Piece for the position
     * 
     * @param x
     * @param y 
     * @return
     */
    public AbstractChessPiece getPiece(int x, int y) {
        //System.out.print("\n%% X :" + x + " Y :" + y);
        return chessSquares[x][y].getPiece();
    }

    /**
     * Method to validate the Pieces of type BoardColor for check condition
     * The Piece is checked if any of the opponent's piece can reach the King.
     *
     * @param color
     * @param squares
     * @return true if there's a checked condition; otherwise false
     */
    private boolean isChecked(CoOrdinate kingFromPosition, CoOrdinate kingToPosition, int color, ChessSquare[][] squares) {

        //System.out.println("%% Piece Color :" + color);
        Movement movement = new Movement();
        //virtual movement
        ChessSquare[][] tempSquare = createVirtualChessBoard(squares, kingFromPosition, kingToPosition);
        //check whether iteratively reachable
        for (ChessSquare[] row : tempSquare) {
            for (ChessSquare col : row) {
                if ((col.getPiece() != null) && (color != col.getPiece().getColor())) {
                    //System.out.print("\n%% KING'S COORDINATES :" + kingToPosition + " Opponent's Piece Coordinate :" + col.toString() + " COLOR :" + col.getPiece().getColor());
                    //iteratively check the reach of each piece to the KING
                    movement.updateCordinates(col.getPiece().getPosition(), kingToPosition);
                    if (isIterativelyCapturable(tempSquare, movement)) {
                        //System.out.print("\n%% [King is checked by " + col.getPiece().toString() + "]");
                        return true;
                    } else {
                        //System.out.print("\n%% No direct path to KING :");
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method returns the King-'s Coordinates in the following format
     * "x0y0", where the x0 = X Coordinate & y0 = Y Coordinate
     * 
     * @param king_Color
     * @return
     */
    private CoOrdinate getKingsCoordinate(int king_Color) {
        CoOrdinate xoyo = null;
        for (ChessSquare[] row : chessSquares) {
            for (ChessSquare col : row) {
                if ((col.getPiece() != null) && (ChessFactory.ChessPieceType.KING == col.getPiece().getType()) && (king_Color == col.getPiece().getColor())) {
                    xoyo = new CoOrdinate(col.getPiece().getPosition().getX(), col.getPiece().getPosition().getY());
                }
            }
        }
        return xoyo;
    }

    /**
     * Method to identify whether the given piece is iteratively reachable or not.
     * The idea here is to check whether the source piece is capture for a given movement.<br/>
     * All the piece except <code>Knight<code/> and <code>Pawn<code/> have the freedom to reach the entire chess board
     * in their allowed movement until they are blocked of a piece.<br/>
     * For <b>Knight</b> the movement is the 'L' shaped in 360*, but for <b>Pawn</b>
     * the capture move is different than its <i>no capture</i> which is forward movement.<br/>
     *
     * @param tempSquare
     * @param sourcePiece
     * @param moveement
     * @return true if the sourcePiece is captured; false otherwise
     */
    private boolean isIterativelyCapturable(ChessSquare[][] tempSquare, Movement movement) {
        printCoordinates(movement.getFromPosition().getX(), movement.getFromPosition().getY(), movement.getToPosition().getX(), movement.getToPosition().getY());

        AbstractChessPiece srcPiece = tempSquare[movement.getFromPosition().getX()][movement.getFromPosition().getY()].getPiece();
        AbstractChessPiece destPiece = tempSquare[movement.getToPosition().getX()][movement.getToPosition().getY()].getPiece();

        //System.out.print("\n%% SOURCE PIECE INFORMATION %% " + srcPiece);
        //System.out.print("\n%% DESTINATION PIECE INFORMATION %% " + destPiece);

        //parse by the movement
        if (movement == null || srcPiece == null || destPiece == null || chessSquares == null) {
            return false;
        }
        //check movement feasible by the piece
        if (!srcPiece.isValidMove(movement.getFromPosition(), movement.getToPosition())) {
            return false;
        }
        //VERTICAL
        if (!(ChessPieceType.PAWNS == srcPiece.getType()) && (movement.getTypeOfMovement() == Movement.movementType.VERTICAL)) {
            if (ChessPieceType.KING == srcPiece.getType() && !(srcPiece.isCaptureMove(movement.getFromPosition(), movement.getToPosition()))) {
                return false;
            }
            return verticalIteration(movement, tempSquare);
        }
        //HORIZONTAL
        if (!(ChessPieceType.PAWNS == srcPiece.getType()) && (movement.getTypeOfMovement() == Movement.movementType.HORIZONTAL)) {
            if (ChessPieceType.KING == srcPiece.getType() && !(srcPiece.isCaptureMove(movement.getFromPosition(), movement.getToPosition()))) {
                return false;
            }
            return horizontalIteration(movement, tempSquare);
        }
        //LSHAPED
        if ((ChessPieceType.NIGHT == srcPiece.getType()) && movement.getTypeOfMovement() == Movement.movementType.LSHAPED) {
            //is this Night capture move
            return srcPiece.isCaptureMove(movement.getFromPosition(), movement.getToPosition());
        }
        //DIAGONAL
        if (movement.getTypeOfMovement() == Movement.movementType.DIAGONAL) {
            //validate capture of Pawns
            if (ChessPieceType.PAWNS == srcPiece.getType()) {
                return srcPiece.isCaptureMove(movement.getFromPosition(), movement.getToPosition());
            }
            if (ChessPieceType.KING == srcPiece.getType() && !(srcPiece.isCaptureMove(movement.getFromPosition(), movement.getToPosition()))) {
                return false;
            }
            return diagonalIteration(movement, tempSquare);
        }
        return false;
    }

    /**
     * Method to generate the range on
     * @param currentX
     * @return
     */
    private int minimaOf(int currentX) {
        return (currentX - 1) < 0 ? 0 : (currentX - 1);
    }

    /**
     * 
     * @param currentX
     * @return
     */
    private int maximaOf(int currentX) {
        //chessBoard[][] 0-7
        return (currentX + 1) > (IConstants.BOARD_SIZE - 1) ? (IConstants.BOARD_SIZE - 1) : (currentX + 1);
    }

    /**
     * Method will identify that Kings check condition
     * 
     * @param color
     * @param chessSquares
     * @return
     */
    private boolean isKingUnderThreat(int color, ChessSquare[][] chessSquares) {
        final CoOrdinate kingXY = getKingsCoordinate(color);
        return isChecked(kingXY, kingXY, color, chessSquares);
    }
//    /**
//     * Method to validate the Pieces of type BoardColor for check condition
//     * The Piece is checked if any of the opponent's piece can reach the King.
//     *
//     * @param color
//     * @param squares
//     * @return true if there's a checked condition; otherwise false
//     */
//    private boolean isChecked(CoOrdinate kingFromPosition, CoOrdinate kingToPosition, int color, ChessSquare[][] squares) {
//
//        //System.out.println("%%% Piece Color :" + color);
//        Movement movement = new Movement();
//        //virtual movement
//        ChessSquare[][] tempSquare = createVirtualChessBoard(squares, kingFromPosition, kingToPosition);
//        //check whether iteratively reachable
//        for (ChessSquare[] row : squares) {
//            for (ChessSquare col : row) {
//                if ((col.getPiece() != null) && (color != col.getPiece().getColor())) {
//                    //System.out.println(" KING'S COORDINATES :" + kingToPosition + " Opponent's Piece Coordinate :" + col.toString() + " COLOR :" + col.getPiece().getColor());
//                    //iteratively check the reach of each piece to the KING
//                    movement.updateCordinates(col.getPiece().getPosition() , kingToPosition);
//                    if (isIterativelyReachable(tempSquare, col.getPiece(), movement)) {
//                        //System.out.println("|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|CHECKED|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|");
//                        return true;
//                    }else{
//                        //System.out.println("|%|%|%|% CAN'T REACH |%|%|%|");
//                    }
//                }
//            }
//        }
//        return false;
//    }
//    /**
//     * Method returns the King-'s Coordinates in the following format
//     * "x0y0", where the x0 = X Coordinate & y0 = Y Coordinate
//     *
//     * @param king_Color
//     * @return
//     */
//    private String getKingsCoordinate(int king_Color) {
//
//        String coOrdinates = null;
//
//        for (ChessSquare[] row : chessSquares) {
//            for (ChessSquare col : row) {
//                if ((col.getPiece() != null) && (ChessFactory.ChessPieceType.KING == col.getPiece().getType()) && (king_Color == col.getPiece().getColor())) {
//                    coOrdinates = String.valueOf(col.getPiece().getPosition().getX());
//                    coOrdinates += col.getPiece().getPosition().getY();
//                }
//            }
//        }
//        return coOrdinates;
//    }
}
