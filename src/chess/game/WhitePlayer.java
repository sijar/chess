/*
 * Developed by Sijar Ahmed on 18/2/19 12:53 AM
 * Last modified 6/2/19 11:22 PM.
 * Sijar Ahmed (sijar.ahmed@gmail.com)
 * Copyright (c) 2019. All rights reserved.
 *
 *
 * The Class / Interface WhitePlayer is responsible for...
 * @author sijarahmed
 * 18/2/19 12:53 AM
 *
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.game;

import chess.board.Board;
import chess.board.ChessSquare;
import chess.pieces.ChessPiece;
import chess.timer.Clock;
import java.util.Calendar;
import java.util.List;

/**
 * Singleton class
 * 
 * @author Sijar.Ahmed
 */
public class WhitePlayer implements IPlayerInformation{


    private char gameResult = 'L';  //Lost,Won,Tie
    private Clock timeTaken;
    private boolean turnOn;
    private static WhitePlayer singletonInstance = new WhitePlayer();

    /**
     * 
     */
    private WhitePlayer() {
        int score = 0;
        timeTaken = new Clock();
        turnOn = false;
    }

    /**
     *
     * @return
     */
    public static WhitePlayer initWhitePlayer(){
        return singletonInstance;
    }


    /**
     * 
     * @param turnStatus
     * @return 
     */
    public boolean updateTurnStatus(boolean turnStatus){
        setTurnOn(turnStatus);
        if (turnStatus) {
            //set the curent time
            Calendar calendar = Calendar.getInstance();
            timeTaken = new Clock();
            timeTaken.setHour(calendar.get(Calendar.HOUR));
            timeTaken.setMin(calendar.get(Calendar.MINUTE));
            timeTaken.setSec(calendar.get(Calendar.SECOND));
        }
        return isTurnOn();
    }

    /**
     * @return the turnOn
     */
    public boolean isTurnOn() {
        return turnOn;
    }

    /**
     * @param turnOn the turnOn to set
     */
    private void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
        if (!turnOn) {
            //calcuate time taken
            Calendar calendar = Calendar.getInstance();
            timeTaken.setHour(calendar.get(Calendar.HOUR) - timeTaken.getHour());
            timeTaken.setMin(calendar.get(Calendar.MINUTE) - timeTaken.getMin());
            timeTaken.setSec(calendar.get(Calendar.SECOND) - timeTaken.getSec());
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public String toString() {
        StringBuilder data = new StringBuilder();
        data.append("\n Score=");data.append(fetchScoreInfo());
        data.append("\n TimeTaken=");data.append(fetchGrossTimeInfo());
        data.append("\n TurnOn=");data.append(fetchTurnInfo());
        data.append("\n Peices Remaining=");data.append(fetchNo_of_PiecesRemaingInfo());
        data.append("\n Pieces Lost=");data.append(fetchNo_of_PiecesLostInfo());
        return data.toString();
    }

    public String fetchTurnInfo() {
        return isTurnOn() ? " Active " : " Inactive " ;
    }

    public String fetchScoreInfo() {
        //TODO score LOGIC
        return "NA";
    }

    /**
     * 
     * @return
     */
    public int fetchNo_of_PiecesRemaingInfo() {
        Board board = Board.getInstance();
        final ChessSquare[][] squares = board.getUnmodifiableChessSquares();
        int pieceCount = 0;
        for(ChessSquare[] rows : squares){
            for(ChessSquare cols : rows){
                if((cols.getPiece() != null)&& (cols.getPiece().getColor() == ChessSquare.BoardColor.WHITE.ordinal())){
                    ++pieceCount;
                }
            }
        }
        return pieceCount;
    }
 
    public String fetchCurrenMoveTimeInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String fetchLastMoveTimeInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String fetchGrossTimeInfo() {
        return timeTaken.toString();
    }

    public int fetchNo_of_PiecesLostInfo() {
        return 16 - fetchNo_of_PiecesRemaingInfo();
    }

    public List<ChessPiece> fetchPiecesLostList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
