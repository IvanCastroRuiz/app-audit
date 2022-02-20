import Sequelize from 'sequelize';
import db from '../config/db.js';

export const Jingreso = db.define('jingreso', {
    FECHA: {
        type: Sequelize.DATE
    },
    CHERET: {
        type: Sequelize.STRING
    },
    EFTCONS: {
        type: Sequelize.STRING
    },
    VTAGNB : {
        type: Sequelize.STRING
    },
    ABNRET: {
        type: Sequelize.STRING
    },
    VTARET: {
        type: Sequelize.STRING
    },
    CMBCHEQ: {
        type: Sequelize.STRING
    },
    CNJCONSI: {
        type: Sequelize.STRING
    },
    CCMBCHEQ: {
        type: Sequelize.STRING
    },
    SALANTVENT: {
        type: Sequelize.NUMBER
    },
    VVLRRETENI: {
        type: Sequelize.STRING
    },
    VVLRABONAD: {
        type: Sequelize.STRING
    },
    SALACTUAL: {
        type: Sequelize.NUMBER
    },
    SALANTCANJ: {
        type: Sequelize.NUMBER
    },
    CVLRRETENI: {
        type: Sequelize.STRING
    },
    CVLRABONAD: {
        type: Sequelize.STRING
    },
    CALACTUAL: {
        type: Sequelize.STRING
    },
});