package Subsidio;


import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class JReclamos {
    
    private int Documento;
    private int FechaInicial, FechaFinal;
    private JConection JBase_Datos;
    private Connection Cn; 
    
    List <String> Fecha = new ArrayList <String>();
    List <String> PeriodoInicial = new ArrayList <String>();
    List <String> PeriodoFinal = new ArrayList <String>();
    List <String> NoHijo = new ArrayList <String>();
    List <String> NoCuotas = new ArrayList <String>();
    List <String> NoCuotasPagadas = new ArrayList <String>();
    List <String> SeñalPagoInmediato = new ArrayList <String>();
    List <String> NoCheque = new ArrayList <String>();
    List <String> CodigoEmpresa = new ArrayList <String>();
    List <String> UsuarioAutirzo = new ArrayList <String>();
    List <Double> ValorCuota = new ArrayList <Double>();
    List <Double> Valor = new ArrayList <Double>();
    
    public JReclamos(int Documento, int FecIni, int FecFin,JConection Jbase, Connection cn2){
        this.Documento = Documento;
        this.FechaInicial = FecIni;
        this.FechaFinal = FecFin;
        this.JBase_Datos = Jbase;
        this.Cn = cn2;
        getInformacion(1,true);
    }
    
    public JReclamos(int Documento,JConection Jbase, Connection cn2){
        this.Documento = Documento;
        this.JBase_Datos = Jbase;
        this.Cn = cn2;
        getInformacion(1,false);
    }
    
    public void getInformacion(int Tipo, boolean Fecha){
        String Str_Sql ="";
        try {
            if ((Tipo==1) && (Fecha==false)) {
                Str_Sql ="select * from subsilib.mrecret where rretra ="+this.Documento;
            }
            if ((Tipo==1) && (Fecha==true)) {
                Str_Sql ="select * from subsilib.mrecret where rretra ="+this.Documento +" "
                        + "and RREFGR>="+this.FechaInicial+" and RREFGR<="+this.FechaFinal;
            }
            if (Tipo==2) {
                Str_Sql = "";
            }
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(rs.next()){
                this.Fecha.add(Str_Sql);
            }   
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,"JReclamos: getInformaciion" );
        }
    }
    
}

