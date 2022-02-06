/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ingresos;

import BD_As400.JConection;
import Principal.Fecha;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author GACA1186
 */
public class JReporte_Saldo_Centro_Detalle extends javax.swing.JFrame {
    private JConection JBase_Datos;
    private Connection Cn;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private SimpleDateFormat d;
    private String jTextField1, jTextField2;
    private double recibo_caja_propio=0;
    private double recibo_caja_otras=0;
    
    /**
     * Creates new form JReporte_Saldo_Centro_Detalle
     */
    public JReporte_Saldo_Centro_Detalle(JConection JBase_Datos3, Connection Cn2) {
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        JFormato= new DecimalFormat(NumeroFormato);
        initComponents();
        d = new SimpleDateFormat("yyyyMMdd");
        jXDatePicker1.setFormats("yyyyMMdd");
        jXDatePicker2.setFormats("yyyyMMdd");
    }

    
    public double getSaldoAnterior_CAC(String Fecha, String Centro ){
          double SaldoAnterior =0;
          try {
              //Centro = getCentroEquivalente(jComboBox1.getSelectedItem().toString().trim());
              String Str_Sql ="SELECT Valor FROM selinlib.jsaldos WHERE "
                             +" fecha ="+RestarDias_Saldos_CAC(Fecha)+" AND de=99999999"
                             +" AND CodigoCAC ="+Centro;
              //System.out.println("SALDO DIA ANTERIOR "+Str_Sql);
              ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
              if(rs.next()){
                    SaldoAnterior = rs.getDouble("Valor");
              }
              rs.close();
          } catch (Exception e) {
              JOptionPane.showMessageDialog(this,"Class:JIngresos:getSaldoAnterior_CAC()"+e.getMessage()); 
          }
          return SaldoAnterior;
      }
    
      public String getMes(String xMes){
                    String Mes = "";
                    int zMes = Integer.parseInt(xMes);
                    zMes = zMes - 1;
                    if (zMes == 0){
                       Mes="12";
                       zMes = 12;
                    }
                    if (zMes < 10){
                            Mes = "0"+ String.valueOf(zMes);
                    }else{
                         Mes =  String.valueOf(zMes);
                    }
                    return Mes ;
     }
    
      public String RestarDias_Saldos_CAC(String FechaIncial){
        int Dia = Integer.parseInt(FechaIncial.substring(6, 8));
        String zMes = FechaIncial.substring(4, 6);
        String zDia ="";
        int zAño = Integer.parseInt( FechaIncial.substring(0, 4));
        if ( Dia  ==  1){
            zMes = this.getMes(zMes);
            if(Integer.parseInt(zMes)== 12){
                zAño = zAño -1;
            }
            try {
                String Str_Sql = " select   max(substring( fecha , 7 ,2 )) as dia "+
                                 " from selinlib.jsaldos   "+
                                 " where substring(fecha , 5 , 2) = "+zMes+" AND substring(fecha , 1 , 4)="+zAño ;
                //System.out.println("Restar dia "+Str_Sql);
                ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                if(rs.next()){
                    zDia = rs.getString("dia");
                    Dia = Integer.parseInt(zDia);
                }
                rs.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Form:JIngresos: RestarDias() "+e.getMessage());
            }
        }else{
            Dia = Dia - 1 ;
        }
        
        if (Dia<10){
            zDia = "0"+String.valueOf(Dia);
        }else{
            zDia = String.valueOf(Dia);
        }
        //System.out.println(""+zAño+zMes+zDia);
        return zAño+zMes+zDia;
    }
    
    
    public double get_Movimiento(String PFecha, String PCentro, String TipoMov){
        double Valor =0;
        try {
            String Str_Sql= "select codigocac , fecha ,sum(valor) as valor from selinlib.jsaldos \n" +
                            " where fecha = "+PFecha+"   and codigocac ="+PCentro+
                            " and observa like '%"+TipoMov+"%'  \n" +
                            " group by codigocac, fecha ";
            //System.out.println(""+Str_Sql);
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                Valor= rs.getDouble("Valor");
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Class:JReporte_Saldo_Centro_Detalle:get_Movimiento "+e.getMessage());
        }
        return  Valor;
    }
    
    
    public double getSubsidio_Pagados(String PFecha, String PCentro){
        double Valor=0;
        try {
            
            String Str_Sql="select * from selinlib.jcac \n" +
                            "where fecha ="+PFecha+" and codigo="+PCentro;
            //System.out.println(""+Str_Sql);
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                Valor= rs.getDouble("valor");
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return Valor;
    }
    
    public double getIngresos_CAC(String Centro, String Fecha){
        double Valor  =0;
        try {
            
            //Centro 1 es la 48
            String Str_Sql ="SELECT T02.cencod , SUM(PLEFSU) as Valor FROM recaudos.LAPORT1 T01,  recaudos.CENTRO t02  WHERE T01.CENCOD =  " +
                            " T02.CENCOD AND PLAFEI="+Fecha+" AND T01.CENCOD ="+Centro+
                            " GROUP BY T02.CENCOD , T02.CENNOM  ORDER BY 1 ASC  ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                Valor=rs.getDouble("Valor");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Class:getIngresos_CAC "+e.getMessage());
        }
        return Valor;
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
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Saldo Detallado por Centro");
        setResizable(false);

        jButton1.setText("Consultar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha Inicial (AAAAMMDD)");

        jLabel2.setText("Fecha Final (AAAAMMDD)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jButton1))
                    .addComponent(jLabel2))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(194, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Informe", jPanel1);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
     try{   
        Fecha Fecha_valida= new Fecha();
        
        jTextField1 = d.format(jXDatePicker1.getDate()).toString();
        jTextField2 = d.format(jXDatePicker2.getDate()).toString();
        
        Fecha_valida.setAño(Integer.parseInt(this.jTextField1.trim().substring(0, 4)));
        Fecha_valida.setMes(Integer.parseInt(this.jTextField1.trim().substring(4, 6)));
        Fecha_valida.setDia(Integer.parseInt(this.jTextField1.trim().substring(6, 8)));
         
        Fecha Fecha_valida2= new Fecha();
        Fecha_valida2.setAño(Integer.parseInt(this.jTextField2.trim().substring(0, 4)));
        Fecha_valida2.setMes(Integer.parseInt(this.jTextField2.trim().substring(4, 6)));
        Fecha_valida2.setDia(Integer.parseInt(this.jTextField2.trim().substring(6, 8)));
        
        
        if ((this.jTextField1.trim().equals("")) || ((this.jTextField1.trim().equals("")) ) 
                || (!Fecha_valida.validaFecha(Fecha_valida)) || (!Fecha_valida2.validaFecha(Fecha_valida2))  ) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe ingresar las fechas o en el formato adecuado");
        }else{
            Clear_Alls();
            principal(this.jTextField1.trim(), this.jTextField2.trim());  
            JJasper PDF_JJasper = new JJasper();
            PDF_JJasper.setTipo(16);
            PDF_JJasper.setFechaInicial(jTextField1.trim());
            PDF_JJasper.setFechaFinal(jTextField2.trim());
            PDF_JJasper.setConeccion(Cn);
            PDF_JJasper.start();
        }
     }catch(Exception e){
         JOptionPane.showMessageDialog(this,"Debe Ingresar la fecha"+e.getMessage());
     }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    public void Generar_Pdf(String FechaInicial, String FechaFinal){
        try {
        
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Class:  Generar_Pdf(String FechaInicial, String FechaFinal) "+e.getMessage());
        }
    }
    
    public String Equivalencia_recaudo_to_tarjeas_siged(String Codigo){
       String XCodigo = "";
       try {
            String Str_Sql = "Select cac,codtarj from Selinlib.JCCentros where codigo="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getString("cac");
            }
            rs.close();
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this," Class:JIngresos: Equivalencia_Tarjetas() "+e.getMessage()); 
       }
       return XCodigo;
   } 
    public void principal(String FecIni, String FecFin){
        try {
            String Str_Sql  =" select fecha, codigocac, valor  from selinlib.jsaldos \n" +
                             " where fecha >="+FecIni+" and fecha<="+FecFin+" and de = 99999999             \n" +
                             "group by fecha, codigocac, valor ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double XValor_Actual;
            while(rs.next()){
                XValor_Actual = 0;
                String XFecha=rs.getString("fecha");
                String XCentro=rs.getString("codigocac");
                XValor_Actual=rs.getDouble("valor");
                getPrincipal_Movimientos(XFecha,XCentro, XValor_Actual);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Class:  principal(String FecIni, String FecFin) "+e.getMessage());
        }
    }
    
    public String get_equivalencia(String Codigo){
        String centro="";
        try {
            String Str_Sql  =" select * from selinlib.JCCENTROS where cac="+Codigo;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(rs.next()){
                centro = rs.getString("CODTARJ");
            }
            System.out.println(""+Str_Sql);
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "get_equivalencia(String Codigo) "+e.getMessage());
        }
        return centro;
    }
    public void get_recibos_cajas(String PCentro, String FechaIncial){
        recibo_caja_propio=0;
        recibo_caja_otras=0;
        try {
            String Centro = Equivalencia_recaudo_to_tarjeas_siged(PCentro);
            String Str_Sql= "";
            if(PCentro.equals("1")){
                Str_Sql =" SELECT CODTARJ FROM SELINLIB.JCCENTROS WHERE CODTARJ  NOT IN ( 0 ,  554 ) ";
                ResultSet rs_centros = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                ResultSet resultado=null;
                //System.out.println(""+Str_Sql);
                while(rs_centros.next()){
                        String WCentro = rs_centros.getString("CODTARJ");
                        String SQL= "SELECT T01.CODCCO , SUM(TRPVAL) as Valor" +
                                    " FROM SGDATOS.TRCJC as T01 , SGDATOS.TRCJP as T04 " +
                                    " WHERE T01.TRCNUM = T04.TRCNUM AND " +
                                    " T01.TRCFEC = "+FechaIncial+"   AND T01.TRCEST ='G' AND TBACVE = 100 " +
                                    " AND T01.CODCCO = "+WCentro+" "+
                                    " GROUP BY T01.CODCCO ";
                       resultado = JBase_Datos.SQL_QRY(this.Cn,SQL);
                       //System.out.println(""+SQL);
                       if (resultado.next()){
                           recibo_caja_otras = recibo_caja_otras+resultado.getDouble("Valor");
                       }
               }
               //rs_x_centros.close(); 
               //rs_centros.close();
            }else{
                String costo = get_equivalencia(Centro);
                Str_Sql= "SELECT T01.CODCCO , SUM(TRPVAL) as Valor " +
                            " FROM SGDATOS.TRCJC as T01 , SGDATOS.TRCJP as T04 " +
                            " WHERE T01.TRCNUM = T04.TRCNUM AND " +
                            " T01.TRCFEC = "+FechaIncial+"   AND T01.TRCEST ='G' AND TBACVE = 100 " +
                            " AND T01.CODCCO = "+costo+" "+
                            " GROUP BY T01.CODCCO ";
                //System.out.println(""+Str_Sql);
                ResultSet rs_x_centros = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                if (rs_x_centros.next()){
                   recibo_caja_propio = recibo_caja_propio + rs_x_centros.getDouble("Valor");
                }
                //rs_x_centros.close();
            }
            
            //System.out.println("propio "+recibo_caja_propio);
            //System.out.println("otras "+recibo_caja_otras);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Class:JIngresos:get_recibos_cajas () "+e.getMessage());
        }
    }
    public void getPrincipal_Movimientos(String PFecha,String PCentro, double Valor_Actual){
           // TODO add your handling code here:
        double  Transferencia = get_Movimiento(PFecha, PCentro, "Transferencia");
        double Ingresos = get_Movimiento(PFecha, PCentro, "Ingreso");
        double Cambio_Cheque = get_Movimiento(PFecha, PCentro, "Cambio");
        double Subsidio_pagados=getSubsidio_Pagados(PFecha, PCentro);
        double Saldo_Anterior = getSaldoAnterior_CAC(PFecha,PCentro);
        recibo_caja_propio=0;
        recibo_caja_otras=0;
        if(Integer.parseInt(PFecha)>=20190110){
            //recibo_caja_propio
            //recibo_caja_otras
            get_recibos_cajas(PCentro,PFecha);
        }
        
        //Para el cenro 1 que es la 48 el calculo es distinto
        //Mejorar para el programa de control de Ingreso Linea 2676
        double Ingresos_CAC =0;
        if(PCentro.equals("1")){
                double TotalEfectivo = get_Total_Efectivo(PFecha);
                double  Vlr_Otras = 0;
                try {
                    String Str_Sql ="select * from selinlib.JCCENTROS where Codigo<> 1 ";
                    ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                    while(rs.next()){
                        Vlr_Otras = Vlr_Otras + getIngresos_CAC(rs.getString("Codigo"), PFecha);
                    }
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Class: "+e.getMessage());
                }
                Ingresos_CAC = TotalEfectivo - Vlr_Otras;
        }else{
               Ingresos_CAC = getIngresos_CAC(PCentro, PFecha);
        }
        
        //System.out.println("Ingresos del centro de atencion "+Ingresos_CAC);
        //System.out.println("Transferencia "+Transferencia);
        //System.out.println("ingreso "+Ingresos);
        //System.out.println("ingreso "+Cambio_Cheque);
        //System.out.println("Subsidio Pagados "+Subsidio_pagados);
        //System.out.println("Saldo Anterior "+Saldo_Anterior);
        //Saldo_Actual_Calculado = 
        //Ingresos_CAC =  Ingresos_CAC + recibo_caja_propio - recibo_caja_otras;
        setMovimiento(PCentro,PFecha,Ingresos_CAC, Transferencia, Ingresos, Cambio_Cheque, Subsidio_pagados,Saldo_Anterior,0.0,Valor_Actual, recibo_caja_propio, recibo_caja_otras);
        
    }
    
     public boolean Clear_Alls(){
        boolean SQL = false;
        try {
            String Str_Sql ="DELETE FROM SELINLIB.JDETALLSAL " ; 
            SQL = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Class:  setMovimiento(...) "+e.getMessage());
        }
        return SQL;
    }
    
    public boolean setMovimiento(String Codigo, String Fecha,double Ingresos_Ventas ,double Transferencia, double Ingreso, double Cambio_Cheque, double Sub_Pagado, double Saldo_Anterior, double Saldo_Actual_Calculado, double Saldo_Actual_Sistema , double recibo_caja_propio, double recibo_caja_otro){
        boolean SQL = false;
        try {
            String Str_Sql ="INSERT INTO SELINLIB.JDETALLSAL (CODIGO,FECHA,INGRESO, TRANSF_MOV, INGRES_MOV,CCHEQU_MOV,SUBPAGAD,SALDOANT, SALDOACT, SALDOACT2, RCAJA_PROP, RCAJA_OTRO )"+
                       "VALUES("+Codigo+","+Fecha+","+Ingresos_Ventas+","+Transferencia+","+Ingreso+","+Cambio_Cheque+","+Sub_Pagado+","+Saldo_Anterior+","+Saldo_Actual_Calculado+","+Saldo_Actual_Sistema+","+recibo_caja_propio+","+recibo_caja_otro+")"; 
            SQL = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Class:  setMovimiento(...) "+e.getMessage());
        }
        return SQL;
    }
    
    
    public double get_Total_Efectivo(String Fecha){
        double Valor = 0;
        try {
            String Str_Sql=" select fecha, eftcons  from selinlib.jingreso "
                            + " where fecha="+Fecha;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                Valor = rs.getDouble("eftcons");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Class:get_Total_Efectivo(String Fecha) "+e.getMessage());
        }
        return Valor;
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
            java.util.logging.Logger.getLogger(JReporte_Saldo_Centro_Detalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JReporte_Saldo_Centro_Detalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JReporte_Saldo_Centro_Detalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JReporte_Saldo_Centro_Detalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JReporte_Saldo_Centro_Detalle(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    // End of variables declaration//GEN-END:variables
}
