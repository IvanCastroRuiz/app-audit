/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fondo_Ley_NIFF_2020;

import Fondo_ley_NIIF.*;
import FondosLey.*;
import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author GACA1186
 */
public class JAportes_wapoment extends javax.swing.JFrame {

    /**
     * Creates new form JAportes_wapoment
     */
    private JConection JBase_Datos;
    private Connection Cn;
    private Vector Cab, Det , Fila;
    private Vector Cab2, Det2 , Fila2;
    private Vector Cab3, Det3 , Fila3;
    private SimpleDateFormat d;
    private Vector Cab5, Det5, Fila5;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private String jTextField1;
    private String jTextField2;
    public JAportes_wapoment(JConection JBase_Datos3, Connection Cn2) {
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        Cab = new Vector();
        Det= new Vector();
        d = new SimpleDateFormat("yyyyMMdd");
        Cab5 = new Vector();
        Fila5 = new Vector();
        
        Cab.add("Tipo");
        Cab.add("Nro Planilla");
        Cab.add("Codigo");
        Cab.add("Cedula");
        Cab.add("Periodo");
        Cab.add("Valor Planilla");
        
        Cab2 = new Vector();
        Det2= new Vector();
        
        Cab2.add("Recibo");
        Cab2.add("Nit");
        Cab2.add("Periodo");
        Cab2.add("Fecha de Pago");
        Cab2.add("Fecha de Actualizacion");
        Cab2.add("Nomina");
        Cab2.add("Aporte");
        Cab2.add("Caja");
        Cab2.add("Interes de Mora");
        Cab2.add("Tipo Afilicacion");
        Cab2.add("Categoria");
        Cab2.add("Tipo Planilla");
        Cab2.add("Valor Planilla");
        Cab2.add("Nro Planilla");
        Cab2.add("Codigo Planilla");
        Cab2.add("Periodo Planilla");
        
          
        Cab3 = new Vector();
        Det3= new Vector();
        
        Cab3.add("Tipo Planilla    ");
        Cab3.add("Total Caja       ");
        
        
        Cab5.add("Emptaf");
        Cab5.add("Emppri");
        Cab5.add("Tipo Planilla ");
        Cab5.add("Valor");
        
        JFormato= new DecimalFormat(NumeroFormato);
        initComponents();
        
        jXDatePicker1.setFormats("yyyyMMdd");
        jXDatePicker2.setFormats("yyyyMMdd");
    }
    
    
    public void get_Analisis_Tipo_M(){
        try {
            String Str_Sql =" select  emptaf , emppri , tipopl  , sum(salcaj)   as valor       \n" +
                            " from " +"selinlib."+jTextField3.getText().trim()+ 
                            "    as t1 LEFT JOIN                                      \n" +
                            " selinlib.jplanillas   as t2                              \n" +
                            " ON  SALCOD = CODIGO  and                                 \n" +
                            "    SALPER = PERAPO                                      \n" +
                            "and nropla = char(salrec)                                \n" +
                            "where tipopl ='M'                                        \n" +
                            "group by emptaf , emppri , tipopl                        ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double Suma = 0;
            Fila5.clear();
            while(rs.next()){
                
                Det5 = new Vector();
                Det5.add(rs.getString("emptaf"));
                Det5.add(rs.getString("emppri"));
                Det5.add(rs.getString("tipopl"));
                Det5.add(this.JFormato.format(rs.getDouble("valor")));
                Suma = Suma + rs.getDouble("valor");
                Fila5.add(Det5);
            }
            if(Suma!= 0){
                Det5 = new Vector();
                Det5.add("Tolta ");
                Det5.add(" ---->>> ");
                Det5.add("");
                Det5.add(this.JFormato.format(Suma));
                Fila5.add(Det5);
            }
                
            
            jTable5.setModel(new javax.swing.table.DefaultTableModel(Fila5, Cab5));
            jTable5.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," get_Analisis_Tipo_M() "+e.getMessage());  
        }
    }
            
    
    

    public void getPlanilla(){
        try {
            
            String Str_Sql ="select distinct tipopl,nropla,codigo , cedpla,perapo , t1.valora   \n" +
                            "from subsilib.cerpla as t1 , subsilib.cerplad t2                   \n" +
                            "where nropla = planil  and                                         \n" +
                            "      perdet =  perapo                                             \n" +
                            "and feccer >= "+jTextField1.trim()+"  and feccer <= "+jTextField2.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Det.clear();
            double Total = 0;
            while(rs.next()){
                 Fila = new Vector();
                 Fila.add(rs.getString("tipopl"));
                 Fila.add(rs.getString("nropla"));
                 Fila.add(rs.getString("codigo"));
                 Fila.add(rs.getString("cedpla"));
                 Fila.add(rs.getString("perapo"));
                 Total = Total + rs.getDouble("valora");
                 Fila.add(this.JFormato.format(rs.getDouble("valora")));
                 Det.add(Fila);
            }
            if(Total!=0){
                 Fila = new Vector();
                 Fila.add("Total ->>> ");
                 Fila.add("");
                 Fila.add("");
                 Fila.add("");
                 Fila.add("");
                 Fila.add(this.JFormato.format(Total));
                 Det.add(Fila);
            }
            rs.close();
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Det, Cab));
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            
            
            Str_Sql ="delete from selinlib.jplanillas ";
            boolean rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            Str_Sql ="insert into selinlib.jplanillas select distinct tipopl,nropla,codigo,perapo , t1.valora   \n" +
                            "from subsilib.cerpla as t1 , subsilib.cerplad t2                   \n" +
                            "where nropla = planil  and                                         \n" +
                            "      perdet =  perapo                                             \n" +
                            "and feccer >= "+jTextField1.trim()+"  and feccer <= "+jTextField2.trim();
            
            rx= JBase_Datos.Exc_Sql(this.Cn,Str_Sql);       
            
            Str_Sql ="select char(salrec) as salr , nropla ,               \n" +
                      "      t1.*, t2.*                             \n" +
                      "        from selinlib."+jTextField3.getText().trim()+" as t1 LEFT JOIN \n" +
                      "selinlib.jplanillas   as t2                     \n" +
                      "ON     SALCOD = CODIGO  and                  \n" +
                      "       SALPER = PERAPO                       \n" +
                      "and nropla = char(salrec)                    ";
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Det2.clear();
            double TotalNom = 0,TotalApor = 0, TotalCaj = 0, TotalInt = 0, Total2 = 0;
            while(rs.next()){
                 Fila2 = new Vector();
                 Fila2.add(rs.getString("salr"));
                 Fila2.add(rs.getString("salnit"));
                 Fila2.add(rs.getString("salper"));
                 Fila2.add(rs.getString("salfec"));
                 Fila2.add(rs.getString("salfe1"));
                 Fila2.add(this.JFormato.format(rs.getDouble("salnom")));
                 Fila2.add(this.JFormato.format(rs.getDouble("salapo")));
                 Fila2.add(this.JFormato.format(rs.getDouble("salcaj")));
                 Fila2.add(this.JFormato.format(rs.getDouble("salvin")));
                 TotalNom = TotalNom+rs.getDouble("salnom");
                 TotalApor=TotalApor+rs.getDouble("salapo");
                 TotalCaj = TotalCaj +rs.getDouble("salcaj");
                 TotalInt = TotalInt+ rs.getDouble("salvin");
                 String Empresa="";
                 if (rs.getInt("emppri")==1){
                     Empresa="PRINCIPAL";
                 }
                 if (rs.getInt("emppri")==2){
                     Empresa="SUCURSAL";
                 }
                 if (rs.getInt("emppri")==5){
                     Empresa="INDEPENDIENTE";
                 }
                 if (rs.getInt("emppri")==6){
                     Empresa="VOLUNTARIO";
                 }
                 
                 String Categoria="";
                 if (rs.getInt("emptaf")==0){
                     Categoria="EMPLEADOR";
                 }
                 if (rs.getInt("emptaf")==1){
                     Categoria="INDEPENDIENTE";
                 }
                 if (rs.getInt("emptaf")==2){
                     Categoria="COOPERATIVA-CTA";
                 }
                 if (rs.getInt("emptaf")==3){
                     Categoria="MIPYME";
                 }
                 if (rs.getInt("emptaf")==4){
                     Categoria="VOLUNTARIO";
                 }
                 Fila2.add(Empresa);
                 Fila2.add(Categoria);
                 Fila2.add(rs.getString("tipopl"));
                 Fila2.add(this.JFormato.format(rs.getDouble("valora")));
                 Fila2.add(rs.getString("nropla"));
                 Fila2.add(rs.getString("codigo"));
                 Fila2.add(rs.getInt("perapo"));
                 Total2 = Total2+rs.getDouble("valora");
                 Det2.add(Fila2);
            }
            if(Total2!=0){
                 Fila2 = new Vector();
                 Fila2.add("TOTAL ----->");
                 Fila2.add(" ");
                 Fila2.add(" ");
                 Fila2.add(" ");
                 Fila2.add(" ");
                 Fila2.add(this.JFormato.format(TotalNom));
                 Fila2.add(this.JFormato.format(TotalApor));
                 Fila2.add(this.JFormato.format(TotalCaj));
                 Fila2.add(this.JFormato.format(TotalInt));
                 Fila2.add("");
                 Fila2.add("");
                 Fila2.add("");
                 Fila2.add(this.JFormato.format(Total2));
                 Fila2.add("");
                 Fila2.add("");
                 Fila2.add("");
                 Det2.add(Fila2);
            }
            rs.close();
            jTable3.setModel(new javax.swing.table.DefaultTableModel(Det2, Cab2));
            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            
          
            Str_Sql ="delete from selinlib.JWAPOMENT";
            rx= JBase_Datos.Exc_Sql(this.Cn,Str_Sql);           
            Str_Sql ="insert into selinlib.jwapoment                            \n" +
                     "select char(salrec) , nropla , salcaj , emptaf , tipopl   \n" +
                     "        from selinlib."+jTextField3.getText().trim()+" as t1 LEFT JOIN              \n" +
                    "selinlib.jplanillas   as t2                               \n" +
                    "ON     SALCOD = CODIGO  and                               \n" +
                    "    SALPER = PERAPO                                    \n" +
                    "and nropla = char(salrec)                                 \n" ;

            System.out.println(""+Str_Sql);
            rx= JBase_Datos.Exc_Sql(this.Cn,Str_Sql); 
            
            Str_Sql="update                         \n" +
                    "selinlib.jwapoment as t1  \n" +
                    "set tipopl='E'                 \n" +
                    "where emptaf in(0, 2,3)  and tipopl is null  ";
            rx= JBase_Datos.Exc_Sql(this.Cn,Str_Sql); 
            //Emptaf = 0 Empleador
            //Emptaf = 2 Cooperativa-Cta
            //Emptaf = 3 MiPymes
            
            
            Str_Sql="update                         \n" +
                    "selinlib.jwapoment as t1  \n" +
                    "set tipopl='I'                 \n" +
                    "where emptaf in( 1 , 4)   and tipopl is null   ";
            rx= JBase_Datos.Exc_Sql(this.Cn,Str_Sql);  
            //Emptaf = 1 Independiente
            //Emptaf = 4 Voluntarios
            
            Str_Sql="select tipopl , sum(salcaj) as salcaj \n" +
                    "from selinlib.jwapoment as t1        \n" +
                    "group by tipopl                      ";
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Det3.clear();
            double Total4 = 0;
            while(rs.next()){
                Fila3 = new Vector();
                Fila3.add(rs.getString("Tipopl"));
                Fila3.add(this.JFormato.format(rs.getDouble("salcaj")));
                Total4 = Total4 +rs.getDouble("salcaj");
                Det3.add(Fila3);
            }
            if(Total4!= 0){
                Fila3 = new Vector();
                Fila3.add("TOTAL --> ");
                Fila3.add(this.JFormato.format(Total4));
                Det3.add(Fila3);
            }
            jTable4.setModel(new javax.swing.table.DefaultTableModel(Det3, Cab3));
            jTable4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            rs.close();
        } catch (Exception e) {
               JOptionPane.showMessageDialog(this," getPlanilla() "+e.getMessage());  
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Ejecutar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo", "Nro Planilla", "Codigo", "cedula", "Periodo", "Valor"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Fecha Inicial (AAAAMMDD)");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Fecha Finall (AAAAMMDD)");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Nombre Archivo WAPOMENTE  (SELINLIB)");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Recibo", "Codigo", "Nit", "Periodo", "Fecha Pago", "Fecha Actualizacion", "Nomina", "Aporte", "Caja", "Interes de Mora", "Tipo Afiliacion", "Categoria", "Tipo Planilla", "Valor Planilla", "Nro Planilla", "Codigo", "Periodo"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de Planilla", "Total Caja       "
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Emptaf", "Emppri", "Tipo Planilla", "Valor"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        jXDatePicker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker2ActionPerformed(evt);
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
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(643, 643, 643))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Control", jPanel1);

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
        this.jTextField1 = d.format(jXDatePicker1.getDate()).toString();
        this.jTextField2 = d.format(jXDatePicker2.getDate()).toString();
        this.getPlanilla();
        this.get_Analisis_Tipo_M();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jXDatePicker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jXDatePicker2ActionPerformed

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
            java.util.logging.Logger.getLogger(JAportes_wapoment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JAportes_wapoment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JAportes_wapoment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JAportes_wapoment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JAportes_wapoment(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField3;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    // End of variables declaration//GEN-END:variables
}
