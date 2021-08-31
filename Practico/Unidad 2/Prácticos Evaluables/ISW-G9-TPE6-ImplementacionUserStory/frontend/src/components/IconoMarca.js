import L from 'leaflet';
import icono from '../assets/marca.svg';

export const IconoMarca = L.icon({
    iconUrl: icono,
    iconSize: [35, 35],
    className: 'leaflet-venue-icon',
});