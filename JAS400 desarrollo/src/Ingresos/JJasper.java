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
import java.util.List;
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
public class JJasper  extends Thread{
    private Connection Cn;
    
    private String CCosto; 
    private int FechaInicial;
    private int FechaFinal;
    private int Medio;
    private int Tipo;
    private String Cedula;
    private String Comprobante;
    private JLeeFichero JRuta;
    
    private int PerInicial, PerFinal;
    private int Ano;
    private String PTipo;
    private String Cuenta;
    
    public void setPerInicial(String Periodo){
        this.PerInicial =Integer.parseInt(Periodo);
    }
    public void setPerFinal(String Periodo){
        this.PerFinal =Integer.parseInt(Periodo);
    }
    public void setAno(String Ano){
        this.Ano =Integer.parseInt(Ano);
    }
    public void setTipoNomina(String Tipo){
        this.PTipo=Tipo;
    }
    public void setCuenta(String Cuenta){
        this.Cuenta=Cuenta;
    }
        
    public void run(){
            JRuta = new JLeeFichero();
            if(Tipo==1){
                jasper_Recibo_Caja();
                jasper_Recibo_Caja_Detalle();
            }
             if(Tipo==2){
                jasper_Recibo_Caja_Todos();
                jasper_Recibo_Caja_Detalle();
            }
            if(Tipo==3){
               jasper_Boleta_recreacion();
               jasper_Boleta_recreacion_POS();
            }
            if(Tipo==4){
               jasper_Comprobante();
            }
            if(Tipo==5){
              jasper_ZPLANILLAS();
            }
            
            if(Tipo==6){
              jasper_ZReversiones();
            }
            if(Tipo==7){
                    jasper_ZMicrocreditos();
            }
            if(Tipo==8){
                    jasper_Rechazos();
            }
            if(Tipo==9){
                jasper_ZNovedad_Tarjeta_MultiServicios_X_Fecha();
            }
            if(Tipo==10){
               jasper_Novedad_Tarjetas_Multiservicios_Pos_Neg_Cedula();
            }
            if(Tipo==11){
                jasper_Saldos_CAC();
            }
            if(Tipo==12){
                jasper_Nomina();
            }
            if(Tipo==13){
                jasper_Informe_Contabilidad();
            }
            if(Tipo==14){
                jasper_ZPLANILLAS_Cedula();
            }
            if(Tipo==15){
                jasper_Contabilidad_Cuenta_Fecha();
            }
            if(Tipo==16){
                jasper_Informe_Detalle_Saldo_X_Centro();
            }
            if(Tipo==17){
                Pasadias();
                Pasadias_POS();
            }
            
    }
    public void setCedula(String Cedula){
        this.Cedula =Cedula;
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
    public void setCentro_Costo(String PCodigo){
        CCosto=PCodigo;
    }

    public void setMedio(int PMedio){
        Medio =PMedio;
    }
    public void setComprobante(String PComprobante){
        Comprobante=PComprobante;
    }
    
    public void setConeccion(Connection Con){
        this.Cn =Con;
    }
        private  void jasper_Informe_Detalle_Saldo_X_Centro(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("FechaInicia", this.FechaInicial);
                params.put("FechaFinal", this.FechaFinal);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZSaldo_Detallado.jrxml";
 
                 String NombreArchivo ="ZSaldo_Detallado_x_Centro"+"_"+this.FechaInicial+"_"+this.FechaFinal;
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
    
      
    private  void jasper_Informe_Saldo_Centro_Detallado(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("FechaInicial", this.FechaInicial);
                params.put("FechaFinal", this.FechaFinal);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\zsaldo_CAC.jrxml";
 
                 String NombreArchivo ="zsaldo_CAC_Contabilidad"+"_"+this.FechaInicial+"_"+this.FechaFinal;
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
    
    
    
    private  void jasper_ZPLANILLAS_Cedula(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("CEDULA", Cedula);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Zplanilla_Cedula.jrxml";
 
                 String NombreArchivo ="Zplanilla_Cedula"+Cedula;
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
    
    
    
     private  void jasper_Informe_Contabilidad(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("FechaInicial", this.FechaInicial);
                params.put("FechaFinal", this.FechaFinal);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\zsaldo_CAC.jrxml";
 
                 String NombreArchivo ="zsaldo_CAC_Contabilidad"+"_"+this.FechaInicial+"_"+this.FechaFinal;
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
    
    
    
    private  void jasper_Nomina(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("PeriodoInicial", this.PerInicial);
                params.put("PeriodoFinal", this.PerFinal);
                params.put("TipoNomina", this.PTipo);
                params.put("Ano",this.Ano );
                
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\znomina.jrxml";
 
                 String NombreArchivo ="znomina_"+this.PerInicial+"_"+this.PerFinal+"_"+PTipo+"_"+this.Ano;
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
    
    
    
    
    
    
    private  void jasper_Saldos_CAC(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\zsaldo_CAC.jrxml";
 
                 String NombreArchivo ="zsaldo_CAC _"+FechaInicial+"_"+FechaFinal;
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
    
    
    private  void jasper_Novedad_Tarjetas_Multiservicios_Pos_Neg_Cedula(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("Cedula", Cedula);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZNovedad_Pos_Neg_Cedula.jrxml";
 
                 String NombreArchivo ="ZNovedad_Pos_Neg_Cedulas_"+Cedula;
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
    
    
    
    private  void jasper_ZNovedad_Tarjeta_MultiServicios_X_Fecha(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZNovedad_Pos_Neg_Fecha.jrxml";
 
                 String NombreArchivo ="ZNovedad_Pos_Neg_Fecha_"+FechaInicial+"_"+FechaFinal;
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
     private  void jasper_Rechazos(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("Cedula", Cedula);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZReversion_2.jrxml";
 
                 String NombreArchivo ="ZRechazos_"+Cedula;
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
    
    
    
    
    
    
    
    
     private  void jasper_ZMicrocreditos(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZMicrocredito.jrxml";
 
                 String NombreArchivo ="ZMicrocredito_"+FechaInicial+"_"+FechaFinal;
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
    
    
     private  void jasper_ZReversiones(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                params.put("cedula", Cedula);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\zreversiones.jrxml";
 
                 String NombreArchivo ="zreversiones_"+FechaInicial+"_"+FechaFinal+"_"+Cedula;
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
    
    
    
    
    private  void jasper_ZPLANILLAS(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                params.put("CEDULA", Cedula);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\Zplanilla.jrxml";
 
                 String NombreArchivo ="ZPLANILLA_"+FechaInicial+"_"+FechaFinal+"_"+Cedula;
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
             JOptionPane.showMessageDialog(null," jasper_ZPLANILLAS()) "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    }
    
    
    
    
    
    private  void jasper_Recibo_Caja(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("CCosto", CCosto);
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                params.put("Medio", Medio);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZCajas.jrxml";
 
                 String NombreArchivo ="ZRecibo_Caja_"+FechaInicial+"_"+FechaFinal+"_"+CCosto;
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
    
    
        private  void jasper_Recibo_Caja_Detalle(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );

                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZCaja_General_Cruce_Definitivo.jrxml";
 
                 String NombreArchivo ="ZCaja_General_Cruce_Definitivo_"+FechaInicial+"_"+FechaFinal+"_"+CCosto;
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
    
    
    
    
    
     private  void jasper_Recibo_Caja_Todos(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("CCosto", CCosto);
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                params.put("Medio", Medio);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZCajas_Todo.jrxml";
 
                 String NombreArchivo ="ZRecibo_Caja_All_"+FechaInicial+"_"+FechaFinal;
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
     
     private  void Pasadias_POS(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZPasadias_POS.jrxml";
 
                 String NombreArchivo ="ZPasadias_POS_"+FechaInicial+"_"+FechaFinal;
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
             JOptionPane.showMessageDialog(null," Pasadias() "+e.getMessage()); 
             System.out.println(""+e.getMessage());
        }
        
    } 
     
    private  void Pasadias(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                
                //params.put("FechaFinal", String.valueOf(FechaFinal));
                params.put("FechaInicial",""+FechaInicial );
                params.put("FechaFinal",""+FechaFinal );
                
                //javax.swing.JOptionPane.showMessageDialog(null, "     "+FechaFinal+" Fecha Inicial "+FechaInicial);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZPasadoas_Sistema_Boletas.jrxml";
 
                 String NombreArchivo ="ZPasadias_Sistema_"+FechaInicial+"_"+FechaFinal;
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
      
     private  void jasper_Boleta_recreacion(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("CCosto", CCosto);
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                params.put("Medio", Medio);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZBoletas.jrxml";
 
                 String NombreArchivo ="ZBoletas_Recreacion_"+FechaInicial+"_"+FechaFinal;
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
     
    private  void jasper_Boleta_recreacion_POS(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("CCosto", CCosto);
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                params.put("Medio", Medio);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZBoleta_POS.jrxml";
 
                 String NombreArchivo ="ZBoletas_Recreacion_POS_"+FechaInicial+"_"+FechaFinal;
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
     
    
    private  void jasper_Comprobante(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("CCosto", CCosto);
                params.put("FechaFinal", FechaFinal);
                params.put("FechaInicial",FechaInicial );
                params.put("Comprobante", Comprobante);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZComprobante.jrxml";
 
                 String NombreArchivo ="ZComprobante_"+FechaInicial+"_"+FechaFinal;
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
    
    private  void jasper_Contabilidad_Cuenta_Fecha(){
        try{ 
                JRuta.Leer();
                HashMap params = new HashMap();

                params.put("FechaInicial",FechaInicial );
                params.put("FechaFinal", FechaFinal);
                params.put("Cuenta", Cuenta);
                
                String file = "Z:\\S01 Software Auditoria\\JAS400\\ZComprobante_X_Centro_X_Cuenta.jrxml";
 
                 String NombreArchivo ="ZComprobante_X_Cuenta_"+FechaInicial+"_"+FechaFinal+"_"+Cuenta;
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
    
}
