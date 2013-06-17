/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_z_interfejsem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
     private User luser;
     private ObjectOutputStream oout; 
     private ObjectInputStream oin; 
     static final int PORT = 8888;  
     private Socket socket = null;
     private Object object;
     String tmp,tmp2 = null;
     String dbDate = null;
     ObjectInputStream we;
     ObjectOutputStream wy;
     String imie_i_nazwisko;
     int id_seansu;
     int[][] tmp_miejsca;
    
     
/**
 * metoda a'la konstruktor klasy, wywoływana zaraz po utworzeniu obiektu
 */    
     private void init(){
          try{
            InetAddress addr = InetAddress.getByName("localhost");
            socket = new Socket(addr, PORT);
            System.out.println("połączono!");
           this.oin = new ObjectInputStream(this.socket.getInputStream()); //input for objects
            this.oout = new ObjectOutputStream(this.socket.getOutputStream()); // output for objects
            }catch(IOException e){
            System.err.println("IOErrorr!");
           
            }
        }
     /**
      * metoda nawiązująca połączenie z serwerem, wywołuje metode init, oraz metode pobierzBaze. przechodzi do stanu gotowości do rpacy między serwerem a klientem
      * @return 0 - jeśli wykona się poprawnie -1 - jeśli błąd połączenie lub inny błąd -2 - jeślij błąd logowania
      */
     @Override
     public int connect(){
         init();
        tmp =(String) odbierzO();
        System.out.println(tmp);
        if (!tmp.equals("!OK!")){
            System.err.println("Błąd połączenia");
            rozlacz();
            return -1; 
          }
        wyslijO(luser);
        tmp = (String) odbierzO();
        System.out.println(tmp);
        if (!tmp.equals("!UOK!")){
            System.err.println("Błąd logowania!");
            rozlacz();
            return -2;
          }  
        System.out.println("Pobieram baze!");
        try {
            pobierzBaze();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Klient2.class.getName()).log(Level.SEVERE, null, ex);
        }
        wyslijO((String)"!RDY!");
            tmp = (String) odbierzO();
            if(tmp.equals("!RDY!")){
                System.out.println("Serwer gotowy do pracy!");
                flush();
             }
      return 0;  
     }
     /**
      * metoda rezerwacji biletu
      * @param imnaz - parametr pobierany z GUI - Imie i Nazwisko
      * @param idse - parametr pobierany z GUI - ID seansu na która ma zostać dokonana rezerwacja
      * @param miejsca - tablica z miejscami jakie mają zostac zarezerwowane
      * @return 0 - jeśli się poprawnie wykona -1 - jeśli wystąpi jakikolwiek błąd
      */
     
     @Override
     public int goToReserve(String imnaz,int idse,int[][] miejsca) {
          wyslijO((String)"!CMD!");
            tmp =(String)odbierzO();
            if(tmp.equals("!OK!")){
                wyslijO((int)5);
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
      System.out.println("tmp"+tmp);
      if (tmp.equals("!SEANS!")){
          wyslijO(idse);
      }
   
         wyslijO(miejsca);
      
         return 0;
     }
    /**
     * metoda anulująca rezerwacje
     * @param imienaz - imie i nazwisko do rezerwacji
     * @return 0 - jeśli się poprawnie wykona -1 jeśli jakikolwiek błąd
     */
     @Override
     public int goToCancelRes(String imienaz){
         wyslijO((String)"!CMD!");
            tmp =(String)odbierzO();
            if(tmp.equals("!OK!")){
                wyslijO((int)8);
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
       /*  for (int i=0;i<rezerwacje.length;i++){
             System.out.println("Imie i nazwisko: "+rezerwacje[i].getName());
             System.out.println("Show ID: "+rezerwacje[i].getShowID());
             System.out.println(rezerwacje[i].formatSeats());
         }*/
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
     /**
      * metoda odbioru rezerwacji
      * @param imienaz - paarametr pobierany z GUI, imie i nazwisko do odbioru rezerwacji
      * @return 0 - jeśli się wykona poprawanie -1 jeśli jakieś błędy 
      */
     @Override
     public int goToGetRes(String imienaz){
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
         /*for (int i=0;i<rezerwacje.length;i++){
             System.out.println("Imie i nazwisko: "+rezerwacje[i].getName());
             System.out.println("Show ID: "+rezerwacje[i].getShowID());
             System.out.println(rezerwacje[i].formatSeats());
         }*/
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
     /**
      * metoda przekazuje tablice z rezerwacjami do GUI
      * @return tablice Res[]
      */
     @Override
     public Res[] getRezerwacja(){
         return rezerwacje;
     }
     /**
      * metoda sprawdza czy podane miejsca są zajęte
      * @param id_show - id seansu na który mamy zwrócić tablice zajętości
      * @return - int[][] tablica z zajętymi miejscami
      */
     @Override
     public int[][] czyZajete(int id_show){
             wyslijO((String)"!CMD!");
            tmp =(String)odbierzO();
            if(tmp.equals("!OK!")){
                wyslijO((int)9);
            }else{
                System.out.println("NOT OK");
                return null;
            }
            ////////////////////////////////////////////
            tmp =(String) odbierzO();
            System.out.println("tmp"+tmp);
            if (!tmp.equals("!GDATA!")){
                 System.out.println("Błąd serwera, oczekiwano !Gdata!");
                rozlacz();
                return null;
            }else{
                 wyslijO((String)"!OK!");
            }
            tmp =(String) odbierzO();
            if (tmp.equals("!GSID!")){
                wyslijO(id_show);
            }
            else {
                 System.out.println("Błąd serwera, oczekiwano !GSID!");
                rozlacz();
                return null;
            }
            int zajete[][] = new int[10][10];
            zajete =(int[][]) odbierzO();
         return zajete;
     }
     /**
      * metoda tworzy nowego użytkownika i wysyła go do sprawdzenia serwerowi
      * @param log - login
      * @param pas - hasło
      * @return  - 0 jeśli wykona się poprawnie, -1 jeśli błąd logowania
      */
     @Override
     public int setLogin(String log,String pas){
         luser = new User(log,pas);
         if (connect()!=0){
             return -1;
         }
         return 0;
     }
     /**
      * przekazuje tablice Show[] do GUI
      * @return  zwraca tablicę Show[]
      */
     @Override
     public Show[] getShow(){
         return shss;
     }
     /**
      * metoda do uniwersalnego wysyłania obiektow do serwera
      * @param object - obiekt do wysłania
      */
     public void wyslijO(Object object){
        try{
            this.oout.writeObject(object);
        } catch(IOException e) {
            System.err.println("IOError!"); 
        }   
     }
     /**
      * metoda do uniwersalnego odbierania obiektów z serwera
      * @return - zwraca ten obiekt
      */
     public Object odbierzO(){
        try{
             object = this.oin.readObject();
        } catch(IOException e) {
            System.err.println("IOError!"); 
        } finally{
            return object;
        }   
     }
     /**
      * metoda zamyka wszystkie połączenia klienta
      */
     @Override
     public void rozlacz() {
       
         try{
             socket.close();
             oin.close();
             oout.close();
             System.exit(0);
         } catch(IOException e){
              System.err.println("IOError!");        
         
     }
  }
     /**
      * metoda do pobierania bazy fimów z serwera
      * @return 0 jeśli wykona się poprawine -1 jeśli z błędem
      * @throws ClassNotFoundException 
      */
@SuppressWarnings("unchecked")
     private int pobierzBaze() throws ClassNotFoundException{
         wyslijO((String)"!GETMOV!");
         tmp2 =(String) odbierzO();
         if (tmp2.equals("!OK!")){//90-98
            try{    
                    List<Show> ar = (ArrayList<Show>)oin.readObject();
                    shss = ar.toArray(new Show[]{});
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
/**
 * metoda do czyszczenia bufora wyjścia do serwera
 */
     private void flush(){
         try {
             oout.flush();
         } catch (IOException e) {
             System.err.println("IOError "+e);
         }
     }
     /**
      * metoda zwwracająca opis danego filmu
      * @param mojstr - tytuł fimu
      * @return opis filmu
      */
     @Override
     public String zwrocOpis(String mojstr){
         String tmpstr = "NULL";
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
     /**
      * metoda do bezpośredniej sprzedaży biletu
      * @param id_show - id seansu na który chcemy sprzedać bilet
      * @param miejsca - tablica miejsc które chcemy sprzedać bilet
      * @return 0 - jeśli się poprawnie wykona -1 - jeśli jakikolwiek błąd
      */
     @Override
     public int sprzedajBilet(int id_show, int[][] miejsca){
          wyslijO((String)"!CMD!");
            tmp =(String)odbierzO();
            if(tmp.equals("!OK!")){
                wyslijO((int)10);
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
          wyslijO("NULL");
      }
      tmp=(String)odbierzO();
      if (tmp.equals("!SEANS!")){
          wyslijO(id_show);
      }
      wyslijO(miejsca);
      
         return 0;
     }
}