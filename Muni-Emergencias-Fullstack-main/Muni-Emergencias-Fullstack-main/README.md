# Sistema de Gestion de Emergencias - Muni-Emergencias

Este proyecto consiste en una plataforma integral desarrollada para la Municipalidad Valle del Sol, orientada a la gestion y reporte de emergencias forestales y urbanas en tiempo real. La solucion emplea una arquitectura de microservicios y un frontend reactivo.

## Arquitectura del Sistema

El sistema se divide en componentes independientes para asegurar la escalabilidad y el mantenimiento:

- Frontend (NPM): Desarrollado con React.js y Vite. Proporciona una interfaz para ciudadanos y administradores.
- Backend For Frontend (BFF) / API Gateway: Desarrollado con Spring Cloud Gateway. Funciona como punto de entrada unico (Puerto 8080), centralizando la seguridad (CORS) y el enrutamiento de peticiones.
- Microservicios: Servicios desacoplados en Java con Spring Boot que manejan la logica de negocio:
  - ms-usuario: Gestion de perfiles y autenticacion.
  - ms-reporte: Registro y seguimiento de incidentes.
  - ms-alerta: Sistema de notificaciones tempranas.

---

## Requisitos Previos

Es necesario contar con las siguientes herramientas en el entorno local:

- Java 17 o superior.
- Node.js (v18+) y npm.
- Maven (incluido mediante el Maven Wrapper `.mvn`).

---

## Guia de Ejecucion Paso a Paso

Para que el sistema funcione correctamente y el Frontend logre comunicarse con el Backend, es estrictamente necesario levantar los servicios por separado utilizando multiples terminales.

### Paso 1: Clonar el Repositorio

Abra una terminal y descargue el proyecto:

```bash
git clone https://github.com/Benjaaes/Muni-Emergencias-Fullstack.git
cd Muni-Emergencias-Fullstack
```

---

### Paso 2: Iniciar el API Gateway (BFF)

El Gateway debe estar activo para enrutar las peticiones del frontend.

1. Abra una terminal nueva.
2. Ingrese a la carpeta del Gateway:

```bash
cd api-gateway
```

3. Ejecute el servicio:

#### Windows

```bash
./mvnw spring-boot:run
```

#### Mac/Linux

```bash
./mvnw spring-boot:run
```

---

### Paso 3: Iniciar los Microservicios

Repita este proceso por cada microservicio que necesite probar (`ms-usuario`, `ms-reporte`, `ms-alerta`).

1. Abra una terminal nueva por cada servicio.
2. Ingrese a la carpeta correspondiente.

Ejemplo para el servicio de usuarios:

```bash
cd ms-usuario
```

3. Ejecute el servicio:

#### Windows

```bash
./mvnw spring-boot:run
```

#### Mac/Linux

```bash
./mvnw spring-boot:run
```

Repita el mismo procedimiento para:

```bash
cd ms-reporte
```

y

```bash
cd ms-alerta
```

---

### Paso 4: Iniciar el Frontend

Una vez que el Gateway (puerto 8080) y los microservicios esten corriendo, proceda a iniciar la interfaz de usuario.

1. Abra una ultima terminal nueva.
2. Ingrese a la carpeta del frontend:

```bash
cd frontend
```

3. Instale las dependencias (solo la primera vez):

```bash
npm install
```

4. Levante el servidor de desarrollo:

```bash
npm run dev
```

---

## Uso del Sistema y Pruebas

1. Acceso Local  
   Una vez finalizado el Paso 4, la terminal del frontend mostrara una ruta. Abra su navegador web e ingrese a:

```txt
http://localhost:5173/
```

2. Registro e Inicio de Sesion  
   Utilice los formularios correspondientes. La peticion viajara desde el puerto `5173` hacia el Gateway en el puerto `8080`, el cual la redirigira al `ms-usuario`.

3. Reporte de Emergencias  
   Complete el formulario para enviar alertas con detalles del incidente.

---

## Patrones de Diseno Utilizados

- API Gateway / BFF: Centralizacion de llamadas y optimizacion de respuestas para el cliente.
- Database-per-Service: Independencia de datos por cada microservicio.
- DTO (Data Transfer Object): Seguridad y eficiencia en la transferencia de datos entre capas.
- Component-Based Architecture: Desarrollo modular y reutilizacion de elementos en React.

---

## Calidad y Desarrollo

- Estrategia de Branching: Se utilizo una gestion de ramas organizada (Feature Branching) para separar el desarrollo de nuevas funcionalidades, gestionar la resolucion de conflictos y asegurar la estabilidad de la rama principal.
- Pruebas Unitarias: Implementacion de pruebas exhaustivas en servicios criticos para garantizar la fiabilidad en situaciones de emergencia y lograr una alta cobertura de codigo.
- Codigo Limpio: Aplicacion de buenas practicas, principios SOLID y separacion de responsabilidades bajo el esquema Controller-Service-Repository.

---

## Autores

- Benjamin Espinoza Oñate

**Docente:** Daniel Williams Concha Saavedra  
**Institucion:** Duoc UC - Escuela de Informatica y Telecomunicaciones