/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package okienka;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import kinomaniak_objs.Show;
import kinomaniak_z_interfejsem.KinomaniakKlientMoj2;

/**
 *
 * @author Adam
 */
public class OknoSeansow extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form OknoSeansow
     */
    public OknoSeansow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel1.setText("Sala 1:");

        jLabel2.setText("Sala 2:");

        jLabel3.setText("Sala 3:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel1)
                .addGap(160, 160, 160)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(71, 71, 71))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(504, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void rysujPrzyciski() {
        int x=50,y=50;int i1=1;int i2=1;int i3=1;
        JButton[] button = new JButton[30];
        Show[] shss = KinomaniakKlientMoj2.klient2.getShow();
        for (int i=0;i<shss.length-1;i++){
                    button[i] = new JButton();          
                    button[i].setSize(250,60);
                    System.out.println(shss[i].getMovie().getName()+"-"+shss[i].getFormattedDate()
                            +"sala: "+shss[i].getRoom().getID()+"\n");
                    button[i].setText(shss[i].getMovie().getName()+"\n"+shss[i].getFormatted());
                    if (shss[i].getRoom().getID()==1){
                        button[i].setLocation(30,y+(i1*61));
                        i1++;
                        button[i].setName("Przycisk1");
                    }
                    if (shss[i].getRoom().getID()==2){
                        button[i].setLocation(280,y+(i2*61));
                        i2++;
                        button[i].setName("Przycisk2");}
                    if (shss[i].getRoom().getID()==3){
                        button[i].setLocation(530,y+(i3*61));
                        i3++;
                        button[i].setName("Przycisk3");}
                    KinomaniakKlientMoj2.klient2.oknseans.add(button[i]);
                    button[i].addActionListener(this);
                    KinomaniakKlientMoj2.klient2.oknseans.repaint();
        }
    }
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
            java.util.logging.Logger.getLogger(OknoSeansow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OknoSeansow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OknoSeansow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OknoSeansow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OknoSeansow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton zrodlo = (JButton) ae.getSource();
        if (zrodlo.getName().equals("Przycisk1")){
            System.out.println("oto1");
        }
          if (zrodlo.getName().equals("Przycisk2")){
            System.out.println("oto2");
        }
          if (zrodlo.getName().equals("Przycisk1")){
            System.out.println("oto3");
        }
        
        //To change body of generated methods, choose Tools | Templates.
    }
}