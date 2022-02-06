/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FondosLey;

import Principal.JLeeFichero;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Garyn Carrillo
 */
public class JJasper  extends Thread{
    private Connection Cn;
    private String FechaInicial; String FechaFinal; String PPeriodo;
    private int Tipo;
    private JLeeFichero JRuta;
    public void run(){
            JRuta = new JLeeFichero();
            if(Tipo==1){
                jasper();
            }
            if(Tipo==2){
                jasper_Distribucion();
            }
            if(Tipo==3){
                jasper_Bancos();
            }
            if(Tipo==4){;
                jasper_Ingresos_Servicios_Sociales();
            }
        
    }
    public void setTipo(int Tipo){
        this.Tipo =Tipo;
    }
    public void setPPeriodo(String Periodo){
        this.PPeriodo = Periodo;
    }
    public void setFechaInicial(String FechaIni){
        this.FechaInicial =FechaIni;
    }
    public void setFechaFinal(String FechaFin){
        this.FechaFinal=FechaFin;
    }
    public void setConeccion(Connection Con){
        this.Cn =Con;
    }
    private  void jasper(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("PPeriodo", PPeriodo);
                params.put("FechaInicial", FechaInicial);
                params.put("FechaFinal", FechaFinal);
                String file = "Z:\\S01 Software Auditoria\\JAS400\\report1.jasper";
                File input = new File(file);
                JasperPrint jasperPrint = JasperFillManager.fillReport(input.getAbsolutePath(), params,Cn);
                String NombreArchivo ="Fondo_Distribucion_Banco_"+FechaInicial+"_"+FechaFinal;
                String Ruta = JRuta.getRutaJReport()+NombreArchivo+".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, Ruta);
                JOptionPane.showMessageDialog(null,"Generado "+Ruta);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"jasper() "+e.getMessage()); 
        }
        
    }
    
    private  void jasper_4_SLMV(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("PPeriodo", PPeriodo);
                params.put("FechaInicial", FechaInicial);
                params.put("FechaFinal", FechaFinal);
                String file = "Cruce_Salario_4.jasper";
                File input = new File(file);
                JasperPrint jasperPrint = JasperFillManager.fillReport(input.getAbsolutePath(), params,Cn);
                String NombreArchivo ="Fondo_Distribucion_Banco_"+FechaInicial+"_"+FechaFinal;
                String Ruta = JRuta.getRutaJReport()+NombreArchivo+".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, Ruta);
                JOptionPane.showMessageDialog(null,"Generado "+Ruta);
                
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"jasper() "+e.getMessage()); 
             
        }
        
    }
    
    
    
     private  void jasper_Distribucion(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("PPeriodo", PPeriodo);
                params.put("FechaInicial", FechaInicial);
                params.put("FechaFinal", FechaFinal);
                String file = "RDistribucion.jasper";
                File input = new File(file);
                JasperPrint jasperPrint = JasperFillManager.fillReport(input.getAbsolutePath(), params,Cn);
                String NombreArchivo ="Fondo_Distribucion_"+FechaInicial+"_"+FechaFinal;
                String Ruta = JRuta.getRutaJReport()+NombreArchivo+".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, Ruta);
                JOptionPane.showMessageDialog(null,"Generado "+Ruta);
                
                
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"jasper() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    private  void jasper_Bancos(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("PPeriodo", PPeriodo);
                String file = "ConciliacionBanco.jasper";
                File input = new File(file);
                JasperPrint jasperPrint = JasperFillManager.fillReport(input.getAbsolutePath(), params,Cn);
                String NombreArchivo ="Fondo_Distribucion_Conciliacion_Banco"+PPeriodo;
                String Ruta = JRuta.getRutaJReport()+NombreArchivo+".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, Ruta);
                JOptionPane.showMessageDialog(null,"Generado "+Ruta);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"jasper() "+e.getMessage()); 
        }
        
    }
    
    private  void jasper_Ingresos_Servicios_Sociales(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("FechaInicial", this.FechaInicial);
                params.put("FechaFinal", this.FechaFinal);
                String file = "RIngresosServiciosSocial.jasper";
                File input = new File(file);
                JasperPrint jasperPrint = JasperFillManager.fillReport(input.getAbsolutePath(), params,Cn);
                String NombreArchivo ="Ingresos_Servicios_Sociales "+this.FechaInicial+"_"+this.FechaFinal;
                String Ruta = JRuta.getRutaJReport()+NombreArchivo+".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, Ruta);
                JOptionPane.showMessageDialog(null,"Generado "+Ruta);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"jasper() "+e.getMessage()); 
        }
        
    }
    
}
