package BD_Auditoria;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GACA1186
 */
import Principal.JLeeFichero;
import java.io.*;
import java.sql.*;
import javax.swing.*;

public class JConeccion_AudiSys { 
        private String RutaLocal ;
        private JLeeFichero Rl;
	public	JConeccion_AudiSys (  ){
           Rl = new JLeeFichero();
           Rl.Leer();
           RutaLocal = Rl.getRutaLocal();
	}
	public Connection Open_BD(){
	     Connection con = null;		
             String StrConnection , DrvConnection ;
	     try{
                String db = RutaLocal;
                //"C:\\Users\\Julio C. Perez\\Documents\\Software Auditoria\\AudiSys\\build\\classes\\Base.mdb"
		//Driver para 32 Bits
                //StrConnection = "jdbc:odbc:MS Access Database;DBQ=" + db;
                //Driver para 64 Bits
                StrConnection = "jdbc:odbc:Driver="
                              + "{Microsoft Access Driver (*.mdb, *.accdb)}; DBQ="+db;
		DrvConnection = "sun.jdbc.odbc.JdbcOdbcDriver";
		//Connection con=DriverManager.getConnection(StrConnection,"root","");
		Class.forName(DrvConnection).newInstance();
		con  = DriverManager.getConnection(StrConnection,"","");
	     }catch (Exception e){
			JOptionPane.showConfirmDialog(null, "Jerror AudiSys() "+e.getMessage());
	     }
	     return con;			
	}

	public void Close_BD(Connection conection){
		try{
			conection.close();
		}catch(Exception e){
			JOptionPane.showConfirmDialog(null, e.getMessage());
		}
	}

	public boolean Exc_Sql(Connection conection , String Str_SQL ){
	    boolean Estado = false;
	    try{	
		Statement st = conection.createStatement();
		st.executeUpdate(Str_SQL);
		Estado = true;
            }catch(SQLException e){
			JOptionPane.showConfirmDialog(null, e.getMessage());
            }
            return Estado;
	}

	public ResultSet SQL_QRY (Connection conection, String SQL_QRY ){
		ResultSet rs = null;
		Statement st;
		try{
   		     st = conection.createStatement();
		     rs = st.executeQuery( SQL_QRY );
		}catch(Exception e){
			JOptionPane.showConfirmDialog(null, e.getMessage());
		}
		return rs; 
	}
        
	public void getArquitecturaSystemOperativo(){
               String Os=System.getProperty("os.name");
               String Osv=System.getProperty("os.version");
               String Osc=System.getProperty("os.arch");
               String Osd=System.getProperty("user.dir");
               String Osn=System.getProperty("user.name");
        }		
}