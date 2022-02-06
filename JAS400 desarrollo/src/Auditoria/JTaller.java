package Auditoria;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author     : GARYN JAIRO CARRILLO CABALLERO GACA1186
 * Derecho     : COMFAMILIAR ATLÁNTICO
 * Version 1.0 : Version Original 2012-02-24
 */

import BD_Auditoria.*;
import BD_MapaRiesgo.*;
import Auditoria.Proceso;
import java.sql.*;
import javax.swing.*;
public final class JTaller extends javax.swing.JFrame {
    
    // Coneccion a base del Servidor de Riesgo
    private JConneccionMapaRiesgos JBase_Datos;
    private Connection Connection_BD;
    private SQLObject SQL_verificacion ;
    
    // Coneccion a base de datos Local
    private JConeccion_AudiSys JBase_Datos2;
    private Connection Connection_BD2;
    private SQLObject SQL_verificacion2 ;
    private Proceso jr;
    public JTaller(JConeccion_AudiSys PJBase_Datos, Connection PConnection_BD){
        this.JBase_Datos = new JConneccionMapaRiesgos ();
     	this.Connection_BD = this.JBase_Datos.Open_BD_Mapa_Riesgos();
        JBase_Datos2 = PJBase_Datos;
     	Connection_BD2 = PConnection_BD;
        jr =  new Proceso();
        try{ 
           for(int i=0;i<100;i++){
               jr.setProger(i);
               Thread.sleep(25);
           }
       }catch(InterruptedException e){
           System.out.println(e.getMessage());
       }
    }
       
    public void SetTaller(){
           try{
                  String QRY_SQL = "SELECT  Nombre  , Fecha_Realizacion , Taller.id , Proceso, "
                  +" Facilitador, Redactor, Taller.Dependencia "+
                  " FROM Dependencia  , Taller "+
                  " where  Dependencia.ID = Taller.Dependencia ";
                  String zAño = null;
                  String zMes = null;
                  String zDia = null ;
                  ResultSet  Result = JBase_Datos.SQL_QRY(Connection_BD,QRY_SQL);
                  boolean Estado = false;
	          while (Result.next()){
                      int Talleres = Result.getInt(3) ;
                      String XAño = Result.getString(2);
                      String XNombre = Result.getString(1);
                      String XProceso = Result.getString(4);
                      String XFacilitador = Result.getString(5);
                      String XRedactor= Result.getString(6);
                      int XDependencia= Result.getInt(7);
                      zAño = "";
                      zMes = "";
                      zDia = "" ;
                      for(int i = 0 ; i<= XAño.length(); i++){
                          if ((i>= 0) && (i<=3)){
                              zAño = zAño + XAño.charAt(i);
                          }
                          if ((i>= 8) && (i<=9)){
                              zDia = zDia + XAño.charAt(i);
                          }
                          if ((i>= 5) && (i<=6)){
                              zMes = zMes + XAño.charAt(i);
                          }
                      }
                      
                      Existe_Taller(Talleres,XNombre, Integer.parseInt(zDia),Integer.parseInt(zMes),
                              zAño, XFacilitador, XRedactor, XProceso, XDependencia);
                      
                      SetRiesgos(Talleres);
                      setCausaRiesgos(Talleres);
                      setCausasriesgos(Talleres);
                      setAccciones(Talleres);
                      setCausaAccion(Talleres);
                      Estado = true;
                  }
                                
                   
   	   }catch(Exception e){
            	 JOptionPane.showMessageDialog( null,e.getMessage());
	   } 
           
           
    }

    public void Existe_Taller(int IdTaller, String Nombre, int Dia , int Mes, String  Año, 
                              String Facilitador , String Redactor, String Proceso, int Dependencia ){
        try {
            String StrSql =" SELECT IDTaller"+ 
                           " FROM JTaller "+
                           " WHERE IDTaller ="+IdTaller;
            ResultSet  Result = JBase_Datos2.SQL_QRY(Connection_BD2,StrSql);	
            if (!Result.next()){
               StrSql = "INSERT INTO JTaller"+
                        "(IDTaller,Nombre,Dia,Mes,Ano,Facilitador,Redactor, Proceso,IdDependencia)"+
                        " VALUES "+
                        "("+IdTaller+" ,'"+Nombre+"' ,'"+Dia+"' , '"+Mes+"' , '"+Año+
                       "' , '"+Facilitador+"' , '"+Redactor+"','"+Proceso+"' , "+Dependencia+")";
               boolean  Resultx = JBase_Datos2.Exc_Sql(Connection_BD2,StrSql);		
            }
           
        }catch(Exception e ){
            JOptionPane.showMessageDialog( null,e.getMessage());
        }
        
        
    }
    
    public void SetRiesgos(int IdTaller){
        try{
             String QRY_SQL = "SELECT ID_Taller,ID, Nombre "+
                             "FROM  Riesgo "+
                             "WHERE ID_Taller ="+IdTaller;
             ResultSet  Result = JBase_Datos.SQL_QRY(Connection_BD,QRY_SQL);
             while (Result.next()){
                 int xIdTaller = Result.getInt(1);
                 String xIdRiesgo = Result.getString(2);
                 String xDRiesgo   = Result.getString(3);
                 Existe_Riesgo( xIdTaller , xIdRiesgo, xDRiesgo);
             }
             
        }catch(Exception e){
            JOptionPane.showMessageDialog( null,e.getMessage());
        }
       
    }
    
    public void Existe_Riesgo(int IdTaller , String IdRiesgo, String DRiesgo){
           try{
              String StrSql = "SELECT IdTaller "+
                        "FROM JRiesgos "+
                        "WHERE IDTaller ="+IdTaller+" AND IDRiesgo ='"+IdRiesgo+"'";
              ResultSet  Result = JBase_Datos2.SQL_QRY(Connection_BD2,StrSql);              
              if (!Result.next()){
                StrSql = "INSERT INTO JRiesgos"+
                          "(IDTaller,IDRiesgo,Descripcion_Riesgo, PuntuacionPromedio)"+
                          " VALUES "+
                          "('"+IdTaller+"' ,'"+IdRiesgo+"' ,'"+DRiesgo.trim()+"' , 0)";
                 boolean  Result2 = JBase_Datos2.Exc_Sql(Connection_BD2,StrSql);	
              }
           }catch(Exception e){
                    JOptionPane.showMessageDialog( null,e.getMessage());         
           }
   }
    
    public void setCausaRiesgos(int IdTaller){
          try{
             String QRY_SQL = "SELECT ID, Nombre, ID_Taller "+
                             "FROM  Causa "+
                             "WHERE ID_Taller ="+IdTaller +
                             " Order by 1, 3 desc ";
             ResultSet  Result = JBase_Datos.SQL_QRY(Connection_BD,QRY_SQL);
             while (Result.next()){
                   String xIdCausa = Result.getString(1);
                   String xIdDescripcion = Result.getString(2);
                   int xIdTaller   = Result.getInt(3);
                   Existe_Causa(xIdCausa, xIdDescripcion ,xIdTaller);
             }
          }catch(Exception e){
               JOptionPane.showMessageDialog( null,e.getMessage());
          }
    }
    
    public void Existe_Causa(String zIdCausa, String zIdDescripcion ,int zIdTaller){
        try{
              String StrSql = "SELECT IdTaller "+
                        "FROM JCausa "+
                        "WHERE IDTaller ="+zIdTaller +" AND IDCausa = '"+zIdCausa+"'";
              ResultSet  Result = JBase_Datos2.SQL_QRY(Connection_BD2,StrSql);              
              if (!Result.next()){
                 StrSql = "INSERT INTO JCausa"+
                          "(IDTaller,IDCausa,DescripcionCausa)"+
                          " VALUES "+
                          "("+zIdTaller+" ,'"+zIdCausa.trim()+"' ,'"+zIdDescripcion.trim()+"')";
                 boolean  Result2 = JBase_Datos2.Exc_Sql(Connection_BD2,StrSql);
              }
             
           }catch(Exception e){
                 JOptionPane.showMessageDialog( null,e.getMessage());        
           }
    }
    
    public void setCausasriesgos(int IdTaller){
        try{
             String QRY_SQL = "SELECT ID_Riesgo,ID_Causa, ID_Taller, Relaciondirecta "+
                             "FROM  RiesgoCausa "+
                             "WHERE ID_Taller ="+IdTaller +
                             " ORDER BY 3 desc";
             ResultSet  Result = JBase_Datos.SQL_QRY(Connection_BD,QRY_SQL);
             while (Result.next()){
                 String xIdRiesgo = Result.getString(1);
                 String xIdCausa  = Result.getString(2);
                 int xIdTaller    = Result.getInt(3);
                 int xRelacion    = Result.getInt(4);
                 ExisteCausaRiesgo(xIdRiesgo, xIdCausa, xIdTaller, xRelacion);
             }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog( null,e.getMessage());
        }
    }
    
    public void ExisteCausaRiesgo(String Riesgo, String Causa, int Taller, int Relacion){
         try{
              String StrSql = "SELECT IdTaller "+
                        "FROM JCausaRiesgo "+
                        "WHERE IDTaller ="+Taller +" AND IDCausa = '"+Causa+"'"
                        +"AND IDRiesgo = '"+Riesgo+"'";
              ResultSet  Result = JBase_Datos2.SQL_QRY(Connection_BD2,StrSql);              
              if (!Result.next()){
                  StrSql = "INSERT INTO JCausaRiesgo"+
                          "(IDRiesgo,IDCausa,IDTaller,Relacion)"+
                          " VALUES "+
                          "('"+Riesgo.trim()+"' ,'"+Causa.trim()+"' ,'"+Taller+"' , '"+Relacion+"' )";
                  boolean  Result2 = JBase_Datos2.Exc_Sql(Connection_BD2,StrSql);
               }
             
           }catch(Exception e){
                 JOptionPane.showMessageDialog( null,e.getMessage());         
           }
         
    }
    
    public void setAccciones(int IdTaller){
        try{
            
             String QRY_SQL = "SELECT ID, Nombre, ID_Taller "+
                             "FROM  Accion "+
                             "WHERE ID_Taller ="+IdTaller;
             ResultSet  Result = JBase_Datos.SQL_QRY(Connection_BD,QRY_SQL);
             while (Result.next()){
                 String xId = Result.getString(1);
                 String xIdAccion  = Result.getString(2);
                 int xIdTaller    = Result.getInt(3);
                 ExisteAcciones(xId, xIdAccion , xIdTaller);
             }
             
        }catch(Exception e){
            JOptionPane.showMessageDialog( null,e.getMessage());
        }
        
    }
    public void ExisteAcciones(String Id , String NombreAccion , int Taller){
        try{
              String StrSql = "SELECT IdTaller "+
                        "FROM JAcciones "+
                        "WHERE IDTaller ="+Taller +" AND IdAccion = '"+Id+"'";
              ResultSet  Result = JBase_Datos2.SQL_QRY(Connection_BD2,StrSql);              
              if (!Result.next()){
                 StrSql = "INSERT INTO JAcciones"+
                          "(IdTaller,IdAccion,Nombre)"+
                          " VALUES "+
                          "('"+Taller+"' , '"+Id.trim()+"' , '"+NombreAccion.trim()+"')";
                 boolean  Result2 = JBase_Datos2.Exc_Sql(Connection_BD2,StrSql);
             }
        
           }catch(Exception e){
                 JOptionPane.showMessageDialog( null,e.getMessage());         
        }
    }
    public void setCausaAccion(int IdTaller){
        try{
             String QRY_SQL = "SELECT ID_Causa, ID_Accion, ID_Taller, RelacionDirecta "+
                             "FROM  CausaAccion "+
                             "WHERE ID_Taller ="+IdTaller;
             ResultSet  Result = JBase_Datos.SQL_QRY(Connection_BD,QRY_SQL);
             while (Result.next()){
                 String xIdCausa = Result.getString(1);
                 String xIdAccion  = Result.getString(2);
                 int xIdTaller    = Result.getInt(3);
                 int xRelacion    = Result.getInt(4);
                 ExisteCausaAccion(xIdCausa, xIdAccion , xIdTaller, xRelacion);
             }
        }catch(Exception e){
            JOptionPane.showMessageDialog( null,e.getMessage());
        }
    }
    public void ExisteCausaAccion(String idCausa, String idAccion , int idTaller , int Relacion){
        
         try{
              String StrSql = "SELECT IdTaller "+
                        "FROM JAccionCausas "+
                        "WHERE IDTaller ="+idTaller +" AND IdCausas = '"+idCausa.trim()+"' AND IdAccion = '"
                        +idAccion.trim()+"'";
              ResultSet  Result = JBase_Datos2.SQL_QRY(Connection_BD2,StrSql);              
              if (!Result.next()){
                 StrSql = "INSERT INTO JAccionCausas"+
                          "(IdTaller,IdCausas,IdAccion,Relacion)"+
                          " VALUES "+
                          "('"+idTaller+"' , '"+idCausa.trim()+"' , '"+idAccion.trim()+"' , '"+Relacion+"')";
                 boolean  Result2 = JBase_Datos2.Exc_Sql(Connection_BD2,StrSql);
             }
           }catch(Exception e){
                 JOptionPane.showMessageDialog( null,e.getMessage());         
        }
        
    }
    
    
 }
