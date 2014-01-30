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
public class Movie implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String genre;
    private String rating;
    private String desc;
    
    /**
     * Konstruktor klasy filmowej bez opisu
     * @param name tytuł filmu
     * @param genre rodzaj filmu
     * @param rating klasyfikacja filmu
     */
    public Movie(String name,String genre,String rating){
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.desc = null;
    }
    /**
     * Konstruktor klasy filmowej z opisem
     * @param name tytuł filmu
     * @param genre rodzaj filmu
     * @param rating klasyfikacja filmu
     * @param desc opis filmu
     */
    public Movie(String name,String genre,String rating,String desc){
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.desc = desc;
    }
    /**
     * Metoda zwracająca tytuł filmu
     * @return tytuł filmu
     */
    public String getName(){
        return this.name;
    }
    /**
     * Metoda zwracająca rodzaj filmu
     * @return rodzaj filmu
     */
    public String getGenre(){
        return this.genre;
    }
    /**
     * Metoda zwracająca klasyfikację filmu
     * @return klasyfikacja filmu
     */
    public String getRating(){
        return this.rating;
    }
    /**
     * Metoda zwracająca krótki opis filmu
     * @return opis filmu
     */
    public String getDesc(){
        return this.desc;
    }
}
