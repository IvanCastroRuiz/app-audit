/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Subsidio.Universitarios;

import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Garyn Carrillo
 */

public class JHijosMayores extends Thread{
    private JConection JBase_Datos;
    private Connection Cn; 
    private String Periodo;
    private JAuxUniversitario Frame;
    private Vector fila;
    private Vector Detalle;
    public void run(){
        this.ContieneSaltos();  
        Frame.setReporte(true);
        Detalle = new Vector();
    }
    public void  JAuxEscolar(JConection Jbase, Connection cn2, String Periodo, JAuxUniversitario frame){
        this.JBase_Datos = Jbase;
        this.Cn = cn2;
        this.Periodo = Periodo;
        Frame = frame;
    }
    private boolean ContieneSaltos(){
        boolean  Rs = false;
        if ( this.Clear_File()){

            try {
                String Str_Sql = "insert into selinlib.JUNIVERSID "
                        + " select cedula , hijdoc, hijfnc,fecemi, 0 from AUXLIB.MAUXIL , SUBSILIB.MHIJOS where perio="+this.Periodo+" and CEDEST <> 0 and"
                        + " CEDULA = TRADOC and CEDEST = hijdoc and sentre = 'S' and activo='A' and VALOR <= 336000";
                Rs = JBase_Datos.Exc_Sql(Cn,Str_Sql);
                this.getMuestra();
            } catch (Exception e) {
                JOptionPane.showMessageDialog( null,"JAuxEscolar:ContieneSaltos() "+e.getMessage());         
            }
        }
        return Rs;
    }
    private int Calcula_Edad(String FNacimiento, String FEmision){
        int NAño, NMes, Ndia, Edad = 0;
        int EAño, EMes, Edia;
        
        NAño =Integer.parseInt(FNacimiento.trim().substring(0,4));
        NMes =Integer.parseInt(FNacimiento.trim().substring(4,6));
        Ndia =Integer.parseInt(FNacimiento.trim().substring(6,8));
        
        EAño =Integer.parseInt(FEmision.substring(0,4));
        EMes =Integer.parseInt(FEmision.substring(4,6));
        Edia =Integer.parseInt(FEmision.substring(6,8));        
        Edad = EAño - NAño;
        
        if(NMes<EMes){
            Edad = Edad - 1;
        }
        return Edad;
    }
    public void setEdad(String DocTrabajador, String DocHijo, int Edad){
        boolean Rs = false;
        try {
            String Str_Sql = "update selinlib.JUNIVERSID "
                    + " set Edad="+Edad
                    + " where doctra="+DocTrabajador+""
                    + " and dochij="+DocHijo;
            Rs = JBase_Datos.Exc_Sql(Cn,Str_Sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog( null,"JAuxEscolar:setEdad() "+e.getMessage());         
        }
    }
    private void getMuestra(){
        try {
            String Str_Sql = "select doctra,dochij,fecnac,fecasig,edad from  selinlib.JUNIVERSID";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while (rs.next()) {
                    int XEdad = Calcula_Edad(rs.getString("fecnac"), rs.getString("fecasig"));
                    this.setEdad(rs.getString("doctra"),rs.getString("dochij"), XEdad);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog( null,"JAuxEscolar:getMuestra() "+e.getMessage());         
        }
    }
    private boolean Clear_File(){
        boolean rs = false;
        try {
            String Str_Sql = "delete from selinlib.JUNIVERSID ";
            rs = JBase_Datos.Exc_Sql(Cn,Str_Sql);       
        } catch (Exception e) {
               JOptionPane.showMessageDialog( null,"JAuxEscolar:Clear_File()"+e.getMessage());         
        }
        return rs;
    }
    public Vector getInformacion(){
        try {
            String Str_Sql = "select doctra,dochij,fecnac,fecasig,edad, hijsdc from selinlib.juniversid , subsilib.mhijos where doctra=tradoc and hijdoc=dochij and hijsdc=0 "
                    + " order by edad desc";
                    
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            this.Detalle.clear();
            while (rs.next()) {
                this.fila = new Vector();
                fila.add(rs.getString("doctra"));
                fila.add(rs.getString("dochij"));
                fila.add(rs.getString("fecnac"));
                fila.add(rs.getString("fecasig"));
                fila.add(rs.getString("edad"));
                fila.add("sin discapacidad");
                this.Detalle.add(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog( null,"JAuxEscolar:getInformacion()"+e.getMessage());         
        }
        return this.Detalle;
    }
    
    
}
