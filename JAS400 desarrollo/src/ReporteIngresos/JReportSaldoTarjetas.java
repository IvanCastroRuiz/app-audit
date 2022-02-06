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
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Date.*;
/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */


public class JReportSaldoTarjetas {
    
    private Document documento;
    private Connection Connection_BD;
    private JConection JBase_Datos;
    private int Tipo;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private int Centro;
    private JLeeFichero JRuta;
    private String FechaInicial , FechaFinal;
    public JReportSaldoTarjetas( Connection Cn3,JConection JBase_Datos2 , String FechaIn , String FechaF, int PTipo, int PCentro){
        this.Tipo = PTipo;
        this.Centro =PCentro;
        this.FechaInicial= FechaIn;
        this.FechaFinal = FechaF;
        this.JBase_Datos = JBase_Datos2;
        this.Connection_BD = Cn3;
        JFormato= new DecimalFormat(NumeroFormato);
        JRuta = new JLeeFichero();
        documento = new Document();
        documento = new Document(PageSize.B2.rotate(),300,3,20,20);
        System.out.println(""+documento.leftMargin());
        this.CreatePdf();
    }
    
    public void CreatePdf(){
       try{
            String NombreArchivo;
            if(Tipo==1){
                NombreArchivo = "Saldo_Tarjetas_"+this.FechaInicial+"_"+this.FechaFinal;
            }else{
                NombreArchivo = "Saldo_Tarjetas_Centro_"+this.Centro+"_"+this.FechaInicial+"_"+this.FechaFinal;
            }
            JRuta.Leer();
            String Ruta1 = JRuta.getRutaJReport()+NombreArchivo+".pdf";
            JOptionPane.showMessageDialog(null,Ruta1);
            FileOutputStream ficheroPdf = new FileOutputStream(Ruta1);
            PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
            documento.open();
            documento.addTitle("COMFAMILIAR DEL ATLÁNTICO");
            
            this.Insert_Logo();
            this.EncabezadoDocumento();
            documento.close();
            ficheroPdf.close();
            
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
    public void EncabezadoDocumento(){
        try{ 
                Paragraph Parrafo = new Paragraph();
                Paragraph Parrafo2 = new Paragraph();


                Parrafo2.setAlignment(Element.ALIGN_CENTER);
                Parrafo2.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));    
                Parrafo2.add("COMFAMILIAR ATLANTICO"); 
                documento.add(Parrafo2);
                
                Parrafo2.clear();
                Parrafo2.setAlignment(Element.ALIGN_CENTER);
                Parrafo2.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));    
                Parrafo2.add("AUDITORIA INTERNA"); 
                documento.add(Parrafo2);
                
                Parrafo2.clear();
                Parrafo2.add("SALDO DE TARJETAS PARA ANTICIPO DE CUOTA MONETARIA POR CENTRO DE ATENCION"); 
                documento.add(Parrafo2);
                
                this.Salto_Linea();
                
                documento.add(new Paragraph(" FECHA INICIAL:"+this.FechaInicial,FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));             

                documento.add(new Paragraph(" FECHA FINAL:"+this.FechaFinal,FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK))); 
                
                this.Salto_Linea();
                this.Salto_Linea();
                this.Salto_Linea();
                this.getSaldoTarjetas();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "JReport:EncabezadoDocumento "+e.getMessage());
        }
    }
    
    public String getNombre_CAC(String Centro){
        String Nombre ="";
        try {
            String Str_Sql =" SELECT * FROM SELINLIB.JCCENTROS WHERE CODTARJ="+Centro;
            //System.out.println(""+Str_Sql);
            ResultSet rs = JBase_Datos.SQL_QRY(this.Connection_BD,Str_Sql);
            if(rs.next()){
                Nombre = rs.getString("DESCRIP");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " getNombre_CAC(String Centro) "+e.getMessage());
        }
        return Nombre;
    }
    
    public void getSaldoTarjetas(){
        try {
            String Str_Sql;
            if(Tipo==1){
                Str_Sql = "SELECT fecha,codigo,entrada,salida,salant,ventas,salact FROM selinlib.jtarjetas where fecha>="+this.FechaInicial+" AND Fecha<="+this.FechaFinal+" "
                        + " order by fecha desc";
            }else{
                Centro = Equivalencia_Tarjetas(Centro);
                Str_Sql = "SELECT fecha,codigo,entrada,salida,salant,ventas,salact FROM selinlib.jtarjetas where fecha>="+this.FechaInicial+" AND Fecha<="+this.FechaFinal+""
                        + " AND Codigo="+Centro+" order by fecha asc";
            }
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Connection_BD,Str_Sql);
            float  AnchoCelda []  = new float[8]; 
            AnchoCelda [0] = (float) 5.0;
            AnchoCelda [1] = (float) 6.0;
            AnchoCelda [2] = (float) 5.0;
            AnchoCelda [3] = (float) 5.0;
            AnchoCelda [4] = (float) 5.0;
            AnchoCelda [5] = (float) 5.0;
            AnchoCelda [6] = (float) 5.0;
            AnchoCelda [7] = (float) 5.0;
            
            PdfPTable tabla = new PdfPTable(8);
            tabla.setWidths(AnchoCelda);    
            
            Paragraph Parrafo = new Paragraph();
            Parrafo.setAlignment(Element.ALIGN_CENTER);
            Parrafo.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo1a = new Paragraph();
            Parrafo1a.setAlignment(Element.ALIGN_CENTER);
            Parrafo1a.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
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

            //***************************************************
            Parrafo.add("Codigo del Centro de Atención");
            PdfPCell Celda1 = new PdfPCell ( Parrafo);
            tabla.addCell(Celda1);
            
            Parrafo7.add("Nombre del Centro de Atencion");
            PdfPCell Celda7 = new PdfPCell ( Parrafo7);
            tabla.addCell(Celda7);
            
            Parrafo1a.add("Fecha");
            PdfPCell Celda1a = new PdfPCell ( Parrafo1a);
            tabla.addCell(Celda1a);
            
            Parrafo2.add("Entradas");
            PdfPCell Celda2 = new PdfPCell ( Parrafo2);
            tabla.addCell(Celda2);
            

            Parrafo3.add("Salidas");
            PdfPCell Celda3 = new PdfPCell ( Parrafo3);
            tabla.addCell(Celda3);


            Parrafo4.add("Saldo Anterior");
            PdfPCell Celda4 = new PdfPCell ( Parrafo4);
            tabla.addCell(Celda4);

            Parrafo5.add("Ventas");
            PdfPCell Celda5 = new PdfPCell ( Parrafo5);
            tabla.addCell(Celda5);


            Parrafo6.add("Saldo Actual");
            PdfPCell Celda6 = new PdfPCell ( Parrafo6);
            tabla.addCell(Celda6);
            double SumaEntrada = 0 , SumaSalida = 0 , SumaSaldoAnterior = 0, SumaSaldoActual =0, SumaVentas=0;
            boolean Control = false;
            while (rs.next()){
                    Control = true;
                    Paragraph Parrafo11 = new Paragraph();
                    Parrafo.setAlignment(Element.ALIGN_CENTER);
                    Parrafo.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

                    Paragraph Parrafo11a = new Paragraph();
                    Parrafo1a.setAlignment(Element.ALIGN_CENTER);
                    Parrafo1a.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
                    Paragraph Parrafo12 = new Paragraph();
                    Parrafo2.setAlignment(Element.ALIGN_CENTER);
                    Parrafo2.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

                    Paragraph Parrafo13 = new Paragraph();
                    Parrafo3.setAlignment(Element.ALIGN_CENTER);
                    Parrafo3.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

                    Paragraph Parrafo14 = new Paragraph();
                    Parrafo4.setAlignment(Element.ALIGN_CENTER);
                    Parrafo4.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

                    Paragraph Parrafo15 = new Paragraph();
                    Parrafo5.setAlignment(Element.ALIGN_CENTER);
                    Parrafo5.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
                    Paragraph Parrafo16 = new Paragraph();
                    Parrafo6.setAlignment(Element.ALIGN_CENTER);
                    Parrafo6.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
                    Paragraph Parrafo99 = new Paragraph();
                    Parrafo99.setAlignment(Element.ALIGN_CENTER);
                    Parrafo99.setFont(FontFactory.getFont("tahoma",14,Font.UNDEFINED,BaseColor.BLACK));
                    
                    
                    //***************************************************
                    Parrafo11.add(""+Equivalencia_Tarjetas_User(""+rs.getInt("codigo")) );
                    PdfPCell Celda11 = new PdfPCell ( Parrafo11);
                    tabla.addCell(Celda11);
                    
                    Parrafo99.add(getNombre_CAC(rs.getString("codigo")) );
                    PdfPCell Celda99 = new PdfPCell ( Parrafo99);
                    tabla.addCell(Celda99);
                    
                    
                    Parrafo11a.add(""+rs.getInt("fecha") );
                    PdfPCell Celda11a = new PdfPCell ( Parrafo11a);
                    tabla.addCell(Celda11a);
                    
                    double Entrada = rs.getDouble("Entrada");
                    SumaEntrada = SumaEntrada + Entrada;
                    Parrafo12.add(""+JFormato.format(Entrada));
                    PdfPCell Celda12 = new PdfPCell ( Parrafo12);
                    tabla.addCell(Celda12);
            
                    double Salida  = rs.getDouble("Salida");
                    SumaSalida = SumaSalida + Salida;
                    Parrafo13.add(""+JFormato.format(Salida));
                    PdfPCell Celda13 = new PdfPCell ( Parrafo13);
                    tabla.addCell(Celda13);

                    double Anterior = rs.getDouble("Salant");
                    SumaSaldoAnterior = SumaSaldoAnterior + Anterior;
                    Parrafo14.add(""+JFormato.format(Anterior));
                    PdfPCell Celda14 = new PdfPCell ( Parrafo14);
                    tabla.addCell(Celda14);

                    double Ventas = rs.getDouble("Ventas");
                    SumaVentas = SumaVentas + Ventas;
                    Parrafo15.add(""+JFormato.format(Ventas));
                    PdfPCell Celda15 = new PdfPCell ( Parrafo15);
                    tabla.addCell(Celda15);

                    double Actual =rs.getDouble("Salact");
                    SumaSaldoActual = SumaSaldoActual + Actual;
                    Parrafo16.add(""+JFormato.format(Actual));
                    PdfPCell Celda16 = new PdfPCell ( Parrafo16);
                    tabla.addCell(Celda16);
            }
            
            if(Control){
                Paragraph Parrafo21 = new Paragraph();
                Parrafo21.setAlignment(Element.ALIGN_CENTER);
                Parrafo21.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.RED));
            
                Paragraph Parrafo22 = new Paragraph();
                Parrafo22.setAlignment(Element.ALIGN_CENTER);
                Parrafo22.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.RED));
            
                Paragraph Parrafo23 = new Paragraph();
                Parrafo23.setAlignment(Element.ALIGN_CENTER);
                Parrafo23.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.RED));
            
                Paragraph Parrafo24 = new Paragraph();
                Parrafo24.setAlignment(Element.ALIGN_CENTER);
                Parrafo24.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.RED));
                
                Paragraph Parrafo25 = new Paragraph();
                Parrafo25.setAlignment(Element.ALIGN_CENTER);
                Parrafo25.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.RED));
                
                Paragraph Parrafo26 = new Paragraph();
                Parrafo26.setAlignment(Element.ALIGN_CENTER);
                Parrafo26.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.RED));
                
                Paragraph Parrafo27 = new Paragraph();
                Parrafo27.setAlignment(Element.ALIGN_CENTER);
                Parrafo27.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.RED));
                
                Paragraph Parrafo28 = new Paragraph();
                Parrafo28.setAlignment(Element.ALIGN_CENTER);
                Parrafo28.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.RED));
                
            
                Parrafo21.add("");
                PdfPCell Celda21 = new PdfPCell ( Parrafo21);
                tabla.addCell(Celda21);
                
                Parrafo28.add("");
                PdfPCell Celda28 = new PdfPCell ( Parrafo28);
                tabla.addCell(Celda28);
                
                
                Parrafo22.add("");
                PdfPCell Celda22 = new PdfPCell ( Parrafo22);
                tabla.addCell(Celda22);
                
                Parrafo23.add(JFormato.format(SumaEntrada));
                PdfPCell Celda23 = new PdfPCell ( Parrafo23);
                tabla.addCell(Celda23);
            
                Parrafo24.add(JFormato.format(SumaSalida));
                PdfPCell Celda24 = new PdfPCell ( Parrafo24);
                tabla.addCell(Celda24);
                
                Parrafo25.add(JFormato.format(SumaSaldoAnterior));
                PdfPCell Celda25 = new PdfPCell ( Parrafo25);
                tabla.addCell(Celda25);
                
                Parrafo26.add(JFormato.format(SumaVentas));
                PdfPCell Celda26 = new PdfPCell ( Parrafo26);
                tabla.addCell(Celda26);
                
                Parrafo27.add("");
                PdfPCell Celda27 = new PdfPCell ( Parrafo27);
                tabla.addCell(Celda27);
            }    
            documento.add(tabla);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "JReport:getSaldoTarjetas() "+e.getMessage());            
        }
    }
    public String Equivalencia_Tarjetas_User(String Codigo){
       String XCodigo = "";
       try {
            String Str_Sql = "Select cac,codtarj from Selinlib.JCCentros where codtarj="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Connection_BD,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getString("cac");
            }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "JReport:Equivalencia_Tarjetas_User "+e.getMessage());            
       }
       return XCodigo;
   }
     public int Equivalencia_Tarjetas(int Codigo){
       int XCodigo = 0;
       try {
            String Str_Sql = "Select cac,codtarj from Selinlib.JCCentros where cac="+Codigo;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Connection_BD,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getInt("codtarj");
            }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "JReport:Equivalencia_Tarjetas_User "+e.getMessage());            
       }
       return XCodigo;
   }   
    
    
    public void Salto_Linea(){
        try{
            documento.add(new Paragraph("         ",FontFactory.getFont("tahoma",   
				12,Font.BOLD,BaseColor.BLACK))); 
        }catch(Exception e ){
            JOptionPane.showMessageDialog(null, "JReport:Salto_Linea() "+e.getMessage());            
        }
    }
    
   
    
}
