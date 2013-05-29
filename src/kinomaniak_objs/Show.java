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
public class Show implements Serializable {
    
    private int showid;
    private Movie mov;
    private CRoom room;
    private Time time;
    
    public Show(Movie mov, CRoom room, Time time){
        this.mov = mov;
        this.room = room;
        this.time = time;
    }
    public int[] getTime(){
        int tim[] = new int[2];
        tim[0] = this.time.getHour();
        tim[1] = this.time.getMinute();
        return tim;
    }
    public Movie getMovie(){
        return mov;
    }
    public CRoom getRoom(){
        return room;
    }
    public Time getCTime(){
        return time;
    }
    public int getID(){
        return this.showid;
    }
    public void setID(int id){
        this.showid = id;
    }
}
