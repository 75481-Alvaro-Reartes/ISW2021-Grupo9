import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { Container, CssBaseline } from '@material-ui/core';
import Navegacion from './components/Navegacion';
import Productos from './pages/LoQueSea/Productos';
import Principal from './pages/Principal/Principal';
import Ubicacion from './pages/LoQueSea/Ubicacion';

export default function App() {
  return (
    <div >
      <BrowserRouter>
      <CssBaseline/>
        <Navegacion/>
        <Container maxWidth="md">
        <Switch>
          <Route exact path="/" component={Principal}/>
          <Route exact path="/loquesea/productos" component={Productos}/>
          <Route exact path="/loquesea/ubicacion" component={Ubicacion}/>
        </Switch>
        </Container>
      </BrowserRouter>
    </div>
  );
}