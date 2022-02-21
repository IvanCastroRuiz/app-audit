// Importamos React
import React, {Component} from 'react';
import logo from '../assets/images/comfa.jpg'
// import logo1 from '../assets/images/logomintic.png'
//import { NavLink } from 'react-router-dom';
 
//Defenimos una clase
class Header extends Component {
    // Metodo render (Se encargara de mostrar la vista al usuario)
    render() {
        return (
                <header id="header">
                    <div className="center">
                        {/* LOGO */}
                        <div id="logo">
                            <div>
                                <img src={logo} className="app-logo" alt="logotipo"/>
                            </div>
                            <div>
                                <span id="brand">
                                    <strong>APP</strong> AuditComfa
                                </span>
                                
                            </div>
                             
                        </div>
                        {/* MENU */}
                        <nav id="menu">
                            <ul>
                                <li>
                                    Inicio
                                    {/* <NavLink to="/home" activeClassName="active">Inicio</NavLink> */}
                                </li>
                                <li>
                                    Ingresos
                                    {/* <NavLink to="/home" activeClassName="active">Ingresos</NavLink> */}
                                </li>
                            </ul>
                        </nav>

                        {/* LIMPIAR FLOTADOS */}
                        <div className="clearfix">
                            
                        </div>

                    </div>
                </header>            
            )    
    }
}
// Lo exportamos 
export default Header;