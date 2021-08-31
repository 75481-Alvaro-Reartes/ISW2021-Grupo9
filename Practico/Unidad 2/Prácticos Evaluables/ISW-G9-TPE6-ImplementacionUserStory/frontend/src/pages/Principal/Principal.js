import { Link } from "react-router-dom";
import { Button, Grid, makeStyles } from '@material-ui/core';
import { COLOR_PRIMARIO, COLOR_PRIMARIO_OSCURO } from './../../utils/colores.js';

const estilos = makeStyles({
    margenTop: {
        marginTop: '20px'
    },
    boton: {
        background: COLOR_PRIMARIO,
        width: '15em',
        "&:hover": {
            background: COLOR_PRIMARIO_OSCURO
        }
    },
    root: {
        height: "100%"
    }
});

export default function Principal() {
    const clases = estilos();

    return (
        <div>
            <Grid
                container
                direction="column"
                alignItems="center"
                justifyContent="center"
                className={clases.root}>

                <Link className={clases.margenTop} style={{ textDecoration: 'none' }}>
                    <Button className={clases.boton} variant="contained" color="primary">De comercio</Button>
                </Link>

                <Link className={clases.margenTop} to="/loquesea/productos" style={{ textDecoration: 'none' }}>
                    <Button className={clases.boton} variant="contained" color="primary">Lo que sea</Button>
                </Link>
            </Grid>

        </div>
    );
}