package ReporteIngresos;


import Principal.JLeeFichero;
import BD_As400.JConection;
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


public class JReportControlIngresos {
    
    private Document documento;
    private String Fecha;
    private Connection Connection_BD;
    private JConection JBase_Datos;
    
    private double EfectivoConsignar, VentasGnb, AbonoRetenido, VentaRetenida, CambioCheque;
    private double CanjeEsperado, CanjeConsignado, CanjeCambioCheque, CanjeChequeConsignar;
    private double SaldoAnteriorVenta, ValorVentaRetenida, ValorAbonadoVenta, SaldoActualVenta;
    private double SaldoAnteriorCanjeVenta, CanjeVentaRetenida, CanjeVentaAbonada, SaldoActualCanjeVenta;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    
    private Date fecha;
    private JLeeFichero JRuta;
    
    public JReportControlIngresos(JConection JBase_Datos2 , Connection Cn3, String xFecha){
        
        this.JBase_Datos = JBase_Datos2;
        this.Connection_BD = Cn3;
        JFormato= new DecimalFormat(NumeroFormato);
        JRuta = new JLeeFichero();
        fecha = new Date(0);
        documento = new Document();
        documento = new Document(PageSize.A4,90,5,5,5);
        this.Fecha = xFecha;
        this.CreatePdf();
    }
    
    public void CreatePdf(){
       try{
            String NombreArchivo = "Saldos_Control_Ingreso"+this.Fecha;
            JRuta.Leer();
            String Ruta1 = JRuta.getRutaJReport()+NombreArchivo+".pdf";
            JOptionPane.showMessageDialog(null,Ruta1);
            FileOutputStream ficheroPdf = new FileOutputStream(Ruta1);
            PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
            documento.open();
            documento.addTitle("COMFAMILIAR DEL ATLÁNTICO");
            this.setInforme();
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
                documento.add(new Paragraph(" SALDO DE CONTROL DE INGRESOS ",FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));             
                documento.add(new Paragraph(" FECHA: "+this.Fecha,FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));             

                Parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
                Parrafo.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
               
                Parrafo2.setAlignment(Element.ALIGN_JUSTIFIED);
                Parrafo2.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));   
                
                this.Salto_Linea();
                this.Salto_Linea();
                
                Parrafo2.clear();
                Parrafo2.add("TOTAL EFECTIVO ESPERADO POR CONSIGNAR"); 
                documento.add(Parrafo2);                
                
                this.Salto_Linea();
                Parrafo.clear();
                Parrafo.add("Efectivo por Consignar :"+JFormato.format(this.EfectivoConsignar)); 
                documento.add(Parrafo);
                
                Parrafo.clear();
                Parrafo.add("Ventas Banco GNB :"+JFormato.format( this.VentasGnb)); 
                documento.add(Parrafo);
                
                Parrafo.clear();
                Parrafo.add("Abono Retenido :"+JFormato.format( this.AbonoRetenido)); 
                documento.add(Parrafo);
                
                Parrafo.clear();
                Parrafo.add("Ventas Retenidas :"+JFormato.format( this.VentaRetenida)); 
                documento.add(Parrafo);
                
                Parrafo.clear();
                Parrafo.add("Cambio de Cheque :"+JFormato.format( this.CambioCheque)); 
                documento.add(Parrafo);
  
 
                
                //*********************** CANJE
                this.Salto_Linea();
                this.Salto_Linea();
                
                Parrafo2.clear();
                Parrafo2.add("TOTAL CANJE ESPERADO"); 
                documento.add(Parrafo2);                
                
                this.Salto_Linea();
                Parrafo.clear();
                Parrafo.add("Canje Esperado : "+JFormato.format( this.CanjeEsperado)); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Canje Consignado : "+JFormato.format( this.CanjeConsignado)); 
                documento.add(Parrafo);
                
                Parrafo.clear();
                Parrafo.add("Cambio de Cheque : "+JFormato.format( this.CanjeCambioCheque)); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Cheque pendiente por Consingar : "+JFormato.format( this.CanjeChequeConsignar)); 
                documento.add(Parrafo);                
            
                //***********************************VENTAS 
                this.Salto_Linea();
                this.Salto_Linea();
                
                Parrafo2.clear();
                Parrafo2.add("TOTAL VENTAS RETENIDAS"); 
                documento.add(Parrafo2);                

                this.Salto_Linea();
                Parrafo.clear();
                Parrafo.add("Saldo Anterior Ventas : "+JFormato.format( this.SaldoAnteriorVenta)); 
                documento.add(Parrafo);

                Parrafo.clear();
                Parrafo.add("Valor Venta Retenida : "+JFormato.format( this.ValorVentaRetenida)); 
                documento.add(Parrafo);                
                
                Parrafo.clear();
                Parrafo.add("Valor Abono de Venta : "+JFormato.format( this.ValorAbonadoVenta)); 
                documento.add(Parrafo);                
                
                Parrafo.clear();
                Parrafo.add("Valor Venta Retenida : "+JFormato.format( this.ValorVentaRetenida)); 
                documento.add(Parrafo);                
                
                Parrafo.clear();
                Parrafo.add("Saldo Actual Ventas : "+JFormato.format( this.SaldoActualVenta)); 
                documento.add(Parrafo);                
                
                //************************CANJE DE VENTA
                this.Salto_Linea();
                this.Salto_Linea();
                
                Parrafo2.clear();
                Parrafo2.add("TOTAL CANJE RETENIDO"); 
                documento.add(Parrafo2);                
                
                this.Salto_Linea();
                Parrafo.clear();
                Parrafo.add("Saldo Anterior Canje : "+JFormato.format( this.SaldoAnteriorCanjeVenta)); 
                documento.add(Parrafo);  
                
                Parrafo.clear();
                Parrafo.add("Canje de Venta Retenida : "+JFormato.format( this.CanjeVentaRetenida)); 
                documento.add(Parrafo);  
                
                Parrafo.clear();
                Parrafo.add("Canje Venta Abonada : "+JFormato.format( this.CanjeVentaAbonada)); 
                documento.add(Parrafo);                 
   
                Parrafo.clear();
                Parrafo.add("Saldo Actual Cenje de Venta : "+JFormato.format( this.SaldoActualCanjeVenta)); 
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
    
    public void setInforme(){
        try{
            String StrSql ;
            StrSql = "SELECT EFTCONS, VTAGNB, ABNRET,VTARET ,CMBCHEQ,     CNJESPER,CNJCONSI,CCMBCHEQ,CHQPCONS,   SALANTVENT,VVLRRETENI,VVLRABONAD,SALACTUAL,      SALANTCANJ,CVLRRETENI,CVLRABONAD,CALACTUAL  "
                    +" FROM SELINLIB.JIngreso WHERE Fecha ="+this.Fecha;
            ResultSet  Result = JBase_Datos.SQL_QRY(this.Connection_BD,StrSql);
	    if(Result.next()){
                
                try{
                    this.EfectivoConsignar    = Result.getDouble("EFTCONS");
                }catch(Exception e ){}
                try{
                    this.VentasGnb    = Result.getDouble("VTAGNB");
                }catch(Exception e ){}
                try{
                    this.AbonoRetenido = Result.getDouble("ABNRET");
                }catch(Exception e ){}
                try{
                    this.VentaRetenida = Result.getDouble("VTARET");
                }catch(Exception e ){}
                try{
                    this.CambioCheque  = Result.getDouble("CMBCHEQ");
                }catch(Exception e ){}
               
                
                try{
                    this.CanjeEsperado    = Result.getDouble("CNJESPER");
                }catch(Exception e ){}
                try{
                    this.CanjeConsignado    = Result.getDouble("CNJCONSI");
                }catch(Exception e ){}
                try{
                    this.CanjeCambioCheque    = Result.getDouble("CCMBCHEQ");
                }catch(Exception e ){}
                try{
                    this.CanjeChequeConsignar     = Result.getDouble("CHQPCONS");
                }catch(Exception e ){}
                
                try{
                    this.SaldoAnteriorVenta    = Result.getDouble("SALANTVENT");
                }catch(Exception e ){}
                try{
                    this.ValorVentaRetenida    = Result.getDouble("VVLRRETENI");
                }catch(Exception e ){}
                try{
                    this.ValorAbonadoVenta    = Result.getDouble("VVLRABONAD");
                }catch(Exception e ){}
                try{
                    this.SaldoActualVenta     = Result.getDouble("SALACTUAL");
                }catch(Exception e ){}
                
                try{
                    this.SaldoAnteriorCanjeVenta = Result.getDouble("SALANTCANJ");
                }catch(Exception e ){}
                try{
                    this.CanjeVentaRetenida      = Result.getDouble("CVLRRETENI");
                }catch(Exception e ){}
                try{
                    this.CanjeVentaAbonada       = Result.getDouble("CVLRABONAD");
                }catch(Exception e ){}
                try{
                    this.SaldoActualCanjeVenta     = Result.getDouble("CALACTUAL");
                }catch(Exception e ){}
            }
        }catch(Exception e ){
            JOptionPane.showMessageDialog(null,"JReportControlIngresos: setInforme()"+e.getMessage());
        }
    }
    
}
