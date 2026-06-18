import React, { useState } from 'react';
import { styled } from '@stitches/react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const LoginOverlay = styled('div', { display: 'flex', alignItems: 'center', justifyContent: 'center', height: '100vh', width: '100vw', backgroundColor: '#0a0b10', fontFamily: 'system-ui, -apple-system, sans-serif', position: 'relative' });
const SecurityCard = styled('div', { backgroundColor: 'rgba(20, 22, 33, 0.85)', backdropFilter: 'blur(12px)', padding: '48px 40px', borderRadius: '24px', boxShadow: '0 20px 50px rgba(0, 0, 0, 0.7), 0 0 1px 1px rgba(255, 56, 56, 0.2)', width: '100%', maxWidth: '400px', border: '1px solid rgba(255, 255, 255, 0.05)' });
const BrandHeader = styled('div', { textAlign: 'center', marginBottom: '36px' });
const BadgeStatus = styled('span', { backgroundColor: 'rgba(255, 56, 56, 0.1)', color: '#ff3838', padding: '6px 14px', borderRadius: '20px', fontSize: '11px', fontWeight: '700', letterSpacing: '1px', textTransform: 'uppercase', display: 'inline-block', marginBottom: '16px' });
const MainTitle = styled('h1', { color: '#ffffff', fontSize: '26px', fontWeight: '800', margin: 0, letterSpacing: '-0.5px' });
const SubTitle = styled('p', { color: '#6c7284', fontSize: '14px', margin: '8px 0 0 0' });
const FieldWrapper = styled('div', { marginBottom: '24px', display: 'flex', flexDirection: 'column' });
const FieldLabel = styled('label', { color: '#94a3b8', fontSize: '11px', fontWeight: '700', marginBottom: '8px', letterSpacing: '0.5px' });
const PremiumInput = styled('input', { padding: '14px 16px', borderRadius: '12px', border: '1px solid #23263b', backgroundColor: '#121420', color: '#ffffff', fontSize: '15px', '&:focus': { outline: 'none', borderColor: '#ff3838', backgroundColor: '#161929' } });
const CyberButton = styled('button', { width: '100%', padding: '16px', backgroundColor: '#ff3838', color: '#ffffff', border: 'none', borderRadius: '12px', fontWeight: '700', fontSize: '15px', cursor: 'pointer', transition: 'all 0.2s ease', '&:hover': { backgroundColor: '#e02828' } });

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8090/api/usuarios/login', { email, password });
      alert("Autenticación correcta. Inicializando pasarela de comando...");
      navigate('/reportes');
    } catch (error) {
      alert("Acceso denegado: Verifique sus credenciales.");
    }
  };

  return (
    <LoginOverlay>
      <SecurityCard>
        <BrandHeader>
          <BadgeStatus>Terminal Operativa V2</BadgeStatus>
          <MainTitle>Muni Valle del Sol</MainTitle>
          <SubTitle>Centro Integrado de Gestión de Incendios</SubTitle>
        </BrandHeader>
        
        <form onSubmit={handleLogin}>
          <FieldWrapper>
            <FieldLabel>OPERADOR CREDENCIAL (EMAIL)</FieldLabel>
            <PremiumInput type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="agente@valledelsol.cl" required />
          </FieldWrapper>
          <FieldWrapper>
            <FieldLabel>LLAVE DE ACCESO INTEGRADA</FieldLabel>
            <PremiumInput type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="••••••••••••" required />
          </FieldWrapper>
          <CyberButton type="submit">ACCEDER AL COMANDO</CyberButton>
        </form>
      </SecurityCard>
    </LoginOverlay>
  );
}