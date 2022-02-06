package Subsidio;


import BD_As400.JConection;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.Date;
import javax.swing.*;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class JHijos {
    
    private int DocumentoEmpleado;
    private Vector DocumentoHijo, Nombre1, Nombre2, Apellido1, Apellido2, Sexo, FechNacimiento, FechaAfiliacion, RecibeSubsidio, Discapacitado;
    private int CantidadHijos;
    private Vector Edad;
    private JConection JBase_Datos;
    private Connection Cn;    
    private int PosicionActual;
    
    public JHijos(int Documento, JConection JBase_Datos3, Connection Cn2){
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        PosicionActual = -1;
        DocumentoEmpleado = Documento;
        DocumentoHijo = new Vector();
        Nombre1= new Vector(); 
        Nombre2= new Vector();
        Apellido1 = new Vector();
        Apellido2 = new Vector();
        Sexo= new Vector();
        FechNacimiento = new Vector();
        FechaAfiliacion = new Vector();
        RecibeSubsidio= new Vector();
        Discapacitado= new Vector();
        Edad = new Vector();
        this.getInformacion();
        
    }
    
    public JHijos(JConection JBase_Datos3, Connection Cn2){
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        PosicionActual = -1;
        DocumentoHijo = new Vector();
        Nombre1= new Vector(); 
        Nombre2= new Vector();
        Apellido1 = new Vector();
        Apellido2 = new Vector();
        Sexo= new Vector();
        FechNacimiento = new Vector();
        FechaAfiliacion = new Vector();
        RecibeSubsidio= new Vector();
        Discapacitado= new Vector();
              
    }
    public void setDocumentoTrabajador(int Documento){
        DocumentoEmpleado = Documento;
    }
    
    public void getInformacion(){
        try {
             boolean Control=false;
             String StrSql = "SELECT hijdoc ,hijno1 ,hijno2 ,hijap1  ,hijap2  ,hijsex ,hijfac, hijfnc ,hijsrs, hijsdc  FROM SUBSILIB.MHIJOS WHERE tradoc ="+this.DocumentoEmpleado;
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             while (rs.next()) {
                int FechaNacimiento = rs.getInt("hijfnc");
                this.Edad.add(calcularEdad(""+FechaNacimiento)); 
                try{
                    this.DocumentoHijo.addElement(rs.getDouble("hijdoc"));
                }catch(Exception e ){
                    this.DocumentoHijo.addElement(0);
                }
                try{
                    this.Nombre1.addElement(rs.getString("hijno1"));
                }catch(Exception e){
                    this.Nombre1.addElement(" ");
                }
                try{
                    this.Nombre2.addElement(rs.getString("hijno2"));
                }catch(Exception e){
                    this.Nombre2.addElement(" ");
                }
                try{
                    this.Apellido1.addElement(rs.getString("hijap1"));
                }catch(Exception e ){
                    this.Apellido1.addElement(" ");
                }
                try{
                    this.Apellido2.addElement(rs.getString("hijap2"));
                }catch(Exception  e){
                    this.Apellido2.addElement(" ");
                }
                try{
                    this.Sexo.addElement(rs.getInt("hijsex"));
                }catch(Exception e ){
                    this.Sexo.addElement(0);
                }
                try{
                    this.FechaAfiliacion.addElement(rs.getInt("hijfac"));
                }catch(Exception e ){
                    this.FechaAfiliacion.addElement(0);
                }
                try{
                    this.FechNacimiento.addElement(FechaNacimiento);
                }catch(Exception e ){
                    this.FechNacimiento.addElement(0);
                }
                try{
                    this.RecibeSubsidio.addElement(rs.getInt("hijsrs"));
                }catch(Exception e ){
                    this.RecibeSubsidio.addElement(0);
                }
                try{
                    this.Discapacitado.addElement(rs.getString("hijsdc"));
                }catch(Exception e ){
                    this.Discapacitado.addElement(" ");
                }
                Control=true;
             }
             
             if(Control==false){
                 
                 Hijos_Eliminados();
                 //System.out.println("MAETRO DE HIJOS");
             }
             
             
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JHijos:getInformacion() "+e.getMessage());
        }
    }
    
    public void Hijos_Eliminados(){
        try {
             String StrSql = "SELECT *  FROM SUBSILIB.EHIJOS WHERE hietra ="+this.DocumentoEmpleado;
            
             ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
             while (rs.next()) {
                int FechaNacimiento = rs.getInt("hiefnc");
                this.Edad.add(calcularEdad(""+FechaNacimiento)); 
                try{
                    this.DocumentoHijo.addElement(rs.getDouble("hietra"));
                }catch(Exception e ){
                    this.DocumentoHijo.addElement(0);
                }
                try{
                    this.Nombre1.addElement(rs.getString("hieno1"));
                }catch(Exception e){
                    this.Nombre1.addElement(" ");
                }
                 
                try{
                    this.Nombre2.addElement(rs.getString("hieno2"));
                }catch(Exception e){
                    this.Nombre2.addElement(" ");
                }
                try{
                    this.Apellido1.addElement(rs.getString("hieap1"));
                }catch(Exception e ){
                    this.Apellido1.addElement(" ");
                }
                
                try{
                    this.Apellido2.addElement(rs.getString("hieap2"));
                }catch(Exception  e){
                    this.Apellido2.addElement(" ");
                }
                try{
                    this.Sexo.addElement(rs.getInt("hiesex"));
                }catch(Exception e ){
                    this.Sexo.addElement(0);
                }
                try{
                    this.FechaAfiliacion.addElement(rs.getInt("hiefac"));
                }catch(Exception e ){
                    this.FechaAfiliacion.addElement(0);
                }
                try{
                    this.FechNacimiento.addElement(FechaNacimiento);
                }catch(Exception e ){
                    this.FechNacimiento.addElement(0);
                }
                try{
                    this.RecibeSubsidio.addElement(rs.getInt("hiesrs"));
                }catch(Exception e ){
                    this.RecibeSubsidio.addElement(0);
                }
                
                try{
                    this.Discapacitado.addElement(rs.getString("hiesdc"));
                }catch(Exception e ){
                    this.Discapacitado.addElement(" ");
                }
                
             }
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JHijos:Hijos_Eliminados() "+e.getMessage());
        }
    }
    
    
    
    
    public String getDocumento(){
        return ""+this.DocumentoHijo.elementAt(this.PosicionActual);
    }
    public int getCantidad(){
        return this.DocumentoHijo.size();
    }
    public String getPrimerNombre(){
        return ""+this.Nombre1.elementAt(this.PosicionActual);
    }
    public String getSegundoNombre(){
        return ""+this.Nombre2.elementAt(this.PosicionActual);
    }
    public String getPrimerApellido(){
        return ""+this.Apellido1.elementAt(this.PosicionActual);
    }
    public String getSegundoApellido(){
        return ""+this.Apellido2.elementAt(this.PosicionActual);
    }
    public String getSexo(){
        return ""+this.Sexo.elementAt(this.PosicionActual);
    }
    public String getFechaNacimiento(){
        return ""+this.FechNacimiento.elementAt(this.PosicionActual);
    }
    public String getFechaAfiliacion(){
        return ""+this.FechaAfiliacion.elementAt(this.PosicionActual);
    }
    public String getRecibeSubsidio(){
        String Estado ="false";
        if (this.RecibeSubsidio.elementAt(this.PosicionActual).toString().trim().equals("1")){
            Estado = "true";
        }
        return ""+Estado;
    }
    public String getDiscapacitado(){
        String Discapacit="false";
        if(this.Discapacitado.elementAt(this.PosicionActual).equals("1")){
            Discapacit = "true";
        }
        return Discapacit;
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
        this.PosicionActual = this.DocumentoHijo.size()-1;
    }
    
    
    public Vector getDocumento_V(){
        return this.DocumentoHijo;
    }
    public Vector getPrimerNombre_V(){
        return this.Nombre1;
    }
    public Vector getSegundoNombre_V(){
        return this.Nombre2;
    }
    public Vector getPrimerApellido_V(){
        return this.Apellido1;
    }
    public Vector getSegundoApellido_V(){
        return this.Apellido2;
    }
    public Vector getSexo_Vector(){
        return this.Sexo;
    }
    public Vector getFechaNacimiento_V(){
        return this.FechNacimiento;
    }
    public Vector getFechaAfiliacion_V(){
        return this.FechaAfiliacion;
    }
    public Vector getRecibeSubsidio_V(){
        return this.RecibeSubsidio;
    }
    public Vector getDiscapacitado_V(){
        return this.Discapacitado;
    }
    public Vector getEdad_V(){
        return this.Edad;
    }
    
    public static Integer calcularEdad(String fecha){
        java.util.Date fechaNac=null;
        try {
            /**Se puede cambiar la mascara por el formato de la fecha
            que se quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
            en este caso es día mes año*/
            fechaNac = new SimpleDateFormat("yyyyMMdd").parse(fecha);
        } catch (Exception ex) {
            System.out.println("Error:"+ex);
        }
        Calendar fechaNacimiento = Calendar.getInstance();
        //Se crea un objeto con la fecha actual
        Calendar fechaActual = Calendar.getInstance();
        //Se asigna la fecha recibida a la fecha de nacimiento.
        fechaNacimiento.setTime(fechaNac);
        //Se restan la fecha actual y la fecha de nacimiento
        int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
        //Se ajusta el año dependiendo el mes y el día
        if(mes<0 || (mes==0 && dia<0)){
            año--;
        }
        //Regresa la edad en base a la fecha de nacimiento
        return año;
    
   }
    
    
    
    
    
    
    
    
    
}
