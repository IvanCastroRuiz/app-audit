package Principal;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */
import java.io.*;
import javax.swing.*;
public class JLeeFichero {
    
   private File archivo = null;
   private FileReader fr = null;
   private BufferedReader br = null; 
   private String RutaLocal;
   private String RutaServidor;
   private String RutaLogo;
   private String RutaAudiSys;
   private String RutaJReport;
   private String RutaEncabezadoRuta;
   private String Ruta_GIASS;
   public JLeeFichero(){
       this.RutaLocal     = "";
       this.RutaServidor  = "";
       this.RutaLogo      = "";
       this.RutaAudiSys   = "";
       this.RutaJReport   = "";
       this.RutaEncabezadoRuta = "";
       this.Ruta_GIASS="";
   } 
   public String getRutaLocal(){
       return this.RutaLocal;
   }
   public String getServidor(){
       return this.RutaServidor;
   }
   public String getLogo(){
       return this.RutaLogo;
   }
   public String getAudiSys(){
       return this.RutaAudiSys;
   }
   public String getRutaJReport(){
       return this.RutaJReport;
   }
   public String getRutaEncabezado(){
       return this.RutaEncabezadoRuta;
   }
   public String Ruta_Ruta_GIASS(){
       return Ruta_GIASS;
   }
           
   
   public void setRutaLocal(String Ruta){
       this.RutaLocal = Ruta;
   }
   public void setRutaServidor(String Ruta){
       this.RutaServidor =Ruta;
   }
   public void setRutaLogo(String Ruta){
       this.RutaLogo = Ruta;
   }
   public void setRutaAudiSys(String Ruta){
       this.RutaAudiSys = Ruta;
   }
   public void setRutaJReport(String Ruta){
       this.RutaJReport = Ruta;
   }
   public void setRutaEncabezado(String Ruta){
       this.RutaEncabezadoRuta = Ruta;
   }
   
   public  void Leer () {
      try {
         archivo = new File ("C:\\ParametrizacionAplicacion.txt");
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);
         // Lectura del fichero
         String linea;
         int fila = 0;
         while((linea=br.readLine())!=null){
            fila ++;
            if (fila==1){
                this.RutaLocal = linea.trim();
            }
            if (fila==2){
                this.RutaServidor = linea.trim();
            }
            if (fila==3){
                this.RutaLogo = linea.trim();
            }
            if (fila==4){
                this.RutaAudiSys = linea.trim();
            }
            if (fila==5){
                this.RutaEncabezadoRuta = linea.trim();
            }
            if (fila==6){
                this.RutaJReport = linea.trim();
            }
            if (fila==7){
                this.Ruta_GIASS = linea.trim();
            }
         }
      }catch(Exception e){
         System.out.println(e.getMessage());
      }finally{
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            System.out.println(e2.getMessage());
         }
      }
   }
}
