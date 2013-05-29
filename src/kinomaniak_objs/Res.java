/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_objs;

/**
 *
 * @author qbass
 */
public class Res {
   
    private String imienazwisko;
    private int showid;
    private int[] seat;
    private boolean checked; // potwierdzenie rezerwacji
    private boolean ok; // odebrana rezerwacja
    
    public Res(String nazwa,int id, int[] seat){
        this.seat = new int[2];
        this.imienazwisko = nazwa;
        this.showid = id;
        this.seat = seat;
        this.checked = false;
        this.ok = false;
    }
    public void accept(){
        this.checked = true;
    }
    public void get(){
        this.ok = true;
    }
    public boolean isok(){
        return this.ok;
    }
    public String getName(){
        return this.imienazwisko;
    }
    public int getShowID(){
        return this.showid;
    }
    public int[] getSeat(){
        return this.seat;
    }
    
}
