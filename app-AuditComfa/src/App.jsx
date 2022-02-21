import React, { useEffect, useState } from 'react';
import clienteAxios from './config/axios';
import Header from "./components/Header";
import Slider from "./components/Slider";
import Home from "./components/Home";

function App() {

  const [consultar, setConsultar] = useState(true);
  const [consultaIngresos, setConsultaIngresos] = useState([]);

  useEffect( () => {
    if(consultar) {
      console.log('Conectando');
      const consultarAPI = () => {
        clienteAxios.get('/ingresos/diarios')
            .then(respuesta => {
            // colocar en el state el resultado
              setConsultaIngresos(respuesta.data.ingresosDiarios);               
            })
            .catch(error => {
              console.log(error)
            })
      }
      consultarAPI();
    }
  }, [consultar] );

  return (
    <div className="App">
         {/* Componente de Rutas y paginas */}
         <Header/>
         <Slider/>
         <Home
          consultar={consultar}
          consultaIngresos={consultaIngresos}
         />
    </div>
  )
}

export default App