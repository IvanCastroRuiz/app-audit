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
public class JEmpleados {
    
    private int Documento;
    private double Salario;
    private String Nombre, Sexo;
    private String Nombre1, Nombre2, Apellido1, Apellido2;
    private int FechaAfiliacion, FechaNacimiento;
    private int CodigoEmpresa;
    private boolean RecibeSubsidio, Estado;
    
    private JHijos Hijos;
    private JPadres Padre;
    private JPadres Madre;
    private JConyuge Conyuge;
    private JEmpresas Empresa;
    
    private JConection JBase_Datos;
    private Connection Cn;    
    
    
    public JEmpleados(int xDocumento, JConection JBase_Datos3, Connection Cn2){
        this.Documento = xDocumento;
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        
        this.Hijos = new JHijos(this.Documento, this.JBase_Datos, this.Cn);
        this.Padre = new JPadres(this.Documento, this.JBase_Datos, this.Cn,1);
        this.Madre = new JPadres(this.Documento, this.JBase_Datos, this.Cn,2);
        this.Conyuge = new JConyuge(this.Documento, this.JBase_Datos, this.Cn);
        this.getInformacionEmpleado();
        this.Empresa = new JEmpresas(this.CodigoEmpresa, this.JBase_Datos, this.Cn);
    }
    public void getInformacionEmpleado(){
             boolean SwControl = false;
             try {
                 String StrSql = "SELECT TRANO1, TRANO2, TRAAP1, TRAAP2,TRASRS,TRANOM,TRASEX, TRAFNC, TRAFAC, TRAVSB,EMPCOD   FROM SUBSILIB.MTRABAJ WHERE tradoc ="+this.Documento;
                 ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
                 this.Estado =false;
                 while(rs.next()) {
                     this.Nombre = rs.getString("TRANOM");
                     this.Nombre1 = rs.getString("TRANO1");
                     this.Nombre2 = rs.getString("TRANO2");
                     this.Apellido1= rs.getString("TRAAP1");
                     this.Apellido2= rs.getString("TRAAP2");
                     this.FechaNacimiento = rs.getInt("TRAFNC");
                     this.FechaAfiliacion = rs.getInt("TRAFAC");
                     this.Salario = rs.getDouble("TRAVSB");
                     this.CodigoEmpresa = rs.getInt("empcod");
                      if(rs.getInt("TRASEX")== 1){
                         this.Sexo = "M";
                     }else{
                         this.Sexo = "F";
                     }
                     if (SwControl) {
                         this.RecibeSubsidio = true;
                     }else{
                         this.RecibeSubsidio = false;
                     }
                    SwControl = true;
                    this.Estado =true;
                    
                }
            }catch(Exception e ){
                JOptionPane.showMessageDialog(null,"Class: JEmpleados getInformacionEmpleado() " +e.getMessage());
            } 
            if(SwControl == false){
                try {
                    String SQL = "SELECT * FROM SUBSILIB.ETRABAJ WHERE TREDOC ="+this.Documento;
                    ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,SQL);
                    while(rs.next()) {
                            this.Nombre = rs.getString("TRENOM");
                            this.Nombre1 = rs.getString("TRENO1");
                            this.Nombre2 = rs.getString("TRENO2");
                            this.Apellido1= rs.getString("TREAP1");
                            this.Apellido2= rs.getString("TREAP2");
                            this.FechaNacimiento = rs.getInt("TREFNC");
                            this.FechaAfiliacion = rs.getInt("TREFAC");
                            this.Salario = rs.getDouble("TREVSB");
                            this.CodigoEmpresa = rs.getInt("TREEMP");
                             if(rs.getInt("TRESEX")== 1){
                                this.Sexo = "M";
                            }else{
                                this.Sexo = "F";
                            }
                            if (SwControl) {
                                this.RecibeSubsidio = true;
                            }else{
                                this.RecibeSubsidio = false;
                            }
                           SwControl = true;
                           this.Estado =true;
                    }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,"Class: JEmpleados getInformacionEmpleado() " +e.getMessage());
                }
            }
    }
    public JEmpresas getEmpresa(){
        return this.Empresa;
    }
    public int getFechaAfiliacion(){
        return this.FechaAfiliacion;
    }
    public int getFechaNacimiento(){
        return this.FechaNacimiento;
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
        return  this.Apellido2;
    }
    public int getDocumento(){
        return this.Documento;
    }
    public JHijos getHijos(){
        return this.Hijos;
    }
    public JPadres getPadres(){
        return this.Padre;
    }
    public JPadres getMadre(){
        return this.Madre;
    }
    public JConyuge getConyuge(){
        return this.Conyuge;
    }
    public String getNombre(){
        return Nombre;
    }
    public String getSexo(){
        return Sexo;
    }
    public boolean getEstado(){
        return this.Estado;
    }
    public double getSalario(){
        return Salario;
    }
    public boolean getRecibe(){
        return RecibeSubsidio;
    }
    public boolean getRecibeSubsidio(){
        return RecibeSubsidio;
    }
}
