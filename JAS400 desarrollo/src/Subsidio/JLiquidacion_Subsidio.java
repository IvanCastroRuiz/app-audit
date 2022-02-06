/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Subsidio;

import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Garyn Carrillo
 */
public class JLiquidacion_Subsidio {
    
    
    private JHijos MHijos;
    private JHijos MHijos_Validacion;
    private JConection JBase_Datos;
    private Connection Cn;
    private String PeriodoInicial, PeriodoFinal;
    private  double Slmv , ValorSubsidio;
    private String FechaActual;
    
    public JLiquidacion_Subsidio(JConection JBase_Datos3, Connection Cn2,String PeriodoI, String PeriodoF){
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.PeriodoInicial = PeriodoI;
        this.PeriodoFinal = PeriodoF;
        Slmv =0;
        ValorSubsidio=0;

    }
    
    public void Liquida(){
        try {
            
            this.MHijos = new JHijos(this.JBase_Datos,this.Cn);
            String Str_Sql ="select * from subsilib.mtrabaj ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            int Documento = 0;
            while(rs.next()){
                Documento = rs.getInt("tradoc");
                if(Documento!=0){
                    //Validar la planilla unica
                    //Validar el Salario con la conyugue

                    //Verificar que la novedades sean aplicadas.


                    //VERIFICAR HIJOS
                    this.MHijos.setDocumentoTrabajador(Documento);
                    this.MHijos.getInformacion();
                    if(this.MHijos.getCantidad()>1){
                        this.Recorrer_hijos();
                    }
                    //VERIFICAR PADRES
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JLiquidacion_Subsidio:Liquida()"+e.getMessage()); 
        }
    }
    public void Recorrer_hijos(){
        try {
            if(this.MHijos.getCantidad()>1){
                for (int i = 0; i < this.MHijos.getCantidad(); i++) {
                    //Validar Edad del hijo 
                    if(Integer.parseInt(this.MHijos.getEdad_V().elementAt(i).toString())<19){
                        int DocHij= Integer.parseInt(this.MHijos.getDocumento_V().elementAt(i).toString());
                        // Validar con el numero de identididad del hijo relacion con conyugue 
                        // y verificar salarios de una.
                                                
                        
                        //Liquidacion de Valores
                        int Discapacidad = Integer.parseInt(this.MHijos.getDiscapacitado_V().elementAt(i).toString());
                    }else{
                        
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JLiquidacion_Subsidio:Liquida():Recorrer_Hijos()"+e.getMessage()); 
        }
    }
    
    
    
}
