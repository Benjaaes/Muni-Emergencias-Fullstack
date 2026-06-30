import React, { useEffect, useState } from 'react';
import { styled } from '@stitches/react';
import { useNavigate } from 'react-router-dom';
import { MapContainer, TileLayer, Marker, Popup, useMap, useMapEvents } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import axios from 'axios';

// Fix para los iconos del mapa
import markerIcon from 'leaflet/dist/images/marker-icon.png';
import markerShadow from 'leaflet/dist/images/marker-shadow.png';
L.Marker.prototype.options.icon = L.icon({
  iconUrl: markerIcon,
  shadowUrl: markerShadow,
  iconSize: [25, 41],
  iconAnchor: [12, 41]
});

// --- DISEÑO ESTRUCTURAL PREMIUM ---
const AppWrapper = styled('div', { display: 'flex', height: '100vh', width: '100vw', backgroundColor: '#090a0f', color: '#f8fafc', fontFamily: 'system-ui, sans-serif', overflow: 'hidden' });
const Sidebar = styled('nav', { width: '260px', backgroundColor: '#11131f', borderRight: '1px solid #1e2235', display: 'flex', flexDirection: 'column', padding: '24px' });
const SidebarBranding = styled('div', { fontSize: '16px', fontWeight: '800', color: '#ff3838', letterSpacing: '1px', marginBottom: '40px', display: 'flex', alignItems: 'center', gap: '8px' });
const NavItem = styled('button', { background: 'none', border: 'none', color: '#64748b', padding: '14px 18px', textAlign: 'left', fontSize: '14px', fontWeight: '600', borderRadius: '12px', cursor: 'pointer', marginBottom: '6px', transition: 'all 0.2s', '&:hover': { color: '#ffffff', backgroundColor: '#181b2d' }, variants: { active: { true: { backgroundColor: 'rgba(255, 56, 56, 0.1)', color: '#ff3838', '&:hover': { backgroundColor: 'rgba(255, 56, 56, 0.15)', color: '#ff3838' } } } } });
const ContentArea = styled('main', { flex: 1, padding: '32px', overflowY: 'auto', display: 'flex', flexDirection: 'column', position: 'relative' });
const HeaderSection = styled('header', { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '28px', backgroundColor: '#ff3838', padding: '20px', borderRadius: '16px' });
const MainLayoutGrid = styled('div', { display: 'grid', gridTemplateColumns: '1fr 1.6fr', gap: '28px', flex: 1, minHeight: '400px' });
const VisualPanel = styled('div', { backgroundColor: '#11131f', border: '1px solid #1e2235', borderRadius: '20px', padding: '24px', display: 'flex', flexDirection: 'column' });
const FeedItem = styled('div', { padding: '16px', backgroundColor: '#161926', borderRadius: '12px', marginBottom: '12px', borderLeft: '4px solid #ff3838' });

const ActionBtn = styled('button', { backgroundColor: '#ffffff', color: '#ff3838', border: 'none', padding: '12px 20px', borderRadius: '8px', fontWeight: 'bold', cursor: 'pointer', transition: '0.2s', '&:hover': { transform: 'scale(1.05)' } });

// --- ESTILOS DEL MODAL (VENTANA EMERGENTE) ---
const ModalOverlay = styled('div', { position: 'absolute', top: 0, left: 0, right: 0, bottom: 0, backgroundColor: 'rgba(0, 0, 0, 0.8)', backdropFilter: 'blur(4px)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 });
const ModalContent = styled('div', { backgroundColor: '#11131f', padding: '30px', borderRadius: '16px', border: '1px solid #1e2235', width: '400px', boxShadow: '0 10px 30px rgba(0,0,0,0.5)' });
const InputField = styled('input', { width: '90%', padding: '12px', marginBottom: '16px', borderRadius: '8px', border: '1px solid #2e3044', backgroundColor: '#1b1c28', color: '#fff', outline: 'none' });
const SelectField = styled('select', { width: '100%', padding: '12px', marginBottom: '20px', borderRadius: '8px', border: '1px solid #2e3044', backgroundColor: '#1b1c28', color: '#fff', outline: 'none' });

function AutoCenterMap({ coords }) {
  const map = useMap();
  useEffect(() => { map.setView(coords, map.getZoom()); }, [coords, map]);
  return null;
}

// Componente para manejar la interactividad del mapa
function LocationSelector({ coords, setCoords }) {
  useMapEvents({
    click(e) {
      setCoords([e.latlng.lat, e.latlng.lng]);
    },
  });

  return (
    <Marker 
      position={coords} 
      draggable={true} 
      eventHandlers={{
        dragend: (e) => {
          setCoords([e.target.getLatLng().lat, e.target.getLatLng().lng]);
        }
      }}
    >
      <Popup>📍 <strong>Ubicación del Incidente</strong><br/>Coordenadas: {coords[0].toFixed(4)}, {coords[1].toFixed(4)}</Popup>
    </Marker>
  );
}

export default function ReportesPage() {
  const [coordenadasReporte, setCoordenadasReporte] = useState([-36.8261, -73.0498]); 
  const navigate = useNavigate();

  // --- ESTADO PARA LOS REPORTES Y EL MODAL ---
  const [showModal, setShowModal] = useState(false);
  const [nuevoTitulo, setNuevoTitulo] = useState('');
  const [nuevaPrioridad, setNuevaPrioridad] = useState('CRÍTICO');
  
  const [incidentes, setIncidentes] = useState([]);

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((p) => setCoordenadasReporte([p.coords.latitude, p.coords.longitude]));
    }
    fetchReportes();
  }, []);

  const fetchReportes = async () => {
    try {
      const response = await axios.get('http://localhost:8090/api/reportes');
      const data = response.data.map(rep => ({
        id: rep.id,
        titulo: rep.tipoEmergencia,
        desc: rep.descripcion,
        prioridad: rep.estado,
        color: rep.estado === 'CRÍTICO' ? '#ff3838' : '#ff9f43'
      })).reverse(); // Mostrar últimos primero
      setIncidentes(data);
    } catch (error) {
      console.error("Error fetching reportes", error);
    }
  };

  // Función para guardar el nuevo reporte
  const handleCrearReporte = async (e) => {
    e.preventDefault();
    const payload = {
      tipoEmergencia: nuevoTitulo,
      descripcion: `Ubicación reportada: [${coordenadasReporte[0].toFixed(4)}, ${coordenadasReporte[1].toFixed(4)}]. Ingresado en tiempo real.`,
      estado: nuevaPrioridad
    };
    
    try {
      await axios.post('http://localhost:8090/api/reportes', payload);
      // Limpiamos y cerramos
      setNuevoTitulo('');
      setShowModal(false);
      fetchReportes(); // Recargar la lista
    } catch (error) {
      console.error("Error creating reporte", error);
      alert("Error al crear el reporte.");
    }
  };

  return (
    <AppWrapper>
      <Sidebar>
        <SidebarBranding>🚨 MUNI VALLE DEL SOL</SidebarBranding>
        <NavItem active={true}>Centro de Reportes</NavItem>
        <NavItem onClick={() => navigate('/alertas')}>Consola de Alertas</NavItem>
        <NavItem onClick={() => navigate('/')}>Cerrar Sesión</NavItem>
      </Sidebar>

      <ContentArea>
        {/* --- VENTANA EMERGENTE (MODAL) --- */}
        {showModal && (
          <ModalOverlay>
            <ModalContent>
              <h3 style={{ marginTop: 0 }}>Crear Nuevo Reporte</h3>
              <form onSubmit={handleCrearReporte}>
                <label style={{ fontSize: '12px', color: '#94a3b8' }}>TIPO DE INCIDENTE</label>
                <InputField 
                  type="text" 
                  placeholder="Ej: Incendio estructural..." 
                  value={nuevoTitulo} 
                  onChange={(e) => setNuevoTitulo(e.target.value)} 
                  required 
                />
                
                <label style={{ fontSize: '12px', color: '#94a3b8' }}>NIVEL DE PRIORIDAD</label>
                <SelectField value={nuevaPrioridad} onChange={(e) => setNuevaPrioridad(e.target.value)}>
                  <option value="CRÍTICO">CRÍTICO (Alerta Roja)</option>
                  <option value="MEDIO">MEDIO (Alerta Amarilla)</option>
                </SelectField>

                {/* Mostrar coordenadas seleccionadas en el Modal */}
                <p style={{ fontSize: '12px', color: '#94a3b8', marginBottom: '20px' }}>
                  📌 <strong>Ubicación seleccionada:</strong> {coordenadasReporte[0].toFixed(4)}, {coordenadasReporte[1].toFixed(4)}
                </p>

                <div style={{ display: 'flex', gap: '10px' }}>
                  <ActionBtn type="button" style={{ backgroundColor: '#2e3044', color: 'white', flex: 1 }} onClick={() => setShowModal(false)}>Cancelar</ActionBtn>
                  <ActionBtn type="submit" style={{ backgroundColor: '#ff3838', color: 'white', flex: 1 }}>Publicar</ActionBtn>
                </div>
              </form>
            </ModalContent>
          </ModalOverlay>
        )}

        <HeaderSection>
          <div>
            <h2 style={{ margin: 0, fontSize: '24px', fontWeight: '800' }}>¿Detectaste un nuevo foco de incendio?</h2>
            <p style={{ margin: '4px 0 0 0', color: '#f8fafc', fontSize: '14px', opacity: 0.9 }}>Ajusta la posición en el mapa y crea un reporte de inmediato.</p>
          </div>
          <ActionBtn onClick={() => setShowModal(true)}>+ Crear Reporte Urgente</ActionBtn>
        </HeaderSection>

        <MainLayoutGrid>
          <VisualPanel>
            <h3 style={{ margin: '0 0 20px 0', fontSize: '16px' }}>Actividad Reciente</h3>
            <div style={{ overflowY: 'auto', flex: 1 }}>
              
              {/* Renderizamos la lista dinámica de incidentes */}
              {incidentes.map((inc) => (
                <FeedItem key={inc.id} style={{ borderLeftColor: inc.color }}>
                  <span style={{ color: inc.color, fontSize: '11px', fontWeight: '800' }}>{inc.prioridad}</span>
                  <h4 style={{ margin: '4px 0', fontSize: '14px' }}>{inc.titulo}</h4>
                  <p style={{ margin: 0, fontSize: '12px', color: '#94a3b8' }}>{inc.desc}</p>
                </FeedItem>
              ))}

            </div>
          </VisualPanel>

          <VisualPanel style={{ padding: '12px', position: 'relative' }}>
            {/* Instrucción Flotante UX/UI */}
            <div style={{ position: 'absolute', top: '24px', left: '60px', zIndex: 1000, backgroundColor: 'rgba(17, 19, 31, 0.85)', padding: '10px 16px', borderRadius: '8px', border: '1px solid #1e2235', fontSize: '12px', color: '#f8fafc', backdropFilter: 'blur(4px)', pointerEvents: 'none', boxShadow: '0 4px 12px rgba(0,0,0,0.3)' }}>
              ℹ️ Arrastra el pin azul o haz clic en el mapa para ajustar la ubicación exacta del reporte.
            </div>
            
            <div style={{ width: '100%', height: '100%', borderRadius: '14px', overflow: 'hidden' }}>
              <MapContainer center={coordenadasReporte} zoom={15} style={{ width: '100%', height: '100%' }}>
                <TileLayer attribution='&copy; OpenStreetMap' url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
                <LocationSelector coords={coordenadasReporte} setCoords={setCoordenadasReporte} />
                <AutoCenterMap coords={coordenadasReporte} />
              </MapContainer>
            </div>
          </VisualPanel>
        </MainLayoutGrid>
      </ContentArea>
    </AppWrapper>
  );
}