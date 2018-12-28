/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.timer;

/**
 *
 * @author Sijar.Ahmed
 */
public class Clock {

    private int hour;
    private int min;
    private int sec;

    public Clock() {
    }

    public Clock(int hour, int min, int sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    /**
     * @return the hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the sec
     */
    public int getSec() {
        return sec;
    }

    /**
     * @param sec the sec to set
     */
    public void setSec(int sec) {
        this.sec = sec;
    }

    /**
     * Method will the difference between the two clocks
     * currentTime >= startTime
     *
     * @param currentTime
     * @param startTime
     * @return
     */
    static Clock getTimeDifference(Clock currentTime, Clock startTime) {
        Clock result = new Clock();
        //TODO
        /** <!- BUG#1 fix the Negative difference in Time
         *      BUG#1 fix the time taken for inactive player
         *  --> */
        result.setHour(currentTime.getHour() - startTime.getHour());
        result.setMin(currentTime.getMin() - startTime.getMin());
        result.setSec(currentTime.getSec() - startTime.getSec());
        return result;
    }

    @Override
    public String toString() {
        return hour + ":" + min + ":" + sec;
    }

    

}
