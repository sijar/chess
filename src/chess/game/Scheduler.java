/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.game;

/**
 *
 * @author Sijar.Ahmed
 */
class Scheduler {

    private BlackPlayer bPlayer;
    private WhitePlayer wPlayer;

    /**
     * 
     */
    private Scheduler() {
        this(0);
    }

    /**
     * Constructor
     * <!-- 0 {white start} -->
     * <!-- 1 {black start} -->
     * @param tossResult
     */
    private Scheduler(int tossResult) {
        //initialize both the player
        bPlayer = BlackPlayer.initWhitePlayer();
        wPlayer = WhitePlayer.initWhitePlayer();
        if (tossResult == 1) {
            //black start            
            bPlayer.updateTurnStatus(true);
        } else {
            //white start            
            wPlayer.updateTurnStatus(true);
        }
    }

    /**
     * Static factory method to initialize the Scheduler
     * The Players are initialize, turn of the player is also set
     *
     * @return
     */
    public static Scheduler initialize(int tossResult) {
        return new Scheduler(tossResult);
    }

    /**
     * <!-- WHITE = 0 ; BLACK = 1 -->
     * 
     * @param moveCommand
     * @return
     */
    boolean legalMove(final int color) {
        if (color == 0) {
            return wPlayer.isTurnOn();
        }
        if (color == 1) {
            return bPlayer.isTurnOn();
        }
        return false;
    }

    /**
     * Change Player's Turn
     * Method changes active current player's turn to inactive and vice-versa
     *
     */
    void changePlayerTurn() {
        if (bPlayer.isTurnOn()) {
            bPlayer.updateTurnStatus(false);
            //bPlayer.
            wPlayer.updateTurnStatus(true);
        } else {
            wPlayer.updateTurnStatus(false);
            bPlayer.updateTurnStatus(true);
        }
    }

    /**
     * <!-- 0 {white start} -->
     * <!-- 1 {black start} -->
     * @param tossResult
     * @return
     */
    String getPlayerInformation(int tossResult) {
        if(tossResult == 0){
            return wPlayer.toString();
        }else{
            return bPlayer.toString();
        }
    }
}
