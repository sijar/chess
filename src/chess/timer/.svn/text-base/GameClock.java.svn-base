/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.timer;

import java.util.Calendar;

/**
 * Single Clock for the entire game
 *
 * @author Sijar.Ahmed
 */
public class GameClock {

    private Clock startTime;
    private Clock currentTime;
    private static GameClock singleInstance = new GameClock();

    /**
     * 
     */
    private GameClock() {
        Calendar calendar = Calendar.getInstance();
        if(startTime == null){            
            startTime = new Clock();
            startTime.setHour(calendar.get(Calendar.HOUR));
            startTime.setMin(calendar.get(Calendar.MINUTE));
            startTime.setSec(calendar.get(Calendar.SECOND));
        }
    }

     public static GameClock getInstance(){
        return singleInstance;
    }



    /**
     * Method return the amount of time passed away from the start
     * 
     * @return
     */
    public Clock getElapsedTime(){
        Calendar calendar = Calendar.getInstance();
        currentTime = new Clock();
        currentTime.setHour(calendar.get(Calendar.HOUR));
        currentTime.setMin(calendar.get(Calendar.MINUTE));
        currentTime.setSec(calendar.get(Calendar.SECOND));
        return Clock.getTimeDifference(currentTime,startTime);
    }

    


}
