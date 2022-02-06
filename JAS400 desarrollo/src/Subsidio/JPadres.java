package Subsidio;


import BD_As400.JConection;
import java.sql.*;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class JPadres {
    
     private int DocumentoEmpleado, DocumentoPadre, FechaNacimiento, FechaAfiliacion;
     private String Nombre1, Nombre2, Apellido1, Apellido2, Sexo;
     private boolean RecibeSubsidio, Discapacitado, Embargo;
     private JConection JBase_Datos;
     private Connection Cn;
     private int XSexo;
    public JPadres(int Documento, JConection JBase_Datos3, Connection Cn2, int Sx) {
           DocumentoEmpleado = Documento;
           this.DocumentoPadre = 0;
           this.JBase_Datos = JBase_Datos3;
           this.Cn = Cn2;
           this.XSexo = Sx;
           this.getInformacion();
    }
    
     public void getInformacion(){
        try {
            boolean Control=false;
             String StrSql = "SELECT paddoc, padno1, padno2, padap1, padap2, padfac, padfnc, padsex, padsdc, padsrs, padsem  FROM SUBSILIB.MPADRES WHERE tradoc ="+this.DocumentoEmpleado+
                             " AND padsex = "+this.XSexo;
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             while(rs.next()) {
                this.DocumentoPadre = rs.getInt("paddoc");
                this.Nombre1 = rs.getString("padno1");
                this.Nombre2 = rs.getString("padno2");
                this.Apellido1 = rs.getString("padap1");
                this.Apellido2 = rs.getString("padap2");
                this.FechaNacimiento = rs.getInt("padfnc");
                this.FechaAfiliacion = rs.getInt("padfac");
                if ( rs.getInt("padsrs")== 1){
                    this.RecibeSubsidio = true;
                }else{
                    this.RecibeSubsidio = false;
                }
                if(rs.getDouble("padsex")== 1){
                    this.Sexo = "M";
                }else{
                    this.Sexo = "F";
                }
                if (rs.getInt("padsdc")==1){
                    this.Discapacitado = true;
                }else{
                    this.Discapacitado = false;
                }
                if (rs.getInt("padsem")==1){
                    this.Embargo = true;
                }else{
                    this.Embargo = false;
                }
                Control=true;
             }
             if(Control==false){
                 getPadres_Eliminados();
//                 System.out.println("ENTRO A PADRESSSSSSSSSSSSSSSS");
             }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JPapa:getInformacion() "+e.getMessage());
        }
    }
    
     public void getPadres_Eliminados(){
         try {
              String StrSql = "SELECT *  FROM SUBSILIB.EPADRES WHERE paetra ="+this.DocumentoEmpleado+
                             " AND paesex = "+this.XSexo;
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             while(rs.next()) {
                this.DocumentoPadre = rs.getInt("paedoc");
                this.Nombre1 = rs.getString("paeno1");
                this.Nombre2 = rs.getString("paeno2");
                this.Apellido1 = rs.getString("paeap1");
                this.Apellido2 = rs.getString("paeap2");
                this.FechaNacimiento = rs.getInt("paefnc");
                this.FechaAfiliacion = rs.getInt("paefac");
                if ( rs.getInt("paesrs")== 1){
                    this.RecibeSubsidio = true;
                }else{
                    this.RecibeSubsidio = false;
                }
                if(rs.getDouble("paesex")== 1){
                    this.Sexo = "M";
                }else{
                    this.Sexo = "F";
                }
                if (rs.getInt("paesdc")==1){
                    this.Discapacitado = true;
                }else{
                    this.Discapacitado = false;
                }
                if (rs.getInt("paesem")==1){
                    this.Embargo = true;
                }else{
                    this.Embargo = false;
                }
                
             }
         } catch (Exception e) {
             
             JOptionPane.showMessageDialog(null,"Class:JPapa:getPadres_Eliminados() "+e.getMessage());
         }
     }
     
     
    public int getDocumento(){
        return this.DocumentoPadre;
    }
    public String getSexo(){
        return this.Sexo;
    }
    public String getNombreCompleto(){
            return this.Nombre1.trim()+""+this.Nombre2+""+this.Apellido1+""+this.Apellido2;
    }
    public String getRecibeSubsidio(){
        return ""+this.RecibeSubsidio;
    }        
    public boolean getEmbargo(){
        return this.Embargo;
    }
    public String getDiscapacitado(){
        String Discapacit = "false";
        if(this.Discapacitado){
            Discapacit = "true";
        }
        return Discapacit;
    }
    public String getFechaNacimiento(){
        return ""+this.FechaNacimiento;
    }
    public String getFechaAfiliacion(){
        return ""+this.FechaAfiliacion;
    }
    public boolean getExistePadre(){
        if (this.DocumentoPadre ==0 ){
            return false;
        }else{
            return true;
        }
    }
    
}
