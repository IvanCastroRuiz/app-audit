/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auditoria;

import BD_Auditoria.JConeccion_AudiSys;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Garyn Carrillo
 */
public class JSeguimientos extends javax.swing.JFrame {

    /**
     * Creates new form JSeguimientos
     */
    private JConeccion_AudiSys JBase_Datos;
    private Connection Connection_BD;
    private Vector Cabecera;
    private Vector Detalle_Total;
    private Vector Detalle_Fila;
    
    public JSeguimientos(JConeccion_AudiSys JBase_Datos, Connection Connection_BD) {
        initComponents();
        this.JBase_Datos=JBase_Datos;
        this.Connection_BD =Connection_BD;
        this.setCabecera();
        this.getAños();
    }
    
    public void setCabecera(){
        this.Cabecera = new Vector();
        this.Cabecera.add("No Trabajo");
        this.Cabecera.add("Codigo");
        this.Cabecera.add("Descripcion del Hallazgo");
        this.Cabecera.add("Accion de mejora");
        this.Cabecera.add("Proposito de la Accion");
        this.Cabecera.add("%Avance");
        this.Cabecera.add("Estado Actual");
        this.Cabecera.add("Division Responsable");
        this.Detalle_Total = new Vector();
    
    }
    public void setAnchoColumnas(){
        jPlanMejoramiento.getColumnModel().getColumn(0).setPreferredWidth(20);
        jPlanMejoramiento.getColumnModel().getColumn(1).setPreferredWidth(20);
        jPlanMejoramiento.getColumnModel().getColumn(2).setPreferredWidth(600);
        jPlanMejoramiento.getColumnModel().getColumn(3).setPreferredWidth(150);
        jPlanMejoramiento.getColumnModel().getColumn(4).setPreferredWidth(90);
        jPlanMejoramiento.getColumnModel().getColumn(5).setPreferredWidth(20);
        jPlanMejoramiento.getColumnModel().getColumn(6).setPreferredWidth(90);
    }

    public void getAños(){
        try {
            String Str_Sql = "select distinct Ano from JTaller order by Ano desc";
            ResultSet Rs =  JBase_Datos.SQL_QRY(Connection_BD,Str_Sql);
            jCAño.removeAll();
            while(Rs.next()){
                jCAño.addItem(Rs.getString("Ano").trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "getAños() "+e.getMessage());
        }
    }
     public Vector ExtraeRecomendacion(String Txt){
        Vector Reco= new Vector();
        Reco.clear();
        int Cuenta = 0;
        String Frase="";
        try {
            for (int i = 0; i < Txt.length(); i++) {
                String Caracter = String.valueOf(Txt.charAt(i));
                if(Cuenta==2){
                    Cuenta=0;
                    Frase = Frase +Caracter;
                    if(!Frase.trim().equals("")){
                        Reco.add(Frase);
                    }
                    Frase = "";
                }else{
                    Frase = Frase +Caracter;
                }
                String Caracter2 = "";
                if((i+1)< Txt.length()){
                    Caracter2 = String.valueOf(Txt.charAt(i))+String.valueOf(Txt.charAt(i+1));
                }
                if(Caracter2.equals("..")){
                    Cuenta = 2;
                    
                }
            }
        } catch (Exception e) {
              JOptionPane.showMessageDialog(this,"ExtraeRecomendacion () "+e.getMessage());  
        }
        return Reco;
        
    }
    public boolean ExisteSeguimiento(String IdPlan){
        boolean existe = false;
        try {
            String Str_Sql = "select * from JSeguimientos where IdPlan="+IdPlan;
            ResultSet Rs =  JBase_Datos.SQL_QRY(Connection_BD,Str_Sql);
            if(Rs.next()){
                existe = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"ExisteSeguimiento() "+e.getMessage());  
        }
        return existe;
    }
    public boolean setSeguimiento(String IdPlan, String DescripcionHallazgo, String AccionMejora , String Proposito, double PAvance, String EstadoActual){
        boolean Rs=false;
        try {
            String Str_Sql = "";
            if( ExisteSeguimiento(IdPlan)){
                Str_Sql = "insert into JSeguimientos () Values()";
            }else{
                Str_Sql = "update JSeguimientos set"
                        + " "
                        + "where ";
            }
            Rs =  JBase_Datos.Exc_Sql(Connection_BD,Str_Sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"setSeguimiento(String IdPlan)"+e.getMessage());  
        }
        return Rs;
    }
    public String getDivision(String IdTaller){
        String Division = "";
        try {
            String Str_Sql = "select Nombre from JTaller where IDTaller="+IdTaller;
            ResultSet Rs =  JBase_Datos.SQL_QRY(Connection_BD,Str_Sql);
            if(Rs.next()){
                Division = Rs.getString("Nombre");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "getDivision() "+e.getMessage());
        }
        return Division;
    }
    
    public void getInformacion(){
        try {
            String Str_Sql = "select t1.IdPlan, t1.IdTaller, t1.Ano, t2.RecomendacionAuditor from JPlanAuditoria as t1, JInforme as T2 where t1.IdPlan = t2.IdPlan and "
                    + " Ano="+jCAño.getSelectedItem().toString();
            ResultSet Rs =  JBase_Datos.SQL_QRY(Connection_BD,Str_Sql);
            this.Detalle_Total.clear();
            
            while(Rs.next()){
                this.Detalle_Fila = new Vector();
                String IdPlan = Rs.getString("IdPlan");
                String Recomendaciones = Rs.getString("RecomendacionAuditor");
                String IdTaller = Rs.getString("IdTaller");
                String Division = this.getDivision(IdTaller);
                if(ExisteSeguimiento(IdPlan)){
                
                }else{
                    Vector Recomendacion = ExtraeRecomendacion(Recomendaciones);
                    for (int i = 0; i < Recomendacion.size(); i++) {
                         this.Detalle_Fila = new Vector();
                         this.Detalle_Fila.add(IdPlan);
                         this.Detalle_Fila.add(""+(i+1));
                         this.Detalle_Fila.add(Recomendacion.elementAt(i));
                         this.Detalle_Fila.add("");
                         this.Detalle_Fila.add("");
                         this.Detalle_Fila.add("");
                         this.Detalle_Fila.add("");
                         this.Detalle_Fila.add(Division);
                         this.Detalle_Total.add(this.Detalle_Fila);
                    }
                }
                
                
                
            }
            
            jPlanMejoramiento.setModel(new javax.swing.table.DefaultTableModel(Detalle_Total, Cabecera));
            this.setAnchoColumnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "getInformacion() "+e.getMessage());
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPlanMejoramiento = new javax.swing.JTable();
        jCAño = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seguimiento de Informes de Auditoria");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPlanMejoramiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Trabajo", "Codigo Hallazgo", "Descripcion Hallazgo", "Accion de mejora", "Proposito de la Accion", "% Avance", "Estado Actual", "Division Responsable"
            }
        ));
        jPlanMejoramiento.setRowHeight(13);
        jPlanMejoramiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPlanMejoramientoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jPlanMejoramiento);

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCAño, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );

        jTabbedPane1.addTab("Seguimiento", jPanel1);

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
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.getInformacion();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPlanMejoramientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPlanMejoramientoMouseClicked
        // TODO add your handling code here:
        jTextArea1.setText(""+jPlanMejoramiento.getValueAt(jPlanMejoramiento.getSelectedRow(), 1));
    }//GEN-LAST:event_jPlanMejoramientoMouseClicked

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
            java.util.logging.Logger.getLogger(JSeguimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JSeguimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JSeguimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JSeguimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JSeguimientos(null , null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jCAño;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable jPlanMejoramiento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}


 class MultiLneCellExample extends JFrame{
        MultiLneCellExample(){
            super("Multi-Line Cell Example");
            DefaultTableModel dm = new DefaultTableModel(){
                    
                    public Class getColumnClass(int columnIndex){
                        return String.class;
                    }
            };
        }
    };