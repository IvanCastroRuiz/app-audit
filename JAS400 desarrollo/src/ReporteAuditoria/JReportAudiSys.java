package ReporteAuditoria;


import Principal.JLeeFichero;
import BD_Auditoria.JConeccion_AudiSys;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.io.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.JOptionPane;
import java.sql.*;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Date.*;
/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */


public class JReportAudiSys {
    private Document documento;
    private int IdPlan;
    private JConeccion_AudiSys JBase_Datos;
    private Connection Connection_BD;
    private String Objetivo, Alcance, Observacion,RecomendacionAuditor, RecomendacionCierre, RecomendacionAutorizacion, Area, Proceso, TipoInforme ; 
    private Date fecha;
    private JLeeFichero JRuta;
    public JReportAudiSys(int IdPlanTrabajo){
        JRuta = new JLeeFichero();
        fecha = new Date(0);
        JBase_Datos = new JConeccion_AudiSys();
	Connection_BD = JBase_Datos.Open_BD();
        documento = new Document();
        this.IdPlan = IdPlanTrabajo;
        this.CreatePdf();
    }
    
    public void CreatePdf(){
       try{
            String NombreArchivo = "AudiSysfichero"+IdPlan;
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
           JOptionPane.showMessageDialog(null,"JReport: "+e.getMessage());
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
            this.Salto_Linea();
            Paragraph Parrafo = new Paragraph();
            this.Salto_Linea();
            documento.add(new Paragraph(" Proceso Examinado: "+this.Proceso,FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));             
            documento.add(new Paragraph(" Area Examinada: "+this.Area,FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));             
            documento.add(Parrafo);
            documento.add(new Paragraph(" Fecha: "+fecha.getDate(),FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));             
            documento.add(Parrafo);
            documento.add(new Paragraph(" Auditor: ",FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));             
            documento.add(new Paragraph(" Tipo de Informe: "+this.TipoInforme,FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK)));             
            this.Salto_Linea();
            this.Salto_Linea();
            documento.add(new Paragraph(" Objetivos del Examen de Auditoria:",FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK))); 
           
            this.Salto_Linea();
            Parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            Parrafo.setFont(FontFactory.getFont("tahoma",14,Font.NORMAL,BaseColor.BLACK));
            Parrafo.clear();
            Parrafo.add(this.Objetivo); 
            documento.add(Parrafo);
            Parrafo.clear();
            this.Salto_Linea();
            documento.add(new Paragraph(" Alcance:",FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK))); 
            this.Salto_Linea();
            Parrafo.add(this.Alcance); 
            documento.add(Parrafo);
            Parrafo.clear();
            this.Salto_Linea();
            documento.add(new Paragraph(" Observaciones:",FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK))); 
            this.Salto_Linea();
            Parrafo.add(this.Observacion); 
            documento.add(Parrafo);
            Parrafo.clear();
            this.Salto_Linea();
            documento.add(new Paragraph(" Recomendaciones:",FontFactory.getFont("tahoma",   
				14,Font.BOLD,BaseColor.BLACK))); 
            this.Salto_Linea();
            Parrafo.add(this.RecomendacionAuditor);
            documento.add(Parrafo); 
            Parrafo.clear();
            
            this.Salto_Linea();
            Parrafo.add(this.RecomendacionCierre);
            documento.add(Parrafo); 
           
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
    
    public void setInforme(){
        try{
            String StrSql ;
            StrSql = "SELECT Alcances, Objetivos,Observaciones, RecomendacionAuditor, RecomendacionCierre, RecomendacionAutorizacion, Descripcion, TipoInforme, Area "
                    +" FROM JInforme WHERE idPlan ="+this.IdPlan;
            ResultSet  Result = JBase_Datos.SQL_QRY(Connection_BD,StrSql);	
	    while(Result.next()){
                this.Alcance     = Result.getString(1);
                this.Objetivo    = Result.getString(2);
                this.Observacion = Result.getString(3);
                this.RecomendacionAuditor = Result.getString(4);
                this.RecomendacionCierre  = Result.getString(5);
                this.Proceso = Result.getString(7);
                this.TipoInforme = Result.getString(8);
                this.Area = Result.getString(9);
            }
        }catch(Exception e ){
            JOptionPane.showMessageDialog(null,"JReport: "+e.getMessage());
        }
    }
    
}
