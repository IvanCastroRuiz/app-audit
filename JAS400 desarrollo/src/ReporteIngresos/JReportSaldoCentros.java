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


public class JReportSaldoCentros {
    
    private Document documento;
    private String Fecha;
    private Connection Cn;
    private JConection JBase_Datos;
    
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private double Saldo30, Saldo48,Saldo82,SaldoSportGym,Saldo74,SaldoSedeNorte,SaldoViva, Saldo_Sabana;
    
    private JLeeFichero JRuta;
    
    public JReportSaldoCentros(JConection JBase_Datos2 , Connection Cn3, String Fecha){
        this.Fecha = Fecha;
        this.JBase_Datos = JBase_Datos2;
        this.Cn = Cn3;
        JFormato= new DecimalFormat(NumeroFormato);
        JRuta = new JLeeFichero();
        documento = new Document();
        documento = new Document(PageSize.A4,90,5,5,5);
        //this.getInformacion();
        this.CreatePdf();
    }
    
    public void CreatePdf(){
       try{
            String NombreArchivo = "Saldo_Centros_Atencion_"+this.Fecha;
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
    
    public void getDetalle_Centros_Atencion(){
        try {
            String Str_Sql ="SELECT codigocac, descrip ,valor             \n" +
                            "FROM  selinlib.jsaldos , selinlib.JCCENTROS  \n" +
                            "where fecha="+this.Fecha+"   and codigo = codigocac \n" +
                            "AND de = 99999999 and para = 99999999        ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.Saldo48=0;
            while (rs.next()){
                this.Salto_Linea();
                Paragraph Parrafo = new Paragraph();
                this.Saldo48=this.Saldo48+rs.getDouble("valor");
                Parrafo.add(rs.getString("descrip")+" : "+JFormato.format(rs.getDouble("valor"))); 
                documento.add(Parrafo);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JReporteSaldoCentro:getInformacion()"+e.getMessage() ); 
        }
    }
    
    public void getInformacion(){
        try {
            String Str_Sql = "SELECT codigocac, valor FROM selinlib.jsaldos where fecha ="+this.Fecha+" AND de = 99999999 and para = 99999999";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while (rs.next()){
                if( rs.getInt("codigocac")==1){
                    this.Saldo48 =rs.getDouble("valor");
                }
                if( rs.getInt("codigocac")==7){
                    this.Saldo74 = rs.getDouble("valor");    
                }
                if( rs.getInt("codigocac")==14){
                    this.Saldo30 =rs.getDouble("valor");
                }
                if( rs.getInt("codigocac")==18){
                    this.Saldo82 = rs.getDouble("valor");
                }
                if( rs.getInt("codigocac")==45){
                    this.SaldoSedeNorte = rs.getDouble("valor");
                }
                if( rs.getInt("codigocac")==52){
                    this.SaldoSportGym =rs.getDouble("valor");
                }
                if( rs.getInt("codigocac")==55){
                    this.SaldoViva =rs.getDouble("valor");
                }
                if( rs.getInt("codigocac")==56){
                    this.Saldo_Sabana =rs.getDouble("valor");
                }
             }
             rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JReporteSaldoCentro:getInformacion()"+e.getMessage() ); 
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
                Parrafo2.add("COMFAMILIAR ATLÁNTICO"); 
                documento.add(Parrafo2);
                
                Parrafo2.clear();
                Parrafo2.add("SALDO EFECTIVO RETENIDO POR CENTRO DE ATENCION     "); 
                documento.add(Parrafo2);
                
                this.Salto_Linea();
                Paragraph Parrafo99 = new Paragraph();
                Parrafo99.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));  
                Parrafo99.add("Fecha:  "+this.Fecha); 
                documento.add(Parrafo99);
                
                this.Salto_Linea();
                this.Salto_Linea();
                getDetalle_Centros_Atencion();
//                Parrafo.clear();
//                Parrafo.add("Saldo Actual Centro Atencion Calle 48 : "+JFormato.format(this.Saldo48)); 
//                documento.add(Parrafo);
//
//                this.Salto_Linea();
//                Parrafo.clear();
//                Parrafo.add("Saldo Actual Centro Atencion Calle 30 : "+JFormato.format(this.Saldo30)); 
//                documento.add(Parrafo);
//
//                this.Salto_Linea();
//                Parrafo.clear();
//                Parrafo.add("Saldo Actual Centro Atencion Calle 74 : "+JFormato.format(this.Saldo74)); 
//                documento.add(Parrafo);
//
//                this.Salto_Linea();
//                Parrafo.clear();
//                Parrafo.add("Saldo Actual Centro Atencion Sport Gym : "+JFormato.format(this.SaldoSportGym)); 
//                documento.add(Parrafo);
//
//                
//                this.Salto_Linea();
//                Parrafo.clear();
//                Parrafo.add("Saldo Actual Centro Atencion Calle 82 : "+JFormato.format(this.Saldo82)); 
//                documento.add(Parrafo);
//                
//                this.Salto_Linea();
//                Parrafo.clear();
//                Parrafo.add("Saldo Actual Centro Atencion Viva : "+JFormato.format(this.SaldoViva)); 
//                documento.add(Parrafo);
//                
//                this.Salto_Linea();
//                Parrafo.clear();
//                Parrafo.add("Saldo Actual Sabanalarga : "+JFormato.format(this.Saldo_Sabana)); 
//                documento.add(Parrafo);
//                
//                
//                this.Salto_Linea();
//                Parrafo.clear();
//                Parrafo.add("Saldo Actual Sede Norte : "+JFormato.format(this.SaldoSedeNorte)); 
//                documento.add(Parrafo);
                this.getBasesAsignadas();
               
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "JReport:EncabezadoDocumento "+e.getMessage());
        }
    }
    public void getBasesAsignadas(){
        try {
            Paragraph Parrafo = new Paragraph();
                
            Parrafo.setAlignment(Element.ALIGN_CENTER);
            Parrafo.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));  
 
            String Str_Sql = "SELECT RESPONS,VALOR FROM SELINLIB.JBASES ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            boolean Sw = true;
            double Total = 0;
            double Tmp = 0;
            while (rs.next()){
                if(Sw){
                    this.Salto_Linea();
                    Parrafo.clear();
                    Parrafo.add("BASES ASIGNADAS "); 
                    documento.add(Parrafo);
                    Sw=false;
                    Parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
                    Parrafo.setFont(FontFactory.getFont("tahoma",13,Font.NORMAL,BaseColor.BLACK));  
                }
                this.Salto_Linea();
                Parrafo.clear();
                Tmp = rs.getDouble("VALOR");
                Total = Total + Tmp;
                Parrafo.add(rs.getString("RESPONS") +":    "+JFormato.format(Tmp)); 
                documento.add(Parrafo);
            }
            
            Total = Total +  this.Saldo48 ;
            this.Salto_Linea();
            Parrafo.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));  
            Parrafo.clear();
            Parrafo.add("Total de Saldos    "+JFormato.format(Total)); 
            documento.add(Parrafo);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "JReport:getBasesAsignadas() "+e.getMessage());
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
