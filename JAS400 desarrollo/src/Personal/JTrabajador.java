/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personal;

import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author GACA1186
 */
public class JTrabajador {
    /*
       Propiedades del modelo
    */
    private String codigo;
    private String documento_identidad;
    private String nombre;
    private int fecha_ingreso;
    private int fecha_baja;
    private String estado;
    private boolean existe;
    /*
      Mapping base datos
    */
    private JConection JBase_Datos;
    private Connection Cn;

   
    public JTrabajador(JConection JBase_Datos3, Connection Cn2){
        JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        existe = false;
    }
    public boolean existe_trabajador(String p_documento_identidad){
        this.existe =  this.get_trabajador(p_documento_identidad);
        return existe;
    }
    public boolean get_trabajador(String p_documento_identidad){
        try {
            String Str_Sql = " select * from adamco.ttrab as t1 , adamco.TTRNS as t2 where t1.tracve =t2.tracve and trim(t2.TRARFI)='"+p_documento_identidad.trim()+"'";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                this.codigo = rs.getString("tracve");
                this.documento_identidad = p_documento_identidad.trim();
                this.nombre = rs.getString("TRANOM");
                this.fecha_ingreso = rs.getInt("TRAFIN");
                this.fecha_baja = rs.getInt("TRAFBA");
                this.estado = rs.getString("TRASIT");
                return true;
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "class:"+this.getClass().getName()+"Exception "+e.getMessage());
        }
        return false;
    }
    
    public double get_horas_extras_al_125(String año_analisis, String mes_inicial , String mes_final){
        try {
            /*
               tipo_nomina: PN (Pagos no realizados), QN (Quincena Ordinaria)
               Maestros: TACNO or TACNM
               Buscar: 1.25% 125% 25%
            */
            String Str_Sql ="";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "class:"+this.getClass().getName()+"get_horas_extras_al_125() Exception "+e.getMessage());
        }
        return 0;
    } 
    
     public double get_horas_extras_al_135(String año_analisis, String mes_inicial , String mes_final){
        try {
            /*
               tipo_nomina: PN (Pagos no realizados), QN (Quincena Ordinaria)
               Maestros: TACNO or TACNM
               Buscar: 1.35% 135% 35%
            */
            String Str_Sql ="";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "class:"+this.getClass().getName()+"get_horas_extras_al_125() Exception "+e.getMessage());
        }
        return 0;
    } 
     public double get_horas_extras_al_175(String año_analisis, String mes_inicial , String mes_final){
        try {
            /*
               tipo_nomina: PN (Pagos no realizados), QN (Quincena Ordinaria)
               Maestros: TACNO or TACNM
               Buscar: 1.75% 175% 75%
            */
            String Str_Sql ="";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "class:"+this.getClass().getName()+"get_horas_extras_al_125() Exception "+e.getMessage());
        }
        return 0;
    }  
     
    public double get_horas_extras_al_200(String año_analisis, String mes_inicial , String mes_final){
        try {
            /*
               tipo_nomina: PN (Pagos no realizados), QN (Quincena Ordinaria)
               Maestros: TACNO or TACNM
               Buscar: 2.00% 200% 
            */
            String Str_Sql ="";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "class:"+this.getClass().getName()+"get_horas_extras_al_125() Exception "+e.getMessage());
        }
        return 0;
    }  
    public double get_horas_extras_al_210(String año_analisis, String mes_inicial , String mes_final){
        try {
            /*
               tipo_nomina: PN (Pagos no realizados), QN (Quincena Ordinaria)
               Maestros: TACNO or TACNM
               Buscar: 2.10% 210% 10%
            */
            String Str_Sql ="";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "class:"+this.getClass().getName()+"get_horas_extras_al_125() Exception "+e.getMessage());
        }
        return 0;
    }  
    public double get_horas_extras_al_250(String año_analisis, String mes_inicial , String mes_final){
        try {
            /*
               tipo_nomina: PN (Pagos no realizados), QN (Quincena Ordinaria)
               Maestros: TACNO or TACNM
               Buscar: 2.50% 250% 50%
            */
            String Str_Sql ="";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "class:"+this.getClass().getName()+"get_horas_extras_al_125() Exception "+e.getMessage());
        }
        return 0;
    }  

    public double [] get_dias_importe_no_laborados_licencia_no_remumeradas(String año_analisis, String mes_inicial, String mes_final){
        double licencia_no_remunerada[] = new double [2]; 
        try {
            /*
               tipo_nomina: QN (Quincena Ordinaria)
               codigo_novedad: 221 (Hrs sin importe) o 2220 (Dias con importe)
               licencia_no_remunerada:  [0] importe en moneda local , [1]= tiempo en dias
            */
            String Str_Sql =" select sum(TRNIMP) as valor , sum(TRNTIE) as dias from adamco.tacno as t1 where  t1.TNOCVE='QN' and concod='2220' "
                    + "and tracve ='"+this.codigo+"' and t1.concod =2220 and CPRAÑO="+año_analisis+""
                    + " and CPRMAC between "+mes_inicial+" and "+mes_final+" ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                licencia_no_remunerada[0]=rs.getDouble("valor");
                licencia_no_remunerada[1]=rs.getDouble("dias");
            }
            rs.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "class:"+this.getClass().getName()+"get_dias_no_laborados_licencia_no_remuneradas() Exception "+e.getMessage());
        }
        return licencia_no_remunerada;
    }
    
    public int get_dias_laborados(int p_año_analisis){
        int dias_laborados = -1;
        Calendar Cal = Calendar.getInstance();
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        String yyyyMMdd="";
        try {
            int fecha_analisis = p_año_analisis-1;
            String fecha = fecha_analisis+"0101";
            if(this.fecha_ingreso<=Integer.parseInt(fecha)){
                dias_laborados = 360;
            }else{
                //Primero Enero siempres es festivo por tal motivo no inica con los 360 dias
                String fecha_final = p_año_analisis+"1231";
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                Date fechaInicial=dateFormat.parse(String.valueOf(this.fecha_ingreso));
                Date fechaFinal=dateFormat.parse(fecha_final);
                dias_laborados = (int) ((fechaFinal.getTime()-fechaInicial.getTime())/ (1000*60*60*24) ); //86400000);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Class:"+this.getClass().getName()+":get_dias_laborados(int p_año_analisis) "+e.getMessage());
        }
        return dias_laborados ;
    }
    public String get_codigo(){
        return this.codigo;
    }
    public String get_documento(){
        return this.documento_identidad;
    }
    public String get_nombre(){
        return this.nombre;
    }
    public int get_fecha_ingreso(){
        return this.fecha_ingreso;
    }
    public int get_fecha_retiro(){
        return this.fecha_baja;
    }
    public String get_estado(){
        return this.estado;
    }

}
