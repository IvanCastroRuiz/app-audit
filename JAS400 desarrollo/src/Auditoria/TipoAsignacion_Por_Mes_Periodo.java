package Auditoria;


import BD_Auditoria.JConeccion_AudiSys;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class TipoAsignacion_Por_Mes_Periodo extends javax.swing.JFrame {

    /**
     * Creates new form TipoAsignacion_Por_Mes_Periodo
     */
    private JConeccion_AudiSys JBase_Datos;
    private Connection Connection_BD;
    private Vector Cabecera;
    private Vector Detalle;
    private Vector Detalle2;
    private String Responsable;
    private String Usuario;
    public TipoAsignacion_Por_Mes_Periodo(JConeccion_AudiSys JBase_Datos,Connection Connection_BD, String XUsuario) {
        this.Usuario = XUsuario;
        initComponents();
        this.JBase_Datos = JBase_Datos;
     	this.Connection_BD = Connection_BD;        
        Cabecera = new Vector();
        Detalle = new Vector();
        this.getAños();
    }

    public void setAnchoColumnas(){
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(5);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(900);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(5);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(5);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(50);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jCMes = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jCAño = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Asignacion de Trabajos");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "IdTaller", "Objetivo", "Responsable", "Mes", "Año", "Estado"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jCMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        jLabel1.setText("Mes");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Año");

        jButton2.setText("Asignar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Ver Informe");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jLabel1)
                                .addGap(133, 133, 133)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jCMes, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(jCAño, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jButton1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(!jCMes.getSelectedItem().toString().trim().equals("") && !jCAño.getSelectedItem().toString().trim().equals("")){
            this.getTalleres();
        }else{
            JOptionPane.showMessageDialog(this, "Debe seleccionar el mes y el año");
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         if (jTable1.getSelectedRow()!= -1){
             int IdTaller = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
             if(!jTable1.getValueAt(jTable1.getSelectedRow(),4).toString().equals("Asignada")){
                PlanAuditoria pl = new PlanAuditoria (IdTaller,""  , "");
             }else{
               PlanAuditoria pl = new PlanAuditoria (IdTaller,""  , "");
             }
         }else{
            JOptionPane.showMessageDialog(this,"Debe seleccionar una tarjeta para consultar su movimiento ");  
         }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
          if (jTable1.getSelectedRow()!= -1){
              String IdTaller = jTable1.getValueAt(jTable1.getSelectedRow(),0).toString();
              InformeAuditoria Papeles = new InformeAuditoria( this.JBase_Datos , this.Connection_BD, IdTaller,this.Usuario, 2);
              Papeles.setVisible(true);
          }
    }//GEN-LAST:event_jButton3ActionPerformed

    public void getTalleres(){
        try {
            Cabecera.clear();
            Cabecera.add("IdTaller");
            Cabecera.add("Objetivo");
            Cabecera.add("Responsable");
            Cabecera.add("Mes");
            Cabecera.add("Año");
            Cabecera.add("Estado");
            String Str_Sql = "select * from JTaller where Mes="+jCMes.getSelectedItem().toString().trim()+ " AND "
                    + "Ano ="+jCAño.getSelectedItem().toString().trim();
            ResultSet Rs =  JBase_Datos.SQL_QRY(Connection_BD,Str_Sql);
            Detalle.clear();
            while(Rs.next()){
                Detalle2 = new Vector();
                String XEstado = Rs.getString("IDTaller");
                Detalle2.add(""+XEstado);
                this.Responsable="";
                XEstado = getEstadoTaller(XEstado);
                Detalle2.add(Rs.getString("Proceso"));
                Detalle2.add(this.Responsable);
                Detalle2.add(Rs.getString("Mes"));
                Detalle2.add(Rs.getString("Ano"));
                Detalle2.add(""+XEstado);
                Detalle.add(Detalle2);
            }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Detalle, Cabecera));
            this.setAnchoColumnas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "tTalleres() "+e.getMessage());
        }
    }
    public String getEstadoTaller(String Id){
        String Estado = " ";
        try {
            String Str_Sql = "select IdTaller,IdEstado,Responsable1  from JPlanAuditoria where  IdTaller="+Id;
            ResultSet Rs =  JBase_Datos.SQL_QRY(Connection_BD,Str_Sql);
            while(Rs.next()){
                Estado = Rs.getString("IdEstado");
                Responsable= Rs.getString("Responsable1");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "getEstadoTaller() "+e.getMessage());
        }
        return Estado;
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
            java.util.logging.Logger.getLogger(TipoAsignacion_Por_Mes_Periodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TipoAsignacion_Por_Mes_Periodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TipoAsignacion_Por_Mes_Periodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TipoAsignacion_Por_Mes_Periodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TipoAsignacion_Por_Mes_Periodo(null,null,null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jCAño;
    private javax.swing.JComboBox jCMes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
