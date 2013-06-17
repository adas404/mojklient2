/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_z_interfejsem;

import java.io.IOException;
import java.util.Scanner;
import kinomaniak_objs.Res;
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
        Res[] res;
        int[][] sala;
        Scanner in = new Scanner(System.in);  
        System.out.println("Podaj login:");
        String login = in.nextLine();
        System.out.println("Podaj hasło:");
        String pass = in.nextLine();
        Klient2 klient2 = new Klient2();
        klient2.setLogin(login,pass);
        while (true){
            komenda = Integer.parseInt(in.nextLine());
            switch(komenda){
                case 1:{//wyświetl filmy
                    shss = klient2.getShow();
                    for (Show sh : shss){
                        System.out.println("Film: "+sh.getMovie().getName()+"Data: "+sh.getFormatted()+"ShowID: "+sh.getID());
                    }
                 break;
                }
                case 2:{//wyswietl rezerwacje
                    res = klient2.getRezerwacja();
                    for (Res rs : res){
                        System.out.println("Imie i Nazwisko: "+rs.getName()+"ShowID: "+rs.getShowID()+rs.formatSeats());
                    }
                    klient2.rozlacz();
                break;
                }
                case 3:{//wyswietl sale
                    System.out.println("Podaj IDShow sali którą chcesz wyświetlić:");
                    komenda = Integer.parseInt(in.nextLine());
                    sala = klient2.czyZajete(komenda);
                    for (int i=0;i<10;i++){
                        for (int j=0;j<10;j++){
                            System.out.print(sala[i][j]+" ");
                        }
                        System.out.println("");
                    }
                 break;   
                }
                case 4:{//rezerwuj bilet
                    System.out.println("Podaj imie i nazwisko do rezerwacji:");
                    String imie_i_nazwisko = in.nextLine();
                    System.out.println("Podaj ShowID na który chesz zarezerwować bilet:");
                    int showid = Integer.parseInt(in.nextLine());
                    System.out.println("Ilość miejsc do zarezerwowania: ");
                    int tmps = Integer.parseInt(in.nextLine());
                    int[][] tmpseat = new int[tmps][2];
                    for(int i=0;i<tmps;i++){
                        System.out.print(i+". Podaj rząd w którym chcesz siedzieć: ");
                         tmpseat[i][0] = Integer.parseInt(in.nextLine());
                         System.out.print(i+". Podaj miejsce w rzędzie na którym chcesz siedzieć: ");
                        tmpseat[i][1] = Integer.parseInt(in.nextLine());
                  }
                 klient2.goToReserve(imie_i_nazwisko,showid,tmpseat);
                 
              break;   
            }
                case 5:{//sprzedaj bilet
                    System.out.println("Podaj ShowID na który chesz sprzedać bilet:");
                    int showid = Integer.parseInt(in.nextLine());
                    System.out.println("Ilość miejsc do sprzedania: ");
                    int tmps = Integer.parseInt(in.nextLine());
                    int[][] tmpseat = new int[tmps][2];
                    for(int i=0;i<tmps;i++){
                        System.out.print(i+". Podaj rząd w którym chcesz siedzieć: ");
                         tmpseat[i][0] = Integer.parseInt(in.nextLine());
                         System.out.print(i+". Podaj miejsce w rzędzie na którym chcesz siedzieć: ");
                        tmpseat[i][1] = Integer.parseInt(in.nextLine());
                    }
                   klient2.sprzedajBilet(showid, tmpseat);
                   break;
                } 
                case 6:{//odbierz rezerwacje
                    System.out.println("Podaj imie i nazwisko na które była rezerwacja");
                    String imie_i_nazwisko = in.nextLine();
                    klient2.goToGetRes(imie_i_nazwisko);
                    break;
                }
                case 7:{//anuluj rezerwacje
                     System.out.println("Podaj imie i nazwisko na które była rezerwacja");
                     String imie_i_nazwisko = in.nextLine();
                     klient2.goToCancelRes(imie_i_nazwisko);
                     break;
                }
                    
        }
       //  String tmp = (String)klient2.odbierzO();   
        }
    }
}
