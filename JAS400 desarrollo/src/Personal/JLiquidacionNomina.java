package Personal;


import BD_Auditoria.JConeccion_AudiSys;
import java.sql.Connection;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */
public class JLiquidacionNomina {
    
    private JConeccion_AudiSys JBase_Datos;
    private Connection Connection_BD;
    
    private int Documento;
    private double SalarioMensual;
    private double SalarioBasico;
    private double NumeroHorasLaboralesLegales = 8;
    private double DiasTrabajados;
    
    private double HorasLaborales, ValorHoraLaboral;
    private double HorasExtrasDiurnas, ValorHoraExtraDiurna;
    private double HorasExtrasNoturna, ValorHoraExtraNocturno;
    
    
    private double HorasDomingosFestivosDerechoCompensatorio, ValorHoraDomingoFestivosDerechoCompensatorio;
    private double HorasDomingosFestivosSinDerechoCompensatorio, ValorHoraDomingoFestivosSinDerechoCompensatorio ;
    
    private double HorasExtrasDomingosFestivosNocturna, ValorHoraExtrasDomingoFestivoNocturna;
    private double HorasExtrasDomingosFestivosDiurno, ValorHoraExtrasDomingoFestivoDiurno;
    
    
    private double HorasRecargoDomingoFestivoDiurno, ValorRecargoDomingoFestivoDiurno ;
    private double HorasRecargoDomingoFestivoNocturno, ValorRecargoDomingoFestivoNocturno ;
    
    private double AuxilioTransporte;
    private double AporteSalud;
    private double AportePension;
    private double FondoSoliradidad;
    private double TotalCredito;
    private double Comisiones;
    private double SalarioDevengado;
    private double SalarioNetoPagar;
    private double HorasRecargoNocturno;
    private double ValorRecargoNocturno;
    
    
    public JLiquidacionNomina(JConeccion_AudiSys JBase_Datos , Connection Connection_BD, int PDocumento, double PSalario ){
        
        this.Documento = PDocumento;
        this.SalarioMensual = PSalario;
        this.SalarioBasico = SalarioMensual / 2 ;
        this.DiasTrabajados = 0;
        this.HorasLaborales = 0;
        this.ValorHoraLaboral = 0;
        this.HorasExtrasDiurnas =0;
        this.ValorHoraExtraDiurna = 0;
        
        this.HorasExtrasNoturna = 0;
        this.ValorHoraExtraNocturno = 0;
        
        this.HorasRecargoNocturno = 0;
        this.ValorRecargoNocturno = 0;
        
        this.HorasDomingosFestivosDerechoCompensatorio = 0;
        this.ValorHoraDomingoFestivosDerechoCompensatorio = 0;
        
        this.HorasDomingosFestivosSinDerechoCompensatorio = 0;
        this.ValorHoraDomingoFestivosSinDerechoCompensatorio = 0;

        this.AuxilioTransporte = 67800;
        this.AporteSalud = 0;
        this.AportePension = 0;
        this.FondoSoliradidad = 0;
        this.TotalCredito = 0;
        this.Comisiones = 0;
        this.SalarioDevengado = 0;
        this.SalarioNetoPagar = 0;
        
    }
    
    public void setHorasLaborales(double Houre){
        this.HorasLaborales = Houre;
    }
    public void setHorasExtrasDiurnas(double Houre){
        this.HorasExtrasDiurnas =Houre;
    }
    public void setHorasExtrasNocturnas(double Houre){
        this.HorasExtrasDiurnas = Houre;
    }
    public void setHorasRecargoNocturno(double Houre){
        this.HorasRecargoNocturno =  Houre;
    }
    
    public void setHorasExtrasDiurna_Domingo_Festivo(double Houre){
        this.HorasExtrasDomingosFestivosDiurno = Houre;
    }
    public void setHorasExtrasNocturno_Domingo_Festivo(double Houre){
        this.HorasExtrasDomingosFestivosNocturna = Houre;
    }
    public void setHoras_Recargo_Diurno_Domingo_Festivo(double Houre){
        this.HorasRecargoDomingoFestivoDiurno = Houre;
    }
    public void setHoras_Recargo_Nocturno_Domingo_Festivo(double Houre){
        this.HorasRecargoDomingoFestivoNocturno = Houre;
    }
    public double getNetoaPagar(){
        return this.SalarioNetoPagar;
    } 
    public double getSalarioDevengado(){
        return this.SalarioDevengado;
    }
    public boolean setLiquidacionNomina(){
        
        boolean Guardo = false;
        try{
            
            this.ValorHoraLaboral       =  (this.SalarioBasico / 240);
            this.ValorHoraExtraDiurna   =  this.ValorHoraLaboral * 1.25;
            this.ValorHoraExtraNocturno =  this.ValorHoraLaboral * 1.75;
            this.ValorRecargoNocturno   =  this.ValorHoraLaboral * 1.35; //Personas que tienen un turno de noche
            
            this.ValorHoraExtrasDomingoFestivoDiurno= this.ValorHoraLaboral  *  2;
            this.ValorHoraExtrasDomingoFestivoNocturna= this.ValorHoraLaboral  *  2.50;
            this.ValorRecargoDomingoFestivoDiurno = this.ValorHoraLaboral * 1.75; // Cuando es turno normal
            this.ValorRecargoDomingoFestivoNocturno = this.ValorHoraLaboral * 1.75; // Cuando es turno normal
            
            this.SalarioDevengado =  (this.HorasLaborales * this.ValorHoraLaboral ) +(this.HorasExtrasDiurnas * this.ValorHoraExtraDiurna)
                                    + (this.HorasExtrasNoturna * this.ValorHoraExtraNocturno) +( this.HorasRecargoNocturno*this.ValorRecargoNocturno)
                                    + (this.HorasExtrasDomingosFestivosDiurno*this.ValorHoraExtrasDomingoFestivoDiurno )
                                    + (this.HorasExtrasDomingosFestivosNocturna*this.ValorHoraExtrasDomingoFestivoNocturna )
                                    + (this.HorasRecargoDomingoFestivoDiurno*ValorRecargoDomingoFestivoDiurno)
                                    + (this.HorasRecargoDomingoFestivoNocturno*ValorRecargoDomingoFestivoNocturno)
                                    + this.AuxilioTransporte
                                    + this.Comisiones;
            
            this.AporteSalud = ( this.SalarioDevengado - this.AuxilioTransporte ) * 0.04;
            this.AportePension = this.AporteSalud;
            this.SalarioNetoPagar = this.SalarioDevengado - this.AporteSalud - this.AportePension;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Class: JLiquidacionNomina : setLiquidacionNomina "+e.getMessage());
        }
            return true; 
        
    }
    public boolean setConcepto(int CodigoConcepto, int CodigoTrabajador, int NumeroQuincena, int Mes, int Periodo, int FechaTransaccion ,String TipoNomina , double Horas, double Valor){
        //Generacion Detalle/Volante de Pago para el Trabajador
        boolean Guardo = false;
        try {
            
        } catch (Exception e) {
            
        }
        return Guardo;
    }
    
    
}
