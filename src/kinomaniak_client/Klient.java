/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetAddress;
import java.util.Scanner;
import kinomaniak_objs.User;

/**
 *
 * @author Adam
 */
public class Klient {
    // private Socket sockfd;
     private User luser;
     private PrintWriter out;  //wyjście tekstowe klienta
     private ObjectOutputStream oout; // wyjście obiektowe klienta
    // private BufferedReader in; //wejście tekstowe klienta
     private ObjectInputStream oin; //wejście obiektowe klienta
     static final int PORT = 8888;  //port połączenia
    // private static final String addr = "localhost";
     private Socket socket = null;
     private Object object;
     String tmp,tmp2 = null;
     String dbDate = null;
     Scanner in = new Scanner(System.in);
     ObjectInputStream we;
     ObjectOutputStream wy;
     File f = null;
     
     
     public Klient (){
        // this.socket=socket;
          try{
            InetAddress addr = InetAddress.getByName("localhost");
            socket = new Socket(addr, PORT);
            System.out.println("połączono!");
           // this.out = new PrintWriter(this.socket.getOutputStream(),true);  //out for data
           this.oin = new ObjectInputStream(this.socket.getInputStream()); //input for objects
            this.oout = new ObjectOutputStream(this.socket.getOutputStream()); // output for objects
           // this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream())); //in for data
            }catch(IOException e){
            System.err.println("IOError!");
           
        }
    }
     
     public void wyslijT(String tmp) {
      try{ 
          this.out.write(tmp);
             }
            catch(Exception e){
              System.err.println("StringError!"); 
      }
      
    }
     public String odbierzT() {
      try{ 
         
         tmp =(String)this.oin.readObject();
          
      }
            
            catch(IOException e){
              System.err.println("IOError!"); 
      }finally {
          return tmp;
      }
      
    }
    
     public void wyslijO(Object object){
        try{
            this.oout.writeObject(object);
        } catch(IOException e) {
            System.err.println("IOError!"); 
        }   
     }
     
     public Object odbierzO(){
        try{
             object = this.oin.readObject();
        } catch(IOException e) {
            System.err.println("IOError!"); 
        } finally{
            return object;
        }   
     }
   
     public void rozlacz() {
       
         try{
             socket.close();
             in.close();
             oin.close();
             oout.close();
             out.close();
         } catch(IOException e){
              System.err.println("IOError!");        
         
     }
  }
     public void pobierzBaze() throws ClassNotFoundException{
         wyslijO((String)"!GETMOV!");
         tmp2 =(String) odbierzO();
         if (tmp2.equals("!OK!")){
            try{
            //  File fil = (File)oin.readObject();  
             wy.writeObject(oin.readObject());
              //  wy = new ObjectOutputStream(new FileOutputStream(fil)); 
            }catch(IOException e){
                System.err.println("IOException!");
            }
         }
         
     }
     
     public void dzialaj() throws ClassNotFoundException{
       // String tmp;
        tmp =(String) odbierzO();
        System.out.println(tmp);
        if (!tmp.equals("!OK!")){
            System.err.println("Błąd połączenia");
            rozlacz();
            System.exit(-1);
          }
        System.out.println("Podaj użytkownika:");
        String username = in.nextLine();
        System.out.println("Podaj hasło");
        String pass = in.nextLine();
        User user = new User(username,pass,0);
        wyslijO(user);
        tmp = (String) odbierzO();
        System.out.println(tmp);
        if (!tmp.equals("!ERROR!")){//zmienić, na !UOK! to tylko na dalsze potrzeby tworzenia klienta
            System.err.println("Błąd logowania!");
            rozlacz();
            System.exit(-1);
          }
     //   try{
        f = new File("movies.db");
       // }  
        //catch(IOException e)
    //    {System.err.println("IOException!");}
        if (f.exists()) { //jeśli to nie zadziała otworzymy nowy strumień i damy wyjątek file not found
            System.out.println("Jest baza! Sprawdzam date");
            try{
                we = new ObjectInputStream(new FileInputStream("movies.db"));
                dbDate = (String)we.readObject();}
                catch(ClassNotFoundException | IOException cnfe){
                System.err.println("IOException! or ClassNotFoundException!");}
            wyslijO((String)"!GETMOVDT!");
            wyslijO(dbDate);
            tmp =(String) odbierzO();
            if (tmp.equals("!MOVUPD!")){ //zrobić usuwanie starej bazy!
                try{
               wy.writeObject(oin.readObject());
               }catch(IOException e){
                System.err.println("IOException!");
            }
          }  
            
        } else {
            System.out.println("brakbazy!");
            pobierzBaze();
        }
        rozlacz();
     }
}  

