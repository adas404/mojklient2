/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak_objs;

import java.io.Serializable;

/**
 *
 * @author Qbass
 */
public class Product implements Serializable{
    private int id;
    private String name;
    private int type;
    private int count;
    private float price;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }
    
    public int getCount(){
        return this.count;
    }
    
    public Product(String name, int type, float price){
        this.name = name;
        this.type = type;
        this.price = price;
        this.count = 1;
        this.id = this.getLastId() + 1;
    }
    
    public Product(String name, int type, float price, int count){
        this.name = name;
        this.type = type;
        this.price = price;
        this.count = count;
        this.id = this.getLastId() + 1;
    }
    
    public boolean buy(){
        if(this.count > 0){
            this.count--;
            return true;
        }else return false;
    }
    
    
    private int getLastId(){
        int tmp = -1;
        
        return tmp;
    }
}
