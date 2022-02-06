package Principal;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GACA1186 (Garyn Jairo Carrillo Caballero-2012)
 */
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Invitado
 */
public class MiLabelCalendario extends JLabel {

    public LineBorder lb = null;

    public MiLabelCalendario( String nombre ) {

            super( nombre );
            setHorizontalAlignment( JLabel.CENTER);
    }

    public void seleccionar(){

        seleccionar( new LineBorder( java.awt.Color.BLUE) );
        
    }

    public void seleccionar( LineBorder l ){

        lb=l;
        setBorder( lb );
    }


}
