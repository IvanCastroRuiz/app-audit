package Subsidio;


import BD_As400.JConection;
import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.CommandCall;
import java.sql.Connection;
import java.sql.ResultSet;
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
public class JSubsidioFamiliar extends javax.swing.JFrame {

    /**
     * Creates new form JSubsidioFamiliar
     */
    private JConection JBase_Datos;
    private Connection Cn;
    final AS400 ServicioAS400;
    private Object [] Cabecera ;
    private Object [] [] Detalle  ;
    private String NumeroFormato = "###,###,###,###.#########";
    private DecimalFormat JFormato ;
    
    
    public JSubsidioFamiliar(AS400 serivicioComando ,JConection JBase_Datos3, Connection Cn2) {
        JFormato= new DecimalFormat(NumeroFormato);
        ServicioAS400 = serivicioComando;
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        initComponents();
        this.getFecha();
    }

    public void getFecha(){
        try {
            String Str_Sql = " select pfeliq from subsilib.liquipag  "+
                             " where pfeliq >= 20120101  "+                    
                             " group by pfeliq "+
                             " order by pfeliq desc";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            jComboBox1.addItem("");
            while(rs.next()){
                jComboBox1.addItem(""+rs.getInt("pfeliq"));   
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"JSubsidioFamiliar: getFecha() "+e.getMessage()); 
        }
    }
    public double getParametros(String Periodo){
        double Vlr = 0;
        try {
            String Str_Sql = " SELECT parcod , parvsn FROM subsilib.mparam WHERE parcod <= "+Periodo.trim()+
                              " and parcod >= 19900101 ORDER BY parcod desc ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                Vlr = rs.getDouble("parvsn");
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"JSubsidioFamiliar: getParametros() "+e.getMessage()); 
        }
        return Vlr;
    }
    public void Generar_liquidacion(){
        try {
            if(!jComboBox1.getSelectedItem().toString().trim().equals("")){
                 String Str_Sql = " select pfeliq, ppeliq from subsilib.liquipag  "+
                             " where pfeliq >= 20120101 and pfeliq= "+jComboBox1.getSelectedItem().toString().trim()+                    
                             " group by pfeliq,ppeliq "+
                             " order by ppeliq desc";
                ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                
                boolean Brs = false;
                String StrSql = "DELETE FROM SELINLIB.ZSUBSIDIO";
                Brs = JBase_Datos.Exc_Sql(this.Cn,StrSql);
                
                while (rs.next()){
                    int Periodo = rs.getInt("ppeliq");
                    int Fe = rs.getInt("pfeliq");
                    String XPeriodo = Periodo+"01";
                    double Vlr = this.getParametros(XPeriodo);
                    if (Vlr == 0){
                        JOptionPane.showMessageDialog(null,"Periodo errado "+Periodo); 
                    }else{
                        final CommandCall call = new CommandCall(this.ServicioAS400, "CALL PGM(SELINLIB/CLAUDI011J) PARM('1' '"+Vlr+"' '" +Periodo+"' "+Fe+" '0')");
                        System.out.println(call.run());
                        final AS400Message[] msgs = call.getMessageList();
                        for (final AS400Message msg : msgs) {
                            JOptionPane.showMessageDialog(this,msg.getText());    
                        }
                    }
                }
                rs.close();
            }else{
                JOptionPane.showMessageDialog(null,"Debes seleccionar la fecha para Ejecutar"); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"JSubsidioFamiliar: Generar_liquidacion() "+e.getMessage()); 
        }
    }
    public void setTabla(){
        try {
            String Str_Sql = "";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(rs.next()){
                
            }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Detalle, Cabecera));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"JSubsidioFamiliar: setTabla() "+e.getMessage()); 
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Liquidacion de Subsidio Familliar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Documento", "Nombre del Trabajador", "Fecha", "Periodo", "Liquidacion Sistema", "Cant. Hijos", "Cant. Padres", "Hij Discapacitado", "Padr Discapacitados", "Embargo", "Actividad Agro", "Calculado", "Diferencia", "Retirados"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Generar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha Liquidacion");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1196, Short.MAX_VALUE)
                .addGap(22, 22, 22))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jButton1)
                        .addGap(39, 39, 39)
                        .addComponent(jButton2))
                    .addComponent(jLabel1))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(44, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Simulacion de Liquidacion de Subsidio", jPanel1);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.Generar_liquidacion();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(JSubsidioFamiliar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JSubsidioFamiliar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JSubsidioFamiliar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JSubsidioFamiliar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JSubsidioFamiliar(null,null,null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
