import React, {Component} from 'react';
import logo from '../assets/images/header-web.png'
import { NavLink } from 'react-router-dom';
//Defenimos una clase
class Slider extends Component {
    // Metodo render (Se encargara de mostrar la vista al usuario)
    render() {
        return (
            <div>
                <div id="slider" className="big">
                    <img src={logo} className="app-logo" alt="header-web"/>
                </div> 
                
                {/*LIMPIAR FLOTADOS*/}
                <div className="clearfix">
                            
                </div>
            </div>
            )    
    }
}
// Lo exportamos 
export default Slider;