/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.util.Vector;

/**
 *
 * @author Garyn Carrillo
 */
public class JJob {
    private Vector ListJob;
    private Vector NombreJob;
    private Vector Estado;
    public JJob(){
        ListJob = new Vector();
        NombreJob = new Vector();
    }
    public void add(Thread Job, String Nombre){
        ListJob.add(Job);
    }
    public int getCantidad(){
        return ListJob.size();
    }
}
