import React from 'react';
import Ingreso from './Ingreso';

const IngresosDiarios = ({ingresos}) => {

    console.log(ingresos);

    return (
        <main className="md:w-3/5  xl:w-4/5 px-5 py-10 bg-gray-200">
            <div className="flex flex-col mt-100">
                <div className="py-2 overflow-x-auto">
                    <div className="align-middle inline-block min-w-full shadow overflow-hidden sm:rounded-lg border-b border-gray-200">
                        <table className="min-w-full">
                            <thead className="bg-gray-100 ">
                                <tr>
                                    <th id="FECHA" className="px-6 py-3 border-b border-gray-200  text-left text-xs leading-4 font-medium text-gray-600 uppercase tracking-wider">
                                            FECHA
                                    </th>
                                    <th id="SALACTUAL" className="px-6 py-3 border-b border-gray-200  text-left text-xs leading-4 font-medium text-gray-600 uppercase tracking-wider">
                                            SALACTUAL
                                    </th>
                                    <th id="SALANTVENT" className="px-6 py-3 border-b border-gray-200  text-left text-xs leading-4 font-medium text-gray-600 uppercase tracking-wider">
                                            SALANTVENT
                                    </th>
                                    <th id="SALANTCANJ" className="px-6 py-3 border-b border-gray-200  text-left text-xs leading-4 font-medium text-gray-600 uppercase tracking-wider">
                                            SALANTCANJ	
                                    </th>
                                    <th id="CALACTUAL" className="px-6 py-3 border-b border-gray-200  text-left text-xs leading-4 font-medium text-gray-600 uppercase tracking-wider">
                                            CALACTUAL
                                    </th>
                                    <th id="acciones" className="px-6 py-3 border-b border-gray-200  text-left text-xs leading-4 font-medium text-gray-600 uppercase tracking-wider">
                                        Acciones
                                    </th>
                                </tr>
                            </thead>

                            <tbody id="listado-contactos" className="bg-white">
                                {
                                    ingresos.map( (ingreso, i ) => {
                                        return (
                                            <Ingreso
                                                key={i}
                                                ingreso={ingreso}
                                            />
                                        )
                                    })
                                        
                                }
                            </tbody>  
                        </table>
                    </div>
                </div>
            </div>
        </main>
        )    
}

export default IngresosDiarios;