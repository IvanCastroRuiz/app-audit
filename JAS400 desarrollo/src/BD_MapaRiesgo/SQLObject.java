package BD_MapaRiesgo;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Version : Clase para validacion de SQL Injecction
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */
public class SQLObject {
    
    public SQLObject(){
    
    }
    public boolean IS_SQL_INJECTION (String SQL){
		for (int i = 1 ; i< SQL.length() ; i++ ){
			if ( (SQL.charAt(i) == '<') ||  ( SQL.charAt(i) == '>')
			      ||  ( SQL.charAt(i) == '=')
                           ){
				return true;
			}		
		}
		return false ;
   }
}
