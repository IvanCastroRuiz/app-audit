/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ingresos;

import BD_As400.JConection;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;


/**
 *
 * @author Garyn Carrillo
 */
public class JCambioCheque extends javax.swing.JFrame {

    /**
     * Creates new form JCambioCheque
     */
    private Vector Cabecera_Fila;
    private Vector Detalle;
    private Vector Detalle_Fila;
    private String jTextField1, jTextField2;
    private Vector Detalle2;
    private Vector Detalle_Fila2;
    private SimpleDateFormat d;
    
    private JConection JBase_Datos;
    private Connection Cn;
    private String NumeroFormato = "$ ###,###,###,###.##";
    private DecimalFormat JFormato ;
    
    public JCambioCheque(JConection JBase_Datos3, Connection Cn2) {
        this.JBase_Datos  = JBase_Datos3;
        this.Cn = Cn2;
        this.Cabecera_Fila = new Vector();
        this.Cabecera_Fila.add("Fecha");
        this.Cabecera_Fila.add("Valor");
        this.Cabecera_Fila.add("Usuario");
        this.Cabecera_Fila.add("Banco");
        this.Detalle = new Vector();
        this.Detalle2 = new Vector();
        JFormato= new DecimalFormat(NumeroFormato);
        initComponents();
        jXDatePicker1.setFormats("yyyyMMdd");
        jXDatePicker2.setFormats("yyyyMMdd");
        d = new SimpleDateFormat("yyyyMMdd");
        jXDatePicker3.setFormats("yyyyMMdd"); 
        jXDatePicker4.setFormats("yyyyMMdd");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTCheque1 = new javax.swing.JTable();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTCheque = new javax.swing.JTable();
        jXDatePicker3 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker4 = new org.jdesktop.swingx.JXDatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informe de Cambio de Cheques");

        jButton2.setText("Buscar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        JTCheque1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Valor", "Usuario", "Banco"
            }
        ));
        jScrollPane1.setViewportView(JTCheque1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cambio Venta Retenida", jPanel1);

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        JTCheque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Valor", "Usuario", "Banco"
            }
        ));
        jScrollPane2.setViewportView(JTCheque);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jXDatePicker3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXDatePicker4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jXDatePicker4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cambio de Fondo", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
       try{
           jTextField1 = d.format(jXDatePicker1.getDate()).toString();
           jTextField2 = d.format(jXDatePicker2.getDate()).toString();
            if(!jTextField1.equals("") && ! jTextField2.equals("")){
                this.getCambioCheque();
            }else{
                JOptionPane.showMessageDialog(this,"Debe ingresar la fecha");	
            }
       }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Debe ingresar una fecha no mayor a un Mes "+e.getMessage());
       }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
            
            jTextField1 = d.format(jXDatePicker3.getDate()).toString();
            jTextField2 = d.format(jXDatePicker4.getDate()).toString();
            if(!jTextField1.equals("") && !jTextField2.equals("")){
                getCambioCheque_Fondo();
            }else{
                JOptionPane.showMessageDialog(this,"Debe ingresar la fecha");	
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Debe ingresar una fecha no mayor a un Mes "+e.getMessage());
        }
   
    }//GEN-LAST:event_jButton1ActionPerformed
    public String getUsuario(String Usu){
        String MUsuario = "";
        try {
            String Str_Sql = "select usunom from recaudos.musuari where usucod= '"+Usu.trim()+"'";
            ResultSet Rs =  JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(Rs.next()){
                MUsuario = Rs.getString("usunom");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getUsuario ()"+e.getMessage());	
        }
        return MUsuario;
    }
    public void getCambioCheque_Fondo(){
        try {
            String Str_Sql = "select camfec,camtot,usucod,camban from recaudos.CAMCHE where camfec>= '"+jTextField1.trim()+"' and camfec<='"+jTextField2.trim()+"'"
                    + " order by camfec desc";
            ResultSet Rs =  JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.Detalle2.clear();
            double Suma = 0;
            while(Rs.next()){
                this.Detalle_Fila2 = new Vector();
                this.Detalle_Fila2.add(Rs.getString("camfec"));
                double Temp  = Rs.getDouble("camtot");
                Suma= Suma + Temp;
                this.Detalle_Fila2.add(this.JFormato.format(Temp));
                this.Detalle_Fila2.add(this.getUsuario(Rs.getString("usucod")));
                this.Detalle_Fila2.add(Rs.getString("camban"));
                this.Detalle2.add(this.Detalle_Fila2);
            }
            if(Detalle2.size()!=0){
                this.Detalle_Fila2 = new Vector();
                this.Detalle_Fila2.add("Total");
                this.Detalle_Fila2.add(this.JFormato.format(Suma));
                this.Detalle_Fila2.add("");
                this.Detalle_Fila2.add("");
                this.Detalle2.add(this.Detalle_Fila2);
            }
            JTCheque.setModel(new javax.swing.table.DefaultTableModel(this.Detalle2, this.Cabecera_Fila));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getCambioCheque_Fondo()"+e.getMessage());	
        }
    }    
    
    public void getCambioCheque(){
        try {
            String Str_Sql = "select plafer,chesub,usucod,bancod from recaudos.PCHEQUE where plafer>= '"+jTextField1.trim()+"' and plafer <='"+jTextField2.trim()+"'"
                    + " order by plafer desc";
            ResultSet Rs =  JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.Detalle.clear();
            double Suma = 0;
            while(Rs.next()){
                this.Detalle_Fila = new Vector();
                this.Detalle_Fila.add(Rs.getString("plafer"));
                double Temp = Rs.getDouble("chesub");
                Suma = Suma +Temp;
                this.Detalle_Fila.add(this.JFormato.format(Temp));
                this.Detalle_Fila.add(this.getUsuario(Rs.getString("usucod")));
                this.Detalle_Fila.add(Rs.getString("bancod"));
                this.Detalle.add(this.Detalle_Fila);
            }
            if(Detalle.size()!=0){
                this.Detalle_Fila = new Vector();
                this.Detalle_Fila.add("Total");
                this.Detalle_Fila.add(this.JFormato.format(Suma));
                this.Detalle_Fila.add("");
                this.Detalle_Fila.add("");
                this.Detalle.add(this.Detalle_Fila);
            }
            JTCheque1.setModel(new javax.swing.table.DefaultTableModel(this.Detalle, this.Cabecera_Fila));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getCambioCheque ()"+e.getMessage());	
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JCambioCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JCambioCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JCambioCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JCambioCheque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JCambioCheque(null,null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTCheque;
    private javax.swing.JTable JTCheque1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker3;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker4;
    // End of variables declaration//GEN-END:variables
}
