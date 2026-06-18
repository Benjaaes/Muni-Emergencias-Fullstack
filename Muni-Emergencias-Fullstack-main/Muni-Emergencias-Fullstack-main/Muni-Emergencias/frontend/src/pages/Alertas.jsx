import React from 'react';
import { styled } from '@stitches/react';
import { useNavigate } from 'react-router-dom';

const MainWrapper = styled('div', { display: 'flex', height: '100vh', width: '100vw', backgroundColor: '#090a0f', color: '#f8fafc', fontFamily: 'system-ui, sans-serif', overflow: 'hidden' });
const Sidebar = styled('nav', { width: '260px', backgroundColor: '#11131f', borderRight: '1px solid #1e2235', display: 'flex', flexDirection: 'column', padding: '24px' });
const SidebarBranding = styled('div', { fontSize: '16px', fontWeight: '800', color: '#ff3838', letterSpacing: '1px', marginBottom: '40px', display: 'flex', alignItems: 'center', gap: '8px' });
const NavItem = styled('button', { background: 'none', border: 'none', color: '#64748b', padding: '14px 18px', textAlign: 'left', fontSize: '14px', fontWeight: '600', borderRadius: '12px', cursor: 'pointer', marginBottom: '6px', transition: 'all 0.2s', '&:hover': { color: '#ffffff', backgroundColor: '#181b2d' }, variants: { active: { true: { backgroundColor: 'rgba(255, 56, 56, 0.1)', color: '#ff3838' } } } });
const ContentBody = styled('main', { flex: 1, padding: '32px', overflowY: 'auto' });
const AlertTable = styled('table', { width: '100%', borderCollapse: 'collapse', backgroundColor: '#11131f', borderRadius: '16px', overflow: 'hidden', border: '1px solid #1e2235' });
const Th = styled('th', { padding: '16px 20px', textAlign: 'left', backgroundColor: '#161926', color: '#94a3b8', fontSize: '12px', fontWeight: '700', borderBottom: '1px solid #1e2235' });
const Td = styled('td', { padding: '18px 20px', fontSize: '14px', borderBottom: '1px solid #1e2235', color: '#cbd5e1' });
const Badget = styled('span', { padding: '6px 12px', borderRadius: '8px', fontSize: '11px', fontWeight: '700', variants: { level: { critico: { backgroundColor: 'rgba(255, 56, 56, 0.1)', color: '#ff3838' }, alto: { backgroundColor: 'rgba(255, 159, 67, 0.1)', color: '#ff9f43' } } } });
const ControlBtn = styled('button', { padding: '8px 14px', backgroundColor: '#1e2235', color: '#ffffff', border: 'none', borderRadius: '8px', fontSize: '12px', fontWeight: '600', cursor: 'pointer', marginLeft: '8px', '&:hover': { backgroundColor: '#ff3838' } });

export default function AlertasPage() {
  const navigate = useNavigate();

  return (
    <MainWrapper>
      <Sidebar>
        <SidebarBranding>🚨 MUNI VALLE DEL SOL</SidebarBranding>
        <NavItem onClick={() => navigate('/reportes')}>Centro de Reportes</NavItem>
        <NavItem active={true}>Consola de Alertas</NavItem>
        <NavItem onClick={() => navigate('/')}>Cerrar Sesión</NavItem>
      </Sidebar>

      <ContentBody>
        <header style={{ marginBottom: '32px' }}>
          <h2 style={{ margin: 0, fontSize: '24px', fontWeight: '800' }}>Control General de Alertas</h2>
          <p style={{ margin: '4px 0 0 0', color: '#64748b', fontSize: '14px' }}>Mesa de gestión y despacho institucional.</p>
        </header>

        <AlertTable>
          <thead>
            <tr>
              <Th>ID</Th>
              <Th>TIPO DE ALERTA</Th>
              <Th>SECTOR / COMUNA</Th>
              <Th>PRIORIDAD</Th>
              <Th>ESTADO</Th>
              <Th>ACCIONES DE COMANDO</Th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <Td style={{ fontWeight: '700', color: '#ff3838' }}>#082</Td>
              <Td style={{ fontWeight: '600' }}>Foco de Incendio Forestal</Td>
              <Td>Cerro Caracol</Td>
              <Td><Badget level="critico">CRÍTICO</Badget></Td>
              <Td><span style={{ color: '#ff3838' }}>● En Combate</span></Td>
              <Td>
                <ControlBtn onClick={() => alert("Unidades despachadas.")}>Despachar Unidades</ControlBtn>
                <ControlBtn>Archivar</ControlBtn>
              </Td>
            </tr>
          </tbody>
        </AlertTable>
      </ContentBody>
    </MainWrapper>
  );
}