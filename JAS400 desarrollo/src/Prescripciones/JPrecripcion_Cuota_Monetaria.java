/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prescripciones;

import Subsidio.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;



import BD_As400.JConection;
import JTable_PDF_Excel.JExport;
import Principal.Fecha;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author GACA1186
 */
public class JPrecripcion_Cuota_Monetaria extends javax.swing.JFrame {

    private String Ruta;
    private Vector Lista;
    private String NombreArchivo;
    private JConection JBase_Datos;
    private Connection Cn;
    private SimpleDateFormat d;
    private Vector Cabecera;
    private Vector Detalle;
    private Vector Fila;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat  JFormato ;
    private JArchivo_Escolar JFile_Es;
    private JArchivo_Cuota_Monetaria JFile_Cuota;
    /**
     * Creates new form JSaldo_RDM
     */
    public JPrecripcion_Cuota_Monetaria(JConection JBase_Datos3, Connection Cn2) {
        Lista = new Vector();
        this.Cabecera = new Vector();
        Cabecera.add("Bolsillo");
        Cabecera.add("Valor");
        Detalle = new Vector();
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        JFormato= new  DecimalFormat(NumeroFormato);
        initComponents();
        d = new SimpleDateFormat("yyyyMMdd");
        jXDatePicker1.setFormats("yyyyMMdd");
        jXDatePicker2.setFormats("yyyyMMdd");
        jXDatePicker3.setFormats("yyyyMMdd");
    }
    public void setRuta(String SRuta){
         this.Ruta = SRuta;
         this.LeerArchivo_Escolar();
    }
    public void setRuta_Cuota_Monetaria(String SRuta){
         this.Ruta = SRuta;
         this.LeerArchivo_Cuota_Monetaria();
    }
     public void LeerArchivo_Escolar(){
          try{
            // Abrimos el archivo
              
            FileInputStream fstream = new FileInputStream(Ruta);
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            
            String Str_Sql="delete from selinlib.JPRESCRIP1 ";
            boolean Rs = this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
            int Linea=0;
            if(Rs){
                
                while ((strLinea = buffer.readLine()) != null)   {
                    String Array_Campo[] = strLinea.split(jTextField1.getText().trim());
                    if(Linea==0){
                        Linea =1;
                    }else{
                        if (!Array_Campo[0].equals("")){
                            //this.jList1.add(strLinea.toLowerCase());
                            Str_Sql ="insert into selinlib.JPRESCRIP1 values('"+Array_Campo[0]+"','"+Array_Campo[1]+"','"+Array_Campo[2]+"','"+Array_Campo[3]+"')";
                            //System.out.println(""+Str_Sql);
                            Rs= this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
                        }   
                    }
                }
             
            }
            // Cerramos el archivo
            entrada.close();
         
     
        }catch (Exception e){ //Catch de excepciones
            javax.swing.JOptionPane.showMessageDialog(this, "ReadFile:_LeerArchivo() "+e.getMessage());
        }
    }
  
    public void get_novedad_prescripcion(String FechaFinal){
        try {
             String Str_Sql =  "select * from subsilib.tarje005 as t01     \n" +
                               "where  FEGDES ='"+FechaFinal+"'   AND            \n" +
                               " PREDES = 1                          ";
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             this.setTable(this.jTable3 ,rs);
            //Ajustar las columnas de forma automatica 
            jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable3.setAutoCreateRowSorter(true);
            Str_Sql =  "select sum(VALDES) as valor from subsilib.tarje005 as t01     \n" +
                               "where  FEGDES ='"+FechaFinal+"'   AND            \n" +
                               " PREDES = 1                          ";
            ResultSet rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double total = 0;
            if(rs2.next()){
                total = rs2.getDouble("valor");
            }
            rs2.close();
            jTextField3.setText(this.JFormato.format(total));
            
            
            Str_Sql="delete from selinlib.JPRESCRIP3 ";
            boolean Rs = this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
            
            Str_Sql=" insert into selinlib.JPRESCRIP3   \n" +
                    " select * from selinlib.JPRESCRIP1 \n" +
                    " where TARJETA >= 8000000000000     ";
            Rs = this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
            
            Str_Sql=" insert into selinlib.JPRESCRIP3   \n" +
                    " select * from selinlib.JPRESCRIP2 \n" +
                    " where TARJETA >= 8000000000000     ";
            Rs = this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
            
            
            Str_Sql =  "select * from selinlib.JPRESCRIP3 as t01     \n" ;
            ResultSet rs3 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.setTable(this.jTable5 ,rs3);
            //Ajustar las columnas de forma automatica 
            jTable5.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable5.setAutoCreateRowSorter(true);
            
            Str_Sql =  "select sum(VALOR) as valor from selinlib.JPRESCRIP3 as t01     \n";
            ResultSet rs4 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double total2 = 0;
            if(rs4.next()){
                total2 = rs4.getDouble("valor");
            }
            rs4.close();
            jTextField4.setText(this.JFormato.format(total2));
            jTextField5.setText(this.JFormato.format(total-total2));
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "setTable(javax.swing.JTable jtQuery , ResultSet rs) "+e.getMessage());
        }
    }  
      
      
    public void get_Cheques_pagos_ventanilla(String FechaIncial , String FechaFinal){
      try {
             String Str_Sql =   "select  *          from subsilib.maecheq as t01               \n" +
                                "where cheval <> 0  and cheest ='A' and cheno2 not like '88%'  \n" +
                                "and chefec <= '"+FechaIncial+"' and chefen between '"+FechaFinal.substring(0, 6)+"01"+"' and    \n" +
                                "'"+FechaFinal+"'"; 
             System.out.println(" "+Str_Sql);
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             this.setTable(this.jTable4 ,rs);
            //Ajustar las columnas de forma automatica 
            jTable4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable4.setAutoCreateRowSorter(true);
            
            Str_Sql =   "select  sum(cheval) as valor         from subsilib.maecheq as t01               \n" +
                                "where cheval <> 0  and cheest ='A' and cheno2 not like '88%'  \n" +
                                "and chefec <= '"+FechaIncial+"' and chefen between '"+FechaFinal.substring(0, 6)+"01"+"' and    \n" +
                                "'"+FechaFinal+"'"; 
            System.out.println("** "+Str_Sql);
            ResultSet rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double Total = 0;
            if(rs2.next()){
                Total =rs2.getDouble("valor");
            }
            jTextField2.setText(this.JFormato.format(Total));
            
            Str_Sql =   "select sum(valor) as valor from selinlib.JPRESCRIP2 \n" +
                        " where TARJETA <  8000000000000  "; 
            ResultSet rs3 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double Total3 = 0;
            if(rs3.next()){
                Total3 =rs3.getDouble("valor");
            }
            jTextField9.setText(this.JFormato.format(Total3));
            jTextField10.setText(this.JFormato.format(Total-Total3));
            
            Str_Sql =   "select *          from selinlib.JPRESCRIP2 \n" +
                        "where TARJETA <  8000000000000             "; 
            System.out.println("Vs "+Str_Sql);
             System.out.println(" "+Str_Sql);
             ResultSet rs10 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             this.setTable(this.jTable6 ,rs10);
             //Ajustar las columnas de forma automatica 
             jTable6.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
             jTable6.setAutoCreateRowSorter(true);
            
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "setTable(javax.swing.JTable jtQuery , ResultSet rs) "+e.getMessage());
        }
    }
     
     public void get_Cheques_pagos_ventanilla_no_incluidas(String FechaIncial , String FechaFinal){
        try {
             String Str_Sql =   "select  *          from subsilib.maecheq as t01               \n" +
                                "where cheval <> 0  and cheest =' ' and cheno2 not like '88%'  \n" +
                                "and chefec <= '"+FechaIncial+"' and chefen between '"+FechaFinal.substring(0, 6)+"01"+"' and    \n" +
                                "'"+FechaFinal+"'"; 
             System.out.println(" "+Str_Sql);
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             this.setTable(this.jTable2 ,rs);
            //Ajustar las columnas de forma automatica 
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable2.setAutoCreateRowSorter(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "setTable(javax.swing.JTable jtQuery , ResultSet rs) "+e.getMessage());
        }
    }   
     
     
     
    public void getMovimiento_Escolar(String FechaIncial , String FechaFinal){
        try {
             String Str_Sql =   "select *     \n" +
                                "FROM SELINLIB.JPRESCRIP1 AS T01 , subsilib.MOVREDEB  AS T02       \n" +
                                "where T01.DOCUMENTO=T02.MOVCED  and                               \n" +
                                "  MOVTIP in(16 ,17)         and  MOVAPR <>0                   "
                                +" AND SUBSTR(TARJETA, 1, 15) =movtar   \n" +
                                "  AND T02.MOVFEC BETWEEN  "+FechaIncial+" AND "+FechaFinal;
             System.out.println(" "+Str_Sql);
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             this.setTable(this.jTable1 ,rs);
            //Ajustar las columnas de forma automatica 
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable1.setAutoCreateRowSorter(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "setTable(javax.swing.JTable jtQuery , ResultSet rs) "+e.getMessage());
        }
    }
    
    public void getMovimiento_Cuota(String FechaIncial , String FechaFinal){
        try {
             String Str_Sql =   "select *                                                      \n" +
                                "FROM SELINLIB.JPRESCRIP2 AS T01 , subsilib.MOVREDEB  AS T02   \n" +
                                "where T01.DOCUMENTO=T02.MOVCED  and                           \n" +
                                " MOVTIP = 1          and  MOVAPR <>0                     \n" +
                                " AND T02.MOVFEC BETWEEN  "+FechaIncial+" AND "+FechaFinal+
                                " AND SUBSTR(TARJETA, 1, 15) =movtar \n";
             System.out.println(" "+Str_Sql);
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             this.setTable(this.jTable1 ,rs);
            //Ajustar las columnas de forma automatica 
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable1.setAutoCreateRowSorter(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "setTable(javax.swing.JTable jtQuery , ResultSet rs) "+e.getMessage());
        }
    }
    
    public void setTable(javax.swing.JTable jtQuery , ResultSet rs){
        /*
           @param jtQuery tabla que contendra informacion del query ejecutado
           @param rs variable que contendra el resulselt del Sql ejectuado
        */
        
        try {

           DefaultTableModel modelo = new DefaultTableModel();
           jtQuery.setModel(modelo); 
            
           ResultSetMetaData rsMd = rs.getMetaData();
           int cantidadColumnas = rsMd.getColumnCount();
 
           for (int i = 1; i <= cantidadColumnas; i++) {
                modelo.addColumn(rsMd.getColumnLabel(i));
                
           }
           //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i]=rs.getObject(i+1);
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "getInformacion_prescripcion() "+e.getMessage());
        }
    }
     
     
    public void LeerArchivo_Cuota_Monetaria(){
          String strLinea="";
          try{
            // Abrimos el archivo
              
            FileInputStream fstream = new FileInputStream(Ruta);
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            
            
            String Str_Sql="delete from selinlib.JPRESCRIP2 ";
            boolean Rs = this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
            int Linea=0;
            if(Rs){
                
                while ((strLinea = buffer.readLine()) != null)   {
                    System.out.println(" "+strLinea);
                    String Array_Campo[] = strLinea.split(jTextField1.getText().trim());
                    if(Linea==0){
                        Linea =1;
                    }else{
                        if (!Array_Campo[0].equals("")){
                            //this.jList1.add(strLinea.toLowerCase());
                            Str_Sql ="insert into selinlib.JPRESCRIP2 values('"+Array_Campo[0]+"','"+Array_Campo[1]+"','"+Array_Campo[2]+"','"+Array_Campo[3]+"')";
                            //System.out.println(""+Str_Sql);
                            Rs= this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
                        }   
                    }
                }
             
            }
            // Cerramos el archivo
            entrada.close();
     
        }catch (Exception e){ //Catch de excepciones
            javax.swing.JOptionPane.showMessageDialog(this, "ReadFile:_LeerArchivo() "+e.getMessage()+" "+strLinea);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jXDatePicker3 = new org.jdesktop.swingx.JXDatePicker();
        jLabel9 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Control de Saldo Tarjeta Multiservicios - Redeban");
        setResizable(false);

        jButton1.setText("Examinar...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Escolares");

        jButton2.setText("Examinar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Cuota Monetaria");

        jLabel3.setText("(Documento,Nombre,Tarjeta,Valor)");

        jLabel4.setText("Estructura Excel");

        jTextField1.setText(";");

        jLabel5.setText("Delimitado por:");

        jButton3.setText("Consultar Escolar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Consulta Cuota Monetaria");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel6.setText("Fecha del Acta");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/export.png"))); // NOI18N
        jButton5.setText("Exportar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jTextArea1.setText("       Tipo de Movimiento en Redeban Multicolor\n               1    Pago Cuota Monetaria\n               14 Subsidio Escolar\n               17 Subidio Universitario\n         Nota: JPRESCRIP2/JPRESCRIP1");
        jScrollPane2.setViewportView(jTextArea1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jLabel7.setText("No incluidas en el acta");

        jButton6.setText("Consultar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 991, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(51, 51, 51)
                                    .addComponent(jLabel6))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(47, 47, 47)
                                    .addComponent(jButton3)
                                    .addGap(33, 33, 33)
                                    .addComponent(jButton4)
                                    .addGap(26, 26, 26)
                                    .addComponent(jButton5))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 991, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(64, 64, 64)
                                    .addComponent(jButton6)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addComponent(jLabel4)))
                                .addGap(75, 75, 75)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jLabel1)))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jButton2))
                                .addGap(97, 97, 97)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jLabel3)
                            .addComponent(jButton2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jButton6))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        jTabbedPane1.addTab("Movimiento Tarjetas", jPanel1);

        jXDatePicker3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker3ActionPerformed(evt);
            }
        });

        jLabel9.setText("Novedad");

        jButton8.setText("Consulta");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTable3);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(jTable5);

        jLabel10.setText("Total Acta");

        jLabel11.setText("Novedad Tarjeta");

        jLabel12.setText("Diferencia");

        jLabel13.setText("ANEXO DE PRESCRIPCION TARJETAS MULTISERVICIOS");

        jLabel14.setText("NOVEDAD APLICADAS A LAS TARJETAS MULTISERVICIOS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jXDatePicker3, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(39, 39, 39)
                                    .addComponent(jLabel9)))
                            .addGap(33, 33, 33)
                            .addComponent(jButton8)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(57, 57, 57)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(46, 46, 46)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(102, 102, 102)
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10)
                                    .addGap(82, 82, 82)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(59, 59, 59)
                                    .addComponent(jLabel12))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jScrollPane6))
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton8)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(7, 7, 7)
                                .addComponent(jXDatePicker3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("TARJET005 vs ACTA", jPanel2);

        jButton7.setText("Consulta");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jXDatePicker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker2ActionPerformed(evt);
            }
        });

        jLabel8.setText("Fecha del Novedad");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTable4);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(jTable6);

        jLabel15.setText("Acta");

        jLabel19.setText("Cheques");

        jLabel20.setText("VENTANILLA DE ACUERDO AL ACTA");

        jLabel21.setText("VENTANILLA DEL MAESTRO CHEQUES");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel20)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                                    .addComponent(jScrollPane7)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(70, 70, 70)
                        .addComponent(jButton7)
                        .addGap(39, 39, 39)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(93, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(143, 143, 143)
                .addComponent(jLabel15)
                .addGap(362, 362, 362))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel15)
                    .addComponent(jLabel19))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(214, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Prescripcion Ventanillas", jPanel3);

        jButton9.setText("Consulta");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel16.setText("Subsidio Escolares");

        jLabel17.setText("Subsidio Familiar");

        jLabel18.setText("Total");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel16))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel17))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jLabel18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton9)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                                .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addContainerGap(827, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton9)
                .addGap(61, 61, 61)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addGap(7, 7, 7)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addGap(3, 3, 3)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(653, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Resumen del Acta", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        JFile_Es = new JArchivo_Escolar (new javax.swing.JFrame(), true, this);
        JFile_Es.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFile_Cuota = new JArchivo_Cuota_Monetaria (new javax.swing.JFrame(), true, this);
        JFile_Cuota.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String jTextField1= d.format(jXDatePicker1.getDate()).toString().trim();
        Fecha Fecha_System = new Fecha();
        String Fec_Inicial = Fecha_System.getTres_Meses(Integer.parseInt(jTextField1.trim()), -36);
        getMovimiento_Escolar(Fec_Inicial , jTextField1);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String jTextField1= d.format(jXDatePicker1.getDate()).toString().trim();
        Fecha Fecha_System = new Fecha();
        String Fec_Inicial = Fecha_System.getTres_Meses(Integer.parseInt(jTextField1.trim()), -36);
        getMovimiento_Cuota(Fec_Inicial , jTextField1);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        JExport  JFile = new JExport ();
        JFile.setTable(jTable1);
        JFile.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String jTextField1= d.format(jXDatePicker1.getDate()).toString().trim();
        Fecha Fecha_System = new Fecha();
        String Fec_Inicial = Fecha_System.getTres_Meses(Integer.parseInt(jTextField1.trim()), -36);
        get_Cheques_pagos_ventanilla_no_incluidas(Fec_Inicial , jTextField1);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jXDatePicker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jXDatePicker2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        String jTextField1= d.format(jXDatePicker2.getDate()).toString().trim();
        Fecha Fecha_System = new Fecha();
        String Fec_Inicial = Fecha_System.getTres_Meses(Integer.parseInt(jTextField1.trim()), -36);
        get_Cheques_pagos_ventanilla(Fec_Inicial , jTextField1);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jXDatePicker3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jXDatePicker3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        String jTextField1= d.format(jXDatePicker3.getDate()).toString().trim();
        get_novedad_prescripcion(jTextField1);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        get_resumen_acta();
    }//GEN-LAST:event_jButton9ActionPerformed

    public void get_resumen_acta(){
        try {
            
             String Str_Sql =  "select sum(valor) as valor from selinlib.JPRESCRIP1 "; 
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             double total1=0;
             if(rs.next()){
                 total1=rs.getDouble("valor");
             }
             rs.close();
             
             Str_Sql =  "select sum(valor) as valor from selinlib.JPRESCRIP2 "; 
             double total2=0;
             ResultSet rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             if(rs2.next()){
                 total2=rs2.getDouble("valor");
             }
             rs2.close();
             jTextField6.setText(this.JFormato.format(total1));
             jTextField7.setText(this.JFormato.format(total2));
             jTextField8.setText(this.JFormato.format(total1+total2));
             
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "get_resumen_acta() "+e.getMessage());
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
            java.util.logging.Logger.getLogger(JPrecripcion_Cuota_Monetaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JPrecripcion_Cuota_Monetaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JPrecripcion_Cuota_Monetaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JPrecripcion_Cuota_Monetaria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JPrecripcion_Cuota_Monetaria(null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker3;
    // End of variables declaration//GEN-END:variables
}
