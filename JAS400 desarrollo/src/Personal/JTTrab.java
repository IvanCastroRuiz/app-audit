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
public class JTTrab {
    private JConection JBase_Datos;
    private Connection Cn;
    private Object [] Cabecera ;
    private Object [] [] Detalle  ;
    private String Documento;
    private int Posicion;
    private DecimalFormat JFormato ;
    private String NumeroFormato = "###,###,###,###.##";
    
    public JTTrab(JConection JBase_Datos3, Connection Cn2, String DocumentoP){
        this.JBase_Datos = JBase_Datos3;
        this.Cn = Cn2;
        this.Documento = DocumentoP;
        Detalle = new Object [5000][6];
        Cabecera =  new Object [6];
        this.getEmpleados();
        JFormato= new DecimalFormat(NumeroFormato);
    }
    public Object [] getCabecera(){
        return  Cabecera;
    }
    public Object [][] getDetalle(){
        return  Detalle;
    } 
    public void getEmpleados(){
      try{
         String Str_Sql;
         if(Documento.equals("*all")){
            Str_Sql = "select t1.tracve, tranom, t2.puedes,t3.trarfi, trasex, trafin,trafan, trafba, trasit from adamco.ttrab as t1 , adamco.tpues as t2, adamco.ttrns as t3 where  "
                    + "  t2.puecve = t3.puecve and t1.tracve = t3.tracve "
                    + " order by tracve asc";
         }else{
            Str_Sql = "select t1.tracve, tranom, t2.puedes ,t3.trarfi, trasex, trafin,trafan, trafba, trasit from adamco.ttrab as t1, adamco.tpues as t2, adamco.ttrns as t3 where  "
                    + "  t2.puecve = t3.puecve and t1.tracve = t3.tracve "
                    + " and tracve = '"+Documento+"'order by tracve asc";
         }
         ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
         Cabecera[0]="Codigo";
         Cabecera[1]="Nombre";
         Cabecera[2]="Fecha de Ingreso";
         Cabecera[3]="Fecha de Retiro";
         Cabecera[4]="Cargo";
         Cabecera[5]="Documento de Identidad";
         this.Posicion = -1;
         while (rs.next()){
             this.Posicion ++;
             Detalle[Posicion][0]=rs.getString("tracve");
             Detalle[Posicion][1]=rs.getString("tranom");
             Detalle[Posicion][2]=""+rs.getInt("trafan");;
             Detalle[Posicion][3]=""+rs.getInt("trafba");;
             Detalle[Posicion][4]=""+rs.getString("puedes").trim();
             Detalle[Posicion][5]=""+rs.getString("trarfi");
         }
      }catch(Exception e ){
          JOptionPane.showMessageDialog(null," getEmpleados: "+e.getMessage()); 
      }
    }
}
