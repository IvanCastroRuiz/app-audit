package ReporteIngresos;

import BD_As400.JConection;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.io.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 * Version 1.0: Origina
 * Version 1.1: Se incluye busqueda de aportes realizados por las 
 *              empresas mediantes cheques.
 * Responsble del Cambio : GARYN JAIRO CARRILLO CABALLERO  AUDITOR DE SISTEMA
 */
public class JReporteVentasRetenidas {
    private Document documento;
    
    private Connection Connection_BD;
    private JConection JBase_Datos;
    private Calendar fecha;
    private String Fecha, Fecha2;
    private String Usuario;
    private String Turno, User;
    private SimpleDateFormat Formato = new SimpleDateFormat ("dd/MMMM/yy HH:mm:ss");
    private Date XFecha;
    
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private double AportesCheque;              
    
    public JReporteVentasRetenidas(JConection JBase_Datos2 , Connection Cn3, String Fechax,String FechaF, String Usuariox){
        
        JFormato= new DecimalFormat(NumeroFormato);
        XFecha = new Date(5);
        this.JBase_Datos = JBase_Datos2;
        this.Usuario = Usuariox;
        documento = new Document(PageSize.B2.rotate(),10,3,20,20);
        //(Margen_Izquierdo, Margen_Derecho, Margen_Superior, Margen_Inferior)
        this.Fecha = Fechax;
        this.Fecha2 = FechaF;
        this.Connection_BD = Cn3;
        this.CreatePdf();
        
    }
        
    public void CreatePdf(){
        
       try{
           
            String NombreArchivo = "Ventas_Retenidas"+"_"+this.Fecha;
            
            String Ruta1 = "Z:\\S01 Software Auditoria\\JReport\\"+NombreArchivo+".pdf";
            JOptionPane.showMessageDialog(null,Ruta1);
            FileOutputStream ficheroPdf = new FileOutputStream(Ruta1);
            PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
            documento.open();
            documento.addTitle("COMFAMILIAR DEL ATLÁNTICO");
            
            this.Insert_Logo();
            this.EncabezadoDocumento();
            documento.close();
            
       }catch(Exception e){
           JOptionPane.showMessageDialog(null,"JReport: "+e.getMessage());
       }
    }
    public void Insert_Logo(){
      try{  
        Image foto = Image.getInstance("Z:\\S01 Software Auditoria\\JReport\\Encabezado.jpg");
        foto.scaleToFit(60, 80);
        foto.setAlignment(Chunk.ALIGN_LEFT);
        documento.add(foto);
      }catch(Exception e ){
          JOptionPane.showMessageDialog(null, "JReport: "+e.getMessage());
      }
    }
    public void EncabezadoDocumento(){
        try{ 
            
            Paragraph Parrafo = new Paragraph();
            
            Parrafo.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            Parrafo.clear();
         
            Parrafo.add("           Usuario : "+ this.Usuario);  
            documento.add(Parrafo);
            Parrafo.clear();
            Parrafo.add("           Fecha  de Ingreso  : "+ this.Fecha);
            documento.add(Parrafo);
            Parrafo.clear();
            Parrafo.setAlignment(Element.ALIGN_CENTER);
            Parrafo.add("AUDITORIA DE SISTEMA"); 
            documento.add(Parrafo);
            Parrafo.clear();
            Parrafo.add("COMFAMILIAR ATLANTICO"); 
            documento.add(Parrafo);
            Parrafo.clear();
            Parrafo.add("REPORTE DE VENTAS RETENIDA"); 
            documento.add(Parrafo);
            
            Parrafo.clear();
            this.Salto_Linea();
            this.getBusqueda();
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "JReport: "+e.getMessage());
        }
        
    }
    public void Salto_Linea(){
        try{
            documento.add(new Paragraph("         ",FontFactory.getFont("tahoma",   
				12,Font.BOLD,BaseColor.BLACK))); 
        }catch(Exception e ){
            JOptionPane.showMessageDialog(null, "JReport: "+e.getMessage());            
        }
    }

    
    public void getBusqueda(){
        try{
            
            String StrSql = "SELECT Fecha, SALANTVENT,VVLRRETENI,VVLRABONAD,SALACTUAL  "+
                            " FROM selinlib.jingreso"+
                            " WHERE fecha >= "+this.Fecha +" and fecha <="+this.Fecha2;
            ResultSet  Result = JBase_Datos.SQL_QRY(Connection_BD,StrSql);
            float  AnchoCelda []  = new float[5]; 
            AnchoCelda [0] = (float) 10.0;
            AnchoCelda [1] = (float) 10.0;
            AnchoCelda [2] = (float) 10.0;
            AnchoCelda [3] = (float) 10.0;
            AnchoCelda [4] = (float) 10.0;
            
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidths(AnchoCelda);    
            
            PdfPCell Celda ;    
            Paragraph Parrafo = new Paragraph();
            Parrafo.setAlignment(Element.ALIGN_CENTER);
            Parrafo.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo2 = new Paragraph();
            Parrafo2.setAlignment(Element.ALIGN_CENTER);
            Parrafo2.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo3 = new Paragraph();
            Parrafo3.setAlignment(Element.ALIGN_CENTER);
            Parrafo3.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            
            Paragraph Parrafo4 = new Paragraph();
            Parrafo4.setAlignment(Element.ALIGN_CENTER);
            Parrafo4.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            
            Paragraph Parrafo5 = new Paragraph();
            Parrafo5.setAlignment(Element.ALIGN_CENTER);
            Parrafo5.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            
            Parrafo.add("Saldo Anterior ");
            Celda = new PdfPCell ( Parrafo);
            tabla.addCell(Celda);
            
            Parrafo2.clear();
            Parrafo2.add("Valor Venta Retenida");
            PdfPCell Celda2 = new PdfPCell ( Parrafo2);
            tabla.addCell(Celda2);

            Parrafo3.clear();
            Parrafo3.add("Valor Abonado");
            PdfPCell Celda3 = new PdfPCell ( Parrafo3);
            tabla.addCell(Celda3);           
            
            Parrafo4.clear();
            Parrafo4.add("Valor Saldo Actual");
            PdfPCell Celda4 = new PdfPCell ( Parrafo4);
            tabla.addCell(Celda4);
            
            Parrafo5.clear();
            Parrafo5.add("Fecha");
            PdfPCell Celda5 = new PdfPCell ( Parrafo5);
            tabla.addCell(Celda5);
            
            
            while(Result.next()){
                
                
                Paragraph Parrafo11 = new Paragraph();
                Parrafo11.setAlignment(Element.ALIGN_CENTER);
                Parrafo11.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
            
                Paragraph Parrafo12 = new Paragraph();
                Parrafo12.setAlignment(Element.ALIGN_CENTER);
                Parrafo12.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
            
                Paragraph Parrafo13 = new Paragraph();
                Parrafo13.setAlignment(Element.ALIGN_CENTER);
                Parrafo13.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
            
            
                Paragraph Parrafo14 = new Paragraph();
                Parrafo14.setAlignment(Element.ALIGN_CENTER);
                Parrafo14.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));

                Paragraph Parrafo15 = new Paragraph();
                Parrafo15.setAlignment(Element.ALIGN_CENTER);
                Parrafo15.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
                
                
                
                Parrafo11.add(JFormato.format(Result.getDouble("SALANTVENT")));
                PdfPCell Celda11 = new PdfPCell ( Parrafo11);
                tabla.addCell(Celda11);
            
                Parrafo12.clear();
                Parrafo12.add(JFormato.format(Result.getDouble("VVLRRETENI")));
                PdfPCell Celda12 = new PdfPCell ( Parrafo12);
                tabla.addCell(Celda12);

                Parrafo13.clear();
                Parrafo13.add(JFormato.format(Result.getDouble("VVLRABONAD")));
                PdfPCell Celda13 = new PdfPCell ( Parrafo13);
                tabla.addCell(Celda13);           
                
                Parrafo14.clear();
                Parrafo14.add(JFormato.format(Result.getDouble("SALACTUAL")));
                PdfPCell Celda14 = new PdfPCell ( Parrafo14);
                tabla.addCell(Celda14);
                
                Parrafo15.clear();
                Parrafo15.add(Result.getString("FECHA"));
                PdfPCell Celda15 = new PdfPCell ( Parrafo15);
                tabla.addCell(Celda15);
                
            }   
            documento.add(tabla);
            Result.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    

    
}
