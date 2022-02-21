import { Jingreso } from '../model/Jingreso.js';

const diarios = async (req, res) => { //req - lo que enviamos : res - lo que express nos respode
    //const { fecha } = req.body;
    try {
        //const ingresosDiarios = await Jingreso.findAll({ where :{ fecha: fecha }});
        const ingresosDiarios = await Jingreso.findAll();
        // Devolver una respuesta al frontend
        return res.status(200).send({
            status: 'success',
            ingresosDiarios
        });
    } catch (error) {
        console.log(error);
    }

};

const planillas = (req, res) => { //req - lo que enviamos : res - lo que express nos respode
    res.send("Planillas");       
};

const administrationIngresos = (req, res) => { //req - lo que enviamos : res - lo que express nos respode
    res.send("Administracion de Ingresos");       
};

const planillaRecaudosPOS = (req, res) => { //req - lo que enviamos : res - lo que express nos respode
    res.send("Planillas Recuados POS");       
};

const subEspecieValePersonal = (req, res) => { //req - lo que enviamos : res - lo que express nos respode
    res.send("Sub. Especie y Vale Personal");       
};

export {
    diarios,
    planillas,
    administrationIngresos,
    planillaRecaudosPOS,
    subEspecieValePersonal
}