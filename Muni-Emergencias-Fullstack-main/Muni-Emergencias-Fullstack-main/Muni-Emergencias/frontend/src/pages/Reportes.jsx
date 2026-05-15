import React, { useState, useEffect } from 'react';

const ReportesPage = () => {
  const [reportes, setReportes] = useState([]);
  const [descripcion, setDescripcion] = useState('');
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState(false);

  const API_URL = "http://localhost:8090/api/reportes";

  const cargarReportes = async () => {
    try {
      const response = await fetch(API_URL);
      if (!response.ok) throw new Error();
      const data = await response.json();
      setReportes(data);
      setError(false);
    } catch (err) {
      setError(true);
    }
  };

  useEffect(() => { cargarReportes(); }, []);

  const enviarReporte = async (e) => {
    e.preventDefault();
    setCargando(true);
    try {
      const res = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          tipoEmergencia: 'Incendio Forestal',
          descripcion: descripcion,
          estado: 'ACTIVO' // Guardamos como ACTIVO por defecto
        })
      });
      if (res.ok) {
        setDescripcion('');
        cargarReportes();
      }
    } catch (err) {
      alert("Error de conexión con la Central.");
    } finally {
      setCargando(false);
    }
  };

  // Función para dar color al estado
  const getStatusStyle = (estado) => {
    const baseStyle = { padding: '4px 12px', borderRadius: '20px', fontSize: '0.75rem', fontWeight: 'bold', display: 'inline-block' };
    if (estado === 'CONTROLADO') return { ...baseStyle, backgroundColor: '#064e3b', color: '#34d399', border: '1px solid #059669' };
    if (estado === 'CRÍTICO') return { ...baseStyle, backgroundColor: '#7f1d1d', color: '#fca5a5', border: '1px solid #dc2626' };
    return { ...baseStyle, backgroundColor: '#78350f', color: '#fbbf24', border: '1px solid #d97706' }; // ACTIVO / Pendiente
  };

  return (
    <div style={styles.appContainer}>
      {/* BARRA DE NAVEGACIÓN SUPERIOR */}
      <nav style={styles.navbar}>
        <div style={styles.navLeft}>
          <div style={styles.logoBadge}>🔥</div>
          <div>
            <h1 style={styles.navTitle}>CENTRAL DE EMERGENCIAS | CONCEPCIÓN</h1>
            <h2 style={styles.navSubtitle}>UNIDAD DE CONTROL DE INCENDIOS</h2>
          </div>
        </div>
        <div style={styles.navMenu}>
          <span style={styles.navLink}>INICIAR SESIÓN</span>
          <span style={{ ...styles.navLink, ...styles.navLinkActive }}>REPORTES DE INCENDIO</span>
          <span style={styles.navLink}>ALERTAS ACTIVAS</span>
        </div>
      </nav>

      {/* CONTENIDO PRINCIPAL */}
      <main style={styles.mainContent}>
        
        {/* SECCIÓN SUPERIOR: FORMULARIO Y MAPA */}
        <div style={styles.topGrid}>
          
          {/* PANEL IZQUIERDO: FORMULARIO */}
          <section style={styles.panel}>
            <h2 style={styles.panelHeader}>NUEVO REPORTE DE INCENDIO</h2>
            <div style={styles.panelBody}>
              <h3 style={styles.formTitle}>📝 REGISTRAR FOCO DE INCENDIO</h3>
              
              <form onSubmit={enviarReporte}>
                <div style={styles.inputGroup}>
                  <label style={styles.label}>ZONA DE INCIDENTE</label>
                  <div style={styles.fakeSelect}>
                    <span>Incendio Forestal</span>
                    <span style={{color: '#666'}}>🔒 Fijo</span>
                  </div>
                </div>

                <div style={styles.inputGroup}>
                  <label style={styles.label}>DESCRIPCIÓN DETALLADA DEL FOCO <span style={styles.labelMuted}>(Ej: Ladera norte, vientos cruzados)</span></label>
                  <textarea 
                    style={styles.textarea}
                    value={descripcion}
                    onChange={(e) => setDescripcion(e.target.value)}
                    required
                  />
                </div>

                <button type="submit" style={cargando ? styles.btnDisabled : styles.btnSubmit} disabled={cargando}>
                  {cargando ? 'ENVIANDO...' : '🚒 DESPACHAR ALERTA DE INCENDIO'}
                </button>
              </form>
            </div>
          </section>

          {/* PANEL DERECHO: SIMULACIÓN DE MAPA */}
          <section style={styles.mapPanel}>
            <div style={styles.mapOverlay}>
              <span style={styles.mapPulse}></span>
              <p style={styles.mapText}>RADAR DE ZONAS CALIENTES - ACTIVO</p>
            </div>
          </section>
        </div>

        {/* SECCIÓN INFERIOR: TABLA DE DATOS */}
        <section style={styles.bottomPanel}>
          <div style={styles.tableHeaderContainer}>
             <h2 style={styles.panelHeader}>ÚLTIMOS REPORTES ACTIVOS (FORESTALES)</h2>
             {error && <span style={styles.errorBadge}>Sin conexión al Gateway (8090)</span>}
          </div>
          
          <div style={styles.tableWrapper}>
            <table style={styles.table}>
              <thead>
                <tr>
                  <th style={styles.th}>ID</th>
                  <th style={styles.th}>ZONA</th>
                  <th style={styles.th}>DESCRIPCIÓN BREVE</th>
                  <th style={styles.th}>ESTADO</th>
                  <th style={styles.th}>ACCIONES</th>
                </tr>
              </thead>
              <tbody>
                {reportes.length === 0 ? (
                  <tr><td colSpan="5" style={styles.emptyRow}>No hay reportes activos en este momento.</td></tr>
                ) : (
                  reportes.map((r, index) => (
                    <tr key={r.id} style={index % 2 === 0 ? styles.trEven : styles.trOdd}>
                      <td style={styles.td}>#FR-{r.id}</td>
                      <td style={styles.td}>{r.tipoEmergencia}</td>
                      <td style={styles.td}>{r.descripcion}</td>
                      <td style={styles.td}>
                        <span style={getStatusStyle(r.estado)}>
                          {r.estado === 'CONTROLADO' ? '✓ ' : '⚠️ '} {r.estado}
                        </span>
                      </td>
                      <td style={styles.tdActions}>
                        <button style={styles.iconBtn}>👁️</button>
                        <button style={styles.iconBtn}>✏️</button>
                        <button style={styles.iconBtnDanger}>⚠️</button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </section>

      </main>
    </div>
  );
};

// --- ESTILOS CSS-IN-JS (Misma apariencia que tu imagen) ---
const styles = {
  appContainer: {
    backgroundColor: '#0a0a0a',
    minHeight: '100vh',
    width: '100vw',
    color: '#e5e5e5',
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    overflowX: 'hidden',
    boxSizing: 'border-box'
  },
  navbar: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: '#171717',
    padding: '0 40px',
    height: '70px',
    borderBottom: '1px solid #262626'
  },
  navLeft: { display: 'flex', alignItems: 'center', gap: '15px' },
  logoBadge: { fontSize: '2rem', filter: 'drop-shadow(0 0 5px red)' },
  navTitle: { margin: 0, fontSize: '1rem', fontWeight: 'bold', color: '#fff', letterSpacing: '1px' },
  navSubtitle: { margin: 0, fontSize: '0.75rem', color: '#a3a3a3', letterSpacing: '2px' },
  navMenu: { display: 'flex', gap: '30px', height: '100%' },
  navLink: { fontSize: '0.85rem', fontWeight: '600', color: '#a3a3a3', cursor: 'pointer', display: 'flex', alignItems: 'center', transition: 'color 0.3s' },
  navLinkActive: { color: '#fff', borderBottom: '3px solid #dc2626' },
  
  mainContent: { padding: '30px 40px', display: 'flex', flexDirection: 'column', gap: '30px' },
  
  topGrid: { display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '30px', minHeight: '400px' },
  
  panel: { backgroundColor: '#171717', borderRadius: '8px', border: '1px solid #262626', display: 'flex', flexDirection: 'column', overflow: 'hidden' },
  panelHeader: { margin: 0, padding: '15px 20px', fontSize: '1rem', color: '#fff', backgroundColor: '#1f1f1f', borderBottom: '1px solid #262626', fontWeight: '600' },
  panelBody: { padding: '25px', flex: 1 },
  formTitle: { margin: '0 0 20px 0', fontSize: '0.9rem', color: '#fbbf24', borderBottom: '1px solid #333', paddingBottom: '10px' },
  
  inputGroup: { marginBottom: '20px' },
  label: { display: 'block', fontSize: '0.75rem', color: '#d4d4d4', marginBottom: '8px', fontWeight: 'bold' },
  labelMuted: { color: '#737373', fontWeight: 'normal' },
  fakeSelect: { backgroundColor: '#111', border: '1px solid #333', padding: '12px 15px', borderRadius: '4px', color: '#fff', fontSize: '0.9rem', display: 'flex', justifyContent: 'space-between' },
  textarea: { width: '100%', boxSizing: 'border-box', backgroundColor: '#111', border: '1px solid #fbbf24', padding: '12px 15px', borderRadius: '4px', color: '#fff', fontSize: '0.9rem', minHeight: '100px', resize: 'none', outline: 'none', boxShadow: '0 0 5px rgba(251, 191, 36, 0.2)' },
  
  btnSubmit: { width: '100%', padding: '15px', background: 'linear-gradient(90deg, #dc2626 0%, #ea580c 100%)', color: '#fff', border: 'none', borderRadius: '6px', fontSize: '1rem', fontWeight: 'bold', cursor: 'pointer', transition: 'transform 0.2s', boxShadow: '0 4px 15px rgba(220, 38, 38, 0.4)' },
  btnDisabled: { width: '100%', padding: '15px', backgroundColor: '#451a03', color: '#888', border: 'none', borderRadius: '6px', fontSize: '1rem', fontWeight: 'bold' },
  
  mapPanel: { backgroundColor: '#111', borderRadius: '8px', border: '1px solid #262626', backgroundImage: 'radial-gradient(#333 1px, transparent 1px)', backgroundSize: '20px 20px', position: 'relative', display: 'flex', justifyContent: 'center', alignItems: 'center' },
  mapOverlay: { textAlign: 'center' },
  mapPulse: { display: 'inline-block', width: '15px', height: '15px', backgroundColor: '#ef4444', borderRadius: '50%', boxShadow: '0 0 20px 10px rgba(239, 68, 68, 0.5)', marginBottom: '10px' },
  mapText: { color: '#ef4444', fontSize: '0.8rem', fontWeight: 'bold', letterSpacing: '2px' },

  bottomPanel: { backgroundColor: '#171717', borderRadius: '8px', border: '1px solid #262626', overflow: 'hidden' },
  tableHeaderContainer: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', backgroundColor: '#1f1f1f', borderBottom: '1px solid #262626', paddingRight: '20px' },
  errorBadge: { backgroundColor: '#7f1d1d', color: '#fca5a5', padding: '5px 10px', borderRadius: '4px', fontSize: '0.8rem', fontWeight: 'bold' },
  
  tableWrapper: { overflowX: 'auto', padding: '10px' },
  table: { width: '100%', borderCollapse: 'collapse', textAlign: 'left' },
  th: { padding: '15px', fontSize: '0.75rem', color: '#a3a3a3', fontWeight: 'bold', borderBottom: '2px solid #262626' },
  trEven: { backgroundColor: '#171717' },
  trOdd: { backgroundColor: '#1a1a1a' },
  td: { padding: '15px', fontSize: '0.85rem', color: '#d4d4d4', borderBottom: '1px solid #262626' },
  emptyRow: { textAlign: 'center', padding: '30px', color: '#737373' },
  
  tdActions: { padding: '15px', borderBottom: '1px solid #262626', display: 'flex', gap: '10px' },
  iconBtn: { background: 'none', border: 'none', cursor: 'pointer', fontSize: '1.2rem', filter: 'grayscale(100%)', opacity: 0.6, transition: '0.2s' },
  iconBtnDanger: { background: 'none', border: 'none', cursor: 'pointer', fontSize: '1.2rem', filter: 'drop-shadow(0 0 2px red)' }
};

export default ReportesPage;