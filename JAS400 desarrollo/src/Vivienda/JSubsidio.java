/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vivienda;

import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author GACA1186
 */
public class JSubsidio {
    private JConection JBase_Datos;
    private Connection Cn;
    private String Periodo;
    public JSubsidio(JConection JBase_Datos3, Connection Cn2, String PPeriodo){
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.Periodo = PPeriodo;
        this.Inicializa();
    }
    public void Inicializa(){
        try {
            String Str_Sql= " DELETE FROM selinlib.ZPUNTUACI1 ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            Str_Sql=" DELETE FROM selinlib.ZPUNTUACI0";
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            Str_Sql=" DELETE FROM selinlib.ZPUNTUACI2";
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            Str_Sql=" DELETE FROM selinlib.ZPUNTUACI3";
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            Str_Sql=" DELETE FROM selinlib.ZPUNTUACI4";
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            Str_Sql=" DELETE FROM selinlib.ZPUNTUACI5";
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            Str_Sql=" DELETE FROM selinlib.ZPUNTUACI6";
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            
            Str_Sql="insert into selinlib.ZPUNTUACI0     \n" +
                    "select  t1.fipnro                   \n" +
                    "from vivienda.FINSPOS t1            \n" +
                    "where calper = =" +this.Periodo+
                    "group by t1.fipnro                  ";
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            Str_Sql="Insert into selinlib.ZPUNTUACI1               \n" +
                    "select  calper , t1.fipnro , count(*)         \n" +
                    "from vivienda.DINSPOS t1 ,vivienda.FINSPOS t2 \n" +
                    "where  t1.FIPNRO = t2.FIPNRO                  \n" +
                    "and calper =" +this.Periodo+
                    "group by calper , t1.fipnro                   \n" +
                    "order by fipnro                               ";
            
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Str_Sql="insert into selinlib.ZPUNTUACI3   \n" +
                    "select HISDOC , HISNRO , HISNOV   \n" +
                    "from vivienda.mdhispo             ";
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            Str_Sql="INSERT INTO  SELINLIB.ZPUNTUACI4         \n" +
                    "SELECT HISDOC , HISNOV                   \n" +
                    "FROM vivienda.MDHISPO WHERE HISCFA =" +this.Periodo;
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            Str_Sql="INSERT INTO SELINLIB.ZPUNTUACI5 \n" +
                    "SELECT  TEMICH , TEMFEC         \n" +
                    "FROM vivienda.TTEMCAL           \n" +
                    "WHERE temcal =" +this.Periodo;
            rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Inicializa() "+e.getMessage());  
        }
    }
    
    public void Calculo(){
        try {
            String Str_Sql ="Select * from selinlib.ZPUNTUACI0";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            double B1, B2,B3,B4,B5,B6;
            double VlrIngreso,VlrFamilia, VlrAhorros,  VlrCesantias;
            while(rs.next()){
                B1 = 0;             
                B2 = 0;             
                B3 = 0;             
                B4 = 0;             
                B5 = 0;             
                B6 = 0;             
                VlrIngreso   = 0 ;  
                VlrFamilia   = 0 ;  
                VlrAhorros   = 0 ;  
                VlrCesantias = 0 ;  
                VlrIngreso   = 0 ;  
                int NroFormulario = rs.getInt("FNRO");
                Str_Sql=" select * from selinlib.DINSPOS where FIPNRO="+NroFormulario;       
                ResultSet rs2 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);       
                // Recorrido sobre el grupo Familiar.
                int Doc=0;
                while(rs2.next()){
                    
                    if(rs2.getString("FIMOCU").equals("EM")){
                        Doc = rs2.getInt("FIMDOC") ;    
                    }       
                    
                    if (rs2.getDouble("FIMING") != 0){                                 
                            VlrIngreso = VlrIngreso  +rs2.getDouble("FIMING") ;        
                    }
                    
                    //Busca si los miembros del grupo familiar cuenta con condicion especial 
                    if (!rs2.getString("FIMCES").trim().equals("")) {
                        B3 = 1;   
                    }
                    
                }
                rs2.close();
                Str_Sql="select * from selinlib.ZPUNTUACI4 where Document="+Doc;
                ResultSet rs3 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);       
                int NoAplica=0;
                if(rs3.next()){
                  if (!rs3.getString("NOVED").trim().equals("01")){
                    NoAplica=1;
                  }
                }
                rs3.close();
                //Puntaje del SISBEN
                
                if(NoAplica==0){
                    B1 = (VlrIngreso/39880);
                    Str_Sql="select * from selinlib.ZPUNTUACI1 where FNRO="+NroFormulario;
                    ResultSet rs4 = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);       
                    VlrFamilia =0;
                    if(rs4.next()){
                        VlrFamilia=rs3.getInt("Cantidad");
                    }
                     if (VlrFamilia == 2){
                          B2 = 1;           
                     }
                     if (VlrFamilia == 3){
                          B2 = 2;           
                     }
                    if (VlrFamilia == 4){
                          B2 = 3;           
                     }
                    if (VlrFamilia >=5){
                          B2 = 4;           
                     }
 
                }   
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Calculo() "+e.getMessage());  
        }
    }
    
}
