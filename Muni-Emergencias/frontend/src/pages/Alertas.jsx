function Alertas() {
  return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif', maxWidth: '800px', margin: '0 auto' }}>
      <h2>🚨 Panel de Alertas Activas</h2>
      
      {/* Alerta de ejemplo simulada */}
      <div style={{ 
        padding: '15px', 
        backgroundColor: '#ffcccc', 
        border: '2px solid red', 
        borderRadius: '8px',
        marginTop: '20px'
      }}>
        <h3 style={{ margin: '0 0 10px 0', color: '#990000' }}>¡ALERTA ROJA!</h3>
        <p style={{ margin: 0 }}>Riesgo de propagación de incendio forestal. Evacuación preventiva en el sector.</p>
      </div>
    </div>
  );
}

export default Alertas;