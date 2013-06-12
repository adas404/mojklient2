/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinomaniak_interfejs;

/**
 *
 * @author JK
 */
public interface KinomaniakInterface {
    
    //przekazuja login i pass do sprawdzenia klientowi
        public String setLogin(String log);
        public String setHaslo(String pas);
    //
//        public int setLogin(String log,String pas);
        public void connect(); // wlasnie nie wiem jak to zalatwic, ale fajnie by bylo gdyby tutaj lączyl z serwerem, pobierał bazę i wchodzil do gotowości
        //metoda dzialaj do while
   //przechodzenie do kolejno: rezerwacji, anulowania, odbioru
        public void goToReserve(); // dalej wywoluje sety
        public void goToCancelRes();
        public void goToGetRes(); //
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
