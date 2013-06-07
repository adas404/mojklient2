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
    
    /**
     *
     * @param mov
     * @param room
     * @param time
     */
    public Show(Movie mov, CRoom room, Time time){
        this.mov = mov;
        this.room = room;
        this.time = time;
    }
    /**
     *
     * @return
     */
    public int[] getTime(){
        int tim[] = new int[2];
        tim[0] = this.time.getHour();
        tim[1] = this.time.getMinute();
        return tim;
    }
    /**
     *
     * @return
     */
    public Movie getMovie(){
        return mov;
    }
    /**
     *
     * @return
     */
    public CRoom getRoom(){
        return room;
    }
    /**
     *
     * @return
     */
    public Time getCTime(){
        return time;
    }
    /**
     *
     * @return
     */
    public int getID(){
        return this.showid;
    }
    /**
     *
     * @param id
     */
    public void setID(int id){
        this.showid = id;
    }
}
