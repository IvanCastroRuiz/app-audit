package Personal;

import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Garyn Carrillo
 */

public class JRetencionEmpleados1 {
    
    private JConection JBase_Datos;
    private Connection Cn;
    private String PeriodoInicial, PeriodoFinal, Año, AñoF, SPeriodoInicial, SPeriodoFinal;
    private  double Slmv , ValorSubsidio;
    private Object [] Cabecera ;
    private Object [] [] Detalle  ;
    
    private Object [] Cabecera2 ;
    private Object [] [] Detalle2  ;
    
    private int Posicion1;
    private DecimalFormat JFormato ;
    private String NumeroFormato = "###,###,###,###.##";
    
    public JRetencionEmpleados1(JConection JBase_Datos3, Connection Cn2,String PPeriodoI, String PPeriodoF,String SPeriodoI, String SPeriodoF, String AñoP, String AñoP2){
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.PeriodoInicial = PPeriodoI;
        this.PeriodoFinal = PPeriodoF;
        this.SPeriodoInicial = SPeriodoI;
        this.SPeriodoFinal = SPeriodoF;       
        
        this.Año = AñoP;
        this.AñoF = AñoP2;
        Slmv =0;
        ValorSubsidio=0;
        Cabecera =  new Object [7];
        Detalle  = new Object [200][7];
        Cabecera2 =  new Object [8];
        Detalle2  = new Object [200][8];
        JFormato= new DecimalFormat(NumeroFormato);
        this.getEmpleadosRetenciones();
        
    }
    
    public int getCantidadSalario(){
        return this.Posicion1;
    }
    public Object [] getCabeceraSalario(){
        return  Cabecera;
    }
    public Object [][] getDetalleSalario(){
        return  Detalle;
    }
    public Object [] getCabeceraSalarioQ(){
        return  Cabecera2;
    }
    public Object [][] getDetalleSalarioQ(){
        return  Detalle2;
    }
    public void setAñoInicial(String AñoP){
        this.Año = AñoP;
    }
    public void setAñoFinal(String AñoF){
        this.AñoF = AñoF;
    }
    public void setMesInicial(String MesInicialP){
        this.PeriodoInicial = MesInicialP;
    }
    public void SetMesFinal(String MesFinal){
        this.PeriodoFinal = MesFinal;
    }
    
    public void setSegundoMesInicial(String MesInicialP){
        this.SPeriodoInicial = MesInicialP;
    }
    public void SetSegundoMesFinal(String MesFinal){
        this.SPeriodoFinal = MesFinal;
    }
    
    
    
    public double getRetencionAplicada(String Tracve){
        double Vlr=0;
        try {
            String Str_Sql ="Select * from selinlib.JEMPLERETE where tracve="+Tracve;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while(rs.next()){
                Vlr = rs.getDouble("Valor");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getRetencionAplicada()"+e.getMessage()); 
        }
        return  Vlr;
    }
    public void getLiquidacionViaticos(){
        try {
            // 2000 Salario Basico,6000 fond o de solidaridad, 6010 Pension, 6030 Salud
            String Str_Sql = " Delete from selinlib.jviatico ";
            boolean rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);

            Str_Sql = " insert into selinlib.jviatico Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                            + " t1.concod = t2.concod and (t1.CPRPER>="+this.PeriodoInicial+" and t1.CPRPER<="+this.PeriodoFinal+" and T1.CPRAÑO="+this.Año+")  "
                            + " and t1.concod in(3700,6270,3710) and TNOCVE='QN' "
                            + " group by t1.tracve, t4.tranom,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = " insert into selinlib.jviatico Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                            + " t1.concod = t2.concod and (t1.CPRPER>="+this.SPeriodoInicial+" and t1.CPRPER<="+this.SPeriodoFinal+" and T1.CPRAÑO="+this.AñoF+")  "
                            + " and t1.concod in(3700,6270,3710) and TNOCVE='QN' "
                            + " group by t1.tracve, t4.tranom,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "Select tracve, nombre as tranom , concod, sum(salario) as trnimp from selinlib.jviatico "
                    + " group by tracve, nombre, concod order by tracve , concod ";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Cabecera[0]="Codigo";
            Cabecera[1]="Nombre";
            Cabecera[2]="Valor Retencion";
            Cabecera[3]=" ";
            Cabecera[4]=" ";
            Cabecera[5]="Valor Viaticos";
            Cabecera[6]=" ";
            this.setTabla();
            this.Posicion1 = 0;
            double VlrPrima = 0;
            String Tracve;
            String Tracve2="";
            double Vlr = 0;
            while(rs.next()){
                Tracve = rs.getString("tracve");
                if(!Tracve.equals(Tracve2)){
                    Tracve2=Tracve;
                    Vlr = 0;
                }
                Detalle[Posicion1][0]= Tracve;
                Detalle[Posicion1][1]=rs.getString("tranom").trim();
                Detalle[Posicion1][2]= JFormato.format(this.getRetencionAplicada(Tracve));
                Vlr = Vlr + rs.getDouble("trnimp");
                Detalle[Posicion1][5]=JFormato.format(Vlr);
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacion()"+e.getMessage()); 
        }
    }
    
    
    public void getLiquidacionVacaciones(){
        try {
            // 2000 Salario Basico,6000 fondo de solidaridad, 6010 Pension, 6030 Salud
            String Str_Sql = " Delete from selinlib.jvaca ";
            boolean rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "  insert into selinlib.jvaca Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                            + " t1.concod = t2.concod  and (t1.CPRMAC>="+this.PeriodoInicial+" and t1.CPRMAC<="+this.PeriodoFinal+" and T1.CPRAÑO="+this.Año+")  "
                            + " and t1.concod in(3100,3200,6000,6010, 6030) and TNOCVE='LV' "
                            + " group by t1.tracve, t4.tranom,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            Str_Sql = "  insert into selinlib.jvaca Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                            + " t1.concod = t2.concod  and (t1.CPRMAC>="+this.SPeriodoInicial+" and t1.CPRMAC<="+this.SPeriodoFinal+" and T1.CPRAÑO="+this.AñoF+")  "
                            + " and t1.concod in(3100,3200,6000,6010, 6030) and TNOCVE='LV' "
                            + " group by t1.tracve, t4.tranom,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "Select tracve, nombre as tranom , concod, sum(salario) as trnimp from selinlib.jvaca "
                    + " group by tracve, nombre, concod order by tracve , concod ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Cabecera[0]="Codigo";
            Cabecera[1]="Nombre";
            Cabecera[2]="Valor Retencion";
            Cabecera[3]="Salario";
            Cabecera[4]="Salud";
            Cabecera[5]="Pension";
            Cabecera[6]="Fondo de Solidaridad";
            this.setTabla();
            this.Posicion1 = 0;
            double VlrPrima = 0;
            while(rs.next()){
                String Tracve = rs.getString("tracve");
                Detalle[Posicion1][0]= Tracve;
                Detalle[Posicion1][1]=rs.getString("tranom").trim();
                Detalle[Posicion1][2]= JFormato.format(this.getRetencionAplicada(Tracve));
                int Codigo = rs.getInt("concod");
                double Vlr = rs.getDouble("trnimp");
                
                if((Codigo==3100) || (Codigo == 3200)){
                    VlrPrima =  VlrPrima +Vlr;
                }
                if(Codigo==6000){
                    Detalle[Posicion1][6]=JFormato.format(Vlr);
                }
                if(Codigo==6030){
                    Detalle[Posicion1][4]=JFormato.format(Vlr);
                    Detalle[Posicion1][3]= JFormato.format(VlrPrima);
                    VlrPrima = 0;
                    this.Posicion1++;
                }
                if(Codigo==6010){
                    Detalle[Posicion1][5]=JFormato.format(Vlr);
                }
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacion()"+e.getMessage()); 
        }
    }
    public void getLiquidacionCesantias(){
        try {
            // 4000 Salario Basico
            String Str_Sql = "Delete from selinlib.jcesantia";
            boolean rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = " insert into selinlib.jcesantia Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                            + " t1.concod = t2.concod and (t1.CPRMAC>="+this.PeriodoInicial+" and t1.CPRMAC<="+this.PeriodoFinal+" and T1.CPRAÑO="+this.Año+")  "
                            + " and t1.concod in(4100,4200)  and TNOCVE='LP' "
                            + " group by t1.tracve, t4.tranom,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = " insert into selinlib.jcesantia Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                            + " t1.concod = t2.concod and (t1.CPRMAC>="+this.SPeriodoInicial+" and t1.CPRMAC<="+this.SPeriodoFinal+" and T1.CPRAÑO="+this.AñoF+")  "
                            + " and t1.concod in(4100,4200)  and TNOCVE='LP' "
                            + " group by t1.tracve, t4.tranom,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            int Tr = Integer.parseInt(this.Año);
            Str_Sql = " insert into selinlib.jcesantia Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                      + " t1.concod = t2.concod and t1.CPRMAC= 12 and CPRAÑO="+Tr
                      + " and t1.concod in(4150)  and TNOCVE='CE' "
                      + " group by t1.tracve, t4.tranom,t1.concod"
                      + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);

            
            
            Str_Sql = "Select tracve, nombre as tranom , concod, sum(salario) as trnimp from selinlib.jcesantia "
                    + " group by tracve, nombre, concod order by tracve , concod ";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Cabecera[0]="Codigo";
            Cabecera[1]="Nombre";
            Cabecera[2]="Valor Retencion";
            Cabecera[3]="Cesantias Parciales";
            Cabecera[4]="Cesantias Consignadas";
            Cabecera[5]="Intereese de Cesantia Parcial";
            Cabecera[6]="";
            this.setTabla();
            this.Posicion1 = -1;
            String Actual = "";
            while(rs.next()){
                String Tracve = rs.getString("tracve");
                if(!Tracve.trim().equals(Actual.trim())){
                    Actual= Tracve.trim();
                    this.Posicion1++;
                }
                Detalle[Posicion1][0]= Tracve;
                Detalle[Posicion1][1]=rs.getString("tranom").trim();
                Detalle[Posicion1][2]= JFormato.format(this.getRetencionAplicada(Tracve));
                int Codigo = rs.getInt("concod");
                double Vlr = rs.getDouble("trnimp");
                if(Codigo==4100){
                  Detalle[Posicion1][3]= JFormato.format(Vlr);
                }
                if(Codigo==4150){
                  Detalle[Posicion1][4]= JFormato.format(Vlr);  
                }
                if(Codigo==4200){
                  Detalle[Posicion1][5]= JFormato.format(Vlr);  
                  
                }

                
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacion()"+e.getMessage()); 
        }
    }
    
    public void getLiquidacionInteresesCesantian_RetroActivos(){
        try {
            // 4000 Salario Basico
            String Str_Sql = "Delete from selinlib.jintcesa";
            boolean rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = " insert into selinlib.jintcesa Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                            + " t1.concod = t2.concod and (t1.CPRPER>="+this.PeriodoInicial+" and t1.CPRPER<="+this.PeriodoFinal+" and T1.CPRAÑO="+this.Año+")  "
                            + " and t1.concod in(4201, 2510) and TNOCVE='QN' "
                            + " group by t1.tracve, t4.tranom,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = " insert into selinlib.jintcesa Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                            + " t1.concod = t2.concod and (t1.CPRPER>="+this.SPeriodoInicial+" and t1.CPRPER<="+this.SPeriodoFinal+" and T1.CPRAÑO="+this.AñoF+")  "
                            + " and t1.concod in(4201, 2510) and TNOCVE='QN' "
                            + " group by t1.tracve, t4.tranom,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "Select tracve, nombre as tranom , concod, sum(salario) as trnimp from selinlib.jintcesa "
                    + " group by tracve, nombre, concod order by tracve , concod ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Cabecera[0]="Codigo";
            Cabecera[1]="Nombre";
            Cabecera[2]="Valor Retencion";
            Cabecera[3]="Intereses de Cesantia";
            Cabecera[4]="Valor RetroActivo";
            Cabecera[5]="";
            Cabecera[6]="";
            this.setTabla();
            this.Posicion1 = 0;
            while(rs.next()){
                String Tracve = rs.getString("tracve");
                Detalle[Posicion1][0]= Tracve;
                Detalle[Posicion1][1]=rs.getString("tranom").trim();
                Detalle[Posicion1][2]= JFormato.format(this.getRetencionAplicada(Tracve));
                int Codigo = rs.getInt("concod");
                double Vlr = rs.getDouble("trnimp");
                if(Codigo==2510){
                   Detalle[Posicion1][4]= JFormato.format(Vlr);
                }else{
                   Detalle[Posicion1][3]= JFormato.format(Vlr);
                   this.Posicion1++;
                }
                
                
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacion()"+e.getMessage()); 
        }
    }
       
    
    
    public void getLiquidacionPrima(){
        try {
            // 4000 Salario Basico
            String Str_Sql = " Delete from selinlib.jprimas ";
            boolean rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "Insert into selinlib.jprimas Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                            + " t1.concod = t2.concod and (t1.CPRPER>="+this.PeriodoInicial+" and t1.CPRPER<="+this.PeriodoFinal+" and T1.CPRAÑO="+this.Año+")  "
                            + " and t1.concod in(4000)  and TNOCVE='PR' "
                            + " group by t1.tracve, t4.tranom,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            Str_Sql = "Insert into selinlib.jprimas Select t1.tracve, t4.tranom,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3, adamco.ttrab as t4 where t1.tracve=t3.tracve and t3.tracve=t4.tracve and  "
                            + " t1.concod = t2.concod and (t1.CPRPER>="+this.SPeriodoInicial+" and t1.CPRPER<="+this.SPeriodoFinal+" and T1.CPRAÑO="+this.AñoF+")  "
                            + " and t1.concod in(4000)  and TNOCVE='PR' "
                            + " group by t1.tracve, t4.tranom,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            Str_Sql = "Select tracve, nombre as tranom , concod, sum(salario) as trnimp from selinlib.jprimas "
                    + " group by tracve, nombre, concod order by tracve , concod ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Cabecera[0]="Codigo";
            Cabecera[1]="Nombre";
            Cabecera[2]="Valor Retencion";
            Cabecera[3]="Salario";
            Cabecera[4]="Salud";
            Cabecera[5]="Pension";
            Cabecera[6]="Fondo de Solidaridad";
            this.setTabla();
            this.Posicion1 = 0;
            while(rs.next()){
                String Tracve = rs.getString("tracve");
                Detalle[Posicion1][0]= Tracve;
                Detalle[Posicion1][1]=rs.getString("tranom").trim();
                Detalle[Posicion1][2]= JFormato.format(this.getRetencionAplicada(Tracve));
                int Codigo = rs.getInt("concod");
                double Vlr = rs.getDouble("trnimp");
                Detalle[Posicion1][3]= JFormato.format(Vlr);
                this.Posicion1++;
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacion()"+e.getMessage()); 
        }
    }
    
    
    public void getLiquidacionBonificacion(int Cantidad){
        try {
            // 5000 Salario Basico,6000 fondo de solidaridad, 6010 Pension, 6030 Salud
            String Str_Sql = " Delete from selinlib.jboni ";
            boolean rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = " insert into selinlib.jboni Select t1.tracve, t3.nombre,t1.concod, sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve and  "
                            + " t1.concod = t2.concod and (t1.CPRPER>="+this.PeriodoInicial+" and t1.CPRPER<="+this.PeriodoFinal+" and T1.CPRAÑO="+this.Año+")  "
                            + " and t1.concod in(5000,6000, 6010, 6030) and TNOCVE='BN' "
                            + " group by t1.tracve, t3.nombre,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            Str_Sql = " insert into selinlib.jboni Select t1.tracve, t3.nombre,t1.concod, sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve and  "
                            + " t1.concod = t2.concod and (t1.CPRPER>="+this.SPeriodoInicial+" and t1.CPRPER<="+this.SPeriodoFinal+" and T1.CPRAÑO="+this.AñoF+")  "
                            + " and t1.concod in(5000,6000, 6010, 6030) and TNOCVE='BN' "
                            + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            rx = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "Select tracve, nombre as tranom , concod, sum(salario) as trnimp from selinlib.jboni "
                    + " group by tracve, nombre, concod order by tracve , concod ";
            
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Cabecera[0]="Codigo";
            Cabecera[1]="Nombre";
            Cabecera[2]="Valor Retencion";
            Cabecera[3]="Salario";
            Cabecera[4]="Salud";
            Cabecera[5]="Pension";
            Cabecera[6]="Fondo de Solidaridad";
            this.setTabla();
            this.Posicion1 = Cantidad;
            while(rs.next()){
                String Tracve = rs.getString("tracve");
                Detalle[Posicion1][0]= Tracve;
                Detalle[Posicion1][1]=rs.getString("tranom").trim();
                Detalle[Posicion1][2]= JFormato.format(this.getRetencionAplicada(Tracve));
                int Codigo = rs.getInt("concod");
                double Vlr = rs.getDouble("trnimp");
                if(Codigo==5000){
                    Detalle[Posicion1][3]= JFormato.format(Vlr);
                }
                if(Codigo==6000){
                    Detalle[Posicion1][6]=JFormato.format(Vlr);
                }
                if(Codigo==6030){
                    Detalle[Posicion1][4]=JFormato.format(Vlr);
                    this.Posicion1++;
                }
                if(Codigo==6010){
                    Detalle[Posicion1][5]=JFormato.format(Vlr);
                }
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacion()"+e.getMessage()); 
        }
    }
    public void getLiquidacion(){
        try {
            // 2000 Salario Basico,6000 fondo de solidaridad, 6010 Pension, 6030 Salud
            String Str_Sql = "Delete from selinlib.jsueldo";
            boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and (t1.CPRPER>="+this.PeriodoInicial+" and t1.CPRPER<="+this.PeriodoFinal+" and T1.CPRAÑO="+this.Año+")  "
                            + " and t1.concod in(2000,6000, 6010,6020,6030)  and TNOCVE='QN' "
                            + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            System.out.println(""+Str_Sql);
            rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,t1.concod,sum(t1.trnimp)as trnimp  from adamco.tacno as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and (t1.CPRPER>="+this.SPeriodoInicial+" and t1.CPRPER<="+this.SPeriodoFinal+" and T1.CPRAÑO="+this.AñoF+")  "
                            + " and t1.concod in(2000,6000, 6010,6020, 6030)  and TNOCVE='QN' "
                            + " group by t1.tracve, t3.nombre,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";            
            System.out.println(""+Str_Sql);
            rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "Select tracve, nombre as tranom , concod, sum(salario) as trnimp from selinlib.jsueldo "
                    + " group by tracve, nombre, concod order by tracve , concod ";
            ResultSet rx = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            Cabecera2[0]="Codigo";
            Cabecera2[1]="Nombre";
            Cabecera2[2]="Valor Retencion";
            Cabecera2[3]="Salario";
            Cabecera2[4]="Salud";
            Cabecera2[5]="Pension";
            Cabecera2[6]="Fondo de Solidaridad";
            Cabecera2[7]="Pension de Volutaria";
            this.setTabla();
            this.Posicion1 = 0;

            while(rx.next()){
                String Tracve = rx.getString("tracve");
                Detalle2[Posicion1][0]= Tracve;
                Detalle2[Posicion1][1]=rx.getString("tranom").trim();
                Detalle2[Posicion1][2]= JFormato.format(this.getRetencionAplicada(Tracve));
                int Codigo = rx.getInt("concod");
                double Vlr = rx.getDouble("trnimp");
                if(Codigo==2000){
                    Detalle2[Posicion1][3]= JFormato.format(Vlr);
                }
                if(Codigo==6000){
                    Detalle2[Posicion1][6]=JFormato.format(Vlr);
                }
                if(Codigo==6010){
                    Detalle2[Posicion1][5]=JFormato.format(Vlr);
                }
                if(Codigo==6020){
                    Detalle2[Posicion1][7]=JFormato.format(Vlr);
                }
                if(Codigo==6030){
                    Detalle2[Posicion1][4]=JFormato.format(Vlr);
                    this.Posicion1++;
                }

             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacion()"+e.getMessage()); 
        }
    }
    public void setTabla(){
        for (int i = 0; i <= this.Posicion1; i++) {
            Detalle[i][0] = "";
            Detalle[i][1] = "";
            Detalle[i][2] = "";
            Detalle[i][3] = "";
            Detalle[i][4] = "";
            Detalle[i][5] = "";
            Detalle[i][6] = "";
        }
    }
    public void getEmpleadosRetenciones(){
        try {
             String Str_Sql ="Delete from selinlib.JEMPLERET2";
             boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
             
             Str_Sql ="Delete from selinlib.JEMPLERETE";
             rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
             
             Str_Sql = "Insert into selinlib.JEMPLERETE "
                    + " Select t1.CPRAÑO , t1.tracve , t2.tranom, (ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07 +ACIM08+ACIM09+ACIM10+ACIM11+ACIM12) as ValorRetencion"
                    + " from adamco.tacnm as t1, adamco.ttrab as t2 where t1.tracve=t2.tracve and t1.concod=6050 and "
                    + " T1.CPRAÑO="+this.AñoF;
             rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
             int AñoOp = Integer.parseInt(AñoF)+1;
             Str_Sql = "Insert into selinlib.JEMPLERET2 "
                    + " Select t1.CPRAÑO , t1.tracve , t2.tranom, (ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07 +ACIM08+ACIM09+ACIM10+ACIM11+ACIM12) as ValorRetencion"
                    + " from adamco.tacnm as t1, adamco.ttrab as t2 where t1.tracve=t2.tracve and t1.concod=6050 and "
                    + " T1.CPRAÑO="+AñoOp;
             rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
             
             if (!rs) {
                JOptionPane.showMessageDialog(null,"no se puedo generar el archivo de retenciones"); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JRetencionEmpleados:getEmpleadosRetenciones()"+e.getMessage()); 
        }
    } 
 
}

 