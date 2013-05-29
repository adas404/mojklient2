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
        private int hour;
        private int minute;
        
        public Time(int hour,int minute){
            this.hour = hour;
            this.minute = minute;
        }        
        public int getHour(){
            return this.hour;
        }
        public int getMinute(){
            return this.minute;
        }
    }
