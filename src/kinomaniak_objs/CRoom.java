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
public class CRoom implements Serializable {
    private int id;
    private boolean[][] seats;
    
    public CRoom(int id){
        this.id = id;
        seats = new boolean[10][10];
    }
    public int getID(){
        return this.id;
    }
}
