/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_z_interfejsem;

import java.io.IOException;
import java.util.Scanner;
import kinomaniak_objs.Show;

/**
 *
 * @author Adam
 */
public class KinomaniakKlientMoj2 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        // TODO code application logic here
        int komenda;
        Show[] shss;
        Scanner in = new Scanner(System.in);  
        System.out.println("Podaj login:");
        String login = in.nextLine();
        System.out.println("Podaj hasło:");
        String pass = in.nextLine();
        Klient2 klient2 = new Klient2();
        klient2.setLogin(login,pass);
        while (true){
            komenda = in.nextInt();
            switch(komenda){
                case 1:{//wyświetl filmy
                    shss = klient2.getShow();
                    klient2.rozlacz();
                }
            }
        }
      
    }
}
