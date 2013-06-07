/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_objs;

import java.io.Serializable;

/**
 *
 * @author qbass
 */
public class Time implements Serializable{
    private static final long serialVersionUID = 1L;
        private int hour;
        private int minute;
        
        /**
     *
     * @param hour exact hour
     * @param minute exact minute
     */
    public Time(int hour,int minute){
            this.hour = hour;
            this.minute = minute;
        }        
        /**
     * gets hour from {@link #Time} class
     * @return hour
     */
    public int getHour(){
            return this.hour;
        }
        /**
     * gets minute from {@link #Time} class
     * @return minute
     */
    public int getMinute(){
            return this.minute;
        }
    }
