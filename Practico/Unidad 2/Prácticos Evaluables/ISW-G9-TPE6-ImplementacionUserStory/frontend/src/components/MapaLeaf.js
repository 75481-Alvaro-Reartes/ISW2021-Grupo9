import React, { useState } from 'react';
import { MapContainer, TileLayer, useMapEvent, Marker } from "react-leaflet";
import 'leaflet/dist/leaflet.css';
import {IconoMarca} from './IconoMarca';

function ClickMapa(props) {
    useMapEvent('click', (e) => {
        props.click(e.latlng);
    });
    return null;
}

export default function MapaLeaf(){

    const [marca, setMarca] = useState(null);

    return (
        <MapContainer
            center={{lat: '-31.420224258389638', lng: '-64.19168105418855'}} 
            zoom={12}
            style={{height: '400px'}}>

            <TileLayer
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'/>

            <ClickMapa click={setMarca}/>

            {marca && <Marker position={marca} icon={IconoMarca}/>}

        </MapContainer>
    );
}