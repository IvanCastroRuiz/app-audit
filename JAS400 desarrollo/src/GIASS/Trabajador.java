/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GIASS;

/**
 *
 * @author GACA1186
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
public class Trabajador extends javax.swing.JFrame {

    /**
     * Creates new form Trabajador
     */
    public Trabajador() {
        initComponents();
        XMl_2();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Trabajador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Trabajador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Trabajador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Trabajador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Trabajador().setVisible(true);
            }
        });
    }
    
    public void XMl_2(){
        Document document=null;
        DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            
            document =implementation.createDocument(null, "bdoCuotaMonetaria",null);
            
            Element xsi54 = document.createElement("registro54");
            Element xsi77 = document.createElement("registro77");
            
            //xsi.setAttribute(":type", "bdoCuotaMonetaria:Registro54");
            Attr att= document.createAttribute("xsi");
            att.setValue("bdoCuotaMonetaria:Registro54");
            xsi54.setAttributeNode(att);
            
            //xsi.setAttribute(":type", "bdoCuotaMonetaria:Registro54");
            Attr att2= document.createAttribute("xsi");
            att2.setValue("bdoCuotaMonetaria:Registro77");
            xsi77.setAttributeNode(att2);
            
            
            
            
            Element C780= document.createElement("C780");
            Text valorC780 = document.createTextNode("T"); 

            Element C47= document.createElement("C47");
            Text valorC47 = document.createTextNode("CC"); 
            
            
            //Asignamos la versión de nuestro XML
            document.setXmlVersion("1.0"); 
            //Añadimos la casa al documento
            document.getDocumentElement().appendChild(xsi54); 
            //Añadimos el elemento hijo a la raíz
            
            xsi54.appendChild(xsi77);
            xsi77.appendChild(C780);
            
            C780.appendChild(valorC780); 
            
            xsi77.appendChild(C47);
            C47.appendChild(valorC47); 
            
            
            guardaConFormato(document, "Z:\\S01 Software Auditoria\\JReport\\"+"prueba.xml");
        } catch (Exception e) {
             System.err.println("Error: "+e);
        }
    }
    

    
    public void XMl(){
        Document document=null;
        DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            document = implementation.createDocument(null, "xml", null);
            //Creación de elementos
            //creamos el elemento principal casa
            Element casa = document.createElement("Casa"); 
            //creamos un nuevo elemento. Casa contiene habitaciones
            Element habitacion= document.createElement("Habitacion");
            //creamos un nuevo elemento. Habitación tiene color
            Element color = document.createElement("Color"); 
            //Ingresamos la info. El color de esta habitación es azul
            Text valorColor = document.createTextNode("Azul"); 

            //Asignamos la versión de nuestro XML
            document.setXmlVersion("1.0"); 
            //Añadimos la casa al documento
            document.getDocumentElement().appendChild(casa); 
            //Añadimos el elemento hijo a la raíz
            casa.appendChild(habitacion); 
            //Añadimos elemento
            habitacion.appendChild(color); 
            //Añadimos valor
            color.appendChild(valorColor); 
            guardaConFormato(document, "Z:\\S01 Software Auditoria\\JReport\\"+"prueba.xml");
        } catch (Exception e) {
             System.err.println("Error: "+e);
        }
    }
    
    public void guardaConFormato(Document document, String URI){
    try {
        TransformerFactory transFact = TransformerFactory.newInstance();

        //Formateamos el fichero. Añadimos sangrado y la cabecera de XML
        transFact.setAttribute("indent-number", new Integer(3));
        Transformer trans = transFact.newTransformer();
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        //Hacemos la transformación
        StringWriter sw = new StringWriter();
        StreamResult sr = new StreamResult(sw);
        DOMSource domSource = new DOMSource(document);
        trans.transform(domSource, sr);

        //Mostrar información a guardar por consola (opcional)
        //Result console= new StreamResult(System.out);
        //trans.transform(domSource, console);
        try {
            //Creamos fichero para escribir en modo texto
            PrintWriter writer = new PrintWriter(new FileWriter(URI));

            //Escribimos todo el árbol en el fichero
            writer.println(sw.toString());

            //Cerramos el fichero
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } catch(Exception ex) {
        ex.printStackTrace();
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
