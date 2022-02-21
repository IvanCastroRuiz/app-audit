import React from 'react';
import IngresosDiarios from './IngresosDiarios';

const Home = ({consultar, consultaIngresos}) => {
    
    const ingresos = consultaIngresos;

    return (
            <div id="home" className="center">
                <h1 className="text-xs text-black uppercase px-6 py-4 " >Reporte de Ingresos</h1>
                    <IngresosDiarios
                        ingresos={ingresos}
                    /> 
            </div>
        )    
}

export default Home;