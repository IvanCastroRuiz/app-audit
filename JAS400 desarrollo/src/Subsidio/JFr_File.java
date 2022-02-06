package Subsidio;


import BD_As400.JConection;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class JFr_File extends javax.swing.JFrame {
    private JArchivo JFile;
    private String Ruta;
    private String NombreArchivo;
    private JConection JBase_Datos;
    private Connection Cn;
    private Vector VSubsidio;
    private Vector VSisSubsidio;
    private String Txt_SUBSILIB;
    private Vector Lista;
    
    private Vector Cab;
    private Vector Det;
    private Vector Fila;
    
    
    /**
     * Creates new form JTaller1
     */
    public JFr_File(JConection JBase_Datos3, Connection Cn2  ) {
        
        this.Cab = new Vector();
        
        this.Cab.add("Archivo");
        this.Cab.add("Sistema Subsidio");
        this.Cab.add("Cantidad");
        this.Fila =new Vector();
        
        
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        VSubsidio = new Vector();
        VSisSubsidio = new Vector();
        Lista = new Vector();
        //System.setProperty("file.encoding", "UTF-8");
        System.setProperty("file.encoding", "ANSI");
        initComponents();
        
    }
    
    
    public void setRuta(String SRuta){
         this.Ruta = SRuta;
         this.LeerArchivo();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jButton1 = new javax.swing.JButton();
        jBEjecutar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Complejida de Algoritmo");

        jButton1.setText("Examinar...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jBEjecutar.setText("Ejecutar");
        jBEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEjecutarActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jList1);

        jTextField1.setText("REDDDMMAA");

        jTextField2.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Nombre Archivo");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Registro Leidos");

        jTextField3.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Registro Coincida");

        jTextField4.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Registro Subsidio");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Archivo Plano", "Sistema Subsidio", "Cantidad"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(32, 32, 32)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(156, 156, 156)
                                .addComponent(jLabel1)
                                .addGap(66, 66, 66)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(20, 20, 20)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(141, 141, 141))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField3)
                                .addGap(18, 18, 18)
                                .addComponent(jBEjecutar)
                                .addGap(33, 33, 33))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jBEjecutar)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFile = new JArchivo(new javax.swing.JFrame(), true, this);
        JFile.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jBEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEjecutarActionPerformed
        // TODO add your handling code here:
        try {
            this.NombreArchivo=jTextField1.getText().trim();
            this.Fila.clear();
            this.Comparar();
            this.CompararDetalle();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ejecutar "+e.getMessage());
        }
    }//GEN-LAST:event_jBEjecutarActionPerformed
    public void Comparar(){
        try {
            String Str_Sql="select count(*) as cantidad from selinlib.red as t1, subsilib."+ this.NombreArchivo+" as t2 "+
                            " where  t1.TARJE004 = t2.TARJE004 ";                                
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()) { 
                jTextField3.setText(""+rs.getDouble("cantidad"));
            }
            Str_Sql="select count(*) as cantidad from subsilib."+this.NombreArchivo;
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()) { 
                jTextField4.setText(""+rs.getDouble("cantidad"));
            }
            rs.close();
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Comparar() "+e.getMessage());
        }
    }
    public  boolean Existe(String Txt){
        boolean Ct=false;
        int Cuenta=0;
        ResultSet Qrs=null;
        try {
            
            String Str_Sql="select TARJE004 as cantidad from subsilib."+this.NombreArchivo+""
                    + "  where TARJE004='"+Txt.trim()+"' ";
            Qrs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            String noTxt="";
            while(Qrs.next()) { 
                noTxt=Qrs.getString("cantidad");
                Ct=true;
                Cuenta++;
            }
            Qrs.close();
            this.JBase_Datos.CloseResulSet();
            
            if(Ct==false){
                this.Det = new Vector();
                this.Det.add(Txt.trim());
                this.Det.add(noTxt);
                this.Det.add(""+Cuenta);
                this.Fila.add(this.Det);
            }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(Fila, Cab));
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Existe() "+e.getMessage());
        }
        return Ct;
    }
    public void CompararDetalle(){
        try{
            for (int i = 0; i < this.Lista.size(); i++) {
                this.Existe(Lista.elementAt(i).toString());
            }
        }catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "CompararDetalle() "+e.getMessage());
        }
   
    }
    
    public void LeerArchivo(){
          try{
            // Abrimos el archivo
              
            FileInputStream fstream = new FileInputStream(Ruta);
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            
            String Str_Sql="delete from selinlib.red ";
            boolean Rs = this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
            String UltimoRegistro ="";
            int count=0;
            if(Rs){
                while ((strLinea = buffer.readLine()) != null)   {
                    Lista.add(strLinea.toLowerCase());
                    Str_Sql ="insert into selinlib.red values('"+strLinea.toLowerCase()+"')";
                    Rs= this.JBase_Datos.Exc_Sql(Cn, Str_Sql);
                    count++;
                    UltimoRegistro = strLinea.toLowerCase();
                }
                
                if(Lista.size()>0){
                    UltimoRegistro = UltimoRegistro.substring(15, 23).trim();
                    String ano=UltimoRegistro.substring(2, 4);
                    String mes=UltimoRegistro.substring(4, 6);
                    String dia=UltimoRegistro.substring(6, 8);
                    this.NombreArchivo="RED"+dia+mes+ano;
                    jTextField1.setText(this.NombreArchivo);
                    jTextField2.setText(""+count);
                    jList1.setListData(Lista);
                    jBEjecutar.setEnabled(true);
                }
            }
            // Cerramos el archivo
            entrada.close();
        }catch (Exception e){ //Catch de excepciones
            javax.swing.JOptionPane.showMessageDialog(this, "ReadFile:_LeerArchivo() "+e.getMessage());
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
            java.util.logging.Logger.getLogger(JFr_File.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFr_File.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFr_File.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFr_File.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JFr_File(null,null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBEjecutar;
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
