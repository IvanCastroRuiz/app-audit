import React from 'react';

const Ingreso = ({ingreso}) => {

    const {FECHA, SALACTUAL, SALANTVENT, SALANTCANJ, CALACTUAL} =  ingreso;

    return (
                <tr>
                    <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                        <p className="text-gray-600 font-bold">{FECHA}</p>
                    </td>
                    <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200 ">
                        <p className="text-gray-600">{SALACTUAL}</p>
                    </td>
                    <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-600">    
                        <p className="text-gray-600">{SALANTVENT}</p>
                    </td>
                    <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                        <p className="text-gray-600">{SALANTCANJ}</p>
                    </td>
                    <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                        <p className="text-gray-600">{CALACTUAL}</p>
                    </td>
                    <td className="px-6 py-4 whitespace-no-wrap border-b border-gray-200 text-sm leading-5">
                        <a id="editar" href="editar-contacto.html?id=${id}" data-contacto="${id}" className="text-teal-600 hover:text-teal-900 mr-5">EXCEL</a>
                        <a id="eliminar" href="#" data-contacto="${id}" className="eliminar text-red-600 hover:text-red-900">PDF</a>
                    </td>
                </tr>
            )
}

export default Ingreso;