package BD_As400;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */
import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.CommandCall;
import com.ibm.as400.security.auth.ProfileTokenCredential;
import java.io.*;

import java.sql.*;
import javax.swing.*;
import java.sql.DriverManager;


public class JConection {
        private ResultSet rs;
        private String RutaLocal ;
        private Statement st;
	public	JConection (){
            
	}
       
	public Connection Open_BD(String Usuario, String Contraseña){
	     Connection con = null;		
             String StrConnection , DrvConnection ;
	     try{
                String db = "QSYS";
                String Ipserver = "172.16.0.1";
                DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver());
		StrConnection = "jdbc:as400://" + Ipserver + ":6789/" + db;
		DrvConnection = "sun.jdbc.odbc.JdbcOdbcDriver";
		con  = DriverManager.getConnection(StrConnection,Usuario.trim(),Contraseña.trim());
                DriverManager.setLoginTimeout(-1);
                System.out.println("Tiempo de Espera Actual "+DriverManager.getLoginTimeout());
	     }catch (Exception e){
			JOptionPane.showConfirmDialog(null, "Class:JConection:Connection Open_BD() "+e.getMessage());
	     }
	     return con;			
	}
        public AS400 Servicios_As400(String Usuario, String Contraseña){
              
             try {
                 final AS400 as400 = new AS400("172.16.0.1", Usuario.trim(),Contraseña.trim());
                 as400.connectService(AS400.COMMAND);
            
                 final ProfileTokenCredential pt = new ProfileTokenCredential();
                 pt.setSystem(as400);
                 pt.setTimeoutInterval(60);
                 //pt.setToken("FCMICROINT", ProfileTokenCredential.PW_NOPWDCHK);
                 //Swapper.swap(as400, pt);
            
                 
                 //final CommandCall call = new CommandCall(as400, "CALL PGM(SELINLIB/CLAUDI0011) PARM('1' '19677' '201307' '20130821')");
                 
                 //pt.destroy();

                 //as400.disconnectService(AS400.COMMAND);
                 //as400.disconnectAllServices();
                 return  as400;
          } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Run as400"+e.getMessage()); 
          }
          return null;
        }
	public void Close_BD(Connection conection){
		try{
                    conection.close();
		}catch(Exception e){
			JOptionPane.showConfirmDialog(null, "Class:JConection:Close_BD() "+e.getMessage());
		}
	}

	public boolean Exc_Sql(Connection conection , String Str_SQL ){
	    boolean Estado = false;
            Statement st2=null;
	    try{	
		st2 = conection.createStatement();
		st2.executeUpdate(Str_SQL);
		Estado = true;
                st2.close();
            }catch(SQLException e){
			JOptionPane.showConfirmDialog(null, "Class:JConection:Exc_Sql() "+e.getMessage());
            }
            return Estado;
	}

	public ResultSet SQL_QRY (Connection conection, String SQL_QRY ){
		rs = null;
		try{
   		     st = conection.createStatement();
		     rs = st.executeQuery( SQL_QRY );
                    
		}catch(Exception e){
			JOptionPane.showConfirmDialog(null, "Class:JConection:SQL_QRY() "+e.getMessage());
		}
		return rs; 
	}	
        public void CloseResulSet(){
            try {
                rs.close();
                st.close();
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, "Class:CloseResulSet() "+e.getMessage());
            }
        }
}
