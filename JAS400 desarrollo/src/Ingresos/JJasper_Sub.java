/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ingresos;

import Subsidio.*;
import FondosLey.*;
import Principal.JLeeFichero;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Garyn Carrillo
 */
public class JJasper_Sub  extends Thread{
    private Connection Cn;
    private String FechaInicial; 
    private String Usuario;
    private String Usuario_JAS;
    private int Tipo;
    private JLeeFichero JRuta;
    public void run(){
            JRuta = new JLeeFichero();
            if(Tipo==1){
                jasper();
            }
      
    }
    public void setTipo(int Tipo){
        this.Tipo = Tipo;
    }
    public void setFechaInicial(String FechaIni){
        this.FechaInicial =FechaIni;
    }
    public void setUsuario(String Usuario){
        this.Usuario= Usuario;
    }
        public void setUsuario_JAS(String Usuario_JAS){
        this.Usuario_JAS= Usuario_JAS;
    }
    public void setConeccion(Connection Con){
        this.Cn =Con;
    }
    private  void jasper(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("FECHA", FechaInicial);
                params.put("USUARIO", Usuario);
                params.put("JUSUARIO", Usuario_JAS);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Planilla_TransaccionxCajeros.jrxml";
 
                 String NombreArchivo ="Planilla_POS_"+FechaInicial+"_"+this.Usuario+"_"+this.Usuario_JAS+"_";
                 String Ruta = JRuta.getRutaJReport()+NombreArchivo+".xlsx";
     
                  // Cargamos la plantilla
                 JasperDesign objJasperDesign = JRXmlLoader.load(file);
                 // Compilamos la plantilla
                 JasperReport objJasperReport = JasperCompileManager.compileReport(objJasperDesign);

                 // Poblamos la plantilla con los datos de la BD y parametros
                 JasperPrint objJasperPrint =  JasperFillManager.fillReport(objJasperReport, params, Cn);
                    
                 // Instanciamos la clase exportadora
                 //JExcelApiExporter xlsExporter = new JExcelApiExporter();
                 System.out.println(""+Ruta);
                
                 JRXlsxExporter exporterXLS = new JRXlsxExporter();

                 exporterXLS.setParameter(JRExporterParameter.JASPER_PRINT, objJasperPrint);
                 exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.TRUE);
                 exporterXLS.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, Ruta);
                 
                 Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler"+Ruta);
                 exporterXLS.exportReport();
                JOptionPane.showMessageDialog(null,"Generado "+Ruta);
                 
                
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"jasper() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    
    
    
}
