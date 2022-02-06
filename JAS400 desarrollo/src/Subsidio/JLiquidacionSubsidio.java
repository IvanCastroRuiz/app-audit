package Subsidio;


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
public class JLiquidacionSubsidio {
    private JConection JBase_Datos;
    private Connection Cn;
    private String PeriodoInicial, PeriodoFinal;
    private  double Slmv , ValorSubsidio;
    public JLiquidacionSubsidio(JConection JBase_Datos3, Connection Cn2,String PeriodoI, String PeriodoF){
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.PeriodoInicial = PeriodoI;
        this.PeriodoFinal = PeriodoF;
        Slmv =0;
        ValorSubsidio=0;
    }
    public JLiquidacionSubsidio(JConection JBase_Datos3, Connection Cn2){
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
    }
    
    public void getLiquidacion(){
        try {
            String Str_Sql = "Select pfeliq, ppeliq from subsilib.liquipag where ppeliq>="+this.PeriodoInicial+" and ppeliq <="+this.PeriodoFinal+" "
                            + " group by pfeliq , ppeliq ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(!rs.next()){
                String PFecha = rs.getString("pfeliq").trim();
                String Fecha = PFecha.substring(0,6)+"01";
                this.getParametrosVigentes(Fecha);
                this.getVerificarLiquidacionSubsidio(PFecha,rs.getString("ppeliq").trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JLiquidacionSubsidio:getLiquidacion()"+e.getMessage()); 
        }
    }
    public void getVerificarLiquidacionSubsidio(String Fecha, String Periodo){
        try {
            String Str_Sql = "Select * from subsilib.liquipag where pfeliq="+Fecha+" and ppeliq="+Periodo;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(rs.next()){
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JLiquidacionSubsidio:getVerificarLiquidacionSubsidio()"+e.getMessage()); 
        }
    }
    public void getParametrosVigentes(String FechaParam){
        try {
            String Str_Sql = "Select * from subsilib.mparam where parcod >= "+FechaParam;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
              this.Slmv = rs.getDouble("parvsm");
              this.ValorSubsidio = rs.getDouble("parvsn");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JLiquidacionSubsidio:getParametrosVigentes()"+e.getMessage()); 
        }
    }
 
}
