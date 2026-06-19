import React, { useState } from 'react';
import { styled } from '@stitches/react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Overlay = styled('div', {
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  minHeight: '100vh',
  width: '100vw',
  backgroundColor: '#121214', // Color base oscuro mate solicitado
  fontFamily: 'system-ui, -apple-system, sans-serif',
});

const Card = styled('div', {
  backgroundColor: '#1a1a1e', // Fondo de la tarjeta ligeramente más claro
  padding: '48px 40px',
  borderRadius: '24px',
  boxShadow: '0 12px 40px rgba(0, 0, 0, 0.6), 0 0 0 1px rgba(255, 255, 255, 0.05)',
  width: '100%',
  maxWidth: '420px',
  boxSizing: 'border-box',
});

const Header = styled('div', {
  textAlign: 'center',
  marginBottom: '32px',
});

const Title = styled('h1', {
  color: '#ffffff',
  fontSize: '26px',
  fontWeight: '800',
  margin: '0 0 8px 0',
  letterSpacing: '-0.5px'
});

const Subtitle = styled('p', {
  color: '#a1a1aa',
  fontSize: '14px',
  margin: 0,
});

const TabContainer = styled('div', {
  display: 'flex',
  marginBottom: '32px',
  backgroundColor: '#27272a',
  borderRadius: '12px',
  padding: '6px',
});

const Tab = styled('button', {
  flex: 1,
  padding: '12px 0',
  borderRadius: '8px',
  border: 'none',
  fontSize: '14px',
  fontWeight: '700',
  cursor: 'pointer',
  transition: 'all 0.2s',
  variants: {
    active: {
      true: {
        backgroundColor: '#3f3f46',
        color: '#ffffff',
        boxShadow: '0 2px 8px rgba(0,0,0,0.2)',
      },
      false: {
        backgroundColor: 'transparent',
        color: '#a1a1aa',
        '&:hover': {
          color: '#e4e4e7',
        },
      },
    },
  },
});

const Form = styled('form', {
  display: 'flex',
  flexDirection: 'column',
  gap: '16px',
});

const Input = styled('input', {
  width: '100%',
  padding: '14px 16px',
  borderRadius: '12px',
  border: '1px solid #3f3f46',
  backgroundColor: '#27272a',
  color: '#ffffff',
  fontSize: '14px',
  boxSizing: 'border-box',
  outline: 'none',
  transition: 'border-color 0.2s',
  '&:focus': {
    borderColor: '#ff3838',
    backgroundColor: '#303036'
  },
});

const SubmitButton = styled('button', {
  width: '100%',
  padding: '16px',
  backgroundColor: '#ff3838',
  color: '#ffffff',
  border: 'none',
  borderRadius: '12px',
  fontSize: '15px',
  fontWeight: '700',
  cursor: 'pointer',
  marginTop: '8px',
  transition: 'background-color 0.2s',
  '&:hover': {
    backgroundColor: '#e02828',
  },
  '&:disabled': {
    opacity: 0.7,
    cursor: 'not-allowed',
  }
});

export default function AuthPage() {
  const [activeTab, setActiveTab] = useState('login'); // 'login' o 'register'

  // Form states
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [nombre, setNombre] = useState('');
  const [rut, setRut] = useState('');
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await axios.post('http://localhost:8090/api/usuarios/login', { email, password });
      navigate('/reportes');
    } catch (error) {
      console.error("Error en Login:", error);
      alert(error.response?.data?.mensaje || "Acceso denegado: Verifique sus credenciales.");
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await axios.post('http://localhost:8090/api/usuarios/registrar', {
        nombre,
        rut,
        email,
        password,
        rol: "USER"
      });
      // Auto-login tras registro exitoso
      await axios.post('http://localhost:8090/api/usuarios/login', { email, password });
      navigate('/reportes');
    } catch (error) {
      console.error("Error en Registro:", error);
      alert(error.response?.data?.mensaje || "Error al registrar la cuenta.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Overlay>
      <Card>
        <Header>
          <Title>Muni Valle del Sol</Title>
          <Subtitle>Gestión y Reporte de Emergencias</Subtitle>
        </Header>

        <TabContainer>
          <Tab
            active={activeTab === 'login'}
            onClick={() => setActiveTab('login')}
            type="button"
          >
            Iniciar Sesión
          </Tab>
          <Tab
            active={activeTab === 'register'}
            onClick={() => setActiveTab('register')}
            type="button"
          >
            Registrarse
          </Tab>
        </TabContainer>

        {activeTab === 'login' ? (
          <Form onSubmit={handleLogin}>
            <Input
              type="email"
              placeholder="Correo electrónico"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
            <Input
              type="password"
              placeholder="Contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <SubmitButton type="submit" disabled={loading}>
              {loading ? 'Validando...' : 'Acceder al Comando'}
            </SubmitButton>
          </Form>
        ) : (
          <Form onSubmit={handleRegister}>
            <Input
              type="text"
              placeholder="Nombre Completo"
              value={nombre}
              onChange={(e) => setNombre(e.target.value)}
              required
            />
            <Input
              type="text"
              placeholder="RUT (Opcional)"
              value={rut}
              onChange={(e) => setRut(e.target.value)}
            />
            <Input
              type="email"
              placeholder="Correo electrónico"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
            <Input
              type="password"
              placeholder="Contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <SubmitButton type="submit" disabled={loading}>
              {loading ? 'Registrando...' : 'Crear Cuenta y Acceder'}
            </SubmitButton>
          </Form>
        )}
      </Card>
    </Overlay>
  );
}