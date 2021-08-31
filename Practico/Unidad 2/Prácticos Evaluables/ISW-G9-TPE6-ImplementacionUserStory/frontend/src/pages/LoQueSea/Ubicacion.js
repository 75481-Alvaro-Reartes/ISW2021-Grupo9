import { Grid } from "@material-ui/core";
import Mapa from "../../components/Mapa";
import credenciales from '../../utils/credenciales';


const urlMaps = `https://maps.googleapis.com/maps/api/js?key=${credenciales.maps}`;

export default function Ubicacion() {
    return (
        <div>
            <Grid container>
                <Grid item sm={12}>
                    <h1>Ubicación</h1>

                    <Mapa
                        googleMapURL={urlMaps}
                        containerElement={<div style={{ height: '400px' }} />}
                        mapElement={<div style={{ height: '100%' }} />}
                        loadingElement={<p>Cargando...</p>} />

                    
                </Grid>
            </Grid>
        </div>
    );
}