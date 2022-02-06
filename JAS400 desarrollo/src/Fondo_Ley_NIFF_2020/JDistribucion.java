/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * Desarrollado por: Garyn Carrillo Caballero, Auditor de Sistemas.
 * Created on 2/05/2015, 07:52:03 AM
 */
package Fondo_Ley_NIFF_2020;

import Fondo_ley_NIIF.*;
import FondosLey.*;
import BD_As400.JConection;
import java.io.File;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;



/**
 *
 * @author Garyn Carrillo
 */

public class JDistribucion extends javax.swing.JFrame {
    SimpleDateFormat d = new SimpleDateFormat("yyyyMMdd");
    /**
     * Creates new form JDistribucion
     */
    
    private double DebAgr, CreAgr;
    private String FechaIncial , FechaFinal;
    private String FechaMes;
    private JConection JBase_Datos;
    private Connection Cn;
    private String Usuario;

    private Vector Cabecera;
    private Vector Fila;
    private Vector Detalle;


    private Vector cabeceraTipo;
    private Vector filaTipo;
    private Vector detalleTipo;


    private Vector CabBanco;
    private Vector FilaBanco;
    private Vector DetBanco;

    private Vector CabBancoInsert;
    private Vector FilaBancoInsert;
    private Vector DetBancoInsert;

    private Vector CabConciliacion;
    private Vector FilaConciliacion;
    private Vector DetConciliacion;


    private String FechaUsuario;
    private String PPeriodo;
    private String NumeroFormato = "##############";
    private String NumeroFormatoPeriodo = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private DecimalFormat JFormatoPeriodos ;
    private double TotalIngresos;

    private boolean CTabla1, CTabla2;

    private Vector Cab_CDTs;
    private Vector Det_CDTs;
    private Vector Fila_CDTs;

    private double DebFin=0;
    private double CredFin=0;

    private double DebCon=0;
    private double CredCon=0;

    private double NetoSistema=0;
    private double NetoCalculado=0;
    private Vector cabSaldo;
    private Vector detSaldoInsert;
    private Vector filaSaldoInsert;
    
    private Vector cabAportes;
    private Vector detAportes;
    private Vector detAportesInsert;
    private Vector filaAportesInsert;
    private Vector filaAportes;
     
    public JDistribucion(JConection JBase_Datos3, Connection Cn2, String Usuariox, String PPeriodo) {
        
        this.Cab_CDTs = new Vector();
        Det_CDTs = new Vector();
        CTabla1 =  CTabla2=false;
        java.util.Date ahora = new java.util.Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd");
        FechaUsuario =formateador.format(ahora);
        
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.Usuario = Usuariox;
        this.PPeriodo =PPeriodo;
        Cabecera = new Vector();
        Fila = new Vector();
        
        this.CabBanco = new Vector();
        this.FilaBanco = new Vector();
        
        this.CabBancoInsert = new Vector();
        this.FilaBancoInsert = new Vector();
        
        
        this.CabConciliacion = new Vector();
        this.FilaConciliacion = new Vector(); 
        
        this.getCabecera();
        JFormato= new DecimalFormat(NumeroFormato);
        JFormatoPeriodos = new DecimalFormat(NumeroFormatoPeriodo);
        this.CabeceraBancoInsert();
        initComponents();
        AutoCompleteDecorator.decorate(jCCuentas);
        
        this.getCuentas_Financieras();
        this.getTablaConciliacion(this.PPeriodo);
        this.getInformacion_Distribucion();
       
        
        this.setCabecera_Conciliacion();
        this.getInformacion_Conciliacion();
        jLabel1.setText("Periodo: "+this.PPeriodo);
        jLabel2.setText("Periodo: "+this.PPeriodo);
        this.getCombo_CDTs();
        this.getCombo_Fondos_CDTs();
        
        jXDatePicker1.setFormats("yyyyMMdd");
        jXDatePicker2.setFormats("yyyyMMdd");
        jXDatePicker3.setFormats("yyyyMMdd");
        jXDatePicker4.setFormats("yyyyMMdd");
        jXDatePicker5.setFormats("yyyyMMdd");
        jXDatePicker6.setFormats("yyyyMMdd");
        jXDatePicker7.setFormats("yyyyMMdd");
        jXDatePicker8.setFormats("yyyyMMdd");
        cabeceraTipo = new Vector();
        this.setCabeceraNiff();
        this.setComboTipo();   
        this.obtenerConciliacionAportes();
        this.setComboCuentaSaldoObras();
        
        cabSaldo =  new Vector();
        cabSaldo.add("Cuenta");
        cabSaldo.add("Fecha");
        cabSaldo.add("Numero Documento");
        cabSaldo.add("Credito");
        cabSaldo.add("Debito");
        cabSaldo.add("Periodo");
        cabAportes =  cabSaldo;
        filaSaldoInsert = new Vector();
        filaAportesInsert = new Vector();
        this.getInformacionSaldoObraBanco();
        this.getInformacionSaldoAportes();
        this.conciliarSaldoObrasBanco();
        jTabbedPane1.removeTabAt(3);
    }
    
    public void conciliarSaldoObrasBanco(){
        double totalTransferencia = this.obtenerSaldoObraProgramas("total_aportes");
        totalTransferencia = totalTransferencia + this.obtenerSaldoObraProgramasPrecripcionAportes("tot_prescrip");
        jTextField11.setText(""+this.JFormatoPeriodos.format(totalTransferencia));
        double totalBanco = getTotalSaldoObraBancos();
        jTextField15.setText(""+this.JFormatoPeriodos.format(totalBanco));
        double total = 0;
        
        if ((totalTransferencia < 0 && totalBanco < 0) || 
             (totalTransferencia > 0 && totalBanco > 0))
            total =  totalTransferencia + (-1*totalBanco); 
        else
            total =  totalTransferencia + totalBanco; 
                
        jTextField16.setText(""+this.JFormatoPeriodos.format(total));
    }
    
    public void setComboCuentaSaldoObras(){
        try {
            String strSql = "select * from selinlib.JCTA_SALDO order by ctacod ";
            ResultSet result =  this.JBase_Datos.SQL_QRY(Cn, strSql);
            jComboBox3.addItem("");
            
            while (result.next()) {
               jComboBox3.addItem(result.getString("ctacod"));
            }
            result.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
    }
    
    public void setComboTipo(){
        try {
            String strSql =" select * from selinlib.JDIST_TIPO ";
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            while (result.next()) {
                jComboBox2.addItem(result.getString("descrip"));
            }
            result.close();
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

        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jComboBox2 = new javax.swing.JComboBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jButton15 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jButton20 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jCCuentas = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jXDatePicker3 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker4 = new org.jdesktop.swingx.JXDatePicker();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jButton16 = new javax.swing.JButton();
        jCDT_Nro = new javax.swing.JTextField();
        jCDT_Cuenta = new javax.swing.JComboBox();
        jCDT_Fec_Cierre = new javax.swing.JTextField();
        jCDT_Valor = new javax.swing.JTextField();
        jCDT_Fec_Aper = new javax.swing.JTextField();
        jCDT_Estado = new javax.swing.JComboBox();
        jCDT_Periodo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jXDatePicker5 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker6 = new org.jdesktop.swingx.JXDatePicker();
        jComboBox3 = new javax.swing.JComboBox();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jButton24 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jXDatePicker7 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker8 = new org.jdesktop.swingx.JXDatePicker();
        jButton25 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTable21 = new javax.swing.JTable();
        jButton57 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Distribucion de Fondos de Ley NIIF");

        jTabbedPane1.setToolTipText("");

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
                "Cuenta Sistema", "Decripcion Cta", "Debito-Contable", "Credito-Contable", "Neto-Contable", "Porcentaje", "Valor-Calculado", "Diferencias"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton6.setText("Guardar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Periodo: ");

        jButton9.setText("Imprimir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta Sistema", "Decripcion Cta", "Valor", "Porcentaje"
            }
        ));
        jScrollPane6.setViewportView(jTable6);

        jButton15.setText("Guardar");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel10.setText("Total aportes");

        jLabel11.setText("Periodo");

        jButton17.setText("Buscar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jLabel22.setText("Total Cortes");

        jLabel23.setText("Total Contable");

        jLabel24.setText("Diferencia");

        jTextField12.setEditable(false);

        jTextField13.setEditable(false);

        jTextField14.setEditable(false);

        jButton20.setText("Guardar");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel10))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jButton17)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(431, 431, 431)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jButton1)
                                .addGap(76, 76, 76)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9)))
                        .addContainerGap(16, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel22)
                        .addGap(107, 107, 107)
                        .addComponent(jLabel23)
                        .addGap(115, 115, 115)
                        .addComponent(jLabel24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(jButton20)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel11)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton17)
                            .addComponent(jButton15))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton6)
                    .addComponent(jButton9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20))
                .addGap(56, 56, 56))
        );

        jTabbedPane1.addTab("Distribuciones", jPanel1);

        jButton2.setText("Consultar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta", "Fecha", "Numero Documento", "Credito", "Debito", "Periodo"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable2MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jCCuentas.setEditable(true);

        jButton3.setText("Mover");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta", "Fecha", "Numero Documento", "Credito", "Debito", "Periodo"
            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jButton4.setText("Guardar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Eliminar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Periodo:");

        jButton10.setText("Imprimir");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Ver");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Ver");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton12)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jButton10))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton11)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jXDatePicker3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jXDatePicker4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jCCuentas, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jButton2)
                                .addGap(70, 70, 70)
                                .addComponent(jLabel2)))
                        .addGap(0, 467, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jCCuentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jXDatePicker3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jXDatePicker4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton11))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton4)
                    .addComponent(jButton10)
                    .addComponent(jButton12))
                .addContainerGap(250, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Traslado Financieros", jPanel2);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta Contable", "Cuenta Financiera", "Descripcion", "Neto Financiero", "Neto Contable", "Neto Calculado", "Diferencias"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        jButton7.setText("Conciliar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Imprimir");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addGap(18, 18, 18)
                        .addComponent(jButton8))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 938, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Conciliacion", jPanel3);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nro", "Cuenta", "Fondo", "Fecha Mov", "Fecha Apertura", "Fecha de Cierre", "Valor", "Periodo", "Estado"
            }
        ));
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable5MousePressed(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        jButton13.setText("Guardar");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("Elimniar");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Todos", "Activa", "Inactivas" }));

        jButton16.setText("Buscar");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jCDT_Cuenta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));

        jCDT_Estado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Activo", "Inactivo" }));

        jLabel3.setText("Nro");

        jLabel4.setText("Cuenta");

        jLabel5.setText("Fecha Apertura");

        jLabel6.setText("Fecha Cierre");

        jLabel7.setText("Valor");

        jLabel8.setText("Estado");

        jLabel9.setText("Periodo");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(jLabel4)
                                .addGap(127, 127, 127)
                                .addComponent(jLabel9)
                                .addGap(98, 98, 98)
                                .addComponent(jLabel8))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(jLabel3)
                                .addGap(128, 128, 128)
                                .addComponent(jLabel5)
                                .addGap(59, 59, 59)
                                .addComponent(jLabel6)
                                .addGap(95, 95, 95)
                                .addComponent(jLabel7)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton16))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCDT_Nro, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jCDT_Cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(29, 29, 29)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jCDT_Periodo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jCDT_Fec_Aper, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jCDT_Fec_Cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jCDT_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(33, 33, 33)
                                        .addComponent(jCDT_Valor, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton13)
                                    .addComponent(jButton14))
                                .addGap(0, 746, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCDT_Nro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCDT_Fec_Aper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCDT_Fec_Cierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCDT_Valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(1, 1, 1)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCDT_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCDT_Cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCDT_Periodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addComponent(jButton13)
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton14)
                .addContainerGap(270, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Titulos y Otros", jPanel5);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Total de Aportes"));

        jLabel12.setText("Total de Aportes");

        jTextField2.setEditable(false);

        jLabel13.setText("Apropiacion de Ley");

        jTextField3.setEditable(false);

        jButton18.setText("Guardar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel14.setText("Sub. en Especie");

        jTextField4.setEditable(false);

        jLabel15.setText("Sub. a la Demanda");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel16.setText("Sub. a la Oferta");

        jLabel17.setText("Total");

        jTextField7.setEditable(false);

        jTextField17.setEditable(false);

        jTextField18.setEditable(false);

        jLabel27.setText("Cuota monetaria liquidada");

        jLabel28.setText("Cuota monetaria apropiada");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel12))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField2)
                                .addComponent(jTextField4)
                                .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                .addComponent(jButton18)
                                .addComponent(jTextField17)))))
                .addGap(55, 55, 55)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3)
                            .addComponent(jTextField5)
                            .addComponent(jTextField7)
                            .addComponent(jTextField18, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(39, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(90, 90, 90))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton18)
                .addContainerGap())
        );

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Total transferencia");

        jTextField11.setEditable(false);

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta", "Fecha", "Numero Documento", "Credito", "Debito", "Periodo"
            }
        ));
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable7MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable7MousePressed(evt);
            }
        });
        jScrollPane7.setViewportView(jTable7);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel());

        jButton21.setText("Buscar");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setText("Mover");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setText("Eliminar");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta", "Fecha", "Numero Documento", "Credito", "Debito", "Periodo"
            }
        ));
        jTable8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable8MouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(jTable8);

        jButton24.setText("Guardar");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jLabel25.setText("Total Banco");

        jTextField15.setEditable(false);

        jLabel26.setText("diferencias");

        jTextField16.setEditable(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel26)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jXDatePicker5, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jXDatePicker6, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton21))
                            .addComponent(jScrollPane7)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jButton22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton24)))
                .addContainerGap(355, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jXDatePicker5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jXDatePicker6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton21)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton22)
                    .addComponent(jButton23)
                    .addComponent(jButton24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("Saldo para Obra y Programas", jPanel6);

        jButton25.setText("Buscar");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta", "Fecha", "Numero Documento", "Credito", "Debito", "Periodo"
            }
        ));
        jTable9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable9MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable9MousePressed(evt);
            }
        });
        jScrollPane9.setViewportView(jTable9);

        jButton26.setText("Guardar");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setText("Eliminar");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton28.setText("Mover");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta", "Fecha", "Numero Documento", "Credito", "Debito", "Periodo"
            }
        ));
        jTable10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable10MouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(jTable10);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Prescripcion"));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Total prescripcion");

        jTextField8.setEditable(false);

        jTextField9.setEditable(false);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Apropiacion de Ley");

        jButton19.setText("Guardar");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Total");

        jTextField10.setEditable(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton19)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 35, Short.MAX_VALUE))
                            .addComponent(jTextField8)
                            .addComponent(jTextField10)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(84, 84, 84)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(92, 92, 92))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton19)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton26))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 878, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jXDatePicker7, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jXDatePicker8, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(131, 131, 131)
                        .addComponent(jButton25)))
                .addContainerGap(352, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton25))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton28)
                    .addComponent(jButton27)
                    .addComponent(jButton26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jTabbedPane1.addTab("Prescripcion de Aportes", jPanel9);

        jTable21.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta Contable", "Cuenta Financiera", "Descripcion", "Neto Financiero", "Neto Contable", "Neto Calculado", "Diferencias"
            }
        ));
        jScrollPane21.setViewportView(jTable21);

        jButton57.setText("Conciliar");
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });

        jButton58.setText("Imprimir");
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jButton57)
                        .addGap(18, 18, 18)
                        .addComponent(jButton58))
                    .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 938, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton57)
                    .addComponent(jButton58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Conc. prescripcion Aporte", jPanel10);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.FechaIncial = d.format(jXDatePicker1.getDate()).toString().trim();
        this.FechaFinal = d.format(jXDatePicker2.getDate()).toString().trim();
        this.Fila.clear();
        this.getCuentAportes();
        this.getDistribucionGeneral();
        this.getSaldoApotes();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
       String BFechaInicial = d.format(jXDatePicker3.getDate()).toString().trim();
       String BFechaFinal = d.format(jXDatePicker4.getDate()).toString().trim();
       this.setCabeceraBanco();
       this.getInformacionBanco(BFechaInicial.trim(),BFechaFinal.trim(), jCCuentas.getSelectedItem().toString() );
       //Buscar_Movimiento_Fosyga(this.BFechaInicial.getText().trim() , BFechaFinal.getText().trim(), "28521501", "900462447" );
    }//GEN-LAST:event_jButton2ActionPerformed
    
    public void setCabeceraNiff(){
        cabeceraTipo.add("Cuenta de Sistema");
        cabeceraTipo.add("Descripcion Cta");
        cabeceraTipo.add("Valor");
        cabeceraTipo.add("Porcentaje");
    }
    
    public void CabeceraBancoInsert(){
        this.CabBancoInsert.add("Cuenta");
        this.CabBancoInsert.add("Fecha");
        this.CabBancoInsert.add("Numero Documento");
        this.CabBancoInsert.add("Credito");
        this.CabBancoInsert.add("Debito");
        this.CabBancoInsert.add("Periodo");
    }
    
    public void setMover(){
        this.DetBancoInsert = new Vector();
        this.DetBancoInsert.add(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
        this.DetBancoInsert.add(jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString());
        this.DetBancoInsert.add(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
        this.DetBancoInsert.add(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString());
        this.DetBancoInsert.add(jTable2.getValueAt(jTable2.getSelectedRow(), 4).toString());
        this.DetBancoInsert.add(jTable2.getValueAt(jTable2.getSelectedRow(), 5).toString());
        this.FilaBancoInsert.add(DetBancoInsert);
        this.jTable3.setModel(new javax.swing.table.DefaultTableModel(this.FilaBancoInsert, this.CabBancoInsert));
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (!estaAbiertoPeriodo()){
            javax.swing.JOptionPane.showMessageDialog(this, " el Periodo esta cerrado no se puede guardar ");
        }else{
            
            try{
                
                if ( !jTable2.getValueAt(jTable2.getSelectedRow(), 5).toString().trim().equals("")){
                  this.setMover();
                }else{
                  JOptionPane.showMessageDialog(this,"Debe Seleccionar el periodo al cual desea conciliar ");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this,"ClickButton() "+e.getMessage() );
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (!estaAbiertoPeriodo()){
            javax.swing.JOptionPane.showMessageDialog(this, " el Periodo esta cerrado no se puede guardar ");
        }else{
            
            if (! this.Guardar()){
                JOptionPane.showMessageDialog(this,"No se pudo Guardar Correctamente " );
            }else{
                JOptionPane.showMessageDialog(this,"Se Guardo Correctamente " );
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (!estaAbiertoPeriodo()){
            javax.swing.JOptionPane.showMessageDialog(this, " el Periodo esta cerrado no se puede guardar ");
        }else{
            this.setBorrarFila_tabla3();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (!estaAbiertoPeriodo()){
           javax.swing.JOptionPane.showMessageDialog(this, " el Periodo esta cerrado no se puede guardar ");
        }else{
            
            if(this.Guardar_Distribucion()){
                   JOptionPane.showMessageDialog(this,"Se Guardo Correctamente  "); 
            }else{
                JOptionPane.showMessageDialog(this,"No se Pudo Guardar Correctamente  "); 
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (!estaAbiertoPeriodo()){
           javax.swing.JOptionPane.showMessageDialog(this, " el Periodo esta cerrado no se puede guardar ");
        }else{
            this.FechaIncial = d.format(jXDatePicker1.getDate()).toString().trim();
            this.FechaFinal = d.format(jXDatePicker2.getDate()).toString().trim();
        
            if ((!FechaIncial.equals(""))|| (!FechaFinal.equals(""))) {
            
                if( this.getConciliar()){
                    this.getInformacion_Conciliacion();
                    JOptionPane.showMessageDialog(this,"Se Guardo Correctamente  "); 
                }else{
                    JOptionPane.showMessageDialog(this,"No se Pudo Guardar Correctamente  "); 
                }
            }else{
                JOptionPane.showMessageDialog(this," Debe diligenciar la feha  "); 
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
              
      this.FechaIncial = d.format(jXDatePicker1.getDate()).toString().trim();
      this.FechaFinal = d.format(jXDatePicker2.getDate()).toString().trim();
        
      if( !(this.FechaIncial.trim().equals(""))  &&!(this.FechaFinal.trim().equals(""))){
            JJasper  GenPdf = new JJasper();
            GenPdf.setTipo(101);
            GenPdf.setFechaInicial(this.FechaIncial.trim());
            GenPdf.setFechaFinal(this.FechaFinal.trim());
            GenPdf.setPPeriodo(PPeriodo);
            GenPdf.setConeccion(Cn);
            GenPdf.start();
      }else{
          JOptionPane.showMessageDialog(this,"Debe diligenciar la fecha inicial y fecha Final en la Primera pestaña  "); 
      }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        this.FechaIncial = d.format(jXDatePicker1.getDate()).toString().trim();
        this.FechaFinal = d.format(jXDatePicker2.getDate()).toString().trim();
  
        if( !(FechaIncial.trim().equals(""))  &&!(FechaFinal.trim().equals(""))){
            JJasper  GenPdf = new JJasper();
            GenPdf.setTipo(102);
            GenPdf.setFechaInicial(FechaIncial.trim());
            GenPdf.setFechaFinal(FechaFinal.trim());
            GenPdf.setPPeriodo(PPeriodo);
            GenPdf.setConeccion(Cn);
            GenPdf.start();
        }else{
          JOptionPane.showMessageDialog(this,"Debe diligenciar la fecha inicial y fecha Final en la Primera pestaña  "); 
       }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        JJasper  GenPdf = new JJasper();
        GenPdf.setTipo(103);
        GenPdf.setPPeriodo(PPeriodo);
        GenPdf.setConeccion(Cn);
        GenPdf.start();
        /* Pendiente crear la tabla JFBANCONCI ==>> JFBANCONIF para NIIF's*/
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:   
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MousePressed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        try {
             String PFecha = jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString();
             String PNumDoc = jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString();
             String PCuenta = jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString();
             JDetalleContable Frm_JDetalleContable =  new JDetalleContable(this.JBase_Datos, this.Cn, PFecha, PNumDoc, PCuenta);
             Frm_JDetalleContable.setVisible(true);       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," Frm_Detalle()  "+e.getMessage()); 
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        try {
            String PFecha = jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString();
            String PNumDoc = jTable3.getValueAt(jTable3.getSelectedRow(), 2).toString();
            String PCuenta = jTable3.getValueAt(jTable3.getSelectedRow(), 0).toString();
            JDetalleContable Frm_JDetalleContable =  new JDetalleContable(this.JBase_Datos, this.Cn, PFecha, PNumDoc, PCuenta);
            Frm_JDetalleContable.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," Frm_Detalle()  "+e.getMessage()); 
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        this.get_CDTS_();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        this.Save_CDTs();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        // TODO add your handling code here:
        try{
            jCDT_Nro.setText(jTable5.getValueAt(jTable5.getSelectedRow(),0).toString());
            jCDT_Fec_Aper.setText(jTable5.getValueAt(jTable5.getSelectedRow(),4).toString());
            jCDT_Fec_Cierre.setText(jTable5.getValueAt(jTable5.getSelectedRow(),5).toString());
            jCDT_Valor.setText(jTable5.getValueAt(jTable5.getSelectedRow(),6).toString());
        }catch(Exception e ){
            JOptionPane.showMessageDialog(this," Click_Tabla()  "+e.getMessage()); 
        }
    }//GEN-LAST:event_jTable5MouseClicked

    private void jTable5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5MousePressed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
         try{
            String NroCdt =jTable5.getValueAt(jTable5.getSelectedRow(),0).toString();
            String NroCuenta =jTable5.getValueAt(jTable5.getSelectedRow(),1).toString();
            String Str_Sql ="select * from selinlib.JFCDT where NROCDT ="+NroCdt+" and ctacon="+NroCuenta.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(rs.next()){
              Str_Sql = "Delete from selinlib.JFCDT where NROCDT ="+NroCdt;
              boolean Guardar = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
              
              if(Guardar){
                DefaultTableModel Modelo =(DefaultTableModel) jTable5.getModel();
                Modelo.removeRow(jTable5.getSelectedRow());
                jTable5.updateUI();
                JOptionPane.showMessageDialog(this," Se Elimino Correcetamente  "); 
              }
            }
        }catch(Exception e ){
            JOptionPane.showMessageDialog(this," Click_Tabla()  "+e.getMessage()); 
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        if (!estaAbiertoPeriodo()){
            javax.swing.JOptionPane.showMessageDialog(this, " el Periodo esta cerrado no se puede guardar ");
        }else{
            
           try {
               
                if(this.jComboBox2.getSelectedItem().toString().trim().equals("") || jTextField1.getText().trim().equals("")){
                   javax.swing.JOptionPane.showMessageDialog(this, "Debe ingresar el valor del aporte y el periodo del analisis");
                }else{
                   double valorAportes = Double.valueOf(jTextField1.getText().trim());
                   String tipo = jComboBox2.getSelectedItem().toString().trim();
                   this.registroIngresoTipo(tipo, valorAportes);
                   this.calculoDistribucion(valorAportes, tipo);
                }
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, " "+e.getMessage());
            }
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        jTextField1.setText("");
        try {
            
            if(this.jComboBox2.getSelectedItem().toString().trim().equals("")){
               javax.swing.JOptionPane.showMessageDialog(this, "Debes ingresar el periodo del analisis");
            }else{
               String tipo = jComboBox2.getSelectedItem().toString().trim();
               this.obtenerTipoDistribucion(this.PPeriodo, tipo);
               this.obtenerIngresoTipo(tipo);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        try {
            double totalAportes = this.obtenerTotalAportes("corte");
            double totalDistribucion = this.obtenerApropiacionLey("corte");
            double totalSubsidioEspecie = this.obtenerSubsidioEspecie();
            double data[] = getValorPorcentualSaldoObra();
            jTextField17.setText(""+this.JFormatoPeriodos.format(data[1]));
            jTextField18.setText(""+this.JFormatoPeriodos.format(data[0]));
            jTextField2.setText(""+this.JFormatoPeriodos.format(totalAportes));
            jTextField3.setText(""+this.JFormatoPeriodos.format(totalDistribucion));
            jTextField4.setText(""+this.JFormatoPeriodos.format(totalSubsidioEspecie));
            double subisidioDemanda = Double.parseDouble(jTextField5.getText());
            double subsidioOferta = Double.parseDouble(jTextField6.getText());
            double total = 0;
            
            if (data[2] == 0){
                // Contable es mayor data[]=1
                total =  totalAportes - (totalDistribucion+(data[1]-data[0])) -totalSubsidioEspecie 
                    - subisidioDemanda  - subsidioOferta;
            }else{
                total =  totalAportes - totalDistribucion -totalSubsidioEspecie 
                    - subisidioDemanda  - subsidioOferta;
            }
                
            this.eliminarSaldoObraProgramas("total_aportes");
            boolean resp = this.crearControlSaldoObraProgramas("total_aportes", totalAportes, totalDistribucion, totalSubsidioEspecie, subisidioDemanda, subsidioOferta,data[0], data[1] ,total);
            
            if (resp) 
               javax.swing.JOptionPane.showMessageDialog(this, "Se guardo correctamente");
            else
                javax.swing.JOptionPane.showMessageDialog(this, "No se pudo guardo correctamente");
            
            jTextField7.setText(""+this.JFormatoPeriodos.format(total));
            conciliarSaldoObrasBanco();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        try {
            double totalPrescipcionAporte = this.obtenerTotalAportes("prescripcion");
            double totalDistribucionPrescripcionAporte = this.obtenerApropiacionLey("prescripcion");
            double total = totalPrescipcionAporte - totalDistribucionPrescripcionAporte;
            jTextField8.setText(""+this.JFormatoPeriodos.format(totalPrescipcionAporte));
            jTextField9.setText(""+this.JFormatoPeriodos.format(totalDistribucionPrescripcionAporte));
            jTextField10.setText(""+this.JFormatoPeriodos.format(total));
            this.eliminarSaldoObraProgramas("tot_prescrip");
            boolean resp = this.crearControlSaldoObraProgramas("tot_prescrip", totalPrescipcionAporte, totalDistribucionPrescripcionAporte, 0, 0, 0, 0, 0, total);
            
            if (resp)
                javax.swing.JOptionPane.showMessageDialog(this, "Se guardo Correctamente");
            else
                javax.swing.JOptionPane.showMessageDialog(this, "No se pudo Guardar Correctamente");
            
            this.conciliarSaldoObrasBanco();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        try {
            this.FechaIncial = d.format(jXDatePicker1.getDate()).toString().trim();
            this.FechaFinal = d.format(jXDatePicker2.getDate()).toString().trim();
            double totalAportes = obtenerAporteContable();
            double totalAportesCortes = this.obtenerTotalAportes("corte");
            jTextField13.setText(this.JFormatoPeriodos.format(totalAportes));
            jTextField12.setText(this.JFormatoPeriodos.format(totalAportesCortes));
            double total = (totalAportesCortes-totalAportes);
            jTextField14.setText(this.JFormatoPeriodos.format(total));
            this.EliminarConciliacionAportes();
            this.crearConciliacionAportes(totalAportesCortes, totalAportes, total);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe ingresar la fecha de analisis "+e.getMessage());
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable7MouseClicked

    private void jTable7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable7MousePressed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        if (!estaAbiertoPeriodo()){
            javax.swing.JOptionPane.showMessageDialog(this, " el Periodo esta cerrado no se puede guardar ");
        }else{
            
            if (this.setBorrarFila_Saldo())
                javax.swing.JOptionPane.showMessageDialog(this, "Se elimino correctamente");
            this.conciliarSaldoObrasBanco();
        }
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jTable8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable8MouseClicked

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        try {
            String fechaInicial = d.format(jXDatePicker5.getDate()).toString().trim();
            String fechaFinal = d.format(jXDatePicker6.getDate()).toString().trim();
            String cta = jComboBox3.getSelectedItem().toString();
            this.obtenerContabilidadSaldoObraProgramasEspeciales(fechaInicial , fechaFinal, cta);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        if (!estaAbiertoPeriodo()){
            javax.swing.JOptionPane.showMessageDialog(this, " el Periodo esta cerrado no se puede guardar ");
        }else{
            
            try{
                
                if ( !jTable7.getValueAt(jTable7.getSelectedRow(), 5).toString().trim().equals("")){
                  this.setMoverSaldo();
                }else{
                  JOptionPane.showMessageDialog(this,"Debe Seleccionar el periodo al cual desea conciliar ");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this,"ClickButton() "+e.getMessage() );
            }
        }
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        if (guardarSaldoObraBanco())
            javax.swing.JOptionPane.showMessageDialog(this, "Se Guardo correctamente");
        
        this.conciliarSaldoObrasBanco();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        try {
            String fechaInicial = d.format(jXDatePicker7.getDate()).toString().trim();
            String fechaFinal = d.format(jXDatePicker8.getDate()).toString().trim();
            String cuenta = jComboBox4.getSelectedItem().toString();
            this.getCuentaAporte(fechaInicial, fechaFinal, cuenta);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Deben ingresar toda las fechas y seleccionar la cuenta!");
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jTable9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable9MouseClicked

    private void jTable9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable9MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable9MousePressed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        if (guardarSaldoAportesBanco())
            javax.swing.JOptionPane.showMessageDialog(this, "Se Guardo correctamente");
        else
            javax.swing.JOptionPane.showMessageDialog(this, "No Se pudo Guardar correctamente");
        
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        if (!estaAbiertoPeriodo()){
            javax.swing.JOptionPane.showMessageDialog(this, " el Periodo esta cerrado no se puede guardar ");
        }else{
            
            if (this.setBorrarFilaAportes())
                javax.swing.JOptionPane.showMessageDialog(this, "Se elimino correctamente");
            
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
        if (!estaAbiertoPeriodo()){
            javax.swing.JOptionPane.showMessageDialog(this, " el Periodo esta cerrado no se puede guardar ");
        }else{
            
            try{
                
                if ( !jTable9.getValueAt(jTable9.getSelectedRow(), 5).toString().trim().equals("")){
                  this.setMoverAportes();
                }else{
                  JOptionPane.showMessageDialog(this,"Debe Seleccionar el periodo al cual desea conciliar ");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this,"ClickButton() "+e.getMessage() );
            }
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jTable10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable10MouseClicked

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton57ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton58ActionPerformed
    
    public void getCuentaAporte(String fechaInicial, String fechaFinal, String cuenta){
        try {
            String Str_Sql ="select * from CONDATNIC.movimi where fecmov>="+fechaInicial.trim()+" and fecmov<="+fechaFinal.trim()+" and"
                  + "  ctamov ='"+cuenta.trim()+"'";
            ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            filaAportes = new Vector();
            
            while(Rs.next()){
              this.getComboPeriodo();
              this.detAportes = new Vector();
              this.detAportes.add(Rs.getString("ctamov"));
              this.detAportes.add(Rs.getString("fecmov"));
              this.detAportes.add(Rs.getString("NUMMOV"));
              this.detAportes.add(this.JFormato.format(Rs.getDouble("cremov")));
              this.detAportes.add(this.JFormato.format(Rs.getDouble("debmov")));
              this.detAportes.add("");
              this.filaAportes.add(this.detAportes);
            } 
            Rs.close();
            this.jTable9.setModel(new javax.swing.table.DefaultTableModel(this.filaAportes, this.cabAportes));
            this.jTable9.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(jCPeriodo));
            jTable9.setAutoCreateRowSorter(true);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
    }
    
    public double [] getValorPorcentualSaldoObra(){
        double data [] = new double[3];
        
        try {
            String strStr= "select * from selinlib.JDISSALDOS  as t1 ";
            ResultSet result =  this.JBase_Datos.SQL_QRY(Cn, strStr);
            double toltalAporte = this.obtenerTotalAportes("corte");
            double totalSaldo = 0;
            double totalContable = 0;
            
            while (result.next()){
                totalContable =  totalContable + getContabilidad(result.getString("CTACON"));
                totalSaldo = totalSaldo + (toltalAporte * (result.getDouble("PORCENT")));
            }
            result.close();
            data[0] = totalSaldo;
            data[1] = totalContable;
            
            if (totalContable>totalSaldo){
                data[2] = 1;
            }
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " getValorPorcentualSaldoObra() "+e.getMessage());
        }
        return data;
    }
    
    public double getContabilidad(String cuenta){
        try {
            String strSql = "select sum(debmov-cremov) as valor from condatnic.movimi "
                            + " where fecmov between "+this.PPeriodo+"01"+" and  "+this.PPeriodo+"31"+" "
                            + " and ctamov like  '%"+cuenta+"%'" ;
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            if (result.next())
                return result.getDouble("valor");
            
            result.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debes digitar la fecha  "+e.getMessage());
        }
        return 0;
    }
    
    public double getTotalSaldoObraBancos(){
        try {
            String strSql = "select sum(debito)-sum(credito) as total from selinlib.JCON_SALDO where periodo="+this.PPeriodo;
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            if (result.next())
                return result.getDouble("total");
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(rootPane, DebAgr);
        }
        return 0;
    }
    
    public void getInformacionSaldoObraBanco(){
        try {
            String strSql = "select * from selinlib.JCON_SALDO where periodo="+this.PPeriodo;
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            filaSaldoInsert.clear();
            
            while (result.next()) {
                this.detSaldoInsert = new Vector();
                this.detSaldoInsert.add(result.getString("ctamov"));
                this.detSaldoInsert.add(result.getString("fecmov"));
                this.detSaldoInsert.add(result.getString("numdoc"));
                this.detSaldoInsert.add(JFormato.format(result.getDouble("credito")));
                this.detSaldoInsert.add(JFormato.format(result.getDouble("debito")));
                this.detSaldoInsert.add(JFormato.format(result.getInt("periodo")));
                this.filaSaldoInsert.add(this.detSaldoInsert);
            }
            jTable8.setModel(new javax.swing.table.DefaultTableModel(this.filaSaldoInsert, this.CabBancoInsert));
            result.close();
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,""+e.getMessage());
        }
    }
    
    public void getInformacionSaldoAportes(){
        try {
            String strSql = "select * from selinlib.JCON_APORT where periodo="+this.PPeriodo;
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            filaAportesInsert.clear();
            
            while (result.next()) {
                this.detAportesInsert = new Vector();
                this.detAportesInsert.add(result.getString("ctamov"));
                this.detAportesInsert.add(result.getString("fecmov"));
                this.detAportesInsert.add(result.getString("numdoc"));
                this.detAportesInsert.add(JFormato.format(result.getDouble("credito")));
                this.detAportesInsert.add(JFormato.format(result.getDouble("debito")));
                this.detAportesInsert.add(JFormato.format(result.getInt("periodo")));
                this.filaAportesInsert.add(this.detAportesInsert);
            }
            jTable10.setModel(new javax.swing.table.DefaultTableModel(this.filaAportesInsert, this.cabAportes));
            result.close();
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,""+e.getMessage());
        }
    }
    
    public boolean guardarSaldoAportesBanco(){
        try {
            this.borrarBandoAportes();
            
            for (int i = 0; i < jTable10.getRowCount(); i++) {
                String cuenta = jTable10.getValueAt(i, 0).toString();
                String fecha = jTable10.getValueAt(i, 1).toString();
                String numDocumento = jTable10.getValueAt(i, 2).toString();
                double credito = Double.valueOf( jTable10.getValueAt(i, 3).toString());
                double debito = Double.valueOf( jTable10.getValueAt(i, 4).toString());
                String periodo = jTable10.getValueAt(i, 5).toString();
                
                
                if (!crearBancoAportes(cuenta,fecha,numDocumento,credito,debito ,periodo))
                  javax.swing.JOptionPane.showMessageDialog(this, "No se pudo Guardar Correctamente");
             }
             return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Guardar() "+e.getMessage() );
        }
        return false;
    }
    
    public boolean crearBancoAportes(String ctaMov, String fecMov, String numDocumento, double credito, double debito, String periodo){
        try {
            String strSql = "insert into selinlib.JCON_APORT "
                + "(CTAMOV, FECMOV, PERIODO, NUMDOC, CREDITO, DEBITO, FECUSU, USUARIO) values "
                + "("+ctaMov+","+fecMov+","+periodo+","+numDocumento+","+credito+","+debito+","+this.FechaUsuario+",'"+this.Usuario.toUpperCase()+"')";
            return  this.JBase_Datos.Exc_Sql(Cn, strSql);
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return false;
    }
    
    public boolean borrarBandoAportes(){
        try {
            
            String strSql = " delete from selinlib.JCON_APORT where periodo="+this.PPeriodo;
            return this.JBase_Datos.Exc_Sql(Cn, strSql);
            
        } catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return false;
    }
    
    public boolean guardarSaldoObraBanco(){
        try {
            int Cantidad =  jTable8.getRowCount();
            this.borrarBandoSaldoObra();
            
            for (int i = 0; i < Cantidad; i++) {
                String cuenta = jTable8.getValueAt(i, 0).toString();
                String fecha = jTable8.getValueAt(i, 1).toString();
                String numDocumento = jTable8.getValueAt(i, 2).toString();
                double credito = Double.valueOf( jTable8.getValueAt(i, 3).toString());
                double debito = Double.valueOf( jTable8.getValueAt(i, 4).toString());
                String periodo = jTable8.getValueAt(i, 5).toString();
                
                if (!crearRegistroBancoSaldoObra(cuenta, fecha, numDocumento, credito, debito, periodo))
                   javax.swing.JOptionPane.showMessageDialog(this, "No se pudo Guardar Correctamente");
             }
             return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Guardar() "+e.getMessage() );
        }
        return false;
    }
    
    public boolean borrarBandoSaldoObra(){
        try {
            
            String strSql = " delete from selinlib.JCON_SALDO where periodo="+this.PPeriodo;
            return this.JBase_Datos.Exc_Sql(Cn, strSql);
            
        } catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return false;
    }
    
    public boolean crearRegistroBancoSaldoObra(String ctaMov, String fecMov, String numDocumento, double credito, double debito, String periodo){
        try {
            String strSql = "insert into selinlib.JCON_SALDO "
                + "(CTAMOV, FECMOV, PERIODO, NUMDOC, CREDITO, DEBITO, FECUSU, USUARIO) values "
                + "("+ctaMov+","+fecMov+","+periodo+","+numDocumento+","+credito+","+debito+","+this.FechaUsuario+",'"+this.Usuario.toUpperCase()+"')";
            return  this.JBase_Datos.Exc_Sql(Cn, strSql);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return false;
    }
    
    public boolean setBorrarFilaAportes(){
       try{
            //llamar a elimninar aqui
            String cuenta = jTable10.getValueAt(this.jTable10.getSelectedRow(), 0).toString();
            String fecha = jTable10.getValueAt(this.jTable10.getSelectedRow(), 1).toString();
            String numDocumento = jTable10.getValueAt(this.jTable10.getSelectedRow(), 2).toString();
            String periodo = jTable10.getValueAt(this.jTable10.getSelectedRow(), 5).toString();
            
            this.borrarBancoAporesItems(numDocumento, cuenta, fecha, periodo);
            DefaultTableModel Modelo =(DefaultTableModel) jTable10.getModel();
            Modelo.removeRow(jTable10.getSelectedRow());
            jTable10.updateUI();
            return true;
       }catch(Exception e){
           JOptionPane.showMessageDialog(this,"El registro seleccionado debe Contener informacion"); 
       }
       return false;
    }
    
    public boolean borrarBancoAporesItems(String numDocumento, String cuenta, String fecha, String periodo){
        try {
            
            String strSql = " delete from selinlib.JCON_APORT where periodo="+periodo+" "
                        + " and NUMDOC="+numDocumento+" and fecmov="+fecha+" and ctamov="+cuenta;
            return this.JBase_Datos.Exc_Sql(Cn, strSql);
        } catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return false;
    }
    
    
    public boolean setBorrarFila_Saldo(){
       try{
            //llamar a elimninar aqui
            String cuenta = jTable8.getValueAt(this.jTable8.getSelectedRow(), 0).toString();
            String fecha = jTable8.getValueAt(this.jTable8.getSelectedRow(), 1).toString();
            String numDocumento = jTable8.getValueAt(this.jTable8.getSelectedRow(), 2).toString();
            String periodo = jTable8.getValueAt(this.jTable8.getSelectedRow(), 5).toString();
            
            borrarBandoSaldoObraItems(numDocumento, cuenta, fecha, periodo);
            DefaultTableModel Modelo =(DefaultTableModel) jTable8.getModel();
            Modelo.removeRow(jTable8.getSelectedRow());
            jTable8.updateUI();
            return true;
       }catch(Exception e){
           JOptionPane.showMessageDialog(this,"El registro seleccionado debe Contener informacion"); 
       }
       return false;
    }
    
    public boolean borrarBandoSaldoObraItems(String numDocumento, String cuenta, String fecha, String periodo){
        try {
            
            String strSql = " delete from selinlib.JCON_SALDO where periodo="+periodo+" "
                        + " and NUMDOC="+numDocumento+" and fecmov="+fecha+" and ctamov="+cuenta;
            return this.JBase_Datos.Exc_Sql(Cn, strSql);
        } catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return false;
    }
    
    public void setMoverAportes(){
        this.detAportesInsert = new Vector();
        this.detAportesInsert.add(jTable9.getValueAt(jTable9.getSelectedRow(), 0).toString());
        this.detAportesInsert.add(jTable9.getValueAt(jTable9.getSelectedRow(), 1).toString());
        this.detAportesInsert.add(jTable9.getValueAt(jTable9.getSelectedRow(), 2).toString());
        this.detAportesInsert.add(jTable9.getValueAt(jTable9.getSelectedRow(), 3).toString());
        this.detAportesInsert.add(jTable9.getValueAt(jTable9.getSelectedRow(), 4).toString());
        this.detAportesInsert.add(jTable9.getValueAt(jTable9.getSelectedRow(), 5).toString());
        this.filaAportesInsert.add(detAportesInsert);
        this.jTable10.setModel(new javax.swing.table.DefaultTableModel(this.filaAportesInsert, this.cabAportes));
    }
    
    public void setMoverSaldo(){
        this.detSaldoInsert = new Vector();
        this.detSaldoInsert.add(jTable7.getValueAt(jTable7.getSelectedRow(), 0).toString());
        this.detSaldoInsert.add(jTable7.getValueAt(jTable7.getSelectedRow(), 1).toString());
        this.detSaldoInsert.add(jTable7.getValueAt(jTable7.getSelectedRow(), 2).toString());
        this.detSaldoInsert.add(jTable7.getValueAt(jTable7.getSelectedRow(), 3).toString());
        this.detSaldoInsert.add(jTable7.getValueAt(jTable7.getSelectedRow(), 4).toString());
        this.detSaldoInsert.add(jTable7.getValueAt(jTable7.getSelectedRow(), 5).toString());
        this.filaSaldoInsert.add(detSaldoInsert);
        this.jTable8.setModel(new javax.swing.table.DefaultTableModel(this.filaSaldoInsert, this.cabSaldo));
    }

    public void obtenerContabilidadSaldoObraProgramasEspeciales(String FIncial , String FFinal, String Cta){
      try {
          String Str_Sql ="select * from CONDATNIC.movimi where fecmov>="+FIncial+" and fecmov<="+FFinal+" and"
                  + "  ctamov ='"+Cta.trim()+"'";
          ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
          Vector detSaldo;
          Vector filaSaldo = new Vector();
          this.getComboPeriodo();
          
          while(Rs.next()){
            detSaldo = new Vector();
            detSaldo.add(Rs.getString("ctamov"));
            detSaldo.add(Rs.getString("fecmov"));
            detSaldo.add(Rs.getString("NUMMOV"));
            detSaldo.add(this.JFormato.format(Rs.getDouble("cremov")));
            detSaldo.add(this.JFormato.format(Rs.getDouble("debmov")));
            detSaldo.add("");
            filaSaldo.add(detSaldo);
          } 
          Rs.close();
          this.jTable7.setModel(new javax.swing.table.DefaultTableModel(filaSaldo, cabSaldo));
          this.jTable7.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(jCPeriodo));
          jTable7.setAutoCreateRowSorter(true);
      } catch (Exception e) {
          JOptionPane.showMessageDialog(this,"getInformacionBanco() "+e.getMessage());  
      }
    }

    public void obtenerConciliacionAportes(){
        try {
            String strSql = " select * from selinlib.JCONCILIFP where periodo ="+this.PPeriodo;
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            if (result.next()) {
                jTextField12.setText(this.JFormatoPeriodos.format(result.getDouble("TOT_CORTE")));
                jTextField13.setText(this.JFormatoPeriodos.format(result.getDouble("TOT_APORT")));
                jTextField14.setText(this.JFormatoPeriodos.format(result.getDouble("DIFERENCIA")));
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "obtenerConciliacionAportes() "+e.getMessage());
        }
    }
    
    public boolean EliminarConciliacionAportes(){
        try {
            String strSql = " delete from  selinlib.JCONCILIFP where Periodo="+this.PPeriodo;
            return  this.JBase_Datos.Exc_Sql(Cn, strSql);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " EliminarConciliacionAportes()"+e.getMessage());
        }
        return false;
    }
    
    public boolean crearConciliacionAportes(double totalCortes, double totalAportes, double total){
        try {
            String strSql = " insert into selinlib.JCONCILIFP (PERIODO, TOT_CORTE, TOT_APORT, DIFERENCIA, USUARIO, FECHA) values "
                         + " ( '"+this.PPeriodo+"',"+totalCortes+","+totalAportes+","+total+",'"+this.Usuario+"','"+this.FechaUsuario+"')";
            return  this.JBase_Datos.Exc_Sql(Cn, strSql);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "crearConciliacionAportes(...) "+e.getMessage());
        }
        return false;
    }
    
    public double obtenerSaldoObraProgramas(String tipo){
        try {
            String strSql = "select * from selinlib.JSALDOPROG where "
                          + "tipo='"+tipo+"' and periodo="+this.PPeriodo;
            ResultSet result =  this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            if (result.next()){
                jTextField2.setText(this.JFormatoPeriodos.format(result.getDouble("VALORAPOR")));
                jTextField3.setText(this.JFormatoPeriodos.format(result.getDouble("DISTRIBU")));
                jTextField4.setText(this.JFormatoPeriodos.format(result.getDouble("SUB_ESPEC")));
                jTextField5.setText(this.JFormato.format(result.getDouble("SUB_DEMAN")));
                jTextField6.setText(this.JFormato.format(result.getDouble("SUB_OFERT")));
                jTextField17.setText(this.JFormatoPeriodos.format(result.getDouble("CUOTLIQUI")));
                jTextField18.setText(this.JFormatoPeriodos.format(result.getDouble("CUOTAPROP")));
                jTextField7.setText(this.JFormatoPeriodos.format(result.getDouble("TOTAL")));
                return result.getDouble("TOTAL");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return 0;
    }
    public double obtenerSaldoObraProgramasPrecripcionAportes(String tipo){
        try {
            String strSql = "select * from selinlib.JSALDOPROG where "
                          + "tipo='"+tipo+"' and periodo="+this.PPeriodo;
            ResultSet result =  this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            if (result.next()){
                jTextField8.setText(this.JFormatoPeriodos.format(result.getDouble("VALORAPOR")));
                jTextField9.setText(this.JFormatoPeriodos.format(result.getDouble("DISTRIBU")));
                jTextField10.setText(this.JFormatoPeriodos.format(result.getDouble("TOTAL")));
                return result.getDouble("TOTAL");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return 0;
    }
    
    public boolean eliminarSaldoObraProgramas(String tipo){
        try {
            String strSql = " delete from selinlib.JSALDOPROG"
                          + " where TIPO ='"+tipo+"' and periodo="+this.PPeriodo;
            boolean result  =  this.JBase_Datos.Exc_Sql(Cn, strSql);
            return result;
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return false;
    }
    
    public boolean crearControlSaldoObraProgramas(String tipo, double totalAportes, double totalDistribucion, double totalSubsidioEspecie, double subisidioDemanda, double  subsidioOferta, double cuotaLiquidada, double cuotaApropiada, double total){
        try {
            String strSql ="INSERT INTO selinlib.JSALDOPROG  "
                    + "(TIPO, PERIODO, VALORAPOR, DISTRIBU,SUB_ESPEC, SUB_DEMAN, SUB_OFERT, CUOTLIQUI, CUOTAPROP, TOTAL, USUARI, FECHA) "
                    + " VALUES('"+tipo+"'"+","+this.PPeriodo+","+totalAportes+","+totalDistribucion+","+totalSubsidioEspecie+","+subisidioDemanda+","+subsidioOferta+","+cuotaLiquidada+","+cuotaApropiada+","+total+",'"+this.Usuario+"','"+this.FechaUsuario+"')";
            boolean result = this.JBase_Datos.Exc_Sql(Cn, strSql);
            return result;
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return false;
    }
    
    public double obtenerSubsidioEspecie(){
        try {
            String strSql = "Select sum(debmov) - sum(cremov) as valor         \n" +
                          "from  CONDATNIC.MOVIMI                     \n" +
                          "where fecmov between "+this.PPeriodo+"01 and "+this.PPeriodo+"31 \n" +
                          "AND CTAMOV  LIKE '610610%'                 ";
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
          
            if(result.next()){
              return result.getDouble("valor");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return 0;
    }
    
    public double obtenerApropiacionLey(String tipo){
        try {
            String strSql = " select sum(valor) valor     \n" +
                            "  from selinlib.JDISCBNIFP   \n" +
                            "where periodo = "+this.PPeriodo+" and TIPO like '%"+tipo+"%'";
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            while(result.next()){
                return result.getDouble("valor");
            }
            result.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return 0;
    }
    
    public double obtenerTotalAportes(String tipo){
        try {
            String strSql = " select sum(valor) as valor                    \n" +
                            "        from selinlib.JDISINGIFP               \n" +
                            "where periodo = "+this.PPeriodo+" and tipo like '%"+tipo+"%' ";
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            while (result.next()){
                return result.getDouble("valor");
            }
            result.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return 0;
    }
    
    public boolean estaAbiertoPeriodo(){
        try {
            String strSql = " select * from SELINLIB.JFDISTRPER where periodo="+this.PPeriodo;
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            if(result.next()){
              return result.getString("estado").trim().equals("ABIERTO");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return  false;
    }
    
    public void obtenerIngresoTipo(String tipo){
        try {
            String strSql =" select * from selinlib.JDISINGIFP "
                         + "where periodo="+this.PPeriodo+" and tipo='"+tipo+"' ";
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            if (result.next()){
               jTextField1.setText(result.getString("valor"));
            }
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
    }
    public boolean registroIngresoTipo(String tipo, double valor){
        try {
            String strSql =" select * from selinlib.JDISINGIFP "
                         + "where periodo="+this.PPeriodo+" and tipo='"+tipo+"' ";
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            
            if (result.next()){
                strSql =" update selinlib.JDISINGIFP "
                        + " set valor="+valor
                        + ", usuari='"+this.Usuario+"' "
                        + ", fecha='"+this.FechaUsuario+"' "
                        + " where periodo="+this.PPeriodo+" and tipo='"+tipo+"' ";
                return this.JBase_Datos.Exc_Sql(Cn, strSql);
            }else{
               strSql =" insert into selinlib.JDISINGIFP (tipo, periodo, valor, usuari, fecha) values"
                       + " ('"+tipo+"',"+this.PPeriodo+","+valor+",'"+this.Usuario+"','"+this.FechaUsuario+"')"; 
               return this.JBase_Datos.Exc_Sql(Cn, strSql);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return false;
    }
    
    public void obtenerTipoDistribucion(String periodo, String tipo){
        try {
            String strSql ="  select * from selinlib.JDISCBNIFP "
                       + " where periodo="+periodo+" and tipo='"+tipo+"'";
            ResultSet result = this.JBase_Datos.SQL_QRY(Cn, strSql);
            this.filaTipo = new Vector();
          
            while(result.next()){
              this.detalleTipo = new Vector();
              this.detalleTipo.add(result.getString("CTACON"));
              this.detalleTipo.add(result.getString("DESCRIP"));
              this.detalleTipo.add(this.JFormatoPeriodos.format(result.getDouble("valor")));
              this.detalleTipo.add(result.getString("PORCEN"));
              this.filaTipo.add(this.detalleTipo);
            }
            result.close();
            jTable6.setModel(new javax.swing.table.DefaultTableModel(this.filaTipo, this.cabeceraTipo));
            jTable6.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable6.setAutoCreateRowSorter(true);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "obtenerTipoDistribucion() "+e.getMessage());
        }
    }
    
    public boolean deleteTipoDistribucion( String tipo , String periodo){
        try {
            String strSql =" delete from selinlib.JDISCBNIFP "
                    + " where periodo="+periodo+" and tipo='"+tipo+"'";
            return JBase_Datos.Exc_Sql(Cn, strSql);
        } catch (Exception e) {
           javax.swing.JOptionPane.showMessageDialog(this, "deleteTipoDistribucion() "+e.getMessage());
        }
        return false;
    }
    
    public boolean insertarTipoDistribucion(String cuentaContable, String tipo, String periodo, String descrip, double valor, double porcen, int verifi, String usuari, String fecha){
        try {
            String strSql=" insert into selinlib.JDISCBNIFP (ctacon, tipo, periodo, descrip, valor, porcen, verifi, usuari, fecha) values "
                          + "("+cuentaContable+",'"+tipo+"',"+periodo+",'"+descrip+"',"+valor+","+porcen+","+verifi+",'"+usuari+"','"+fecha+"')";
            return JBase_Datos.Exc_Sql(Cn, strSql);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " ");
        }
        return false;
    }
    
    public void calculoDistribucion(double valorAportes, String Tipo){
        try {
            String strSql = "select * from selinlib.JDISTRNIFP as t1 where estado ='ACTIVA' order by descrip asc";
            ResultSet result= JBase_Datos.SQL_QRY(this.Cn,strSql);
            this.filaTipo= new Vector();
            jCheck = new javax.swing.JComboBox();
            jCheck.addItem("");
            jCheck.addItem("Si");
            jCheck.addItem("No");
            this.deleteTipoDistribucion(Tipo,this.PPeriodo);
            
            while(result.next()){
                this.detalleTipo = new Vector();
                this.detalleTipo.add(result.getString("CTACON"));
                this.detalleTipo.add(result.getString("DESCRIP"));
                double vlr = (result.getDouble("PORCEN")*valorAportes)/100;
                this.detalleTipo.add( this.JFormatoPeriodos.format(vlr));
                this.detalleTipo.add(result.getString("PORCEN"));
                this.filaTipo.add(this.detalleTipo);
                this.insertarTipoDistribucion(result.getString("CTACON"),Tipo,this.PPeriodo, result.getString("DESCRIP"), vlr, result.getDouble("PORCEN"), 0, this.Usuario, this.FechaUsuario);  
            }
            jTable6.setModel(new javax.swing.table.DefaultTableModel(this.filaTipo, this.cabeceraTipo));
        
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " calculoDistribucion(double totalAporte, String Tipo) "+e.getMessage());
        }
    }
    
    public void Save_CDTs(){
        try {
            String Str_Sql ="select * from selinlib.JFCDT where NROCDT ="+jCDT_Nro.getText();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(rs.next()){
                Str_Sql ="update selinlib.JFCDT "
                        +" set ctacon= '"+jCDT_Cuenta.getSelectedItem().toString().trim()+"'"
                        +" , fecmov=20160518"
                        +" , fecape="+jCDT_Fec_Aper.getText().trim()
                        +" , feccie="+jCDT_Fec_Cierre.getText().trim()
                        +" , valor="+jCDT_Valor.getText().trim()
                        +" , periodo="+jCDT_Periodo.getSelectedItem().toString().trim()
                        +" , estado='"+jCDT_Estado.getSelectedItem().toString().trim()+"'"
                        + "where nrocdt='"+jCDT_Nro.getText().trim()+"'";
            }else{
                Str_Sql ="insert  into selinlib.JFCDT (nrocdt, ctacon, fecmov, fecape, feccie, valor, periodo, estado) values('"+
                        jCDT_Nro.getText().trim()+"','"+jCDT_Cuenta.getSelectedItem().toString().trim()+"',"
                        +"20160518,"+jCDT_Fec_Aper.getText().trim()+","+jCDT_Fec_Cierre.getText().trim()+","
                        +jCDT_Valor.getText().trim()+","+jCDT_Periodo.getSelectedItem().toString().trim()+",'"+jCDT_Estado.getSelectedItem().toString().trim()
                        +"')";
            }
            boolean Guardar = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
        
            if(Guardar){
                JOptionPane.showMessageDialog(this,"Se Guardo Correctamente");     
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," Save_CDTs() "+e.getMessage()); 
        }
    }
    
    public void Cab_CDTs_(){
        Cab_CDTs.clear();
        Cab_CDTs.add("Nro");
        Cab_CDTs.add("Cuenta");
        Cab_CDTs.add("Fondo");
        Cab_CDTs.add("Feha Mov");
        Cab_CDTs.add("Fecha Apertura");
        Cab_CDTs.add("Fecha Cierre");
        Cab_CDTs.add("Valor");
        Cab_CDTs.add("Periodo");
        Cab_CDTs.add("Estado");
    }
    
    public void get_CDTS_(){
        try {
            String Str_Sql ="";
            this.Cab_CDTs_();
            
            if (jComboBox1.getSelectedItem().toString().trim().equals("Todos")){
                Str_Sql ="select * from selinlib.JFCDT";
            }else{
                
                if (jComboBox1.getSelectedItem().toString().trim().equals("Activo")){
                    Str_Sql ="select * from selinlib.JFCDT where Estado='Activo'";
                }else{
                    
                    if (jComboBox1.getSelectedItem().toString().trim().equals("Inactivo")){
                        Str_Sql ="select * from selinlib.JFCDT where Estado='Inactivo'";
                    }
                }
            }
            
            if(!Str_Sql.trim().equals("")){
               ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
               Det_CDTs.clear();
               
               while(rs.next()){
                   Fila_CDTs = new Vector();
                   Fila_CDTs.add(rs.getString("NROCDT"));
                   Fila_CDTs.add(rs.getString("CTACON"));
                   Fila_CDTs.add("");
                   Fila_CDTs.add(rs.getInt("FECMOV"));
                   Fila_CDTs.add(rs.getInt("FECAPE"));
                   Fila_CDTs.add(rs.getInt("FECCIE"));
                   Fila_CDTs.add(rs.getDouble("VALOR"));
                   Fila_CDTs.add(rs.getInt("PERIODO"));
                   Fila_CDTs.add(rs.getString("ESTADO"));
                   Det_CDTs.add(Fila_CDTs);
               }
               jTable5.setModel(new javax.swing.table.DefaultTableModel(Det_CDTs, Cab_CDTs)); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," get_CDTS_()  "+e.getMessage()); 
        }
    }
    
    public void setCabecera_Conciliacion(){
        this.CabConciliacion.add("Cuenta Contable");
        this.CabConciliacion.add("Cuenta Financiera");
        this.CabConciliacion.add("Descripcion");
        this.CabConciliacion.add("Neto Financiero");
        this.CabConciliacion.add("Neto Contable");
        this.CabConciliacion.add("Neto Calculado");
        this.CabConciliacion.add("Diferencias");
    }
    
    public void getInformacion_Conciliacion(){
        try {
            String Str_Sql = "select * from SELINLIB.JDISDIFNIF WHERE period="+this.PPeriodo;
            ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.FilaConciliacion.clear();
            
            while(Rs.next()){
                this.DetConciliacion = new Vector();
                this.DetConciliacion.add(Rs.getString("CTACON"));
                this.DetConciliacion.add(Rs.getString("CTAFIN"));
                this.DetConciliacion.add(Rs.getString("DESCRI"));
                this.DetConciliacion.add(this.JFormato.format(Rs.getDouble("NTOBAN")));
                this.DetConciliacion.add(this.JFormato.format(Rs.getDouble("NTOCON")));
                this.DetConciliacion.add(this.JFormato.format(Rs.getDouble("NTOCAL")));
                this.DetConciliacion.add("+");
                this.FilaConciliacion.add(this.DetConciliacion);
            }
            Rs.close();
            this.jTable4.setModel(new javax.swing.table.DefaultTableModel(this.FilaConciliacion, this.CabConciliacion));
            this.setAnchoColumnas_Tabla4();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getInformacion_Conciliacion(++) "+e.getMessage()); 
        }
    }
    
    public boolean getConciliar(){
        boolean Rs=false;
        try {
            String Str_Sql ="DELETE FROM selinlib.JDISDIFNIF WHERE PERIOD="+this.PPeriodo;
            Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);                                           

            Str_Sql = "INSERT INTO selinlib.JDISDIFNIF "                                  
                             +" SELECT T1.CTACON , CTAFIN , DESCRIP ,BANCO, NTOSIS, NTOCAL ,"+this.PPeriodo +",'"+this.Usuario+"','"+this.FechaUsuario+"'"     
                             +" FROM ( "      
                             +" SELECT CUENTA , CTAFIN , CTACON  ,  "       
                             +" SUM(DEBITO)-SUM(CREDIT) BANCO FROM SELINLIB.JDISTRINIF AS T2  "                                                  
                             +" LEFT JOIN SELINLIB.JDISBANNIF AS B "       
                             +" ON (CTAFIN=  CHAR(CUENTA)   ) "    
                             +" WHERE MARCA='CN' AND B.PERIODO ="+this.PPeriodo+" AND CTACON <> 520520 and CTACON <> 522010  "
                             +" GROUP BY CUENTA  , CTAFIN , CTACON  ) AS T1 "
                             +" LEFT JOIN SELINLIB.JDISCABNIF AS T2 "
                             +" ON (T1.CTACON =T2.CTACON) "
                             +" WHERE PERIODO = "+this.PPeriodo ;
             
             Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql); 
             //Agrupacion fosfec
             Str_Sql = "INSERT INTO selinlib.JDISDIFNIF "
                    +" select CUENAGR ,CUENFIN, DESCRIP, ntoban , ntocon, ntocal, "     
                    +" periodo  "+",'"+this.Usuario+"','"+this.FechaUsuario+"'"      
                     + " from  "                                       
                    +" (select cuenagr , CUENFIN, "                                     
                    +" t1.descrip , sum(ntosis) ntocon , sum(ntocal) ntocal  ,periodo "
                    +" from selinlib.JDISAGRNIF as t1 , selinlib.JDISCABNIF as t2 "    
                    +" where t1.cuecon=t2.ctacon "                                     
                    +" and periodo = "+this.PPeriodo                                      
                    +" group by cuenagr , t1.descrip , periodo  ,CUENFIN "             
                    +"  ) as t1 , (select  cuenta, sum(debito) ntoban   "                
                    +"  from selinlib.JDISBANNIF as tt "          
                    +"  where cuenta in( 1810350201 , 112005117, 112005121) and periodo="+this.PPeriodo                                       
                    +" group by cuenta ) as t2 "                                      
                    +" where t1.cuenfin=t2.cuenta  ";
             Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql); 

             //Cambio de Norma 2018/04/25
             // FOSFEC
             Str_Sql="select * from selinlib.JDISAGRNIF where Marca = 'X' ";
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

             while(rs.next()){
                    String Cuenta_Financiera = rs.getString("CUENFIN");
                    String Cuenta_Agrupa = rs.getString("CUENAGR");
                    String Cuenta_con = rs.getString("CUECON");
                    this.getTotal_Financiero(Cuenta_Financiera);
                    NetoSistema=0;
                    NetoCalculado=0;
                    get_Calculo_Porcentaje(Cuenta_con , this.PPeriodo);
                    Str_Sql ="select * from selinlib.JDISDIFNIF where PERIOD='"+this.PPeriodo+"' and ctacon='"+Cuenta_Agrupa.trim()+"'";
                    ResultSet resultado = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

                    if(resultado.next()){
                        Str_Sql= "update selinlib.JDISDIFNIF set "
                             + " ntoban=ntoban+"+(this.DebFin - this.CredFin)+""
                             + " , ntocon= ntocon+"+NetoSistema
                             + " , ntocal= ntocal+"+NetoCalculado   
                             + " where Period='"+this.PPeriodo+"'"
                             + " and ctacon='"+Cuenta_Agrupa.trim()+"' ";
                        
                        Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql); 
                    }else{
                        //
                        //
                        //String SQL =" insert into selinlib.JDISDIFNIF (CTACON,CTAFIN, DESCRI,NTOBAN, NTOCON, NTOCAL, PERIOD,USUARI,FECHA)"
                        //    + " VALUES ('"+Cuenta_Agrupa.trim()+"','"+Cuenta_Financiera.trim()+"' ,'ADMINISTRACION',"+(PDeb-PCre)+","+NetoSistema+","+NetoCalculado
                        //    +",'"+this.PPeriodo+"','"+this.Usuario+"','"+this.FechaUsuario+"')";
                        //Rs = JBase_Datos.Exc_Sql(this.Cn,SQL); 
                   }
             }
             rs.close();

             //GASTOS ADMINISTRATIVOS
             Str_Sql="select * from selinlib.JDISAGRNIF where Marca = 'A'";
             ResultSet res = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             double PDeb=0, PCre=0;
             String Cuenta_Agrupa="";
             String Cuenta_Financiera="";

             while(res.next()){
                Cuenta_Financiera = res.getString("CUENFIN");
                Cuenta_Agrupa = res.getString("CUENAGR");
                this.getTotal_Financiero(Cuenta_Financiera);
                PDeb =PDeb+this.DebFin;
                PCre = PCre+this.CredFin;
             }

             if(!Cuenta_Agrupa.equals("")){
                NetoSistema=0;
                NetoCalculado=0;
                get_Calculo_Porcentaje(Cuenta_Agrupa ,this.PPeriodo);
                //Buscar en la Tabla JDISCABNIF
                String SQL =" insert into selinlib.JDISDIFNIF (CTACON,CTAFIN, DESCRI,NTOBAN, NTOCON, NTOCAL, PERIOD,USUARI,FECHA)"
                       + " VALUES ('"+Cuenta_Agrupa.trim()+"','"+Cuenta_Financiera.trim()+"' ,'ADMINISTRACION',"+(PDeb-PCre)+","+NetoSistema+","+NetoCalculado
                       +",'"+this.PPeriodo+"','"+this.Usuario+"','"+this.FechaUsuario+"')";
                Rs = JBase_Datos.Exc_Sql(this.Cn,SQL); 
             }
             res.close();


             //CUOTA MONETARIA
             Str_Sql="select * from selinlib.JDISAGRNIF where Marca = 'B'";
             ResultSet res2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             PDeb=0; PCre=0;
             Cuenta_Agrupa="";
             Cuenta_Financiera="";

             while(res2.next()){
                 Cuenta_Financiera = res2.getString("CUENFIN");
                 Cuenta_Agrupa = res2.getString("CUENAGR");
                 this.getTotal_Financiero(Cuenta_Financiera);
                 PDeb =PDeb+this.DebFin;
                 PCre = PCre+this.CredFin;
             }

             if(!Cuenta_Agrupa.equals("")){
                 NetoSistema=0;
                 NetoCalculado=0;
                 get_Calculo_Porcentaje(Cuenta_Agrupa ,this.PPeriodo);
                 //Buscar en la Tabla JDISCABNIF
                 String SQL =" insert into selinlib.JDISDIFNIF (CTACON,CTAFIN, DESCRI,NTOBAN, NTOCON, NTOCAL, PERIOD,USUARI,FECHA)"
                        + " VALUES ('"+Cuenta_Agrupa.trim()+"','"+Cuenta_Financiera.trim()+"' ,'CUOTA MONETARIA',"+(PDeb-PCre)+","+NetoSistema+","+NetoCalculado
                        +",'"+this.PPeriodo+"','"+this.Usuario+"','"+this.FechaUsuario+"')";
                 Rs = JBase_Datos.Exc_Sql(this.Cn,SQL); 
             }
             res2.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getConciliar()  "+e.getMessage()); 
        }
        return Rs;
    }
     
    public void get_Calculo_Porcentaje(String CtaCon , String Periodo){
        try {
            String Str_Sql = "select * from  selinlib.JDISCABNIF where Periodo="+Periodo+" and ctacon='"+CtaCon+"' ";
            ResultSet rs_cal = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(rs_cal.next()){
                NetoCalculado = rs_cal.getDouble("NTOCAL");
                NetoSistema   = rs_cal.getDouble("NTOSIS");
            }
            rs_cal.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"get_Calculo_Porcentaje(String CtaCon , String Periodo)  "+e.getMessage()); 
        }
    }
    
    public void getTotal_Financiero(String Cuenta_Financiera){
        try {
            String Str_Sql = "select cuenta , sum(debito) as debito, sum(Credit) as credito from "
                      + " selinlib.JDISBANNIF"
                      + " where periodo="+this.PPeriodo
                      + " and cuenta="+Cuenta_Financiera
                      + " group  by cuenta";
            ResultSet rs_fin = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.DebFin =0;
            this.CredFin =0;

            if(rs_fin.next()){
                DebFin =rs_fin.getDouble("debito");
                CredFin =rs_fin.getDouble("credito");
            }
            rs_fin.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getTotal_Financiero(String Cuenta_Financiera)  "+e.getMessage()); 
        }
    }
    
    public void setAnchoColumnas_Tabla1(){
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(230);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(10);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(10);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(10);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(10);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(10);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(10);
    }
    
    public void setAnchoColumnas_Tabla4(){
        jTable4.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTable4.getColumnModel().getColumn(1).setPreferredWidth(30);
        jTable4.getColumnModel().getColumn(2).setPreferredWidth(230);
        jTable4.getColumnModel().getColumn(3).setPreferredWidth(10);
        jTable4.getColumnModel().getColumn(4).setPreferredWidth(10);
        jTable4.getColumnModel().getColumn(5).setPreferredWidth(10);
        jTable4.getColumnModel().getColumn(6).setPreferredWidth(10);
    }
    
    public boolean getExiste_Distribucion(String Cuenta , String Periodo){
        boolean Control = false;
        try {
            String Str_Sql =" select * from selinlib.JDISCABNIF where Ctacon="+Cuenta+" and Periodo="+Periodo;
            ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(Rs.next()){
                Control=true;
            }
            Rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getExiste_Distribucion()  "+e.getMessage()); 
        }
        return Control;
    }
    
    public void getInformacion_Distribucion(){
        try {
            String Str_Sql = "select * from selinlib.JDISCABNIF where periodo="+this.PPeriodo;
            ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

            while(Rs.next()){
                this.Detalle = new Vector();
                this.Detalle.add(Rs.getString("ctacon"));
                this.Detalle.add(Rs.getString("descrip"));
                this.Detalle.add(this.JFormato.format( Rs.getDouble("debsis") ) );
                this.Detalle.add(this.JFormato.format( Rs.getDouble("cresis")));
                this.Detalle.add(this.JFormato.format( Rs.getDouble("ntosis")));
                this.Detalle.add(Rs.getDouble("porcen"));
                this.Detalle.add(this.JFormato.format( Rs.getDouble("ntocal")));
                this.Detalle.add(this.JFormato.format( Rs.getDouble("difere")));
                this.Fila.add(this.Detalle);
            }
            this.jTable1.setModel(new javax.swing.table.DefaultTableModel(Fila, Cabecera));
            this.jTable1.setAutoCreateRowSorter(true);
            Rs.close();
            this.setAnchoColumnas_Tabla1();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getInformacion_Distribucion()  "+e.getMessage()); 
        }
    }
    
    public boolean Guardar_Distribucion(){
        boolean Rs=false;
        try {
            int Cantidad = jTable1.getRowCount();
            
            for (int i = 0; i < Cantidad; i++) {
                String Cuenta = this.jTable1.getValueAt(i, 0).toString();
                String Descrip = this.jTable1.getValueAt(i, 1).toString();
                double DebSis = Double.valueOf(this.jTable1.getValueAt(i, 2).toString());        
                double CreSis = Double.valueOf(this.jTable1.getValueAt(i, 3).toString());        
                double NetoSis = Double.valueOf(this.jTable1.getValueAt(i, 4).toString());
                double Porcentaje = Double.valueOf(this.jTable1.getValueAt(i, 5).toString());
                double VlrCalculado = Double.valueOf(this.jTable1.getValueAt(i, 6).toString());
                double Diferencias = Double.valueOf(this.jTable1.getValueAt(i, 7).toString());
                
                if (!this.getExiste_Distribucion(Cuenta, this.PPeriodo)){
                    String Str_Sql ="Insert into selinlib.JDISCABNIF "
                                    + " VALUES ('"+Cuenta+"' , '"+Descrip+"' ,"+CreSis+" , "+DebSis+" , "
                                    +NetoSis+" ,"+Porcentaje+", "+VlrCalculado+", "+Diferencias+","+this.PPeriodo+",'"+this.Usuario+"','"+this.FechaUsuario+"')";
                    Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                }else{
                    String Str_Sql= "update selinlib.JDISCABNIF "
                                    + "set cresis="+CreSis
                                    + ", debsis="+DebSis
                                    + ", ntosis="+NetoSis
                                    + ", porcen="+Porcentaje
                                    + ", ntocal="+VlrCalculado
                                    + ", difere="+Diferencias
                                    + ", usuari='"+this.Usuario+"'"
                                    + ", fecha='"+this.FechaUsuario+"'"
                                    +" where Ctacon="+Cuenta+" and Periodo="+this.PPeriodo;
                    Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Guardar_Distribucion()  "+e.getMessage()); 
        }
        return Rs;
    }
     
    public boolean EliminaDetalle(){
        boolean Rs=false;
        try {
            String Cuenta = jTable3.getValueAt(this.jTable3.getSelectedRow(), 0).toString();
            String Fecha = jTable3.getValueAt(this.jTable3.getSelectedRow(), 1).toString();
            String NumDocumento = jTable3.getValueAt(this.jTable3.getSelectedRow(), 2).toString();
            String Periodo = jTable3.getValueAt(this.jTable3.getSelectedRow(), 5).toString();
            String Str_Sql = "delete from selinlib.JDISBANNIF where fecha="+Fecha+" and numdoc="+NumDocumento+
                " and periodo="+Periodo+" and cuenta="+Cuenta;    
            Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"EliminaDetalle()  "+e.getMessage()); 
        }
        return Rs;
    }
    
    public void setBorrarFila_tabla3(){
       try{
            if (EliminaDetalle()){
               DefaultTableModel Modelo =(DefaultTableModel) jTable3.getModel();
               Modelo.removeRow(jTable3.getSelectedRow());
               jTable3.updateUI();
            }
       }catch(Exception e){
           JOptionPane.showMessageDialog(this,"El registro seleccionado debe Contener informacion"); 
       }
    }
    
    public boolean getExiste(String NumDocumento, String Periodo, String Cuenta, String Fecha){
        boolean Control = false;
        try {
            String Str_Sql = "select * from selinlib.JDISBANNIF where fecha="+Fecha+" and numdoc="+NumDocumento+
                    " and periodo="+Periodo+" and cuenta="+Cuenta;    
            ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(Rs.next()){
                Control=true;
            }
            Rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getExiste() "+e.getMessage() );
        }
        return Control;
    }
    
    public void getTablaConciliacion(String Periodo){
        try {
            String Str_Sql = "select * from selinlib.JDISBANNIF where Periodo="+Periodo;
            ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
           
            while(Rs.next()){
                DetBancoInsert = new Vector();
                DetBancoInsert.add(Rs.getInt("Cuenta"));
                DetBancoInsert.add(Rs.getInt("Fecha"));
                DetBancoInsert.add(Rs.getDouble("Numdoc"));
                DetBancoInsert.add(this.JFormato.format(Rs.getDouble("Credit")));
                DetBancoInsert.add(this.JFormato.format(Rs.getDouble("Debito")));
                DetBancoInsert.add(Rs.getInt("Periodo"));
                FilaBancoInsert.add(DetBancoInsert);
            }
            this.jTable3.setModel(new javax.swing.table.DefaultTableModel(this.FilaBancoInsert, this.CabBancoInsert));
            jTable1.setAutoCreateRowSorter(true);
            Rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getTablaConciliacion()-1 ** "+e.getMessage() );
        }
    }

    public boolean Guardar(){
        boolean Rs=false;
        try {
            int Cantidad =  jTable3.getRowCount();
            
            for (int i = 0; i < Cantidad; i++) {
                String Cuenta = jTable3.getValueAt(i, 0).toString();
                String Fecha = jTable3.getValueAt(i, 1).toString();
                String NumDocumento = jTable3.getValueAt(i, 2).toString();
                double Credito = Double.valueOf( jTable3.getValueAt(i, 3).toString());
                double Debito = Double.valueOf( jTable3.getValueAt(i, 4).toString());
                String Periodo = jTable3.getValueAt(i, 5).toString();

                if (!this.getExiste(NumDocumento, Periodo, Cuenta, Fecha)){
                    String Str_Sql ="insert into selinlib.JDISBANNIF"
                                    + " VALUES("+Cuenta+" , "+Periodo+" , "+Fecha+" , "+NumDocumento+" , "+Debito+" , "
                                    + ""+Credito+", '"+this.Usuario+"',"+FechaUsuario+")";
                    Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
                    if(Rs==false){
                        JOptionPane.showMessageDialog(this,"No se pudo insertar " ); 
                    }
                }else{
                    String Str_Sql ="update selinlib.JDISBANNIF"
                                    + " set Debito="+Debito
                                    + ", Credit="+Credito
                                    + ", Usuari='"+Usuario+"' "
                                    + ", Fecusu="+FechaUsuario
                                    + " where fecha="+Fecha+" and Periodo="+Periodo+" and Cuenta="+Cuenta+" and numdoc="+NumDocumento;
                    Rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                
                    if(Rs==false){
                        JOptionPane.showMessageDialog(this,"No se pudo Actualizar " ); 
                    }
                }
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Guardar() "+e.getMessage() );
        }
        return Rs;
    }
    
    public void getCabecera(){
        this.Cabecera.add("Cuenta Sistema");
        this.Cabecera.add("Cta Aportes");
        this.Cabecera.add("Debito-Contable");
        this.Cabecera.add("Credito-Contable");
        this.Cabecera.add("Neto-Contable");
        this.Cabecera.add("Porcentaje");
        this.Cabecera.add("Valor-Calculado");
        this.Cabecera.add("Diferencias");
    }
    
    public double obtenerAporteContable(){
        try {
            String Str_Sql = "select sum(cremov) as cred , sum(debmov)as deb, sum(cremov)-sum(debmov) as Neto from CONDATNIC.movimi where fecmov>="+this.FechaIncial
                             +" and fecmov<="+this.FechaFinal
                             +" and ctamov like '410505%'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if (rs.next())
                return rs.getDouble("Neto");
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, ""+e.getMessage());
        }
        return 0;
    }
    
    public void getCuentAportes(){
        try {
            String Str_Sql = "select sum(cremov) as cred , sum(debmov)as deb, sum(cremov)-sum(debmov) as Neto from CONDATNIC.movimi where fecmov>="+this.FechaIncial
                             +" and fecmov<="+this.FechaFinal
                             +" and ctamov like '410505%'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(rs.next()){
               Detalle = new Vector();
               Detalle.add("410505");
               Detalle.add("Cta de Aportes");
               Detalle.add(this.JFormato.format(rs.getDouble("cred")));
               Detalle.add(this.JFormato.format(rs.getDouble("deb")));
               this.TotalIngresos = rs.getDouble("Neto");
               Detalle.add(this.JFormato.format(this.TotalIngresos));
               Detalle.add(0);
               Detalle.add(0);
               Detalle.add(0);
               Fila.add(Detalle);
            }
            this.jTable1.setModel(new javax.swing.table.DefaultTableModel(Fila, Cabecera));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getCuentAportes() "+e.getMessage());  
        }
    }
    
    public void getVlrPersonas_Mayores_Edad_19_a_23(double Vlr){
        try {
            String Str_Sql = "select * from selinlib.JDISTRINIF where estado ='TOTAL1'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            while(rs.next()){
                String PCuenta = rs.getString("CTACON");
                String NombreCta = rs.getString("DESCRIP");
                double Porcentaje = rs.getDouble("PORCEN");
                Str_Sql = "select sum(cremov) as cred , sum(debmov)as deb, sum(cremov)-sum(debmov) as Neto from CONDATNIC.movimi where fecmov>="+this.FechaIncial
                         +" and fecmov<="+this.FechaFinal
                         +" and ctamov like '"+PCuenta+"%'";
                ResultSet Rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                
                if(Rs2.next()){ 
                    Detalle = new Vector();
                    Detalle.add(PCuenta);
                    Detalle.add(NombreCta);
                    Detalle.add(this.JFormato.format(Rs2.getDouble("deb")));
                    Detalle.add(this.JFormato.format(Rs2.getDouble("cred")));
                    double Valor = Rs2.getDouble("deb");
                    Detalle.add(this.JFormato.format(Valor));
                    Detalle.add(Porcentaje);
     
                    double Calculado = Vlr*(Porcentaje/100);
                    Detalle.add(this.JFormato.format(Calculado));
                    double Diferencia = Valor - Calculado;
                    Detalle.add(this.JFormato.format(Diferencia));
                    Fila.add(Detalle);
              }
            }
            this.jTable1.setModel(new javax.swing.table.DefaultTableModel(Fila, Cabecera));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getVlrPersonas_Mayores_Edad_19_a_23() "+e.getMessage());  
        }
    }
    
    public void getSubsidioEducacion_Ley_115(double Vlr){
        try {
            String Str_Sql = "select * from selinlib.JDISTRINIF where estado ='TOTAL2'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

            while(rs.next()){
                String PCuenta = rs.getString("CTACON").trim();  
                String NomCta = rs.getString("DESCRIP");  
                double Porcentaje =  rs.getDouble("PORCEN");  
                Str_Sql = "select sum(cremov) as cred , sum(debmov)as deb, sum(cremov)-sum(debmov) as Neto from CONDATNIC.movimi where fecmov>="+this.FechaIncial
                             +" and fecmov<="+this.FechaFinal
                             +" and ctamov like '"+PCuenta+"%'";
                ResultSet Rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

                if(Rs2.next()){  
                    Detalle = new Vector();
                    Detalle.add(PCuenta);
                    Detalle.add(NomCta);
                    Detalle.add(this.JFormato.format(Rs2.getDouble("deb")));//Debito
                    Detalle.add(this.JFormato.format(Rs2.getDouble("cred")));//Credito
                    double RContable= Rs2.getDouble("deb");
                    Detalle.add(this.JFormato.format(RContable));//Neto
                    Detalle.add(Porcentaje);//Porcentaje
                    double Calculado  = Vlr;
                    Detalle.add( this.JFormato.format(Calculado));//Valor
                    double Diferencias = Calculado - RContable;
                    Detalle.add(this.JFormato.format(Diferencias));
                    Fila.add(Detalle);
                }
           }
           this.jTable1.setModel(new javax.swing.table.DefaultTableModel(Fila, Cabecera));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getSubsidioEducacion_Ley_115(double Vlr) "+e.getMessage());  
        }
    }
    
    public void getSaldoApotes(){
        try {
            String Str_Sql = "select * from selinlib.JDISTRINIF where estado ='DEDU1'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            while(rs.next()){
                double Porcentaje = rs.getDouble("PORCEN");
                double Calc_71 = this.TotalIngresos*(Porcentaje/100);
                Str_Sql = "select * from selinlib.JDISTRINIF where estado ='DEDU2'";
                ResultSet rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                
                if(rs2.next()){
                    double XPorcentaje = rs2.getDouble("PORCEN");
                    double deduccion2 = Calc_71 *(XPorcentaje/100);
                    double Total2 = (Calc_71 -deduccion2)*0.10;
                    this.getSubsidioEducacion_Ley_115(Total2);
                } 
                getVlrPersonas_Mayores_Edad_19_a_23(Calc_71);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getSaldoApotes() "+e.getMessage());  
        }
    }
    
    public void getDistribucionGeneral(){
        try {
            String Str_Sql = "select * from selinlib.JDISTRINIF where estado ='ACTIVA'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            while(rs.next()){
                String PCuenta = rs.getString("CTACON").trim();  
                String NomCta = rs.getString("DESCRIP");  
                double Porcentaje =  rs.getDouble("PORCEN");  
                Str_Sql="select * from selinlib.JFPORCEAGR where CUENAGR='"+PCuenta.trim()+"' ";
                ResultSet resultado = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                boolean Control=false;
                DebAgr =0; CreAgr=0;
               
                while(resultado.next()){
                   Control=true;
                   getMovimiento_Contable(resultado.getString("cuecon"),resultado.getString("CCOSTO"));
                }
                resultado.close();
                Str_Sql = "select sum(cremov) as cred , sum(debmov)as deb, sum(cremov)-sum(debmov) as Neto from CONDATNIC.movimi where fecmov>="+this.FechaIncial
                                      +" and fecmov<="+this.FechaFinal
                                      +" and ctamov like '"+PCuenta.trim()+"%'";
              
                ResultSet Rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

                if(Rs2.next()){  
                    Detalle = new Vector();
                    Detalle.add(PCuenta);
                    Detalle.add(NomCta);

                    if(Control){
                        Detalle.add(this.JFormato.format(DebAgr));//Debito
                        Detalle.add(this.JFormato.format(CreAgr));//Credito
                        double RContable= DebAgr-CreAgr;
                        Detalle.add(this.JFormato.format(RContable));//Neto
                        Detalle.add(Porcentaje);//Porcentaje
                        double Calculado  = (Porcentaje/100)*this.TotalIngresos;
                        Detalle.add( this.JFormato.format(Calculado));//Valor
                        double Diferencias = Calculado - RContable;
                        Detalle.add(this.JFormato.format(Diferencias));
                   }else{
                        Detalle.add(this.JFormato.format(Rs2.getDouble("deb")));//Debito
                        Detalle.add(this.JFormato.format(Rs2.getDouble("cred")));//Credito
                        double RContable= Rs2.getDouble("deb") - Rs2.getDouble("cred");
                        Detalle.add(this.JFormato.format(RContable));//Neto
                        Detalle.add(Porcentaje);//Porcentaje
                        double Calculado  = (Porcentaje/100)*this.TotalIngresos;
                        Detalle.add( this.JFormato.format(Calculado));//Valor
                        double Diferencias = Calculado - RContable;
                        Detalle.add(this.JFormato.format(Diferencias));
                    }
                    Fila.add(Detalle);
                }
                 Rs2.close();
            }
            this.jTable1.setModel(new javax.swing.table.DefaultTableModel(Fila, Cabecera));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getDistribucionGeneral() "+e.getMessage());  
        }
        
    }
    
    public void distribucionAportesPeriodos(double valorAport){
        try {
            String Str_Sql = "select * from selinlib.JDISTRINIF where estado ='ACTIVA'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            while(rs.next()){
                String PCuenta = rs.getString("CTACON").trim();  
                String NomCta = rs.getString("DESCRIP");  
                double Porcentaje =  rs.getDouble("PORCEN");  
                Str_Sql="select * from selinlib.JFPORCEAGR where CUENAGR='"+PCuenta.trim()+"' ";
                ResultSet resultado = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                boolean Control=false;
                DebAgr =0; CreAgr=0;
               
                while(resultado.next()){
                   Control=true;
                   getMovimiento_Contable(resultado.getString("cuecon"),resultado.getString("CCOSTO"));
                }
                resultado.close();
                Str_Sql = "select sum(cremov) as cred , sum(debmov)as deb, sum(cremov)-sum(debmov) as Neto from CONDATNIC.movimi where fecmov>="+this.FechaIncial
                                      +" and fecmov<="+this.FechaFinal
                                      +" and ctamov like '"+PCuenta.trim()+"%'";
              
                ResultSet Rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

                if(Rs2.next()){  
                    Detalle = new Vector();
                    Detalle.add(PCuenta);
                    Detalle.add(NomCta);
                    
                    if(Control){
                        Detalle.add(this.JFormato.format(DebAgr));//Debito
                        Detalle.add(this.JFormato.format(CreAgr));//Credito
                        double RContable= DebAgr-CreAgr;
                        Detalle.add(this.JFormato.format(RContable));//Neto
                        Detalle.add(Porcentaje);//Porcentaje
                        double Calculado  = (Porcentaje/100)*this.TotalIngresos;
                        Detalle.add( this.JFormato.format(Calculado));//Valor
                        double Diferencias = Calculado - RContable;
                        Detalle.add(this.JFormato.format(Diferencias));
                    }else{
                        Detalle.add(this.JFormato.format(Rs2.getDouble("deb")));//Debito
                        Detalle.add(this.JFormato.format(Rs2.getDouble("cred")));//Credito
                        double RContable= Rs2.getDouble("deb") - Rs2.getDouble("cred");
                        Detalle.add(this.JFormato.format(RContable));//Neto
                        Detalle.add(Porcentaje);//Porcentaje
                        double Calculado  = (Porcentaje/100)*this.TotalIngresos;
                        Detalle.add( this.JFormato.format(Calculado));//Valor
                        double Diferencias = Calculado - RContable;
                        Detalle.add(this.JFormato.format(Diferencias));
                    }
                    Fila.add(Detalle);
                }
                Rs2.close();
           }
           this.jTable1.setModel(new javax.swing.table.DefaultTableModel(Fila, Cabecera));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getDistribucionGeneral() "+e.getMessage());  
        }
    }
    
    public void getMovimiento_Contable(String PCuenta, String Tiene_centro_Costo){
        try {
            String Str_Sql2="";
            
            if(Tiene_centro_Costo.trim().equals("N") ) {
                Str_Sql2 = "select sum(cremov) as cred , sum(debmov)as deb, sum(cremov)-sum(debmov) as Neto from CONDATNIC.movimi where fecmov>="+this.FechaIncial
                                      +" and fecmov<="+this.FechaFinal
                                      +" and ctamov like '"+PCuenta.trim()+"%'";
            }else{
                Str_Sql2 = "select 51 , sum(debmov)  as deb , sum(cremov) as cred   \n" +
                            " from CONDATNIC.movimi                  \n" +
                            " where fecmov>= "+this.FechaIncial+"  and fecmov <= " +this.FechaFinal+
                            " and ctamov like '"+PCuenta.trim()+"%' and cenmov like '"+Tiene_centro_Costo.trim()+"%' ";   
            }
            ResultSet Rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql2);
            
            if(Rs2.next()){  
               DebAgr= DebAgr+Rs2.getDouble("deb");
               CreAgr = CreAgr+ Rs2.getDouble("cred");
            }
            Rs2.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," getMovimiento_Contable(String PCuenta, String Tiene_centro_Costo) "+e.getMessage());  
        } 
    }
    
    public void getCuentas_Financieras(){
        try {
             String Str_Sql = "Select CTAFIN from SELINLIB.JDISTRINIF WHERE CTAFIN <> '' AND CTACON <> 51 AND CTACON <> 6105";
             ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             jCCuentas.addItem("");
             jComboBox4.addItem("");
             
             while(Rs.next()){      
                 jCCuentas.addItem(Rs.getString("CTAFIN"));
                 jComboBox4.addItem(Rs.getString("CTAFIN"));
             }
             Rs.close();
             Str_Sql= "Select CUENFIN from SELINLIB.JDISAGRNIF WHERE MARCA='A' OR MARCA ='B'"  ;
             ResultSet Rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

             while(Rs2.next()){      
               jCCuentas.addItem(Rs2.getString("CUENFIN"));
             }
             Rs2.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getCuentas() "+e.getMessage());  
        }
    }  
    
    public void getComboPeriodo(){
        try {
             String Str_Sql ="select periodo from selinlib.JFDISTRPER order by periodo desc";
             ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             jCPeriodo = new javax.swing.JComboBox();
             jCDT_Periodo.addItem("");

             while(Rs.next()){
                 jCPeriodo.addItem(Rs.getString("periodo"));
             }
             Rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getComboPeriodo() "+e.getMessage());  
        }
    }    
  
    public void getCombo_CDTs(){
        try {
             String Str_Sql ="select periodo from selinlib.JFDISTRPER order by periodo desc";
             ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
             jCDT_Periodo.addItem("-");
           
             while(Rs.next()){
                 jCDT_Periodo.addItem(Rs.getString("periodo"));
             }
             Rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getComboPeriodo() "+e.getMessage());  
        }
    }    
  
    public void getCombo_Fondos_CDTs(){
        try {
            String Str_Sql ="select distinct cuenfin as cuenta from selinlib.JDISAGRNIF order by cuenfin asc";
            ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

            while(Rs.next()){
                 jCDT_Cuenta.addItem(Rs.getString("cuenta"));
            }
            Rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getComboPeriodo() "+e.getMessage());  
        }
    }    
  
    public void setCabeceraBanco(){
        this.CabBanco.clear();
        this.CabBanco.add("Cuenta"); 
        this.CabBanco.add("Fecha"); 
        this.CabBanco.add("Numero Documento"); 
        this.CabBanco.add("Credito"); 
        this.CabBanco.add("Debito"); 
        this.CabBanco.add("Periodo"); 
    }  
    
    public void getInformacionBanco(String FIncial , String FFinal, String Cta){
      try {
          String Str_Sql ="select * from CONDATNIC.movimi where fecmov>="+FIncial+" and fecmov<="+FFinal+" and"
                  + "  ctamov ='"+Cta.trim()+"'";
          ResultSet Rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
          this.FilaBanco.clear();
          
          while(Rs.next()){
            this.getComboPeriodo();
            this.DetBanco = new Vector();
            this.DetBanco.add(Rs.getString("ctamov"));
            this.DetBanco.add(Rs.getString("fecmov"));
            this.DetBanco.add(Rs.getString("NUMMOV"));
            this.DetBanco.add(this.JFormato.format(Rs.getDouble("cremov")));
            this.DetBanco.add(this.JFormato.format(Rs.getDouble("debmov")));
            this.DetBanco.add("");
            this.FilaBanco.add(this.DetBanco);
          } 
          Rs.close();
          this.jTable2.setModel(new javax.swing.table.DefaultTableModel(this.FilaBanco, this.CabBanco));
          this.jTable2.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(jCPeriodo));
          jTable2.setAutoCreateRowSorter(true);
      } catch (Exception e) {
          JOptionPane.showMessageDialog(this,"getInformacionBanco() "+e.getMessage());  
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
            java.util.logging.Logger.getLogger(JDistribucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDistribucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDistribucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDistribucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JDistribucion(null,null,null,null).setVisible(true);
            }
        });
    }
    
    private javax.swing.JComboBox jCPeriodo;
    private javax.swing.JComboBox jCheck;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jCCuentas;
    private javax.swing.JComboBox jCDT_Cuenta;
    private javax.swing.JComboBox jCDT_Estado;
    private javax.swing.JTextField jCDT_Fec_Aper;
    private javax.swing.JTextField jCDT_Fec_Cierre;
    private javax.swing.JTextField jCDT_Nro;
    private javax.swing.JComboBox jCDT_Periodo;
    private javax.swing.JTextField jCDT_Valor;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable21;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
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
    private org.jdesktop.swingx.JXDatePicker jXDatePicker4;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker5;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker6;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker7;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker8;
    // End of variables declaration//GEN-END:variables
}
