/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author GACA1186
 */
public class Fecha {
    private int año;
    private int mes; // 1 a 12
    private int dia; // 1 a 31

    static int[] diasMes= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
/**
 * Comprueba si la fecha es correcta. Se comprueban solo fechas de 1900 
 * o posteriores.
 *
 *
 * @throws IllegalArgumentException si el año es menor que 1900.
 */
    public void setAño(int PAño){
        this.año = PAño;
    }
    public void setMes(int PMes){
        this.mes=PMes;
    }
    public void setDia(int PDia){
        this.dia=PDia;
    }
    public boolean validaFecha( Fecha fecha){
        if ( fecha.año < 1900 ) {
            throw new IllegalArgumentException( 
                    "Solo se comprueban fechas del año 1900 o posterior");
        }   
        if ( fecha.mes<1 || fecha.mes>12 )
            return false;
            // Para febrero y bisiesto el limite es 29
        if ( fecha.mes==2 && fecha.año%4==0 )
            return fecha.dia>=1 && fecha.dia<=29;
            return fecha.dia>=0 && fecha.dia<=diasMes[fecha.mes-1];
    } 
    
    

     public String getTres_Meses(int PFecha, int meses){
        /*
            Metodo para restar a una fecha en meses
            @param PFecha: fecha a cual se le desea realizar operacion meses: cantidad de meses a operar (+:suma, -resta)
            @return La fecha en formato aaaaMMYY
        */
        Calendar Cal = Calendar.getInstance();
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        String yyyyMMdd="";
        try {
            Integer value = PFecha;
            
            Date Fecha = originalFormat.parse(value.toString());
            Cal.setTime(Fecha);
            Cal.add(Calendar.MONTH, meses);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            yyyyMMdd = sdf.format(Cal.getTime());
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "class:JLiquidaciones:getTres_Meses() "+e.getMessage());
        }
        return yyyyMMdd;
    }
}
