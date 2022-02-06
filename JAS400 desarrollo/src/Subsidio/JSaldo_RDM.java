/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Subsidio;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;



import BD_As400.JConection;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import java.text.DecimalFormat;

/**
 *
 * @author GACA1186
 */
public class JSaldo_RDM extends javax.swing.JFrame {
    private JArchivo_RDM JFile;
    private JArchivo_RDM_TC JFile_TC;
    private String Ruta;
    private Vector Lista;
    private String NombreArchivo;
    private JConection JBase_Datos;
    private Connection Cn;
    
    private Vector Cabecera;
    private Vector Detalle;
    private Vector Fila;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat  JFormato ;
    /**
     * Creates new form JSaldo_RDM
     */
    public JSaldo_RDM(JConection JBase_Datos3, Connection Cn2) {
        Lista = new Vector();
        this.Cabecera = new Vector();
        Cabecera.add("Bolsillo");
        Cabecera.add("Valor");
        Detalle = new Vector();
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        JFormato= new  DecimalFormat(NumeroFormato);
        initComponents();
    }
    public void setRuta(String SRuta){
         this.Ruta = SRuta;
         this.LeerArchivo();
    }
    public void setRuta_TC(String SRuta){
         this.Ruta = SRuta;
         this.LeerArchivo_TC();
    }
     public void LeerArchivo_TC(){
          try{
            // Abrimos el archivo
              
            FileInputStream fstream = new FileInputStream(Ruta);
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            
            String Str_Sql="delete from selinlib.JREADRDM_3 ";
            boolean Rs = this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
            if(Rs){
                while ((strLinea = buffer.readLine()) != null)   {
                    
                    //this.jList1.add(strLinea.toLowerCase());
                   String Array_Campo[] = strLinea.split(",");
                   Str_Sql ="insert into selinlib.JREADRDM_3 values('"+Array_Campo[0]+"','"+Array_Campo[1]+"','"+Array_Campo[2]+"','"+Array_Campo[3]+"','"+Array_Campo[4]+"')";
                    System.out.println(""+Str_Sql);
                   Rs= this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
                }
             
            }
            // Cerramos el archivo
            entrada.close();
            this.setTable();
     
        }catch (Exception e){ //Catch de excepciones
            javax.swing.JOptionPane.showMessageDialog(this, "ReadFile:_LeerArchivo() "+e.getMessage());
        }
    }
    
    public void LeerArchivo(){
          try{
            // Abrimos el archivo
              
            FileInputStream fstream = new FileInputStream(Ruta);
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            
            String Str_Sql="delete from selinlib.JREADRDM_2 ";
            boolean Rs = this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
            if(Rs){
                while ((strLinea = buffer.readLine()) != null)   {
                    
                    //this.jList1.add(strLinea.toLowerCase());
                   String Array_Campo[] = strLinea.split(",");
                   Str_Sql ="insert into selinlib.JREADRDM_2 values('"+Array_Campo[0]+"','"+Array_Campo[1]+"','"+Array_Campo[2]+"','"+Array_Campo[3]+"',"+Array_Campo[4]+","+Array_Campo[5]+","+Array_Campo[6]+","+Array_Campo[7]+","+Array_Campo[8]+","+Array_Campo[9]+")";
                   Rs= this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
                }
             
            }
            // Cerramos el archivo
            entrada.close();
            this.setTable();
     
        }catch (Exception e){ //Catch de excepciones
            javax.swing.JOptionPane.showMessageDialog(this, "ReadFile:_LeerArchivo() "+e.getMessage());
        }
    }
    public void setTable(){
        try {
             String StrSql = "SELECT BOLSILLO , SUM(VALOR)/100 AS VALOR    " +
                             " FROM SELINLIB.JREADRDM_2                   " +
                             " GROUP BY BOLSILLO                          ";
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             while (rs.next()) {
                 Fila = new Vector();
                 Fila.add(rs.getString("Bolsillo"));
                 Fila.add(JFormato.format(rs.getDouble("VALOR")));
                 Detalle.add(Fila);
             }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Detalle, Cabecera));
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "setTable()) "+e.getMessage());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Control de Saldo Tarjeta Multiservicios - Redeban");

        jButton1.setText("Examinar...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bolsillo", "Valor"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Archivo de Saldo CT");

        jButton2.setText("Examinar tc");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 804, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(308, 308, 308)))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jButton2)))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFile = new JArchivo_RDM (new javax.swing.JFrame(), true, this);
        JFile.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFile_TC = new JArchivo_RDM_TC (new javax.swing.JFrame(), true, this);
        JFile_TC.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(JSaldo_RDM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JSaldo_RDM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JSaldo_RDM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JSaldo_RDM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JSaldo_RDM(null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
