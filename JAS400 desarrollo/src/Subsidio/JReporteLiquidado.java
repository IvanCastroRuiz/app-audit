/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Subsidio;

import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 *
 * @author GACA1186
 */
public class JReporteLiquidado extends javax.swing.JFrame {

    /**
     * Creates new form JReporteLiquidado
     */
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private Vector Cab, Det, Fila;
    private JConection JBase_Datos;
    private Connection Cn;
    
    
    public JReporteLiquidado(JConection JBase_Datos3, Connection Cn2) {
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        JFormato= new DecimalFormat(NumeroFormato);
        
        Cab = new Vector();
        Cab.add("Mes");
        Cab.add("Valor");
        Cab.add("Cantidad Beneficiario");
        Fila = new Vector();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reporte de Subidios  Pagados vs  610505");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mes", "Valor", "Cantidad Beneficiarios"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Consultar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);

        jTextField4.setEditable(false);

        jTextField5.setEditable(false);

        jLabel1.setText("Debito");

        jLabel2.setText("Credito");

        jLabel3.setText("Con Ajuste");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel1)
                        .addGap(81, 81, 81)
                        .addComponent(jLabel2)
                        .addGap(78, 78, 78)
                        .addComponent(jLabel3)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.getInformacion();
        this.getContabilidad();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    public void getContabilidad(){
        try {
            String Str_Sql=" select ctamov ,sum(debmov) deb,         \n" +
                           " sum(cremov) cred , sum(debmov)-sum(cremov) dif  \n" +
                           " from condatNIC.movimi                          \n" +
                           " where ctamov ='610505' and docmov <>'96'        \n" +
                           "    and fecmov>="+jTextField1.getText().trim()+" and fecmov <= "+jTextField2.getText().trim()+"  \n" +
                           " group by ctamov ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                jTextField3.setText(""+this.JFormato.format(rs.getDouble("deb")));
                jTextField4.setText(""+this.JFormato.format(rs.getDouble("cred")));
                jTextField5.setText(""+this.JFormato.format(rs.getDouble("dif")));
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " "+e.getMessage()+" getContabilidad()");
        }
    }
    
    
    public void getInformacion(){
        try {
            String Str_Sql =" select substr(pfeliq , 1, 6)as mes , sum(pvaliq) as valor , count(*) cant_bene        \n" +
                            "         from subsilib.liquipag  as t1             \n" +
                            "where pfeliq >= "+this.jTextField1.getText().trim()+" and pfeliq <= "+this.jTextField2.getText().trim()+" \n" +
                            "and pcoliq  in( 1, 30)                             \n" +
                            "group by substr(pfeliq , 1, 6)                     \n" +
                            "order by 1                                         ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double  Total = 0;
            double Cantidad_beneficiario = 0;
            Fila.clear();
            while(rs.next()){
                Det = new Vector();
                Det.add(rs.getString("mes"));
                Total = Total + rs.getDouble("valor");
                Det.add(this.JFormato.format(rs.getDouble("valor")));
                Det.add(this.JFormato.format(rs.getDouble("cant_bene")));
                Cantidad_beneficiario = Cantidad_beneficiario+ rs.getDouble("cant_bene");
                Fila.add(Det);
            }
            if(Total!=0){
                Det = new Vector();
                Det.add("Total ---> ");
                Det.add(this.JFormato.format(Total));
                Det.add(this.JFormato.format(Cantidad_beneficiario));
                Fila.add(Det);
            }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Fila, Cab));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " "+e.getMessage());
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
            java.util.logging.Logger.getLogger(JReporteLiquidado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JReporteLiquidado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JReporteLiquidado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JReporteLiquidado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JReporteLiquidado(null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
