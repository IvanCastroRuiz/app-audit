package Subsidio;


import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class JSubsidio {
 
    private Vector Importe, NumeroCheque, Fecha,Periodo, DocumentoTrabajado, DocumentoBeneficiario, NombreBeneficiario;
    private Vector ConceptoLiquidacion;
    private int PosicionActual;
    
    private JConection JBase_Datos;
    private Connection Cn;      
    
    
    public JSubsidio(JEmpleados DTrabajador ,int DPeriodo, int DFecha, double DNumeroCheque , JConection JBase_Datos3, Connection Cn2){
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        
        this.PosicionActual =  -1;
        Importe = new Vector();
        NumeroCheque = new Vector();
        Fecha=new Vector();
        Periodo= new Vector();
        DocumentoTrabajado=new Vector();
        this.DocumentoBeneficiario = new Vector();
        NombreBeneficiario= new Vector();
        ConceptoLiquidacion = new Vector();
        
        this.DocumentoTrabajado.addElement(DTrabajador.getDocumento());
        this.Periodo.addElement(DPeriodo);
        this.Fecha.addElement(""+DFecha);
        this.NumeroCheque.addElement(DNumeroCheque);
        this.getInformacion(1);
    }
    public void getInformacion(int tipo){
        String StrSql = "";
        if (tipo==1) {
            StrSql = "SELECT pfeliq, ppeliq, pceliq, pctliq, pccliq, pdbliq, pnbliq,pchliq,pcoliq,pvaliq  FROM SUBSILIB.LIQUIPAG WHERE "+
                     " pchliq ="+this.NumeroCheque.elementAt(0) +" AND ppeliq ="+this.Periodo.elementAt(0)+
                     " AND pfeliq ="+this.Fecha.elementAt(0)+" AND pctliq ="+this.DocumentoTrabajado.elementAt(0);
        }
        try {
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            int Primer = 1;
            while(rs.next()) {
                if (Primer == 1) {
                    Primer = 0;
                }else{
                    this.Fecha.addElement(rs.getString("pfeliq"));
                    this.Periodo.addElement(rs.getInt("ppeliq"));
                    this.NumeroCheque.addElement(rs.getDouble("pchliq"));
                    this.DocumentoTrabajado.addElement(rs.getInt("pctliq"));
                }
                
                try{
                    this.DocumentoBeneficiario.addElement(rs.getString("pdbliq"));
                }catch(Exception e ){
                    this.DocumentoBeneficiario.addElement(0);
                }
                try{
                    this.NombreBeneficiario.addElement(rs.getString("pnbliq"));
                }catch(Exception e ){
                    this.NombreBeneficiario.addElement(" ");
                }
                try{
                    this.Importe.addElement(rs.getDouble("pvaliq"));
                }catch(Exception e ){
                    this.Importe.addElement(0.0);
                }
                try{
                    this.ConceptoLiquidacion.addElement(rs.getInt("pcoliq"));
                }catch(Exception e ){
                    this.ConceptoLiquidacion.addElement(0);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class: JSubsidio: getInformacion()" +e.getMessage());
        }
    }
    public String getPeriodo(){
        return ""+this.Periodo.elementAt(this.PosicionActual);
    }
    public String getFecha(){
        return ""+this.Fecha.elementAt(this.PosicionActual);
    }
    public String getDocumentoTrabajador(){
        return ""+this.DocumentoTrabajado.elementAt(this.PosicionActual);
    }
    public String getDocumentoBenefiaciado(){
        return ""+this.DocumentoBeneficiario.elementAt(this.PosicionActual);
    }
    public String getConcepto(){
        return ""+this.ConceptoLiquidacion.elementAt(this.PosicionActual);
    }
    public String getValor(){
        return ""+this.Importe.elementAt(this.PosicionActual);
    }
    public int getCantidad(){
        return this.NumeroCheque.size();
    }
    public String getNumeroCheque(){
        return ""+this.NumeroCheque.elementAt(this.PosicionActual);
    }
    public String getNombreBeneficiario(){
        return ""+this.NombreBeneficiario.elementAt(this.PosicionActual);
    }
    public boolean getNext(){
        this.PosicionActual ++;
        if( this.PosicionActual < this.getCantidad()){
            return true;    
        }else{
            return false;
        }
    }
    public void setFirst(){
        this.PosicionActual = -1;
    }
    public void setEnd(){
        this.PosicionActual = this.NumeroCheque.size()-1;
    }
    public boolean getExiste(){
        if ( this.ConceptoLiquidacion.size()>0){
            return true;
        }else{
            return false;
        }
    }
}
