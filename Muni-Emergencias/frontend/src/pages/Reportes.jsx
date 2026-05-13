function Reportes() {
  return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif', maxWidth: '800px', margin: '0 auto' }}>
      <h2>📋 Listado de Reportes Ingresados</h2>
      <table border="1" style={{ width: '100%', textAlign: 'left', borderCollapse: 'collapse', marginTop: '20px' }}>
        <thead style={{ backgroundColor: '#f2f2f2' }}>
          <tr>
            <th style={{ padding: '10px' }}>ID</th>
            <th style={{ padding: '10px' }}>Tipo de Emergencia</th>
            <th style={{ padding: '10px' }}>Descripción</th>
            <th style={{ padding: '10px' }}>Estado</th>
          </tr>
        </thead>
        <tbody>
          {/* Fila de ejemplo simulada */}
          <tr>
            <td style={{ padding: '10px' }}>1</td>
            <td style={{ padding: '10px' }}>Incendio</td>
            <td style={{ padding: '10px' }}>Pastizales en el sector sur</td>
            <td style={{ padding: '10px', color: 'orange' }}>Pendiente</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default Reportes;