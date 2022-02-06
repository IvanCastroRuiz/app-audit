package Auditoria;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */
public class JUserAudiSys {
    private String Nombre_Completo;
    private String Nombre_Corto;
    
    public JUserAudiSys(){
    }
    public String getNombre_Completo(){
        return this.Nombre_Completo;
    }
    public String getNombre_Corto(){
        return this.Nombre_Corto;
    }
    public void setNombre_Completo(String Nombre){
        this.Nombre_Completo = Nombre;
    }
    public void setNombre_Corto(String Nombre){
        this.Nombre_Corto = Nombre;
    }
}
