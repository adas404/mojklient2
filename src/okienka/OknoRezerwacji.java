/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package okienka;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import kinomaniak_objs.Res;
import kinomaniak_objs.Show;
import kinomaniak_z_interfejsem.KinomaniakKlientMoj2;

/**
 *
 * @author Adam
 */
public class OknoRezerwacji extends javax.swing.JFrame {

    /**
     * Creates new form OknoRezerwacji
     */
    Res[] tabres = null;
    public OknoRezerwacji() {
        initComponents();
        
    }
    public javax.swing.JTable getTabela(){
        return tabelaRezerwacji;
}
    public DefaultTableModel tab;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaRezerwacji = new javax.swing.JTable();
        usunRezerwacje = new javax.swing.JButton();
        resPowrot = new javax.swing.JButton();
        odbierz = new javax.swing.JButton();
        sprzedane = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabelaRezerwacji.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Imie i nazwisko", "Film", "Miejsca", "ShowID", "Data:", "Odebrana"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaRezerwacji);

        usunRezerwacje.setText("Usuń rezerwacje");
        usunRezerwacje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usunRezerwacjeActionPerformed(evt);
            }
        });

        resPowrot.setText("Powrót");
        resPowrot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resPowrotActionPerformed(evt);
            }
        });

        odbierz.setText("Odbierz rezerwację");
        odbierz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                odbierzActionPerformed(evt);
            }
        });

        sprzedane.setSelected(true);
        sprzedane.setText("Ukryj sprzedane");
        sprzedane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprzedaneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(resPowrot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(usunRezerwacje)
                .addGap(35, 35, 35)
                .addComponent(odbierz)
                .addGap(55, 55, 55)
                .addComponent(sprzedane)
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usunRezerwacje)
                    .addComponent(resPowrot)
                    .addComponent(odbierz)
                    .addComponent(sprzedane))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usunRezerwacjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usunRezerwacjeActionPerformed
        // TODO add your handling code here:
        int Wiersz =tabelaRezerwacji.getSelectedRow();
        Object imnaz =(String)tabelaRezerwacji.getValueAt(Wiersz, 0);
        KinomaniakKlientMoj2.klient2.goToCancelRes((String)imnaz);
        System.out.println("Stirng"+imnaz);
        JOptionPane.showMessageDialog(null,imnaz);
        this.usunTabele();
        this.stworzTabele();
        tabelaRezerwacji.revalidate();
        tabelaRezerwacji.repaint();
         KinomaniakKlientMoj2.klient2.oknrez.revalidate();
        KinomaniakKlientMoj2.klient2.oknrez.repaint();
    }//GEN-LAST:event_usunRezerwacjeActionPerformed
    
    public void stworzTabele(){
         tab = (DefaultTableModel)KinomaniakKlientMoj2.klient2.oknrez.getTabela().getModel();
         KinomaniakKlientMoj2.klient2.pobierzRezerwacje();
         tabres = KinomaniakKlientMoj2.klient2.getRezerwacja();
         Show[] shss = KinomaniakKlientMoj2.klient2.getShow();
         for (int i=0;i<tabres.length;i++){ //tabres[i].getShowID()
             int id = 0;
             for(int j = 0; j < shss.length-1; j++) if(shss[j].getID() == tabres[i].getShowID()) id = j;
             if(tabres[i].isok()) 
                if (sprzedane.isSelected())
                    continue;
                tab.addRow(new Object[]{tabres[i].getName(),shss[id].getMovie().getName(),tabres[i].formatSeats(),tabres[i].getShowID(),
                shss[id].getFormatted(),tabres[i].isok(),
                });
         
         }
         
         tab.fireTableDataChanged(); 
      
    }
    public void usunTabele(){
        tab.setRowCount(0);
    }
    private void resPowrotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resPowrotActionPerformed
        // TODO add your handling code here:
        KinomaniakKlientMoj2.klient2.oknrez.setVisible(false);
        KinomaniakKlientMoj2.klient2.okngl.setVisible(true);
        this.usunTabele();
    }//GEN-LAST:event_resPowrotActionPerformed

    private void odbierzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_odbierzActionPerformed
        // TODO add your handling code here:
        KinomaniakKlientMoj2.klient2.oknsali.setVisible(true);
         KinomaniakKlientMoj2.klient2.oknrez.setVisible(false);
         int Wiersz =tabelaRezerwacji.getSelectedRow();
         Object tmp =(int)tabelaRezerwacji.getValueAt(Wiersz, 3);
         Object imn =(String)tabelaRezerwacji.getValueAt(Wiersz, 0);
         System.out.println("znaleziono ShowID:"+tmp + "imie"+imn);
         KinomaniakKlientMoj2.klient2.oknsali.rysujSale((int)tmp);
         for(int i=0;i<tabres.length;i++){
             String tmp2 = tabres[i].getName();
                if((tabres[i].getName().equals(imn))&&(tabres[i].getShowID()==tmp)){
                    KinomaniakKlientMoj2.klient2.oknsali.poOdbiorze(tabres[i].getSeats());
           }
          KinomaniakKlientMoj2.klient2.goToCancelRes((String)imn);
         }
         this.usunTabele();
    }//GEN-LAST:event_odbierzActionPerformed

    private void sprzedaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprzedaneActionPerformed
        // TODO add your handling code here:
      //  if (!sprzedane.isSelected()){
            this.usunTabele();
            this.stworzTabele();
             KinomaniakKlientMoj2.klient2.oknrez.revalidate();
            KinomaniakKlientMoj2.klient2.oknrez.repaint();
       // }
    }//GEN-LAST:event_sprzedaneActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OknoRezerwacji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OknoRezerwacji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OknoRezerwacji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OknoRezerwacji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OknoRezerwacji().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton odbierz;
    private javax.swing.JButton resPowrot;
    private javax.swing.JCheckBox sprzedane;
    private javax.swing.JTable tabelaRezerwacji;
    private javax.swing.JButton usunRezerwacje;
    // End of variables declaration//GEN-END:variables
}
