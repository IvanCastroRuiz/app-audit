package Subsidio;


import Subsidio.JEmpleados;
import Subsidio.JSubsidio;
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

public class JCheques {
    
    private Vector Numero, Secuencia,VPeriodo, Fecha;
    private int Periodo,FPeriodo;
    private Vector Trabajador;
    private Vector CodigoEmpresa;
    private Vector Situacion, EstadoCheque;
    private Vector Importe;
    private JSubsidio  Liquidacion;
    private String xEstado ;
    private int PosicionActual;
    
    private JConection JBase_Datos;
    private Connection Cn;  
    
    public JCheques(int Documento, JConection JBase_Datos3, Connection Cn2){
        
        Numero = new Vector(); 
        Secuencia = new Vector(); 
        VPeriodo = new Vector(); 
        Fecha= new Vector();
        CodigoEmpresa = new Vector();
        Situacion= new Vector(); 
        EstadoCheque= new Vector();
        Importe = new Vector();
        Trabajador = new Vector();
        
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.PosicionActual = -1;
        this.Trabajador.add(Documento);
        this.getInformacion(2);
    }
    public JCheques (int PPeriodo, int xPeriodo, JConection JBase_Datos3, Connection Cn2){
        
        Numero = new Vector(); 
        Secuencia = new Vector(); 
        VPeriodo = new Vector(); 
        Fecha= new Vector();
        CodigoEmpresa = new Vector();
        Situacion= new Vector(); 
        EstadoCheque= new Vector();
        Importe = new Vector();
        Trabajador = new Vector();

        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.Periodo = PPeriodo;
        this.FPeriodo= FPeriodo;
        this.PosicionActual = -1;
        this.getInformacion(3);
    }
    public JCheques(JEmpleados DTrabajador, JConection JBase_Datos3, Connection Cn2){
        
        Numero = new Vector(); 
        Secuencia = new Vector(); 
        VPeriodo = new Vector(); 
        Fecha= new Vector();
        CodigoEmpresa = new Vector();
        Situacion= new Vector(); 
        EstadoCheque= new Vector();
        Importe = new Vector();
        Trabajador = new Vector();
        
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.Trabajador.addElement( DTrabajador.getDocumento());
        this.PosicionActual = -1;
        this.getInformacion(2);
    }
    public JCheques(String Estado, int IPeriodo , int FPeriodo,JEmpleados DTrabajador, JConection JBase_Datos3, Connection Cn2){
        
        Numero = new Vector(); 
        Secuencia = new Vector(); 
        VPeriodo = new Vector(); 
        Fecha= new Vector();
        CodigoEmpresa = new Vector();
        Situacion= new Vector(); 
        EstadoCheque= new Vector();
        Importe = new Vector();
        Trabajador = new Vector();
        
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.xEstado = Estado;
        this.Trabajador.addElement(DTrabajador.getDocumento());
        this.PosicionActual = -1;
        this.getInformacion(1);
    }
    public void getInformacion(int tipo){
        String StrSql = "";
        try{
            if(tipo==1){
                if(xEstado =="*all"){
                    StrSql = "SELECT chesec, chenro,cheemp,cheper, chefec, chetra, cheval, chesit, cheest FROM SUBSILIB.MAECHEQ WHERE "+
                         " CHEPER >= "+this.Periodo+" AND CHEPER <= "+this.FPeriodo+" AND  CHETRA ="+this.Trabajador.elementAt(0) +" order by chefec desc ";
                }else{
                    StrSql = "SELECT chesec, chenro,cheemp,cheper, chefec, chetra, cheval, chesit, cheest FROM SUBSILIB.MAECHEQ WHERE "+
                         " CHEPER >= "+this.Periodo+" AND CHEPER <= "+this.FPeriodo+" AND  CHETRA ="+this.Trabajador.elementAt(0) +" AND CHEEST ="+this.xEstado+" order by chefec desc";
                }
            }
            
            if(tipo==2){
                    StrSql = "SELECT chesec, chenro,cheemp,cheper, chefec, chetra, cheval, chesit, cheest FROM SUBSILIB.MAECHEQ WHERE "+
                             " CHETRA ="+this.Trabajador.elementAt(0) +" order by chefec desc ";
            }
            if(tipo==3){
                    StrSql = "SELECT chesec, chenro,cheemp,cheper, chefec, chetra, cheval, chesit, cheest FROM SUBSILIB.MAECHEQ WHERE "+
                         " CHEPER >= "+this.Periodo+" AND CHEPER <= "+this.FPeriodo+" order by chefec desc ";
            }
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            int Primer = 1;
            while(rs.next()) {
               this.VPeriodo.addElement(rs.getInt("cheper"));
               this.Fecha.addElement(rs.getInt("chefec"));
               this.Secuencia.addElement(rs.getInt("chesec"));
               this.Numero.addElement(rs.getString("chenro"));
               this.Importe.addElement(rs.getDouble("cheval"));
               if ( (Primer == 1)  &&   ((tipo == 1) || (tipo == 2))  ){
                   Primer= 0;
               }else{
                   Primer = 0;
                   this.Trabajador.addElement(rs.getInt("cheval"));
               }
               this.Situacion.addElement(rs.getString("chesit"));
               this.EstadoCheque.addElement(rs.getString("cheest"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Class: JCheques: getInformacion" +e.getMessage());
        }
    }
    public String getSecuencia(){
        return ""+this.Secuencia.elementAt(this.PosicionActual);
    }
    public String getPeriodo(){
        return ""+this.VPeriodo.elementAt(this.PosicionActual);
    }
    public String getFecha(){
        return ""+this.Fecha.elementAt(this.PosicionActual);
    }
    public String getNumero(){
        
        return ""+this.Numero.elementAt(this.PosicionActual);
    }  
    public String getEmpleado(){
        return ""+this.Trabajador.elementAt(this.PosicionActual);
    }
    public String getEmpresa(){
        return ""+this.CodigoEmpresa.elementAt(this.PosicionActual);
    }
    public String getSituacion(){
        return ""+this.Situacion.elementAt(this.PosicionActual);
    }
    public String getEstado(){
        return ""+this.EstadoCheque.elementAt(this.PosicionActual);
    }
    public String getValor(){
       return ""+this.Importe.elementAt(this.PosicionActual);
    }
    public JSubsidio getSubsidio(){
        return this.Liquidacion;
    }
    public void setFirts(){
        this.PosicionActual =-1;
    }
    public void setEnd(){
        this.PosicionActual = this.VPeriodo.size();
    }
    public int getCantidad(){
         return this.VPeriodo.size();
    }
    public boolean getNext(){
        this.PosicionActual ++;
        if( this.PosicionActual < this.getCantidad()){
            return true;    
        }else{
            return false;
        }
    }
}
