import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import ReportesPage from './pages/Reportes';
import Alertas from './pages/Alertas';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/reportes" element={<ReportesPage />} />
        <Route path="/alertas" element={<Alertas />} />
      </Routes>
    </Router>
  );
}

export default App;