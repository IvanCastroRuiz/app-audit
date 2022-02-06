import express from 'express';
import {
    paginaInicio
} from '../controllers/paginasController.js';

const router = express.Router();

//Rutas utilies
router.get('/', paginaInicio);

export default router;