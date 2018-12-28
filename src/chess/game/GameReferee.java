/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.game;

/**
 *
 * @author Sijar.Ahmed
 */
class GameReferee {

    private boolean gameOn = false; //Game state
    private Scheduler scheduler; //schedule player
    private static GameReferee singleInstance; //singleton

    /**
     * Constructor will initialize the Game Referee
     * and also the Scheduler for this game 
     */
    private GameReferee(int toss) {
        gameOn = true;
        scheduler = Scheduler.initialize(toss);
    }


    /**
     * Create an instance of the Singleton Class
     *
     * @param toss
     * @return
     */
    public static GameReferee getInstance(int toss){
        if(singleInstance == null){
            singleInstance = new GameReferee(toss);
        }
        return singleInstance;
    }


    /**
     * Update the Player's Turn
     *
     */
    void changePlayerTurn() {        
        scheduler.changePlayerTurn();
    }

    /**
     * 
     * @param string
     * @return
     */
    boolean isPlayerTurn(int pieceColor) {
        return scheduler.legalMove(pieceColor);
    }



    /**
     * 
     * @return
     */
    String getWhitePlayerInfo(){
       return scheduler.getPlayerInformation(0);
    }

    /**
     *
     * @return
     */
    String getBlackPlayerInfo(){
       return scheduler.getPlayerInformation(1);
    }


}
