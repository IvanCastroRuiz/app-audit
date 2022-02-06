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
public class JEmpresas {

    private int Codigo;
    private String RazonSocial;
    private String Representante;
    private int Nit;
    private String Direccion, Telefono;
    private int FechaAfiliacion;
    
    private Vector FechasPago, Periodo;
    private Vector ValorAporte, ValorNomina, ValorCaja,ValorSena, ValorICBF, ValorESAP;
    
    private int PosicionActual;
    
    private String Estado ;
    private JConection JBase_Datos;
    private Connection Cn;  
    
    public JEmpresas(int CodigoEmpresa, JConection JBase_Datos3, Connection Cn2){
        
        this.Codigo = CodigoEmpresa;
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        
        FechasPago = new Vector();
        Periodo = new Vector();
        ValorAporte = new Vector();
        this.ValorNomina = new Vector();
        this.ValorCaja = new Vector();
        this.ValorSena = new Vector();
        this.ValorICBF = new Vector();
        this.ValorESAP = new Vector();
        this.PosicionActual = -1;
        this.getInformacion();
    }
    public void getInformacion(){
       try{ 
            String StrSql ="SELECT empcod, empraz, empnit, empdir,empte1,empafi,empnrl FROM SUBSILIB.MEMPVAL WHERE EMPCOD ="+this.Codigo;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            if(rs.next()) {
                this.Codigo    = rs.getInt("empcod");
                this.RazonSocial = rs.getString("empraz");
                this.Nit       = rs.getInt("empnit");
                this.Direccion = rs.getString("empdir");
                this.Representante = rs.getString("empnrl");
                this.FechaAfiliacion = rs.getInt("empafi");
                this.Telefono = rs.getString("empte1");
            }
            this. geInformacionAportes();
       }catch(Exception e ){
           JOptionPane.showMessageDialog(null,"Class: JEmpresas: getInformacionEmpleado() " +e.getMessage());
       }
       
    }
    public String getFechaAfiliacion(){
        return ""+this.FechaAfiliacion;
    }
    public String getTelefono(){
        return this.Telefono;
    }
    public String getNit(){
        return ""+this.Nit;
    }
    public String getEstado(){
        return this.Estado;
    }
    public String getNombre(){
        return this.RazonSocial;
    }
    public String getRepresentante(){
       return this.Representante; 
    }
    public String getTelefeno(){
        return this.Telefono;
    }
    public String getDireccion(){
        return this.Direccion;
    }
    public int getCodigo(){
        return  this.Codigo;
    }
    
    //__________________________________________________________________________
    //_______________   ___________________________   __________________________
    //__________________________________________________________________________
    
    public String getValorAporte(int Fecha){
        return "";
    }
    public int getFechaPosicionPeriodo(int Periodo){
        return 0;
    }
    public String getFechas(){
        return "";
    }
    public String getValorSubsidio(){
        return "";
    }
    public void geInformacionAportes(){
       try{ 
            String StrSql ="SELECT salper, salfec,salnom, salapo,salcaj,salsen,salicb,salesa  FROM SUBSILIB.SALAPOR WHERE salcod ="+this.Codigo+
                           " ORDER BY salper desc";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            while(rs.next()) {
                this.Periodo.addElement(rs.getInt("salper"));
                this.FechasPago.addElement(rs.getInt("salfec"));
                this.ValorNomina.addElement(rs.getDouble("salnom"));
                this.ValorAporte.addElement(rs.getDouble("salapo"));
                this.ValorCaja.addElement( rs.getDouble("salcaj"));
                this.ValorSena.addElement(rs.getDouble("salsen"));
                this.ValorICBF.addElement(rs.getDouble("salicb"));
                this.ValorESAP.addElement(rs.getDouble("salesa"));
            }
       }catch(Exception e ){
           JOptionPane.showMessageDialog(null,"Class: JEmpresas: getInformacionEmpleado() " +e.getMessage());
       }
    }
    public String  getPeriodoAporte(){
        return ""+this.Periodo.elementAt(this.PosicionActual);
    }
    public String getFechaAporte(){
        return ""+this.FechasPago.elementAt(this.PosicionActual);
    }
    public String getAporteNomina(){
        return ""+this.ValorNomina.elementAt(this.PosicionActual);
    }
    public String getAporte(){
        return ""+this.ValorAporte.elementAt(this.PosicionActual);
    }
    public String getAporteCaja(){
        return ""+this.ValorCaja.elementAt(this.PosicionActual);
    }
    public String getAporteSena(){
        return ""+this.ValorSena.elementAt(this.PosicionActual);
    }
    public String getAporteIcbf(){
        return ""+this.ValorICBF.elementAt(this.PosicionActual);
    }
    public String getAporteEsap(){
        return ""+this.ValorESAP.elementAt(this.PosicionActual);
    }
    public void setFirts(){
        this.PosicionActual = -1;
    }
    public void setEnd(){
        this.PosicionActual = this.FechasPago.size();
    }
    public boolean getNext(){
        this.PosicionActual ++;
        if( this.PosicionActual < this.FechasPago.size()){
            return true;    
        }else{
            return false;
        }
    }
    public int getCantidad(){
        return this.FechasPago.size();
    }
}
