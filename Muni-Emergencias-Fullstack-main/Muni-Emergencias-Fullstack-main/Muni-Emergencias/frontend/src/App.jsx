import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Login from './pages/Login';
import Reportes from './pages/Reportes';
import Alertas from './pages/Alertas';

function App() {
  return (
    <Router>
      {/* Este es un menú de navegación temporal para que probemos */}
      <nav style={{ padding: '15px', backgroundColor: '#333', textAlign: 'center' }}>
        <Link to="/" style={{ color: 'white', margin: '0 15px', textDecoration: 'none' }}>Login</Link>
        <Link to="/reportes" style={{ color: 'white', margin: '0 15px', textDecoration: 'none' }}>Reportes</Link>
        <Link to="/alertas" style={{ color: 'white', margin: '0 15px', textDecoration: 'none' }}>Alertas</Link>
      </nav>

      {/* Aquí le decimos qué componente cargar en cada URL */}
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/reportes" element={<Reportes />} />
        <Route path="/alertas" element={<Alertas />} />
      </Routes>
    </Router>
  );
}

export default App;