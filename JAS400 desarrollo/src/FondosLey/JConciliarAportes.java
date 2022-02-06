/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FondosLey;

import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 *
 * @author Garyn Carrillo
 */
public class JConciliarAportes extends javax.swing.JFrame {
     private JConection JBase_Datos;
     private Connection Cn;
     private Vector Cab, Det, Fila;
     private String NumeroFormato = "###,###,###,###.##";
     private DecimalFormat JFormato ;
     private SimpleDateFormat d;
     
    /**
     * Creates new form JConciliarAportes
     */
    public JConciliarAportes(JConection JBase_Datos3, Connection Cn2) {
        JFormato= new DecimalFormat(NumeroFormato);
        d = new SimpleDateFormat("yyyyMMdd");
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.Cab = new Vector();
        this.Cab.add("Codigo Banco");
        this.Cab.add("Nombre");
        this.Cab.add("Financiero");
        this.Cab.add("Subsidio");
        this.Fila= new Vector();
        initComponents();
        
        jXDatePicker1.setFormats("yyyyMMdd");
        jXDatePicker2.setFormats("yyyyMMdd");
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
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Aportes Recibidos en Banco");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo Banco", "Nombre", "Financiero"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Ejecutar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha Inicial");

        jLabel2.setText("Fecha Final");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel1)
                        .addGap(123, 123, 123)
                        .addComponent(jLabel2)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Conciliacion", jPanel1);

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
        String Fecha_inicial = d.format(jXDatePicker1.getDate()).toString();
        String Fecha_final = d.format(jXDatePicker2.getDate()).toString();
        this.getInformacion(Fecha_inicial.trim() , Fecha_final.trim());
    }//GEN-LAST:event_jButton1ActionPerformed
    public double getBanco(String CodigoFin, String FechaIni, String  FechaFin){
        double Vlr=0;
        try {
            String Str_Sql = " select tafban , sum(TAFVPG) as Valor "+                   
                             " from subsilib.TARCFIN "+                        
                             " where TAFBAN ="+CodigoFin+                            
                             " and TAFFPG >="+FechaIni+" and TAFFPG <="+FechaFin+ 
                             " group by tafban  ";
            //System.out.println(" "+Str_Sql);
           ResultSet rs = this.JBase_Datos.SQL_QRY(Cn, Str_Sql);
           if(rs.next()){
               Vlr = rs.getDouble("Valor");
           }
           rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"getBanco(String CodigoFin, String FechaIni, String  FechaFin) "+e.getMessage());
        }
        return Vlr;
    }
    public void getInformacion(String FechaIni , String FechaFin){
        boolean Rs=false;
        try {
            
            String Str_Sql ="delete from selinlib.JFAPORTDET ";
            Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);    
            Str_Sql = "insert into selinlib.JFAPORTDET "+                     
                             " select   trim(substring(fecapo, 1 , 6)) ,  "+          
                             " t2.ptocod , t2.ptosuc ,  codfin ,t1.nbanco  "+ 
                             " , sum(t2.vaport +t2.vinter) "+                        
                             " from selinlib.JFAPORTBAN t1, subsilib.salnoaf as t2 "+ 
                             " where t1.codigo = t2.ptocod and t1.sucurs =t2.ptosuc "+
                             " and fecapo>="+FechaIni+" and fecapo <="+FechaFin+         
                             " group by nbanco , t2.ptocod , t2.ptosuc , codfin "+    
                             " , trim(substring(fecapo, 1 , 6))            ";
            
            //System.out.println("SQl_1 "+Str_Sql);
            Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql); 
            
            Str_Sql =" insert into selinlib.JFAPORTDET "+                                   
                     " select  trim(substring(salfec, 1 , 6 )) ,t2.ptocod , t2.ptosuc  "+   
                     " ,codfin , trim(nbanco), sum(t2.salapo) "+                    
                     " from selinlib.JFAPORTBAN t1, subsilib.salapor as t2 "+               
                     " where t1.codigo = t2.ptocod and t1.sucurs =t2.ptosuc "+              
                     " and salfec>='"+FechaIni+"' and salfec <='"+FechaFin+"' "+                       
                     " group by nbanco, t2.ptocod, t2.ptosuc, trim(substring(salfec,1,6)) "+
                     " , codfin  ";
            //System.out.println("SQl_2 "+Str_Sql);
            Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql); 
            
            String Periodo = FechaIni.substring(0, 6);
            
            Str_Sql=" SELECT CODFIN  , NBANCO , SUM(VAPORTE) as aporte"+
                    " from selinlib.JFAPORTDET  "+
                    " WHERE period="+Periodo+            
                    " group by CODFIN  , NBANCO ";
            //System.out.println("" +Str_Sql);
            ResultSet QR = this.JBase_Datos.SQL_QRY(Cn, Str_Sql);
            this.Fila.clear();
            double sumFinanciero =0;
            double sumSubsidio=0;
            
            while(QR.next()){
                
                this.Det = new Vector();
                String CodigoFin = QR.getString("CODFIN");
                Det.add(CodigoFin);
                Det.add(QR.getString("NBANCO"));
                double Val = getBanco(CodigoFin,FechaIni,FechaFin );
                sumFinanciero = sumFinanciero + Val;
                Det.add(this.JFormato.format(Val));
                double Valor = QR.getDouble("aporte");
                sumSubsidio =  sumSubsidio + Valor;
                Det.add(this.JFormato.format(Valor));
                this.Fila.add(Det);
                
            }
            
            if(sumSubsidio!= 0){
                this.Det = new Vector();
                this.Det.add("Total ");
                this.Det.add("-->> ");
                this.Det.add(this.JFormato.format(sumFinanciero));
                this.Det.add(this.JFormato.format(sumSubsidio));
                this.Fila.add(Det);
                
            }
            QR.close();
            this.jTable1.setModel(new javax.swing.table.DefaultTableModel(this.Fila, this.Cab));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"getInformacion() "+e.getMessage());
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
            java.util.logging.Logger.getLogger(JConciliarAportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JConciliarAportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JConciliarAportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JConciliarAportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JConciliarAportes dialog = new JConciliarAportes(null,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
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
