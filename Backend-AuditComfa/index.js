import express from 'express';
import router from './routes/index.js'

const app = express();

//Definir puerto y host para el deployments
const port = process.env.PORT || 4000;
const host = process.env.HOST || '0.0.0.0';

app.use(express.urlencoded({extended: true}));

// Agregar router
app.use('/', router);

// Puerto y host para la app 
app.listen(port, host, () =>{
    console.log(`El servidor esta funcionando en el puerto ${port} y host ${host}`);
} ) ; 
