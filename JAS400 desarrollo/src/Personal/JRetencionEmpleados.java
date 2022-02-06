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
 * 
 * 
 *  
 */

public class JRetencionEmpleados {
    
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
    
    public JRetencionEmpleados(JConection JBase_Datos3, Connection Cn2,String PPeriodoI, String PPeriodoF,String SPeriodoI, String SPeriodoF, String AñoP, String AñoP2, int Tip){
        
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
        Cabecera2 =  new Object [16];
        Detalle2  = new Object [2000][16];
        JFormato= new DecimalFormat(NumeroFormato);
        this.getEmpleadosRetenciones(Tip);
        
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
    public void setTabla2(){
        
        for (int i = 0; i <= this.Posicion1; i++) {
            Detalle2[i][0] = "";
            Detalle2[i][1] = "";
            Detalle2[i][2] = "";
            Detalle2[i][3] = "";
            Detalle2[i][4] = "";
            Detalle2[i][5] = "";
            Detalle2[i][6] = "";
            Detalle2[i][7] = "";
            Detalle2[i][8] = "";
            Detalle2[i][9] = "";
            Detalle2[i][10] = "";
            Detalle2[i][11] = "";
            Detalle2[i][12] = "";
            Detalle2[i][13] = "";
            Detalle2[i][14] = "";
            Detalle2[i][15] = "";
        }
        this.Posicion1 = 0;
    }
    
    public void getEmpleadosRetenciones(int Tipo){
        try {
             String Str_Sql ="Delete from selinlib.JEMPLERET2";
             boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
             
             Str_Sql ="Delete from selinlib.JEMPLERETE";
             rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
          if(Tipo==1){
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
          }
          if(Tipo==2){
              
              Str_Sql = "Insert into selinlib.JEMPLERETE "
                    + " Select t1.CPRAÑO , t1.tracve , t2.tranom, (ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12) as ValorRetencion"
                    + " from adamco.tacnm as t1, adamco.ttrab as t2 where t1.tracve=t2.tracve and t1.concod=6050 and "
                    + " T1.CPRAÑO="+this.Año;
             
             rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
             
             Str_Sql = "Insert into selinlib.JEMPLERET2 "
                    + " Select t1.CPRAÑO , t1.tracve , t2.tranom, (ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05) as ValorRetencion"
                    + " from adamco.tacnm as t1, adamco.ttrab as t2 where t1.tracve=t2.tracve and t1.concod=6050 and "
                    + " T1.CPRAÑO="+this.AñoF;
             
             rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
          }
          
          
             if (!rs) {
                JOptionPane.showMessageDialog(null,"no se puedo generar el archivo de retenciones"); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," JRetencionEmpleados:getEmpleadosRetenciones()"+e.getMessage()); 
        }
    } 
     public void getLiquidacionDevengado(int Tipo){
        try {
          if(Tipo==1){  
                String Str_Sql = "Delete from selinlib.jsueldo";
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,t1.concod,sum(ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                                + " and t1.concod in(6000,6010,6020,6030,6270,3700,2635 )   "
                                + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,t1.concod,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in( 6000, 6010,6020,6030,6270,3700,2635 )   "
                            + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
          }
          
          if(Tipo==2){  
                String Str_Sql = "Delete from selinlib.jsueldo";
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,t1.concod,sum(ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                                + " and t1.concod in(6000,6010,6020,6030,6270,3700,2635 )   "
                                + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,t1.concod,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in(6000, 6010,6020,6030,6270,3700,2635 )   "
                            + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
          }
          
          
          
          
          
          if(Tipo==99){
                String Str_Sql = "Delete from selinlib.jsueldo";
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,t1.concod,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                            + " and t1.concod in( 6000, 6010,6020,6030,6270,3700,2635 )   "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
          }   
          this.Liquidacion(Tipo);
          this.Cesantias(Tipo);
          String Str_Sql = "Select tracve, nombre as tranom , concod, sum(salario) as trnimp from selinlib.jsueldo "
                    + " group by tracve, nombre, concod order by tracve , concod ";
          ResultSet rx = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
          
          
            Cabecera2[0]="Codigo";
            Cabecera2[1]="Nombre";
            Cabecera2[2]="Vlr Retencion";
            Cabecera2[3]="Aporte IVM";
            Cabecera2[4]="Aporte Voluntario IVM";
            Cabecera2[5]="Aporte Salud";
            Cabecera2[6]="Fondo de Solidaridad";
            Cabecera2[7]="Informativo Viaticos";
            Cabecera2[8]="Devengado";
            Cabecera2[9]="Cesantias Autorizada";
            Cabecera2[10]="Cesantias";
            Cabecera2[11]="Cesantias Anual";
            Cabecera2[12]="Intereses de Cesantia";
            Cabecera2[13]="Intereses de Cesantia Anual";
            Cabecera2[14]="Intereses de Cesantia";
            Cabecera2[15]="Incap. prorrogada";

          this.setTabla2();
          
          while(rx.next()){
                String Tracve = rx.getString("tracve");
                Detalle2[Posicion1][0]= Tracve;
                Detalle2[Posicion1][1]=rx.getString("tranom").trim();
                Detalle2[Posicion1][2]= JFormato.format(this.getRetencionAplicada(Tracve));
                int Codigo = rx.getInt("concod");
                double Vlr = rx.getDouble("trnimp");
                if(Codigo==6010){
                    Detalle2[Posicion1][3]=JFormato.format(Vlr);
                }
                if(Codigo==6020){
                    Detalle2[Posicion1][4]=JFormato.format(Vlr);
                }
                if(Codigo==6000){
                    Detalle2[Posicion1][6]=JFormato.format(Vlr);
                }
                if(Codigo==3700){
                    Detalle2[Posicion1][7]=JFormato.format(Vlr);
                }
                if(Codigo==2000){
                    Detalle2[Posicion1][8]=JFormato.format(Vlr);
                }
                if(Codigo==6030){
                    Detalle2[Posicion1][5]=JFormato.format(Vlr);
                    Posicion1++;
                }
                if(Codigo==4100){
                    Detalle2[Posicion1][9]=JFormato.format(Vlr);
                }
                if(Codigo==4110){
                    Detalle2[Posicion1][10]=JFormato.format(Vlr);
                }
                if(Codigo==4150){
                    Detalle2[Posicion1][11]=JFormato.format(Vlr);
                }
                if(Codigo==4200){
                    Detalle2[Posicion1][12]=JFormato.format(Vlr);
                }
                if(Codigo==4201){
                    Detalle2[Posicion1][13]=JFormato.format(Vlr);
                }
                if(Codigo==4210){
                    Detalle2[Posicion1][14]=JFormato.format(Vlr);
                }
                if(Codigo==2635){
                    Detalle2[Posicion1][15]=JFormato.format(Vlr);
                    //System.out.println("ENTRO ACA....");
                }
           }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacion()"+e.getMessage()); 
        }
    }
    public void getLiquidacionDevengadoTodos(int Tipo){
        try {
          String Str_Sql;
          if(Tipo==1){  
            Str_Sql = "Delete from selinlib.jsueldo";
            boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,t1.concod,sum(ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t3.tracve = t1.tracve and"
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                            + " and t1.concod in (6000,6010,6020,6030,6270,3700,2635 )   "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            
            rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom ,t1.concod,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t3.tracve = t1.tracve and "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in (6000,6010,6020,6030,6270,3700,2635 )   "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
            
            rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
          }    
          
          if(Tipo==2){  
            Str_Sql = "Delete from selinlib.jsueldo";
            boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,t1.concod,sum(ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t3.tracve = t1.tracve and"
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                            + " and t1.concod in (6000,6010,6020,6030,6270,3700,2635 )   "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            
            rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom ,t1.concod,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05) as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t3.tracve = t1.tracve and "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in (6000,6010,6020,6030,6270,3700,2635 )   "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
            
            rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
          }   
          
          
          LiquidacionTodos(Tipo);
          CesantiasTodo(Tipo);
            
            Str_Sql = "Select tracve, nombre as tranom , concod, sum(salario) as trnimp from selinlib.jsueldo "
                    + " group by tracve, nombre, concod order by tracve , concod ";
            ResultSet rx = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            
            Cabecera2[0]="Codigo";
            Cabecera2[1]="Nombre";
            Cabecera2[2]="Vlr Retencion";
            Cabecera2[3]="Aporte IVM";
            Cabecera2[4]="Aporte Voluntario IVM";
            Cabecera2[5]="Aporte Salud";
            Cabecera2[6]="Fondo de Solidaridad";
            Cabecera2[7]="Informativo Viaticos";
            Cabecera2[8]="Devengado";
            Cabecera2[9]="Cesantias Autorizada";
            Cabecera2[10]="Cesantias";
            Cabecera2[11]="Cesantias Anual";
            Cabecera2[12]="Intereses de Cesantia";
            Cabecera2[13]="Intereses de Cesantia Anual";
            Cabecera2[14]="Intereses de Cesantia";
            Cabecera2[15]="Incp. prorrogable";
            
            this.setTabla2();

            while(rx.next()){
                String Tracve = rx.getString("tracve");
                Detalle2[Posicion1][0]= Tracve;
                Detalle2[Posicion1][1]=rx.getString("tranom").trim();
                Detalle2[Posicion1][2]= JFormato.format(this.getRetencionAplicada(Tracve));
                int Codigo = rx.getInt("concod");
                double Vlr = rx.getDouble("trnimp");
                if(Codigo==6010){
                    Detalle2[Posicion1][3]=JFormato.format(Vlr);
                }
                if(Codigo==6020){
                    Detalle2[Posicion1][4]=JFormato.format(Vlr);
                }
                if(Codigo==6000){
                    Detalle2[Posicion1][6]=JFormato.format(Vlr);
                }
                if(Codigo==3700){
                    Detalle2[Posicion1][7]=JFormato.format(Vlr);
                }
                if(Codigo==2000){
                    Detalle2[Posicion1][8]=JFormato.format(Vlr);
                }
                if(Codigo==6030){
                    Detalle2[Posicion1][5]=JFormato.format(Vlr);
                    Posicion1++;
                }
                if(Codigo==4100){
                    Detalle2[Posicion1][9]=JFormato.format(Vlr);
                }
                if(Codigo==4110){
                    Detalle2[Posicion1][10]=JFormato.format(Vlr);
                }
                if(Codigo==4150){
                    Detalle2[Posicion1][11]=JFormato.format(Vlr);
                }
                if(Codigo==4200){
                    Detalle2[Posicion1][12]=JFormato.format(Vlr);
                }
                if(Codigo==4201){
                    Detalle2[Posicion1][13]=JFormato.format(Vlr);
                }
                if(Codigo==4210){
                    Detalle2[Posicion1][14]=JFormato.format(Vlr);
                }
                if(Codigo==2635){
                    Detalle2[Posicion1][15]=JFormato.format(Vlr);
                    //System.out.println("ENTRO ACA....");
                }
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacionDevengadoTodo() "+e.getMessage()); 
        }
    } 

    public void Liquidacion(int Tipo){
        try {
            if(Tipo==1){  
                String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,2000,sum(ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                                + " and t1.concod in("
                                + "2000,  2048,  2113,   2173, "
                                + "2005,   2058,  2123,  2183, "
                                + "2007,   2068,  2133,  2193, "
                                + "2018,   2078,  2143,  2196, "
                                + "2028,   2088,  2153,  2500, "
                                + "2038,   2098,  2163,  2505, "
                                + "2510,   2525,  2540,  2555, "
                                + "2513,   2528,  2543,  2558, "
                                + "2516,   2531,  2546,  2561, "
                                + "2519,   2534,  2549,  2564, "
                                + "2522,   2537,  2552,  2567, "
                                + "2600,   3110,  3110,  3400, "
                                + "2601,   3200,  3200,  3600, "
                                + "2610,   3220,  3220,  3700, "
                                + "2652,   3240,  3240,  4000, "
                                + "2652,   3240,  3240,  4000, "
                                + "3100,   3300,  3300,  4010, "
                                + "4300,   4400) "
                                + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
                
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,2000,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in( "
                                + "2000,  2048,  2113,   2173, "
                                + "2005,   2058,  2123,  2183, "
                                + "2007,   2068,  2133,  2193, "
                                + "2018,   2078,  2143,  2196, "
                                + "2028,   2088,  2153,  2500, "
                                + "2038,   2098,  2163,  2505, "
                                + "2510,   2525,  2540,  2555, "
                                + "2513,   2528,  2543,  2558, "
                                + "2516,   2531,  2546,  2561, "
                                + "2519,   2534,  2549,  2564, "
                                + "2522,   2537,  2552,  2567, "
                                + "2600,   3110,  3110,  3400, "
                                + "2601,   3200,  3200,  3600, "
                                + "2610,   3220,  3220,  3700, "
                                + "2652,   3240,  3240,  4000, "
                                + "3100,   3300,  3300,  4010, "
                                + "4300,   4400) "
                            + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
            
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            
          }
          if(Tipo==2){  
                String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,2000,sum(ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                                + " and t1.concod in("
                                + "2000,  2048,  2113,   2173, "
                                + "2005,   2058,  2123,  2183, "
                                + "2007,   2068,  2133,  2193, "
                                + "2018,   2078,  2143,  2196, "
                                + "2028,   2088,  2153,  2500, "
                                + "2038,   2098,  2163,  2505, "
                                + "2510,   2525,  2540,  2555, "
                                + "2513,   2528,  2543,  2558, "
                                + "2516,   2531,  2546,  2561, "
                                + "2519,   2534,  2549,  2564, "
                                + "2522,   2537,  2552,  2567, "
                                + "2600,   3110,  3110,  3400, "
                                + "2601,   3200,  3200,  3600, "
                                + "2610,   3220,  3220,  3700, "
                                + "2652,   3240,  3240,  4000, "
                                + "2652,   3240,  3240,  4000, "
                                + "3100,   3300,  3300,  4010, "
                                + "4300,   4400) "
                                + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
                
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,2000,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in( "
                                + "2000,  2048,  2113,   2173, "
                                + "2005,   2058,  2123,  2183, "
                                + "2007,   2068,  2133,  2193, "
                                + "2018,   2078,  2143,  2196, "
                                + "2028,   2088,  2153,  2500, "
                                + "2038,   2098,  2163,  2505, "
                                + "2510,   2525,  2540,  2555, "
                                + "2513,   2528,  2543,  2558, "
                                + "2516,   2531,  2546,  2561, "
                                + "2519,   2534,  2549,  2564, "
                                + "2522,   2537,  2552,  2567, "
                                + "2600,   3110,  3110,  3400, "
                                + "2601,   3200,  3200,  3600, "
                                + "2610,   3220,  3220,  3700, "
                                + "2652,   3240,  3240,  4000, "
                                + "3100,   3300,  3300,  4010, "
                                + "4300,   4400) "
                            + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            
          }
          if(Tipo==99){

              String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,2000,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                            + " and t1.concod in(  "
                                + "2000,   2048,  2113,  2173, "
                                + "2005,   2058,  2123,  2183, "
                                + "2007,   2068,  2133,  2193, "
                                + "2018,   2078,  2143,  2196, "
                                + "2028,   2088,  2153,  2500, "
                                + "2038,   2098,  2163,  2505, "
                                + "2510,   2525,  2540,  2555, "
                                + "2513,   2528,  2543,  2558, "
                                + "2516,   2531,  2546,  2561, "
                                + "2519,   2534,  2549,  2564, "
                                + "2522,   2537,  2552,  2567, "
                                + "2600,   3110,  3110,  3400, "
                                + "2601,   3200,  3200,  3600, "
                                + "2610,   3220,  3220,  3700, "
                                + "2652,   3240,  3240,  4000, "
                                + "3100,   3300,  3300,  4010, "
                                + "4300,   4400) "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
              
              boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
              
              //Otros();
          } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacionDevengadoTodo() "+e.getMessage()); 
        }
    }
    
    public void LiquidacionTodos(int Tipo){
        try {
            
            if(Tipo==1){  
                String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,2000,sum(ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                                + " and t1.concod in("
                                + "2000,  2048,  2113,   2173, "
                                + "2005,   2058,  2123,  2183, "
                                + "2007,   2068,  2133,  2193, "
                                + "2018,   2078,  2143,  2196, "
                                + "2028,   2088,  2153,  2500, "
                                + "2038,   2098,  2163,  2505, "
                                + "2510,   2525,  2540,  2555, "
                                + "2513,   2528,  2543,  2558, "
                                + "2516,   2531,  2546,  2561, "
                                + "2519,   2534,  2549,  2564, "
                                + "2522,   2537,  2552,  2567, "
                                + "2600,   3110,  3110,  3400, "
                                + "2601,   3200,  3200,  3600, "
                                + "2610,   3220,  3220,  3700, "
                                + "2652,   3240,  3240,  4000, "
                                + "2652,   3240,  3240,  4000, "
                                + "3100,   3300,  3300,  4010, "
                                + "4300,   4400) "
                                + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
                
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,2000,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in( "
                                + "2000,  2048,  2113,   2173, "
                                + "2005,   2058,  2123,  2183, "
                                + "2007,   2068,  2133,  2193, "
                                + "2018,   2078,  2143,  2196, "
                                + "2028,   2088,  2153,  2500, "
                                + "2038,   2098,  2163,  2505, "
                                + "2510,   2525,  2540,  2555, "
                                + "2513,   2528,  2543,  2558, "
                                + "2516,   2531,  2546,  2561, "
                                + "2519,   2534,  2549,  2564, "
                                + "2522,   2537,  2552,  2567, "
                                + "2600,   3110,  3110,  3400, "
                                + "2601,   3200,  3200,  3600, "
                                + "2610,   3220,  3220,  3700, "
                                + "2652,   3240,  3240,  4000, "
                                + "3100,   3300,  3300,  4010, "
                                + "4300,   4400) "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
                
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            
          }
          if(Tipo==2){  
                String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,2000,  sum(ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12) as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                                + " and t1.concod in("
                                + "2000,  2048,  2113,   2173, "
                                + "2005,   2058,  2123,  2183, "
                                + "2007,   2068,  2133,  2193, "
                                + "2018,   2078,  2143,  2196, "
                                + "2028,   2088,  2153,  2500, "
                                + "2038,   2098,  2163,  2505, "
                                + "2510,   2525,  2540,  2555, "
                                + "2513,   2528,  2543,  2558, "
                                + "2516,   2531,  2546,  2561, "
                                + "2519,   2534,  2549,  2564, "
                                + "2522,   2537,  2552,  2567, "
                                + "2600,   3110,  3110,  3400, "
                                + "2601,   3200,  3200,  3600, "
                                + "2610,   3220,  3220,  3700, "
                                + "2652,   3240,  3240,  4000, "
                                + "2652,   3240,  3240,  4000, "
                                + "3100,   3300,  3300,  4010, "
                                + "4300,   4400) "
                                + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,2000,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in( "
                                + "2000,  2048,  2113,   2173, "
                                + "2005,   2058,  2123,  2183, "
                                + "2007,   2068,  2133,  2193, "
                                + "2018,   2078,  2143,  2196, "
                                + "2028,   2088,  2153,  2500, "
                                + "2038,   2098,  2163,  2505, "
                                + "2510,   2525,  2540,  2555, "
                                + "2513,   2528,  2543,  2558, "
                                + "2516,   2531,  2546,  2561, "
                                + "2519,   2534,  2549,  2564, "
                                + "2522,   2537,  2552,  2567, "
                                + "2600,   3110,  3110,  3400, "
                                + "2601,   3200,  3200,  3600, "
                                + "2610,   3220,  3220,  3700, "
                                + "2652,   3240,  3240,  4000, "
                                + "3100,   3300,  3300,  4010, "
                                + "4300,   4400) "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
            
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
            
          }  
          if(Tipo==99){

              String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,2000,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                            + " and t1.concod in(  "
                                + "2000,   2048,  2113,  2173, "
                                + "2005,   2058,  2123,  2183, "
                                + "2007,   2068,  2133,  2193, "
                                + "2018,   2078,  2143,  2196, "
                                + "2028,   2088,  2153,  2500, "
                                + "2038,   2098,  2163,  2505, "
                                + "2510,   2525,  2540,  2555, "
                                + "2513,   2528,  2543,  2558, "
                                + "2516,   2531,  2546,  2561, "
                                + "2519,   2534,  2549,  2564, "
                                + "2522,   2537,  2552,  2567, "
                                + "2600,   3110,  3110,  3400, "
                                + "2601,   3200,  3200,  3600, "
                                + "2610,   3220,  3220,  3700, "
                                + "2652,   3240,  3240,  4000, "
                                + "3100,   3300,  3300,  4010, "
                                + "4300,   4400) "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
              
              boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);

          } 
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacionDevengadoTodo() "+e.getMessage()); 
        }
    }
    public void Cesantias(int Tipo){
        try {
            if(Tipo==1){  
                String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,t1.concod,sum(ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                                + " and t1.concod in("
                                + "4100,   4110,  4200,  4150, 4201, 4210"
                                + ") "
                                + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,t1.concod,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in( "
                                + "4100,   4110,  4200,  4150, 4201, 4210"
                                + ") "
                            + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
            
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
          }
          if(Tipo==2){  
                String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,t1.concod,sum(ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12) as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                                + " and t1.concod in("
                                + "4100,   4110,  4200,  4150, 4201, 4210"
                                + ") "
                                + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
            
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.nombre,t1.concod, sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05) as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, selinlib.JEMPLERET2 as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in( "
                                + "4100,   4110,  4200,  4150, 4201, 4210"
                                + ") "
                            + " group by t1.tracve, t3.nombre ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
            
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
            
          }

          if(Tipo==99){

              String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,t1.concod,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                            + " and t1.concod in(  "
                            + "4100,   4110,  4200,  4150, 4201, 4210 "
                            + " )"
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
              
              boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
          } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacionDevengadoTodo() "+e.getMessage()); 
        }
    }
     public void CesantiasTodo(int Tipo){
        try {
            if(Tipo==1){  
                String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,t1.concod,sum(ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                                + " and t1.concod in("
                                + "4100,   4110,  4200,  4150, 4201, 4210"
                                + ") "
                                + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
                
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,t1.concod,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in( "
                                + "4100,   4110,  4200,  4150, 4201, 4210"
                                + ") "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
            
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
          }
          if(Tipo==2){  
                String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,t1.concod,sum(ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12) as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                                + " and t1.concod in("
                                + "4100,   4110,  4200,  4150, 4201, 4210"
                                + ") "
                                + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
                
                boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
                Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,t1.concod,  sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05) as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.AñoF    
                            + " and t1.concod in( "
                                + "4100,   4110,  4200,  4150, 4201, 4210"
                                + ") "
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";     
            
                rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
          }  
            
            
          if(Tipo==99){

              String Str_Sql = "  insert into selinlib.jsueldo "
                            + " Select t1.tracve, t3.tranom,t1.concod,sum(ACIM01+ACIM02 +ACIM03+ACIM04+ACIM05+ACIM06+ACIM07+ACIM08+ACIM09+ACIM10+ACIM11+ACIM12)as trnimp  from adamco.tacnm as t1, adamco.tcono as t2, adamco.ttrab as t3 where t1.tracve=t3.tracve  and  "
                            + " t1.concod = t2.concod and  T1.CPRAÑO="+this.Año
                            + " and t1.concod in(  "
                            + "4100,   4110,  4200,  4150, 4201, 4210 "
                            + " )"
                            + " group by t1.tracve, t3.tranom ,t1.concod"
                            + " order by t1.tracve, t1.concod asc ";
                
              boolean rs = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
          } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"JRetencionEmpleados:getLiquidacionDevengadoTodo() "+e.getMessage()); 
        }
    }
    
    
}

 