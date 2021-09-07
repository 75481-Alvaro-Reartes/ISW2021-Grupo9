import React from "react";
import {
    GoogleMap,
    withScriptjs,
    withGoogleMap
} from 'react-google-maps'

function Mapa(props) {
    return (
        <GoogleMap
            defaultZoom={12}
            defaultCenter={{ lat: -31.420224258389638, lng: -64.19168105418855 }}/>
    );
}

export default withScriptjs(
    withGoogleMap(
        Mapa
    )
);