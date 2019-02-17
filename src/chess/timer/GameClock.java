/*
 * Developed by Sijar Ahmed on 18/2/19 12:53 AM
 * Last modified 6/2/19 11:22 PM.
 * Sijar Ahmed (sijar.ahmed@gmail.com)
 * Copyright (c) 2019. All rights reserved.
 *
 *
 * The Class / Interface GameClock is responsible for...
 * @author sijarahmed
 * 18/2/19 12:53 AM
 *
 */

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
        Clock currentTime = new Clock();
        currentTime.setHour(calendar.get(Calendar.HOUR));
        currentTime.setMin(calendar.get(Calendar.MINUTE));
        currentTime.setSec(calendar.get(Calendar.SECOND));
        return Clock.getTimeDifference(currentTime,startTime);
    }

    


}
