package ReporteIngresos;


import Principal.JLeeFichero;
import BD_As400.JConection;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.*;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.DecimalFormat;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Date.*;
/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */


public class JRepor_Detalles_Ventas_Retenidas {
    
    private Document documento;
    private String Fecha, FechaFinal;
    private Connection Cn;
    private JConection JBase_Datos;
    
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    
    private PdfPTable tabla ;
    private JLeeFichero JRuta;
    private double VentaRetenida;
    private double SaldoAnterior;
    private double Acomula_Subsidio_Pagados;
    private double Acomula_pagado_efectivo;
    public JRepor_Detalles_Ventas_Retenidas(JConection JBase_Datos2 , Connection Cn3, String Fecha, String FechaFinal){
       
        this.Fecha = Fecha;
        this.FechaFinal = FechaFinal;
        this.JBase_Datos = JBase_Datos2;
        this.Cn = Cn3;
        JFormato= new DecimalFormat(NumeroFormato);
        JRuta = new JLeeFichero();
        documento = new Document();
        documento = new Document(PageSize.B2.rotate(),10,3,20,20);
        tabla = new PdfPTable(7);
        this.CreatePdf();
      
    }
    
    public void CreatePdf(){
       try{
            String NombreArchivo = "Detalle_Ventas_Retenidas_"+this.Fecha+"_"+this.FechaFinal;
            JRuta.Leer();
            String Ruta1 = JRuta.getRutaJReport()+NombreArchivo+".pdf";
            JOptionPane.showMessageDialog(null,Ruta1);
            FileOutputStream ficheroPdf = new FileOutputStream(Ruta1);
            PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
            documento.open();
            documento.addTitle("COMFAMILIAR DEL ATLÁNTICO");
            
            this.Insert_Logo();
            this.EncabezadoDocumento();
            this.getInformacion();
            documento.close();
            
       }catch(Exception e){
           JOptionPane.showMessageDialog(null,"JReport:CratePdf() "+e.getMessage());
       }
    }
    public void Insert_Logo(){
      try{  
        Image foto = Image.getInstance(JRuta.getRutaEncabezado());
        foto.scaleToFit(200, 80);
        foto.setAlignment(Chunk.ALIGN_LEFT);
        documento.add(foto);
      }catch(Exception e ){
          JOptionPane.showMessageDialog(null, "JReport: "+e.getMessage());
      }
                
    }
    
    public void getCabecera_Tabla(){
        
            Paragraph Parrafo1 = new Paragraph();
            Parrafo1.setAlignment(Element.ALIGN_CENTER);
            Parrafo1.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

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
            
            Paragraph Parrafo6 = new Paragraph();
            Parrafo6.setAlignment(Element.ALIGN_CENTER);
            Parrafo6.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo7 = new Paragraph();
            Parrafo7.setAlignment(Element.ALIGN_CENTER);
            Parrafo7.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            
            Parrafo1.add("Fecha");
            PdfPCell Celda1 = new PdfPCell ( Parrafo1);
            tabla.addCell(Celda1);
            
            Parrafo2.add("Codigo");
            PdfPCell Celda2 = new PdfPCell ( Parrafo2);
            tabla.addCell(Celda2);

            Parrafo3.add("Descripcion");
            PdfPCell Celda3 = new PdfPCell ( Parrafo3);
            tabla.addCell(Celda3);
            
            Parrafo4.add("Sub. Pagado");
            PdfPCell Celda4 = new PdfPCell ( Parrafo4);
            tabla.addCell(Celda4);

            Parrafo5.add("Val. Venta Retenida");
            PdfPCell Celda5 = new PdfPCell ( Parrafo5);
            tabla.addCell(Celda5);
            
            Parrafo6.add("Val. Saldo Anterior");
            PdfPCell Celda6 = new PdfPCell ( Parrafo6);
            tabla.addCell(Celda6);
            
            Parrafo7.add("Saldo Actual");
            PdfPCell Celda7 = new PdfPCell ( Parrafo7);
            tabla.addCell(Celda7);
            
    }
    public void get_Ingresos(int Fecha){
        try {
            this.VentaRetenida = 0;
            this.SaldoAnterior = 0;
            String Str_Sql = "select VVLRRETENI , SALANTVENT from selinlib.jingreso where fecha="+Fecha;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                this.VentaRetenida = rs.getDouble("VVLRRETENI");
                this.SaldoAnterior = rs.getDouble("SALANTVENT");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "JReport:get_Ingresos() "+e.getMessage());
        }
    }
    public void Imprimir_Subtotales( int Fecha , double valor_pagados){
        
                     Paragraph Parrafo5 = new Paragraph();
                     Parrafo5.setAlignment(Element.ALIGN_CENTER);
                     Parrafo5.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
                     Paragraph Parrafo6 = new Paragraph();
                     Parrafo6.setAlignment(Element.ALIGN_CENTER);
                     Parrafo6.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
                     Paragraph Parrafo7 = new Paragraph();
                     Parrafo7.setAlignment(Element.ALIGN_CENTER);
                     Parrafo7.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));  
                      
                      
                     Paragraph Parrafo99 = new Paragraph();
                     Parrafo99.setAlignment(Element.ALIGN_CENTER);
                     Parrafo99.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                     PdfPCell Celda99 = new PdfPCell ( Parrafo99);
                     
                     Paragraph Parrafo98 = new Paragraph();
                     Parrafo98.setAlignment(Element.ALIGN_CENTER);
                     Parrafo98.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                     
                     Paragraph Parrafo97 = new Paragraph();
                     Parrafo97.setAlignment(Element.ALIGN_CENTER);
                     Parrafo97.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                     
                     
                     Parrafo97.add("TOTAL -> " );
                     PdfPCell Celda97 = new PdfPCell ( Parrafo97);
                     tabla.addCell(Celda97); 
                     
                     Parrafo99.clear();
                     Parrafo99.add("" );
                     tabla.addCell(Celda99);  

                     Parrafo99.add("" );
                     tabla.addCell(Celda99); 

                     Parrafo98.add(""+JFormato.format(Acomula_Subsidio_Pagados) );
                     PdfPCell Celda98 = new PdfPCell ( Parrafo98);
                     tabla.addCell(Celda98);

                     //******************************************** 
                     this.get_Ingresos(Fecha);
                     
                     Parrafo5.add(""+JFormato.format(this.VentaRetenida));
                     PdfPCell Celda5 = new PdfPCell ( Parrafo5);
                     tabla.addCell(Celda5);
             
                     Parrafo6.add(""+JFormato.format(this.SaldoAnterior));
                     PdfPCell Celda6 = new PdfPCell ( Parrafo6);
                     tabla.addCell(Celda6);
                     //System.out.println(Fecha+" "+valor_pagados);
                     double Operacion = this.SaldoAnterior + this.VentaRetenida - this.Acomula_Subsidio_Pagados -valor_pagados;
                     
                     Parrafo7.add(""+JFormato.format(Operacion));
                     PdfPCell Celda7 = new PdfPCell ( Parrafo7);
                     tabla.addCell(Celda7);
    }
    public void getInformacion(){
        try {
            String Str_Sql = "SELECT t01.fecha , t01.codigo ,t02.descrip , t01.valor FROM selinlib.JCAC as t01, selinlib.jccentros as t02 where t01.fecha >="+this.Fecha+" AND t01.fecha <="+this.FechaFinal+" and t01.codigo = t02.codigo "
                            + "order by t01.fecha asc ";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            float  AnchoCelda []  = new float[7]; 
            AnchoCelda [0] = (float) 15.0;
            AnchoCelda [1] = (float) 15.0;
            AnchoCelda [2] = (float) 25.0;
            AnchoCelda [3] = (float) 25.0;
            AnchoCelda [4] = (float) 25.0;
            AnchoCelda [5] = (float) 25.0;
            AnchoCelda [6] = (float) 25.0;
            
            tabla.setWidths(AnchoCelda);  
            boolean SwCotrol= true;
            int Fecha = 0;
            Acomula_Subsidio_Pagados = 0;
            Acomula_pagado_efectivo = 0;
            while (rs.next()){
                int FechaAnterior = rs.getInt("Fecha");
                if (SwCotrol==false) {
                  if(Fecha != FechaAnterior ){
                     //Inclusion de valor de anticipos pagados a los empleados de comfamiliar que antes eran cheques
                     double valor_pagado_empledos_efectivo= get_pago_efectivo_empleados(""+Fecha );
                     //System.out.println(Fecha+" "+valor_pagado_empledos_efectivo);
                     this.Imprimir_Subtotales(Fecha, valor_pagado_empledos_efectivo);
                     Fecha = FechaAnterior;
                     Acomula_Subsidio_Pagados = 0;
                  }
                }
                 if (SwCotrol) {
                    SwCotrol =false;
                    Fecha = FechaAnterior;
                    this.getCabecera_Tabla();
                }
                
                Paragraph Parrafo1 = new Paragraph();
                Parrafo1.setAlignment(Element.ALIGN_CENTER);
                Parrafo1.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));

                Paragraph Parrafo2 = new Paragraph();
                Parrafo2.setAlignment(Element.ALIGN_CENTER);
                Parrafo2.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
            
                Paragraph Parrafo3 = new Paragraph();
                Parrafo3.setAlignment(Element.ALIGN_CENTER);
                Parrafo3.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
            
                Paragraph Parrafo4 = new Paragraph();
                Parrafo4.setAlignment(Element.ALIGN_CENTER);
                Parrafo4.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
            
                Paragraph Parrafo5 = new Paragraph();
                Parrafo5.setAlignment(Element.ALIGN_CENTER);
                Parrafo5.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
            
                Paragraph Parrafo6 = new Paragraph();
                Parrafo6.setAlignment(Element.ALIGN_CENTER);
                Parrafo6.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
            
                Paragraph Parrafo7 = new Paragraph();
                Parrafo7.setAlignment(Element.ALIGN_CENTER);
                Parrafo7.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
                
                Parrafo1.add(""+FechaAnterior);
                PdfPCell Celda1 = new PdfPCell ( Parrafo1);
                tabla.addCell(Celda1);
            
                Parrafo2.add(""+rs.getInt("Codigo"));
                PdfPCell Celda2 = new PdfPCell ( Parrafo2);
                tabla.addCell(Celda2);

                Parrafo3.add(""+rs.getString("descrip"));
                PdfPCell Celda3 = new PdfPCell ( Parrafo3);
                tabla.addCell(Celda3);
                
                double Vlr = rs.getDouble("Valor");
                Acomula_Subsidio_Pagados = Acomula_Subsidio_Pagados + Vlr;
                Parrafo4.add(""+this.JFormato.format(Vlr) );
                PdfPCell Celda4 = new PdfPCell ( Parrafo4);
                tabla.addCell(Celda4);

                
                Parrafo5.add("");
                PdfPCell Celda5 = new PdfPCell ( Parrafo5);
                tabla.addCell(Celda5);
            
                Parrafo6.add("");
                PdfPCell Celda6 = new PdfPCell ( Parrafo6);
                tabla.addCell(Celda6);
                
                Parrafo7.add("");
                PdfPCell Celda7 = new PdfPCell ( Parrafo7);
                tabla.addCell(Celda7);
            }
            if(SwCotrol==false){
                double valor_pagado_empledos_efectivo= get_pago_efectivo_empleados(""+Fecha );
                this.Imprimir_Subtotales(Fecha, valor_pagado_empledos_efectivo);
            }
            documento.add(tabla);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JReporteSaldoCentro:getInformacion()"+e.getMessage() ); 
        }
    }
    
    public double get_pago_efectivo_empleados(String p_fecha){
        double valor = 0;
        try {
            String Str_Sql=" select SUM(VAGEMO) as valor from weblib.maoref  \n" +
                           " where FEENEF='"+p_fecha+"'" +
                           " and ESOREF='E'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                valor = rs.getDouble("valor");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Class:JIngresos:get_efectivo_empleados() "+e.getMessage());
        }
        return valor;
    }
    
    public void EncabezadoDocumento(){
        try{ 
                Paragraph Parrafo = new Paragraph();
                Paragraph Parrafo2 = new Paragraph();
                
                Parrafo2.setAlignment(Element.ALIGN_CENTER);
                Parrafo2.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));    
                Parrafo2.add("AUDITORIA INTERNA"); 
                documento.add(Parrafo2);
                
                Parrafo2.clear();
                Parrafo2.add("COMFAMILIAR ATLÁNTICO"); 
                documento.add(Parrafo2);
                
                Parrafo2.clear();
                Parrafo2.add("DETALLE DE VENTAS RETENIDAS    "); 
                documento.add(Parrafo2);
                
                this.Salto_Linea();
           
                 
                
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "JReport:EncabezadoDocumento "+e.getMessage());
        }
    }
    public void Salto_Linea(){
        try{
            documento.add(new Paragraph("         ",FontFactory.getFont("tahoma",   
				12,Font.BOLD,BaseColor.BLACK))); 
        }catch(Exception e ){
            JOptionPane.showMessageDialog(null, "JReport:Salto_Linea "+e.getMessage());            
        }
    }
    
   
    
}
