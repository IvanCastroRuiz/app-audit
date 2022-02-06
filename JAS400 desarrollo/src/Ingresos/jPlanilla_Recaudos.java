/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ingresos;

import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;


import javax.swing.DefaultListModel;
import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.calendar.CalendarUtils;





/**
 *
 * @author GACA1186
 */
public class jPlanilla_Recaudos extends javax.swing.JFrame {
    private JConection JBase_Datos;
    private Connection Cn;
    private String Usuario_jas;
    private SimpleDateFormat d;
    private String jTextField1;
    /**
     * Creates new form jPlanilla_Recaudos
     */
    public jPlanilla_Recaudos(JConection JBase_Datos3, Connection Cn2, String Usuario) {
        this.Usuario_jas = Usuario;
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        initComponents();
        AutoCompleteDecorator.decorate(jComboBox1);
        setLista();
        d = new SimpleDateFormat("yyyyMMdd");
        jXDatePicker1.setFormats("yyyyMMdd");

        
        
        
    }
    
    public void setLista(){
        try {
            String SQL ="select USUARI, NOMCJE from poslib.POSCAJE ORDER BY 1";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,SQL);
            System.out.println(""+SQL);
            while(rs.next()){
                this.jComboBox1.addItem(rs.getString("USUARI"));
            }
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
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
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Ejecutar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha (AAAAMMDD)");

        jLabel2.setText("Cajeros (UUUU9999)");

        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(93, 93, 93)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap(179, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(231, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Planilla", jPanel1);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        try{
            jTextField1 = d.format(jXDatePicker1.getDate()).toString(); 
            getPlanilla(this.jComboBox1.getSelectedItem().toString().trim(), jTextField1.trim());
            JJasper_Sub  GenPdf2 = new JJasper_Sub();
            
            //Generacion de archivo EXCEL
            GenPdf2.setTipo(1);
            GenPdf2.setFechaInicial(jTextField1.trim());
            GenPdf2.setUsuario(this.jComboBox1.getSelectedItem().toString().trim());
            GenPdf2.setUsuario_JAS(this.Usuario_jas);
            GenPdf2.setConeccion(Cn);
            GenPdf2.start();
        }catch(Exception e ){
             JOptionPane.showMessageDialog(this, "Debe ingresar la fecha "+e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    public void getPlanilla(String Usuario, String Fecha){
        try {
            //Pago de Cuota Montaria
            boolean Guardar = JBase_Datos.Exc_Sql(this.Cn,"Delete from SELINLIB.JPLANILLA WHERE USUAR_JAS='"+this.Usuario_jas+"'");
            String SQL ="INSERT INTO SELINLIB.JPLANILLA (CODIGO,NOMTRA, CODCA2 , USUARI , NOMBRE , \n" +
                        "VLRUNI, VLRTOTAL, CANTIDAD, FECPAGO, ESTADO,USUAR_JAS)                       \n" +
                        "( SELECT 2 CODIGO, 'Cuota Monetaria' ,                       \n" +
                        "       CODCA2 , USUARI , NOMCJE, SUM(VLRUNI) UNI , SUM(VLRBRU) TOT,\n" +
                        "       count(*),T1.FECTR2 , ESTAD3 ,'"+this.Usuario_jas+"'                                  \n" +
                        "       FROM poslib.posv02 as T1  left join poslib.posv03  T2       \n" +
                        "ON  T1.NROTR2 = T2.NROTR3  AND T1.CODCA2 = T2.CODCA3  AND          \n" +
                        "    T1.CODCJ2 = T2.CODCJ3  AND T1.FECTR2 = T2.FECTR3  AND          \n" +
                        "    T1.CONSE2 =   T2.CONSE3 LEFT JOIN poslib.POSCAJE T3            \n" +
                        "ON  T1.CODCJ2 = T3.CODCJE                                          \n" +
                        "WHERE T1.CODART= 5020 AND    ESTAD3 ='P' AND                       \n" +
                        "      T1.CODCJ2 = T3.CODCJE   \n" +
                        "      AND T1.FECTR2 = "+Fecha+" AND USUARI = '" +Usuario+"'"+
                        " GROUP BY CODCA2 ,USUARI, NOMCJE, T1.FECTR2  , ESTAD3     )  ";
            System.out.println(""+SQL);
            Guardar = JBase_Datos.Exc_Sql(this.Cn,SQL);
            //Embargo
            SQL ="Insert into selinlib.jplanilla (Codigo, Nomtra, Fecpago, Usuari ,\n" +
                 "      Estado, Cantidad, VlrTotal, USUAR_JAS)                                \n" +
                 "SELECT 0 Codigo, 'Sub. Embargante'                               \n" +
                 "        ,EMBFEN , EMBUSU, EMBEST , COUNT(*) , SUM(EMBVAL)  ,'"+this.Usuario_jas+"'       \n" +
                 "        FROM SUBSILIB.MCHEMBA AS T1                              \n" +
                 "WHERE EMBFEN = "+Fecha+" AND EMBUSU = '" +Usuario+"'"+
                 "AND EMBEST = 'P'                                                 \n" +
                 "GROUP BY EMBFEN , EMBUSU, EMBEST                                 ";
            System.out.println("EMBARGOS ************************"+SQL);
            Guardar = JBase_Datos.Exc_Sql(this.Cn,SQL);
            //Reembolso
            SQL = "insert into selinlib.jplanilla  (Codigo , Nomtra, Usuari , Cantidad\n" +
                  "    , VlrTotal, USUAR_JAS)                                                    \n" +
                  "SELECT 2 , 'Reembolso' , USUCAJ ,      COUNT(*) , SUM(VALPAG)  ,'"+this.Usuario_jas+"'    \n" +
                  "   FROM POSLIB.MPAGVEN                                       \n" +
                  "WHERE FECCAJ = "+Fecha+" AND USUCAJ ='"+Usuario+"'"+ " AND ESTPAG='P' AND TIPPAG = 1" +
                  "GROUP BY USUCAJ                                                      ";
            System.out.println(""+SQL);
            Guardar = JBase_Datos.Exc_Sql(this.Cn,SQL);
            
            //Micro-Credito
            SQL = "insert into selinlib.jplanilla  (Codigo , Nomtra, Usuari , Cantidad\n" +
                  "    , VlrTotal, USUAR_JAS)                                                    \n" +
                  "SELECT 3 , 'MicroCredito' , USUCAJ ,      COUNT(*) , SUM(VALPAG)     ,'"+this.Usuario_jas+"' \n" +
                  "   FROM POSLIB.MPAGVEN                                       \n" +
                  "WHERE FECCAJ = "+Fecha+" AND USUCAJ ='"+Usuario+"'"+ " AND ESTPAG='P' AND TIPPAG = 2" +
                  "GROUP BY USUCAJ                                                      ";
            System.out.println(""+SQL);
            Guardar = JBase_Datos.Exc_Sql(this.Cn,SQL);
            
            
            
            SQL ="insert into selinlib.jplanilla                                     \n" +
                 "(Codigo , nomtra, usuari,fecpago, cantidad , vlrtotal, USUAR_JAS)             \n" +
                 "SELECT 4 , 'Pag. Fosfec', LIBUSU , LIBFEN , COUNT(*) , SUM(LIBVAL) ,'"+this.Usuario_jas+"'\n" +
                 "         FROM SUBSILIB.MLIBDES                                     \n" +
                 "WHERE LIBFEN = " +Fecha+
                 " AND LIBUSU ='"+Usuario+"'"+
                 "GROUP BY LIBUSU , LIBFEN                                           ";
            Guardar = JBase_Datos.Exc_Sql(this.Cn,SQL);
            
            SQL = "INSERT INTO SELINLIB.JPLANILLA (CODIGO , NOMTRA , USUARI ,    \n" +
                  " FECPAGO ,ESTADO , CANTIDAD , VLRTOTAL, USUAR_JAS)                       \n" +
                  "SELECT 5 , 'Sub. Transporte' ,                                \n" +
                  "   USUNOV , FECNOV , ESTREG, COUNT(*) , SUM(VALOCU)  ,'"+this.Usuario_jas+"'     \n" +
                  "   FROM SUBSILIB.MSUBTRL1    \n" +
                  "WHERE ESTREG = 'P'      \n" +
                  "AND USUNOV = '"+Usuario+"' AND FECNOV ="+Fecha+" AND ESTREG ='P' \n" +
                  "GROUP BY USUNOV , FECNOV , ESTREG   ";
            Guardar = JBase_Datos.Exc_Sql(this.Cn,SQL);
            

            
            //javax.swing.JOptionPane.showMessageDialog(this, "Se Ejecuto correctamente "+Guardar);
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
            java.util.logging.Logger.getLogger(jPlanilla_Recaudos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jPlanilla_Recaudos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jPlanilla_Recaudos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jPlanilla_Recaudos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jPlanilla_Recaudos(null, null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    // End of variables declaration//GEN-END:variables
}
