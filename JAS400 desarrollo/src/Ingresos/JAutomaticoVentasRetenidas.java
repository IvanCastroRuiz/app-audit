package Ingresos;


import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;



class JAutomaticoVentasRetenidas{
    
    private Connection Connection_BD;
    private JConection JBase_Datos;
    private String FechaInicial, FechaFinal;
    private double SaldoAnteriorVentasRetenidas, SaldoAnteriorCanjeRetenido;
    
    public JAutomaticoVentasRetenidas(String FecIni, String FecFin,JConection JBase_Datos2 , Connection Cn3){
        this.Connection_BD = Cn3;
        this.JBase_Datos = JBase_Datos2;
        this.FechaInicial = FecIni;
        this.FechaFinal = FecFin;
    }
    
    public void Genera_Ventas_Automatico(){
        try {
            int FecInicial = Integer.parseInt(this.FechaInicial.trim());
            int FecFinal  = Integer.parseInt(this.FechaFinal.trim());
            for (int i = FecInicial; i <= FecFinal; i++) {
                this.getIngresos(i);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JAutomaticoVentasRetenidas:Genera_Ventas_Automatico() "+e.getMessage());
        }
    }
    public void getIngresos(int fecha){
        try {
             String Str_Sql ="Select * from selinlib.jingreso where fecha="+fecha;
             ResultSet rs = JBase_Datos.SQL_QRY(this.Connection_BD,Str_Sql);
             
             while (rs.next()) {
                
                double EfectivoConsignar = rs.getDouble("EFTCONS") ;
                double VentaGnb = rs.getDouble("VTAGNB");
                double AbonoVentaRetenida = rs.getDouble("ABNRET");
                double VentaRetenida = rs.getDouble("VTARET");
                double CambioCheque = rs.getDouble("CMBCHEQ");
                
                
                double CanjeEsperado = rs.getDouble("CNJESPER");
                double CanjeXConsignar = rs.getDouble("CNJCONSI");
                double CambioChequeCanje = rs.getDouble("CCMBCHEQ");
                double ChequePendienteConsignar = rs.getDouble("CHQPCONS");
                 
                this.getIngresos_Dia_Antes(RestarDias(String.valueOf(fecha))); 
                
                double VVentaRetenida = rs.getDouble("VVLRRETENI");
                double VValorAbonado = rs.getDouble("VVLRABONAD");
                double VSaldoActual = this.SaldoAnteriorVentasRetenidas+VVentaRetenida-VValorAbonado;
                
                
                double CValorRetenida = rs.getDouble("CVLRRETENI");
                double CValorAbonado  = rs.getDouble("CVLRABONAD");
                double CSaldoActual   = this.SaldoAnteriorCanjeRetenido +CValorRetenida-CValorAbonado;
                
                this.setIngresos(fecha,VSaldoActual, CSaldoActual);
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JAutomaticoVentasRetenidas:getIngresos()"+e.getMessage());
        }
    }
    public void getIngresos_Dia_Antes(String Fecha){
        try {
             String Str_Sql ="Select SALACTUAL, CALACTUAL from selinlib.jingreso where fecha="+Fecha;
             ResultSet rs = JBase_Datos.SQL_QRY(this.Connection_BD,Str_Sql);
             while (rs.next()) {
                 this.SaldoAnteriorVentasRetenidas = rs.getDouble("SALACTUAL");
                 this.SaldoAnteriorCanjeRetenido = rs.getDouble("CALACTUAL");
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JAutomaticoVentasRetenidas:getIngresos_Dia_Antes()"+e.getMessage());
        }
    }
    
     public String RestarDias(String PFecha){
         
        int Dia = Integer.parseInt(PFecha.substring(6, 8));
        String zMes = PFecha.substring(4, 6);
        String zDia ="";
        int zAño = Integer.parseInt( PFecha.substring(0, 4));
        if ( Dia  ==  1){
            zMes = this.getMes(zMes);
            if(Integer.parseInt(zMes)== 12){
                zAño = zAño -1;
            }
            try {
                String Str_Sql = " select   max(substring( fecha , 7 ,2 )) as dia "+
                                 " from selinlib.jingreso   "+
                                 " where substring(fecha , 5 , 2) = "+zMes ;
                ResultSet rs = JBase_Datos.SQL_QRY(this.Connection_BD,Str_Sql);
                if(rs.next()){
                    zDia = rs.getString("dia");
                    Dia = Integer.parseInt(zDia);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Form:JIngresos: RestarDias() "+e.getMessage());
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
    
    
    public void setIngresos(int Fecha, double VSaldoActual, double CSaldoActual){
        try {
            String Str_Sql = "UPDATE selinlib.jingreso "
                            + "set SALACTUAL = "+VSaldoActual
                            + ", CALACTUAL ="+CSaldoActual 
                            + " where fecha = "+Fecha;
            boolean SQL = JBase_Datos.Exc_Sql(this.Connection_BD,Str_Sql);
            if (SQL){
                JOptionPane.showMessageDialog(null,"Se Guardo Correctamente -> "+Fecha);
            }else{
                JOptionPane.showMessageDialog(null,"NO SE PUDO Guardar Correctamente -> "+Fecha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JAutomaticoVentasRetenidas:setIngresos()"+e.getMessage());
        }
    }
    
    
}