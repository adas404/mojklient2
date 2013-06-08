/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_objs;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author qbass
 */
public class Res implements Serializable{
    
    private static final long serialVersionUID = 2L;
   
    private String imienazwisko;
    private int showid;
    private int[] seat;
    private boolean checked; // potwierdzenie rezerwacji
    private boolean ok; // odebrana rezerwacja
    
    /**
     * Klasa Rezerwacji danego seansu
     * @param nazwa nazwa/nazwisko klienta, który rezerwuje dany seans
     * @param id identyfikator rezerwowanego seansu
     * @param seat tablica dwóch liczb identyfikujących rząd oraz miejsce w danym rzędzie do zarezerwowania
     */
    public Res(String nazwa,int id, int[] seat){
        this.seat = new int[2];
        this.imienazwisko = nazwa;
        this.showid = id;
        this.seat = seat;
        this.checked = false;
        this.ok = false;
    }
    
    @Override
    public boolean equals(Object obj){
        boolean isEqual = false;
        if (this.getClass() == obj.getClass())
        {
            Res res = (Res) obj;
            if ((res.imienazwisko).equals(this.imienazwisko) && Arrays.equals(res.seat, this.seat) && res.showid == this.showid) {
                System.out.println("equals");
                isEqual = true;
            }
        } 
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.imienazwisko);
        hash = 59 * hash + this.showid;
        hash = 59 * hash + Arrays.hashCode(this.seat);
        return hash;
    }

    /**
     * akceptacja rezerwacji
     */
    public void accept(){
        this.checked = true;
    }
    /**
     * odbiór rezerwacji
     */
    public void get(){
        this.ok = true;
    }
    /**
     * sprawdzenie czy rezerwacja jest już odebrana
     * @return true jeśli rezerwacja została odebrana
     */
    public boolean isok(){
        return this.ok;
    }
    /**
     * Metoda zwracająca nazwę/nazwisko osoby rezerwującej dany seans
     * @return nazwa/nazwisko rezerwującego
     */
    public String getName(){
        return this.imienazwisko;
    }
    /**
     * Metoda zwracająca identyfikator zarezerwowanego seansu
     * @return id seansu
     */
    public int getShowID(){
        return this.showid;
    }
    /**
     * Metoda zwracająca tablicę identyfikującą dokładne miejsce rezerwacji
     * @return int[] 
     */
    public int[] getSeat(){
        return this.seat;
    }
    
}
