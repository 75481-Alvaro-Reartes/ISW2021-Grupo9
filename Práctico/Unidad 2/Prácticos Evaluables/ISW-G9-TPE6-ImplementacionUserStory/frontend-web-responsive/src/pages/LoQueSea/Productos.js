import { Grid, makeStyles, Switch, TextField, Typography, FormControlLabel, Button } from "@material-ui/core";
import { DropzoneArea } from "material-ui-dropzone";
import { useState } from "react";
import { Link } from "react-router-dom";

const estilos = makeStyles({
    entrada: {
    },
});

export default function Productos() {
    const clases = estilos();

    const [valores, setValores] = useState({
        llegaPronto: true,
    });

    return (
        <div>
            <Grid container>
                <Grid item xs={12} >
                    <TextField
                        label="Qué buscar?"
                        required
                        variant="outlined"
                        multiline
                        minRows={5}
                        margin="normal"
                        fullWidth
                        className={clases.entrada} />
                </Grid>
                <Grid
                    item
                    sm={12}>
                    <Typography variant="h6">Agregar imágenes (opcional)</Typography>
                </Grid>
                <Grid item xs={12}>
                    <DropzoneArea
                        onChange={(archivos) => { console.log(archivos) }}
                        acceptedFiles={['image/*']}
                        dropzoneText={"Soltá las imágenes acá o hacé click"}
                        maxFileSize={5000000}
                        filesLimit={10}
                        previewText={"Imágenes cargadas"}
                        previewGridProps={{ container: { spacing: 1, direction: 'row', justifyContent: 'flex-start' }, item: { sm: 3 } }} 
                        getFileAddedMessage={(archivo) => `Se agregó el archivo ${archivo}`}
                        getFileRemovedMessage={(archivo) => `Se eliminó el archivo ${archivo}`}
                        getDropRejectMessage={(rechazado, aceptados, maxSize) => {
                            let msg = `No se puede agregar el archivo ${rechazado.name}`;
                            if (!aceptados.includes(rechazado.type)) {
                                msg += ", debe ser una imagen";
                            }
                            if (rechazado.size > maxSize) {
                                msg += ", es demasiado grande";
                            }
                            return msg;
                        }}/>
                </Grid>
                <Grid
                    item
                    sm={12}>
                    <FormControlLabel
                        control={<Switch />}
                        label={valores.llegaPronto ? 'Recibirlo lo antes posible' : 'Programar entrega'}
                        checked={valores.llegaPronto}
                        onChange={(e) => { setValores({ ...valores, 'llegaPronto': e.target.checked }) }} />
                </Grid>
                <Grid
                    item
                    sm={12}>
                    <input type="datetime-local" hidden={valores.llegaPronto} />
                </Grid>
                <Grid
                    container
                    justifyContent="flex-end">
                    <Grid
                        item
                        sm={2}>
                        <Link to="/"  style={{ textDecoration: 'none' }}>
                            <Button
                                variant="contained">
                                Cancelar
                            </Button>
                        </Link>
                    </Grid>
                    <Grid
                        item
                        sm={2}>
                        <Link to="/loquesea/ubicacion" style={{ textDecoration: 'none' }}>
                            <Button
                                variant="contained"
                                color="primary">
                                Continuar
                            </Button>
                        </Link>
                    </Grid>
                </Grid>
            </Grid>
        </div>
    );
}