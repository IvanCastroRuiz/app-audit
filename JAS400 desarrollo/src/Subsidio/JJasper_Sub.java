/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Subsidio;

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
    private int FechaInicial; 
    private int FechaFinal;
    private int SLVM;
    private int Tipo;
    private String Codigo_empresa;
    private String Periodo;
    private JLeeFichero JRuta;
    public void run(){
            JRuta = new JLeeFichero();
            if(Tipo==1){
                jasper();
            }
            if(Tipo==2){
               jasper_SLMV4();
            }
            if(Tipo==3){
                jasper_Liquidado_Antes_Nacer();
            }
            if(Tipo==4){
                jasper_Hijo_Trabajador_Afiliado();
            }      
            if(Tipo==5){
                jasper_Padre_Trabajador_Afiliado();
            }
            if(Tipo==6){
                jasper_Madres_Menor60_CuotaMonteria();
            }
            if(Tipo==7){
                    jasper_Hijos_Padres_Padrastos();
            }
            if(Tipo==8){
                jasper_Hijos_Mayores_18();
            }
            if(Tipo==9){
                jasper_Hijo_Dobles_Similitud_MismaFechaNacimiento();
            }
            if(Tipo==10){
                jasper_Padres_Dobles_Similitud_MismaFechaNacimiento();
            }
            if(Tipo==11){
                jasper_Similitud_Hijos_Liquidacion();
            }
            if(Tipo==12){
               jasper_Similitud_Padres_Liquidacion();
            }
            if(Tipo==13){
                jasper_Empresa_Planilla_Unica();
                jasper_Planilla_Unica_No_Empresa();
            }
    }
    public void setSLMV(int PSLMV){
        this.SLVM = PSLMV;
    }
    public void setTipo(int Tipo){
        this.Tipo =Tipo;
    }
    public void setFechaInicial(String FechaIni){
        this.FechaInicial =Integer.parseInt(FechaIni);
    }
    public void setFechaFinal(String FechaFin){
        this.FechaFinal=Integer.parseInt(FechaFin);
    }
    public void setConeccion(Connection Con){
        this.Cn =Con;
    }
    
    public void setPeriodo(String PPeriodo){
        this.Periodo = PPeriodo;
    }
    
    public void setCodigo_Empresa(String PCodigoEmpresa){
        this.Codigo_empresa=PCodigoEmpresa;
    }
    
    private  void jasper(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("FechaInicial", FechaInicial);
                params.put("FechaFinal", FechaFinal);
                params.put("SLMV", SLVM);
                String file = "Cruce_Salarios.jrxml";
 
                 String NombreArchivo ="Cruce_Salario"+FechaInicial+"_"+FechaFinal;
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
    
    private  void jasper_SLMV4(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("FechaInicial", FechaInicial);
                params.put("FechaFinal", FechaFinal);
                params.put("SLMV", SLVM);
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Cruce_Salario4.jrxml";
 
                 String NombreArchivo ="Cruce_4_Salario"+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    private  void jasper_Liquidado_Antes_Nacer(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("FechaInicial", FechaInicial);
                params.put("FechaFinal", FechaFinal);
                params.put("SLMV", SLVM);
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Liquidacion_AntesNacer.jrxml";
 
                 String NombreArchivo ="Liquidado_Antes_Nacer"+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    
      private  void jasper_Hijo_Trabajador_Afiliado(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                String file = "Z:\\S01 Software Auditoria\\JAS400\\Hijo_Trabajador_Afiliado.jrxml";
 
                 String NombreArchivo ="Hijo_Trabajador_Afiliado "+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    
    private  void jasper_Padre_Trabajador_Afiliado(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                String file = "Z:\\S01 Software Auditoria\\JAS400\\Padre_Trabajador.jrxml";
 
                 String NombreArchivo ="Padres_Trabajador_Afiliado "+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    private  void jasper_Madres_Menor60_CuotaMonteria(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("FechaInicial", FechaInicial);
                params.put("FechaFinal", FechaFinal);
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Padre_Menor60.jrxml";
 
                 String NombreArchivo ="Padre_Menor60"+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
     private  void jasper_Hijos_Padres_Padrastos(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Hijos_Padrastp_Padre.jrxml";
 
                 String NombreArchivo ="Hijos_Padrasto_Padre"+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    
    private  void jasper_Hijos_Mayores_18(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("FechaInicial", FechaInicial);
                params.put("FechaFinal", FechaFinal);
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Hijos_mayores18.jrxml";
 
                 String NombreArchivo ="Hijos_mayores18"+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    
     private  void jasper_Hijo_Dobles_Similitud_MismaFechaNacimiento(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                String file = "Z:\\S01 Software Auditoria\\JAS400\\Similitud_Hijos.jrxml";
 
                 String NombreArchivo ="Similitud_Hijos "+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
     private  void jasper_Padres_Dobles_Similitud_MismaFechaNacimiento(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                String file = "Z:\\S01 Software Auditoria\\JAS400\\Similitud_Padres.jrxml";
 
                 String NombreArchivo ="Similitud_Padres "+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    
    
    private  void jasper_Similitud_Hijos_Liquidacion(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("FechaInicial", FechaInicial);
                params.put("FechaFinal", FechaFinal);
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Similitud_Hijos_Liquidacion.jrxml";
 
                 String NombreArchivo ="Similitud_Hijos_Liquidacion"+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    
    private  void jasper_Similitud_Padres_Liquidacion(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("FechaInicial", FechaInicial);
                params.put("FechaFinal", FechaFinal);
                String file = "Z:\\S01 Software Auditoria\\JAS400\\similitud_padres_liquidacion.jrxml";
 
                 String NombreArchivo ="similitud_padres_liquidacion "+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    
    
    private  void jasper_Empresa_Planilla_Unica(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("PPeriodo", this.Periodo);
                params.put("PEmpresa", this.Codigo_empresa);
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Maestro_Planilla_coincidencia.jrxml";
 
                 String NombreArchivo ="Maestro_Planilla_coincidencia "+this.Periodo+"_"+this.Codigo_empresa;
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
                 
                 //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler"+Ruta);
                 exporterXLS.exportReport();
                 
                 JOptionPane.showMessageDialog(null,"Generado "+Ruta);
                 
                
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    
    
      private  void jasper_Planilla_Unica_No_Empresa(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("PPeriodo", this.Periodo);
                params.put("PCodigo", this.Codigo_empresa);
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Planilla_No_Maestro.jrxml";
 
                 String NombreArchivo ="Planilla_No_Maestro "+this.Periodo+"_"+this.Codigo_empresa;
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
                 
                 //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler"+Ruta);
                 exporterXLS.exportReport();
                JOptionPane.showMessageDialog(null,"Generado "+Ruta);
                 
                
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null," jasper_SLMV4() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
}
