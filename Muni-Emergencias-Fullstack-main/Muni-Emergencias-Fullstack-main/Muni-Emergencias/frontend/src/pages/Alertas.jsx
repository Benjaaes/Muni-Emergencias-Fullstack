import React from 'react';
import { useNavigate } from 'react-router-dom';

function Alertas() {
  const navigate = useNavigate();

  // Simulamos un arreglo de alertas para que se vea como un sistema real monitoreando
  const alertasActivas = [
    { 
      id: 1, 
      nivel: 'CRÍTICA', 
      titulo: '¡ALERTA ROJA - EVACUACIÓN!', 
      descripcion: 'Riesgo inminente de propagación de incendio forestal. Evacuación preventiva inmediata en el Sector Lomas.',
      tiempo: 'Hace 2 min',
      color: '#dc2626' // Rojo
    },
    { 
      id: 2, 
      nivel: 'ALTA', 
      titulo: 'CAMBIO METEOROLÓGICO', 
      descripcion: 'Vientos cruzados detectados a 45km/h. Alta probabilidad de cambio de dirección del frente de fuego.',
      tiempo: 'Hace 15 min',
      color: '#ea580c' // Naranja
    },
    { 
      id: 3, 
      nivel: 'MEDIA', 
      titulo: 'MONITOREO PREVENTIVO', 
      descripcion: 'Aumento de temperatura superficial anómalo en cuadrante B-4. Unidades de observación en ruta.',
      tiempo: 'Hace 45 min',
      color: '#d97706' // Ámbar
    }
  ];

  return (
    <div style={styles.appContainer}>
      
      {/* BARRA DE NAVEGACIÓN SUPERIOR */}
      <nav style={styles.navbar}>
        <div style={styles.navLeft}>
          <div style={styles.logoBadge}>🚨</div>
          <div>
            <h1 style={styles.navTitle}>CENTRAL DE EMERGENCIAS | CONCEPCIÓN</h1>
            <h2 style={styles.navSubtitle}>SISTEMA DE ALARMA TEMPRANA</h2>
          </div>
        </div>
        <div style={styles.navMenu}>
          <span style={styles.navLink} onClick={() => navigate('/login')}>CERRAR SESIÓN</span>
          <span style={styles.navLink} onClick={() => navigate('/reportes')}>REPORTES DE INCENDIO</span>
          <span style={{ ...styles.navLink, ...styles.navLinkActive }}>ALERTAS ACTIVAS</span>
        </div>
      </nav>

      <main style={styles.mainContent}>
        <div style={styles.headerContainer}>
          <div style={styles.headerLeft}>
            <span style={styles.pulseIndicator}></span>
            <h2 style={styles.mainTitle}>PANEL DE ALERTAS CRÍTICAS</h2>
          </div>
          <p style={styles.statusText}>TRANSMISIÓN EN VIVO 📡</p>
        </div>

        <div style={styles.grid}>
          {alertasActivas.map((alerta) => (
            <div key={alerta.id} style={{
              ...styles.alertCard, 
              borderLeft: `5px solid ${alerta.color}`,
              boxShadow: alerta.nivel === 'CRÍTICA' ? '0 0 20px rgba(220, 38, 38, 0.2)' : 'none'
            }}>
              
              <div style={styles.cardHeader}>
                <div style={styles.badgeContainer}>
                  <span style={{...styles.badge, backgroundColor: alerta.color}}>
                    NIVEL: {alerta.nivel}
                  </span>
                  <span style={styles.timeText}>{alerta.tiempo}</span>
                </div>
              </div>

              <h3 style={{...styles.alertTitle, color: alerta.color}}>
                {alerta.nivel === 'CRÍTICA' ? '⚠️ ' : 'ℹ️ '} {alerta.titulo}
              </h3>
              
              <p style={styles.alertDescription}>
                {alerta.descripcion}
              </p>
              
              {alerta.nivel === 'CRÍTICA' && (
                <button style={styles.actionBtn}>
                  VERIFICAR PROTOCOLO DE EVACUACIÓN
                </button>
              )}
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}

// ESTILOS: Modo "Centro de Control"
const styles = {
  appContainer: {
    backgroundColor: '#0a0a0a',
    backgroundImage: 'radial-gradient(#262626 1px, transparent 1px)',
    backgroundSize: '40px 40px',
    minHeight: '100vh',
    width: '100vw',
    color: '#e5e5e5',
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    margin: 0,
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
  
  mainContent: {
    maxWidth: '1000px',
    margin: '0 auto',
    padding: '40px 20px'
  },
  headerContainer: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '30px',
    borderBottom: '1px solid #333',
    paddingBottom: '15px'
  },
  headerLeft: {
    display: 'flex',
    alignItems: 'center',
    gap: '15px'
  },
  pulseIndicator: {
    width: '12px',
    height: '12px',
    backgroundColor: '#dc2626',
    borderRadius: '50%',
    boxShadow: '0 0 15px #dc2626',
    animation: 'pulse 1.5s infinite' // Nota: Requiere keyframes en CSS global para parpadear, pero se ve bien como punto estático rojo
  },
  mainTitle: {
    margin: 0,
    fontSize: '1.5rem',
    color: '#fff',
    letterSpacing: '1px'
  },
  statusText: {
    margin: 0,
    color: '#fbbf24',
    fontSize: '0.85rem',
    fontWeight: 'bold',
    letterSpacing: '2px'
  },
  grid: {
    display: 'flex',
    flexDirection: 'column',
    gap: '20px'
  },
  alertCard: {
    backgroundColor: '#171717',
    border: '1px solid #262626',
    borderRadius: '8px',
    padding: '25px',
    position: 'relative',
    overflow: 'hidden'
  },
  cardHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    marginBottom: '15px'
  },
  badgeContainer: {
    display: 'flex',
    justifyContent: 'space-between',
    width: '100%',
    alignItems: 'center'
  },
  badge: {
    padding: '5px 12px',
    borderRadius: '4px',
    color: '#fff',
    fontSize: '0.75rem',
    fontWeight: 'bold',
    letterSpacing: '1px'
  },
  timeText: {
    color: '#737373',
    fontSize: '0.8rem',
    fontWeight: '600'
  },
  alertTitle: {
    margin: '0 0 10px 0',
    fontSize: '1.3rem',
    fontWeight: '800',
    letterSpacing: '0.5px'
  },
  alertDescription: {
    margin: 0,
    color: '#d4d4d4',
    fontSize: '1rem',
    lineHeight: '1.6'
  },
  actionBtn: {
    marginTop: '20px',
    padding: '10px 20px',
    backgroundColor: 'transparent',
    color: '#dc2626',
    border: '1px solid #dc2626',
    borderRadius: '4px',
    fontWeight: 'bold',
    cursor: 'pointer',
    transition: 'all 0.3s',
  }
};

export default Alertas;