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
public class JConyuge {
    
    private int DocumentoEmpleado;
    private int DocumentoConyuge, FechaNacimiento, FechaAfiliacion;
    private double SalarioVigente;
    private String Nombre1, Nombre2, Apellido1, Apellido2, Sexo;
    private boolean RecibeSubsidio;
    private JConection JBase_Datos;
    private Connection Cn;
    
    public JConyuge(int Documento, JConection JBase_Datos3, Connection Cn2){
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        DocumentoEmpleado = Documento;
        this.DocumentoConyuge = 0;
        this.getInformacion();
    }
    public void getInformacion(){
        try {
            String StrSql = "SELECT condoc, confac, confnc, conno1, conno2, conap1, conap2, convsb, consrs, consex  FROM SUBSILIB.MCONYUG WHERE tradoc ="+this.DocumentoEmpleado+
                            " AND  Condoc <> 0";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            if (rs.next()) {
                this.DocumentoConyuge = rs.getInt("condoc");
                this.Nombre1 = rs.getString("conno1");
                this.Nombre2 = rs.getString("conno2");
                this.Apellido1 = rs.getString("conap1");
                this.Apellido2 = rs.getString("conap2");
                this.FechaNacimiento = rs.getInt("confnc");
                this.FechaAfiliacion = rs.getInt("confac");
                this.SalarioVigente = rs.getDouble("convsb");
                if (rs.getInt("consex")== 1){
                    this.Sexo = "M";
                }else{
                    this.Sexo = "F";
                }
                if(rs.getInt("consrs")==1){
                    this.RecibeSubsidio = true;
                }else{
                    this.RecibeSubsidio = false;
                }
            }else{
                this.DocumentoConyuge = 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JConyuge:getInformacion() "+e.getMessage());
        }
    }
    public int getDocumento(){
        return this.DocumentoConyuge;
    }
    public boolean getExisteConyuge(){
        boolean Existe;
        if(this.DocumentoConyuge == 0){
            Existe = false;
        }else{
            Existe = true;   
        }
        return Existe;
    }
    public String getNombreCompleto(){
        return this.Nombre1.trim()+" "+this.Nombre2.trim()+" "+this.Apellido1.trim()+" "+this.Apellido2.trim();
    }
    public int getFechaNacimiento(){
        return this.FechaNacimiento;
    }
    public int getFechaAfiliacion(){
        return this.FechaAfiliacion;
    }
    public String getSexo(){
       return this.Sexo; 
    }
    public double getSalario(){
        return this.SalarioVigente;
    }
    public boolean getRecibeSubsidio(){
        return this.RecibeSubsidio;
    }
    public String getPrimerNombre(){
        return this.Nombre1;
    }
    public String getSegundoNombre(){
        return this.Nombre2;
    }
    public String getPrimerApellido(){
        return this.Apellido1;
    }
    public String getSegundoApellido(){
        return this.Apellido2;
    }
}
