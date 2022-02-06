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
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author GACA1186
 */
public class JControl8 extends javax.swing.JFrame {

    /** 
     * Creates new form JControl8
     */
    private JConection JBase_Datos;
    private Connection Cn;
    private Vector Cab, Det , Fila;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private double TPreEnero, TEjeEnero,TPreFebrero, TEjeFebrero, TPreMarzo, TEjeMarzo, TPreAbril, TEjeAbril,TPreMayo, TEjeMayo;
    private double TPreJunio, TEjeJunio,TPreJulio, TEjeJulio, TPreAgosto, TEjeAgosto, TPreSep, TEjeSept,TPreOct, TEjeOct;
    private double TPreNov, TEjeNov,TPreDic, TEjeDic;
    public JControl8(JConection JBase_Datos3, Connection Cn2) {
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        Cab = new Vector();
        Det= new Vector();
        Cab.add("Cuenta");
        Cab.add("Descripcion");
        Cab.add("Enero Pre.");
        Cab.add("Enero Ejec.");
        Cab.add("Enero Dif.");
        Cab.add("Febrero Pre.");
        Cab.add("Febrero Ejec.");
        Cab.add("Febrero Dif.");
        Cab.add("Marzo Pre.");
        Cab.add("Marzo Ejec.");
        Cab.add("Marzo Dif.");
        Cab.add("Abril Pre.");
        Cab.add("Abril Ejec.");
        Cab.add("Abril Dif.");
        Cab.add("Mayo Pre.");
        Cab.add("Mayo Ejec.");
        Cab.add("Mayo Dif.");
        Cab.add("Junio Pre.");
        Cab.add("Junio Ejec.");
        Cab.add("Junio Dif.");
        Cab.add("Julio Ejec.");
        Cab.add("Julio Pre.");
        Cab.add("Julio Dif.");
        Cab.add("Agosto Ejec.");
        Cab.add("Agoto Pre.");
        Cab.add("Agosto Dif.");
        Cab.add("Septi Ejec.");
        Cab.add("Septi Pre.");
        Cab.add("Septi Dif.");
        Cab.add("Octu Pre..");
        Cab.add("Octu Ejec.");
        Cab.add("Octu Dif.");
        Cab.add("Nov Pre.");
        Cab.add("Nov Ejec.");
        Cab.add("Nov Dif.");
        Cab.add("Dic Pre.");
        Cab.add("Dic Ejec.");
        Cab.add("Dic Dif.");
        JFormato= new DecimalFormat(NumeroFormato);
        initComponents();
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
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Cuenta", "Descripcion", "Enero Pre.", "Enero Eje.", "Enero Dif.", "Febrero Pre.", "Febrero Ejec.", "Febreo Dif.", "Marzo Pre.", "Marzo Ejec.", "Marzo Dif.", "Abril Pre.", "Abril Ejec.", "Abril Dif.", "Mayo Pre.", "Mayo Ejec.", "Mayo Dif.", "Junio Pre.", "Junio Ejec.", "Junio Dif.", "Julio Pre.", "Julio Ejec.", "Julio Dif.", "Agosto Pre.", "Agosto Ejec.", "Agosto Dif.", "Septi Pre.", "Septi Ejec.", "Septi Dif.", "Octu Pre.", "Octu Ejec.", "Octu Dif.", "Nov Pre.", "Nov Ejec.", "Nov Dif.", "Dic Pre.", "Dic Ejec.", "Dic Dif."
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Consultar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "2016", "2015" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1978, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Control 8% Administracion", jPanel1);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        TPreEnero=0; TEjeEnero=0;TPreFebrero=0; TEjeFebrero=0; TPreMarzo=0; TEjeMarzo=0; TPreAbril=0; TEjeAbril=0;TPreMayo=0; TEjeMayo=0;
        TPreJunio=0; TEjeJunio=0;TPreJulio=0; TEjeJulio=0; TPreAgosto=0; TEjeAgosto=0; TPreSep=0; TEjeSept=0;TPreOct=0; TEjeOct=0;
        TPreNov=0; TEjeNov=0;TPreDic=0; TEjeDic=0;
        this.getPresupuesto_Gastos_Administrativos();
        this.getPresupuesto_Costos_Administrativos();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    public String getNombre_Cuenta(String Cuenta){
        String Nombre = "";
        try {
            String Str_Sql ="select * from condat2006.cuenta where codcta='"+Cuenta+"'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(rs.next()){
                Nombre =rs.getString("NOMCTA");
            }
            rs.close();
        } catch (Exception e) {
        }
        return Nombre;
    }
    public void getPresupuesto_Gastos_Administrativos(){
        try {
            String Ano=jComboBox1.getSelectedItem().toString().trim();
            String Str_Sql ="select ctapre, sum(ENIPRE) as ENIPRE, sum(FEIPRE) as FEIPRE, sum(MAIPRE) as MAIPRE,"
                            + " sum(ABIPRE) as ABIPRE, sum(MYIPRE) as MYIPRE , sum(MYIPRE) as MYIPRE, sum(JUIPRE) as JUIPRE,"
                            + " sum(JLIPRE) as JLIPRE, sum(AGIPRE) as AGIPRE, sum(SEIPRE) as SEIPRE, sum(OCIPRE) as OCIPRE,"
                            + " sum(OCIPRE) as OCIPRE, sum(NOIPRE) as NOIPRE, sum(DIIPRE) as DIIPRE "
                            + " from condat2006.presup where anopre="+Ano+" and cenpre like '10%' and ctapre like '5%' "
                            + " group by ctapre "
                            + " order by ctapre asc "; 
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Det.clear();
            while(rs.next()){
                 String Cuenta = rs.getString("ctapre");
                 double E_Enero = this.getContabilidad(Ano, "01", Cuenta);
                 double E_Febrero = this.getContabilidad(Ano, "02", Cuenta);
                 double E_Marzo = this.getContabilidad(Ano, "03", Cuenta);
                 double E_Abril = this.getContabilidad(Ano, "04", Cuenta);
                 double E_Mayo = this.getContabilidad(Ano, "05", Cuenta);
                 double E_Junio = this.getContabilidad(Ano, "06", Cuenta);
                 double E_Julio = this.getContabilidad(Ano, "07", Cuenta);
                 double E_Agosto = this.getContabilidad(Ano, "08", Cuenta);
                 double E_Sep = this.getContabilidad(Ano, "09", Cuenta);
                 double E_Oct = this.getContabilidad(Ano, "10", Cuenta);
                 double E_Nov = this.getContabilidad(Ano, "11", Cuenta);
                 double E_Dic = this.getContabilidad(Ano, "12", Cuenta);
                 System.out.println("Cuenta-> "+Cuenta);
                 this.Fila = new Vector();
                 Fila.add(Cuenta);
                 Fila.add(this.getNombre_Cuenta(Cuenta));
                 
                 
                 Fila.add(JFormato.format(rs.getDouble("ENIPRE")));
                 Fila.add(JFormato.format(E_Enero));
                 Fila.add(JFormato.format(rs.getDouble("ENIPRE")-E_Enero));
                 this.TPreEnero = this.TPreEnero + rs.getDouble("ENIPRE");
                 this.TEjeEnero = this.TEjeEnero + E_Enero;
                 
                 Fila.add(JFormato.format(rs.getDouble("FEIPRE")));
                 Fila.add(JFormato.format(E_Febrero));
                 Fila.add(JFormato.format(rs.getDouble("FEIPRE")-E_Febrero));
                 this.TPreFebrero = this.TPreFebrero+rs.getDouble("FEIPRE");
                 this.TEjeFebrero = this.TEjeFebrero+E_Febrero;
                 
                 Fila.add(JFormato.format(rs.getDouble("MAIPRE")));
                 Fila.add(JFormato.format(E_Marzo));
                 Fila.add(JFormato.format(rs.getDouble("MAIPRE")-E_Marzo));
                 this.TPreMarzo = this.TPreMarzo+rs.getDouble("MAIPRE");
                 this.TEjeMarzo =  this.TEjeMarzo +E_Marzo;
                 
                 Fila.add(JFormato.format(rs.getDouble("ABIPRE")));
                 Fila.add(JFormato.format(E_Abril));
                 Fila.add(JFormato.format(rs.getDouble("ABIPRE")-E_Abril));
                 this.TPreAbril = this.TPreAbril+rs.getDouble("ABIPRE");
                 this.TEjeAbril = this.TEjeAbril+E_Abril;
                 
                 Fila.add(JFormato.format(rs.getDouble("MYIPRE")));
                 Fila.add(JFormato.format(E_Mayo));
                 Fila.add(JFormato.format(rs.getDouble("MYIPRE")-E_Mayo));
                 this.TPreMayo = this.TPreMayo  +rs.getDouble("MYIPRE");
                 this.TEjeMayo = this.TEjeMayo+E_Mayo;
                 
                 
                 Fila.add(JFormato.format(rs.getDouble("JUIPRE")));
                 Fila.add(JFormato.format(E_Junio));
                 Fila.add(JFormato.format(rs.getDouble("JUIPRE")-E_Abril));
                 this.TPreJunio = this.TPreJunio + rs.getDouble("JUIPRE");
                 this.TEjeJunio = this.TEjeJunio + E_Junio;
                 
                 Fila.add(JFormato.format(rs.getDouble("JLIPRE")));
                 Fila.add(JFormato.format(E_Julio));
                 Fila.add(JFormato.format(rs.getDouble("JLIPRE")-E_Julio));
                 this.TPreJulio = this.TPreJulio+rs.getDouble("JLIPRE");
                 this.TEjeJulio = this.TEjeJulio +E_Julio;
                 
                 Fila.add(JFormato.format(rs.getDouble("AGIPRE")));
                 Fila.add(JFormato.format(E_Agosto));
                 Fila.add(JFormato.format(rs.getDouble("AGIPRE")-E_Agosto));
                 this.TPreAgosto = this.TPreAgosto+rs.getDouble("AGIPRE");
                 this.TEjeAgosto =this.TEjeAgosto+E_Agosto;
                 
                 Fila.add(JFormato.format(rs.getDouble("SEIPRE")));
                 Fila.add(JFormato.format(E_Sep));
                 Fila.add(JFormato.format(rs.getDouble("SEIPRE")-E_Sep));
                 this.TPreSep =  this.TPreSep + rs.getDouble("SEIPRE");
                 this.TEjeSept = this.TEjeSept +E_Sep;
                 
                 Fila.add(JFormato.format(rs.getDouble("OCIPRE")));
                 Fila.add(JFormato.format(E_Oct));
                 Fila.add(JFormato.format(rs.getDouble("OCIPRE")-E_Oct));
                 this.TPreOct = this.TPreOct + rs.getDouble("OCIPRE");
                 this.TEjeOct = this.TEjeOct+E_Oct;
                 
                 Fila.add(JFormato.format(rs.getDouble("NOIPRE")));
                 Fila.add(JFormato.format(E_Nov));
                 Fila.add(JFormato.format(rs.getDouble("NOIPRE")-E_Nov));                 
                 this.TEjeNov = this.TEjeNov+rs.getDouble("NOIPRE");
                 this.TPreNov =  this.TPreNov + E_Nov;
                 
                 Fila.add(JFormato.format(rs.getDouble("DIIPRE")));
                 Fila.add(JFormato.format(E_Dic));
                 Fila.add(JFormato.format(rs.getDouble("DIIPRE")-E_Dic));
                 this.TPreDic = this.TPreDic+rs.getDouble("DIIPRE");
                 this.TEjeDic = this.TEjeDic + E_Dic;
                         
                 this.Det.add(Fila);
                 
            }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Det, Cab));
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            rs.close();
        } catch (Exception e) {
               JOptionPane.showMessageDialog(this," getPresupuesto_Costo_Gastos_Administrativos() "+e.getMessage());  
        }
    }
     public void getPresupuesto_Costos_Administrativos(){
        try {
            String Ano=jComboBox1.getSelectedItem().toString().trim();
            String Str_Sql ="select ctapre, sum(ENIPRE) as ENIPRE, sum(FEIPRE) as FEIPRE, sum(MAIPRE) as MAIPRE,"
                            + " sum(ABIPRE) as ABIPRE, sum(MYIPRE) as MYIPRE , sum(MYIPRE) as MYIPRE, sum(JUIPRE) as JUIPRE,"
                            + " sum(JLIPRE) as JLIPRE, sum(AGIPRE) as AGIPRE, sum(SEIPRE) as SEIPRE, sum(OCIPRE) as OCIPRE,"
                            + " sum(OCIPRE) as OCIPRE, sum(NOIPRE) as NOIPRE, sum(DIIPRE) as DIIPRE "
                            + " from condat2006.presup where anopre="+Ano+" and ctapre like '6%' and cenpre like '10%' "
                            + " group by ctapre "
                            + " order by ctapre asc "; 
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(rs.next()){
                 String Cuenta = rs.getString("ctapre");
                 double E_Enero = this.getContabilidad(Ano, "01", Cuenta);
                 double E_Febrero = this.getContabilidad(Ano, "02", Cuenta);
                 double E_Marzo = this.getContabilidad(Ano, "03", Cuenta);
                 double E_Abril = this.getContabilidad(Ano, "04", Cuenta);
                 double E_Mayo = this.getContabilidad(Ano, "05", Cuenta);
                 double E_Junio = this.getContabilidad(Ano, "06", Cuenta);
                 double E_Julio = this.getContabilidad(Ano, "07", Cuenta);
                 double E_Agosto = this.getContabilidad(Ano, "08", Cuenta);
                 double E_Sep = this.getContabilidad(Ano, "09", Cuenta);
                 double E_Oct = this.getContabilidad(Ano, "10", Cuenta);
                 double E_Nov = this.getContabilidad(Ano, "11", Cuenta);
                 double E_Dic = this.getContabilidad(Ano, "12", Cuenta);
                 //System.out.println("Cuenta-> "+Cuenta);
                 this.Fila = new Vector();
                 Fila.add(Cuenta);
                 Fila.add(this.getNombre_Cuenta(Cuenta));
                 
                 Fila.add(JFormato.format(rs.getDouble("ENIPRE")));
                 Fila.add(JFormato.format(E_Enero));
                 Fila.add(JFormato.format(rs.getDouble("ENIPRE")-E_Enero));
                 this.TPreEnero = this.TPreEnero + rs.getDouble("ENIPRE");
                 this.TEjeEnero = this.TEjeEnero + E_Enero;
                 
                 Fila.add(JFormato.format(rs.getDouble("FEIPRE")));
                 Fila.add(JFormato.format(E_Febrero));
                 Fila.add(JFormato.format(rs.getDouble("FEIPRE")-E_Febrero));
                 this.TPreFebrero = this.TPreFebrero+rs.getDouble("FEIPRE");
                 this.TEjeFebrero = this.TEjeFebrero+E_Febrero;
                 
                 Fila.add(JFormato.format(rs.getDouble("MAIPRE")));
                 Fila.add(JFormato.format(E_Marzo));
                 Fila.add(JFormato.format(rs.getDouble("MAIPRE")-E_Marzo));
                 this.TPreMarzo = this.TPreMarzo+rs.getDouble("MAIPRE");
                 this.TEjeMarzo =  this.TEjeMarzo +E_Marzo;
                 
                 Fila.add(JFormato.format(rs.getDouble("ABIPRE")));
                 Fila.add(JFormato.format(E_Abril));
                 Fila.add(JFormato.format(rs.getDouble("ABIPRE")-E_Abril));
                 this.TPreAbril = this.TPreAbril+rs.getDouble("ABIPRE");
                 this.TEjeAbril = this.TEjeAbril+E_Abril;
                 
                 Fila.add(JFormato.format(rs.getDouble("MYIPRE")));
                 Fila.add(JFormato.format(E_Mayo));
                 Fila.add(JFormato.format(rs.getDouble("MYIPRE")-E_Mayo));
                 this.TPreMayo = this.TPreMayo  +rs.getDouble("MYIPRE");
                 this.TEjeMayo = this.TEjeMayo+E_Mayo;
                 
                 
                 Fila.add(JFormato.format(rs.getDouble("JUIPRE")));
                 Fila.add(JFormato.format(E_Junio));
                 Fila.add(JFormato.format(rs.getDouble("JUIPRE")-E_Abril));
                 this.TPreJunio = this.TPreJunio + rs.getDouble("JUIPRE");
                 this.TEjeJunio = this.TEjeJunio + E_Junio;
                 
                 Fila.add(JFormato.format(rs.getDouble("JLIPRE")));
                 Fila.add(JFormato.format(E_Julio));
                 Fila.add(JFormato.format(rs.getDouble("JLIPRE")-E_Julio));
                 this.TPreJulio = this.TPreJulio+rs.getDouble("JLIPRE");
                 this.TEjeJulio = this.TEjeJulio +E_Julio;
                 
                 Fila.add(JFormato.format(rs.getDouble("AGIPRE")));
                 Fila.add(JFormato.format(E_Agosto));
                 Fila.add(JFormato.format(rs.getDouble("AGIPRE")-E_Agosto));
                 this.TPreAgosto = this.TPreAgosto+rs.getDouble("AGIPRE");
                 this.TEjeAgosto =this.TEjeAgosto+E_Agosto;
                 
                 Fila.add(JFormato.format(rs.getDouble("SEIPRE")));
                 Fila.add(JFormato.format(E_Sep));
                 Fila.add(JFormato.format(rs.getDouble("SEIPRE")-E_Sep));
                 this.TPreSep =  this.TPreSep + rs.getDouble("SEIPRE");
                 this.TEjeSept = this.TEjeSept +E_Sep;
                 
                 Fila.add(JFormato.format(rs.getDouble("OCIPRE")));
                 Fila.add(JFormato.format(E_Oct));
                 Fila.add(JFormato.format(rs.getDouble("OCIPRE")-E_Oct));
                 this.TPreOct = this.TPreOct + rs.getDouble("OCIPRE");
                 this.TEjeOct = this.TEjeOct+E_Oct;
                 
                 Fila.add(JFormato.format(rs.getDouble("NOIPRE")));
                 Fila.add(JFormato.format(E_Nov));
                 Fila.add(JFormato.format(rs.getDouble("NOIPRE")-E_Nov));                 
                 this.TEjeNov = this.TEjeNov+rs.getDouble("NOIPRE");
                 this.TPreNov =  this.TPreNov + E_Nov;
                 
                 Fila.add(JFormato.format(rs.getDouble("DIIPRE")));
                 Fila.add(JFormato.format(E_Dic));
                 Fila.add(JFormato.format(rs.getDouble("DIIPRE")-E_Dic));
                 this.TPreDic = this.TPreDic+rs.getDouble("DIIPRE");
                 this.TEjeDic = this.TEjeDic + E_Dic;
                 
                 this.Det.add(Fila);
                 
            }
            Fila = new Vector();
            
            Fila.add("TOTAL ");
            Fila.add("----->");
            
            Fila.add(this.JFormato.format(this.TPreEnero));
            Fila.add(this.JFormato.format(this.TEjeEnero));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreFebrero));
            Fila.add(this.JFormato.format(this.TEjeFebrero));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreMarzo));
            Fila.add(this.JFormato.format(this.TEjeMarzo));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreAbril));
            Fila.add(this.JFormato.format(this.TEjeAbril));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreMayo));
            Fila.add(this.JFormato.format(this.TEjeMayo));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreJunio));
            Fila.add(this.JFormato.format(this.TEjeJunio));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreJulio));
            Fila.add(this.JFormato.format(this.TEjeJulio));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreAgosto));
            Fila.add(this.JFormato.format(this.TEjeAgosto));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreSep));
            Fila.add(this.JFormato.format(this.TEjeSept));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreOct));
            Fila.add(this.JFormato.format(this.TEjeOct));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreNov));
            Fila.add(this.JFormato.format(this.TEjeNov));
            Fila.add("");
            
            Fila.add(this.JFormato.format(this.TPreDic));
            Fila.add(this.JFormato.format(this.TEjeDic));
            Fila.add("");
            
            Det.add(Fila);
            
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Det, Cab));
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            rs.close();
        } catch (Exception e) {
               JOptionPane.showMessageDialog(this," getPresupuesto_Costo_Gastos_Administrativos() "+e.getMessage());  
        }
    }
    public double getContabilidad(String PAno, String PMes, String PCuenta){
        double neto = 0;
        try {
            String FechaIni =PAno+PMes+"01";
            String FechaFin =PAno+PMes+"31";
            String Str_Sql = "select ctamov , sum(debmov) as deb, sum(cremov) as cre from condat2006.movimi where ctamov='"+PCuenta.trim()+"'"
                            +" and fecmov>="+FechaIni+" and fecmov<="+FechaFin+" group by ctamov ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double Debito =0, Credito=0;
            while(rs.next()){
                Debito = rs.getDouble("deb");
                Credito = rs.getDouble("cre");
            }
           neto = Debito-Credito;
           rs.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this," getContabilidad(String PAno, String PMes) "+e.getMessage());  
        }
        return neto;
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
            java.util.logging.Logger.getLogger(JControl8.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JControl8.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JControl8.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JControl8.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JControl8(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
