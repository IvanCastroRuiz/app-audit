package Ingresos;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JIngresos.java   
 * Desarrollado por: Garyn Carrillo Caballero, Auditor de Sistemas.
 * Created on 2/05/2012, 07:52:03 AM
 */


import ReporteIngresos.JReportSaldoCentros;
import ReporteIngresos.JReportControlIngresos;
import BD_As400.JConection;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */
public class JIngresos extends javax.swing.JFrame {

    /** Creates new form JIngresos */
    private String FechaIncial , FechaFinal;
    private String FechaMes;
    private JConection JBase_Datos;
    private Connection Cn;
    private String Usuario;
    private int Fila ;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private double TotalEfectivo = 0;
    private double TotalConsolidadoEfectivo = 0;
    private double TotalCanje = 0;
    private double TotalConsolidadoCanje = 0;
    private double SaldoAnteriorVentasRetenidas = 0;
    private double SaldoAnteriorCanjeRetenidas = 0;
    private double TotalSubsidiosPagados = 0;
    private double SaldoActualVentasRetenidas =0;
    private double SaldoActualCanje = 0 ;
    private Object [] Cabecera ;
    private Object [] [] Detalle  ;
    private Object [] Cabecera2 ;
    private Object [] [] Detalle2  ;
    private double CanjeNominaRetenida = 0;
    private Vector CentroAtenciones;
    private Vector VlrCentroAtenciones;
    private Object [] Cabecera3 ;
    private Object [] [] Detalle3 ;
    private Object [] Cabecera4 ;
    private Object [] [] Detalle4 ;
    private Object [] Cabecera5 ;
    private Object [] [] Detalle5 ;
    private Object [] Cabecera6 ;
    private Object [] [] Detalle6 ;
    
    private Vector Cab_Micro;
    private Vector Det_Micro;
    private Vector Fil_Micro;
    
    private double xVentaRetenida ;
    private double xValorAbonado ;
    private double xCajeConsignar;
    private double SubsidioPagadosCentrosAtencion;
    private double SaldoAnteriorCentroAtencion, SubsidiosPagadosXCentroAtencion,SaldoActualXCentroAtencion;
    private int PosicionTabla4, PosicionTabla5,PosicionTabla6;
    private double SumaIngreso = 0, SumaTransferencia = 0, SumaComision =0, SumaCambioCheque = 0, ChequeCambiadoRetenido = 0;
    private double SumaTarjetasPositivas, SumaTarjetasNegativas, SumaTarjetasW;
    private double SaldoAnteriorTarjetas, SaldoActualTarjetas, TotalVentasTarjetas;
    private boolean Grabar;
    private Vector ZCab;
    private Vector ZFila;
    private Vector ZDet;
    
    private double recibo_caja_propio=0;
    private double recibo_caja_otras=0;
    private double valor_pagado_empledos_efectivo=0;
    
    public JIngresos(JConection JBase_Datos3, Connection Cn2, String Usuariox, String FechaIni, String FechaFin) {
        valor_pagado_empledos_efectivo =0;
        ZCab = new Vector();
        ZFila= new Vector();
        Cabecera =  new Object [6];
        Detalle  = new Object [30][6];
        Cabecera2 =  new Object [5];
        Detalle2  =  new Object [5][5];
        Cabecera3 =  new Object [6];
        Detalle3  =  new Object [4][6];
        Cabecera4 =  new Object [4];
        Detalle4  =  new Object [50][4];
        Cabecera5 =  new Object [4];
        Detalle5  =  new Object [50][4];
        Cabecera6 =  new Object [5];
        Detalle6  =  new Object [50][5];
        Fil_Micro = new Vector();
        Cab_Micro = new Vector();
        
        CentroAtenciones = new Vector ();
        VlrCentroAtenciones = new Vector ();
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.Usuario = Usuariox;
        this.FechaIncial = FechaIni;
        this.FechaFinal  = FechaFin;
        JFormato= new DecimalFormat(NumeroFormato);
        initComponents();
        this.buscarSaldoAnterior();
        this.getConsolidado();
        //Mejora 1.0
        ZCab.add("Codigo Centro Atencion");
        ZCab.add("Cenrro Atencion");
        ZCab.add("Valor Pagado");
        ZCab.add("Valor Esperado");
        ZCab.add("Diferencia");
        boolean si = setTable_2();
        //this.getTable();
        this.getIngresos();
        //this.getSubsidioPagados(0);
        this.getSubSidioPagados_2();
        this.getEfectivo_CAC();
        this.PosicionTabla4 = -1;
        PosicionTabla5 = -1;
        PosicionTabla6 = -1;
        this.getDetalle_Pagos_Cac();
        
        jLabel1.setText("Fecha Inicial: "+this.FechaIncial);
        jLabel30.setText("Fecha: " + this.FechaIncial);
        jLabel41.setText("Fecha: " + this.FechaIncial);
        jLabel42.setText("Fecha: " + this.FechaIncial);
        this.getEstadoPeriodo();
        setCombo_Centro_Costo();
        
        if(si=false){
            setCentro_Atencion();
        }
        jButton9.setEnabled(false);
        jButton3.setEnabled(false);
    }
    
    public void setCentro_Atencion(){
        try {
            String Str_Sql ="select codigo , descrip from selinlib.JCCENTROS ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            ZFila.clear();
            
            while(rs.next()){
                ZDet = new Vector();
                ZDet.add(rs.getString("codigo"));
                ZDet.add(rs.getString("descrip"));
                ZDet.add("0");
                ZDet.add("0");
                ZDet.add("");
                ZFila.add(ZDet);
            }
            this.jTable2.setModel(new javax.swing.table.DefaultTableModel(ZFila, ZCab));
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"class:Jngresos:setCentro_Atencion() "+e.getMessage());  
        }
    }
    
    public void setCombo_Centro_Costo(){
        try {
            String Str_Sql=" select cac,codigo, codtarj from selinlib.JCCENTROS ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            jComboBox7.addItem("");
            jComboBox1.addItem("");
            jComboBox5.addItem("");
            jComboBox4.addItem("");
            jComboBox3.addItem("");
        
            while(rs.next()){
                
                if(rs.getInt("codtarj")!=0){
                    jComboBox7.addItem(rs.getString("cac"));
                }
                jComboBox1.addItem(rs.getString("cac"));
                jComboBox5.addItem(rs.getString("codigo"));
                jComboBox3.addItem(rs.getString("cac"));
                jComboBox4.addItem(rs.getString("cac"));
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:JIngresos: setCombo_Centro_Costo() "+e.getMessage());  
        }
    }
    
    public void getEstadoPeriodo(){
        try {
            String PPeriodo =  this.FechaIncial.substring(0, 6);
            String Str_Sql = "select * from selinlib.jingcierre where periodo="+PPeriodo+" "
                            + " and Estado ='ABIERTO'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.Grabar = false;
            
            if(rs.next()){
                this.Grabar = true;
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:JIngresos:getEstadoPeriodo() "+e.getMessage());  
        }
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
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jTextField41 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jTextField26 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jTextField27 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jTextField40 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox();
        jButton13 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        JTxtReciboCajas = new javax.swing.JTextField();
        jTxtReciboCajaOtros = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTextField29 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTextField35 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jTextField30 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jTextField38 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTextField39 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox();
        jComboBox10 = new javax.swing.JComboBox();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema para Control de Ingresos Centros de Atención");
        setBounds(new java.awt.Rectangle(180, 0, 0, 0));
        setResizable(false);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo Centro Atencion", "Centros de Atencion", "Valor Pagado", "Valor Esperado", "Diferencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(jTable2);

        jButton4.setText("Calcular");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Fecha  : ");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton11.setText("Eliminar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Agregar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setText("Valor");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setText("Caja Pos");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setText("Centro de Atencion");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Cac", "Cajero", "Valor"
            }
        ));
        jScrollPane6.setViewportView(jTable5);

        jLabel59.setText("Pagos efectivo");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(27, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel59)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel31)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton12)
                                .addGap(24, 24, 24)
                                .addComponent(jButton11)))
                        .addGap(22, 22, 22))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(jLabel31))
                .addGap(10, 10, 10)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jButton12)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );

        jTabbedPane1.addTab("1. Subsidios pagados", jPanel6);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Control de Ingresos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jTable1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Punto de Venta", "Efectivo", "Canje", "Consolidado Efectivo", "Consolidado Canje"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TOTAL EFECTIVO ESPERADO POR CONSIGNAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jTextField5.setText("0");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Menos: Cambio de Cheque");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Menos: Venta Retenida");

        jTextField4.setText("0");

        jTextField3.setText("0");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Abono de Ventas retenidas");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Menos: Efectivo GNB");

        jTextField1.setText("0");
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Efectivo por Consignar");

        jTextField18.setEditable(false);
        jTextField18.setBackground(new java.awt.Color(204, 204, 204));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Sobrante/Faltante");

        jTextField20.setEditable(false);
        jTextField20.setBackground(new java.awt.Color(204, 204, 204));

        jTextField28.setText("0");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel43.setText("Menos: Cheque Retenido");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(57, 57, 57)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(44, 44, 44)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel43))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(jTextField28))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TOTAL ESPERADO EN CANJE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Canje Consignado");

        jTextField6.setText("0");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Cambio de Cheques/Retenido:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Cheque pendiente por consignar");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Canje Retenido");

        jTextField7.setEditable(false);
        jTextField7.setBackground(new java.awt.Color(204, 204, 204));

        jTextField8.setText("0");

        jTextField9.setEditable(false);
        jTextField9.setBackground(new java.awt.Color(204, 204, 204));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Canje Esperado");

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel21)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12))
                .addGap(106, 106, 106)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VENTAS RETENIDAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Saldo Anterior");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Valor Retenido");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Valor Abonado");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Saldo Actual");

        jTextField10.setEditable(false);
        jTextField10.setBackground(new java.awt.Color(204, 204, 204));

        jTextField11.setEditable(false);
        jTextField11.setBackground(new java.awt.Color(204, 204, 204));

        jTextField12.setEditable(false);
        jTextField12.setBackground(new java.awt.Color(204, 204, 204));
        jTextField12.setToolTipText("Dpto de Auditoria:\nSumatoria de Abonos diarios del Acumulado ventas retenidas.");

        jTextField13.setEditable(false);
        jTextField13.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CANJE RETENIDO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Saldo Actual");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Saldo Anterior");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Valor Retenido");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Valor Abonado");

        jTextField14.setEditable(false);
        jTextField14.setBackground(new java.awt.Color(204, 204, 204));

        jTextField15.setEditable(false);
        jTextField15.setBackground(new java.awt.Color(204, 204, 204));

        jTextField16.setText("0");

        jTextField17.setEditable(false);
        jTextField17.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setText("Guardar");
        jButton1.setToolTipText("Guarda la informacion en el Sistema Central");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setText("Imprimir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel17))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton5))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5))
        );

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel42.setText("Fecha:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel42)
                        .addGap(91, 91, 91))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))))
        );

        jTabbedPane1.addTab("2. Control de Ingreso", jPanel1);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jButton2.setText("Guardar");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(234, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton2)
                .addContainerGap(335, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("3. Observaciones", jPanel7);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tipo movimiento", "Descripcion", "De", "Para", "Valor"
            }
        ));
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTable6);

        jTextField26.setEnabled(false);
        jTextField26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField26ActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setText("Total Entradas");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("Total Salidas");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel36.setText("Saldo Anterior");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel38.setText("Saldo Actual");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel39.setText("Total venta de Tarjetas");

        jTextField31.setEnabled(false);
        jTextField31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField31ActionPerformed(evt);
            }
        });

        jTextField32.setEnabled(false);
        jTextField32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField32ActionPerformed(evt);
            }
        });

        jTextField33.setEnabled(false);
        jTextField33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField33ActionPerformed(evt);
            }
        });

        jTextField34.setEnabled(false);
        jTextField34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField34ActionPerformed(evt);
            }
        });

        jButton3.setText("Guardar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setText("Total venta.");

        jLabel56.setText("Diferencia");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel35)
                                                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel13Layout.createSequentialGroup()
                                                    .addGap(50, 50, 50)
                                                    .addComponent(jLabel37))
                                                .addGroup(jPanel13Layout.createSequentialGroup()
                                                    .addGap(29, 29, 29)
                                                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel40)
                                                        .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                    .addComponent(jLabel39))
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addComponent(jLabel36)
                                        .addGap(90, 90, 90)
                                        .addComponent(jLabel38))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel13Layout.createSequentialGroup()
                                                .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(jLabel56))))
                            .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel56))
                .addGap(8, 8, 8)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jComboBox7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox7MouseClicked(evt);
            }
        });

        jButton13.setText("Buscar");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setText("Centro de Atencion");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setText("Fecha: ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel41)
                        .addGap(93, 93, 93))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jButton13)))
                        .addContainerGap(191, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(281, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("4. Control de Tarjetas", jPanel8);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });

        jButton6.setText("Buscar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Centros de Atencion");

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ingreso", "Transferencia", "Comision", "Cambio Cheque" }));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setText("Tipo de Movimiento");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setText("Para:");

        jButton7.setText("Insertar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "De:", "Para:", "Valor", "Observacion"
            }
        ));
        jScrollPane5.setViewportView(jTable4);

        jTextField21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField21.setEnabled(false);

        jTextField22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField22.setEnabled(false);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText("Ingreso de Efectivo ");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setText("Saldo Actual");

        jButton8.setText("Eliminar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setText("Valor");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setText("Saldo Anterior");

        jTextField24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField24.setEnabled(false);

        jButton9.setText("Guardar");
        jButton9.setEnabled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setText("De:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setText("Subsidio Pagado");

        jTextField25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField25.setEnabled(false);

        jButton10.setText("Imprimir");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        JTxtReciboCajas.setEditable(false);

        jTxtReciboCajaOtros.setEditable(false);

        jLabel57.setText("Recibo de Caja");

        jLabel58.setText("Recibo de Caja Otros Centros");

        jLabel60.setText("Pagos en efectivo");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)
                                        .addComponent(jButton7)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton8))))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JTxtReciboCajas, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel57)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                        .addGap(188, 188, 188)
                                        .addComponent(jButton9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton10)
                                        .addGap(10, 10, 10))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel58)
                                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField22, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                                .addComponent(jTxtReciboCajaOtros)))))))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel60)
                            .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel26))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField23)
                            .addComponent(jButton7)
                            .addComponent(jButton8)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel57)
                                .addComponent(jLabel58)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JTxtReciboCajas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtReciboCajaOtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 188, Short.MAX_VALUE)
                        .addComponent(jButton9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton10)))
                .addContainerGap())
        );

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel30.setText("Fecha: ");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel30)
                        .addGap(77, 77, 77))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6))
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(176, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("5. Saldo por Centro", jPanel9);

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30", "48", "55", "74", "76", "82", "99" }));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel44.setText("Centros de Atencion");

        jButton14.setText("Buscar");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "De:", "Para:", "Tipo Movimiento", "Cta Banco", "Valor"
            }
        ));
        jScrollPane4.setViewportView(jTable3);

        jLabel45.setText("Cta Banco");

        jTextField35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField35ActionPerformed(evt);
            }
        });

        jLabel46.setText("Valor");

        jLabel47.setText("Tipo Movimiento");

        jButton15.setText("Agregar");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("Eliminar");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setText("Guardar");

        jLabel48.setText("Saldo Anterior");

        jLabel49.setText("Saldo Actual");

        jLabel50.setText("Pago POS");

        jTextField38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField38ActionPerformed(evt);
            }
        });

        jLabel51.setText("Salidas");

        jLabel52.setText("Diferencia");

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30", "48", "55", "74", "76", "82", "99" }));

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30", "48", "55", "74", "76", "82", "99" }));

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel53.setText("Para:");

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel54.setText("De:");

        jLabel55.setText("Valor");

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ingreso", "Transferencia", "Comision", "Cambio Cheque" }));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel46)
                .addGap(222, 222, 222))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel44)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jButton14))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox10, 0, 106, Short.MAX_VALUE))
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGap(95, 95, 95)
                                        .addComponent(jLabel47))))))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel48)
                                .addGap(76, 76, 76)
                                .addComponent(jLabel50)))
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addComponent(jLabel52)
                                .addGap(76, 76, 76)))
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel49)))
                        .addGap(107, 107, 107))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton15, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel55)
                                .addGap(29, 29, 29)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton16)))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14))
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel53)
                                            .addComponent(jLabel54))
                                        .addGap(10, 10, 10))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel47)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel45)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15)
                    .addComponent(jButton16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(jLabel49)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51)
                            .addComponent(jLabel52))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addComponent(jButton17)
                        .addGap(69, 69, 69))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("6. Micro-Credito", jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        if(Grabar){
            this.CalculaEfectivoConsigna();
            this.CalculaCanjeEsperado();
            this.CalculoVentaRetenida ();
            this.CalculoCanjeRetenido();
            this.setControlIngreso();
       }else{
           JOptionPane.showMessageDialog(this," El periodo esta Cerrado"); 
       }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       // TODO add your handling code here:
       //Detalle de la informacion Digitada en la tabla5 de los pagos por cajeroa
       
       if(Grabar){
            this.setDetalle_Pagos_Cac();
            this.Agrupa_Centro_Atencion();
            this.getSubsidioPagados(1);
            //Recalculo Nuevamente
            this.CalculoCanjeRetenido();
            jButton1.setEnabled(true);
       }else{
           JOptionPane.showMessageDialog(this," El periodo esta Cerrado"); 
       }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        JReportControlIngresos rp = new JReportControlIngresos(this.JBase_Datos, this.Cn ,this.FechaIncial);
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    
        if(Grabar){
       
            if(!jTextField23.getText().trim().equals("")){
                this.AgregaPosicion();
                jTextField23.setText("");
            }else{
                JOptionPane.showMessageDialog(this,"Ingrese un Valor");  
            }
        }else{
            JOptionPane.showMessageDialog(this," El periodo esta Cerrado"); 
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if(Grabar){
        
            if (jTable4.getSelectedRow()!= -1){
                 this.setBorrarFila();
            }
        }else{
            JOptionPane.showMessageDialog(this," El periodo esta Cerrado"); 
        }
    }//GEN-LAST:event_jButton8ActionPerformed
    public double getCambiocheque(){
        double Vlr = 0;
        try {
            String Str_Sql = "select CMBCHEQ from selinlib.jingreso where fecha="+this.FechaIncial;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(rs.next()){
                Vlr = rs.getDouble("CMBCHEQ");
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Class:JIngresos:getCambiocheque() "+e.getMessage());
        }
        return Vlr;
    }
    
    public void get_recibos_cajas(){
        recibo_caja_propio=0;
        recibo_caja_otras=0;
        try {
            String Centro= Equivalencia_Tarjetas(jComboBox1.getSelectedItem().toString().trim());
            String Str_Sql= "";
            
            if(jComboBox1.getSelectedItem().toString().trim().equals("48")){
                this.valor_pagado_empledos_efectivo= get_pago_efectivo_empleados(this.FechaIncial );
            
                if(Integer.parseInt(this.FechaIncial)>=20190612){
                    jTextField42.setText(""+this.valor_pagado_empledos_efectivo);
                }else{
                    this.valor_pagado_empledos_efectivo = 0;
                    jTextField42.setText(""+this.valor_pagado_empledos_efectivo);
                }
                
                Str_Sql =" SELECT CODTARJ FROM SELINLIB.JCCENTROS WHERE CODTARJ  NOT IN ( 0 ,  554 ) ";
                ResultSet rs_centros = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                ResultSet resultado=null;
                //System.out.println(""+Str_Sql);

                while(rs_centros.next()){
                        String WCentro = rs_centros.getString("CODTARJ");
                        String SQL= "SELECT T01.CODCCO , SUM(TRPVAL) as Valor" +
                                    " FROM SGDATOS.TRCJC as T01 , SGDATOS.TRCJP as T04 " +
                                    " WHERE T01.TRCNUM = T04.TRCNUM AND " +
                                    " T01.TRCFEC = "+this.FechaIncial+"   AND T01.TRCEST ='G' AND TBACVE = 100 " +
                                    " AND T01.CODCCO = "+WCentro+" "+
                                    " GROUP BY T01.CODCCO ";
                       resultado = JBase_Datos.SQL_QRY(this.Cn,SQL);
                       int Error = 0;
                       //System.out.println(""+SQL);

                       if (resultado.next()){
                           recibo_caja_otras = recibo_caja_otras+resultado.getDouble("Valor");
                           Error++;
                           System.out.println("Error "+Error);
                       }
               }
               //rs_x_centros.close(); 
               //rs_centros.close();
            }else{
                this.valor_pagado_empledos_efectivo =0;
                jTextField42.setText(""+this.valor_pagado_empledos_efectivo);
                Str_Sql= "SELECT T01.CODCCO , SUM(TRPVAL) as Valor " +
                            " FROM SGDATOS.TRCJC as T01 , SGDATOS.TRCJP as T04 " +
                            " WHERE T01.TRCNUM = T04.TRCNUM AND " +
                            " T01.TRCFEC = "+this.FechaIncial+"   AND T01.TRCEST ='G' AND TBACVE = 100 " +
                            " AND T01.CODCCO = "+Centro+" "+
                            " GROUP BY T01.CODCCO ";
                ResultSet rs_x_centros = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

                if (rs_x_centros.next()){
                   recibo_caja_propio = recibo_caja_propio + rs_x_centros.getDouble("Valor");
                }
                //rs_x_centros.close();
            }
            
            System.out.println("propio "+recibo_caja_propio);
            System.out.println("otras "+recibo_caja_otras);
            JTxtReciboCajas.setText(""+recibo_caja_propio);
            jTxtReciboCajaOtros.setText(""+recibo_caja_otras);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Class:JIngresos:get_recibos_cajas () "+e.getMessage());
        }
    }
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if(!jComboBox1.getSelectedItem().toString().trim().equals("") ){
            this.SubsidioPagadosCentrosAtencion = this.getCentroAtencion(jComboBox1.getSelectedItem().toString().trim());
            
            if(Integer.parseInt(this.FechaIncial)>=20190110){
                //Apartir de esta fecha se deja de grabar los recibo de cajas en el sistema
                this.get_recibos_cajas();
            }
            
            if(jComboBox1.getSelectedItem().toString().trim().equals("48")){
                this.SubsidioPagadosCentrosAtencion= this.SubsidioPagadosCentrosAtencion- this.getCambiocheque();
                jTextField21.setText(this.JFormato.format(this.SubsidioPagadosCentrosAtencion));
            }else{
                jTextField21.setText(this.JFormato.format(this.SubsidioPagadosCentrosAtencion));
            }
            
            this.SaldoAnteriorCentroAtencion = this.getSaldoAnterior_CAC();
            jTextField24.setText(this.JFormato.format(this.SaldoAnteriorCentroAtencion));
            jButton9.setEnabled(true);
            this.setClearTable();
            this.getMovimiento_CAC();
            this.getSaldoActualCentro();
            jButton9.setEnabled(true);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
       if(Grabar){
          SumaIngreso = 0; SumaTransferencia = 0; SumaComision =0; SumaCambioCheque = 0;

          if ( this.setSaldos_CAC()){
             JOptionPane.showMessageDialog(this,"se ha Guardado Correctamente"); 
             this.getOperacionTabla();
          }

          this.SaldoActualXCentroAtencion = this.SaldoAnteriorCentroAtencion +this.SumaIngreso+this.SumaTransferencia-this.SumaComision-this.SumaCambioCheque-this.SubsidiosPagadosXCentroAtencion+this.SubsidioPagadosCentrosAtencion+recibo_caja_propio - recibo_caja_otras-valor_pagado_empledos_efectivo;
          jTextField22.setText(JFormato.format(this.SaldoActualXCentroAtencion));
          this.setSaldoActual();
       }else{
           JOptionPane.showMessageDialog(this," El periodo esta Cerrado"); 
       }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        JReportSaldoCentros rp = new JReportSaldoCentros(this.JBase_Datos,this.Cn,this.FechaFinal);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:

        if(Grabar){
            this.AgregarCajaCentro();
            jTextField2.setText("");
            jComboBox6.setSelectedIndex(0);
       }else{
           JOptionPane.showMessageDialog(this," El periodo esta Cerrado"); 
       }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        
        if(Grabar){

            if (jTable5.getSelectedRow()!= -1){
                this.setBorrarFila_tabla5();
                this.PosicionTabla5--;
            }
        }else{
            JOptionPane.showMessageDialog(this," El periodo esta Cerrado"); 
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        this.getMovimierntoTarjetas();
        this.getSaldo_Anterior_Tarjetas();
        this.getTarjetas();
        jButton3.setEnabled(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTextField26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField26ActionPerformed

    private void jTextField31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31ActionPerformed

    private void jTextField32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField32ActionPerformed

    private void jTextField33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField33ActionPerformed

    private void jTextField34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField34ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {

            if(Grabar){
                this.TotalVentasTarjetas = Double.parseDouble(jTextField27.getText());
                this.SaldoActualTarjetas = this.SaldoAnteriorTarjetas + this.SumaTarjetasPositivas - this.SumaTarjetasNegativas-this.TotalVentasTarjetas;
                jTextField34.setText(JFormato.format(this.SaldoActualTarjetas));
                jTextField40.setText(""+JFormato.format(this.SumaTarjetasW-this.TotalVentasTarjetas));
                this.setTarjetas();
            }else{
                JOptionPane.showMessageDialog(this," El periodo esta Cerrado"); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," JIngresos:Button3 "+e.getMessage()); 
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField35ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField35ActionPerformed

    private void jTextField38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField38ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        this.agragar_Micro_Credito();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        this.Eliminar_Micro_Credito();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        
        //319651 plu de microcredito
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable6MouseClicked

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        // TODO add your handling code here:
        jButton9.setEnabled(false);
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox7MouseClicked
        // TODO add your handling code here:
        jButton3.setEnabled(false);
    }//GEN-LAST:event_jComboBox7MouseClicked
    
    public void agragar_Micro_Credito(){
        this.Cab_Micro.clear();
        this.Cab_Micro.add("De:");
        this.Cab_Micro.add("Para:");
        this.Cab_Micro.add("Tipo Movimiento");
        this.Cab_Micro.add("Cta Banco");
        this.Cab_Micro.add("Valor");
        
        try {
           this.Det_Micro = new Vector();
           String xDe = jComboBox9.getSelectedItem().toString();
           String xPara = jComboBox10.getSelectedItem().toString();
           String xTmovimiento = jComboBox11.getSelectedItem().toString();
           String xBanco = jTextField29.getText().trim();
           String xValor = jTextField35.getText().trim();
           this.Det_Micro.add(xDe);
           this.Det_Micro.add(xPara);
           this.Det_Micro.add(xTmovimiento);
           this.Det_Micro.add(xBanco);
           this.Det_Micro.add(xValor);
           this.Fil_Micro.add(this.Det_Micro);
           this.jTable3.setModel(new javax.swing.table.DefaultTableModel(this.Fil_Micro, this.Cab_Micro));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "JClass:JIngresos:Agragar_Micro_Credito() "+e.getMessage());
        }
    }
    
    public void Eliminar_Micro_Credito(){
       try{ 
         String xDe = jTable3.getValueAt(jTable3.getSelectedRow(),0).toString();
         String xPara = jTable3.getValueAt(jTable3.getSelectedRow(),1).toString();
         //this.setDeleteTable(xDe, xPara);
         DefaultTableModel Modelo =(DefaultTableModel) jTable3.getModel();
         Modelo.removeRow(jTable3.getSelectedRow());
         jTable3.updateUI();
       }catch(Exception e){
           JOptionPane.showMessageDialog(this," Class:JIngresos: El registro seleccionado debe Contener informacion "); 
       }
    }
    
    
    public double getSubsidios_Pagados_Sistema_Pos(String Centro){
        double Vlr = 0;
        try {
            String Str_Sql = "Select t2.cencod , sum(comvlr) as valor from recaudos.bascom as t1 ,recaudos.cbacom as t2 where "
                            + " t1.comfec ="+this.FechaIncial +" and t1.comfec = t2.comfec and t1.usucod = t2.usucod  "
                            + " and t2.cencod = "+Centro+" and t1.cajcod = t2.cajcod and TISUEN <>7"
                            + " group by t2.cencod ";
            //tisuen=7 Corresponde a las bases asiganada a los cajeros para micro credito inicado desde 5 Junio de 2017.
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(rs.next()){
               Vlr = rs.getDouble("Valor");
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," Class:JIngresos:getSubsidios_Pagados_Sistema_Pos() "+e.getMessage()); 
        }
        return Vlr;
    }
    public void getSaldoActualCentro(){
        try {
            String Centro = getCentroEquivalente(jComboBox1.getSelectedItem().toString().trim());
            String Str_Sql = "SELECT valor FROM selinlib.jsaldos WHERE codigocac= "+Centro
                    + " AND fecha="+this.FechaIncial
                    + " AND DE = 99999999 AND PARA = 99999999 ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(rs.next()){
                jTextField22.setText(""+JFormato.format(rs.getDouble("valor")));
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," class:JIngresos:getSaldoActualCentro() "+e.getMessage()); 
        }
    }
    
    
    public void getTarjetas(){
        try {
            String Centro= Equivalencia_Tarjetas(jComboBox7.getSelectedItem().toString().trim());
            String Str_Sql = "SELECT ventas,salact FROM selinlib.jtarjetas "
                            +" WHERE fecha ="+this.FechaIncial +" AND codigo="+Centro;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(rs.next()){
                this.TotalVentasTarjetas =rs.getDouble("ventas");
                jTextField27.setText(""+this.TotalVentasTarjetas);
                jTextField34.setText(JFormato.format(rs.getDouble("salact")));
            }
            //Diferencia Solicitada
            jTextField40.setText(""+JFormato.format(this.SumaTarjetasW-this.TotalVentasTarjetas));
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," Class:JIngresos: getTarjetas()"+e.getMessage()); 
        }
    }    
   
    public void setTarjetas(){
        try {
            String Centro= Equivalencia_Tarjetas(jComboBox7.getSelectedItem().toString().trim());
            String Str_Sql = "SELECT fecha FROM selinlib.jtarjetas "
                            +" WHERE fecha ="+this.FechaIncial +" AND codigo="+Centro;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(!rs.next()){
               Str_Sql = "INSERT INTO selinlib.jtarjetas (fecha,codigo,entrada,salida,salant,ventas,salact) "
                       + " VALUES ("+this.FechaIncial+","+Centro+","+this.SumaTarjetasPositivas+","+this.SumaTarjetasNegativas+","+this.SaldoAnteriorTarjetas+","+this.TotalVentasTarjetas+","+this.SaldoActualTarjetas+")";
            }else{
               Str_Sql = "UPDATE selinlib.jtarjetas "
                       + "SET entrada="+this.SumaTarjetasPositivas
                       + " ,Salida="+this.SumaTarjetasNegativas
                       + " ,salant="+this.SaldoAnteriorTarjetas
                       + " ,ventas="+this.TotalVentasTarjetas
                       + " ,salact="+this.SaldoActualTarjetas
                       + " WHERE fecha="+this.FechaIncial +" AND codigo="+Centro; 
            }
            boolean Guardar = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            if(Guardar){
                JOptionPane.showMessageDialog(this,"Se Guardo Correctamente");     
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," JIngresos: setTarjetas()"+e.getMessage()); 
        }
    }
    
    public void getSaldo_Anterior_Tarjetas(){
        try {
           String FechaAnterior = RestarDias_Saldos_Tarjertas_CAC();
           String Centro= Equivalencia_Tarjetas(jComboBox7.getSelectedItem().toString().trim());
           String Str_Sql ="SELECT codigo , salact FROM selinlib.jtarjetas WHERE fecha="+FechaAnterior +""
                           + " AND codigo ="+Centro;
           ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

           if(rs.next()){
               this.SaldoAnteriorTarjetas = rs.getDouble("salact");
           }
           jTextField33.setText(JFormato.format(this.SaldoAnteriorTarjetas));
           rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," JIngresos: getSaldo_Anterior_Tarjetas() "+e.getMessage()); 
        }
    }
    
   public String RestarDias_Saldos_Tarjertas_CAC(){
        int Dia = Integer.parseInt(this.FechaIncial.substring(6, 8));
        String zMes = this.FechaIncial.substring(4, 6);
        String zDia ="";
        int zAño =  Integer.parseInt( this.FechaIncial.substring(0, 4) );

        if ( Dia  ==  1){
            zMes = this.getMes(zMes);

            if(Integer.parseInt(zMes)== 12){                                                               
                zAño = zAño -1;
            }
            try {
                String Str_Sql = " select   max(substring( fecha , 7 ,2 )) as dia "+
                                 " from selinlib.jtarjetas   "+
                                 " where substring(fecha , 5 , 2) = "+zMes+" AND substring(fecha , 1 , 4)="+zAño ;
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
        System.out.println(""+zAño+zMes+zDia);
        return zAño+zMes+zDia;
    }
    public void clearTable6(){
       try {
           Cabecera6[0] ="Tipo movimiento";
           Cabecera6[1] ="Descripcion";
           Cabecera6[2] ="De";
           Cabecera6[3] ="Para";
           Cabecera6[4] ="Valor";

           for (int i = 0; i <=this.PosicionTabla6; i++) {
                   Detalle6[i][0]="";
                   Detalle6[i][1]="";
                   Detalle6[i][2]="";
                   Detalle6[i][3]="";
                   Detalle6[i][4]="";
           }
           this.PosicionTabla6 = -1;
           jTable6.setModel(new javax.swing.table.DefaultTableModel(Detalle6, Cabecera6));
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this," JIngresos: clearTable6() "+e.getMessage()); 
       }
   }
   public void getMovimierntoTarjetas(){
       try {
           String Centro= Equivalencia_Tarjetas(jComboBox7.getSelectedItem().toString().trim());

           if(!Centro.trim().equals("")){
               Cabecera6[0] ="Tipo movimiento";
               Cabecera6[1] ="Descripcion";
               Cabecera6[2] ="De";
               Cabecera6[3] ="Para";
               Cabecera6[4] ="Valor";
               String PDe="", PPara="";
               //Movimiento de Entradas del Centro de Atencion
               String Str_Sql ="SELECT TIPALB, CODCCO, CCORCV, (UNIDAD *COSULT) as VALOR   FROM SGDATOS.TRENTRF WHERE "
                           + " CODART  = 319649 AND CCORCV = "+Centro+" AND FECHAM ="+this.FechaIncial+" "
                           + " AND TIPALB <> 'V'";
               this.clearTable6();
               PosicionTabla6 = -1;
               ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
               SumaTarjetasPositivas = 0;
               SumaTarjetasNegativas = 0;

               while(rs.next()){
                   PosicionTabla6 ++;
                   Detalle6[PosicionTabla6][0]=rs.getString("TIPALB");
                   Detalle6[PosicionTabla6][1]="Ingreso";
                   PDe = rs.getString("CODCCO");
                   PPara = rs.getString("CCORCV");
                   PDe = this.Equivalencia_Tarjetas_User(PDe);
                   PPara= this.Equivalencia_Tarjetas_User(PPara);
                   Detalle6[PosicionTabla6][2]=PDe;
                   Detalle6[PosicionTabla6][3]=PPara;
                   double Vlr = rs.getDouble("VALOR");
                   Detalle6[PosicionTabla6][4]=Vlr;
                   this.SumaTarjetasPositivas =  this.SumaTarjetasPositivas +Vlr;
               }
               
               Str_Sql ="SELECT TIPALB, CODCCO, (UNIDAD *COSULT) as VALOR   FROM SGDATOS.GCENTRF WHERE "
                           + " CODART  = 319649 AND CODCCO = "+Centro+" AND FECHAM ="+this.FechaIncial;
               rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

               while(rs.next()){
                   PosicionTabla6 ++;
                   Detalle6[PosicionTabla6][0]=rs.getString("TIPALB");
                   Detalle6[PosicionTabla6][1]="Ingreso";
                   PDe = rs.getString("CODCCO");
                   PDe = this.Equivalencia_Tarjetas_User(PDe);
                   Detalle6[PosicionTabla6][2]=PDe;
                   Detalle6[PosicionTabla6][3]="";
                   double Vlr = rs.getDouble("VALOR");
                   Detalle6[PosicionTabla6][4]=Vlr;
                   this.SumaTarjetasPositivas =  this.SumaTarjetasPositivas +Vlr;
               }
               
               //Movimiento de Salidas del Centro de Atencion
               Str_Sql ="SELECT TIPALB, CODCCO, CCORCV, (UNIDAD *COSULT) as VALOR  FROM SGDATOS.TRENTRF WHERE "
                           + " CODART  = 319649 AND CODCCO = "+Centro+" AND FECHAM ="+this.FechaIncial
                           + " AND TIPALB <> 'U'";//20130829
               rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

               while(rs.next()){
                   PosicionTabla6 ++;
                   Detalle6[PosicionTabla6][0]=rs.getString("TIPALB");
                   Detalle6[PosicionTabla6][1]="Pagos";
                   PDe = rs.getString("CODCCO");
                   PPara = rs.getString("CCORCV");
                   PDe = this.Equivalencia_Tarjetas_User(PDe);
                   PPara= this.Equivalencia_Tarjetas_User(PPara);
                   Detalle6[PosicionTabla6][2]=PDe;
                   Detalle6[PosicionTabla6][3]=PPara;
                   double Vlr = rs.getDouble("VALOR");
                   Detalle6[PosicionTabla6][4]=Vlr;
                   this.SumaTarjetasNegativas =  this.SumaTarjetasNegativas +Vlr;
               }
               
               Str_Sql ="SELECT 'W' AS TIPALB, CODCCO, (UNIDAD *COSULT) as VALOR FROM SGDATOS.GCSALIF WHERE fecham="+this.FechaFinal+""
                       + " AND CODCCO="+Centro+" AND CODART=319649";
               rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
               SumaTarjetasW = 0;

               while(rs.next()){
                   PosicionTabla6 ++;
                   Detalle6[PosicionTabla6][0]=rs.getString("TIPALB");
                   Detalle6[PosicionTabla6][1]="Salida";
                   PDe = rs.getString("CODCCO");
                   PDe = this.Equivalencia_Tarjetas_User(PDe);
                   Detalle6[PosicionTabla6][2]=PDe;
                   Detalle6[PosicionTabla6][3]="";
                   double Vlr = rs.getDouble("VALOR");
                   Detalle6[PosicionTabla6][4]=Vlr;
                   this.SumaTarjetasW=  this.SumaTarjetasW +Vlr;
               }
               jTextField26.setText(JFormato.format(this.SumaTarjetasPositivas));
               jTextField32.setText(JFormato.format(this.SumaTarjetasNegativas));
               jTextField31.setText(JFormato.format(this.SumaTarjetasW));
               jTable6.setModel(new javax.swing.table.DefaultTableModel(Detalle6, Cabecera6));
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this," Class:JIngresos: getMovimierntoTarjetas() "+e.getMessage()); 
       }
   } 
   public String Equivalencia_Tarjetas_User(String Codigo){
       String XCodigo = "";
       try {
            String Str_Sql = "Select cac,codtarj from Selinlib.JCCentros where codtarj="+Codigo.trim();
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
   public String Equivalencia_Tarjetas(String Codigo){
       String XCodigo = "";

       try {
            String Str_Sql = "Select cac,codtarj from Selinlib.JCCentros where cac="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

            if (rs.next()) {
                XCodigo =rs.getString("codtarj");
            }
            rs.close();
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this," Class:JIngresos: Equivalencia_Tarjetas() "+e.getMessage()); 
       }

       return XCodigo;
   } 
    
   public void getDetalle_Pagos_Cac(){
       try{
           String Str_Sql = "SELECT fecha,codigo,cac,codcaja, valor FROM selinlib.jcacdet WHERE fecha ="+this.FechaIncial 
                            +" ORDER BY codigo asc , codcaja asc" ;
           ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
           this.Cabecera5[0] ="Codigo";
           this.Cabecera5[1] ="Cac";
           this.Cabecera5[2] ="Caja";
           this.Cabecera5[3] ="Valor";
           this.PosicionTabla5 = -1;

           while(rs.next()){
               this.PosicionTabla5++;
               Detalle5[this.PosicionTabla5][0] =rs.getInt("Codigo");
               Detalle5[this.PosicionTabla5][1] =rs.getString("cac");
               Detalle5[this.PosicionTabla5][2] =rs.getInt("CodCaja");        
               Detalle5[this.PosicionTabla5][3] =rs.getDouble("Valor");
           }
           rs.close();
           jTable5.setModel(new javax.swing.table.DefaultTableModel(Detalle5, Cabecera5));

           if(Integer.parseInt(this.FechaIncial)>=20190612){
              this.valor_pagado_empledos_efectivo= get_pago_efectivo_empleados(this.FechaIncial );
              this.jTextField41.setText(this.JFormato.format(valor_pagado_empledos_efectivo));
           }else{
               valor_pagado_empledos_efectivo = 0 ;
               this.jTextField41.setText(this.JFormato.format(valor_pagado_empledos_efectivo));
           }
           
       }catch(Exception e){
               JOptionPane.showMessageDialog(this," Class:JIngresos: getDetalle_Pagos_Cac "+e.getMessage()); 
       }
   } 
   public void setDetalle_Pagos_Cac(){
       try {
           boolean rx = false;

           for (int i = 0; i <= this.PosicionTabla5; i++) {
               String Str_Sql ="SELECT * FROM selinlib.jcacdet where fecha="+this.FechaIncial
                   + " and codigo="+jTable5.getValueAt(i,0).toString() + " and codcaja="+jTable5.getValueAt(i,2).toString();
               //System.out.println("Prueba de insercion "+Str_Sql);
               ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

               if(!rs.next()){
                    Str_Sql = "INSERT INTO selinlib.jcacdet (fecha,codigo,cac,codcaja,valor) "
                            + " VALUES ("+this.FechaIncial+","+jTable5.getValueAt(i,0).toString()+",'"+jTable5.getValueAt(i,1).toString()+"',"+jTable5.getValueAt(i,2).toString()+","+jTable5.getValueAt(i,3).toString()+")";
               }else{
                    Str_Sql = "UPDATE selinlib.jcacdet "
                            + "SET "
                            + "Valor = "+jTable5.getValueAt(i,3).toString()
                            + " WHERE fecha="+this.FechaIncial
                            + " and codigo="+jTable5.getValueAt(i,0).toString() + " and codcaja="+jTable5.getValueAt(i,2).toString();
               }
               rx= JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
           }
           
           if (rx) {
               JOptionPane.showMessageDialog(this,"Se guardo Correctamente Detalle Subsidios Pagados"); 
          }else{
               JOptionPane.showMessageDialog(this,"NO SE PUDO Guardar Correctamente Detalle"); 
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this,"Class:JIngresos:setDetalle_Pagos_Cac() "+e.getMessage()); 
       }
   }
   
   public void Agrupa_Centro_Atencion(){
       try {
           String SQL ="select codigo,DESCRIP  from selinlib.jccentros " ;
           ResultSet resultado = JBase_Datos.SQL_QRY(this.Cn,SQL);

           while(resultado.next()) {
               String Str_Sql = " SELECT FECHA , CODIGO , SUM(VALOR) as Valor "+                            
                                " FROM selinlib.jcacdet WHERE FECHA = "+this.FechaIncial
                               +" and Codigo="+resultado.getString("CODIGO")
                               + " GROUP BY CODIGO, FECHA";
                //System.out.println(" *************************************************** "+Str_Sql);
                ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

                if(rs.next()) {
                        String xFecha =  rs.getString("Fecha");
                        int xCodigo = rs.getInt("Codigo");
                        double xValor =rs.getDouble("Valor");
                        String xCentroAtencion = resultado.getString("DESCRIP");
                        setC_A_C(xFecha, xCodigo, xCentroAtencion,xValor);
                }else{
                    System.out.println(""+resultado.getString("CODIGO"));
                    System.out.println(""+resultado.getString("CODIGO"));
                    setC_A_C(this.FechaIncial, Integer.parseInt(resultado.getString("CODIGO")), resultado.getString("CODIGO"),0);
                }
                rs.close();
           }
           resultado.close();
           //this.getTable();
           this.setTable_2();
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this,"Class:JIngresos:Agrupa_Centro_Atencion() "+e.getMessage()); 
       }
   } 
   public boolean EliminaDetalle(){
       boolean rs = false;

       try {
          
           if(!jTable5.getValueAt(jTable5.getSelectedRow(),0).toString().trim().equals("")){
                String Str_Sql = "DELETE  FROM selinlib.jcacdet WHERE fecha="+this.FechaIncial+" "
                            +" and Codigo = "+jTable5.getValueAt(jTable5.getSelectedRow(),0).toString().trim()
                            +" and codcaja  ="+jTable5.getValueAt(jTable5.getSelectedRow(),2).toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                this.Agrupa_Centro_Atencion();           
           }

           if(rs){
                JOptionPane.showMessageDialog(this,"Eliminado Correctamente "); 
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this,"Class:JIngresos:EliminaDetalle() "+e.getMessage()); 
       }
       return rs;
   }
   public void setBorrarFila_tabla5(){
       try{ 
            
            if (EliminaDetalle()){
                DefaultTableModel Modelo =(DefaultTableModel) jTable5.getModel();
                Modelo.removeRow(jTable5.getSelectedRow());
                jTable5.updateUI();
                //this.PosicionTabla5 --;
            }
       }catch(Exception e){
           JOptionPane.showMessageDialog(this,"El registro seleccionado debe Contener informacion"); 
       }
    }
    public void AgregarCajaCentro(){
        try {
            this.Cabecera5[0]= "Codigo";
            this.Cabecera5[1]= "Cac";
            this.Cabecera5[2]= "Cajero";
            this.Cabecera5[3]= "Valor";
            this.PosicionTabla5++;
            if (this.PosicionTabla5<=50) {
                this.Detalle5[this.PosicionTabla5][0] = jComboBox5.getSelectedItem().toString().trim();
                this.Detalle5[this.PosicionTabla5][1] = this.getCentroEquivalente_Descripcion(jComboBox5.getSelectedItem().toString().trim());
                this.Detalle5[this.PosicionTabla5][2] = jComboBox6.getSelectedItem().toString().trim();
                this.Detalle5[this.PosicionTabla5][3] = jTextField2.getText().trim();
            }
            jTable5.setModel(new javax.swing.table.DefaultTableModel(Detalle5, Cabecera5));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:JIngresos:AgregarCajaCentro()"+e.getMessage()); 
        }
    }
    public void setSaldoActual(){
        try{
           String Centro = getCentroEquivalente(jComboBox1.getSelectedItem().toString().trim());
           String Str_Sql = "SELECT * FROM selinlib.jsaldos  Where FECHA ="+this.FechaFinal+" AND DE=99999999 and PARA = 99999999"
                   + " and codigocac ="+Centro;
           ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
           if(!rs.next()){
               Str_Sql  ="INSERT INTO selinlib.jsaldos (fecha,codigocac,de,para,valor) values "
                       + "("+this.FechaFinal+","+Centro+",99999999,99999999 , "+this.SaldoActualXCentroAtencion+")";
           }else{
               Str_Sql  ="UPDATE selinlib.jsaldos set "
                       + "valor= "+this.SaldoActualXCentroAtencion
                       + " where fecha="+this.FechaFinal
                       + " and de= 99999999 and para=99999999 "
                       + " and codigocac="+Centro;
           }
           boolean rt = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
           if(rt == true){
               JOptionPane.showMessageDialog(this,"Guardo Correctamente"); 
           }

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Class:JIngresos:getSaldoActual()"+e.getMessage()); 
        }
    }
    public void setBorrarFila(){
       try{ 
            String xDe = jTable4.getValueAt(jTable4.getSelectedRow(),0).toString();
            String xPara = jTable4.getValueAt(jTable4.getSelectedRow(),1).toString();
            this.setDeleteTable(xDe, xPara);
            DefaultTableModel Modelo =(DefaultTableModel) jTable4.getModel();
            Modelo.removeRow(jTable4.getSelectedRow());
            jTable4.updateUI();
            this.PosicionTabla4 --;
       }catch(Exception e){
           JOptionPane.showMessageDialog(this,"El registro seleccionado debe Contener informacion"); 
       }
    }
    public void getOperacionTabla(){
        try {
            SumaIngreso = 0; SumaTransferencia = 0; SumaComision =0; SumaCambioCheque = 0;
            
            for (int i = 0; i <= this.PosicionTabla4; i++) {
                if (jTable4.getValueAt(i,3)!= null){
                    double Vlr = Double.parseDouble(jTable4.getValueAt(i,2).toString());
                    if ( jTable4.getValueAt(i,3).toString().toString().trim().equals("Ingreso") ){
                        SumaIngreso = SumaIngreso + Vlr;
                    }
                    if ( jTable4.getValueAt(i,3).toString().toString().trim().equals("Transferencia") ){
                      if(! jComboBox1.getSelectedItem().toString().trim().equals(jTable4.getValueAt(i,0).toString().toString().trim())){  
                          SumaTransferencia = SumaTransferencia +Vlr;
                       }else{  
                          SumaIngreso = SumaIngreso - Vlr;
                       }
                    }
                    if ( jTable4.getValueAt(i,3).toString().toString().trim().equals("Comision") ){
                        SumaComision = SumaComision + Vlr;
                    }
                    if ( jTable4.getValueAt(i,3).toString().toString().trim().equals("Cambio Cheque") ){
                        SumaCambioCheque = SumaCambioCheque + Vlr;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:JIngresos:getOperacionTabla()"+e.getMessage()); 
        }
    }
    public void setClearTable(){
        try {
            Cabecera4[0]="De:";
            Cabecera4[1]="Para:";
            Cabecera4[2]="Valor";
            Cabecera4[3]="Observacion";
            for (int i = 0; i <= 49; i++) {
                Detalle4[i][0] = "";
                Detalle4[i][1] = "";
                Detalle4[i][2] = "";
                Detalle4[i][3] = "";
            }
            this.PosicionTabla4 = -1;
            jTable4.setModel(new javax.swing.table.DefaultTableModel(Detalle4, Cabecera4));
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Class:JIngresos: setClearTable()"+e.getMessage()); 
        }
    }
    public void setDeleteTable(String De , String Para){
        try {
            String Centro = getCentroEquivalente(jComboBox1.getSelectedItem().toString().trim());
            String ZDe = De, ZPara= null;
            if ( Integer.parseInt(De)<100){
                ZDe = this.getCentroEquivalente(De);
            }
            ZPara = this.getCentroEquivalente(Para);
            String Str_Sql = "DELETE FROM selinlib.jsaldos WHERE fecha="+this.FechaFinal+" AND codigocac ="+Centro+""
                    + " AND de="+ZDe+" AND para="+ZPara;
            boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            if(rs == true){
                JOptionPane.showMessageDialog(this,"Eliminado Correctamente"); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:JIngresos: setDeleteTable() "+e.getMessage()); 
        }
    }
    public void getMovimiento_CAC(){
        try {
            Cabecera4[0]="De:";
            Cabecera4[1]="Para:";
            Cabecera4[2]="Valor";
            Cabecera4[3]="Observacion";
            String Centro = getCentroEquivalente(jComboBox1.getSelectedItem().toString().trim());
            //Buscado Subsidios Pagados
            this.SubsidiosPagadosXCentroAtencion = this.getSubsidiosPagados(Centro);
            jTextField25.setText(JFormato.format(this.SubsidiosPagadosXCentroAtencion));
            
            String Str_Sql = "SELECT fecha,codigocac,de,para,valor,observa FROM selinlib.jsaldos where Codigocac = "+Centro +" AND Fecha ="+this.FechaFinal+""
                    + " and de <> 99999999";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.PosicionTabla4 = -1;
            while(rs.next()){
                this.PosicionTabla4 ++;
                int De = Integer.parseInt(rs.getString("de"));
                if(De>=100){
                    Detalle4[this.PosicionTabla4][0] = ""+De;
                }else{
                    Detalle4[this.PosicionTabla4][0] = this.getCentroEquivalenteUser(""+De);
                }
                Detalle4[this.PosicionTabla4][1] = this.getCentroEquivalenteUser(rs.getString("para"));
                Detalle4[this.PosicionTabla4][2] = rs.getDouble("valor");
                try{
                    Detalle4[this.PosicionTabla4][3] = rs.getString("observa");
                }catch(Exception e){}
            }
            rs.close();
            if(this.PosicionTabla4 != -1){
                jTable4.setModel(new javax.swing.table.DefaultTableModel(Detalle4, Cabecera4));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:getMovimiento_CAC()"+e.getMessage()); 
        }
    }
    public boolean ExistsCentro(String Centro, String De, String Para){
       boolean Sw = false;
       try{
            String Str_Sql = "SELECT Fecha FROM selinlib.jsaldos WHERE fecha ="+this.FechaFinal+""
                            +" AND CodigoCac="+Centro+ " AND de="+De+" AND para="+Para;;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                try{
                    int vlr = rs.getInt("Fecha");
                }catch(Exception e){}
                Sw =true;
            }
            rs.close();
       }catch(Exception e ){
           JOptionPane.showMessageDialog(this,"Class:JIngresos:ExistsCentro"+e.getMessage()); 
       }
      return Sw;
    }
    public boolean setSaldos_CAC(){
        boolean Guardo = false;
        String Str_Sql;
        String Centro = getCentroEquivalente(jComboBox1.getSelectedItem().toString().trim());
        try{
            String Celda = jTable4.getValueAt(0,0).toString();
            int Fila = -1;
            while(!Celda.trim().equals("")){
                Fila++;
                if (jTable4.getValueAt(Fila+1,0)!= null){
                     Celda = jTable4.getValueAt(Fila,0).toString();
                }else{
                    Celda ="";
                }
               if(!Celda.trim().equals("")){
                String CentroDe = null;
                if (Integer.parseInt(jTable4.getValueAt(Fila,0).toString().trim() )>=100){
                      CentroDe = jTable4.getValueAt(Fila,0).toString().trim();
                }else{
                  CentroDe = this.getCentroEquivalente(jTable4.getValueAt(Fila,0).toString().trim());
                }
                String CentroPara = this.getCentroEquivalente(jTable4.getValueAt(Fila,1).toString().trim());
                boolean Sw = ExistsCentro(Centro, CentroDe , CentroPara);               
                 
                if(Sw == false)   {    
                     Str_Sql = "INSERT INTO selinlib.jsaldos (fecha,codigocac,de,para, valor,observa) VALUES "
                             + " ("+this.FechaFinal+","+Centro+","+ CentroDe+","+ CentroPara+","+ jTable4.getValueAt(Fila,2).toString().trim()+",'"+jTable4.getValueAt(Fila,3).toString()+"')";
                }else{
                     Str_Sql = "UPDATE selinlib.jsaldos set "
                             + " valor="+jTable4.getValueAt(Fila,2).toString().trim()
                             + " ,observa= '"+jTable4.getValueAt(Fila,3).toString().trim()+"'"
                             + " where codigocac ="+Centro+ " AND fecha="+this.FechaFinal+""
                             + " AND de="+CentroDe+" AND para="+CentroPara;
                 } 
                 //Cambio de Cheque
                 if(CentroDe.trim().equals("102") && (jTable4.getValueAt(Fila,3).toString().trim().equals("Cambio Cheque")) ){
                   //DESARROLLO 1.9   
                     //this.crear_Movimiento(CentroDe, Centro, CentroDe, "Transferencia Cheque");
                 }
                 Guardo = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                }
            }
        }catch(Exception e ){
            JOptionPane.showMessageDialog(this,"Class:JIngresos:setSaldos_CAC()"+e.getMessage()); 
        }
        return Guardo;
    }
    public boolean crear_Movimiento(String CodigoPrincipal, String De, String Para , String Observacion){
        boolean Sw=false;
        try {
            String Str_Sql = "select * from selinlib.jsaldos where fecha="+this.FechaIncial
                            + " and Codigocac ="+CodigoPrincipal
                            + " and De="+De+"Para="+Para+" and OBSERVA='"+Observacion.trim()+"'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(!rs.next()){
                Str_Sql = "Insert";
            }else{
                Str_Sql = "Update";
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:JIngresos:crear_Movimiento(..) "+e.getMessage()); 
        }
        return Sw;
    }
    public double getSaldoAnterior_CAC(){
          double SaldoAnterior =0;
          String Centro = null;
          try {
              Centro = getCentroEquivalente(jComboBox1.getSelectedItem().toString().trim());
              String Str_Sql ="SELECT Valor FROM selinlib.jsaldos WHERE "
                             +" fecha ="+this.RestarDias_Saldos_CAC()+" AND de=99999999"
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
      public String RestarDias_Saldos_CAC(){
        int Dia = Integer.parseInt(this.FechaIncial.substring(6, 8));
        String zMes = this.FechaIncial.substring(4, 6);
        String zDia ="";
        int zAño = Integer.parseInt( this.FechaIncial.substring(0, 4));
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
                JOptionPane.showMessageDialog(this,"Class:JIngresos: RestarDias() "+e.getMessage());
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
    public void AgregaPosicion(){
        try {
            Cabecera4[0]="De:";
            Cabecera4[1]="Para:";
            Cabecera4[2]="Valor";
            Cabecera4[3]="Observacion";
            this.PosicionTabla4 ++;
            if(this.PosicionTabla4 < 50){
                if ( jComboBox2.getSelectedItem().toString().trim().equals("Ingreso") ){
                      Detalle4[this.PosicionTabla4][0] =jComboBox4.getSelectedItem().toString().trim();
                      Detalle4[this.PosicionTabla4][1] =jComboBox4.getSelectedItem().toString().trim();
                      Detalle4[this.PosicionTabla4][2] =""+jTextField23.getText();
                      Detalle4[this.PosicionTabla4][3] =jComboBox2.getSelectedItem().toString().trim();
                }else{
                      //Transferencia 
                      if ( jComboBox2.getSelectedItem().toString().trim().equals("Transferencia") ){
                        Detalle4[this.PosicionTabla4][0] =jComboBox4.getSelectedItem().toString().trim();
                        Detalle4[this.PosicionTabla4][1] =jComboBox3.getSelectedItem().toString().trim();
                        Detalle4[this.PosicionTabla4][2] =""+jTextField23.getText();
                        Detalle4[this.PosicionTabla4][3] =jComboBox2.getSelectedItem().toString().trim();
                      }else{
                         if ( jComboBox2.getSelectedItem().toString().trim().equals("Comision") ){
                            Detalle4[this.PosicionTabla4][0] ="101";
                            Detalle4[this.PosicionTabla4][1] =jComboBox3.getSelectedItem().toString().trim();
                            Detalle4[this.PosicionTabla4][2] =""+jTextField23.getText();
                            Detalle4[this.PosicionTabla4][3] =jComboBox2.getSelectedItem().toString().trim()+" ";
                        }else{
                             //Cambio de Cheque
                            Detalle4[this.PosicionTabla4][0] ="102";
                            Detalle4[this.PosicionTabla4][1] =jComboBox3.getSelectedItem().toString().trim();
                            Detalle4[this.PosicionTabla4][2] =""+jTextField23.getText();
                            Detalle4[this.PosicionTabla4][3] =jComboBox2.getSelectedItem().toString().trim();
                        }
                      }
                }
            }else{
                JOptionPane.showMessageDialog(this,"No se pueden Agregar mas transaccion comunicarce con el administrador del Aplicativo"); 
            }
            jTable4.setModel(new javax.swing.table.DefaultTableModel(Detalle4, Cabecera4));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:JIngresos: AgregaPosicion()"); 
        }
     }
    public String getCentroEquivalente_Descripcion(String Codigo){
       String XCodigo = null;
       try {
            String Str_Sql = "Select Codigo,descrip from Selinlib.JCCentros where Codigo="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getString("descrip");
            }
            rs.close();
        } catch (Exception e) {
           JOptionPane.showMessageDialog(this,"Class:JIngresos :getCentroEquivalenteUser "+e.getMessage()); 
        }
            return XCodigo;
    }
    
    
    public String getCentroEquivalenteUser(String Codigo){
       String XCodigo = null;
       try {
            String Str_Sql = "Select Cac,Codigo from Selinlib.JCCentros where Codigo="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getString("cac");
            }
            rs.close();
        } catch (Exception e) {
           JOptionPane.showMessageDialog(this,"Class:JIngresos :getCentroEquivalenteUser "+e.getMessage()); 
        }
            return XCodigo;
    }
    public String getCentroEquivalente(String Codigo){
       String XCodigo = null;
       try {
            String Str_Sql = "Select Cac,Codigo from Selinlib.JCCentros where Cac ="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getString("Codigo");
            }
            rs.close();
        } catch (Exception e) {
           JOptionPane.showMessageDialog(this,"Class:JIngresos :getCentroEquivalente "+e.getMessage()); 
        }
            return XCodigo;
    }
    public double getCentroAtencion(String Codigo){
        double Vlr =0;
        double Vlr74 =0; double Vlr30=0; double VlrSGym = 0; double VlrSNorte = 0;
        double Vlr82 =0;
        double VlrSViva = 0;
        String XCodigo = null;
        
        if(!Codigo.trim().equals("TOTAL ->")){
            XCodigo = this.getCentroEquivalente(Codigo);
        }else{
            XCodigo = Codigo;
        }
        
        if(XCodigo!= null){
            if(Codigo.trim().equals("48")){
                Vlr = this.TotalEfectivo;
                
                //Vlr74 = this.getCentroAtencion("74");
                //Vlr30 = this.getCentroAtencion("30");
                //Vlr82 = this.getCentroAtencion("82");
                //VlrSGym = this.getCentroAtencion("76");
                //VlrSNorte = this.getCentroAtencion("99");
                //VlrSViva = this.getCentroAtencion("55");
                
                double Vlr_Otras=0;
                try {
                    String Str_Sql ="select * from selinlib.JCCENTROS where Codigo<> 1 ";
                    ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                    while(rs.next()){
                        Vlr_Otras = Vlr_Otras + this.getCentroAtencion(rs.getString("cac"));
                    }
                    rs.close();
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Class:JIngresos: setCentroAtencion(String Codigo) "+e.getMessage());
                }
                
                
                //Vlr = Vlr - Vlr74-Vlr30-Vlr82-VlrSGym-VlrSNorte-VlrSViva;
                
                Vlr = Vlr - Vlr_Otras;
                
            }else{
                for (int i = 0; i < this.CentroAtenciones.size(); i++) {
                    //System.out.println(""+XCodigo+" ---  "+Vlr);
                    if (this.CentroAtenciones.elementAt(i).toString().trim().equals(XCodigo.trim())){
                        Vlr = Double.parseDouble(this.VlrCentroAtenciones.elementAt(i).toString());
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(this,"Codigo del Centro de Atencion no Existe"); 
        }
        return Vlr;
    }
    public  void getEfectivo_CAC(){
        try {
            /*Pendiente modificar el Sql 
              que incluya los nuevos centros de atencion que se abran
            */
            String AnoMes = this.FechaIncial.substring(0, 6);
            AnoMes = AnoMes+"01";
            /* Se modifico por inclusion de la tabla maestra de centro
               de costos.
            */
            /*String Str_Sql = " SELECT T02.cencod , SUM(PLEFSU)"+        
                            " FROM recaudos.LAPORT1 T01, "+                          
                            " recaudos.CENTRO t02 "+                                 
                            " WHERE T01.CENCOD =  T02.CENCOD "+                       
                            " AND PLAFEI "+               
                            " = "+this.FechaFinal+" AND T01.CENCOD IN(1 , 7, 14, 18, 45, 52, 38,55 ) "+      
                            " GROUP BY T02.CENCOD , T02.CENNOM "+                     
                            " ORDER BY 1 ASC ";  
            */
            String Str_Sql = "SELECT T02.cencod , SUM(PLEFSU) FROM recaudos.LAPORT1 T01, \n" +
                             " recaudos.CENTRO t02 , selinlib.jccentros                   \n" +
                             " WHERE T01.CENCOD =  T02.CENCOD  AND PLAFEI  = " +this.FechaFinal+
                             " AND T01.CENCOD = codigo  GROUP BY                          \n" +
                             " T02.CENCOD , T02.CENNOM  ORDER BY 1 ASC                    ";
            //System.out.println("***** Ingresos en efectivo "+Str_Sql);
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while (rs.next()) {
                try{    
                    this.CentroAtenciones.add(""+rs.getInt(1));
                }catch(Exception e){}
                try{
                    this.VlrCentroAtenciones.add(""+rs.getDouble(2));
                }catch(Exception e ){}
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:JIngresos :getEfectivo_CAC "+e.getMessage());
        }
    }
   
    public void getIngresos(){
        try{
            String StrSql = "SELECT * FROM SELINLIB.JINGRESO "+
                            " WHERE FECHA ="+this.FechaIncial;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            
            if(rs.next()){    
                
               jTextField1.setText(""+rs.getDouble("VTAGNB"));
               jTextField3.setText(""+rs.getDouble("ABNRET"));
               xVentaRetenida =  rs.getInt("VTARET");
               
               //if(xVentaRetenida>=100000){
               //  jTextField4.setText(""+JFormato.format( xVentaRetenida ) );
               //}else{
                 //System.out.println("Valor con problema "+xVentaRetenida);
                 jTextField4.setText(""+xVentaRetenida  );   
               //}
               jTextField5.setText(""+rs.getDouble("CMBCHEQ"));
               jTextField28.setText(""+rs.getDouble("CHERET"));
               this.xCajeConsignar = rs.getDouble("CNJCONSI");
               if(this.xCajeConsignar>=1000000){
                 jTextField6.setText(""+JFormato.format(this.xCajeConsignar));
               }else{
                 jTextField6.setText(""+this.xCajeConsignar);   
               }
               jTextField7.setText(""+rs.getDouble("CCMBCHEQ"));
               jTextField8.setText(""+rs.getDouble("CHQPCONS"));
               
              // if (xVentaRetenida >= 1000000){
              //    jTextField11.setText(""+JFormato.format( xVentaRetenida ) );
              // }else{
                  jTextField11.setText(""+ xVentaRetenida );   
              // }
              
               this.xValorAbonado = rs.getDouble("VVLRABONAD");
                
               //this.xValorAbonado=this.xValorAbonado+this.valor_pagado_empledos_efectivo;
               if(this.xValorAbonado >= 1000000){
                    jTextField12.setText(""+(this.xValorAbonado));
                    System.out.println(" ****  ***  "+xValorAbonado);
               }else{
                   jTextField12.setText(""+(this.xValorAbonado));
                   System.out.println(" ****  +++  "+xValorAbonado);
               }
                
               jTextField15.setText(""+rs.getDouble("CVLRRETENI"));
               jTextField16.setText(""+rs.getDouble("CVLRABONAD"));

               
               this.CalculaEfectivoConsigna();
               this.CalculaCanjeEsperado();
               this.CalculoVentaRetenida ();
               this.CalculoCanjeRetenido();
               jButton1.setEnabled(true);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(this,"Class:Jingresos: getIngresos() -----  "+e.getMessage());
        }
    }
    
    public double getSubsidiosPagados(String PCodigo){
        double Vlr = 0;
        /*for (int i= 0; i<jTable2.getRowCount();i++){
            if ( jTable2.getValueAt(i, 0).toString().trim().equals(PCodigo)){
                Vlr = Double.parseDouble( jTable2.getValueAt(i, 2).toString() );
            }
        }*/
        try {
            String Str_Sql =" select * from selinlib.jcac  \n" +
                            " where fecha ="+this.FechaIncial+" and codigo="+PCodigo;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                  Vlr= rs.getDouble("valor");
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:Jingresos: getSubsidiosPagados(String PCodigo) -----  "+e.getMessage());
        }
        return Vlr;
    }
    
    public boolean setTable_2(){
            boolean Control=false;
            try {
                String Str_Sql ="SELECT Codigo, Cac, Valor,PAGOS_EFE  FROM SELINLIB.JCAC WHERE FECHA ='"+this.FechaFinal+"'" ;
                ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                ZFila.clear();
                while(rs.next()){
                    
                    ZDet = new Vector();
                    String Codigo =rs.getString("codigo");
                    ZDet.add(Codigo);
                    ZDet.add(rs.getString("Cac"));
                    double Vlr = rs.getDouble("Valor");
                    double Vlr2 = this.getSubsidios_Pagados_Sistema_Pos(Codigo);
                    ZDet.add(""+this.JFormato.format(Vlr));
                    ZDet.add(""+this.JFormato.format(Vlr2));
                    ZDet.add(""+this.JFormato.format((Vlr-Vlr2)));
                    ZFila.add(ZDet);
                    if(Codigo.trim().equals("1")){
                       this.valor_pagado_empledos_efectivo = rs.getDouble("PAGOS_EFE");
                    }
                    Control=true;
                }
                this.jTable2.setModel(new javax.swing.table.DefaultTableModel(ZFila, ZCab));
                rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Class:JIngresos: setTable_2() "+e.getMessage());  
        }
        return Control;
    }
//    public void getTable(){
//        boolean Sw = false;
//        for (int i= 0; i<jTable2.getRowCount();i++){
//            String Codigo = jTable2.getValueAt(i, 0).toString();
//            Sw = this.getC_A_C(Codigo,i);
//        }
//        if(Sw){
//            jTable2.setModel(new javax.swing.table.DefaultTableModel(Detalle2, Cabecera2));
//        }
//    }
//    public boolean getC_A_C(String Codigo, int Fila2){
//        boolean Sw = false;
//        try{
//            String StrSql = "SELECT Codigo, Cac, Valor  FROM SELINLIB.JCAC WHERE FECHA ='"+this.FechaIncial+"'"
//                    + " AND CODIGO ="+Codigo;
//            
//            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
//            Cabecera2[0] = "Codigo";
//            Cabecera2[1] = "Centro Atencion";
//            Cabecera2[2] = "Valor";
//            Cabecera2[3] = "Valor Esperado";
//            Cabecera2[4] = "Diferencia";
//            
//            if(rs.next()){     
//            
//                
//                Detalle2[Fila2][0] = Codigo;
//            
//                Detalle2[Fila2][1] = rs.getString("Cac");
//            
//                double Vlr = rs.getDouble("Valor");
//                Detalle2[Fila2][2] = ""+Vlr;
//            
//                double Vlr2 = this.getSubsidios_Pagados_Sistema_Pos(Codigo);
//                
//                Detalle2[Fila2][3] = JFormato.format(Vlr2);
//                Detalle2[Fila2][4] = JFormato.format(Vlr-Vlr2);
//                Sw=true;
//            }  
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(this,"Form:JIngresos: getC_A_C()"+e.getMessage());
//        }
//        return Sw;
//    }
    public boolean setC_A_C(String Fecha, int Codigo,String CentroAtencion, double Valor){
        boolean SQL = false;
        try{
            String StrSql ="SELECT * FROM SELINLIB.JCAC WHERE FECHA ='"+this.FechaIncial+"'"
                    + " AND CODIGO ="+Codigo;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            //System.out.println("Set_CAC_Cabecera  "+StrSql);
            if(!rs.next()){
                if((Integer.parseInt(this.FechaIncial)>=20190612)&&(Codigo==1)){
                    StrSql ="INSERT INTO SELINLIB.JCAC (FECHA,CODIGO, CAC, VALOR, PAGOS_EFE)"+
                       "VALUES("+Fecha+","+Codigo+",'"+CentroAtencion+"',"+Valor+","+this.valor_pagado_empledos_efectivo+")"; 
                }else{
                    StrSql ="INSERT INTO SELINLIB.JCAC (FECHA,CODIGO, CAC, VALOR)"+
                       "VALUES("+Fecha+","+Codigo+",'"+CentroAtencion+"',"+Valor+")"; 
                }
               
            }else{
                
                if((Integer.parseInt(this.FechaIncial)>=20190612)&&(Codigo==1)){
                        StrSql ="UPDATE SELINLIB.JCAC "
                            +"SET VALOR ="+Valor
                            +" , CAC ='"+CentroAtencion+"' "
                            + ", PAGOS_EFE="+this.valor_pagado_empledos_efectivo
                            +" WHERE FECHA ='"+this.FechaIncial+"' AND Codigo="+Codigo;   
                        //System.out.println(" ******************************************** ++++++++++++++++++++++++++++ "+StrSql);
                    
                }else{
                    StrSql ="UPDATE SELINLIB.JCAC "
                        +"SET VALOR ="+Valor
                        +" , CAC ='"+CentroAtencion+"' "
                        +" WHERE FECHA ='"+this.FechaIncial+"' AND Codigo="+Codigo;   
                }
            }
            //System.out.println(""+StrSql);
            SQL = JBase_Datos.Exc_Sql(this.Cn,StrSql);
            System.out.println("ACA");
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Class:JIngresos setC_A_C() "+e.getMessage());
        }
        return SQL ;
    }
    public void buscarSaldoAnterior(){
        try{
            String StrSql = "SELECT FECHA,SALACTUAL,  CALACTUAL FROM SELINLIB.JINGRESO WHERE FECHA ='"+this.RestarDias()+"'";
            //m.out.println(""+StrSql);
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            if(rs.next()){
                this.SaldoAnteriorVentasRetenidas = rs.getDouble("SALACTUAL");
                this.SaldoAnteriorCanjeRetenidas  = rs.getDouble("CALACTUAL");
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(this,"Class:JIngresos buscarSaldoAnterior() "+e.getMessage());
        }
        jTextField10.setText(JFormato.format(this.SaldoAnteriorVentasRetenidas));
        jTextField14.setText(JFormato.format(this.SaldoAnteriorCanjeRetenidas));
    }
    public String RestarDias(){
        int Dia = Integer.parseInt(this.FechaIncial.substring(6, 8));
        String zMes = this.FechaIncial.substring(4, 6);
        String zDia ="";
        int zAño = Integer.parseInt( this.FechaIncial.substring(0, 4));
        if ( Dia  ==  1){
            zMes = this.getMes(zMes);
            if(Integer.parseInt(zMes)== 12){
                zAño = zAño -1;
            }//else{
         
                    try {
                        String Str_Sql = " select   max(substring( fecha , 7 ,2 )) as dia "+
                                 " from selinlib.jingreso   "+
                                 " where substring(fecha , 5 , 2) = "+zMes+" AND substring( fecha , 1 ,4 )="+zAño ;
                        //System.out.println("SQL_REVISAR"+Str_Sql);
                        ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                        if(rs.next()){
                            zDia = rs.getString("dia");
                            Dia = Integer.parseInt(zDia);
                        }
                        rs.close();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this,"Class:JIngresos: RestarDias() "+e.getMessage());
                    }
              
            //}
        }else{
            Dia = Dia - 1 ;
         }
        
        if (Dia<10){
            zDia = "0"+String.valueOf(Dia);
        }else{
            zDia = String.valueOf(Dia);
        }
        //System.out.println("ESTA ES LA FECHA "+zAño+zMes+zDia);
        return zAño+zMes+zDia;
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
    public void getSubsidioPagados(int Validar){
        TotalSubsidiosPagados = 0;
        /*for (int i= 0; i<jTable2.getRowCount();i++){
           TotalSubsidiosPagados = TotalSubsidiosPagados+ Double.parseDouble(jTable2.getValueAt(i, 2).toString().trim()); 
        }*/
        try {
            String Str_Sql=" select sum(valor) as valor      from selinlib.jcac     " +
                           " where fecha = " +this.FechaIncial;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                TotalSubsidiosPagados = rs.getDouble("valor");
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showConfirmDialog(this, "Class:JIngresos:getSubsidioPagados"+e.getMessage());
        }
        
        if(Integer.parseInt(this.FechaIncial)>=20190612){
            this.xValorAbonado = this.TotalSubsidiosPagados + this.valor_pagado_empledos_efectivo;
        }else{
           this.xValorAbonado = this.TotalSubsidiosPagados; 
        }
        
        //jTextField12.setText(""+this.JFormato.format(this.TotalSubsidiosPagados));
        jTextField12.setText(""+this.JFormato.format(xValorAbonado));
        System.out.println(" *************** ultimo ");
    }
    
    public void getSubSidioPagados_2(){
        try {
            String Str_Sql="select sum(valor) as valor from selinlib.jcac " +
                           " where fecha ="+this.FechaIncial;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double valor=0;
            if(rs.next()){
                valor= rs.getDouble("valor");
            }
            
            if(Integer.parseInt(this.FechaIncial)>=20190612){
                this.valor_pagado_empledos_efectivo = get_pago_efectivo_empleados(this.FechaIncial);
                this.xValorAbonado =valor+this.valor_pagado_empledos_efectivo;
            }else{
                this.valor_pagado_empledos_efectivo =0;
                this.xValorAbonado =valor;
            }
            
            jTextField12.setText(""+this.JFormato.format( this.xValorAbonado));
            System.out.println(" 44444444 ");
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showConfirmDialog(this, "Class:JIngresos:getSubSidioPagados_2() "+e.getMessage());
        }
    }
    
    public void setControlIngreso(){
        try{
            String StrSql = "SELECT * FROM SELINLIB.JINGRESO WHERE fecha = '"+this.FechaIncial+"'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            try{
                xVentaRetenida = Double.valueOf(jTextField4.getText());
            }catch(Exception e ){
                javax.swing.JOptionPane.showMessageDialog(this, "class:JIngresos:setControlIngreso() "+e.getMessage() );
            }
            try{
                this.xValorAbonado = Double.valueOf(jTextField12.getText());
            }catch(Exception e){}
            try{
                this.xCajeConsignar = Double.valueOf(jTextField6.getText());
            }catch(Exception e ){}
            
            
            if(!rs.next()){
                StrSql = "INSERT INTO SELINLIB.JINGRESO "+
                         "(FECHA,CHERET,EFTCONS,VTAGNB,ABNRET,VTARET,CMBCHEQ,CNJESPER,CNJCONSI,CCMBCHEQ,CHQPCONS, "+
                         " SALANTVENT,VVLRRETENI,VVLRABONAD,SALACTUAL,SALANTCANJ,CVLRRETENI,CVLRABONAD,CALACTUAL ) "+
                         " VALUES ('"+this.FechaIncial+"','"+jTextField28.getText().trim()+"' ,'"+
                         this.TotalEfectivo+"','"+jTextField1.getText().trim()+"','"+jTextField3.getText().trim()+"','"+xVentaRetenida+"','"+jTextField5.getText().trim()+"','"+
                         this.TotalCanje+"','"+jTextField6.getText().trim()+"','"+jTextField7.getText().trim()+"','"+jTextField8.getText().trim()+"','"+
                         this.SaldoAnteriorVentasRetenidas+"','"+this.xVentaRetenida+"','"+this.xValorAbonado+"','"+this.SaldoActualVentasRetenidas+"','"+
                         this.SaldoAnteriorCanjeRetenidas+"','"+this.CanjeNominaRetenida+"','"+this.jTextField3.getText().trim()+"','"+this.SaldoActualCanje+"')";
            }else{
                StrSql = "UPDATE SELINLIB.JINGRESO "
                         +"SET " 
                         +"CHERET='"+jTextField28.getText().trim()+"'"
                         +",EFTCONS  ='"+this.TotalEfectivo+"' "
                         +",VTAGNB  ='"+jTextField1.getText().trim()+"' "
                         +",ABNRET  ='"+jTextField3.getText().trim()+"' "
                         +",VTARET  ='"+xVentaRetenida+"' "
                         +",CMBCHEQ ='"+jTextField5.getText().trim()+"' "
                        
                         +",CNJESPER='"+this.TotalCanje+"' "
                         +",CNJCONSI='"+this.xCajeConsignar+"' "
                         +",CCMBCHEQ='"+jTextField7.getText().trim()+"' "
                         +",CHQPCONS='"+jTextField8.getText().trim()+"' "
                        
                         +",SALANTVENT='"+this.SaldoAnteriorVentasRetenidas+"' "
                         +",VVLRRETENI='"+xVentaRetenida+"' "
                         +",VVLRABONAD='"+this.xValorAbonado+"' "
                         +",SALACTUAL ='"+this.SaldoActualVentasRetenidas+"' "
                        
                         +",SALANTCANJ='"+this.SaldoAnteriorCanjeRetenidas+"' "
                         +",CVLRRETENI='"+this.CanjeNominaRetenida+"' "
                         +",CVLRABONAD='"+jTextField16.getText().trim()+"' "
                         +",CALACTUAL ='"+this.SaldoActualCanje+"' " 
                         
                         +" WHERE FECHA ='"+this.FechaIncial+"'";
                        
            }
            boolean SQL = JBase_Datos.Exc_Sql(this.Cn,StrSql);
            if (SQL){
                JOptionPane.showMessageDialog(this,"Se Guardo Correctamente");
            }else{
                JOptionPane.showMessageDialog(this,"No se pudo Guardar Correctamente");
            }
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Class: Jingresos: setControlIngreso()"+e.getMessage());
        }
    }
    public void CalculoCanjeRetenido(){
        double VlrRetenido = 0,VlrAbonado = 0;
        if (!jTextField15.getText().trim().equals("")){ 
            try{
                if (Double.parseDouble(jTextField15.getText().trim())>1000000){
                    VlrRetenido = Double.parseDouble(jTextField15.getText().trim());
                }else{
                    VlrRetenido = this.CanjeNominaRetenida;
                }
            }catch(Exception e ){
                VlrRetenido = this.CanjeNominaRetenida;
            }
        }
        if (!jTextField16.getText().trim().equals("")){ 
            try{
                VlrAbonado = Double.parseDouble(jTextField16.getText().trim());
                
            }catch(Exception e ){
                VlrAbonado = 0;
            }
        }
        this.SaldoActualCanje = this.SaldoAnteriorCanjeRetenidas + VlrRetenido-VlrAbonado ;
        jTextField17.setText(JFormato.format(this.SaldoActualCanje));
    }
    public void CalculoVentaRetenida (){
       double VlrRetenido = 0, VlrAbonado = 0;
       if (!jTextField11.getText().trim().equals("")){
            try{
                
                xVentaRetenida = Double.valueOf(jTextField11.getText().trim());
                
            }catch(Exception e){
                javax.swing.JOptionPane.showMessageDialog(this,"Class:JIngresos;CalculoVentaRetenida ():jTextField11.getTex() "+ e.getMessage());
            }
            VlrRetenido = xVentaRetenida;
            //javax.swing.JOptionPane.showMessageDialog(this," Calculo Venta Retenida "+ jTextField11.getText().trim());
       }
       if (!jTextField12.getText().trim().equals("")){
            try{
                this.xValorAbonado = Double.parseDouble(jTextField12.getText().trim());
                //System.out.println("PRUEBA..... 999 "+jTextField12.getText().trim());
            }catch(Exception e ){
               //javax.swing.JOptionPane.showMessageDialog(this," Class:JIngresos; Calculo Venta Retenida():jTextField12.getText()"+ e.getMessage());
            }
            VlrAbonado = xValorAbonado;
       }
       
       this.SaldoActualVentasRetenidas = this.SaldoAnteriorVentasRetenidas + VlrRetenido - VlrAbonado;
       jTextField13.setText(""+JFormato.format(this.SaldoActualVentasRetenidas));   
       
    }
    public void CalculaEfectivoConsigna(){
        double CambioCheque = 0 ,EfectivoGnb=0, AbonosVentasRetenidas=0, VentasRetenidas=0, SobranteFaltante = 0, EfectivoPorConsignar = 0;
        if (!jTextField1.getText().trim().equals("")){
            EfectivoGnb = Double.parseDouble(jTextField1.getText().trim());
        }
        if (!jTextField3.getText().trim().equals("")){
            AbonosVentasRetenidas = Double.parseDouble(jTextField3.getText().trim());
        }
        if (!jTextField4.getText().trim().equals("")){
            try{
                xVentaRetenida = Double.parseDouble(jTextField4.getText().trim());
            }catch(Exception e){}
            VentasRetenidas =  xVentaRetenida;
        }
        if (!jTextField5.getText().trim().equals("")){
            CambioCheque = Double.parseDouble(jTextField5.getText().trim());
        }
        if(!jTextField28.getText().trim().equals("")){
            ChequeCambiadoRetenido = Double.parseDouble(jTextField28.getText().trim());
        }
        SobranteFaltante = this.TotalEfectivo - EfectivoGnb-AbonosVentasRetenidas-VentasRetenidas-CambioCheque-ChequeCambiadoRetenido;
        jTextField20.setText(""+JFormato.format(SobranteFaltante));
        //jTextField11.setText(""+jTextField4.getText());
        //jTextField11.setText(""+ JFormato.format( xVentaRetenida ));
        jTextField11.setText(""+ (xVentaRetenida) );
        double Suma = CambioCheque +ChequeCambiadoRetenido;
        jTextField7.setText(""+Suma);
    }
    public void CalculaCanjeEsperado(){
        double CanjeRecaudado = 0 , CambioCheque=0, ChequePendienteConsignar = 0;
        if (!jTextField6.getText().trim().equals("")){
            try{
                this.xCajeConsignar = Double.valueOf(jTextField6.getText());
            }catch(Exception e ){}
            CanjeRecaudado = this.xCajeConsignar;
            
        }
        if (!jTextField7.getText().trim().equals("")){
            CambioCheque = Double.parseDouble(jTextField7.getText().trim());
        }
        if (!jTextField8.getText().trim().equals("")){
            ChequePendienteConsignar = Double.parseDouble(jTextField8.getText().trim());
        }
        CanjeNominaRetenida = this.TotalCanje -CanjeRecaudado+CambioCheque-ChequePendienteConsignar;
        jTextField9.setText(""+JFormato.format(CanjeNominaRetenida));
        jTextField15.setText(jTextField9.getText());
   }
   public void getInformacion(){
        try{
            String StrSql = "SELECT T02.CENCOD , T02.CENNOM , SUM(PLEFSU) "+
                             " FROM recaudos.LAPORT1 T01,  recaudos.CENTRO t02 "+
                             " WHERE T01.CENCOD =  T02.CENCOD  "+
                             " AND PLAFEI >= '"+this.FechaIncial+"' AND PLAFEI <= '"+this.FechaFinal+"'"+
                             " GROUP BY T02.CENCOD , T02.CENNOM ORDER BY 1 ASC";
            //System.out.println(" *********************** "+StrSql);
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            while(rs.next()){
               String CodCentro ="";
               try{
                CodCentro     = rs.getString(1);
               }catch(Exception e){}
               String NombreCentro ="";
               try{
                 NombreCentro  = rs.getString(2);
               }catch(Exception e){}
               double Vlr = 0;
               try{
                 Vlr           = rs.getDouble(3);
               }catch(Exception e){}
               this.TotalEfectivo = this.TotalEfectivo + Vlr;
               int Posicion = Buscar(CodCentro);
               if (Posicion!= -1){
                 this.Detalle [Posicion][2] = JFormato.format(Double.parseDouble(""+Vlr));
               }else{
                 Insertar(CodCentro,NombreCentro,Vlr,2);
               }
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(this, "Class:JIngreso getInformacion() " +e.getMessage());
        }
        this.getCajeChequeNow();
        this.getCajeCheque();
        
        Fila = Fila + 1 ;
        this.Detalle [Fila][0] = "TOTAL -> " ;
        this.Detalle [Fila][2] = JFormato.format(this.TotalEfectivo) ;
        this.Detalle [Fila][3] = JFormato.format(this.TotalCanje) ;
        this.Detalle [Fila][4] = JFormato.format(this.TotalConsolidadoEfectivo);
        this.Detalle [Fila][5] = JFormato.format(this.TotalConsolidadoCanje) ;
        jTable1.setModel(new javax.swing.table.DefaultTableModel(Detalle, Cabecera));
        jTextField18.setText(""+JFormato.format(this.TotalEfectivo));
        jTextField19.setText(""+JFormato.format(this.TotalCanje));
    }
     public void getConsolidado(){
        try{
            TotalEfectivo = 0;
            TotalConsolidadoEfectivo = 0;
            TotalCanje = 0;
            TotalConsolidadoCanje = 0;
            
            String AnoMes = this.FechaIncial.substring(0, 6);
            AnoMes = AnoMes+"01";
            this.FechaMes = AnoMes;
            this.Cabecera[0]="Codigo";
            this.Cabecera[1]="Punto de Venta";
            this.Cabecera[2]="Efectivo";
            this.Cabecera[3]="Canje";
            this.Cabecera[4]="Consolidado Efectivo";
            this.Cabecera[5]="Consolidado Caje";
            String StrSql = "SELECT T02.CENCOD , T02.CENNOM , SUM(PLEFSU) "+
                             " FROM recaudos.LAPORT1 T01,  recaudos.CENTRO t02 "+
                             " WHERE T01.CENCOD =  T02.CENCOD  "+
                             " AND PLAFEI >= '"+AnoMes+"' AND PLAFEI <= '"+this.FechaFinal+"'"+
                             " GROUP BY T02.CENCOD , T02.CENNOM ORDER BY 1 ASC ";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            Fila = -1;
            while(rs.next()){
               Fila ++;
               String CodCentro     = rs.getString(1);
               String NombreCentro  = rs.getString(2);
               double Vlr           = rs.getDouble(3);
               this.TotalConsolidadoEfectivo = this.TotalConsolidadoEfectivo+Vlr;
               this.Detalle [Fila][0] = CodCentro;
               this.Detalle [Fila][1] = NombreCentro;
               this.Detalle [Fila][4] = JFormato.format(Double.parseDouble(""+Vlr));
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(this,"Class:JIngresos: getConsolidado()"+ e.getMessage());
        }
        this.getInformacion();
    }
    public void Insertar(String CodCentrox, String NombreCentrox, double Vlr,int Pos){
           Fila++;
           this.Detalle [Fila][0] = CodCentrox;
           this.Detalle [Fila][1] = NombreCentrox;
           this.Detalle [Fila][Pos] = JFormato.format(Double.parseDouble(""+Vlr));
    }
    public int Buscar(String CodigoCentro){
        int Posicion = -1;
        int i=0; 
         while (i<= Fila){
            if(this.Detalle[i][0].toString().trim().equals(CodigoCentro.trim())){
                Posicion = i;
                i = Fila + 10;
            }
            i++;
        }
        return Posicion;
    }
    public void getCajeChequeNow(){
        try{
            String StrSql ="SELECT T02.CENCOD , T02.CENNOM , SUM(PLCHSU) FROM RECAUDOS.LAPORT1 T01 "+
                           ",RECAUDOS.CENTRO T02  "+
                           "WHERE T01.CENCOD =  T02.CENCOD   AND "+ 
                           "PLAFEI = '"+this.FechaIncial +"' "+
                           "GROUP BY T02.CENCOD ,T02.CENNOM";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            while(rs.next()){
               String CodCentro     = rs.getString(1);
               String NombreCentro  = rs.getString(2);
               double Vlr           = rs.getDouble(3);
               this.TotalCanje =  this.TotalCanje +Vlr;
               int Posicion = Buscar(CodCentro);
               if (Posicion!= -1){
                  this.Detalle [Posicion][3] = JFormato.format(Double.parseDouble(""+Vlr));
               }else{
                 Insertar(CodCentro,NombreCentro,Vlr,3);
               }
            }
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Class:JIngresos:getCajeChequeNow() "+e.getMessage());
        }
    }
    public void getCajeCheque(){
        try{
            String StrSql ="SELECT T02.CENCOD , T02.CENNOM , SUM(PLCHSU) FROM RECAUDOS.LAPORT1 T01 "+
                           ",RECAUDOS.CENTRO T02  "+
                           "WHERE T01.CENCOD =  T02.CENCOD   AND "+ 
                           "PLAFEI>= '"+this.FechaMes+"' AND PLAFEI <= '"+this.FechaIncial +"' "+
                           "GROUP BY T02.CENCOD ,T02.CENNOM";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            while(rs.next()){
               String CodCentro     = rs.getString(1);
               String NombreCentro  = rs.getString(2);
               double Vlr           = rs.getDouble(3);
               this.TotalConsolidadoCanje = this.TotalConsolidadoCanje + Vlr;
               int Posicion = Buscar(CodCentro);
               if (Posicion!= -1){
                  this.Detalle [Posicion][5] = JFormato.format(Double.parseDouble(""+Vlr));
               }else{
                 Insertar(CodCentro,NombreCentro,Vlr,5);
               }
            }
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Class:JIngresos:getCajeCheque() "+e.getMessage());
        }
    }
    
    public double get_pago_efectivo_empleados(String p_fecha){
        double valor = 0;
        try {
            String Str_Sql=" select SUM(VAGEMO) as valor from weblib.maoref  \n" +
                           " where FEENEF='"+p_fecha+"'" +
                           " and ESOREF='E'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                valor = rs.getDouble("valor");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Class:JIngresos:get_efectivo_empleados() "+e.getMessage());
        }
        return valor;
    }
    
    
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
            java.util.logging.Logger.getLogger(JIngresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JIngresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JIngresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JIngresos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JIngresos( null, null, "", "","").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTxtReciboCajas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox10;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox9;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
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
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTxtReciboCajaOtros;
    // End of variables declaration//GEN-END:variables
}
