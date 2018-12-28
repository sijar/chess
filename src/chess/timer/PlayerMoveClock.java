/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.timer;

import java.util.Calendar;

/**
 *
 * @author Sijar.Ahmed
 */
public class PlayerMoveClock {
    
    private Clock startTime;
    private Clock elapsedTime;
    private static PlayerMoveClock singleInstance = new PlayerMoveClock();

    /**
     *
     */
    private PlayerMoveClock() {
        Calendar calendar = Calendar.getInstance();
        if(startTime == null){
            startTime = new Clock();
            startTime.setHour(calendar.get(Calendar.HOUR));
            startTime.setMin(calendar.get(Calendar.MINUTE));
            startTime.setSec(calendar.get(Calendar.SECOND));
        }
    }


    /**
     * 
     * @return
     */
    public static PlayerMoveClock startClock(){
        return singleInstance;
    }



    /**
     * Method return the amount of time passed away from the start
     *
     * @return
     */
    public Clock recordClock(){
        Calendar calendar = Calendar.getInstance();
        elapsedTime = new Clock();
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        elapsedTime.setHour(hour);
        elapsedTime.setMin(min);
        elapsedTime.setSec(sec);
        elapsedTime = Clock.getTimeDifference(elapsedTime,startTime);
        restartClock(hour,min, sec);
        return elapsedTime;
    }

    /**
     * 
     * @param hour
     * @param min
     * @param sec
     */
    private void restartClock(int hour, int min, int sec) {
        //restart the clock
        startTime.setHour(hour);
        startTime.setMin(min);
        startTime.setSec(sec);
    }



    
}
