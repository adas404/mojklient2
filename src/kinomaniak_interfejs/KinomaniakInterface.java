/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak2;

/**
 *
 * @author JK
 */
public interface KinomaniakInterface {
    
    //przekazuja login i pass do sprawdzenia klientowi
        public String setLogin(int log);
        public String setHaslo(int pas);
    //
        
        public void connect(); // wlasnie nie wiem jak to zalatwic, ale fajnie by bylo gdyby tutaj lączyl z serwerem, pobierał bazę i wchodzil do gotowości
        
   //przechodzenie do kolejno: rezerwacji, anulowania, odbioru
        public void goToReserve();
        public void goToCancelRes();
        public void goToGetRes();
    //
        
    //zwracanie danych rezerwacji dla gui    
        public String getImieNazw();
        public int getIdSeansu();
        public int getRzad();
        public int getNrMiejsca();
    //
        
    //pobieranie danych rezerwacji z gui przez klienta    
        public String setImieNazw(String imnaz);
        public int setIdSeansu(int idse);
        public int setRzad(int rz);
        public int setNrMiejsca(int mie);
    //
        
    //wysylanie info do gui czy miejsce jest wolne
        public int czyZajete(int rz, int mie);
    //
}
