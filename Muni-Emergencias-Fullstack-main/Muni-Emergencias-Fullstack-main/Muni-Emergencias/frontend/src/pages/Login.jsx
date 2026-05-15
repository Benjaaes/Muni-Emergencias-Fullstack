import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Login() {
  // Estados para controlar el formulario y la vista
  const [esRegistro, setEsRegistro] = useState(false);
  const [formData, setFormData] = useState({ 
    nombre: '', 
    email: '', 
    password: '' 
  });
  
  const navigate = useNavigate();

  // Función para capturar lo que el usuario escribe
  const handleChange = (e) => {
    setFormData({ 
      ...formData, 
      [e.target.name]: e.target.value 
    });
  };

  // Función para enviar los datos al Backend (API Gateway)
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // URL de tu API Gateway en puerto 8090
    const urlBase = "http://localhost:8090/api/usuarios";
    const endpoint = esRegistro ? `${urlBase}/registrar` : `${urlBase}/login`;

    try {
      const respuesta = await axios.post(endpoint, formData);
      
      console.log("Respuesta del servidor:", respuesta.data);
      
      if (esRegistro) {
        alert("✔️ Credenciales autorizadas. Cuenta creada con éxito.");
        setEsRegistro(false); // Si se registró, lo pasamos al login
      } else {
        navigate('/reportes'); // Si se logueó, lo mandamos a los reportes
      }

    } catch (error) {
      console.error("Error en la conexión:", error);
      alert("⚠️ ALERTA DE SISTEMA: No se pudo conectar con la Central. Verifica el Gateway (8090).");
    }
  };

  // Diseño de la interfaz Táctica / Dark Mode
  return (
    <div style={styles.appContainer}>
      <div style={styles.card}>
        
        {/* ENCABEZADO DEL LOGIN */}
        <div style={styles.header}>
          <div style={styles.logoBadge}>🔥</div>
          <h2 style={styles.title}>SISTEMA DE EMERGENCIAS</h2>
          <p style={styles.subtitle}>{esRegistro ? 'REGISTRO DE PERSONAL AUTORIZADO' : 'ACCESO RESTRINGIDO - CONCEPCIÓN'}</p>
        </div>
        
        <form onSubmit={handleSubmit} style={styles.form}>
          {esRegistro && (
            <div>
              <label style={styles.label}>IDENTIFICACIÓN DEL AGENTE</label>
              <input 
                type="text" 
                name="nombre" 
                value={formData.nombre} 
                onChange={handleChange} 
                required 
                style={styles.input} 
                placeholder="Ej: Cmdte. Juan Pérez"
              />
            </div>
          )}
          
          <div>
            <label style={styles.label}>CORREO INSTITUCIONAL</label>
            <input 
              type="email" 
              name="email" 
              value={formData.email} 
              onChange={handleChange} 
              required 
              style={styles.input} 
              placeholder="agente@concepcion.cl"
            />
          </div>

          <div>
            <label style={styles.label}>CLAVE DE SEGURIDAD</label>
            <input 
              type="password" 
              name="password" 
              value={formData.password} 
              onChange={handleChange} 
              required 
              style={styles.input} 
              placeholder="••••••••"
            />
          </div>

          <button type="submit" style={styles.button}>
            {esRegistro ? 'SOLICITAR ALTA EN EL SISTEMA' : 'INICIAR ENLACE SEGURO'}
          </button>
        </form>

        <div style={styles.footer}>
          <p style={styles.footerText}>
            {esRegistro ? '¿Ya posees credenciales activas?' : '¿Personal nuevo sin acceso?'}
          </p>
          <button 
            type="button"
            onClick={() => setEsRegistro(!esRegistro)} 
            style={styles.switchButton}
          >
            {esRegistro ? '← VOLVER AL INGRESO' : 'SOLICITAR CREDENCIALES'}
          </button>
        </div>
        
      </div>
    </div>
  );
}

// ESTILOS: Modo "Centro de Control"
const styles = {
  appContainer: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '100vh',
    width: '100vw',
    backgroundColor: '#0a0a0a',
    backgroundImage: 'radial-gradient(#333 1px, transparent 1px)',
    backgroundSize: '30px 30px',
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    margin: 0,
    boxSizing: 'border-box'
  },
  card: {
    padding: '40px 50px',
    borderRadius: '12px',
    backgroundColor: '#171717',
    border: '1px solid #dc2626', // Borde rojo de alerta
    boxShadow: '0 0 30px rgba(220, 38, 38, 0.15)', // Resplandor rojo
    width: '100%',
    maxWidth: '450px',
    boxSizing: 'border-box'
  },
  header: {
    textAlign: 'center',
    marginBottom: '35px',
    borderBottom: '1px solid #262626',
    paddingBottom: '20px'
  },
  logoBadge: {
    fontSize: '3rem',
    filter: 'drop-shadow(0 0 10px red)',
    marginBottom: '10px'
  },
  title: {
    color: '#fff',
    fontSize: '1.4rem',
    fontWeight: '800',
    margin: '0 0 5px 0',
    letterSpacing: '1px'
  },
  subtitle: {
    color: '#ef4444', // Rojo claro
    fontSize: '0.75rem',
    fontWeight: 'bold',
    letterSpacing: '2px',
    margin: 0
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '20px'
  },
  label: {
    fontSize: '0.75rem',
    fontWeight: 'bold',
    color: '#a3a3a3',
    display: 'block',
    marginBottom: '8px',
    letterSpacing: '1px'
  },
  input: {
    width: '100%',
    padding: '14px 15px',
    borderRadius: '6px',
    border: '1px solid #333',
    backgroundColor: '#111',
    color: '#fff',
    fontSize: '1rem',
    boxSizing: 'border-box',
    outline: 'none',
    transition: 'border 0.3s'
  },
  button: {
    padding: '15px',
    background: 'linear-gradient(90deg, #dc2626 0%, #ea580c 100%)', // Gradiente rojo/naranja
    color: 'white',
    border: 'none',
    borderRadius: '6px',
    fontSize: '0.9rem',
    fontWeight: '800',
    letterSpacing: '1px',
    cursor: 'pointer',
    marginTop: '15px',
    transition: 'transform 0.2s, box-shadow 0.2s',
    boxShadow: '0 4px 15px rgba(220, 38, 38, 0.4)'
  },
  footer: {
    marginTop: '30px',
    textAlign: 'center',
    borderTop: '1px solid #262626',
    paddingTop: '20px'
  },
  footerText: {
    fontSize: '0.85rem',
    color: '#737373',
    margin: '0 0 10px 0'
  },
  switchButton: {
    background: 'none',
    border: 'none',
    color: '#fbbf24', // Amarillo alerta
    cursor: 'pointer',
    fontWeight: 'bold',
    fontSize: '0.85rem',
    letterSpacing: '1px',
    transition: 'color 0.3s'
  }
};

export default Login;