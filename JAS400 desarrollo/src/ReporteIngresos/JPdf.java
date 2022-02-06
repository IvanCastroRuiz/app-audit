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
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;
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
 * Responsble del Cambio : GARYN JAIRO CARRILLO CABALLERO -> AUDITOR DE SISTEMA
 */
class JPdf {
    private Document documento;
    
    private Connection Connection_BD;
    private JConection JBase_Datos;
    private Calendar fecha;
    private String Fecha;
    private String Usuario;
    private String Turno, User;
    private SimpleDateFormat Formato = new SimpleDateFormat ("dd/MMMM/yy HH:mm:ss");
    private Date XFecha;
    private String NumeroFormato = "###,###,###,###.##";
    private DecimalFormat JFormato ;
    private double AportesCheque;              
    private Vector Cheques , Centro, PUsuario ;
    public JPdf(JConection JBase_Datos2 , Connection Cn3, String Fechax, String Usuariox){
        Cheques = new Vector();
        Centro = new Vector();
        PUsuario = new Vector();
        JFormato= new DecimalFormat(NumeroFormato);
        XFecha = new Date(5);
        this.JBase_Datos = JBase_Datos2;
        this.Usuario = Usuariox;
        documento = new Document(PageSize.B2.rotate(),10,3,20,20);
        //(Margen_Izquierdo, Margen_Derecho, Margen_Superior, Margen_Inferior)
        this.Fecha = Fechax;
        this.Connection_BD = Cn3;
        this.CreatePdf();
        
    }
        
    public void CreatePdf(){
        
       try{
           
            String NombreArchivo = "Control_Ingreso"+"_"+this.Fecha;
            
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
            //Parrafo.clear();
            //Parrafo.add("           Fecha   : "+ Formato.format(fecha.DATE));
            //documento.add(Parrafo);
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
            Parrafo.add("SISTEMA PARA CONTROL DE INGRESO"); 
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
    

    public double getBusquedaCheque(String Usuario, String Turno, String Fecha, String Caja, String CCentro_Costo, String FechaIngreso){
        ResultSet rs = null;
        double Vlr = 0;
        try{
            String StrSql = "SELECT CHESEC, SUM(CHEVAL) FROM RECAUDOS.PCHEQUE1 AS T1, RECAUDOS.PAPORTES AS T2 "
                            +"WHERE T1.USUCOD ='"+Usuario.trim() +"' AND T1.TURCOD='"+Turno+"' AND T1.PLAFER='"+Fecha+"' AND T1.CAJCOD = '"+Caja+"' "
                            + " AND T1.USUCOD= T2.USUCOD AND T1.TURCOD=T2.TURCOD AND T1.PLAFER= T2.PLAFER AND "
                            + "  T1.PLAHOR=T2.PLAHOR AND T1.CAJCOD = T2.CAJCOD AND T2.CENCOD="+CCentro_Costo+" AND PLAFEI='"+FechaIngreso+"'"
                            +" GROUP BY T1.CHESEC";   
            System.out.println(" "+StrSql);
            rs = JBase_Datos.SQL_QRY(Connection_BD,StrSql);
            if(rs.next()){
                Vlr = Double.parseDouble(rs.getString(2));
                
                if ((Centro.contains(CCentro_Costo)) && (Cheques.contains(rs.getString(1))) &&(PUsuario.contains(Caja))){
                    Vlr=0;
                }
                this.Centro.add(CCentro_Costo);
                this.Cheques.add(rs.getString(1));
                this.PUsuario.add(Caja);
            }
            System.out.println(" "+Vlr);
            
        }catch(Exception e ){
            JOptionPane.showMessageDialog(null, "JReport: "+e.getMessage());
        }
        return Vlr;
    }
    public void getAportesCheque(String Usuario, String Turno, String Fecha, String Caja){
             ResultSet rs = null;
            double Vlr = 0;
            this.AportesCheque = 0;
            try{
                String StrSql = "SELECT CHESEC, SUM(CHEVAL) FROM RECAUDOS.PCHEQUE1 "
                                +"WHERE USUCOD ='"+Usuario.trim() +"' AND TURCOD='"+Turno+"' AND PLAFER='"+Fecha+"' AND CAJCOD = '"+Caja+"' "
                                +" AND BANCOD = 2 "
                                +"GROUP BY CHESEC";   
                if(Caja.equals("9960")){
                    System.out.println(StrSql);
                }
                rs = JBase_Datos.SQL_QRY(Connection_BD,StrSql);
                if(rs.next()){
                    Vlr = Double.parseDouble(rs.getString(2));
                }
                this.AportesCheque = Vlr;
            }catch(Exception e ){
                JOptionPane.showMessageDialog(null, "JReport: "+e.getMessage());
            }
    }
    
    public void getBusqueda(){
        try{
            String StrSql = "SELECT T01.CENCOD,T02.CENNOM,T01.PLAEST,T01.PLCOSU,T01.PLCRSU,T01.PLTATO,T01.PLEFSU,T01.PLAFAL,  "+
                            " T01.USUCOD, T01.TURCOD,T01.PLAFER,T01.PLAHOR, T01.CAJCOD, T01.PLSESU,T01.APOTOT,T01.PLCHSU "+
                            " FROM RECAUDOS.LAPORT1 as T01 , RECAUDOS.CENTRO as T02"+
                            " WHERE T01.CENCOD = T02.CENCOD AND T01.PLAFEI = '"+this.Fecha+"'"
                            + " ORDER BY T01.CENCOD ASC";
            System.out.println("BUSQUEDA GENERAL "+StrSql);
            ResultSet  Result = JBase_Datos.SQL_QRY(Connection_BD,StrSql);
            float  AnchoCelda []  = new float[13]; 
            AnchoCelda [0] = (float) 18.0;
            AnchoCelda [1] = (float) 60.0;
            AnchoCelda [2] = (float) 25.0;
            AnchoCelda [3] = (float) 25.0;
            AnchoCelda [4] = (float) 25.0;
            AnchoCelda [5] = (float) 25.0;
            AnchoCelda [6] = (float) 25.0;
            AnchoCelda [7] = (float) 25.0;
            AnchoCelda [8] = (float) 35.0;
            AnchoCelda [9] = (float) 35.0;
            AnchoCelda [10] = (float)20.0;
            AnchoCelda [11] = (float)20.0;
            AnchoCelda [12] = (float)20.0;
            
            PdfPTable tabla = new PdfPTable(13);
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
            
            Paragraph Parrafo6 = new Paragraph();
            Parrafo6.setAlignment(Element.ALIGN_CENTER);
            Parrafo6.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo7 = new Paragraph();
            Parrafo7.setAlignment(Element.ALIGN_CENTER);
            Parrafo7.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo8 = new Paragraph();
            Parrafo8.setAlignment(Element.ALIGN_CENTER);
            Parrafo8.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo9 = new Paragraph();
            Parrafo9.setAlignment(Element.ALIGN_CENTER);
            Parrafo9.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo10 = new Paragraph();
            Parrafo10.setAlignment(Element.ALIGN_CENTER);
            Parrafo10.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo11 = new Paragraph();
            Parrafo11.setAlignment(Element.ALIGN_CENTER);
            Parrafo11.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo12 = new Paragraph();
            Parrafo12.setAlignment(Element.ALIGN_CENTER);
            Parrafo12.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo13 = new Paragraph();
            Parrafo13.setAlignment(Element.ALIGN_CENTER);
            Parrafo13.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo14 = new Paragraph();
            Parrafo14.setAlignment(Element.ALIGN_CENTER);
            Parrafo14.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Parrafo.add(" ");
            Celda = new PdfPCell ( Parrafo);
            tabla.addCell(Celda);
            
            Parrafo2.clear();
            Parrafo2.add("Nombre del Centro");
            PdfPCell Celda2 = new PdfPCell ( Parrafo2);
            tabla.addCell(Celda2);
            
            Parrafo8.clear();
            Parrafo8.add("Total Cheque");
            PdfPCell Celda8 = new PdfPCell ( Parrafo8);
            tabla.addCell(Celda8);
            
            Parrafo3.clear();
            Parrafo3.add("Total Tar. MultServicio");
            Celda.addElement(Parrafo3);
            PdfPCell Celda3 = new PdfPCell ( Parrafo3);
            tabla.addCell(Celda3);
            
            Parrafo4.clear();
            Parrafo4.add("Total Creditos");
            Celda.addElement(Parrafo4);
            PdfPCell Celda4 = new PdfPCell ( Parrafo4);
            tabla.addCell(Celda4);
            
            Parrafo5.clear();
            Parrafo5.add("Total Tarjetas");
            Celda.addElement(Parrafo5);
            PdfPCell Celda5 = new PdfPCell ( Parrafo5);
            tabla.addCell(Celda5);
            
            Parrafo6.clear();
            Parrafo6.add("Total Efectivo");
            Celda.addElement(Parrafo6);
            PdfPCell Celda6 = new PdfPCell ( Parrafo6);
            tabla.addCell(Celda6);
            
            Parrafo7.clear();
            Parrafo7.add("Faltante-Sobrante");
            PdfPCell Celda7 = new PdfPCell ( Parrafo7);
            tabla.addCell(Celda7);
            
            Parrafo9.clear();
            Parrafo9.add("TOTAL");
            PdfPCell Celda9 = new PdfPCell ( Parrafo9);
            tabla.addCell(Celda9);
            
            Parrafo10.clear();
            Parrafo10.add("Total de Servicios");
            PdfPCell Celda10 = new PdfPCell ( Parrafo10);
            tabla.addCell(Celda10);
            
            Parrafo11.clear();
            Parrafo11.add("Total P.O.S.");
            PdfPCell Celda11= new PdfPCell ( Parrafo11);
            tabla.addCell(Celda11);
            
            Parrafo12.clear();
            Parrafo12.add("Total Aporte");
            PdfPCell Celda12= new PdfPCell ( Parrafo12);
            tabla.addCell(Celda12);
            
            Parrafo13.clear();
            Parrafo13.add("Total Cheques Retenidos");
            PdfPCell Celda13= new PdfPCell ( Parrafo13);
            tabla.addCell(Celda13);
            
            double TarMultiServicio , TotCredito, TotTarjerta, TotEfectivo, FaltanteSobrante,TotCheque, TotServicio, TotPos, TotChequeRetenido, TotAporte;
            String CentroCosto = "" , CentroCostoActual = "", CodigoCentro="";
            
            double TmpMultiServicio,TmpCredito,TmpTarjeta,tmpEfectivo,TmpFaltanteSobrante, TmpCheque, TmpServicios, TmpPos, TmpAporte, TmpChequeRetenido;
            
            double GMultiServicio,GCredito,GTarjeta,GEfectivo,GFaltanteSobrante, GCheque,GServicio, GPos , GSuma, GAporte, GChequeRetenido;
            
            TarMultiServicio = 0;
            TotCredito = 0;
            TotTarjerta = 0 ;
            TotEfectivo = 0;
            TotCheque = 0;
            FaltanteSobrante = 0;
            TotServicio = 0;
            TotPos = 0;
            TotAporte = 0;
            TotChequeRetenido = 0;
            
            TmpMultiServicio = 0;
            TmpCredito = 0;
            TmpTarjeta = 0;
            tmpEfectivo = 0;
            TmpFaltanteSobrante = 0;
            TmpCheque = 0;
            TmpServicios = 0;
            TmpPos = 0; 
            TmpAporte = 0;
            TmpChequeRetenido =0;
            
            GMultiServicio = 0;
            GCredito = 0;
            GTarjeta = 0;
            GEfectivo = 0;
            GFaltanteSobrante = 0;
            GCheque = 0;
            GServicio = 0;
            GPos = 0;
            GSuma = 0 ;
            GAporte = 0;
            GChequeRetenido = 0;
            
            while(Result.next()){
                CodigoCentro = Result.getString(1);
                
                CentroCostoActual = Result.getString(2);
                
                if (CentroCosto.equals("")){
                    CentroCosto = CentroCostoActual;
                }
                
                if(!CentroCosto.equals(CentroCostoActual)){
                    this.Centro.clear();
                    this.Cheques.clear();
                    this.PUsuario.clear();
                    Paragraph Parrafo99 = new Paragraph();
                    Parrafo99.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo99.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
                    Paragraph Parrafo100 = new Paragraph();
                    Parrafo100.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo100.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
                    Paragraph Parrafo101 = new Paragraph();
                    Parrafo101.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo101.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
                    Paragraph Parrafo102 = new Paragraph();
                    Parrafo102.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo102.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
                    Paragraph Parrafo103 = new Paragraph();
                    Parrafo103.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo103.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
                    
                    Paragraph Parrafo104 = new Paragraph();
                    Parrafo104.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo104.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
                    Paragraph Parrafo105 = new Paragraph();
                    Parrafo105.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo105.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
                    Paragraph Parrafo106 = new Paragraph();
                    Parrafo106.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo106.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

                    Paragraph Parrafo107 = new Paragraph();
                    Parrafo107.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo107.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

                    Paragraph Parrafo108 = new Paragraph();
                    Parrafo108.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo108.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

                    Paragraph Parrafo109 = new Paragraph();
                    Parrafo109.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo109.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

                    Paragraph Parrafo110 = new Paragraph();
                    Parrafo110.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo110.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

                    Paragraph Parrafo111 = new Paragraph();
                    Parrafo111.setAlignment(Element.ALIGN_RIGHT);
                    Parrafo111.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
                    Parrafo99.clear();
                    Parrafo99.add("TOTAL");
                    PdfPCell Celda99 = new PdfPCell ( Parrafo99);
                    tabla.addCell(Celda99);
                    
                    Parrafo100.clear();
                    Parrafo100.add(CentroCosto);
                    PdfPCell Celda100 = new PdfPCell ( Parrafo100);
                    tabla.addCell(Celda100);
            
                    
                    Parrafo101.clear();
                    Parrafo101.add(""+JFormato.format(TotCheque));
                    PdfPCell Celda101 = new PdfPCell ( Parrafo101);
                    Celda101.addElement(Parrafo101);
                    tabla.addCell(Celda101);
                    
                    Parrafo102.clear();
                    Parrafo102.add(""+JFormato.format(TarMultiServicio));
                    PdfPCell Celda102 = new PdfPCell ( Parrafo102);
                    Celda101.addElement(Parrafo102);
                    tabla.addCell(Celda102);
                    
                    Parrafo103.clear();
                    Parrafo103.add(""+JFormato.format(TotCredito));
                    PdfPCell Celda103 = new PdfPCell ( Parrafo103);
                    Celda103.addElement(Parrafo103);
                    tabla.addCell(Celda103);
                    
                    Parrafo104.clear();
                    Parrafo104.add(""+JFormato.format(TotTarjerta));
                    PdfPCell Celda104 = new PdfPCell ( Parrafo104);
                    Celda104.addElement(Parrafo104);
                    tabla.addCell(Celda104);
                    
                    Parrafo105.clear();
                    Parrafo105.add(""+JFormato.format(TotEfectivo));
                    PdfPCell Celda105 = new PdfPCell ( Parrafo105);
                    Celda105.addElement(Parrafo105);
                    tabla.addCell(Celda105);
                    
                    Parrafo106.clear();
                    Parrafo106.add(""+JFormato.format(FaltanteSobrante));
                    PdfPCell Celda106 = new PdfPCell ( Parrafo106);
                    Celda106.addElement(Parrafo106);
                    tabla.addCell(Celda106);
                    
                    
                    double Suma = TotCheque+TarMultiServicio+TotCredito+TotTarjerta+TotEfectivo;

                    Parrafo107.clear();
                    Parrafo107.add(""+JFormato.format(Suma));
                    PdfPCell Celda107 = new PdfPCell ( Parrafo107);
                    Celda106.addElement(Parrafo107);
                    tabla.addCell(Celda107);
                    
                    Parrafo108.clear();
                    Parrafo108.add(""+JFormato.format(TotServicio));
                    PdfPCell Celda108 = new PdfPCell ( Parrafo108);
                    Celda108.addElement(Parrafo108);
                    tabla.addCell(Celda108);
                    
                    
                    Parrafo109.clear();
                    Parrafo109.add(""+JFormato.format(TotPos));
                    PdfPCell Celda109 = new PdfPCell ( Parrafo109);
                    Celda108.addElement(Parrafo109);
                    tabla.addCell(Celda109);
                    
                    Parrafo110.clear();
                    Parrafo110.add(""+JFormato.format(TotAporte));
                    PdfPCell Celda110 = new PdfPCell ( Parrafo110);
                    Celda110.addElement(Parrafo110);
                    tabla.addCell(Celda110);
                    
                    Parrafo111.clear();
                    Parrafo111.add(""+JFormato.format(TotChequeRetenido));
                    PdfPCell Celda111 = new PdfPCell ( Parrafo111);
                    Celda111.addElement(Parrafo111);
                    tabla.addCell(Celda111);
                    
                    GMultiServicio = GMultiServicio+TarMultiServicio;
                    GCredito = GCredito +TotCredito;
                    GTarjeta = GTarjeta+TotTarjerta;
                    GEfectivo = GEfectivo+TotEfectivo;
                    GFaltanteSobrante = GFaltanteSobrante+FaltanteSobrante;
                    GCheque = GCheque +TotCheque;
                    GServicio = GServicio+TotServicio;
                    GPos = + GPos+TotPos;
                    GSuma = GSuma+Suma;
                    GAporte =  GAporte+TotAporte;
                    GChequeRetenido = GChequeRetenido+TotChequeRetenido;
                    
                    TarMultiServicio = 0;
                    TotCredito = 0;
                    TotTarjerta = 0 ;
                    TotEfectivo = 0;
                    FaltanteSobrante = 0;
                    TotCheque = 0;
                    TotServicio = 0;
                    TotPos = 0;
                    TotAporte=0;
                    TotChequeRetenido = 0;
                    CentroCosto = CentroCostoActual;
                    
                }
                
                TmpMultiServicio = Double.parseDouble(Result.getString(4));
                TmpCredito  = Double.parseDouble(Result.getString(5));
                TmpTarjeta  = Double.parseDouble(Result.getString(6));
                tmpEfectivo = Double.parseDouble(Result.getString(7)); 
                TmpFaltanteSobrante = Double.parseDouble(Result.getString(8));
                TmpServicios = Double.parseDouble(Result.getString(14));
                TmpPos = Double.parseDouble(Result.getString(15));
                String JUsuario = Result.getString(9);
                String JTurno = Result.getString(10);
                String JFecha = Result.getString(11);
                String JCaja  = Result.getString(13);
                TmpChequeRetenido = this.getBusquedaCheque(JUsuario,JTurno,JFecha,JCaja, CodigoCentro,this.Fecha.trim());
                
                TmpAporte =Double.parseDouble(Result.getString(15));
                //Version 1.1
                AportesCheque = 0;
                this.getAportesCheque(JUsuario,JTurno,JFecha,JCaja);
                TmpAporte = TmpAporte + AportesCheque;

                TmpCheque = Double.parseDouble(Result.getString(16));
               
                TarMultiServicio = TarMultiServicio + TmpMultiServicio;
                TotCredito = TotCredito + TmpCredito ;
                TotTarjerta = TotTarjerta + TmpTarjeta;
                TotEfectivo = TotEfectivo + tmpEfectivo;
                TotCheque = TotCheque + TmpCheque;
                FaltanteSobrante = FaltanteSobrante + TmpFaltanteSobrante;
                TotServicio = TotServicio + TmpServicios;
                TotPos = +TotPos +TmpPos;
                TotAporte = TotAporte+TmpAporte;
                TotChequeRetenido = TotChequeRetenido+TmpChequeRetenido;
                
                tabla.addCell("");
                tabla.addCell(CentroCostoActual);
                tabla.addCell(""+JFormato.format(TmpCheque));
                tabla.addCell(""+JFormato.format(TmpMultiServicio));
                tabla.addCell(""+JFormato.format(TmpCredito));
                tabla.addCell(""+JFormato.format(TmpTarjeta));
                tabla.addCell(""+JFormato.format(tmpEfectivo));
                tabla.addCell(""+JFormato.format(TmpFaltanteSobrante));
                double Suma = TmpCheque+TmpMultiServicio+TmpCredito+TmpTarjeta+tmpEfectivo;
                tabla.addCell(""+JFormato.format(Suma));
                tabla.addCell(""+JFormato.format(TmpServicios));
                tabla.addCell(""+JFormato.format(TmpPos));
                tabla.addCell(""+JFormato.format(TmpAporte));
                tabla.addCell(""+JFormato.format(TmpChequeRetenido));
                
            }
            
            Paragraph Parrafo99x = new Paragraph();
            Parrafo99x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo99x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo100x = new Paragraph();
            Parrafo100x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo100x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo101x = new Paragraph();
            Parrafo101x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo101x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
            Paragraph Parrafo102x = new Paragraph();
            Parrafo102x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo102x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
            Paragraph Parrafo103x = new Paragraph();
            Parrafo103x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo103x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
            Paragraph Parrafo104x = new Paragraph();
            Parrafo104x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo104x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
            Paragraph Parrafo105x = new Paragraph();
            Parrafo105x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo105x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
            Paragraph Parrafo106x = new Paragraph();
            Parrafo106x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo106x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo107x = new Paragraph();
            Parrafo107x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo107x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo108x = new Paragraph();
            Parrafo108x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo108x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo109x = new Paragraph();
            Parrafo109x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo109x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo110x = new Paragraph();
            Parrafo110x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo110x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo111x = new Paragraph();
            Parrafo111x.setAlignment(Element.ALIGN_RIGHT);
            Parrafo111x.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Parrafo99x.clear();
            Parrafo99x.add("TOTAL");
            PdfPCell Celda99x = new PdfPCell ( Parrafo99x);
            tabla.addCell(Celda99x);
            
            Parrafo100x.clear();
            Parrafo100x.add(CentroCosto);
            PdfPCell Celda100x = new PdfPCell ( Parrafo100x);
            tabla.addCell(Celda100x);
            
            Parrafo101x.clear();
            Parrafo101x.add(JFormato.format(TotCheque));
            PdfPCell Celda101x = new PdfPCell ( Parrafo101x);
            tabla.addCell(Celda101x);
            
            Parrafo102x.clear();
            Parrafo102x.add(""+JFormato.format(TarMultiServicio));
            PdfPCell Celda102x = new PdfPCell ( Parrafo102x);
            tabla.addCell(Celda102x);
            
            Parrafo103x.clear();
            Parrafo103x.add(""+JFormato.format(TotCredito));
            PdfPCell Celda103x = new PdfPCell ( Parrafo103x);
            tabla.addCell(Celda103x);            
            
            Parrafo104x.clear();
            Parrafo104x.add(""+JFormato.format(TotTarjerta));
            PdfPCell Celda104x = new PdfPCell ( Parrafo104x);
            tabla.addCell(Celda104x);            
            
            Parrafo105x.clear();
            Parrafo105x.add(""+JFormato.format(TotEfectivo));
            PdfPCell Celda105x = new PdfPCell ( Parrafo105x);
            tabla.addCell(Celda105x);             
            
            Parrafo106x.clear();
            Parrafo106x.add(""+JFormato.format(FaltanteSobrante));
            PdfPCell Celda106x = new PdfPCell ( Parrafo106x);
            tabla.addCell(Celda106x);             
            
            double Suma = TotCheque+TarMultiServicio+TotCredito+TotTarjerta+TotEfectivo;
            Parrafo107x.clear();
            Parrafo107x.add(""+JFormato.format(Suma));
            PdfPCell Celda107x = new PdfPCell ( Parrafo107x);
            tabla.addCell(Celda107x);             
            
            Parrafo108x.clear();
            Parrafo108x.add(""+JFormato.format(TotServicio));
            PdfPCell Celda108x = new PdfPCell ( Parrafo108x);
            tabla.addCell(Celda108x);           
            
            Parrafo109x.clear();
            Parrafo109x.add(""+JFormato.format(TotPos));
            PdfPCell Celda109x = new PdfPCell ( Parrafo109x);
            tabla.addCell(Celda109x);                       
            
            Parrafo110x.clear();
            Parrafo110x.add(""+JFormato.format(TotAporte));
            PdfPCell Celda110x = new PdfPCell ( Parrafo110x);
            tabla.addCell(Celda110x);                       
            
            Parrafo111x.clear();
            Parrafo111x.add(""+JFormato.format(TotChequeRetenido));
            PdfPCell Celda111x = new PdfPCell ( Parrafo111x);
            tabla.addCell(Celda111x);                       
            
            GMultiServicio = GMultiServicio+TarMultiServicio;
            GCredito = GCredito +TotCredito;
            GTarjeta = GTarjeta+TotTarjerta;
            GEfectivo = GEfectivo+TotEfectivo;
            GFaltanteSobrante = GFaltanteSobrante+FaltanteSobrante;
            GCheque = GCheque +TotCheque;
            GServicio = GServicio+TotServicio;
            GPos = + GPos+TotPos;
            
            GAporte =  GAporte+TotAporte;
            GChequeRetenido = GChequeRetenido+TotChequeRetenido;            
            
            
            Paragraph Parrafo99 = new Paragraph();
            Parrafo99.setAlignment(Element.ALIGN_RIGHT);
            Parrafo99.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo100 = new Paragraph();
            Parrafo100.setAlignment(Element.ALIGN_RIGHT);
            Parrafo100.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
            
            Paragraph Parrafo101 = new Paragraph();
            Parrafo101.setAlignment(Element.ALIGN_RIGHT);
            Parrafo101.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
            Paragraph Parrafo102 = new Paragraph();
            Parrafo102.setAlignment(Element.ALIGN_RIGHT);
            Parrafo102.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
            Paragraph Parrafo103 = new Paragraph();
            Parrafo103.setAlignment(Element.ALIGN_RIGHT);
            Parrafo103.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
                    
            Paragraph Parrafo104 = new Paragraph();
            Parrafo104.setAlignment(Element.ALIGN_RIGHT);
            Parrafo104.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
            Paragraph Parrafo105 = new Paragraph();
            Parrafo105.setAlignment(Element.ALIGN_RIGHT);
            Parrafo105.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                    
            Paragraph Parrafo106 = new Paragraph();
            Parrafo106.setAlignment(Element.ALIGN_RIGHT);
            Parrafo106.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo107 = new Paragraph();
            Parrafo107.setAlignment(Element.ALIGN_RIGHT);
            Parrafo107.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo108 = new Paragraph();
            Parrafo108.setAlignment(Element.ALIGN_RIGHT);
            Parrafo108.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo109 = new Paragraph();
            Parrafo109.setAlignment(Element.ALIGN_RIGHT);
            Parrafo109.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo110 = new Paragraph();
            Parrafo110.setAlignment(Element.ALIGN_RIGHT);
            Parrafo110.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));

            Paragraph Parrafo111 = new Paragraph();
            Parrafo111.setAlignment(Element.ALIGN_RIGHT);
            Parrafo111.setFont(FontFactory.getFont("tahoma",14,Font.BOLD,BaseColor.BLACK));
                       
            Parrafo99.clear();
            Parrafo99.add("TOTAL");
            PdfPCell Celda99 = new PdfPCell ( Parrafo99);
            tabla.addCell(Celda99);
            tabla.addCell("");
            
            Parrafo100.clear();
            Parrafo100.add(""+JFormato.format(GCheque));
            PdfPCell Celda100 = new PdfPCell ( Parrafo100);
            tabla.addCell(Celda100);            
            
            Parrafo101.clear();
            Parrafo101.add(""+JFormato.format(GMultiServicio));
            PdfPCell Celda101 = new PdfPCell ( Parrafo101);
            tabla.addCell(Celda101);            
            
            Parrafo102.clear();
            Parrafo102.add(""+JFormato.format(GCredito));
            PdfPCell Celda102 = new PdfPCell ( Parrafo102);
            tabla.addCell(Celda102);            
            
            Parrafo103.clear();
            Parrafo103.add(""+JFormato.format(GTarjeta));
            PdfPCell Celda103 = new PdfPCell ( Parrafo103);
            tabla.addCell(Celda103);            

            Parrafo104.clear();
            Parrafo104.add(""+JFormato.format(GEfectivo));
            PdfPCell Celda104 = new PdfPCell (Parrafo104);
            tabla.addCell(Celda104);            
            
            Parrafo105.clear();
            Parrafo105.add(""+JFormato.format(GFaltanteSobrante));
            PdfPCell Celda105 = new PdfPCell ( Parrafo105);
            tabla.addCell(Celda105);            
            
            GSuma = GSuma+Suma;
            
            Parrafo106.clear();
            Parrafo106.add(""+JFormato.format(GSuma));
            PdfPCell Celda106 = new PdfPCell ( Parrafo106);
            tabla.addCell(Celda106);             
            
            Parrafo107.clear();
            Parrafo107.add(""+JFormato.format(GServicio));
            PdfPCell Celda107 = new PdfPCell ( Parrafo107);
            tabla.addCell(Celda107);             
            
            Parrafo108.clear();
            Parrafo108.add(""+JFormato.format(GPos));
            PdfPCell Celda108 = new PdfPCell ( Parrafo108);
            tabla.addCell(Celda108);                         
            
            Parrafo109.clear();
            Parrafo109.add(""+JFormato.format(GAporte));
            PdfPCell Celda109 = new PdfPCell ( Parrafo109);
            tabla.addCell(Celda109);                         
            
            Parrafo110.clear();
            Parrafo110.add(""+JFormato.format(GChequeRetenido));
            PdfPCell Celda110 = new PdfPCell ( Parrafo110);
            tabla.addCell(Celda110);                         
            
            documento.add(tabla);
            
            Result.close();
            //JBase_Datos.Close_BD(Connection_BD);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
