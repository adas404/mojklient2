/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_interfejs;

import kinomaniak_objs.Res;

/**
 *
 * @author JK
 */
public interface KinomaniakInterface {
    
    //przekazuja login i pass do sprawdzenia klientowi
   
        public int setLogin(String log,String pas);
      //
        public int connect(); // wlasnie nie wiem jak to zalatwic, ale fajnie by bylo gdyby tutaj lączyl z serwerem, pobierał bazę i wchodzil do gotowości
        //metoda dzialaj do while
   //przechodzenie do kolejno: rezerwacji, anulowania, odbioru
        public int goToReserve(String imnaz,int idse, int[][] miejsca); // na koncu wywołanie! najpierw SETY!
        public int goToCancelRes(String imienaz);
        public int goToGetRes(String imienaz); //
    //
        
    //zwracanie danych rezerwacji dla gui    
        public Res getRezerwacja();
    //
        
    //pobieranie danych rezerwacji z gui przez klienta    
      //  public int setImieNazw(String imnaz);
       // public int setIdSeansu(int idse);
       // public int setMiejsca(int[][] miejsca);//jako miejsca[0][0] - rzad, miejsca[0][1] -miejsca, miejsca [1][0] -rzad itd.
    //
        
    //wysylanie info do gui czy miejsce jest wolne
        public int czyZajete(int rz, int mie);
    //
        public String zwrocOpis(String tytul);
}
