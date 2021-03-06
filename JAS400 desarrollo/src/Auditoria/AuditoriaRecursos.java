package Auditoria;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import BD_Auditoria.JConeccion_AudiSys;
import java.sql.*;
import javax.swing.*;
/*
 * AuditoriaRecursos.java
 *
 * Created on 7/03/2012, 05:59:09 PM
 */
/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */
public class AuditoriaRecursos extends javax.swing.JFrame {

    private JConeccion_AudiSys JBase_Datos;
    private Connection Connection_BD;
    private Object [] Cabecera ;
    private Object [] [] Detalle  ;
    private String Calendario  [];
    private int FilaTabla;
    
    /** Creates new form AuditoriaRecursos */
    public AuditoriaRecursos( JConeccion_AudiSys JBase_Datos,Connection Connection_BD ) {
        Cabecera =  new Object [4];
        Detalle  =  new Object [100][4];
        Calendario = new String [13];
        this.JBase_Datos = JBase_Datos;
     	this.Connection_BD = Connection_BD; 
        initComponents();
        this.setAsignarTrabajo();
        
        Calendario[1]= "Enero";
        Calendario[2]= "Febrero";
        Calendario[3]= "Marzo";
        Calendario[4]= "Abril";
        Calendario[5]= "Mayo";
        Calendario[6]= "Junio";
        Calendario[7]= "Julio";
        Calendario[8]= "Agosto";
        Calendario[9]= "Septiembre";
        Calendario[10]= "Octubre";
        Calendario[11]= "Noviembre";
        Calendario[12]= "Diciembre";
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox2 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Auditoria de Recursos");
        setBounds(new java.awt.Rectangle(260, 130, 0, 0));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "IdRiesgo", "Nombre Riesgo", "Responsable", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Meses");

        jButton1.setText("Buscar...");
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
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Trabajos por Mes", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Responsable");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "IdRiesgo", "Nombre Riesgo", "Responsable", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jButton2.setText("Buscar...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addComponent(jLabel2))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Trabajo por Recursos", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
// TODO add your handling code here:
}//GEN-LAST:event_jTable1MouseClicked

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
    if(jComboBox2.getSelectedItem().toString().isEmpty()){
       JOptionPane.showMessageDialog(this,"Debe escoger un responsable");
    }
    this.getBusquedaXResponsable(jComboBox2.getSelectedItem().toString().trim(),0);
    
}//GEN-LAST:event_jButton2ActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
    if(jComboBox1.getSelectedItem().toString().isEmpty()){
       JOptionPane.showMessageDialog(this,"Debe escoger un mes");
    }
    this.getBusquedaXResponsable("",this.getCalendario());
}//GEN-LAST:event_jButton1ActionPerformed

public void getBusquedaXResponsable(String Responsable, int Calendario){
    String StrSql;
    if (Calendario ==0 ){
        StrSql = "SELECT IdRiesgo, NombreAuditoria, Responsable1, IdEstado"+
                        " FROM JPlanAuditoria"+
                        " WHERE Responsable1='"+Responsable+"'";
    }else{
        StrSql = "SELECT IdRiesgo, NombreAuditoria, Responsable1, IdEstado "+
                 "FROM JPlanAuditoria "+
                 "Where Mes = "+Calendario;
    }
    Cabecera [0] = "IdRiesgo";
    Cabecera [1] = "Nombre del Riesgo";
    Cabecera [2] = "Responsable";
    Cabecera [3] = "Estado";
    this.ClearTable();
    try{
            FilaTabla = -1;
            ResultSet xResult =  JBase_Datos.SQL_QRY(Connection_BD,StrSql);
            while(xResult.next()){
                FilaTabla++;
                Detalle [FilaTabla][0] = xResult.getString(1);
                Detalle [FilaTabla][1] = xResult.getString(2);
                Detalle [FilaTabla][2] = xResult.getString(3);
                Detalle [FilaTabla][3] = xResult.getString(4);
            }
    }catch(Exception e ){
               JOptionPane.showMessageDialog(this,"InformeAuditoria: "+e.getMessage());
    }
    if (Calendario == 0 ){
     jTable2.setModel(new javax.swing.table.DefaultTableModel(Detalle, Cabecera));
    }else{
     jTable1.setModel(new javax.swing.table.DefaultTableModel(Detalle, Cabecera));   
    }
}

private void setAsignarTrabajo(){
       try{
            String StrSql = "SELECT  t1.PrimerNombre, t1.PrimerApellido,t1.SegundoApellido FROM  JUser as t1 , JCargos t2 "
                        +"WHERE t1.IdCargo = t2.IdCargo AND t1.Estado ='Activo' AND Nombre <> 'Jefe Auditoria' ";
            jComboBox2.removeAllItems();
            jComboBox2.addItem("");
            ResultSet xResult =  JBase_Datos.SQL_QRY(Connection_BD,StrSql);
            while(xResult.next()){
                jComboBox2.addItem(xResult.getString(1)+" "+xResult.getString(2)+" "+xResult.getString(3) );
            }
       }catch(Exception e ){
           JOptionPane.showMessageDialog(this,"Form: PlanAuditoria (AsignarTrabajo)"+e.getMessage());
       }
}
public int getCalendario(){
    for(int i = 0; i <= 12; i++  ){
        if(jComboBox1.getSelectedItem().toString().trim().equals(Calendario[i])){
            return i;
        }
    }
    return 0;
}
private void ClearTable(){
            for(int i =0 ; i<= FilaTabla; i ++){
                Detalle [i][0] = "";
                Detalle [i][1] = "";
                Detalle [i][2] = "";
                Detalle [i][3] = "";
            }
            FilaTabla = -1;
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
            java.util.logging.Logger.getLogger(AuditoriaRecursos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AuditoriaRecursos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AuditoriaRecursos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AuditoriaRecursos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AuditoriaRecursos(null,null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
