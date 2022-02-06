/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Subsidio.Escolar;

import BD_As400.JConection;
import BD_As400.JConection;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Garyn Carrillo
 */
public class JAuxEscolar extends Thread{
    private JConection JBase_Datos;
    private Connection Cn; 
    private String Periodo;
    public void run(){
        this.ContieneSaltos();  
        JOptionPane.showMessageDialog( null,"Finalizo el proceso correctamente ");    
    }
    public void  JAuxEscolar(JConection Jbase, Connection cn2, String Periodo){
        this.JBase_Datos = Jbase;
        this.Cn = cn2;
        this.Periodo = Periodo;
    }
    private boolean ContieneSaltos(){
        boolean  Rs = false;
//        if ( this.Clear_File()){
//            Clear_File_Error();
            try {
//                String Str_Sql = "insert into selinlib.jauxescola "
//                        + " select bectra,bechij,becper,becned from subsilib.MBECAS where becper="+this.Periodo+" and becvpg<>0 ";
//                Rs = JBase_Datos.Exc_Sql(Cn,Str_Sql);
                this.getMuestra();
            } catch (Exception e) {
                JOptionPane.showMessageDialog( null,"JAuxEscolar:ContieneSaltos() "+e.getMessage());         
//            }
        }
        return Rs;
    }
    private void getMuestra(){
        try {
            String Str_Sql = "select bectra,bechij,becned,becper from selinlib.jauxescola order by bectra, bechij, becper desc";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while (rs.next()) {
                String Documento = rs.getString("bectra");
                String Hijo = rs.getString("bechij");
                String Gra = rs.getString("becned");
                String Per = rs.getString("becper");
                this.getFoundError(Documento, Hijo, Gra, Per);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog( null,"JAuxEscolar:getMuestra() "+e.getMessage());         
        }
    }
    private boolean getPeriodoPosteriorCongradoInferior(String Documento_trabajador, String Documento_hijo, String Grado, String Periodo){
        boolean RsExc = false;
        try {
            String Str_Sql = "select becned,becper from subsilib.mbecas where bectra="+Documento_trabajador+" and "
                    + " bechij="+Documento_hijo+" and becned<"+Grado+" and becper >"+Periodo
                    + " group by becned,becper";
            System.out.println(""+Str_Sql);
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            while (rs.next()) {
                String Gr= rs.getString("becned");
                String Pr =rs.getString("becper");        
                Str_Sql ="INSERT INTO SELINLIB.JAUXERROR (BECTRA,BECHIJ,BECPER,BECGRA,ERRPER,ERRGRA) VALUES ("+Documento_trabajador+","+Documento_hijo+","+Periodo+","+Grado+","+Gr+","+Pr+")";
                RsExc = JBase_Datos.Exc_Sql(Cn,Str_Sql);
            }
            JBase_Datos.CloseResulSet();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog( null,"JAuxEscolar:getPeriodoPosteriorCongradoInferior() "+e.getMessage());         
        }
        return RsExc;
    }
    private void getFoundError(String Documento_trabajador, String Documento_hijo, String Grado, String Periodo){
        try {
            String Str_Sql = "select becned, becper from subsilib.mbecas where bectra="+Documento_trabajador+" and "
                    + " bechij="+Documento_hijo+" and becned<="+Grado+" "
                    + " group by becned,becper"
                    + " order by becper asc ";
            ResultSet rs = JBase_Datos.SQL_QRY(this.Cn,Str_Sql);
            int count = 0;
            while (rs.next()) {
                String Periodo2 = rs.getString("becper");
                String Grado2 =rs.getString("becned");
                getPeriodoPosteriorCongradoInferior(Documento_trabajador,Documento_hijo,Grado2,Periodo2);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog( null,"JAuxEscolar:getFoundError() "+e.getMessage());         
        }
    }
    private boolean Clear_File(){
        boolean  Rs = false;
        try {
            String Str_Sql = "delete from selinlib.jauxescola ";
            Rs = JBase_Datos.Exc_Sql(Cn,Str_Sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog( null,"JAuxEscolar:Clear_File() "+e.getMessage());         
        }
        return Rs;
    }
    private boolean Clear_File_Error(){
        boolean  Rs = false;
        try {
            String Str_Sql = "delete from selinlib.JAUXERROR ";
            Rs = JBase_Datos.Exc_Sql(Cn,Str_Sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog( null,"JAuxEscolar:Clear_File_Error() "+e.getMessage());         
        }
        return Rs;
    }
}
