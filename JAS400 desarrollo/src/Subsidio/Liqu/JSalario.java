/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Subsidio.Liqu;

import BD_As400.JConection;
import Subsidio.JJasper_Sub;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author GACA1186
 */
public class JSalario extends javax.swing.JFrame {

    /**
     * Creates new form JSalario
     */
    private Vector Cabecera;
    private Vector Detalle;
    private Vector Fila;
    
    private Vector Cabecera2;
    private Vector Detalle2;
    private Vector Fila2;
    
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    
    private JConection JBase_Datos;
    private Connection Cn;
    
    public JSalario(JConection JBase_Datos3, Connection Cn2) {
        
        JFormato= new DecimalFormat(NumeroFormato);
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.Cabecera = new Vector();
        this.Cabecera.add("Documento");
        this.Cabecera.add("Beneficiario");
        this.Cabecera.add("Valor");
        this.Cabecera.add("Salario Liquidacion");
        this.Cabecera.add("Fecha de Liquidacion");
        this.Cabecera.add("Ibcd");
        this.Cabecera.add("Salpla");
        this.Cabecera.add("Fecha de Planilla");
        this.Cabecera.add("Salario trab.");
        this.Cabecera.add("Doc. Conyugue ");
        this.Cabecera.add("Salario");
        this.Cabecera.add("Ibcd.");
        this.Cabecera.add("Salpla Con.");
        this.Cabecera.add("Fecha planilla Con.");
        this.Cabecera.add("Periodo");
        this.Cabecera.add("Salario");
        Fila = new Vector();
        
        this.Cabecera2 = new Vector();
        this.Cabecera2.add("Documento trabajador");
        this.Cabecera2.add("Salario Planilla");
        this.Cabecera2.add("Documento Beneficiario");
        this.Cabecera2.add("Salpla");
        this.Cabecera2.add("Ibcd");
        this.Cabecera2.add("Periodo");
        this.Cabecera2.add("Salario");
        
        Fila2 = new Vector();
        initComponents();
    }
    
    public double getSalarioMinimo(String Ano){
        double Vlr = 0;
        try {
            Ano = Ano+"0101";
            String Str_sql =" select parvsm from subsilib.mparam where parcod ="+Ano;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_sql);
            
            if(rs.next()){
                Vlr = rs.getDouble("parvsm");
            }
            rs.close();

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "getSalarioMinimo() "+e.getMessage());
        }
        return Vlr;
    }
    
    public void getValidacion_4_Salario(double SLMV){
        try {
            String Str_sql="select salpla , PSALIQ, ibcd ,salpla/"+SLMV+" as Salario,t1.*                      \n" +
                           "      from subsilib.liquipag as t1 , subsilib.cerpla as t4 ,   \n" +
                           " subsilib.cerplad as t5                                        \n" +
                           "where pfeliq >="+this.jFechaInicial.getText().trim()+" and pfeliq <= "+this.jFechaFinal.getText().trim()+" \n" +
                           "and not exists ( select * from subsilib.mconyug as t2          \n" +
                           "where t1.pctliq = t2.tradoc)                                   \n" +
                           "and NROPLA = PLANIL and PERDET = PERAPO                        \n" +
                           "and cedpla = pctliq and perapo = ppeliq                        \n" +
                           "and (salpla/"+SLMV+")>4                                          ";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_sql);
            while(rs.next()){
                this.Detalle2 = new Vector();
                this.Detalle2.add(JFormato.format(rs.getDouble("pctliq")));
                this.Detalle2.add(JFormato.format(rs.getDouble("PSALIQ")));
                this.Detalle2.add(JFormato.format(rs.getDouble("pdbliq")));
                this.Detalle2.add(JFormato.format(rs.getDouble("salpla")));
                this.Detalle2.add(JFormato.format(rs.getDouble("ibcd")));
                this.Detalle2.add(rs.getString("ppeliq"));
                this.Detalle2.add(rs.getString("Salario"));
                this.Fila2.add(Detalle2);
            }
            rs.close();
            jTable2.setModel(new javax.swing.table.DefaultTableModel(Fila2, Cabecera2));
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "getSalarioMinimo() "+e.getMessage());
        }
    }
    public void Valida_Salario(){
        try {
            String Pano = this.jFechaFinal.getText().trim().substring(0, 4);
            double SLMV = this.getSalarioMinimo(Pano);
            
            String Str_sql= " SELECT TX1.TRADOC , TX1.BENE , TX1.PVALIQ , TX1.PFELIQ ,IBCD , SALPLA ,   \n" +
                            "        TX1.FECCER   ,TX1.TRAVSB , TX1.CONDOC , TX2.TRAVSB , TX1.IBC_C ,   \n" +
                            "        TX1.SALPLA_C  , FECCER_C ,  TX1.PSALIQ ,                   \n" +
                            "        (TX1.SALPLA + TX1.SALPLA_C)/"+SLMV+" AS SALARIO , TX1.PPELIQ     \n" +
                            "        FROM                                                              \n" +
                            "         (  SELECT T1.TRADOC ,T2.PDBLIQ AS BENE , T2.PVALIQ , T2.PFELIQ , T2.PSALIQ    \n" +
                            "            ,T1.TRAVSB , T3.CONDOC , T2.PDBLIQ , T4.FECCER           \n" +
                            "            , T5.IBCD , T5.SALPLA  , T7.IBCD AS IBC_C , T7.SALPLA AS  \n" +
                            "            SALPLA_C , T6.FECCER AS FECCER_C, T2.PPELIQ             \n" +
                            "            FROM SUBSILIB.MTRABAJ AS T1 , SUBSILIB.LIQUIPAG AS T2     \n" +
                            "            , SUBSILIB.MCONYUG AS T3 , SUBSILIB.LIQUIPAG AS T3A ,\n"+
                            "           SUBSILIB.CERPLA AS T4   , SUBSILIB.CERPLAD AS T5          \n" +
                            "            , SUBSILIB.CERPLA AS T6  , SUBSILIB.CERPLAD AS T7         \n" +
                            "            WHERE T1.TRADOC = T2.PCTLIQ AND T1.TRADOC = T3.TRADOC   \n" +
                            "            AND T2.PFELIQ >= "+jFechaInicial.getText().trim()+" AND T2.PFELIQ <= "+jFechaFinal.getText().trim()+"\n"
                            + "          AND T3.CONDOC = T3A.PCTLIQ  AND T3A.PPELIQ = T2.PPELIQ \n"
                            + "          AND T2.PDBLIQ = T3A.PDBLIQ \n"+
                            "            AND T3.CONDOC <> 0  AND T4.NROPLA = T5.PLANIL           \n" +
                            "            AND T5.PERDET = T4.PERAPO   AND T6.NROPLA = T7.PLANIL   \n" +
                            "            AND T7.PERDET = T6.PERAPO                               \n" +
                            "            AND T5.CEDPLA = T1.TRADOC AND T2.PPELIQ = T4.PERAPO     \n" +
                            "            AND T7.CEDPLA = T3.CONDOC AND T2.PPELIQ = T6.PERAPO     \n"+
                            "         ) AS TX1 , SUBSILIB.MTRABAJ AS TX2                     \n" +
                            " WHERE TX1.CONDOC = TX2.TRADOC  AND BENE <> 0                   \n" +
                            "       AND NOT EXISTS ( SELECT * FROM SUBSILIB.MPADRES AS Y1    \n" +
                            "       WHERE TX1.TRADOC = Y1.TRADOC AND TX1.BENE = Y1.PADDOC)   \n" +
                            "       ORDER BY SALARIO DESC                                ";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_sql);
            while(rs.next()){
                this.Detalle = new Vector();
                this.Detalle.add(JFormato.format(rs.getDouble("TRADOC")));
                this.Detalle.add(JFormato.format(rs.getDouble("BENE")));
                this.Detalle.add(JFormato.format(rs.getDouble("PVALIQ")));
                //double T = rs.getDouble("PSALIQ");
                this.Detalle.add(JFormato.format(rs.getDouble("PSALIQ")));
                this.Detalle.add(rs.getString("PFELIQ"));
                this.Detalle.add(JFormato.format(rs.getDouble("IBCD")));
                this.Detalle.add(JFormato.format(rs.getDouble("SALPLA")));
                this.Detalle.add(rs.getString("FECCER"));
                this.Detalle.add(rs.getString("TRAVSB"));
                this.Detalle.add(JFormato.format(rs.getDouble("CONDOC")));
                this.Detalle.add(JFormato.format(rs.getDouble("TRAVSB")));
                this.Detalle.add(JFormato.format(rs.getDouble("IBC_C")));
                this.Detalle.add(JFormato.format(rs.getDouble("SALPLA_C")));
                this.Detalle.add(rs.getString("FECCER_C"));
                this.Detalle.add(rs.getString("PPELIQ"));
                this.Detalle.add(JFormato.format(rs.getDouble("SALARIO")));
                this.Fila.add(Detalle);
            }
            rs.close();
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Fila, Cabecera));
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            this.getValidacion_4_Salario(SLMV);    
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Valida_Salario() "+e.getMessage());
        }
        //prueba de programacion,
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
        jFechaInicial = new javax.swing.JTextField();
        jFechaFinal = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jButton14 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Validacion de Salarios Liquidacion de Subsidio Familiar");

        jButton1.setText("Validar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jFechaInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFechaInicialActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Documento", "Beneficiario", "Valor", "Salario Liquidacion", "Fecha Liquidacion", "Ibcd", "SalPla", "Fecha Planilla", "Salario Trab.", "Doc. Conyugue", "Salario Con.", "Ibcd Con.", "SalPla Cony.", "Fecha Planilla Con.", "Periodo", "Salario"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Documento Trabajador", "Salario Planilla", "Documento Beneficiario", "Salpla", "Ibcd", "Periodo", "Salario"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton8.setText("Imprimir");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1391, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8)
                            .addComponent(jButton1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        jTabbedPane1.addTab("Salario", jPanel1);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Periodo", "Fecha de Nacimiento", "Fecha Liq", "Doc. Trabajador", "Doc. Conyuge", "Doc. Benef.", "Nombre Beneficiario", "Tipo Liquidacion", "Valor"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jButton2.setText("Buscar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha Inicial");

        jLabel2.setText("Fecha Final");

        jButton9.setText("Imprimir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1381, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton9)
                                    .addComponent(jButton2)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel1)
                        .addGap(96, 96, 96)
                        .addComponent(jLabel2)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(512, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Fecha Nacimiento", jPanel2);

        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Doc hijo", "Nombre", "Fec. Nacimiento", "Recibe Subsidio", "Doc. Trabajador", "Nombre", "Fec. Nacimiento"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        jButton10.setText("Imprimir");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1354, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton10))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(437, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hijo Trabaj. Afiliado", jPanel3);

        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Doc Padre", "Nombre", "Fec. Nacimiento", "Recibe Subsidio", "Doc. Trabajador", "Nombre", "Fec. Nacimiento"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        jButton11.setText("Imprimir");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1354, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton11))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(446, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Padre Trabaj. Afiliado", jPanel4);

        jButton5.setText("Buscar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Edad", "Periodo", "Fecha Nacimiento", "Fecha Liquidacion", "Doc. Trabajador", "Doc. Padre", "Nombre", "Discapacitado", "Tipo Liq.", "Valor"
            }
        ));
        jScrollPane6.setViewportView(jTable6);

        jButton12.setText("Imprimir");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(322, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Padres Menor de 60", jPanel5);

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Doc. Hijo", "Doc. Trabajador", "Sexo", "Cantidad", "Recibe Subsidio"
            }
        ));
        jScrollPane7.setViewportView(jTable7);

        jButton6.setText("Buscar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton13.setText("Imprimir");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton13))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(315, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hijos Afiliado Pad mismo Sexo", jPanel6);

        jButton7.setText("Buscar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Edad", "Periodo", "Fecha Nacimiento", "Doc. Hijo", "Doc. Trabajador", "Tipo ", "Valor"
            }
        ));
        jScrollPane8.setViewportView(jTable8);

        jButton14.setText("Imprimir");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1347, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton7)
                        .addGap(18, 18, 18)
                        .addComponent(jButton14)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7)
                    .addComponent(jButton14))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(310, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mas 18 Recibe Cuota", jPanel7);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFechaInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFechaInicialActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jFechaInicialActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.Valida_Salario();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(!jTextField1.getText().trim().equals("") && !jTextField2.getText().trim().equals("")){
            getLiquidacionAntes_Nacer(jTextField1.getText().trim() , jTextField2.getText().trim());
        }else{
            javax.swing.JOptionPane.showMessageDialog(this, "Debe diligenciar los campos de fechas");
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Hijo_Trabajador_Afiliado();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Hijo_Padre_Trabajador_Afiliado();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(!jTextField3.getText().trim().equals("") && !jTextField4.getText().trim().equals("")){
            get_Padres_Menor_60_Recibiendo_Cuota(jTextField3.getText().trim() , jTextField4.getText().trim());
        }else{
            javax.swing.JOptionPane.showMessageDialog(this, "Debe diligenciar los campos de fechas");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        getMismoSexoTrabajador_hijos();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
         if(!jTextField5.getText().trim().equals("") && !jTextField6.getText().trim().equals("")){
            getMayores_RecibeCuota(jTextField5.getText().trim() , jTextField6.getText().trim());
        }else{
            javax.swing.JOptionPane.showMessageDialog(this, "Debe diligenciar los campos de fechas");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
            String Pano = this.jFechaFinal.getText().trim().substring(0, 4);
            int SLM = (int) this.getSalarioMinimo(Pano);
            
            
            JJasper_Sub  GenPdf2 = new JJasper_Sub();
            GenPdf2.setTipo(1);
            GenPdf2.setFechaInicial(jFechaInicial.getText().trim());
            GenPdf2.setFechaFinal(jFechaFinal.getText().trim());
            GenPdf2.setSLMV(SLM);
            GenPdf2.setConeccion(Cn);
            GenPdf2.start();
            
            JJasper_Sub GenPdf = new JJasper_Sub();
            GenPdf.setTipo(2);
            GenPdf.setFechaInicial(jFechaInicial.getText().trim());
            GenPdf.setFechaFinal(jFechaFinal.getText().trim());
            GenPdf.setSLMV(SLM);
            GenPdf.setConeccion(Cn);
            GenPdf.start();
            
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
            JJasper_Sub GenPdf = new JJasper_Sub();
            GenPdf.setTipo(3);
            GenPdf.setFechaInicial(jTextField1.getText().trim());
            GenPdf.setFechaFinal(jTextField2.getText().trim());
            GenPdf.setConeccion(Cn);
            GenPdf.start();
        
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
            JJasper_Sub GenPdf = new JJasper_Sub();
            GenPdf.setTipo(4);
            GenPdf.setConeccion(Cn);
            GenPdf.start();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        JJasper_Sub GenPdf = new JJasper_Sub();
        GenPdf.setTipo(5);
        GenPdf.setConeccion(Cn);
        GenPdf.start();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
           JJasper_Sub GenPdf = new JJasper_Sub();
            GenPdf.setTipo(6);
            GenPdf.setFechaInicial(jTextField3.getText().trim());
            GenPdf.setFechaFinal(jTextField4.getText().trim());
            GenPdf.setConeccion(Cn);
            GenPdf.start();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
            JJasper_Sub GenPdf = new JJasper_Sub();
            GenPdf.setTipo(7);
            GenPdf.setConeccion(Cn);
            GenPdf.start();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        JJasper_Sub GenPdf = new JJasper_Sub();
        GenPdf.setTipo(8);
        GenPdf.setFechaInicial(jTextField5.getText().trim());
        GenPdf.setFechaFinal(jTextField6.getText().trim());
        GenPdf.setConeccion(Cn);
        GenPdf.start();
    }//GEN-LAST:event_jButton14ActionPerformed
    public void getMayores_RecibeCuota(String FechaIni , String FechaFin){
        try {
            String SQL = "select  ( ppeliq - int (substr( hijfnc , 1 , 6) ) ) *1000  Edad   \n" +
                        "       , ppeliq                                                   \n" +
                        "       , hijfnc  , tradoc , hijdoc , pcoliq , pvaliq              \n" +
                        "        from subsilib.liquipag as t1 , subsilib.mhijos as t2      \n" +
                        "where t1.pdbliq = hijdoc and pctliq = tradoc                      \n" +
                        "and pfeliq >= 20170101 and pfeliq <= 20170131                     \n" +
                        "order by 1 desc                                                   \n";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,SQL);
            
            Vector ZCab = new Vector();
            Vector ZFila = new Vector();
            Vector ZDet;
            
            ZCab.add("Edad");
            ZCab.add("Periodo");
            ZCab.add("Fecha Nacimiento");
            ZCab.add("Doc. Hijo");
            ZCab.add("Doc. Trabajador");
            ZCab.add("Tipo Liq.");
            ZCab.add("Valor");
            while(rs.next()){
                ZDet = new Vector();
                ZDet.add(rs.getDouble("edad")/100000);
                ZDet.add(rs.getString("ppeliq"));
                ZDet.add(rs.getString("hijfnc"));
                ZDet.add(rs.getString("hijdoc"));
                ZDet.add(rs.getString("tradoc"));
                ZDet.add(rs.getString("pcoliq"));
                ZDet.add(rs.getString("pvaliq"));
                ZFila.add(ZDet);
            }
            jTable8.setModel(new javax.swing.table.DefaultTableModel(ZFila, ZCab));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " getMayores_RecibeCuota() "+e.getMessage());
        }
    }
    
    
    public void getMismoSexoTrabajador_hijos(){
        try {
            String SQL= "select  t1.* , t2.tradoc , trasrs                             \n" +
                        "       from                                                   \n" +
                        " ( select  hijdoc , trasex , count(*) as Cuenta                        \n" +
                        "   from subsilib.mhijos as t1 , subsilib.mtrabaj as t2        \n" +
                        "   where t1.tradoc  = t2.tradoc                               \n" +
                        "   group by hijdoc , trasex                                   \n" +
                        "   having count(*)>1                                          \n" +
                        " ) as t1 , subsilib.mhijos  as t2  , subsilib.mtrabaj as t3   \n" +
                        "where t1.hijdoc = t2.hijdoc and t3.tradoc = t2.tradoc         \n" +
                        "order by hijdoc                                               ";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,SQL);
            
            Vector ZCab = new Vector();
            Vector ZFila = new Vector();
            Vector ZDet;
            
            ZCab.add("Doc. Hijo");
            ZCab.add("Doc. Trabajador");
            ZCab.add("Sexo");
            ZCab.add("Cantidad");
            ZCab.add("Recibe Subsidio");
            while(rs.next()){
                ZDet = new Vector();
                ZDet.add(rs.getString("Hijdoc"));
                ZDet.add(rs.getString("Tradoc"));
                ZDet.add(rs.getString("Trasex"));
                ZDet.add(rs.getString("Cuenta"));
                ZDet.add(rs.getString("trasrs"));
                ZFila.add(ZDet);
            }
            jTable7.setModel(new javax.swing.table.DefaultTableModel(ZFila, ZCab));
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " getMismoSexoTrabajador_hijos() "+e.getMessage());
        }
    }
            
    
    
    public void get_Padres_Menor_60_Recibiendo_Cuota(String FechaIni, String FechaFinal){
        try {
            String SQL = "  select                                                            \n" +
                         " ( ppeliq  - int(substring(padfnc, 1 , 6)) )* 10   as edad        \n" +
                         ",  substring(padfnc, 1 , 6) ,  ppeliq  , pctliq , pdbliq, padnom  \n" +
                         ", pcoliq , pvaliq,padsdc, padfnc,pfeliq,paddoc               \n" +
                         "from subsilib.liquipag as t1 , subsilib.mpadres as t2             \n" +
                         "where pfeliq >= "+FechaIni+"  and pfeliq <= " +FechaFinal+
                         " and pdbliq = paddoc  and padsdc = 0                               \n" +
                         "order by 1 asc                                                     ";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,SQL);
            
            Vector ZCab = new Vector();
            Vector ZFila = new Vector();
            Vector ZDet;
            
            ZCab.add("Edad");
            ZCab.add("Periodo");
            ZCab.add("Fecha Nacimiento");
            ZCab.add("Fecha Liquidacion");
            ZCab.add("Doc. Trabajador");
            ZCab.add("Doc. Padre");
            ZCab.add("Nombre");
            ZCab.add("Discapacitado");
            ZCab.add("Tipo Liq.");
            ZCab.add("Valor");
            while(rs.next()){
                ZDet = new Vector();
                ZDet.add(rs.getString("edad"));
                ZDet.add(rs.getString("ppeliq"));
                ZDet.add(rs.getString("padfnc"));
                ZDet.add(rs.getString("pfeliq"));
                ZDet.add(rs.getString("pctliq"));
                ZDet.add(rs.getString("paddoc"));
                ZDet.add(rs.getString("padnom"));
                ZDet.add(rs.getString("padsdc"));
                ZDet.add(rs.getString("pcoliq"));
                ZDet.add(rs.getString("pvaliq"));
                ZFila.add(ZDet);
            }
            jTable6.setModel(new javax.swing.table.DefaultTableModel(ZFila, ZCab));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " get_Padres_Menor_60_Recibiendo_Cuota() "+e.getMessage());
        }
    }
            
    public void Hijo_Padre_Trabajador_Afiliado(){
        try {
            String SQL ="select * from subsilib.mtrabaj as t1 , subsilib.mpadres as t2 \n" +
                        " where t1.tradoc =  t2.paddoc                                    ";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,SQL);
            
            Vector ZCab = new Vector();
            Vector ZFila = new Vector();
            Vector ZDet;
            
            ZCab.add("Doc. Padre");
            ZCab.add("Nombre");
            ZCab.add("Fec. Nacimiento");
            ZCab.add("Recibe Subsidio");
            ZCab.add("Doc. Trabajador");
            ZCab.add("Nombre");
            ZCab.add("Fec. Nacimiento");
            
            while(rs.next()){
                ZDet = new Vector();
                ZDet.add(rs.getString("paddoc"));
                ZDet.add(rs.getString("padnom"));
                ZDet.add(rs.getString("padfnc"));
                ZDet.add(rs.getString("padsrs"));
                ZDet.add(rs.getString("tradoc"));
                ZDet.add(rs.getString("tranom"));
                ZDet.add(rs.getString("trafnc"));
                ZFila.add(ZDet);
            }
            jTable5.setModel(new javax.swing.table.DefaultTableModel(ZFila, ZCab));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " Hijo_Trabajador_Afiliado() "+e.getMessage());
        }
    }
    
    
    
    public void Hijo_Trabajador_Afiliado(){
        try {
            String SQL =" select * from subsilib.mtrabaj as t1 , subsilib.mhijos as t2  \n" +
                        " where t1.tradoc =  t2.hijdoc  ";
          
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,SQL);
            
            Vector ZCab = new Vector();
            Vector ZFila = new Vector();
            Vector ZDet;
            
            ZCab.add("Doc. hijo");
            ZCab.add("Nombre");
            ZCab.add("Fec. Nacimiento");
            ZCab.add("Recibe Subsidio");
            ZCab.add("Doc. Trabajador");
            ZCab.add("Nombre");
            ZCab.add("Fec. Nacimiento");
            
            while(rs.next()){
                ZDet = new Vector();
                ZDet.add(rs.getString("hijdoc"));
                ZDet.add(rs.getString("hijnom"));
                ZDet.add(rs.getString("hijfnc"));
                ZDet.add(rs.getString("hijsrs"));
                ZDet.add(rs.getString("tradoc"));
                ZDet.add(rs.getString("tranom"));
                ZDet.add(rs.getString("trafnc"));
                ZFila.add(ZDet);
            }
            jTable4.setModel(new javax.swing.table.DefaultTableModel(ZFila, ZCab));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, " Hijo_Trabajador_Afiliado() "+e.getMessage());
        }
    }
            
        
    public void getLiquidacionAntes_Nacer(String FechaIni , String FechaFin){
        try {
            String SQL = "select ppeliq , hijfnc, t1.*  \n" +
                         "from subsilib.liquipag as t1 , subsilib.mhijos  as t2 \n" +
                         "where ppeliq < substr(hijfnc , 1 ,6)                  \n" +
                        "and pfeliq >= "+FechaIni+" and pfeliq <="+FechaFin+" "+
                        "and pdbliq = hijdoc  and pctliq = tradoc ";

            Vector PCab = new Vector();
            Vector PFila = new Vector();
            Vector PDet;
            
            PCab.add("Periodo");
            PCab.add("Fecha Nacimiento");
            PCab.add("Fecha Liq");
            PCab.add("Doc. Trabajador");
            PCab.add("Doc. Conyugue");
            PCab.add("Doc. Benef.");
            PCab.add("Nombre  Beneficiario");
            PCab.add("Tipo Liquidacio");
            PCab.add("Valor");
            
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,SQL);
            while(rs.next()){
                PDet = new Vector();
                PDet.add(rs.getString("ppeliq"));
                PDet.add(rs.getString("hijfnc"));
                PDet.add(rs.getString("pfeliq"));
                PDet.add(rs.getString("pctliq"));
                PDet.add(rs.getString("pccliq"));
                PDet.add(rs.getString("pdbliq"));
                PDet.add(rs.getString("pnbliq"));
                PDet.add(rs.getString("pcoliq"));
                PDet.add(rs.getString("pvaliq"));
                PFila.add(PDet);
            }
            jTable3.setModel(new javax.swing.table.DefaultTableModel(PFila, PCab));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "getLiquidacionAntes_Nacer(String FechaIni , String FechaFin)  "+e.getMessage());
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
            java.util.logging.Logger.getLogger(JSalario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JSalario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JSalario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JSalario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JSalario(null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JTextField jFechaFinal;
    private javax.swing.JTextField jFechaInicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
