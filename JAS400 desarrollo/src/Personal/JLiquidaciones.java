/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personal;

import BD_As400.JConection;
import Ingresos.JIngresos;
import Principal.Fecha;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author GACA1186
 */
public class JLiquidaciones extends javax.swing.JFrame {
    private JConection JBase_Datos;
    private Connection Cn;
    private String jTextField1 , jTextField2;
    private SimpleDateFormat d;   
    private Vector Cabecera;
    private Vector Detalle;
    private Vector Fila;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    /**
     * Creates new form JLiquidaciones
     */
    public JLiquidaciones(JConection JBase_Datos3, Connection Cn2) {
        JFormato= new DecimalFormat(NumeroFormato);
        JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        initComponents();
        Cabecera = new Vector();
        Cabecera.add("Codigo");
        Cabecera.add("Nombre del trabajador");
        Cabecera.add("Fecha de Liquidacion");
        Cabecera.add("Fecha Ingreso");
        Cabecera.add("Fecha Retiro");
        Cabecera.add("Fecha Fin contrato");
        Cabecera.add("Fecha Inicio contrato");
        Cabecera.add("Fecha Ultimo Salario");
        Cabecera.add("Extras y Recargo");
        Fila= new Vector();
        jXDatePicker1.setFormats("yyyyMMdd"); 
        jXDatePicker2.setFormats("yyyyMMdd");
        d = new SimpleDateFormat("yyyyMMdd");
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Ejecutar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha Inicial");

        jLabel2.setText("Fecha Final");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre del trabajador", "Fecha de Liquidacion", "Fecha Ingreso", "Fecha Retiro", "Fecha Fin Contrato", "Fecha Inicio Contrato", "Ultimo Salario", "Extras y recargo"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel1)
                .addGap(117, 117, 117)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(190, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
            
            jTextField1 = d.format(jXDatePicker1.getDate()).toString();
            jTextField2 = d.format(jXDatePicker2.getDate()).toString();
            
            Fecha Fecha_valida= new Fecha();
            Fecha_valida.setA??o(Integer.parseInt(this.jTextField1.trim().substring(0, 4)));
            Fecha_valida.setMes(Integer.parseInt(this.jTextField1.trim().substring(4, 6)));
            Fecha_valida.setDia(Integer.parseInt(this.jTextField1.trim().substring(6, 8)));
            
            Fecha Fecha_valida_2= new Fecha();
            Fecha_valida_2.setA??o(Integer.parseInt(this.jTextField2.trim().substring(0, 4)));
            Fecha_valida_2.setMes(Integer.parseInt(this.jTextField2.trim().substring(4, 6)));
            Fecha_valida_2.setDia(Integer.parseInt(this.jTextField2.trim().substring(6, 8)));
            
            if(Fecha_valida.validaFecha(Fecha_valida) && Fecha_valida_2.validaFecha(Fecha_valida_2)){
                this.getInformacion(this.jTextField1.trim(), this.jTextField2.trim());
            }else{
                javax.swing.JOptionPane.showMessageDialog(this, "Fecha Invalida");
            }
        }catch(Exception e ){
            javax.swing.JOptionPane.showMessageDialog(this,""+ e.getMessage());
        } 
         
    }//GEN-LAST:event_jButton1ActionPerformed
 
    public String getTres_Meses(int PFecha, int meses){
        /*
            Metodo para restar a una fecha en meses
            @param PFecha: fecha a cual se le desea realizar operacion meses: cantidad de meses a operar (+:suma, -resta)
            @return La fecha en formato aaaaMMYY
        */
        Calendar Cal = Calendar.getInstance();
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        String yyyyMMdd="";
        try {
            Integer value = PFecha;
            
            Date Fecha = originalFormat.parse(value.toString());
            Cal.setTime(Fecha);
            Cal.add(Calendar.MONTH, meses);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            yyyyMMdd = sdf.format(Cal.getTime());
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "class:JLiquidaciones:getTres_Meses() "+e.getMessage());
        }
        return yyyyMMdd;
    }
    public void get(String FecInicial, String FecFinal){
        try {
            /*
                @param  FecInicial: , FecFinal: 
                @return Salario promedio del ultimo a??o
            */
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "class:JLiquidaciones:getTres_Meses() "+e.getMessage());
        }
    }
    
    public double [] get_Ultimo_Salario(String Fecha_Liq_Cesantia_Def, String Tracve, String FecIni, String FecFin){
        double Ultimo_Salario[]= new double[3];
        Ultimo_Salario[0]=0;
        Ultimo_Salario[1]=0;
        Ultimo_Salario[2]=0;
                    
        try {
            /* Buscar el ultimo salario teniendo en cuenta si tuvo salario variable en los ultimos tres meses o fijo
                @param 
                @return 
            */
            String Fecha_3 = getTres_Meses(Integer.parseInt(Fecha_Liq_Cesantia_Def), -3);
            System.out.println(""+Fecha_3+" Fecha Fin"+Fecha_Liq_Cesantia_Def);
            String Str_Sql ="select count(*) cantidad from adamco.thsue where hsufec>="+Fecha_3+" and hsufec<="+Fecha_Liq_Cesantia_Def+
                            " and tracve like '% "+Tracve.trim()+"' " ;
            //System.out.println(""+Str_Sql);
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double Salario =0;
            if(rs.next()){
                if (rs.getInt("cantidad")>1){
                    /*
                        buscar el promedio del ultimo A??o
                    */
                    System.out.println("ENTRO POR ACA");
                    String Fec_Un_A??o = getTres_Meses(Integer.parseInt(Fecha_Liq_Cesantia_Def), -12);
                    double Prom_Salario = getSalario_Historico(Fec_Un_A??o, Fecha_Liq_Cesantia_Def, Tracve);
                    //System.out.println("Tracve "+Tracve+" --Fec inicial "+Fec_Un_A??o+" Fecha Final "+Fecha_Liq_Cesantia_Def);
                    double Valor_Horas_Extras = getSalario_Horas_Extras(Fec_Un_A??o, Fecha_Liq_Cesantia_Def, Tracve);
                    Ultimo_Salario[0]=Prom_Salario;
                    Ultimo_Salario[1]=Valor_Horas_Extras;
                    Ultimo_Salario[2]=1;
                }else{
                    /*
                        buscar el ultimo salario vigente 
                    */
                    Salario = get_ultimo_Sal_Vigente(Tracve, FecIni, FecFin);
                    Ultimo_Salario[0]=Salario;
                    Ultimo_Salario[1]=0;
                    Ultimo_Salario[2]=2;
                }
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "class:JLiquidaciones:get_Ultimo_Salario() "+e.getMessage());
        }
        return  Ultimo_Salario;
    }
    
     public double getSalario_Horas_Extras(String FecInicia, String FecFinal, String Tracve){
         /*
            Metodo que busca las horas extras y recargos que pueda tener un trabajador en un intervalo de tiempo
            @param String FecInicia: Fecha Inicial,  FecFinal: Fecha final del analisis , Tracve:Codigo interno del trabajador
            @return Total_Extras: corresponden al total devengado
         */
         double Promedio__Extras=0;
         try {
             String ano_inicial=FecInicia.substring(0,4);
             String mes_inicial=FecInicia.substring(4,6);
            
             String ano_final=FecFinal.substring(0,4);
             String mes_final=FecFinal.substring(4,6);
             /*
                Busca las horas extras, y recargos con sus respectivos retroactivos por quincen para obtener el promedio.
             */
             String Str_Sql="select tracve, sum(TRNIMP) as TRNIMP from adamco.TACNO where tracve like '% "+Tracve.trim()+"' "
                           + " and tnocve='QN' and CPRA??O>="+ano_inicial+" and CPRMAC>="+mes_inicial
                           + " and CPRA??O<="+ano_final+" and CPRMAC<="+mes_final+" and concod in ("
                     + "2018,2028,2038,2048,2058,2068,\n" +
                       "2078,2088,2098,2113,2123,2133,\n" +
                       "2143,2153,2163,2173,2183,2193,\n" +
                       "2196,2513,2516,2519,2522,2525,\n" +
                       "2528,2531,2534,2537,2540,2543,\n" +
                       "2546,2549,2552,2555,2558,2561,\n" +
                       "2564,2567) "
                     + "group by Tracve ";
             System.out.println(""+Str_Sql);
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             if(rs.next()){
                   Promedio__Extras = Promedio__Extras+0; 
             }
             Promedio__Extras=Promedio__Extras/12;
             rs.close();
             /*
               Buscas los recargos por quincen y obtiene el promedio de estas
             */
             
             
         } catch (Exception e) {
             javax.swing.JOptionPane.showMessageDialog(this, "class:JLiquidaciones:getSalario_Horas_Extras() "+e.getMessage());
         }
         return Promedio__Extras;
     }
    public double getSalario_Historico(String FecInicia, String FecFinal, String Tracve){
        double Salario = 0;
        try {
            /*
                Metodo para obtener el salario promedio del ultimo a??o por tener variacion en los ultimos tres meses
                @param FecInicia, FecFinal, Tracve
            */
            
            
            String ano_inicial=FecInicia.trim().substring(0,4);
            String mes_inicial=FecInicia.trim().substring(4,6);
            
            String ano_final=FecFinal.trim().substring(0,4);
            String mes_final=FecFinal.trim().substring(4,6);
            
            String Str_Sql=" select tracve , sum(TRNIMP) as TRNIMP  from adamco.TACNO where tracve like '% "+Tracve.trim()+"' and "
                           + " TNOCVE='QN' and CPRA??O>="+ano_inicial+" and CPRMAC>="+mes_inicial
                           + " and CPRA??O<="+ano_final+" and CPRMAC<="+mes_final+" and concod=2000"
                           + " group by Tracve ";
            //System.out.println(""+Str_Sql);
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(rs.next()){
                Salario= Salario +rs.getDouble("TRNIMP");
            }
            Salario = Salario/12;
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "class:JLiquidaciones:getSalario_Historico(String FecInicia, String FecFinal, String Tracve) "+e.getMessage());
        }
        return Salario;
    }
    public double get_ultimo_Sal_Vigente(String Tracve, String Fecha_Inicial, String Fecha_Liq_Cesantia_Def){
        /*
            buscar el ultimo salario vigente 
            @param Tracve:Codigo del trabajador, FecInicial: Fecha Inical, FecFinal:Fecha Final
            @return Fecha:fecha en la que incio el contrato
        */
        double Salario=0;
        try {
             String Str_Sql ="select * from adamco.thsue where hsufec>="+Fecha_Inicial+" and hsufec<="+Fecha_Liq_Cesantia_Def+
                            " and tracve like '% "+Tracve.trim()+"' order by hsufec desc" ;
             //System.out.println(""+Str_Sql);
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             if(rs.next()){
                 //System.out.println(""+rs.getString("Tracve")+" - "+rs.getString("HSUFEC")+" - "+rs.getString("HSUSDI"));
                 Salario=rs.getDouble("hsusdi");
             }
             rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "class:JLiquidaciones:get_Ultimo_Salario() "+e.getMessage());
        }
        return Salario;
    }
    public int getFecha_Inicio(String Tracve, String FecInicial, String FecFinal){
        /*  
            @param Tracve:Codigo del trabajador, FecInicial: Fecha Inical, FecFinal:Fecha Final
            @return Fecha:fecha en la que incio el contrato
        */
        int Fecha=0;
        try {
            String Str_Sql =" select * from adamco.thsue where tracve like '%"+Tracve.trim()+"' and HSUFEC>="+FecInicial+" and HSUFEC<="+FecFinal;
            //System.out.println(""+Str_Sql);
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                Fecha=rs.getInt("HSUFEC");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "class:JLiquidaciones:getFecha_Cesantia_Definitiva() "+e.getMessage());
        }
        return Fecha;
    }
    
    
    public int getFecha_Finalizacion_Contrato(String Tracve, String FecInicial, String FecFinal){
        /*
            Metodo para buscar la fecha de finalizacion del contrato al que se le desea realizar la liquidacion Definitiva
            @param Tracve: Codigo Trabajador, FecInciial:Fecha Incial, FecFinal: Fecha Final
            @return Fecha de finalizacion del contrato
        */
        int Fecha =0;
        try {
            String Str_Sql ="select liqfec from adamco.tliqh where liqfec>="+FecInicial+" and liqfec<="+FecFinal+""
                    + " and TNOCVE='LD' and Tracve like'%"+Tracve.trim()+"'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                Fecha=rs.getInt("liqfec");
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "class:JLiquidaciones:getFecha_Finalizacion_Contrato(String FecInicial, String FecFinal) "+e.getMessage());
        }
        return Fecha;
    }

    public void getInformacion(String Fecha_inicial, String Fecha_Final){
        /*
            Metodo principal para armar estructura requerida
            Para esto se recorre todos los trabajadores que tenga liquidacion definitiva
            y se hace llamado a los distintos metodos que brindan informacion.
        */
        
        try {
            String Str_Sql =" SELECT TRACVE, TRANOM  , TRAFIN FEC_INGRESO ,  TRAFBA  FEC_RETIRO , TRAFAN FEC_CESANTIA \n" +
                            " FROM ADAMCO.TTRAB                                                \n" +
                            " WHERE  INT(TRACVE) <='9999'                                      \n" +
                            " and TRAFBA>="+Fecha_inicial+" and TRAFBA<="+Fecha_Final+" ORDER BY 1     ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Fila.clear();
            while(rs.next()){
                
                String Tracve = rs.getString("tracve");
                String Nombre = rs.getString("tranom");
                String Fecha_Liquidacion = rs.getString("FEC_CESANTIA");
                String Fecha_Ingreso =rs.getString("FEC_INGRESO");
                String Fecha_retiro =rs.getString("FEC_RETIRO");
                
                int Fecha_Fin_Contrato =getFecha_Finalizacion_Contrato(Tracve, Fecha_inicial, Fecha_Final);
                int Fecha_Inicio_Contrato=getFecha_Inicio(Tracve, Fecha_inicial, Fecha_Final);
                double U_Salario [] =get_Ultimo_Salario(""+Fecha_Fin_Contrato,Tracve,Fecha_inicial, Fecha_Final );
               
                Detalle=  new Vector();
                Detalle.add(Tracve);
                Detalle.add(Nombre);
                Detalle.add(Fecha_Liquidacion);
                Detalle.add(Fecha_Ingreso);
                Detalle.add(Fecha_retiro);
                Detalle.add(Fecha_Fin_Contrato);
                Detalle.add(Fecha_Inicio_Contrato);
                Detalle.add(""+this.JFormato.format(U_Salario[0]));
                Detalle.add(""+U_Salario[1]);
                this.Fila.add(Detalle);
            }

            jTable1.setModel(new javax.swing.table.DefaultTableModel(Fila, Cabecera));
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "class:JLiquidaciones:getInformacion() "+e.getMessage());
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
            java.util.logging.Logger.getLogger(JLiquidaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JLiquidaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JLiquidaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JLiquidaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JLiquidaciones(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    // End of variables declaration//GEN-END:variables
}
