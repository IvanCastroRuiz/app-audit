import express from 'express';
import  dotenv  from 'dotenv';
import cors from 'cors';
import router from './routes/index.js'
import db from './config/db.js'

dotenv.config({ path: 'variables.env' });

const app = express();

// Conectar la base de datos
db.authenticate()
    .then( () => console.log('Base de datos conectada'))
    .catch( error => console.log('error') );

//Definir puerto y host para el deployments
const port = process.env.PORT || 4000;
const host = process.env.HOST || '0.0.0.0';

// middlewares
// Se utiliza para realizar la comunicacion entre el servidor del frontend y el backend
app.use(cors()); 
app.use(express.json());
app.use(express.urlencoded({extended: true}));

// Agregar router
app.use('/', router);

// Puerto y host para la app 
app.listen(port, host, () =>{
    console.log(`El servidor esta funcionando en el puerto ${port} y host ${host}`);
} ) ; 