package Ingresos;


import ReporteIngresos.JReportIngresoConsolidado;
import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class JIngresosXPeriodos extends javax.swing.JFrame {

    /**
     * Creates new form JIngresosXPeriodos
     */
    
    private JConection JBase_Datos;
    private Connection Cn;
    private Object [] Cabecera ;
    private Object [] [] Detalle  ;
    private String NumeroFormato = "###,###,###,###.##";
    private final DecimalFormat JFormato;
    private int CantidadFila = 0;
    private double SumCCMBCHEQ = 0, SumEFTCONS=0,SumVTAGNB= 0, SumABNRET = 0, SumVTARET = 0, SumCNJESPER = 0, SumCNJCONSI =0;
    private  double SumVVLRRETENI = 0, SumVVLRABONAD = 0, SumCVLRRETENI=0, SumCVLRABONAD = 0, SumCHQPCONS = 0;
    private int FechaInicial, FechaFinal;
    private String jTextField1 , jTextField2;
    private Vector Cab;
    private Vector Det;
    private Vector DetalleResumen;
    private Vector DetalleTotal;
    private SimpleDateFormat d;
    
    public JIngresosXPeriodos(JConection JBase_Datos3, Connection Cn2) {
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        Cabecera =  new Object [13];
        Detalle  =  new Object [400][13];
        JFormato= new DecimalFormat(NumeroFormato);
        CantidadFila = 0;
        initComponents();
       jXDatePicker1.setFormats("yyyyMMdd"); 
       jXDatePicker2.setFormats("yyyyMMdd");
       d = new SimpleDateFormat("yyyyMMdd");
    }
    public double getCambioChequeEfectivo(){
        return this.SumCCMBCHEQ;
    }
    public double getEfectivoConsignar(){
        return this.SumEFTCONS;
    }
    public double getVentasGnb(){
        return this.SumVTAGNB;
    }
    public double getVentasRetenidas(){
        return this.SumVTARET;
    }
    public double getAbonoRetenido(){
        return this.SumABNRET;
    }
    public int getFechaInicial(){
        return this.FechaInicial;
    }
    public double getCangeEsperado(){
        return this.SumCNJESPER;
    }
    public double getCangeConsignado(){
        return this.SumCNJCONSI;
    }
    public int getFechaFinal(){
        return this.FechaFinal;
    }
    public double getValorVentaRetenida(){
        return this.SumVVLRRETENI;
    }
    public double getValorVentaAbonada(){
        return SumVVLRABONAD;
    }
    public double getCajeVentaRetenida(){
        return SumCVLRRETENI;
    }
    public double getVentaAbonada(){
        return SumCVLRABONAD;
    }
    public double getChequePendienteConsignar(){
        return SumCHQPCONS;
    }
    public void getMovimientoResumen(){
        try {
            this.Cab = new Vector();
            Cab.add("Codigo");
            Cab.add("Centro Costo");
            Cab.add("Valor");
            String Str_Sql =  " select  t3.CENCOD , t4.CENNOM  , sum(t1.COMVLR) as Vlr  " +
                              " from recaudos.bascom as t1 , recaudos.CAJERA as t2 "+
                              "  , recaudos.CBACOM as t3 , recaudos.CENTRO as t4  "+  
                              "  where t1.comfec >= 20140201 "+                       
                              "  and t1.comfec <= 20140231 "+                         
                              "  and T1.CAJCOD = T2.CAJCOD "+                         
                              "  and T1.USUCOD = T3.USUCOD "+                         
                              "  and T1.COMFEC = T3.COMFEC "+                         
                              "  and T1.CAJCOD = T3.CAJCOD "+                         
                              "  and T3.CENCOD = T4.CENCOD "+                         
                              "  group by t3.cencod , t4.cennom "
                              + " order by 1";                    
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.Det = new Vector();
            Det.clear();
            while (rs.next()) {  
              this.DetalleResumen = new Vector();
              this.DetalleResumen.add(rs.getString("CENCOD"));
              this.DetalleResumen.add(rs.getString("CENNOM"));
              this.DetalleResumen.add(this.JFormato.format( rs.getDouble("Vlr") ));
              this.Det.add(this.DetalleResumen);               
            }
            jTResumen.setModel(new javax.swing.table.DefaultTableModel(Det, Cab));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"getMovimiento() "+e.getMessage());
        }
    }
    
    public void getMovimiento(){
        try {
            this.Cab = new Vector();
            Cab.add("Codigo");
            Cab.add("Centro Costo");
            Cab.add("Valor");
            String Str_Sql =  " select  t3.CENCOD , t4.CENNOM  , sum(t1.COMVLR) as Vlr  " +
                              " from recaudos.bascom as t1 , recaudos.CAJERA as t2 "+
                              "  , recaudos.CBACOM as t3 , recaudos.CENTRO as t4  "+  
                              "  where t1.comfec >= 20140201 "+                       
                              "  and t1.comfec <= 20140231 "+                         
                              "  and T1.CAJCOD = T2.CAJCOD "+                         
                              "  and T1.USUCOD = T3.USUCOD "+                         
                              "  and T1.COMFEC = T3.COMFEC "+                         
                              "  and T1.CAJCOD = T3.CAJCOD "+                         
                              "  and T3.CENCOD = T4.CENCOD "+                         
                              "  group by t3.cencod , t4.cennom "
                              + " order by 1";                    
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.Det = new Vector();
            Det.clear();
            while (rs.next()) {  
              this.DetalleResumen = new Vector();
              this.DetalleResumen.add(rs.getString("CENCOD"));
              this.DetalleResumen.add(rs.getString("CENNOM"));
              this.DetalleResumen.add(this.JFormato.format( rs.getDouble("Vlr") ));
              this.Det.add(this.DetalleResumen);               
            }
            jTResumen.setModel(new javax.swing.table.DefaultTableModel(Det, Cab));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"getMovimiento() "+e.getMessage());
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTResumen = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(150, 85, 0, 0));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Total", "Cambio de Cheques", "Efectivos por Consignar", "Ventas GNB", "Abono Retenido", "Venta Retenida", "Canje Esperado", "Canje por Consignar", "Cheque pendiente por Consignar", "Valor Retenido", "Valor Abonado de Ventas", "Valor Retenido de Canje", "Valor Abonado de Canje"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Fecha Inicio");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Fecha Fin");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Imprimir");
        jButton2.setEnabled(false);
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel1)
                                .addGap(103, 103, 103)
                                .addComponent(jLabel2)))
                        .addGap(0, 310, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(261, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Periodos", jPanel1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre del centro", "Codigo cajera", "Nombre cajera", "Fecha", "Valor"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jTResumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Centro de Costo", "Valor"
            }
        ));
        jScrollPane3.setViewportView(jTResumen);

        jButton3.setText("Buscar..");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(357, 357, 357))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton3)
                .addGap(78, 78, 78)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(225, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ventas", jPanel2);

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
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            jTextField1 = d.format(jXDatePicker1.getDate()).toString();
            jTextField2 = d.format(jXDatePicker2.getDate()).toString();
            this.getInformacion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la fecha "+e.getMessage());
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JReportIngresoConsolidado rp = new JReportIngresoConsolidado(this.JBase_Datos, this.Cn,this);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:thi
        this.getMovimientoResumen();
    }//GEN-LAST:event_jButton3ActionPerformed
    public void getInformacion(){
        try {
            
            
            
            String StrSql = "SELECT  CCMBCHEQ, EFTCONS, EFTCONS,VTAGNB ,ABNRET, VTARET, CNJESPER, CNJCONSI, "+
                            " CNJESPER, CHQPCONS, VVLRRETENI, VVLRABONAD, CVLRRETENI, "+
                            " CVLRABONAD, CALACTUAL "+
                            " FROM SELINLIB.JINGRESO WHERE "+
                            " FECHA >= "+jTextField1.trim()+" AND FECHA <="+jTextField2.trim();
            
            this.FechaInicial = Integer.parseInt(jTextField1.trim());
            this.FechaFinal =  Integer.parseInt(jTextField2.trim());
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            Cabecera [0]  = "Total";
            Cabecera [1]  = "Cambio de Cheque";
            Cabecera [2]  = "Efectivo por Consignar";
            Cabecera [3]  = "Venta GNB";
            Cabecera [4]  = "Abono Retenido";
            Cabecera [5]  = "Venta Retenida";
            Cabecera [6]  = "Canje Esperado";
            Cabecera [7]  = "Canje por Consignar";
            Cabecera [8]  = "Cheque Pendiente por Consignar";
            Cabecera [9]  = "Valor Retenido";
            Cabecera [10]  = "Valor Abono";
            Cabecera [11] = "Valor Retenido";
            Cabecera [12] = "Valor Abonado";
            int fila = -1;
            this.Cleartable();
   
            
           SumCCMBCHEQ=0;
           SumEFTCONS =0;
           SumVTAGNB = 0;
           SumABNRET=0;
           SumVTARET =0;
           SumCNJESPER=0;
           SumCNJCONSI=0;
           SumCHQPCONS=0;
           SumVVLRRETENI=0;
           SumVVLRABONAD=0;
           SumCVLRRETENI=0;
           SumCVLRABONAD=0;
            
             
            while (rs.next()) {    
                fila ++;
                
                double zCCMBCHEQ = rs.getDouble("CCMBCHEQ");
                Detalle[fila][1] = JFormato.format(zCCMBCHEQ);
                SumCCMBCHEQ = SumCCMBCHEQ+zCCMBCHEQ;
                
                double zEFTCONS = rs.getDouble("EFTCONS");
                Detalle[fila][2] = JFormato.format(zEFTCONS);
                SumEFTCONS = SumEFTCONS+zEFTCONS;
               
                double zVTAGNBS = rs.getDouble("VTAGNB");
                Detalle[fila][3] = JFormato.format(zVTAGNBS);
                SumVTAGNB = SumVTAGNB+ zVTAGNBS;
                
                double zABNRET = rs.getDouble("ABNRET");
                Detalle[fila][4] = JFormato.format(zABNRET);
                SumABNRET =SumABNRET+ zABNRET;
               
                double zVTARET = rs.getDouble("VTARET");
                Detalle[fila][5] = JFormato.format(zVTARET);
                SumVTARET =SumVTARET+ zVTARET;
                
                double zCNJESPER = rs.getDouble("CNJESPER");
                Detalle[fila][6] = JFormato.format(zCNJESPER);
                SumCNJESPER =SumCNJESPER+ zCNJESPER;
                
                double zCNJCONSI = rs.getDouble("CNJCONSI");
                Detalle[fila][7] = JFormato.format(zCNJCONSI);
                SumCNJCONSI =SumCNJCONSI+ zCNJCONSI;
                
                double zCHQPCONS = rs.getDouble("CHQPCONS");
                Detalle[fila][8] = JFormato.format(zCHQPCONS);
                SumCHQPCONS =SumCHQPCONS+ zCHQPCONS;
                
                double zVVLRRETENI = rs.getDouble("VVLRRETENI");
                Detalle[fila][9] = JFormato.format(zVVLRRETENI);
                SumVVLRRETENI =SumVVLRRETENI+ zVVLRRETENI;
                
                double zVVLRABONAD = rs.getDouble("VVLRABONAD");
                Detalle[fila][10] = JFormato.format(zVVLRABONAD);
                SumVVLRABONAD =SumVVLRABONAD+ zVVLRABONAD;
                
                double zCVLRRETENI = rs.getDouble("CVLRRETENI");
                Detalle[fila][11] = JFormato.format(zCVLRRETENI);
                SumCVLRRETENI =SumCVLRRETENI+ zCVLRRETENI;
                
                double zCVLRABONAD = rs.getDouble("CVLRABONAD");
                Detalle[fila][12] = JFormato.format(zCVLRABONAD);
                SumCVLRABONAD =SumCVLRABONAD+ zCVLRABONAD;
            }
            CantidadFila = fila;
            if(fila != -1){
                fila++;
                CantidadFila = fila;
                jButton2.setEnabled(true);
                Detalle[fila][0] = "TOTAL";
                Detalle[fila][1] = JFormato.format(SumCCMBCHEQ);
                Detalle[fila][2] = JFormato.format(SumEFTCONS);
                Detalle[fila][3] = JFormato.format(SumVTAGNB);
                Detalle[fila][4] = JFormato.format(SumABNRET);
                Detalle[fila][5] = JFormato.format(SumVTARET);
                Detalle[fila][6] = JFormato.format(SumCNJESPER);
                Detalle[fila][7] = JFormato.format(SumCNJCONSI);
                Detalle[fila][8] = JFormato.format(SumCHQPCONS);
                Detalle[fila][9] = JFormato.format(SumVVLRRETENI);
                Detalle[fila][10] = JFormato.format(SumVVLRABONAD);
                Detalle[fila][11] = JFormato.format(SumCVLRRETENI);
                Detalle[fila][12] = JFormato.format(SumCVLRABONAD);
             }else{
                jButton2.setEnabled(false);
            }
            jTable1.getColumnModel().getColumn(1).setWidth(10000);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Detalle, Cabecera));
        }catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,"JIngresosXPeriodos:getInformacion "+e.getMessage());
        }
    }
    public void Cleartable(){
        for (int i = 0; i < Detalle.length; i++) {
            Detalle[i][1] = "";
            Detalle[i][2] = "";
            Detalle[i][3] = "";
            Detalle[i][4] = "";
            Detalle[i][5] = "";
            Detalle[i][6] = "";
            Detalle[i][7] = "";
            Detalle[i][8] = "";
            Detalle[i][9] = "";
            Detalle[i][10] = "";
            Detalle[i][11] = "";
            Detalle[i][12] = "";
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
            java.util.logging.Logger.getLogger(JIngresosXPeriodos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JIngresosXPeriodos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JIngresosXPeriodos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JIngresosXPeriodos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
           public void run() {
                new JIngresosXPeriodos(null, null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTResumen;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    // End of variables declaration//GEN-END:variables
}
