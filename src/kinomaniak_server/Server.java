/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_server;
import kinomaniak_client.*;
import kinomaniak_objs.*;
import java.net.Socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
/**
 *
 * @author qbass
 */
public class Server  implements Runnable{
//    private String user;
//    private String password;
    private Socket sockfd;
//    private int logintype;
//    private int[] availcmds;
//    private final int cmdCount = 10;
//    private Client[] activeconns;
    private boolean logged;
//    private int command;
    private User luser;
     private PrintWriter out;  //out for data
     private ObjectOutputStream oout; // output for objects
     private BufferedReader in; //in for data
     private ObjectInputStream oin; //input for objects
     
    
    
    /*
    public Server(int type,String username){
        this.loginname = username;
        this.availcmds = new int[10];
        this.setCmds(type);        
    }
    */
     /**
      * Konstruktor wątku serwera
     * @param sockfd deskryptor gniazda dla podłączonego klienta
     */
    public Server(Socket sockfd){
        this.sockfd = sockfd;
        try{
            this.out = new PrintWriter(this.sockfd.getOutputStream(),true);  //out for data
            this.oout = new ObjectOutputStream(this.sockfd.getOutputStream()); // output for objects
            this.in = new BufferedReader(new InputStreamReader(this.sockfd.getInputStream())); //in for data
            this.oin = new ObjectInputStream(this.sockfd.getInputStream()); //input for objects
            this.logged = true;
        }catch(IOException e){
            System.err.println("IOError from "+sockfd.getInetAddress().getHostAddress()+": "+e);
        }
    }
    
    private void endThread(){
        try{
            in.close();
            oin.close();
            out.close();
            oout.close();
            sockfd.close();
            return;
        }catch(IOException e){
            
        }
    }
    
    @Override
    public void run(){ //todo!
        boolean cmdAvail = false;
        String tmp;
        try{
//            this.out.write("!OK!");
            this.oout.writeObject((String)"!OK!");
            this.luser = (User)oin.readObject(); // odczyt obiektu użytkownika od klienta        
            boolean uok = checkUser(); // sprawdzenie użytkownika
            if(uok) this.oout.writeObject((String)"!UOK!");
            else{
                this.logged = false;
                this.oout.writeObject((String)"!ERROR!");
                this.in.close();
                this.out.close();
                this.oin.close();
                this.oout.close();
                System.exit(-1);
            }
            tmp = (String)oin.readObject();
            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Shows.kin"));
//            File fil = (File)oin.readObject();
//            ObjectInputStream we2 = new ObjectInputStream(new FileInputStream(fil));
//            ObjectOutputStream wyyy = new ObjectOutputStream(new FileOutputStream("mov.db"));
//            wyyy.writeObject(fil);
            switch (tmp) {
                case "!GETMOV!":
                    this.oout.writeObject((String)"!OK!");
//                    System.out.println("Debug pre");
                    String date = (String)we.readObject();
//                    System.out.println("Debug pre");
//                    int cnt = (Integer)we.readObject();
                    System.out.println("Debug pre");
//                    Show[] ssstmp = (Show[])we.readObject();
                    List<Show> ssstmp = (ArrayList<Show>)we.readObject();
//                    System.out.println("Debug pre");
                    this.oout.writeObject(ssstmp);
//                    System.out.println("Debug post");
//                    this.oout.writeObject(MoviesDB);
                    break;
                case "!GETMOVDT!":
                    tmp = (String)oin.readObject();
                    String dbDate = (String)we.readObject();
                    if(tmp.equals(dbDate)) this.oout.writeObject((String)"!MOVOK!");
                    else { this.oout.writeObject((String)"!MOVUPD!"); this.oout.writeObject(we); }
//                    if(tmp.equals(newestDBdate)) this.out.write("!MOVOK!");
//                    else{ this.out.write("!MOVUPD!"); this.oout.writeObject(MoviesDB); }
                    break;
            }
            while(!tmp.equals("!RDY!")){
                tmp = (String)oin.readObject();
            }
            this.oout.writeObject((String)"!RDY!");            
            while (this.logged){
                if(in.ready()){ //sprawdzenie dostępności danych w buforze wejścia 
                    String data = (String)oin.readObject();
                    switch (data) {
                        case "!CMD!":
//                            jeśli klient wysyła komendę
                            this.oout.writeObject((String)"!OK!");
                            cmdAvail = true;
                            break;
                        case "!OK!":
                            break;
                        default:
                            this.oout.writeObject((String)"!NAVAIL!"); //not available
                            break;
                    }
                    if(cmdAvail)
                        if(in.ready()){
                            int cmd = (Integer)oin.readObject();
                            if(this.checkGrants(cmd)){
                                this.processCmd(cmd);
                                this.oout.writeObject((String)"!OK!");
                            }
                            else this.oout.writeObject((String)"!NGRANT!"); //not granted
                            
                        }
                    cmdAvail = false;
                }
            }
            this.endThread();
        }catch(IOException e){
            System.err.println("IOError from "+sockfd.getInetAddress().getHostAddress()+": "+e);
            this.endThread();
        }catch(ClassNotFoundException e){
            System.err.println("Class not found from "+sockfd.getInetAddress().getHostAddress()+": "+e);
        }
    }
    
    /**
     * Metoda sprawdzająca czy zalogowany użytkownik ma prawo do wykonania danej komendy
     * @param cmd identyfikator komendy 
     * @return true - jeśli dany użytkownik ma uprawnienia do wykonania, false - jeśli nie
     */
    public boolean checkGrants(int cmd){
        if(cmd < 0) return true;
        for(int i=0;i<12;i++){
            if(cmd == luser.getACmds()[i]){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metoda wykonująca komendę podaną przez użytkownika
     * @param cmd identyfikator komendy
     */
    public void processCmd(int cmd){
        switch(cmd){
            case -1 : {
                this.logged = false;
                break;
            }
            case 1 :{
                
                break;
            }
            case 2:{
                
                break;
            }
            case 3 :{
                
                break;
            }
            case 4:{
                
                break;
            }
            case 5:{ // rezerwacja biletu
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();
                    if(tmp.equals("!OK!"))
                        this.oout.writeObject((String)"!NAZW!");
                        String nazwa = (String)oin.readObject();
                        this.oout.writeObject((String)"!SEANS!");
                        int showid = (Integer)oin.readObject();
                        this.oout.writeObject((String)"!MIEJSC!");
                        int[] seat = (int[])oin.readObject();
                        Res res = new Res(nazwa,showid,seat);
                    synchronized (this){
                        try{
                            File r = new File("Res.kin");
                            Res ares[];
                            List<Res> reslist = new ArrayList<Res>();
                            int num = 0;
                            if(!r.exists()){
                                r.createNewFile();
//                                ares = new Res[1];
//                                ares[0] = res;
                                reslist.add(res);
                            }else{
                                ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
//                                num = (Integer)we.readObject();
//                                ares= new Res[num+1];
//                                System.out.println(num+1);
//                                ares = (Res[])we.readObject();
                                reslist = (ArrayList<Res>)we.readObject();
                                we.close();
//                                System.out.println(ares.length);
//                                ares[num] = res;
                                reslist.add(res);
                            }
                            ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Res.kin"));
//                            wy.writeObject(num+1);
//                            wy.writeObject(ares);
                            wy.writeObject(reslist);
                            wy.close();
                        }catch(IOException e){
                            System.err.println("IO Error from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                        }
                    }
                }catch(IOException e){
                    System.err.println("IO Error: "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found :"+e);
                }
                break;
            }
            case 6:{// potwierdzenie rezerwacji
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();                   
//                    Res ares[];
                    List<Res> reslist = new ArrayList<Res>();
                    if(tmp.equals("!OK!")){
                        File r = new File("Res.kin");
                            if(!r.exists()){
                                r.createNewFile();
                            }
                         synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
//                            int num = (Integer)we.readObject();
//                            ares = new Res[num];
//                            ares = (Res[])we.readObject();
                            reslist = (ArrayList<Res>)we.readObject();
                            we.close();
                         }
//                        this.oout.writeObject((Res[])ares);
                         Res restmp[] = new Res[reslist.size()];
                         restmp = reslist.toArray(restmp);
                         this.oout.writeObject((Res[])restmp);
                        this.oout.writeObject((String)"!GORES!"); // Get Object Res
                        Res res = (Res)oin.readObject();
                        synchronized(this){
                            boolean acc = false;
//                            for (int i = 0;i<ares.length;i++){
//                                if(ares[i].equals(res)){
//                                    ares[i].accept();
                            for(int i =0;i<reslist.size();i++){
                                if(reslist.get(i).equals(res)){
                                    reslist.get(i).accept();
                                    acc = true;
                                    break;
                                }
                            }
                            if(acc){
                                ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Res.kin"));
//                                wy.writeObject(ares.length);
//                                wy.writeObject(ares);
                                wy.writeObject(reslist);
                                wy.close();
                                this.oout.writeObject((String)"!OK!");
                            }else this.oout.writeObject((String)"!NORES!");
                        }
                    }
                }catch(IOException e){
                    System.err.println("IO Error from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 7 :{ // odbiór rezerwacji
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmp = (String)oin.readObject();
//                    Res ares[];
                    List<Res> reslist = new ArrayList<Res>();
                    if(tmp.equals("!OK!")){
                        File r = new File("Res.kin");
                            if(!r.exists()){
                                r.createNewFile();
                            }
                        synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
//                            int num = (Integer)we.readObject();
//                            ares = new Res[num];
//                            ares = (Res[])we.readObject();
                            reslist = (ArrayList<Res>)we.readObject();
                            we.close();
                         }
//                        this.oout.writeObject((Res[])ares);
                        Res restmp[] = new Res[reslist.size()];
                        restmp = reslist.toArray(restmp);
                        this.oout.writeObject((Res[])restmp);
                        this.oout.writeObject((String)"!GORES!"); // Get Object Res
                        Res res = (Res)oin.readObject();
                        synchronized(this){                            
                            boolean ok = false;
//                            for (int i = 0;i<ares.length;i++){
//                                if(ares[i].equals(res)){
//                                    ares[i].get();
                            for(int i = 0;i<reslist.size();i++){
                                if(reslist.get(i).equals(res)){
                                    reslist.get(i).get();
                                    System.out.println(i);
                                    ok = true;
                                    break;
                                }
                            }
                            if(ok){
                                ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Res.kin"));
//                                wy.writeObject(ares.length);
//                                wy.writeObject(ares);
                                wy.writeObject(reslist);
                                wy.close();                                
                                this.oout.writeObject((String)"!OK!");
                            }else this.oout.writeObject((String)"!NORES!");
                        }
                    }
                }catch(IOException e){
                    System.err.println("IO Error from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 8:{ // anulowanie rezerwacji
                try{
                    this.oout.writeObject((String)"!GDATA!");
                    String tmps = (String)oin.readObject();
//                    Res ares[];
                    List<Res> reslist = new ArrayList<Res>();
                    if(tmps.equals("!OK!")){
                        File r = new File("Res.kin");
                            if(!r.exists()){
                                r.createNewFile();
                            }
                         synchronized(this){
                            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Res.kin"));
//                            int num = (Integer)we.readObject();
//                            ares = new Res[num];
//                            ares = (Res[])we.readObject();
                            reslist = (ArrayList<Res>)we.readObject();
                            we.close();
                         }
//                        this.oout.writeObject((Res[])ares);
                        Res restmp[] = new Res[reslist.size()];
                        restmp = reslist.toArray(restmp);
                        this.oout.writeObject((Res[])restmp);
                        this.oout.writeObject((String)"!GORES!"); // Get Object Res
                        Res res = (Res)oin.readObject();
                        synchronized(this){
                            int tmp = -254;
//                            for (int i = 0;i<ares.length;i++){
//                                if(ares[i].equals(res)){
//                                    tmp = i;
//                                    break;
//                                }
                            for(int i = 0;i<reslist.size();i++){
                               if(reslist.get(i).equals(res)){
                                    tmp = i;
                                    reslist.remove(i);
                                    break;
                                }
                            }
                            if(tmp>-1){
                                
//                                int len = ares.length;
//                                Res arestmp[] = new Res[len];
//                                System.arraycopy(ares,0,arestmp,0,len);
//                                for(int i = tmp;i<arestmp.length-1;i++){
//                                    arestmp[i] = arestmp[i+1];
//                                }
//                                ares = new Res[len-1];
//                                System.arraycopy(arestmp,0,ares,0,len-1);
                                
                                
                                ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream("Res.kin"));
//                                wy.writeObject(ares.length);
//                                wy.writeObject(ares);
                                wy.writeObject(reslist);
                                wy.close();                                
                                 this.oout.writeObject((String)"!OK!");
                            }else  this.oout.writeObject((String)"!NORES!");
                        }
                    }
                }catch(IOException e){
                    System.err.println("IO Error from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                }catch(ClassNotFoundException e){
                    System.err.println("Class not found from "+sockfd.getInetAddress().getHostAddress()+": "+e);
                }
                break;
            }
            case 9:{
                
                break;
            }
            case 10:{
                
                break;
            }
            case 666 :{
                
                break;
            }
            case 667:{
                
                break;
            }
            default:{
                out.write("!NOCMD!");
            }
            
     }
    }
    private boolean checkUser(){
        String usr = this.luser.getName();
        String pwd = this.luser.getPass();//sha1pass
        
        boolean log = false;
        try{
            //BufferedReader userfile = new BufferedReader(new InputStreamReader(new FileInputStream("users.txt")));
            ObjectInputStream we = new ObjectInputStream(new FileInputStream("Users.kin"));
            try {
                List<User> ar = (ArrayList<User>)we.readObject();
                User[] tmp = ar.toArray(new User[]{}); 
                //User[] tmp = (User[])we.readObject();
                we.close();
                int ctmp = 0;
                for(int i=0;i<tmp.length;i++){
                    if(usr.equals(tmp[i].getName())){
                        ctmp = i;
                        break;
                    }
                }
                if(pwd.equals(tmp[ctmp].getPass())){
                    log = true;
                }
            } catch (ClassNotFoundException e) {
                System.err.println("ClassNotFoundException from "+sockfd.getInetAddress().getHostAddress()+": "+e);
            }
        }catch(IOException e){
            System.err.println("IO Error from "+sockfd.getInetAddress().getHostAddress()+": "+e);
        }
        return log;
    }
    private Object getObj(String file){
        try{
            ObjectInputStream we = new ObjectInputStream(new FileInputStream(file));
            Object obj = we.readObject();
            return obj;
        }catch(IOException e){
            System.err.println("Błąd odczytu pliku");
        }catch(ClassNotFoundException c){
            System.err.println("Brak klasy Object xD");
        } 
        return null;
    } 
    
    
     private String toSHA1(byte[] pass) {
        MessageDigest md;
        try{
            md = MessageDigest.getInstance("SHA-1");
        }catch(NoSuchAlgorithmException e){
            System.err.println("No Such Algorithm Exception: "+e);
            return null;
        }
        return byteToHex(md.digest(pass));
    }
    
    private String byteToHex(byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
}
