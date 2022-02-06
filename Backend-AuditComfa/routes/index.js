import express from 'express';
import {
    paginaInicio
} from '../controllers/paginasController.js';

import {
    diarios,
    planillas,
    administrationIngresos,
    planillaRecaudosPOS,
    subEspecieValePersonal    
} from '../controllers/controlIngresos.js';

const router = express.Router();

//Rutas utilies
router.get('/', paginaInicio);


//Rutas Control Ingresos
router.get('/ingresos/diarios', diarios);
router.get('/ingresos/planillas', planillas);
router.get('/ingresos/adm-ingresos', administrationIngresos);
router.get('/ingresos/planilla-recaudo', planillaRecaudosPOS);
router.get('/ingresos/sub-esp-vale-personal', subEspecieValePersonal);

export default router;