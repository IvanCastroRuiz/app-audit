/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ingresos;

import BD_As400.JConection;
import FondosLey.JJasper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Garyn Carrillo
 */
public class JServiciosSociales extends javax.swing.JFrame {
    private Vector Cabecera ;
    private Vector Detalle  ;
    private Vector DetalleTotal  ;
    private JConection JBase_Datos;
    private Connection Cn;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private int Fila;
    private double TotalConsolidadoCanje, TotalEfectivo, TotalCanje, TotalConsolidadoEfectivo;
    private String jTFechaInicial , jTFechaFinal;
    private SimpleDateFormat d;
    /**
     * Creates new form JServiciosSociales
     */
    public JServiciosSociales(JConection JBase_Datos3, Connection Cn2) {
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        JFormato= new DecimalFormat(NumeroFormato);
        TotalConsolidadoCanje = 0;
        TotalConsolidadoCanje= 0;
        TotalEfectivo =0;
        TotalCanje=0;
        TotalConsolidadoEfectivo = 0;
        Cabecera =  new Vector();
        Detalle  = new Vector();
        DetalleTotal = new Vector();
        initComponents();
        jXDatePicker1.setFormats("yyyyMMdd"); 
        jXDatePicker2.setFormats("yyyyMMdd");
        d = new SimpleDateFormat("yyyyMMdd");
    }
     public void getConsolidado(String FechaIncial ,String FechaFinal){
        try{
            this.Cabecera.clear();
            this.Cabecera.add("Codigo");
            this.Cabecera.add("Punto de Venta");
            this.Cabecera.add("Efectivo");
  
            String StrSql = "SELECT  T01.CENCOD,T03.CENNOM, SUM(T02.COMSUF)  "
                    + " FROM RECAUDOS.PAPORTES AS T01, RECAUDOS.PCOMFACH AS T02, RECAUDOS.CENTRO AS T03"
                    + " WHERE T01.USUCOD = T02.USUCOD AND"
                    + " T01.TURCOD = T02.TURCOD AND"
                    + " T01.PLAFER = T02.PLAFER AND"
                    + " T01.PLAHOR = T02.PLAHOR AND"
                    + " T01.CAJCOD = T02.CAJCOD AND "
                    + " T01.CENCOD = T03.CENCOD AND PLAFEI>="+FechaIncial+" AND PLAFEI<="+FechaFinal+
                    " GROUP  BY T01.CENCOD , T03.CENNOM ";
            System.out.println(" "+StrSql);
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            double sumVlr = 0;
            this.DetalleTotal.clear();
            while(rs.next()){
               Fila ++;
               String CodCentro     = rs.getString(1);
               String NombreCentro  = rs.getString(2);
               double Vlr           = rs.getDouble(3);
               sumVlr = sumVlr + Vlr;
               Detalle = new Vector();
               this.Detalle.add(CodCentro);
               this.Detalle.add( NombreCentro);
               this.Detalle.add(JFormato.format(Double.parseDouble(""+Vlr)));
               this.DetalleTotal.add(Detalle);
            }
            if(DetalleTotal.size()!=0){
                Detalle = new Vector();
                this.Detalle.add("Total Recaudo ");
                this.Detalle.add("->>>>");
                this.Detalle.add(JFormato.format(Double.parseDouble(""+sumVlr)));
                this.DetalleTotal.add(Detalle);
            }
            
            //***************************************************************************************
            // Base de los Centro de Atencion
           StrSql = "SELECT  T01.CENCOD,T03.CENNOM, SUM(T01.CBACOM)  "
                    + " FROM RECAUDOS.CBACOM AS T01, RECAUDOS.CENTRO AS T03 "
                    + " WHERE "
                    + " T01.CENCOD = T03.CENCOD AND COMFEC>="+FechaIncial+" AND COMFEC<="+FechaFinal+
                    " GROUP  BY T01.CENCOD , T03.CENNOM ";
            
            rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            double sumVlrBase =0;
            while(rs.next()){
               Fila ++;
               String CodCentro     = rs.getString(1);
               String NombreCentro  = rs.getString(2);
               double Vlr           = rs.getDouble(3);
               sumVlrBase = sumVlrBase + Vlr;
               Detalle = new Vector();
               this.Detalle.add(CodCentro);
               this.Detalle.add(NombreCentro);
               this.Detalle.add(JFormato.format(Double.parseDouble(""+Vlr)));
               this.DetalleTotal.add(Detalle);
            }
            if(this.Detalle.size()!=0){
                Detalle = new Vector();
                this.Detalle.add("Total Base ");
                this.Detalle.add("->>>>");
                this.Detalle.add(JFormato.format(Double.parseDouble(""+sumVlrBase)));
                this.DetalleTotal.add(Detalle);
                
                Detalle = new Vector();
                this.Detalle.add("Gran Total  ");
                this.Detalle.add("->>>>");
                this.Detalle.add(JFormato.format(Double.parseDouble(""+(sumVlrBase+sumVlr))));
                this.DetalleTotal.add(Detalle);
            }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(DetalleTotal, Cabecera));
        }catch(Exception e ){
            JOptionPane.showMessageDialog(this,"Form:JIngresos: getConsolidado()"+ e.getMessage());
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
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Listado de verificacion transferencia a Cuenta de Administracion");
        setResizable(false);

        jTable1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "Punto de Venta", "Efectivo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Fecha Inicio (aaaammdd)");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Fecha Fin (aaaammdd)");

        jButton2.setText("Imprimir");
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addGap(41, 41, 41)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Control de Transferencias", jPanel1);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            jTFechaInicial = d.format(jXDatePicker1.getDate()).toString();
            jTFechaFinal = d.format(jXDatePicker2.getDate()).toString();
            this.getConsolidado(this.jTFechaInicial.trim(), this.jTFechaFinal.trim()); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una fecha no mayor a un Mes "+e.getMessage());
        }
        
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if( !(jTFechaInicial.trim().equals(""))  &&!(jTFechaFinal.trim().equals(""))){
            JJasper  GenPdf = new JJasper();
            GenPdf.setTipo(4);
            GenPdf.setFechaInicial(jTFechaInicial.trim());
            GenPdf.setFechaFinal(jTFechaFinal.trim());
            GenPdf.setConeccion(Cn);
            GenPdf.start();
      }else{
          JOptionPane.showMessageDialog(this,"Debe diligenciar la fecha inicial y fecha Final en la Primera pestaña  "); 
      }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(JServiciosSociales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JServiciosSociales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JServiciosSociales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JServiciosSociales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JServiciosSociales(null,null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    // End of variables declaration//GEN-END:variables
}
