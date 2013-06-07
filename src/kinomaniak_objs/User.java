/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_objs;
import java.io.Serializable;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @author qbass
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String password;
    private int utype;
    private int[] availcmds;
    
    /**
     * Utworzenie instancji klasy użytkownika o podanych parametrach i ustawiająca dostępne komendy
     * @param name nazwa użytkownika
     * @param password hasło w czystej postaci
     * @param utype type(0-klient,1-kasjer)
     */
    public User(String name, String password,int utype){
        this.name = name;
        this.password = password;
        this.utype = utype;
        this.availcmds = new int[12];
        this.setCmds();
    }
    
    /**
     * Metoda zwracająca dostępne komendy
     * @return tablica int[] z dostępnymi komendami dla danego użytkownika
     */
    public int[] getACmds(){
        return this.availcmds;
    }    
    /**
     * Metoda zwracająca nazwę użytkownika
     * @return nazwa użytkownika
     */
    public String getName(){
        return this.name;
    }
    /**
     * Metoda zwracająca hasło zahashowane algorytmem SHA1
     * @return ciąg SHA1 
     */
    public String getPass(){
        return toSHA1(this.password.getBytes());
    }
    /**
     * Metoda zwracająca typ użytkownika
     * @return typ użytkownika
     */
    public int getUType(){
        return this.utype;
    }
    /**
     * Metoda ustawiająca tablicę z dostępnymi komendami dla danego użytkownika
     */
    private void setCmds(){
        for(int i=0;i<12;i++){
            this.availcmds[i] = 0;
        }
        switch(this.utype){
            case 0 : { //Client
                this.availcmds[0] = 5;
                break;
            }
            case 1 : { //User
                this.availcmds[0] = 5;
                this.availcmds[1] = 6;
                this.availcmds[2] = 7;
                this.availcmds[3] = 8;
                break;
            }
            case 2 : { //SuperUser/Admin
                this.availcmds[0] = 1;
                this.availcmds[1] = 2;
                this.availcmds[2] = 3;
                this.availcmds[3] = 4;
                this.availcmds[4] = 5;
                this.availcmds[5] = 6;
                this.availcmds[6] = 7;
                this.availcmds[7] = 8;
                this.availcmds[8] = 9;
                this.availcmds[9] = 10;
                this.availcmds[10] = 666;
                this.availcmds[11] = 667;
                break;
            }
        }
    }
    /**
     * Metoda hashująca hasło algorytmem SHA1
     * @param pass hasło w czystej postaci
     * @return hasło w SHA1
     */
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
