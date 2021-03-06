package Subsidio;


import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import BD_As400.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class JTarjetas extends javax.swing.JFrame {

    /**
     * Creates new form JTarjetas
     */
    private JConection JBase_Datos;
    private Connection Cn; 
    private Vector Cabecera;
    private Vector Detalle;
    private Vector Detalle2;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    
    public JTarjetas(JConection JBase_Datos3, Connection Cn2) {
        JFormato= new DecimalFormat(NumeroFormato);
        Cabecera = new Vector();
        Detalle = new Vector();
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTCedula = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTarjetas = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTTarjetaDetalle = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTHTarjetas = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tarjetas Multiservicios");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTarjetas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Numero de Tarjeta", "Fec.  Estado", "Estado", "Descp. Estado", "Ubicacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTarjetas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTarjetasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTarjetas);

        jTTarjetaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Lugar", "Valor", "Tipo de Movimiento", "Movimiento", "Fecha"
            }
        ));
        jScrollPane3.setViewportView(jTTarjetaDetalle);

        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cedula de Ciudadania");

        jTHTarjetas.setAutoCreateRowSorter(true);
        jTHTarjetas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Num. Novedad", "Numero de Tarjeta", "Usuario", "Fecha", "Tip. Novedad", "Descr. Novedad"
            }
        ));
        jScrollPane4.setViewportView(jTHTarjetas);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jTCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(40, 40, 40)
                                    .addComponent(jButton1))
                                .addComponent(jButton3)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
                                .addComponent(jScrollPane2)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addComponent(jLabel1))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Movimiento de Tarjeta", jPanel2);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Tarjeta", "Estado", "Ubicacion", "Documento", "Trabajador", "Tipo de Movimiento", "Movimiento", "Lugar del Movimiento", "Fecha del Movimiento", "Valor Movimiento"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Buscar");
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1034, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("En custodio con movimientos", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
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
        if(!jTCedula.getText().trim().equals("")){
                getTarjetaCedula(jTCedula.getText().trim());
        }else{
           JOptionPane.showMessageDialog(this,"Class:getTarjetaCedula() "); 
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         this.get_Informacion();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTarjetasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTarjetasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTarjetasMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         if (jTarjetas.getSelectedRow()!= -1){
             get_InformacionxTarjeta(""+jTarjetas.getValueAt(jTarjetas.getSelectedRow(),0));
             getHistorico(""+jTarjetas.getValueAt(jTarjetas.getSelectedRow(),0));
         }else{
            JOptionPane.showMessageDialog(this,"Debe seleccionar una tarjeta para consultar su movimiento ");  
         }
    }//GEN-LAST:event_jButton3ActionPerformed

  
    public void setCedula(String Documento){
        try {
            jTCedula.setText(Documento);
            getTarjetaCedula(jTCedula.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:setCedula(Documento) "+e.getMessage());
        }
                
    }
    
    public void getTarjetaCedula(String Documento){
        try {
             Vector Cab = new Vector();
             Vector Det = new Vector();
             Vector Det2;
             Cab.add("Numero Tarjeta");
             Cab.add("Fec. Estado");
             Cab.add("Estado");
             Cab.add("Descp. Estado");
             Cab.add("Ubicacion");
             String StrSql = "select TARNTA, TAREST, TARDES,TARFES, TARUBI from subsilib.mtarjet where tardoc ="+Documento.trim();
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             Det.clear();
             while (rs.next()){
                 Det2 = new Vector();
                 Det2.add(rs.getString("TARNTA"));
                 Det2.add(rs.getString("TARFES"));
                 Det2.add(rs.getString("TAREST"));
                 Det2.add(rs.getString("TARDES"));
                 Det2.add(rs.getString("TARUBI"));
                 Det.add(Det2);
             }
             jTarjetas.setModel(new javax.swing.table.DefaultTableModel(Det, Cab));
             jTarjetas.setAutoCreateRowSorter(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:getTarjetaCedula() "+e.getMessage());
        }
    }
    public void getHistorico(String NroTarjeta){
        try {
            String Str_Sql = "select TAHCSC,TAHNTA,TAHUSU,TAHFEC,TAHNOV,TAHDES from subsilib.HTARJET where TAHNTA ='"+NroTarjeta+"' "
                    + " order by TAHFEC desc ";
            Vector Cab = new Vector();
            Vector Det = new Vector();
            Cab.add("Num. Novedad");
            Cab.add("Numero de Tarjeta");
            Cab.add("Usuario");
            Cab.add("Fecha");
            Cab.add("Tip. Novedad");
            Cab.add("Descr. Novedad");
            Vector Det2;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Det.clear();
            while (rs.next()){
                Det2 = new Vector();
                Det2.add(rs.getString("TAHCSC"));
                Det2.add(rs.getString("TAHNTA"));
                Det2.add(rs.getString("TAHUSU"));
                Det2.add(rs.getString("TAHFEC"));
                Det2.add(rs.getString("TAHNOV"));
                Det2.add(rs.getString("TAHDES"));
                Det.add(Det2);
            }
            jTHTarjetas.setModel(new javax.swing.table.DefaultTableModel(Det, Cab));
            jTHTarjetas.setAutoCreateRowSorter(true);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this,"Class:getHistorico() "+e.getMessage());
        }
    }
    
    
    
    
    
     public void get_InformacionxTarjeta(String Tarjeta){
         
        Vector Cab = new Vector();
        Vector Det = new Vector();
        Vector Det2;
        Cab.add("Lugar");
        Cab.add("Valor");
        Cab.add("Tipo de Movimiento");
        Cab.add("Movimiento");
        Cab.add("Fecha");
        try {
             String StrSql = "select  MOVTIP, MOVNEG, MOVFEC, MOVVAL, MOVCED "
                            +" from subsilib.MOVREDEB where"
                            +" movtar ='"+Tarjeta.trim().substring(0, 15) +"' "
                            +" order by movfec desc";
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             Det.clear();
             while (rs.next()){
                 Det2 = new Vector();
                 Det2.add(rs.getString("MOVNEG"));
                 Det2.add(rs.getString("MOVVAL"));
                 String Tipo = rs.getString("MOVTIP");
                 Det2.add(Tipo);
                 Det2.add(getMovimiento(Tipo).trim());
                 Det2.add(rs.getString("MOVFEC"));
                 Det.add(Det2);
             }
             jTTarjetaDetalle.setModel(new javax.swing.table.DefaultTableModel(Det, Cab));
             jTTarjetaDetalle.setAutoCreateRowSorter(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:get_Informacion() "+e.getMessage());
        }
    }   
    
    public void get_Informacion(){
        Cabecera.add("No Tarjeta");
        Cabecera.add("Estado");
        Cabecera.add("Ubicacion");
        Cabecera.add("Documento");
        Cabecera.add("Trabajador");
        Cabecera.add("Tipo de Movimiento");
        Cabecera.add("Movimiento");
        Cabecera.add("Lugar del Movimiento");
        Cabecera.add("Fecha del Movimiento");
        Cabecera.add("Valor");
        try {
             String StrSql = "select TARUBI, TARNTA, TARDOC,TARNOM,TAREST, "
                            +" MOVTIP, MOVNEG, MOVFEC, MOVVAL "
                            +" from subsilib.mtarjet as t1, subsilib.MOVREDL1 as t2 where"
                            +" SUBSTR (TARNTA,1,15) = t2.movtar AND TAREST ='C' "
                            +" AND MOVFEC>=TARFES "
                            +" ORDER BY TARDOC , TARNTA ";
             System.out.println(" "+StrSql);
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             Detalle.clear();
             while (rs.next()){
                 Detalle2 = new Vector();
                 Detalle2.add(rs.getString("TARNTA"));
                 Detalle2.add(rs.getString("TAREST"));
                 Detalle2.add(rs.getString("TARUBI"));
                 Detalle2.add(JFormato.format(rs.getDouble("TARDOC")));
                 Detalle2.add(rs.getString("TARNOM"));
                 String Tipo = rs.getString("MOVTIP");
                 Detalle2.add(Tipo);
                 Detalle2.add(getMovimiento(Tipo));
                 Detalle2.add(rs.getString("MOVNEG"));
                 Detalle2.add(rs.getString("MOVFEC"));
                 Detalle2.add(JFormato.format(rs.getDouble("MOVVAL")));
                 Detalle.add(Detalle2);
             }
             jTable1.setModel(new javax.swing.table.DefaultTableModel(Detalle, Cabecera));
             jTable1.setAutoCreateRowSorter(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:get_Informacion() "+e.getMessage());
        }
    }
    
    public String getMovimiento(String Tipo){
        String Nombre="";
        
        try {
            String StrSql="select * from selinlib.JTIPOMOV where codigo="+Tipo;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            if(rs.next()){
              Nombre= rs.getString("DESCRIP"); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:getMovimiento() "+e.getMessage());
        }
        return Nombre;
    }
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
            java.util.logging.Logger.getLogger(JTarjetas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JTarjetas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JTarjetas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JTarjetas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JTarjetas(null,null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTCedula;
    private javax.swing.JTable jTHTarjetas;
    private javax.swing.JTable jTTarjetaDetalle;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTarjetas;
    // End of variables declaration//GEN-END:variables
}
