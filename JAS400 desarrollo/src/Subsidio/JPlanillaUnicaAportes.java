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
public class JPlanillaUnicaAportes {
    
    private int Documento;
    private int Empresa;
    private JConection JBase_Datos;
    private Connection Cn;  
    private int PosicionActual;
    
    
    private Vector VPeriodo, Fecha, CodigoEmpresa,NombreEmpresa, ValorAporte;
    private Vector  Dias, NroPlanilla, TipoPlanilla, IBCD, SENA, Porcentaje,Salpla,Consecutivo, ValorRa;
    private Vector FecCer,NroAfi,Operad,CerSec, NitEmp,ApoExs,ApoLey,DiasMo;
    private Vector Codpla, Nompla, Apepla, Ingres,Retiro, VPS, VTS,STC,ITC,LMT,VACA,ITA;
    private Vector PERDET, PORCA, DIAARP, FECING, FECRET, FEIVSP, FEISLN, FEFSLN, FEIIGE,FEFIGE, FEILMA,FEFLMA;
    private Vector FEIVAC, FEFVAC, FEIVCT, FEFVCT,FEIIRL, FEFIRL, SECUEN,PLAASO,PLAANT;
    
    public JPlanillaUnicaAportes(int XDocumento, JConection JBase_Datos3, Connection Cn2){
            this.JBase_Datos = JBase_Datos3;
            this.Cn = Cn2;
            this.Documento = XDocumento;
            VPeriodo = new Vector();
            Fecha= new Vector();
            CodigoEmpresa = new Vector();
            ValorAporte = new Vector();
            NombreEmpresa = new Vector();
            TipoPlanilla = new Vector();
            NroPlanilla = new Vector();
            Dias = new Vector();
            
            IBCD = new Vector();
            Salpla= new Vector();
            Consecutivo= new Vector();
            FecCer = new Vector();
            NroAfi = new Vector();
            Operad = new Vector();
            NitEmp=new Vector();
            ApoExs = new Vector();
            ApoLey = new Vector();
            DiasMo = new Vector();
            
            Codpla = new Vector();
            Nompla = new Vector();
            Apepla = new Vector();
            Ingres = new Vector();
            Retiro= new Vector();
            VPS = new Vector();
            VTS= new Vector();
            STC = new Vector();
            ITC = new Vector();
            LMT = new Vector();
            VACA = new Vector();
            ITA = new Vector();
            
            
            PERDET= new Vector();
            PORCA = new Vector();
            DIAARP= new Vector();
            FECING = new Vector();
            FECRET = new Vector();
            FEIVSP = new Vector();
            FEISLN = new Vector();
            FEFSLN = new Vector();
            FEIIGE = new Vector();
            FEFIGE = new Vector();
            FEILMA = new Vector();
            FEFLMA = new Vector();
            
            
            FEIVAC = new Vector();
            FEFVAC = new Vector();
            FEIVCT = new Vector();
            FEFVCT = new Vector();
            FEIIRL = new Vector();
            FEFIRL = new Vector();
            SECUEN = new Vector();
            PLAASO = new Vector();
            PLAANT = new Vector();
            
            
            
            
            this.getInformacion();
    }
    public void getInformacion(){
        try{
             String StrSql = "SELECT *  FROM SUBSILIB.CERPLA AS t1, SUBSILIB.CERPLAD AS t2 WHERE t1.NROPLA=t2.PLANIL "
                     + " and t2.cedpla="+this.Documento 
                     +" order by perapo desc";
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             while (rs.next()){
                 
//                try{
//                }catch(Exception e){
//                } 
//
//                try{
//                }catch(Exception e){
//                } 
//
//                try{
//                }catch(Exception e){
//                } 
//                 
//                try{
//                }catch(Exception e){
//                } 
//                 
//                try{
//                }catch(Exception e){
//                } 
//                 
//                try{
//                }catch(Exception e){
//                } 
//                 
//                try{
//                    FEFLMA.add(rs.getString("FEFLMA"));
//                }catch(Exception e){
//                    FEFLMA.add("");
//                }
//                try{
//                    FEILMA.add(rs.getString("FEILMA"));
//                }catch(Exception e){
//                    FEILMA.add("");
//                }
//                try{
//                    FEFIGE.add(rs.getString("FEFIGE"));
//                }catch(Exception e){
//                    FEFIGE.add("");
//                }
//                try{
//                    FEIIGE.add(rs.getString("FEIIGE"));
//                }catch(Exception e){
//                    FEIIGE.add("");
//                }
//                try{
//                    FEFSLN.add(rs.getString("FEFSLN"));
//                }catch(Exception e){
//                    FEFSLN.add("");
//                }
//                try{
//                    FEISLN.add(rs.getString("FEISLN"));
//                }catch(Exception e){
//                    FEISLN.add("");
//                }
//                try{
//                    FEIVSP.add(rs.getString("FEIVSP"));
//                }catch(Exception e){
//                    FEIVSP.add("");
//                }
//                try{
//                    FECRET.add(rs.getString("FECRET"));
//                }catch(Exception e){
//                    FECRET.add("");
//                }
//                try{
//                    FECING.add(rs.getString("FECING"));
//                }catch(Exception e){
//                    FECING.add("");
//                }
//                try{
//                    DIAARP.add(rs.getInt("DIAARP"));
//                }catch(Exception e){
//                    DIAARP.add(0);
//                }
//                try{
//                    PORCA.add(rs.getString("PORCA"));
//                }catch(Exception e){
//                    PORCA.add("");
//                }
//                try{
//                    PERDET.add(rs.getInt("PERDET"));
//                }catch(Exception e){
//                    PERDET.add(0);
//                }
//                try{
//                    ITA.add(rs.getString("ITA"));
//                }catch(Exception e){
//                    ITA.add("");
//                }
//                try{
//                    VACA.add(rs.getString("VACA"));
//                }catch(Exception e){
//                    VACA.add("");
//                }
//                try{
//                    LMT.add(rs.getString("LMT"));
//                }catch(Exception e){
//                    LMT.add("");
//                }
//                try{
//                    ITC.add(rs.getString("ITC"));
//                }catch(Exception e){
//                    ITC.add("");
//                }
//                try{
//                    STC.add(rs.getString("STC"));
//                }catch(Exception e){
//                    STC.add("");
//                }
//                try{
//                    VPS.add(rs.getString("VPS"));
//                }catch(Exception e){
//                    VPS.add("");
//                }
//                try{
//                    Retiro.add(rs.getString("retiro"));
//                }catch(Exception e){
//                    Retiro.add("");
//                }
//                try{
//                    Ingres.add(rs.getString("ingres"));
//                }catch(Exception e){
//                    Ingres.add("");
//                }
//                try{
//                    Apepla.add(rs.getString("apepla"));
//                }catch(Exception e){
//                    Apepla.add("");
//                }
//                try{
//                    Nompla.add(rs.getString("Codpla"));
//                }catch(Exception e){
//                    Nompla.add("");
//                    
//                }
//                try{
//                    Codpla.add(rs.getInt("Codpla"));
//                }catch(Exception e){
//                     Codpla.add(0);
//                }
//                try{
//                    this.DiasMo.add(rs.getInt("Diasmo"));
//                }catch(Exception e){
//                    DiasMo.add(0);
//                }
//                try{
//                    ApoLey.add(rs.getString("apoley"));
//                }catch(Exception e){
//                    ApoLey.add("");
//                }
//                
//                                
//                try{
//                    ApoExs.add(rs.getString("apoexs"));
//                }catch(Exception e){
//                    ApoExs.add("");
//                }
//                try{
//                    NitEmp.add(rs.getInt("nitemp"));
//                }catch(Exception e){
//                        NitEmp.add(0);
//                }
//                try{
//                   Operad.add(rs.getInt("operad"));
//                }catch(Exception e){
//                    Operad.add(0);
//                }
//                try{
//                    NroAfi.add(rs.getInt("nroafi"));
//                }catch(Exception e){
//                    NroAfi.add(0);
//                }
//                try{
//                    FecCer.add(rs.getInt("feccer"));
//                }catch(Exception e){
//                    FecCer.add(0);
//                }
//                try{
//                    Consecutivo.add(rs.getInt("consec"));
//                }catch(Exception e){
//                    Consecutivo.add(0);
//                } 
//                try{
//                    Salpla.add(rs.getInt("salpla"));
//                }catch(Exception e){
//                    Salpla.add(0);
//                } 
//                
//                
//                
//                try{
//                    IBCD.add(rs.getString("IBCD"));
//                }catch(Exception e){
//                    IBCD.add("");
//                }
                
                
                
                 try{
                    VPeriodo.addElement(rs.getInt("perapo"));
                 }catch(Exception e){
                     VPeriodo.addElement(0);
                 }
                 try{
                    Fecha.addElement(rs.getInt("feccer"));
                 }catch(Exception e){
                    Fecha.addElement(0);
                 }
                 try{
                    CodigoEmpresa.addElement(rs.getInt("codigo"));
                 }catch(Exception e){
                     CodigoEmpresa.addElement(0);
                 }
                 try{
                    NombreEmpresa.addElement(rs.getString("nomapo"));
                 }catch(Exception e){
                     NombreEmpresa.addElement("");
                 }
                 try{
                    ValorAporte.addElement(rs.getDouble("VALRA"));
                 }catch(Exception e){
                    ValorAporte.addElement(0);
                 }
                 try{
                    Dias.addElement(rs.getDouble("Dias"));
                 }catch(Exception e){
                     Dias.addElement(0);
                 }
                 try{
                    NroPlanilla.addElement(rs.getInt("nropla"));
                 }catch(Exception e){
                    NroPlanilla.addElement(0);
                 }
                 try{
                    TipoPlanilla.addElement(rs.getInt("tipopl"));
                 }catch(Exception e){
                     TipoPlanilla.addElement(0);
                 }
             }
        }catch(Exception e ){
               JOptionPane.showMessageDialog(null,"Class:JPlanillaUnicaAportes:getInformacion() "+e.getMessage());
        }
    }
    public String getPeriodo(){
        return ""+this.VPeriodo.elementAt(this.PosicionActual);
    }
    public String getFecha(){
        return ""+this.Fecha.elementAt(this.PosicionActual);
    }
    public String getCodigoEmpresa(){
        return ""+this.CodigoEmpresa.elementAt(this.PosicionActual);
    }
    public String getNombreEmpresa(){
        return ""+this.NombreEmpresa.elementAt(this.PosicionActual);
    }
    public String getValorAporte(){
        return ""+this.ValorAporte.elementAt(this.PosicionActual);
    }
    public String getDias(){
        return ""+this.Dias.elementAt(this.PosicionActual);
    }
    public String getNumeroPlanilla(){
        return ""+this.NroPlanilla.elementAt(this.PosicionActual);
    }
    public String getTipoPlanilla(){    
        return ""+this.TipoPlanilla.elementAt(this.PosicionActual);
    }
    public void setFirts(){
        this.PosicionActual =-1;
    }
    public boolean getNext(){
        this.PosicionActual ++;
        if( this.PosicionActual < this.VPeriodo.size()){
            return true;    
        }else{
            return false;
        }
    }
    public int getCantidad(){
        return this.VPeriodo.size()-1;
    } 
    public void setEnd(){
        this.PosicionActual = this.VPeriodo.size()-1;
    }
    

}
