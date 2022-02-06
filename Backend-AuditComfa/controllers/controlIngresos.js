

const diarios = (req, res) => { //req - lo que enviamos : res - lo que express nos respode
    res.send("Ingresos Diarios");       
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