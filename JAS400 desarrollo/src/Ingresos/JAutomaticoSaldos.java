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
public class JAutomaticoSaldos {
    
    private String CentroCosto;
    private JConection JBase_Datos;
    private Connection Cn;
    
    private String FechaActual;
    private int FechaIncial, FechaFinal;
    private double SaldoAnteriorCentroAtencion;
    private double SaldoActualXCentroAtencion;
    private double ValorPagadoSubsidio, EfectivoPlanilla = 0;
    private double VlrTranferencia = 0, VlrIngreso = 0, VlrComision = 0, VlrCheque = 0;
    private double Pagos_Efectivos ;
    
    public JAutomaticoSaldos(JConection JBase_Datos2 , Connection Cn3, String PCentro, int FechaI, int FechaF){
        this.JBase_Datos = JBase_Datos2;
        this.Cn = Cn3;
        this.CentroCosto = PCentro;
        this.FechaIncial = FechaI;
        this.FechaFinal = FechaF;
        Pagos_Efectivos =0;
    }
    public double getCambiocheque(String Fecha){
        double Vlr = 0;
        try {
            String Str_Sql = "select CMBCHEQ from selinlib.jingreso where fecha="+Fecha;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if(rs.next()){
                Vlr = rs.getDouble("CMBCHEQ");
            }
        } catch (Exception e) {
        }
        return Vlr;
    }
    
    public double getRecibosCajas(){
        double recibo_caja_otras=0;
        try {
               
            
            String Str_Sql =" SELECT CODTARJ FROM SELINLIB.JCCENTROS WHERE CODTARJ  NOT IN ( 0 ,  554 ) ";
                ResultSet rs_centros = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                ResultSet resultado=null;
                //System.out.println(""+Str_Sql);
                while(rs_centros.next()){
                        String WCentro = rs_centros.getString("CODTARJ");
                        String SQL= "SELECT T01.CODCCO , SUM(TRPVAL) as Valor" +
                                    " FROM SGDATOS.TRCJC as T01 , SGDATOS.TRCJP as T04 " +
                                    " WHERE T01.TRCNUM = T04.TRCNUM AND " +
                                    " T01.TRCFEC = "+this.FechaActual+"   AND T01.TRCEST ='G' AND TBACVE = 100 " +
                                    " AND T01.CODCCO = "+WCentro+" "+
                                    " GROUP BY T01.CODCCO ";
                       resultado = JBase_Datos.SQL_QRY(this.Cn,SQL);
                       int Error = 0;
                       //System.out.println(""+SQL);
                       if (resultado.next()){
                           recibo_caja_otras = recibo_caja_otras+resultado.getDouble("Valor");
                       }
               }       
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null," error "+ e.getMessage());
        }
        return recibo_caja_otras;
    }
    public double reciboCajasPropio(String WCentro){
        double recibo_caja_propio=0;
        try {
             String Str_Sql= "SELECT T01.CODCCO , SUM(TRPVAL) as Valor " +
                            " FROM SGDATOS.TRCJC as T01 , SGDATOS.TRCJP as T04 " +
                            " WHERE T01.TRCNUM = T04.TRCNUM AND " +
                            " T01.TRCFEC = "+this.FechaActual+"   AND T01.TRCEST ='G' AND TBACVE = 100 " +
                            " AND T01.CODCCO = "+WCentro+" "+
                            " GROUP BY T01.CODCCO ";
                ResultSet rs_x_centros = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                if (rs_x_centros.next()){
                   recibo_caja_propio = recibo_caja_propio + rs_x_centros.getDouble("Valor");
                }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null," error "+ e.getMessage());
        }
        return recibo_caja_propio;
    }
    public void Proceso(){
        for (int i = this.FechaIncial; i <= this.FechaFinal; i++) {
            this.FechaActual = String.valueOf(i);
            this.ValorPagadoSubsidio = getSubsidioPagado(this.CentroCosto);
            this.EfectivoPlanilla = this.getEfectivoPlanilla(this.CentroCosto);
            this.getMovimiento();
            this.SaldoAnteriorCentroAtencion =  this.getSaldoAnterior_CAC();
            double recibo_caja_propio=0, recibo_caja_otras=0; 
            
            if(this.CentroCosto.trim().equals("48")){
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////// Corregir                     //////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Inclusion del pago en efectivo calle-48
                this.EfectivoPlanilla = this.EfectivoPlanilla -this.getCambiocheque(this.FechaActual)-this.Pagos_Efectivos;
                recibo_caja_otras=getRecibosCajas();
                
            }else{
                String WCentro = getCentroEquivalenteSiged(CentroCosto);
                recibo_caja_propio = reciboCajasPropio(WCentro);
            }
            
            this.SaldoActualXCentroAtencion = this.SaldoAnteriorCentroAtencion +this.VlrIngreso+this.VlrTranferencia-this.VlrComision-this.VlrCheque-this.ValorPagadoSubsidio+this.EfectivoPlanilla +recibo_caja_propio - recibo_caja_otras;
            this.setSaldoActual();
            
            System.out.println("Valor Pagado"+this.ValorPagadoSubsidio);
            System.out.println("Valor Efectivo "+this.EfectivoPlanilla);
            System.out.println("Saldo Anterior "+this.SaldoAnteriorCentroAtencion);
            System.out.println("Saldo actual "+this.SaldoActualXCentroAtencion);
            System.out.println("cheq  "+this.VlrCheque);
            System.out.println("Comision "+this.VlrComision);
            System.out.println("ingreso "+this.VlrIngreso);
            System.out.println("transfere "+this.VlrTranferencia);
        }
    }
    public double  getSubsidioPagado(String Centro){
        double Vlr = 0;
        try {
            String Str_Sql = "Select t2.cac,PAGOS_EFE, t1.valor from selinlib.jcac as t1, selinlib.jccentros as t2 where t1.codigo = t2.codigo "
                    + " and t2.cac ="+Centro +" and fecha="+this.FechaActual;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                Vlr = rs.getDouble("Valor");
                this.Pagos_Efectivos =rs.getDouble("PAGOS_EFE");
            }
            return Vlr;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo:getCentroEquivalente "+e.getMessage()); 
        }
        return Vlr;
    }
    public String getCentroEquivalenteUser(String Codigo){
       String XCodigo = null;
       try {
            String Str_Sql = "Select Cac,Codigo from Selinlib.JCCentros where Codigo="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getString("cac");
            }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"JAutomaticoSaldo:getCentroEquivalenteUser "+e.getMessage()); 
        }
            return XCodigo;
    }
    public void getMovimiento(){
        try {
            this.VlrTranferencia = 0;
            this.VlrCheque = 0;
            this.VlrIngreso = 0;
            this.VlrComision = 0;
                    
            String Str_Sql = "select de, para, valor, observa from selinlib.jsaldos as t1, selinlib.jccentros as t2 where t1.codigocac = t2.codigo "
                    + " and Cac="+this.CentroCosto
                    + " and fecha="+this.FechaActual+ " and de <> 99999999 and para<>99999999 ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);

            while (rs.next()) {
                
                String de = rs.getString("de");
                String  para = rs.getString("para");
                
                de = getCentroEquivalenteUser(de);
                para = getCentroEquivalenteUser(para);
                String Observacion = rs.getString("observa");
                if(Observacion.trim().equals("Ingreso")){
                     this.VlrIngreso=  this.VlrIngreso + rs.getDouble("Valor");
                }else{
                    if(Observacion.trim().equals("Transferencia")){    
                        if(this.CentroCosto.trim().equals(para)){
                            this.VlrTranferencia =  this.VlrTranferencia + rs.getDouble("Valor");
                        }else{
                            this.VlrIngreso = this.VlrIngreso - rs.getDouble("Valor");
                        }
                    }else{
                          if(Observacion.trim().equals("Cambio Cheque")){
                              this.VlrCheque = this.VlrCheque+rs.getDouble("Valor");
                          }else{
                              if(Observacion.trim().equals("Comision")){
                                  this.VlrComision = this.VlrComision + rs.getDouble("Valor");
                              }
                          }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo:getCentroEquivalente "+e.getMessage()); 
        }
    }
    
     public String getCentroEquivalenteSiged(String Codigo){
       String XCodigo = null;
       try {
           
            String Str_Sql = "Select Cac,CODTARJ from Selinlib.JCCentros where Cac ="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getString("CODTARJ");
            }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo:getCentroEquivalente "+e.getMessage()); 
        }
        return XCodigo;
    }
    public String getCentroEquivalente(String Codigo){
       String XCodigo = null;
       try {
            String Str_Sql = "Select Cac,Codigo from Selinlib.JCCentros where Cac ="+Codigo.trim();
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                XCodigo =rs.getString("Codigo");
            }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo:getCentroEquivalente "+e.getMessage()); 
        }
        return XCodigo;
    }
    public double getSaldoAnterior_CAC(){
          double SaldoAnterior =0;
          String Centro = null;
          try {
              Centro = getCentroEquivalente(CentroCosto);
              String Str_Sql ="SELECT Valor FROM selinlib.jsaldos WHERE "
                             +" fecha ="+this.RestarDias_Saldos_CAC()+" AND de=99999999"
                             +" AND CodigoCAC ="+Centro;
              ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
              if(rs.next()){
                    SaldoAnterior = rs.getDouble("Valor");
              }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo:getSaldoAnterior_CAC()"+e.getMessage()); 
          }
          return SaldoAnterior;
      }
      public String RestarDias_Saldos_CAC(){
        int Dia = Integer.parseInt(this.FechaActual.substring(6, 8));
        String zMes = this.FechaActual.substring(4, 6);
        String zDia ="";
        int zAño = Integer.parseInt( this.FechaActual.substring(0, 4));
        if ( Dia  ==  1){
            zMes = this.getMes(zMes);
            if(Integer.parseInt(zMes)== 12){
                zAño = zAño -1;
            }
            try {
                String Str_Sql = " select   max(substring( fecha , 7 ,2 )) as dia "+
                                 " from selinlib.jsaldos   "+
                                 " where substring(fecha , 5 , 2) = "+zMes+" and substring( fecha , 1 ,4 )="+zAño; ;
                ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                if(rs.next()){
                    zDia = rs.getString("dia");
                    Dia = Integer.parseInt(zDia);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo RestarDias() "+e.getMessage());
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
    public void setSaldoActual(){
        try{
           String Centro = getCentroEquivalente(CentroCosto);
           String Str_Sql = "SELECT * FROM selinlib.jsaldos  Where FECHA ="+this.FechaActual+" AND DE=99999999 and PARA = 99999999"
                   + " and codigocac ="+Centro;
           ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
           if(!rs.next()){
               Str_Sql  ="INSERT INTO selinlib.jsaldos (fecha,codigocac,de,para,valor) values "
                       + "("+this.FechaActual+","+Centro+",99999999,99999999 , "+this.SaldoActualXCentroAtencion+")";
               
           }else{
               Str_Sql  ="UPDATE selinlib.jsaldos set "
                       + "valor= "+this.SaldoActualXCentroAtencion
                       + " where fecha="+this.FechaActual
                       + " and de= 99999999 and para=99999999 "
                       + " and codigocac="+Centro;
              
           }
           boolean rt = JBase_Datos.Exc_Sql(this.Cn,Str_Sql);
           if(rt == true){
               JOptionPane.showMessageDialog(null,"Guardo Correctamente "+this.FechaActual); 
           }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo:getSaldoActual()"+e.getMessage()); 
        }
    }
    
 
    public boolean ExistsCentro(String Centro, String De, String Para){
       boolean Sw = false;
       try{
            String Str_Sql = "SELECT Fecha FROM selinlib.jsaldos WHERE fecha ="+this.FechaFinal+""
                            +" AND CodigoCac="+Centro+ " AND de="+De+" AND para="+Para;;
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            if (rs.next()) {
                try{
                    int vlr = rs.getInt("Fecha");
                }catch(Exception e){}
                Sw =true;
            }
       }catch(Exception e ){
           JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo:ExistsCentro"+e.getMessage()); 
       }
      return Sw;
    }
    public double get_Retenido_Calle48(){
        double VlrAcomulado = 0;
        try {
           String Str_Sql = "SELECT   SUM(PLEFSU) as Valor "     
                            +" FROM recaudos.LAPORT1 T01"
                            +" WHERE  PLAFEI = '"+this.FechaActual+ "'";
           ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
           if(rs.next()){
                VlrAcomulado = rs.getDouble("Valor");
           }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo:get_Retenido_Calle48()"+e.getMessage()); 
        }
        return VlrAcomulado;
    }
    public double getEfectivoPlanillaXCentro(String TxtCentro){
        double Vlr = 0;
        try{
            String Centro = "";
            String StrSql = "SELECT T01.CENCOD  , SUM(PLEFSU) as Valor "+
                             " FROM recaudos.LAPORT1 T01,  selinlib.jccentros T02 "+
                             " WHERE T01.CENCOD=T02.codigo  "+
                             " AND PLAFEI = '"+this.FechaActual+ "' AND T02.cac="+TxtCentro.trim()+
                             " GROUP BY T01.CENCOD  ORDER BY 1 ASC";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,StrSql);
            if(rs.next()){
                Vlr = rs.getDouble("Valor");
            }
        }catch(Exception e ){
           JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo:getEfectivoPlanillaXCentro()"+e.getMessage()); 
       }
       return  Vlr;
    }
    
    public double getEfectivoPlanilla(String Centro){
        //double Vlr = 0, Vlr74 = 0,Vlr30 = 0, VlrSNorte = 0,Vlr82 = 0,VlrSGym = 0, VlrViva = 0;
        double Vlr=0;
        try{
            //if(!this.CentroCosto.trim().equals("48")){
              if(!Centro.trim().equals("48")){
                //String Centro = "";
//                String StrSql = "SELECT T01.CENCOD  , SUM(PLEFSU) as Valor "+
//                             " FROM recaudos.LAPORT1 T01,  selinlib.jccentros T02 "+
//                             " WHERE T01.CENCOD=T02.codigo  "+
//                             " AND PLAFEI = '"+this.FechaActual+ "' AND T02.cac="+this.CentroCosto+
//                             " GROUP BY T01.CENCOD  ORDER BY 1 ASC ";
//                
                
                 String Str_Sql = "SELECT T02.cencod , SUM(PLEFSU) as Valor FROM recaudos.LAPORT1 T01, \n" +
                             " recaudos.CENTRO t02 , selinlib.jccentros                   \n" +
                             " WHERE T01.CENCOD =  T02.CENCOD  AND PLAFEI  = " +this.FechaActual+
                             " AND T01.CENCOD = codigo  AND CAC="+Centro
                             + " GROUP BY                          \n" +
                             " T02.CENCOD , T02.CENNOM  ORDER BY 1 ASC                    ";
                 //System.out.println(""+Str_Sql);
                ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                if(rs.next()){
                    Vlr = rs.getDouble("Valor");
                }
            }else{
                
                ////////////////////////////////////////////////////////////////////////////////////////////
                /////////////// Corregir                                      //////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////
                Vlr = this.get_Retenido_Calle48();
                //Vlr74 = this.getEfectivoPlanillaXCentro("74");
                //Vlr30 = this.getEfectivoPlanillaXCentro("30");
                //Vlr82 = this.getEfectivoPlanillaXCentro("82");
                //VlrSGym = this.getEfectivoPlanillaXCentro("76");
                //VlrSNorte = this.getEfectivoPlanillaXCentro("99");
                //VlrViva = this.getEfectivoPlanillaXCentro("55");
                //Vlr = Vlr - Vlr74-Vlr30-Vlr82-VlrSGym-VlrSNorte-VlrViva;
                
                double Vlr_Otras=0;
                try {
                    String Str_Sql ="select * from selinlib.JCCENTROS where Codigo<> 1 ";
                    //System.out.println(""+Str_Sql);
                    ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
                    while(rs.next()){
                        //System.out.println("+++++++++++++++++++++++++++++++ "+rs.getString("CAC"));
                        Vlr_Otras = Vlr_Otras + this.getEfectivoPlanilla(rs.getString("CAC"));
                    }
                    Vlr = Vlr - Vlr_Otras;
                    rs.close();
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Class:JIngresos: setCentroAtencion(String Codigo)  "+e.getMessage());
                }
                
            }
        }catch(Exception e ){
           JOptionPane.showMessageDialog(null,"Class:JAutomaticoSaldo:getEfectivoPlanilla()   "+e.getMessage()); 
       }
       return  Vlr;
    }
    
}
