package Subsidio;


import BD_As400.JConection;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class JAportesPlanillaEmpleados extends javax.swing.JFrame {

    /**
     * Creates new form JAportesPlanillaEmpleados
     */
    private JConection JBase_Datos;
    private Connection Cn;
    private Object [] Cabecera ;
    private Object [] [] Detalle  ;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private String Mes;
    private String Mes2;
    private int Fila ;
    public JAportesPlanillaEmpleados(JConection JBase_Datos3, Connection Cn2) {
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        initComponents();
        Cabecera =  new Object [7];
        Detalle  = new Object [10000][7];
        this.getAño();
        JFormato= new DecimalFormat(NumeroFormato);
    }
    public void getInformacion_Nomina(){
        try {
           String Str_Sql = "Delete from selinlib.znomina ";
           boolean result = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," afiliaciones doblesafiliaciones dobles:getInformacion_Nomina() "+e.getMessage()); 
        }
    }
    
    public void get_Informacion_Adam(){
        try {
            String Str_Sql = " INSERT INTO SELINLIB.ZNOMINA "  +                                 
                              " (CEDULA, TRACVE , TRANOM, CODIGO, DESCRI , ENERO, FEBRE, MARZO, "+
                              " ABRIL, MAYO, JUNIO, JULIO, AGOST, SEPTI, OCTUB, NOVI, DICIE) "+   
                              " SELECT T3.TRARFI , T1.TRACVE , T1.TRANOM, T4.CONCOD, T4.CONDES , "+  
                              "      T2.ACIM01 , T2.ACIM02 , T2.ACIM03, T2.ACIM04,   "+            
                              "       T2.ACIM05 , T2.ACIM06 , T2.ACIM07, T2.ACIM08,  "+               
                              "       T2.ACIM09 , T2.ACIM10 , T2.ACIM11, T2.ACIM12   "+              
                              "         FROM ADAMCO.TTRAB AS T1 , ADAMCO.TACNM AS T2 , "+            
                              " ADAMCO.TTRNS AS T3  , ADAMCO.TCONO AS T4 "+                           
                              " WHERE T1.TRACVE = T2.TRACVE AND T3.TRACVE = T2.TRACVE "+               
                              " AND T2.CONCOD = T4.CONCOD "+                                          
                              " AND T2.CONCOD  IN( 2000,  2048, 2113, 2173, 2510,  2525, 2540, 2555, "+
                              "                   2005,  2058, 2123, 2183, 2513,  2528, 2543, 2558, "+
                              "                   2005,  2058, 2123, 2183, 2516,  2531, 2546, 2561, "+
                              "                   2018,  2078, 2143, 2196, 2519,  2534, 2549, 2564, "+
                              "                   2028,  2088, 2153, 2522,  2537, 2552, 2567, "+
                              "                   2038,  2098, 2163, 2505, 2600,  3110, 3110, "+
                              "                   2601,  3600,3200,3220,3240,             "+                      
                              "                   2610,  3110, 3220, 3700, "+                        
                              "                   4000, "+    
                              "                   3000,  3300, 3300, 4010, "+  
                              "                   4300,  4400,2220 ) "+    
                              " AND T2.CPRAÑO = "+jComboBox1.getSelectedItem().toString().trim() +                          
                              " ORDER BY T1.TRACVE   ";
            boolean result = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            Str_Sql = " INSERT INTO SELINLIB.ZNOMINA "+                                
                      " (CEDULA, TRACVE , TRANOM, CODIGO,ENERO, FEBRE, MARZO, "+       
                      " ABRIL, MAYO, JUNIO, JULIO, AGOST, SEPTI, OCTUB, NOVI, DICIE) "+
                      " select cedula , tracve , tranom ,   7777 , "+                  
                      "       sum(enero) , sum(febre) , sum(marzo), sum(abril), "+    
                      "       sum(mayo) , sum(junio), sum(julio), sum(agost), "+      
                      "       sum(septi) , sum(octub), sum(novi), sum(dicie) "+       
                      " from selinlib.znomina " +                                       
                      " group by tracve , tranom , cedula "; 
            result = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," JAportesPlanillaEmpleados:get_Informacion_Adam(): => "+e.getMessage()); 
        }
                
    }
    
    public void Buscar_Icbd(){
        try {
            boolean rs= false;
            
            if(jComboBox2.getSelectedItem().toString().trim().equals("01")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,enero2)  "
                            +" select cedula,9980,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            if(jComboBox2.getSelectedItem().toString().trim().equals("02")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,febre2)  "
                            +" select cedula,9981,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            
            if(jComboBox2.getSelectedItem().toString().trim().equals("03")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,marzo2)  "
                            +" select cedula,9982,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            if(jComboBox2.getSelectedItem().toString().trim().equals("04")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,abril2)  "
                            +" select cedula,9983,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            if(jComboBox2.getSelectedItem().toString().trim().equals("05")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,mayo2)  "
                            +" select cedula,9984,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
       
            if(jComboBox2.getSelectedItem().toString().trim().equals("06")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,junio2)  "
                            +" select cedula,9985,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            if(jComboBox2.getSelectedItem().toString().trim().equals("07")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,julio2)  "
                            +" select cedula,9986,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            if(jComboBox2.getSelectedItem().toString().trim().equals("08")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,agost2)  "
                            +" select cedula,9987,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            if(jComboBox2.getSelectedItem().toString().trim().equals("09")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,septi2)  "
                            +" select cedula,9988,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            if(jComboBox2.getSelectedItem().toString().trim().equals("10")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,octub2)  "
                            +" select cedula,9989,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            if(jComboBox2.getSelectedItem().toString().trim().equals("11")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,novi2)  "
                            +" select cedula,9990,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            if(jComboBox2.getSelectedItem().toString().trim().equals("12")){
                String Str_Sql = "insert into selinlib.znomina (cedula,codigo, tranom,dicie2)  "
                            +" select cedula,9991,tranom, ibcd from selinlib.znomina as t1 , SUBSILIB.CERPLAD as t2 where codigo = 7777 "
                            +" and CEDPLA = CEDULA AND PERDET = "+jComboBox1.getSelectedItem().toString().trim()+jComboBox2.getSelectedItem().toString().trim();
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," JAportesPlanillaEmpleados:get_Informacion_Adam() "+e.getMessage()); 
        }
    }
    public double getValor_Mes(String PCedula, String PMes){
        double Vlr = 0;
        try {
            String PCodigo = "";
            if(PMes.trim().equals("01")){
                PCodigo = "9980";
            }
            if(PMes.trim().equals("02")){
                PCodigo = "9981";
            }
            if(PMes.trim().equals("03")){
                PCodigo = "9982";
            }
            if(PMes.trim().equals("04")){
                PCodigo = "9983";
            }
            if(PMes.trim().equals("05")){
                PCodigo = "9984";
            }
            if(PMes.trim().equals("06")){
                PCodigo = "9985";
            }
            if(PMes.trim().equals("07")){
                PCodigo = "9986";
            }
            if(PMes.trim().equals("08")){
                PCodigo = "9987";
            }
            if(PMes.trim().equals("09")){
                PCodigo = "9988";
            }
            if(PMes.trim().equals("10")){
                PCodigo = "9989";
            }
            if(PMes.trim().equals("11")){
                PCodigo = "9990";
            }
            if(PMes.trim().equals("12")){
                PCodigo = "9991";
            }
            String Str_Sql = "Select * from selinlib.znomina where codigo ="+PCodigo +" and Cedula="+PCedula;
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            if(rs.next()){
                Vlr = rs.getDouble(this.getMes(PMes));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," JAportesPlanillaEmpleados:getValor_Mes() "+e.getMessage()); 
        }
        return Vlr;
    }
    public String getMes(String PMes){
                String XMes ="";
                if(PMes.trim().equals("01")){
                    XMes= "Enero2";
                    this.Mes ="Enero";
                }
                if(PMes.trim().equals("02")){
                    XMes = "Febre2";
                    this.Mes = "Febre";
                }
                if(PMes.trim().equals("03")){
                    XMes= "Marzo2";
                    this.Mes = "Marzo";
                }
                if(PMes.trim().equals("04")){
                    XMes= "Abril2";
                    this.Mes  =  "Abril";
                }
                if(PMes.trim().equals("05")){
                    XMes = "Mayo2";
                    this.Mes = "Mayo";
                }
                if(PMes.trim().equals("06")){
                    XMes = "Junio2";
                    this.Mes = "Junio";
                }
                if(PMes.trim().equals("07")){
                     XMes ="Julio2";
                     this.Mes ="Julio";
                }
                if(PMes.trim().equals("08")){
                     XMes ="Agost2";
                     this.Mes ="Agost";
                }
                if(PMes.trim().equals("09")){
                     XMes ="Septi2";
                     this.Mes = "Septi";
                }
                if(PMes.trim().equals("10")){
                     XMes ="Octub2";
                     this.Mes = "Octub";
                }
                if(PMes.trim().equals("11")){
                     XMes ="Novi2";
                     this.Mes = "Novi";
                }
                if(PMes.trim().equals("12")){
                    XMes ="Dicie2";
                    this.Mes = "Dicie";
                }
                return XMes;
    }
    
    public void getMostrar_Informacion(){
        try {
           Cabecera[0] = "Codigo Empleado ";
           Cabecera[1] = "Cedula ";
           Cabecera[2] = "Codigo ";
           Cabecera[3] = "Nombre ";
           Cabecera[4] = "Vlr Mes";
           Cabecera[5] = "Icdb";
           Cabecera[6] = "Diferencias";
           String Str_Sql = "";
           
               Str_Sql = " Select * from selinlib.znomina where codigo <= 7777 "
                       + " order by tranom, codigo ";
               ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
               Fila = -1;
               
           while(rs.next()){
                   
                   Fila++;
                   
                   double PCedula = rs.getInt("Cedula");
                   int XCodigo = rs.getInt("Codigo");
                   if(XCodigo!=7777){
                    Detalle [Fila][0] = rs.getString("Tracve").trim();
                   }
                   Detalle [Fila][1] = JFormato.format(PCedula);
                   
                   if (XCodigo != 7777){
                       Detalle [Fila][2] = XCodigo; 
                       Detalle [Fila][3] = rs.getString("tranom");
                   }else{
                        Detalle [Fila][3] = "Total-> ";   
                        Detalle [Fila][2] = "";
                        Detalle [Fila][1] = "";
                   }
                   this.getMes(jComboBox2.getSelectedItem().toString().trim());
                   
                   double Vlr = 0 ;
                   if(XCodigo==7777){
                     String SCedula = String.valueOf(PCedula);
                     Vlr = this.getValor_Mes(SCedula ,jComboBox2.getSelectedItem().toString().trim());
                   }
                   double VlrMes = rs.getDouble(this.Mes);
                   Detalle [Fila][4] = JFormato.format(VlrMes);
                   Detalle [Fila][5] = JFormato.format(Vlr);
                   if (XCodigo == 7777){
                    Detalle [Fila][6] = JFormato.format(VlrMes-Vlr);
                   }
               
               
           }
           jTable1.setModel(new javax.swing.table.DefaultTableModel(Detalle, Cabecera));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," JAportesPlanillaEmpleados:getMostrar_Informacion() "+e.getMessage()); 
        }
    }
    public void Clear_Table(){
        for (int i = 0; i < Fila; i++) {
            Detalle[i][0] = "";
            Detalle[i][1] = "";
            Detalle[i][2] = "";
            Detalle[i][3] = "";
            Detalle[i][4] = "";
            Detalle[i][5] = "";
        }
        Fila = -1;
    }
    public void getAño(){
        try {
            String Str_Sql ="Select distinct CPRAÑO from adamco.tacno order by 1 desc";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(rs.next()){
                int PAño = rs.getInt("CPRAÑO");
                if (PAño>=1990){
                    jComboBox1.addItem(""+PAño);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionNomina:getAño()"+e.getMessage()); 
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
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Planilla Unica de Empleados");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo Empleado", "Cedula", "Codigo", "Nombre", "Vlr Mes", "Icbd", "Diferencia"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Año");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Mes");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel1)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel2)))
                        .addGap(0, 509, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Aporte Planilla", jPanel1);

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
        this.Clear_Table();
        this.getInformacion_Nomina();
        this.get_Informacion_Adam();
        Buscar_Icbd();
        getMostrar_Informacion();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(JAportesPlanillaEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JAportesPlanillaEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JAportesPlanillaEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JAportesPlanillaEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JAportesPlanillaEmpleados(null,null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
