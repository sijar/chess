/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.game;

import chess.board.Board;
import chess.pieces.AbstractChessPiece;
import chess.timer.GameClock;

/**
 *
 * @author Sijar.Ahmed
 */
public class GameMaster {

    GameReferee referee;
    Board board;
    GameClock gameClock;

    /**
     * 
     */
    public GameMaster() {
        //initialization
        referee = GameReferee.getInstance(0);
        board = Board.getInstance();
        gameClock = GameClock.getInstance();
    }

    /**
     * Move the piece if the turns and the command is correct
     * 
     * @param moveCommand
     * @return
     */
    public boolean move(final String moveCommand) throws IllegalArgumentException {
        try {
            if (moveCommand != null) {
                final String[] moves = moveCommand.split("-");
                //System.err.println(moves[0]);
                final int x = Integer.parseInt(moves[0].substring(1, 2));
                final int y = Integer.parseInt(moves[0].substring(2, 3));
                AbstractChessPiece piece = board.getPiece(x, y);
                if ((piece != null) && referee.isPlayerTurn(piece.getColor()) && board.movePiece(moveCommand)) {
                    //update the turns of the player
                    referee.changePlayerTurn();
                    return true;
                }
            }
            return false;
        } catch (IllegalArgumentException excp) {
            //System.out.println(excp.getMessage());
            throw excp;
        }
    }

    /**
     * Method returns the color for the particular player whose turn it is
     * 
     * <!-- WHITE=1 ; BLACK=0  -->
     * @return
     */
    public int getTurnPlayerColor() {
        return referee.isPlayerTurn(0) ? 1 : 0;
    }

    /**
     * 
     * @return
     */
    public String getPlayersInformation() {
        StringBuilder playerInfo = new StringBuilder();
        playerInfo.append("\n%%");
        playerInfo.append("\nGame Clock");
        playerInfo.append(gameClock.getElapsedTime().toString());
        playerInfo.append("\n%%");
        playerInfo.append("\nWhite Player Information ");
        playerInfo.append(referee.getWhitePlayerInfo());
        playerInfo.append("\n");
        playerInfo.append("\n%%");
        playerInfo.append("\nBlack Player Information ");
        playerInfo.append(referee.getBlackPlayerInfo());
        return playerInfo.toString();
    }
}
