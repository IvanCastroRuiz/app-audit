/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Vivienda;

/**
 *
 * @author Garyn Carrillo
 */
public class JProceso_Fovis {
      String Ano1,mes1,Dia1,Ano2,mes2,Dia2,Fecha2;
      double NoAplica,OtherMes;
      double P1,P2,P3,P4,P5,P6;
      int Per;
      public JProceso_Fovis(int Periodo){
          this.Per =Periodo;
                  
      }
      public void Clear_all(){
          String Str_Sql ="DELETE FROM selinlib/ZPUNTUACI1";
          Str_Sql ="DELETE FROM selinlib/ZPUNTUACI0";
          Str_Sql ="DELETE FROM selinlib/ZPUNTUACI2 ";
          Str_Sql ="DELETE FROM selinlib/ZPUNTUACI3";
          Str_Sql ="DELETE FROM selinlib/ZPUNTUACI4";
          Str_Sql ="DELETE FROM selinlib/ZPUNTUACI5";
          Str_Sql ="DELETE FROM selinlib/ZPUNTUACI6";
          
          Str_Sql ="insert into selinlib/ZPUNTUACI0  "+
                   " select  t1.fipnro "+               
                   " from vivienda/FINSPOS t1  "+       
                   " where calper ="+Per+           
                   " group by t1.fipnro               ";
          
          Str_Sql="Insert into selinlib/ZPUNTUACI1 "+               
                  " select  calper , t1.fipnro , count(*) "+        
                  " from vivienda/DINSPOS t1 ,vivienda/FINSPOS t2 "+
                  " where  t1.FIPNRO = t2.FIPNRO "+                 
                  " and calper = "+Per+                             
                  " group by calper , t1.fipnro "+                  
                  " order by fipnro             ";
          
          Str_Sql=" insert into selinlib/ZPUNTUACI3 "+ 
                  " select HISDOC , HISNRO , HISNOV "+
                  " from vivienda/mdhispo          ";
          
          
          Str_Sql =" INSERT INTO  SELINLIB/ZPUNTUACI4 "+          
                   " SELECT HISDOC , HISNOV "+                    
                   " FROM vivienda/MDHISPO WHERE HISCFA ="+Per;
          
          Str_Sql="INSERT INTO SELINLIB/ZPUNTUACI5 "+ 
                   " SELECT  TEMICH , TEMFEC "+        
                   " FROM vivienda/TTEMCAL "+          
                   " WHERE temcal = "+Per;
      }
}
