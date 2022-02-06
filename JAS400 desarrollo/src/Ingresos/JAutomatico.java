package Ingresos;


import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */

//Recalcular automatico de Tarjetas Multiservicios

public class JAutomatico {
    private int FechaIncial, FechaFinal;
    private String FechaActual;
    private String CentroCosto;
    private JConection JBase_Datos;
    private Connection Cn;
    private double SaldoAnteriorTarjetas;
    private double TotalVentasTarjetas;
    private double SumaTarjetasPositivas;
    private double SumaTarjetasNegativas;
    private double SumaTarjetasW;
    private double SaldoActualTarjetas;
    private int Tipo;
    private double SaldoVentasGrabado;
    
    public JAutomatico(JConection JBase_Datos3, Connection Cn2, int PFechaInic, int PFechaFin, String PCentroCosto, int PTipo ){
        this.FechaIncial = PFechaInic;
        this.FechaFinal = PFechaFin;
        this.CentroCosto = PCentroCosto;
        this.Cn = Cn2;
        this.JBase_Datos =JBase_Datos3;
        this.Tipo = PTipo;
    }
    public void Proceso(){
       for (int i = this.FechaIncial; i <=this.FechaFinal; i++) {
            if(Tipo==1){
                this.FechaActual = String.valueOf(i);
                this.Generacion_Automatica_Tarjetas();
            }else{
            
            }
       }
    }
    public void Generacion_Automatica_Tarjetas(){
        this.getMovimierntoTarjetas();
        this.getSaldo_Anterior_Tarjetas();
        this.getTarjetas();
        this.Guardar_Tarjetas();
    }
    public void Guardar_Tarjetas(){
        try {
            this.getSaldoActual_Tarjetas();
            this.SaldoActualTarjetas = this.SaldoAnteriorTarjetas + this.SumaTarjetasPositivas - this.SumaTarjetasNegativas-this.TotalVentasTarjetas;
            this.setTarjetas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JAutomatico:Guardar_Tarjetas() "+e.getMessage()); 
        }
    }
    public void getSaldoActual_Tarjetas(){
       try{
            String Centro= Equivalencia_Tarjetas(this.CentroCosto.trim());
            String Str_Sql = "SELECT fecha,salact,ventas FROM selinlib.jtarjetas "
                            +" WHERE fecha ="+this.FechaActual +" AND codigo="+Centro;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                this.TotalVentasTarjetas = rs.getDouble("ventas");
            }
       }catch(Exception e){
           JOptionPane.showMessageDialog(null," JAutomatico:getSaldoActual_Tarjetas()"+e.getMessage()); 
       }
    }
    
    
    public void setTarjetas(){
        try {
            String Centro= Equivalencia_Tarjetas(this.CentroCosto.trim());
            String Str_Sql = "SELECT fecha,salact FROM selinlib.jtarjetas "
                            +" WHERE fecha ="+this.FechaActual +" AND codigo="+Centro;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(!rs.next()){
               Str_Sql = "INSERT INTO selinlib.jtarjetas (fecha,codigo,entrada,salida,salant,ventas,salact) "
                       + " VALUES ("+this.FechaActual+","+Centro+","+this.SumaTarjetasPositivas+","+this.SumaTarjetasNegativas+","+this.SaldoAnteriorTarjetas+","+TotalVentasTarjetas+","+this.SaldoActualTarjetas+")";
            }else{
               System.out.println(" Fecha "+this.FechaActual );
               System.out.println("Postivas "+this.SumaTarjetasPositivas); 
               System.out.println("Postivas "+this.SumaTarjetasNegativas); 
               System.out.println("Postivas "+this.SaldoAnteriorTarjetas); 
               System.out.println("Postivas "+this.SumaTarjetasPositivas); 
               Str_Sql = "UPDATE selinlib.jtarjetas "
                       + "SET entrada="+this.SumaTarjetasPositivas
                       + " ,Salida="+this.SumaTarjetasNegativas
                       + " ,salant="+this.SaldoAnteriorTarjetas
                       + " ,ventas="+TotalVentasTarjetas
                       + " ,salact="+this.SaldoActualTarjetas
                       + " WHERE fecha="+this.FechaActual +" AND codigo="+Centro; 
            }
            boolean Guardar = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            if(Guardar){
                JOptionPane.showMessageDialog(null,"Se Guardo Correctamente "+this.FechaActual);     
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JAutomatico: setTarjetas()"+e.getMessage()); 
        }
    }
    
    public void getMovimierntoTarjetas(){
       try {
           String Centro= Equivalencia_Tarjetas(this.CentroCosto.trim());
           if(!Centro.trim().equals("")){
               //Movimiento de Entradas del Centro de Atencion
               String Str_Sql ="SELECT TIPALB, CODCCO, CCORCV, (UNIDAD *COSULT) as VALOR   FROM SGDATOS.TRENTRF WHERE "
                           + " CODART  = 319649 AND CCORCV = "+Centro+" AND FECHAM ="+this.FechaActual+" "
                           + " AND TIPALB <> 'V'";
               
               ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
               SumaTarjetasPositivas = 0;
               SumaTarjetasNegativas = 0;
               while(rs.next()){
                   double Vlr = rs.getDouble("VALOR");
                   this.SumaTarjetasPositivas =  this.SumaTarjetasPositivas +Vlr;
               }
               
               Str_Sql ="SELECT TIPALB, CODCCO, (UNIDAD *COSULT) as VALOR   FROM SGDATOS.GCENTRF WHERE "
                           + " CODART  = 319649 AND CODCCO = "+Centro+" AND FECHAM ="+this.FechaActual;
               rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
               while(rs.next()){
                   double Vlr = rs.getDouble("VALOR");
                   this.SumaTarjetasPositivas =  this.SumaTarjetasPositivas +Vlr;
               }
               
               //Movimiento de Salidas del Centro de Atencion
               Str_Sql ="SELECT TIPALB, CODCCO, CCORCV, (UNIDAD *COSULT) as VALOR  FROM SGDATOS.TRENTRF WHERE "
                           + " CODART  = 319649 AND CODCCO = "+Centro+" AND FECHAM ="+this.FechaActual
                           + " AND TIPALB <> 'U'"; //20120829
               rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
               while(rs.next()){
                   double Vlr = rs.getDouble("VALOR");
                   this.SumaTarjetasNegativas =  this.SumaTarjetasNegativas +Vlr;
               }
               
               Str_Sql ="SELECT 'W' AS TIPALB, CODCCO, (UNIDAD *COSULT) as VALOR FROM SGDATOS.GCSALIF WHERE fecham="+this.FechaFinal+""
                       + " AND CODCCO="+Centro+" AND CODART=319649";
               rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
               SumaTarjetasW = 0;
               while(rs.next()){
                   double Vlr = rs.getDouble("VALOR");
                   this.SumaTarjetasW=  this.SumaTarjetasW +Vlr;
               }
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null," JAutomatico: getMovimierntoTarjetas() "+e.getMessage()); 
       }
   }
    public void getTarjetas(){
        try {
            String Centro= Equivalencia_Tarjetas(this.CentroCosto);
            String Str_Sql = "SELECT ventas,salact FROM selinlib.jtarjetas "
                            +" WHERE fecha ="+this.FechaActual +" AND codigo="+Centro;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                this.TotalVentasTarjetas =rs.getDouble("ventas");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JAutomatico: getTarjetas()"+e.getMessage()); 
        }
    } 
    
    public void getSaldo_Anterior_Tarjetas(){
        try {
           ////////////////////////////////////////////////////////////////////////////////
           ////////////////////////////////////////////////////////////////////////////////
           //////////// Se corrijio para año bisisesto                         ///////////
           ////////////////////////////////////////////////////////////////////////////////
           ////////////////////////////////////////////////////////////////////////////////
           
           String FechaAnterior = RestarDias_Saldos_Tarjertas_CAC();
           String Centro= Equivalencia_Tarjetas(this.CentroCosto.trim());
           String Str_Sql ="SELECT codigo , salact FROM selinlib.jtarjetas WHERE fecha="+FechaAnterior +""
                           + " AND codigo ="+Centro;
           ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
           if(rs.next()){
               this.SaldoAnteriorTarjetas = rs.getDouble("salact");
           }
            System.out.println("Saldo Anterior "+FechaAnterior+" --  Valor "+this.SaldoAnteriorTarjetas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JAutomatico: getSaldo_Anterior_Tarjetas() "+e.getMessage()); 
        }
            
    }
     
    public String RestarDias_Saldos_Tarjertas_CAC(){
        int Dia = Integer.parseInt(this.FechaActual.substring(6, 8));
        String zMes = this.FechaActual.substring(4, 6);
        String zDia ="";
        int zAño =  Integer.parseInt( this.FechaActual.substring(0, 4) );
        if ( Dia  ==  1){
            zMes = this.getMes(zMes);
            if(Integer.parseInt(zMes)== 12){
                zAño = zAño -1;
            }
            try {
                String Str_Sql = " select   max(substring( fecha , 7 ,2 )) as dia "+
                                 " from selinlib.jtarjetas   "+
                                 " where substring(fecha , 5 , 2) = "+zMes +" and substring( fecha , 1 ,4 )="+zAño;
                //System.out.println(""+Str_Sql);
                ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                if(rs.next()){
                    zDia = rs.getString("dia");
                    Dia = Integer.parseInt(zDia);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"JAutomatico: RestarDias() "+e.getMessage());
            }
        }else{
            Dia = Dia - 1 ;
        }
        
        if (Dia<10){
            zDia = "0"+String.valueOf(Dia);
        }else{
            zDia = String.valueOf(Dia);
        }
        return zAño+zMes+zDia;
    }
     public String getMes(String xMes){
        String Mes = "";
        int zMes = Integer.parseInt(xMes);
        zMes = zMes - 1;
        if (zMes == 0){
           Mes="12";
           zMes = 12;
        }
        if (zMes < 10){
                Mes = "0"+ String.valueOf(zMes);
        }else{
             Mes =  String.valueOf(zMes);
        }
        return Mes ;
    }
    public String Equivalencia_Tarjetas_User(String Codigo){
       String XCodigo = "";
       try {
            String Str_Sql = "Select cac,codtarj from Selinlib.JCCentros where codtarj="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getString("cac");
            }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null," JAutomatico: Equivalencia_Tarjetas() "+e.getMessage()); 
       }
       return XCodigo;
   }  
   public String Equivalencia_Tarjetas(String Codigo){
       String XCodigo = "";
       try {
            String Str_Sql = "Select cac,codtarj from Selinlib.JCCentros where cac="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getString("codtarj");
            }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null," JAutomatico: Equivalencia_Tarjetas() "+e.getMessage()); 
       }
       return XCodigo;
   }   
     
}
