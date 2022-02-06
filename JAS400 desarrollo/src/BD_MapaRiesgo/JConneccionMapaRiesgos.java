package BD_MapaRiesgo;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */
import Principal.JLeeFichero;
import java.io.*;
import java.sql.*;
import javax.swing.*;

public class JConneccionMapaRiesgos {
        private JLeeFichero Rl;
        private String RutaServidor ;
        public	JConneccionMapaRiesgos (  ){
           Rl = new JLeeFichero();
           Rl.Leer();
           RutaServidor = Rl.getServidor();
	}
        
	public Connection Open_BD_Mapa_Riesgos(){
	     Connection con = null;		
             String StrConnection , DrvConnection ;
	     try{
		String db = RutaServidor;
                //"Z:\\DB\\bd_talleresriesgos.mdb"
		StrConnection = "jdbc:odbc:MS Access Database;DBQ=" + db;
		DrvConnection = "sun.jdbc.odbc.JdbcOdbcDriver";
		con  = DriverManager.getConnection(StrConnection,"riesgo","riesgo");
	     }catch (Exception e){
		JOptionPane.showConfirmDialog(null, e.getMessage());	
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
}
