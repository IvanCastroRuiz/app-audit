/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Auditoria;

import BD_Auditoria.JConeccion_AudiSys;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import javax.mail.*;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




/**
 *
 * @author Garyn Carrillo
 */
public class JMail extends javax.swing.JFrame {

    /**
     * Creates new form JMail
     */
    private JConeccion_AudiSys JBase_Datos;
    private Connection Connection_BD;
    
    private String XUsuario, XIdTaller, XRiesgo,XObjetivo,XHoras, XFecha;
    public JMail(JConeccion_AudiSys JBase_Datos, Connection Connection_BD, String Usuario, String IdTaller, String Riesgo, String Objetivos, String Horas, String Fecha) {
        this.JBase_Datos = JBase_Datos;
        this.Connection_BD = Connection_BD;
        this.XUsuario =Usuario;
        this.XIdTaller=IdTaller;
        this.XRiesgo = Riesgo;
        this.XObjetivo = Objetivos;
        this.XHoras = Horas;
        this.XFecha = Fecha;
        initComponents();
        jtfAsunto.setText("Trabajo de Auditoria Asignado  No Trabajo "+this.XIdTaller);
        jtaMensaje.setText("Nombre del Proceso a Auditar: "+getNombreTrabajo()+"\n\nRiesgos: "+this.XRiesgo+"\n\nObjetivos: "+this.XObjetivo+"\n\nFecha de Inicio: "+this.XFecha+" Horas Estimadas "+this.XHoras);
        this.getCorreo();
    }
    public String getNombreTrabajo(){
        String Nombre="";
        try {
            String Str_Sql = "select Proceso from JTaller where IdTaller="+this.XIdTaller;
            ResultSet Rs = JBase_Datos.SQL_QRY(Connection_BD,Str_Sql);
            if(Rs.next()){
                Nombre = Rs.getString("Proceso");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getNombreTrabajo()"+e.getMessage());
        }
        return Nombre;
    }
    
    public void getCorreo(){
        try {
            String Str_Sql = "select NombreCompleto, correo from JUser where NombreCompleto='"+this.XUsuario.trim()+"'";
            ResultSet Rs = JBase_Datos.SQL_QRY(Connection_BD,Str_Sql);
            jtfDestino.setText("");
            if(Rs.next()){
                jtfDestino.setText(Rs.getString("correo"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"getCorreo() "+e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfRemitente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfDestino = new javax.swing.JTextField();
        jtfAsunto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jpfPasswordRemitente = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaMensaje = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Email de Trabajo asignado");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("De:");

        jtfRemitente.setText("auditoria@comfamiliar.com.co");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Para:");

        jtfDestino.setToolTipText("Separar por comas");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("asunto");

        jpfPasswordRemitente.setToolTipText("Clave del correo electronico");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Clave:");

        jtaMensaje.setColumns(20);
        jtaMensaje.setRows(5);
        jScrollPane1.setViewportView(jtaMensaje);

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
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
                .addGap(135, 135, 135)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(156, 156, 156))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jpfPasswordRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                            .addComponent(jtfAsunto)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(jLabel3)))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpfPasswordRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfAsunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         if(enviarEmail() ) {
            JOptionPane.showMessageDialog(this,"Mensaje enviado!");
            jtaMensaje.requestFocus(true);
         } else {
            JOptionPane.showMessageDialog(this,"Por el momento NO SE PUDO ENVIAR el mensaje.");
         }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(JMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
     
    }
    
    
      private boolean enviarEmail() {
        try{
            // Propiedades de la conexión
            Properties props = new Properties();
            //        props.setProperty("mail.smtp.host",  "smtp.gmail.com");
            //        props.setProperty("mail.smtp.starttls.enable", "true");
            //        props.setProperty("mail.smtp.port", "587");
            //        props.setProperty("mail.smtp.auth", "true");
        
            props.setProperty("mail.smtp.host",  "172.16.0.80");
            props.setProperty("mail.smtp.starttls.enable", "false");
            props.setProperty("mail.smtp.port", "25");
            props.setProperty("mail.smtp.auth", "true");
        
            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);
            //Recoger los datos
            String str_De       = jtfRemitente.getText();
            String str_PwRemitente      = jpfPasswordRemitente.getText();              
            String str_Para     = jtfDestino.getText();
            String str_Asunto = jtfAsunto.getText();
            String str_Mensaje = jtaMensaje.getText();
            //Obtenemos los destinatarios
            String destinos[] = str_Para.split(",");
                 
            // Cnstruimos el mensaje
            MimeMessage message = new MimeMessage(session);
        
            message.setFrom(new InternetAddress( str_De ));
             
            //Otra forma de especificar las direcciones de email
            //a quienes se enviar el correo electronico
            //Forma 1
            //Address [] receptores = new Address []{
            //      new InternetAddress ("fuerenio@gmail.com"),
            //      new InternetAddress ("gonzasilve@gmail.com")
            //  };
            //Forma 2
            //  Address [] receptores = new Address []{
            //      new InternetAddress ( str_De )
            // };
            //Forma 3
            Address [] receptores = new Address [ destinos.length ];
            int j = 0;
            while(j<destinos.length){                   
                receptores[j] = new InternetAddress ( destinos[j] ) ;                  
                j++;               
            }
 
         
            //receptores.
            message.addRecipients(Message.RecipientType.TO, receptores);       
            message.setSubject( str_Asunto );       
            message.setText( str_Mensaje );
            
            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            
            
            t.connect(str_De, str_PwRemitente);
        
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        
            // Cierre de la conexion.
            t.close();
            return true;
       }catch (Exception e){
        JOptionPane.showMessageDialog(this,"Enviar "+e.getMessage());
        return false;
      }      
 }
    
    
    
    
    
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField jpfPasswordRemitente;
    private javax.swing.JTextArea jtaMensaje;
    private javax.swing.JTextField jtfAsunto;
    private javax.swing.JTextField jtfDestino;
    private javax.swing.JTextField jtfRemitente;
    // End of variables declaration//GEN-END:variables
}