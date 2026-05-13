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
    
    // URL de tu API Gateway en puerto 8080
    const urlBase = "http://localhost:8080/api/usuarios"; 
    const endpoint = esRegistro ? `${urlBase}/registrar` : `${urlBase}/login`;

    try {
      const respuesta = await axios.post(endpoint, formData);
      
      console.log("Respuesta del servidor:", respuesta.data);
      alert(esRegistro ? "¡Cuenta creada con éxito! Ahora puedes iniciar sesión." : "¡Bienvenido al sistema!");
      
      if (esRegistro) {
        setEsRegistro(false); // Si se registró, lo pasamos al login
      } else {
        navigate('/reportes'); // Si se logueó, lo mandamos a los reportes
      }

    } catch (error) {
      console.error("Error en la conexión:", error);
      alert("Error: No se pudo conectar con el servidor. Revisa que el Microservicio y el Gateway estén encendidos.");
    }
  };

  // Diseño de la interfaz
  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2 style={styles.title}>{esRegistro ? 'Crear Cuenta Municipal' : 'Iniciar Sesión'}</h2>
        
        <form onSubmit={handleSubmit} style={styles.form}>
          {esRegistro && (
            <div>
              <label style={styles.label}>Nombre Completo:</label>
              <input 
                type="text" 
                name="nombre" 
                value={formData.nombre} 
                onChange={handleChange} 
                required 
                style={styles.input} 
                placeholder="Ej: Juan Pérez"
              />
            </div>
          )}
          
          <div>
            <label style={styles.label}>Correo Electrónico:</label>
            <input 
              type="email" 
              name="email" 
              value={formData.email} 
              onChange={handleChange} 
              required 
              style={styles.input} 
              placeholder="correo@municipalidad.cl"
            />
          </div>

          <div>
            <label style={styles.label}>Contraseña:</label>
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
            {esRegistro ? 'Registrarse' : 'Ingresar al Sistema'}
          </button>
        </form>

        <p style={styles.footerText}>
          {esRegistro ? '¿Ya tienes una cuenta?' : '¿Eres nuevo funcionario?'}
          <button 
            onClick={() => setEsRegistro(!esRegistro)} 
            style={styles.switchButton}
          >
            {esRegistro ? 'Inicia Sesión aquí' : 'Regístrate aquí'}
          </button>
        </p>
      </div>
    </div>
  );
}

// Estilos rápidos para que se vea profesional
const styles = {
  container: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '90vh',
    fontFamily: 'Segoe UI, Tahoma, Geneva, Verdana, sans-serif'
  },
  card: {
    padding: '40px',
    borderRadius: '12px',
    boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
    backgroundColor: '#fff',
    width: '100%',
    maxWidth: '400px'
  },
  title: {
    textAlign: 'center',
    color: '#333',
    marginBottom: '30px'
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '20px'
  },
  label: {
    fontSize: '14px',
    fontWeight: '600',
    color: '#555',
    display: 'block',
    marginBottom: '5px'
  },
  input: {
    width: '100%',
    padding: '12px',
    borderRadius: '6px',
    border: '1px solid #ddd',
    fontSize: '16px',
    boxSizing: 'border-box'
  },
  button: {
    padding: '12px',
    backgroundColor: '#007bff',
    color: 'white',
    border: 'none',
    borderRadius: '6px',
    fontSize: '16px',
    fontWeight: 'bold',
    cursor: 'pointer',
    marginTop: '10px',
    transition: 'background 0.3s'
  },
  footerText: {
    marginTop: '25px',
    textAlign: 'center',
    fontSize: '14px',
    color: '#666'
  },
  switchButton: {
    background: 'none',
    border: 'none',
    color: '#007bff',
    textDecoration: 'underline',
    cursor: 'pointer',
    fontWeight: '600',
    marginLeft: '5px'
  }
};

export default Login;