package ReporteIngresos;


import Principal.JLeeFichero;
import BD_As400.JConection;
import Ingresos.*;
import com.itextpdf.text.*;
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


public class JReportIngresoConsolidado {
    
    private Document documento;
    private String Fecha;
    private Connection Connection_BD;
    private JConection JBase_Datos;
    
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private JIngresosXPeriodos JConsolidado;
    private JLeeFichero JRuta;
    
    public JReportIngresoConsolidado(JConection JBase_Datos2 , Connection Cn3, JIngresosXPeriodos jConsolidado){
        this.JConsolidado=jConsolidado;
        this.JBase_Datos = JBase_Datos2;
        this.Connection_BD = Cn3;
        JFormato= new DecimalFormat(NumeroFormato);
        JRuta = new JLeeFichero();
        documento = new Document();
        documento = new Document(PageSize.A4,90,5,5,5);
        
        this.CreatePdf();
    }
    
    public void CreatePdf(){
       try{
            String NombreArchivo = "Ingreso_Consolidado_"+this.JConsolidado.getFechaInicial()+"_"+this.JConsolidado.getFechaFinal();
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
                Parrafo2.add("AUDITORIA INTERNA"); 
                documento.add(Parrafo2);
                
                Parrafo2.clear();
                Parrafo2.add("SISTEMA PARA EL CONTROL DE LOS  INGRESOS"); 
                documento.add(Parrafo2);
                
                this.Salto_Linea();
                documento.add(new Paragraph(" TOTAL ACOMULADO A LA FECHA ",FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));        
                
                documento.add(new Paragraph(" FECHA INICIAL: "+this.JConsolidado.getFechaInicial(),FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));             

                documento.add(new Paragraph(" FECHA FINAL: "+this.JConsolidado.getFechaFinal(),FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));             
                
                Parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
                Parrafo.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
               
                this.Salto_Linea();
                Parrafo.clear();
                Parrafo.add("Cheques Cambiado : "+JFormato.format(this.JConsolidado.getCambioChequeEfectivo())); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Ventas Gnb : "+JFormato.format(this.JConsolidado.getVentasGnb())); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Abono Retenido : "+JFormato.format(this.JConsolidado.getAbonoRetenido())); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Abono Retenido : "+JFormato.format(this.JConsolidado.getAbonoRetenido())); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Ventas Retenidas : "+JFormato.format(this.JConsolidado.getVentasRetenidas())); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Canje Esperado : "+JFormato.format(this.JConsolidado.getCangeEsperado())); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Canje Pendiente por Consignar : "+JFormato.format(this.JConsolidado.getCangeConsignado())); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Valor Venta Retenida : "+JFormato.format(this.JConsolidado.getValorVentaRetenida())); 
                documento.add(Parrafo);
                
                Parrafo.clear();
                Parrafo.add("Valor Venta Abonado : "+JFormato.format(this.JConsolidado.getVentaAbonada())); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Venta Cheque Pendiente por Consignar : "+JFormato.format(this.JConsolidado.getChequePendienteConsignar())); 
                documento.add(Parrafo);

                
                
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
