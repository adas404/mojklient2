/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_z_interfejsem;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import kinomaniak_objs.Res;
import kinomaniak_objs.Show;
import kinomaniak_objs.User;
import kinomaniak_interfejs.*;

/**
 *
 * @author Adam
 */
public class Klient2 implements KinomaniakInterface {
    private Show[] shss;
    private Res[] rezerwacje;
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
     String imie_i_nazwisko;
     int id_seansu;
     int[][] tmp_miejsca;
     //skasować
     int[] ksk= new int[2]; 
     Res res = new Res("costam",0,ksk);
    
     
    
     private void init(){
          try{
            InetAddress addr = InetAddress.getByName("localhost");
            socket = new Socket(addr, PORT);
            System.out.println("połączono!");
           // this.out = new PrintWriter(this.socket.getOutputStream(),true);  //out for data
           this.oin = new ObjectInputStream(this.socket.getInputStream()); //input for objects
            this.oout = new ObjectOutputStream(this.socket.getOutputStream()); // output for objects
           // this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream())); //in for data
            }catch(IOException e){
            System.err.println("IOErrorr!");
           
            }
        }
     @Override
     public int connect(){
         init();
        tmp =(String) odbierzO();
        System.out.println(tmp);
        if (!tmp.equals("!OK!")){
            System.err.println("Błąd połączenia");
            rozlacz();
            return -1; //jak błąd połączenia
          }
        
      
        //User user = new User(username,pass,1);
        wyslijO(luser);
        tmp = (String) odbierzO();
        System.out.println(tmp);
        if (!tmp.equals("!UOK!")){//zmienić, na !UOK! to tylko na dalsze potrzeby tworzenia klienta
            System.err.println("Błąd logowania!");
            rozlacz();
            return -2;// jak błąd logowania
          }  
        System.out.println("Pobieram baze!");
        try {
            pobierzBaze();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Klient2.class.getName()).log(Level.SEVERE, null, ex);
        }
        wyslijO((String)"!RDY!");
            tmp = (String) odbierzO();
           // System.out.println("tmp"+tmp); //waiting for rdy command zamiast do mnie powinno być na ekran w serwerze
            if(tmp.equals("!RDY!")){
                System.out.println("Serwer gotowy do pracy!");
                flush();
             }
      return 0;  //jak oki
     }
     
  /*   @Override
     public int setImieNazw(String imnaz){ //trzeba to tak rozwiązać że wywołanie rezerwacji 
         imie_i_nazwisko=imnaz;  //będzie wywoływało metode albo SetImieNazw albo SetMiejsca jak Ci lepiej Kuba
         return 0;
     }
     @Override 
     public int setIdSeansu(int idse){
         id_seansu=idse;
         return 0;
     }
     @Override
     public int setMiejsca(int[][] miejsca){
         tmp_miejsca = miejsca;
         return 0; 
     }*/
     @Override
     public int goToReserve(String imnaz,int idse,int[][] miejsca) {
          wyslijO((String)"!CMD!");
            tmp =(String)odbierzO();
            if(tmp.equals("!OK!")){
                wyslijO((int)6);//komenda rezerwacji
            }else{
                System.out.println("NOT OK");
                return -1;
            }
            ///////////////////////////////////////////////////////
      tmp =(String) odbierzO();//Kuba ma już przekazywać konkretną ilośc miejsc gdzie pierwszy [] jest kolejnym miejscem a a drugi [0] rzędem a [1] miejscem
      if (!tmp.equals("!GDATA!")){
          System.out.println("Błąd serwera, oczekiwano !Gdata!");
          rozlacz();
          return -1;
      }
      wyslijO((String)"!OK!");
      tmp=(String)odbierzO();
      if (tmp.equals("!NAZW!")){
          wyslijO(imnaz);
      }
      tmp=(String)odbierzO();
      if (tmp.equals("!SEANS!")){
          wyslijO(idse);
      }
      tmp=(String)odbierzO();
      if (tmp.equals("!MIEJSC!")){
         wyslijO(miejsca);
      }
         return 0;
     }
     @Override
     public int goToCancelRes(String imienaz){
         wyslijO((String)"!CMD!");
            tmp =(String)odbierzO();
            if(tmp.equals("!OK!")){
                wyslijO((int)7);
            }else{
                System.out.println("NOT OK");
                return -1;
            }
        ///////////////////////////////////////////////
            tmp =(String) odbierzO();
         if (!tmp.equals("!GDATA!")){
             System.out.println("Błąd serwera, oczekiwano !Gdata!");
             rozlacz();
             return -1;
         }else{
             wyslijO((String)"!OK!");
         }
         rezerwacje = (Res[])odbierzO();
         for (int i=0;i<rezerwacje.length;i++){
             System.out.println("Imie i nazwisko: "+rezerwacje[i].getName());
             System.out.println("Show ID: "+rezerwacje[i].getShowID());
//             System.out.println("Rząd"+rezerwacje[i].getSeats()[0][0]+"Miejsce"+rezerwacje[i].getSeats()[0][1]);
//             System.out.println("Rząd"+rezerwacje[i].getSeats()[0][0]+" Miejsce"+rezerwacje[i].getSeats()[1][0]);
             System.out.println(rezerwacje[i].formatSeats());
         }
        // System.out.println("Podaj imie i nazwisko ktore chcesz potwierdzić:");
         //   tmp = in.nextLine();
          Res res = null;  
        for (int i=0;i<rezerwacje.length;i++){
             if (rezerwacje[i].getName().equals(imienaz)){
                 res = rezerwacje[i];
                 break;
             }
         } 
        
         tmp = (String) odbierzO();
         if (!tmp.equals("!GORES!")){
             System.out.println("Błąd serwera, oczekiwano !GORES!");
             rozlacz();
             return -1;
         }
        wyslijO((Res)res);
        return 0; 
     }
     @Override
     public int goToGetRes(String imienaz){
          wyslijO((String)"!CMD!");
            tmp =(String)odbierzO();
            if(tmp.equals("!OK!")){
                wyslijO((int)6);
            }else{
                System.out.println("NOT OK");
                return -1;
            }
        ///////////////////////////////////////////////
            tmp =(String) odbierzO();
         if (!tmp.equals("!GDATA!")){
             System.out.println("Błąd serwera, oczekiwano !Gdata!");
             rozlacz();
             return -1;
         }else{
             wyslijO((String)"!OK!");
         }
         rezerwacje = (Res[])odbierzO();
         for (int i=0;i<rezerwacje.length;i++){
             System.out.println("Imie i nazwisko: "+rezerwacje[i].getName());
             System.out.println("Show ID: "+rezerwacje[i].getShowID());
//             System.out.println("Rząd"+rezerwacje[i].getSeats()[0][0]+"Miejsce"+rezerwacje[i].getSeats()[0][1]);
//             System.out.println("Rząd"+rezerwacje[i].getSeats()[0][0]+" Miejsce"+rezerwacje[i].getSeats()[1][0]);
             System.out.println(rezerwacje[i].formatSeats());
         }
        // System.out.println("Podaj imie i nazwisko ktore chcesz potwierdzić:");
         //   tmp = in.nextLine();
          Res res = null;  
        for (int i=0;i<rezerwacje.length;i++){
             if (rezerwacje[i].getName().equals(imienaz)){
                 res = rezerwacje[i];
                 break;
             }
         } 
         return 0;
     }
     @Override
     public Res[] getRezerwacja(){
         return rezerwacje;
     }
     @Override
     public int czyZajete(int rz, int mie){
         return rz;
     }
     
     @Override
     public int setLogin(String log,String pas){
         luser = new User(log,pas,1);
         connect();
         return 0;
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
    /**
     * 
     * @param object 
     */
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
     
     public String menu(){
         System.out.println("Wybierz co chcesz robić:");
         System.out.println("5.rezerwacja biletu");
         System.out.println("6.potwierdzenie rezerwacji");
         System.out.println("7.odbiór rezerwacji");
         System.out.println("8.anulowanie rezerwacji");
         String cmd = in.nextLine();
         return cmd;
     }
     
     public void rezerwujBilet(){
      
          
//      System.out.println("Podaj rząd w którym chcesz siedzieć:");
//      tmp = in.nextLine();
//      int[] tmpseat = new int[2];
//      tmpseat[0]=Integer.parseInt(tmp);
//      System.out.println("Podaj miejsce w rzędzie na którym chcesz siedzieć");
//      tmp = in.nextLine();
//      tmpseat[1] = Integer.parseInt(tmp);
//      wyslijO(tmpseat);
      
      
     }
     
     public void potwierdzOdbierzRezerwacje(String decyzja){
         switch (decyzja) {
             case "POTWIERDZ":
                 System.out.println("Wybrałeś opcję potwierdzenia rezerwacji");
                 break;
             case "ANULUJ":
                 System.out.println("Wybrałeś opcję anulowania rezerwacji");
                 break;
             case "ODBIERZ":
                 System.out.println("Wybrałeś opcję odebrania rezerwacji");
         }
         tmp =(String) odbierzO();
         if (!tmp.equals("!GDATA!")){
             System.out.println("Błąd serwera, oczekiwano !Gdata!");
             rozlacz();
            
         }else{
             wyslijO((String)"!OK!");
         }
         rezerwacje = (Res[])odbierzO();
         for (int i=0;i<rezerwacje.length;i++){
             System.out.println("Imie i nazwisko: "+rezerwacje[i].getName());
             System.out.println("Show ID: "+rezerwacje[i].getShowID());
//             System.out.println("Rząd"+rezerwacje[i].getSeats()[0][0]+"Miejsce"+rezerwacje[i].getSeats()[0][1]);
//             System.out.println("Rząd"+rezerwacje[i].getSeats()[0][0]+" Miejsce"+rezerwacje[i].getSeats()[1][0]);
             System.out.println(rezerwacje[i].formatSeats());
         }
         System.out.println("Podaj imie i nazwisko ktore chcesz potwierdzić:");
            tmp = in.nextLine();
          Res res = null;  
        for (int i=0;i<rezerwacje.length;i++){
             if (rezerwacje[i].getName().equals(tmp)){
                 res = rezerwacje[i];
                 break;
             }
         } 
        
         tmp = (String) odbierzO();
         if (!tmp.equals("!GORES!")){
             System.out.println("Błąd serwera, oczekiwano !GORES!");
             rozlacz();
             System.exit(-1);
         }
        wyslijO((Res)res);
     }
     
     private void rozlacz() {
       
         try{
             socket.close();
             in.close();
             oin.close();
             oout.close();
          //   out.close();
         } catch(IOException e){
              System.err.println("IOError!");        
         
     }
  }
@SuppressWarnings("unchecked")
     private int pobierzBaze() throws ClassNotFoundException{
         wyslijO((String)"!GETMOV!");
         tmp2 =(String) odbierzO();
         if (tmp2.equals("!OK!")){//90-98
            try{    
                    List<Show> ar = (ArrayList<Show>)oin.readObject();
                    shss = ar.toArray(new Show[]{});
                   // Show[] sh = (Show[])oin.readObject();
                    wy = new ObjectOutputStream(new FileOutputStream("Shows.kin"));
                    Date dateNow = new Date (); 
                    SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
                    StringBuilder sdt = new StringBuilder( dt.format( dateNow ) );
                    String st = sdt.toString();
                    wy.writeObject(st);
                    wy.writeObject(shss.length);
                    wy.writeObject(shss);
                    wy.close();
            }catch(IOException e){
                System.err.println("IOException! "+e);
            }
         }else {
             return -1;
         }
       return 0;  
     }
     private void flush(){
         try {
             oout.flush();
         } catch (IOException e) {
             System.err.println("IOError "+e);
         }
     }
     @Override
     public String zwrocOpis(String mojstr){
         String tmpstr = "NULL";
        /* ObjectInputStream wel;
         Show[] tmpshss;
         try{
             wel = new ObjectInputStream(new FileInputStream("Shows.kin"));
         //Show[] tmpstr = new (Show[]) wex.readObject;
          tmpstr = (String)wel.readObject();
          int tmpint = (Integer)wel.readObject();
          System.out.println("tmpint"+tmpint);
          tmpshss = (Show[]) we.readObject();
          System.out.println("deb");
         // ArrayList<Res> reslist = (ArrayList<Res>)wel.readObject();
         //Show[] tmpshss = reslist.toArray(new Show[]{});*/
          for (Show sh : shss){
              System.out.println(sh.getMovie().getName());
              if(sh.getMovie().getName().equals("Superman")) {
                  tmpstr = sh.getMovie().getDesc();
                  System.out.println("Przeszukiwanie");
                  break;
              }
          }
         
         return tmpstr;
         
         
     }
     public void dzialaj() throws ClassNotFoundException{
       // String tmp;
       
       //    f = new File("Shows.kin");
     
     /*   if (f.exists()) { //jeśli to nie zadziała otworzymy nowy strumień i damy wyjątek file not found
            System.out.println("Jest baza! Sprawdzam date");
            try{
                we = new ObjectInputStream(new FileInputStream("Shows.kin"));
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
            
        } else {*/
            
      //  }
            
        //sekcja gotowość do pracy!
            
            while(true){
            flush();
            wyslijO((String)"!CMD!");
            tmp =(String)odbierzO();
            int tmp3;
            if(tmp.equals("!OK!")){
                tmp3 = Integer.parseInt(menu());
                wyslijO(tmp3);
            }else{
                System.out.println("NOT OK");
                return;
            }
            switch(tmp3){
                case 5:{
                    rezerwujBilet();//todo!
                    break;
                }
                case 6:{
                    potwierdzOdbierzRezerwacje("POTWIERDZ");
                    break;
                }
                case 7:{
                    potwierdzOdbierzRezerwacje("ANULUJ");
                    break;
                }
                case 8:{
                    potwierdzOdbierzRezerwacje("ODBIERZ");
                    break;
                
                }
                case 9:{
                    System.out.println(zwrocOpis("Superman"));
                    break;
                }
           }
            tmp = (String)odbierzO(); // spradzić czy działa czy to generuje błędy;D
            }
//        rozlacz();
     }
}  

