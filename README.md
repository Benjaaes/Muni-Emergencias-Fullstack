# 🚨 Sistema de Gestión de Emergencias — Muni-Emergencias

> **Municipalidad Valle del Sol** | Plataforma integral para la gestión y reporte de emergencias forestales y urbanas en tiempo real.

---

## 📐 Arquitectura del Sistema

El sistema emplea una **arquitectura de microservicios** para asegurar la escalabilidad, el mantenimiento independiente y la separación de responsabilidades:

```
[React Frontend :5173]
        │
        ▼
[API Gateway / BFF :8080]  ← Punto de entrada único (CORS + Enrutamiento)
        │
   ┌────┴─────────┐
   ▼              ▼
[ms-usuario]  [ms-reporte]  [ms-alerta]
  :8081          :8082         :8083
```

| Servicio         | Puerto | Responsabilidad                                    |
|------------------|--------|----------------------------------------------------|
| `api-gateway`    | 8080   | Enrutamiento, CORS y seguridad centralizada (BFF)  |
| `ms-usuario`     | 8081   | Registro de agentes y autenticación con BCrypt     |
| `ms-reporte`     | 8082   | Registro y seguimiento de incidentes de emergencia |
| `ms-alerta`      | 8083   | Emisión de alertas tempranas a destinatarios       |
| `frontend`       | 5173   | Interfaz React/Vite para ciudadanos y operadores   |

> ℹ️ Estos puertos corresponden a la **ejecución manual (Opción A)**. Si usas **Docker Compose (Opción B)**, los puertos son distintos (8090-8093) — revisa la sección de Docker más abajo.

---

## ✅ Requisitos Previos

| Herramienta | Versión mínima | ¿Cuándo la necesito? |
|-------------|----------------|------------------------|
| Java        | 17+            | Ejecución manual (Opción A) |
| Node.js     | 18+            | Ejecución manual (Opción A) |
| npm         | 9+             | Ejecución manual (Opción A) |
| Maven       | Incluido vía `mvnw` | Ejecución manual (Opción A) |
| Docker      | 24+            | Ejecución con contenedores (Opción B) |
| Docker Compose | v2 (`docker compose`) | Ejecución con contenedores (Opción B) |

> 💡 No necesitas instalar Java, Node ni Maven si vas a usar la **Opción B (Docker)** — todo se construye dentro de los contenedores.

---

## 🚀 Guía de Ejecución

Existen dos formas de levantar el sistema: **manualmente** (un servicio por terminal) o con **Docker Compose** (todo con un solo comando). Elige la que prefieras.

### Paso 1 — Clonar el repositorio

```bash
git clone https://github.com/Benjaaes/Muni-Emergencias-Fullstack.git
cd Muni-Emergencias-Fullstack
```

---

## Opción A — Ejecución Manual (sin Docker)

Levanta cada servicio en una **terminal separada**, en el orden indicado.

---

### Paso 1.1 — Iniciar el API Gateway (BFF)

```bash
# Ingresa a la carpeta del Gateway
cd Muni-Emergencias/Muni-Emergencias/backend/api-gateway

# Windows
.\mvnw.cmd spring-boot:run

# Mac / Linux
./mvnw spring-boot:run
```

> El Gateway quedará disponible en `http://localhost:8080`

---

### Paso 1.2 — Iniciar los Microservicios

Abre una terminal nueva para **cada uno** de los siguientes servicios:

**ms-usuario** (Terminal 2)
```bash
cd Muni-Emergencias/Muni-Emergencias/backend/ms-usuario

# Windows
.\mvnw.cmd spring-boot:run

# Mac / Linux
./mvnw spring-boot:run
```

**ms-reporte** (Terminal 3)
```bash
cd Muni-Emergencias/Muni-Emergencias/backend/ms-reporte

# Windows
.\mvnw.cmd spring-boot:run

# Mac / Linux
./mvnw spring-boot:run
```

**ms-alerta** (Terminal 4)
```bash
cd Muni-Emergencias/Muni-Emergencias/backend/ms-alerta

# Windows
.\mvnw.cmd spring-boot:run

# Mac / Linux
./mvnw spring-boot:run
```

---

### Paso 1.3 — Iniciar el Frontend

```bash
cd frontend

# Instalar dependencias (solo la primera vez)
npm install

# Iniciar servidor de desarrollo
npm run dev
```

> La aplicación estará disponible en `http://localhost:5173`

---

## Opción B — Ejecución con Docker 🐳

Esta es la forma más rápida de levantar **todo el sistema** (Postgres + 3 microservicios + Gateway + Frontend) con un solo comando, sin instalar Java, Node ni Maven en tu máquina.

### Servicios y puertos definidos en `docker-compose.yml`

| Servicio        | Contenedor              | Puerto host → contenedor |
|-----------------|--------------------------|----------------------------|
| `postgres`      | `postgres-emergencias`   | `5433 → 5432`              |
| `ms-usuario`    | `ms-usuario`              | `8091 → 8091`              |
| `ms-reporte`    | `ms-reporte`              | `8092 → 8092`              |
| `ms-alerta`     | `ms-alerta`               | `8093 → 8093`              |
| `api-gateway`   | `api-gateway`             | `8090 → 8090`              |
| `frontend`      | `frontend-emergencias`   | `5173 → 80`                |

> ⚠️ **Importante:** estos puertos (8090-8093) son distintos a los usados en la Opción A (8080-8083), porque corresponden a la configuración real del `docker-compose.yml`. Si pruebas con Docker, usa siempre `8090`, `8091`, `8092`, `8093` para acceder al Gateway y a los microservicios.

> ⚠️ El contenedor `postgres` solo crea la base de datos por defecto `postgres`. Como el Gateway y los microservicios necesitan `bd_usuario`, `bd_reporte` y `bd_alerta`, asegúrate de tener un archivo `init.sql` en la raíz del proyecto (junto al `docker-compose.yml`) con las sentencias `CREATE DATABASE bd_usuario;`, `CREATE DATABASE bd_reporte;` y `CREATE DATABASE bd_alerta;`. Ese script se ejecuta automáticamente la primera vez que se crea el volumen de Postgres.

### 🔨 Construir y levantar todo el sistema

```bash
# Ubícate en la raíz del proyecto (donde está docker-compose.yml)
cd Muni-Emergencias-Fullstack

# Construir las imágenes y levantar todos los contenedores (logs visibles)
docker compose up --build

# Lo mismo pero en segundo plano (modo detached)
docker compose up --build -d
```

> La primera vez puede tardar varios minutos mientras se compilan los microservicios Java y el frontend.

### 🧱 Solo construir las imágenes (sin levantar contenedores)

```bash
docker compose build

# Forzar reconstrucción ignorando la caché de Docker
docker compose build --no-cache
```

### ▶️ Levantar los contenedores ya construidos (sin reconstruir)

```bash
docker compose up -d
```

### 🔁 Reconstruir y levantar solo un servicio puntual (ej. tras un cambio de código)

```bash
docker compose up --build -d ms-reporte
docker compose up --build -d ms-usuario
docker compose up --build -d ms-alerta
docker compose up --build -d api-gateway
docker compose up --build -d frontend
```

### 📋 Ver el estado y los logs de los contenedores

```bash
# Estado de todos los contenedores
docker compose ps

# Logs de todos los servicios en tiempo real
docker compose logs -f

# Logs de un servicio específico
docker compose logs -f ms-usuario
docker compose logs -f api-gateway
```

### 🐘 Entrar a la base de datos Postgres dentro del contenedor

```bash
docker exec -it postgres-emergencias psql -U postgres

# Una vez dentro de psql, listar las bases de datos:
\l
```

### ⏹️ Detener y limpiar

```bash
# Detener los contenedores (mantiene los datos de Postgres)
docker compose stop

# Detener y eliminar los contenedores, redes (mantiene el volumen de datos)
docker compose down

# Detener y eliminar TODO, incluido el volumen de Postgres (borra la base de datos)
docker compose down -v
```

> Una vez levantado con Docker, el sistema queda disponible en:
> - Frontend → `http://localhost:5173`
> - API Gateway → `http://localhost:8090`
> - ms-usuario → `http://localhost:8091`
> - ms-reporte → `http://localhost:8092`
> - ms-alerta → `http://localhost:8093`

---

## 🧪 Pruebas Unitarias y Cobertura con JaCoCo

Los microservicios `ms-usuario`, `ms-reporte` y `ms-alerta` cuentan con pruebas unitarias implementadas con **JUnit 5 + Mockito**, con cobertura de instrucciones **superior al 80%** validada mediante **JaCoCo 0.8.12**.

### Stack de pruebas utilizado

| Herramienta       | Uso                                                                 |
|-------------------|---------------------------------------------------------------------|
| **JUnit 5**       | Motor de pruebas (`@Test`, `@BeforeEach`, `@DisplayName`)          |
| **Mockito**       | Simulación de dependencias (`@Mock`, `@InjectMocks`)               |
| **JaCoCo**        | Medición de cobertura de código (instrucciones, ramas, métodos)    |

---

### ▶️ 1. Ejecutar las pruebas y generar el reporte

Ubícate en la carpeta del microservicio que quieres validar y ejecuta el comando correspondiente a tu sistema operativo.

| Microservicio | Windows (CMD / PowerShell)                                              | Mac / Linux                          |
|----------------|---------------------------------------------------------------------------|----------------------------------------|
| `ms-usuario`  | `cd Muni-Emergencias/Muni-Emergencias/backend/ms-usuario && .\mvnw.cmd clean test jacoco:report` | `cd Muni-Emergencias/Muni-Emergencias/backend/ms-usuario && ./mvnw clean test jacoco:report` |
| `ms-reporte`  | `cd Muni-Emergencias/Muni-Emergencias/backend/ms-reporte && .\mvnw.cmd clean test jacoco:report` | `cd Muni-Emergencias/Muni-Emergencias/backend/ms-reporte && ./mvnw clean test jacoco:report` |
| `ms-alerta`   | `cd Muni-Emergencias/Muni-Emergencias/backend/ms-alerta && .\mvnw.cmd clean test jacoco:report`  | `cd Muni-Emergencias/Muni-Emergencias/backend/ms-alerta && ./mvnw clean test jacoco:report`  |

> 💡 En Mac/Linux, si `./mvnw` no tiene permisos de ejecución, corre primero `chmod +x mvnw`.

**Ejecutar los tres microservicios de una sola vez** (desde la carpeta `backend/`):

```bash
# Mac / Linux
for s in ms-usuario ms-reporte ms-alerta; do
  (cd "Muni-Emergencias/Muni-Emergencias/backend/$s" && ./mvnw clean test jacoco:report)
done
```

```powershell
# Windows PowerShell
foreach ($s in "ms-usuario","ms-reporte","ms-alerta") {
  Set-Location "Muni-Emergencias\Muni-Emergencias\backend\$s"
  .\mvnw.cmd clean test jacoco:report
  Set-Location $PSScriptRoot
}
```

El reporte HTML se genera automáticamente en:
```
<microservicio>/target/site/jacoco/index.html
```

---

### 📊 2. Abrir el reporte HTML en el navegador

Una vez generado (o usando la copia estática incluida en el repo), ábrelo con el comando de tu sistema operativo.

**Windows — PowerShell**
```powershell
# ms-usuario
Start-Process "Muni-Emergencias\Muni-Emergencias\backend\ms-usuario\target\site\jacoco\index.html"

# ms-reporte
Start-Process "Muni-Emergencias\Muni-Emergencias\backend\ms-reporte\target\site\jacoco\index.html"

# ms-alerta
Start-Process "Muni-Emergencias\Muni-Emergencias\backend\ms-alerta\target\site\jacoco\index.html"
```

**Windows — CMD**
```cmd
start Muni-Emergencias\Muni-Emergencias\backend\ms-usuario\target\site\jacoco\index.html
start Muni-Emergencias\Muni-Emergencias\backend\ms-reporte\target\site\jacoco\index.html
start Muni-Emergencias\Muni-Emergencias\backend\ms-alerta\target\site\jacoco\index.html
```

**Mac (macOS)**
```bash
open Muni-Emergencias/Muni-Emergencias/backend/ms-usuario/target/site/jacoco/index.html
open Muni-Emergencias/Muni-Emergencias/backend/ms-reporte/target/site/jacoco/index.html
open Muni-Emergencias/Muni-Emergencias/backend/ms-alerta/target/site/jacoco/index.html
```

**Linux**
```bash
xdg-open Muni-Emergencias/Muni-Emergencias/backend/ms-usuario/target/site/jacoco/index.html
xdg-open Muni-Emergencias/Muni-Emergencias/backend/ms-reporte/target/site/jacoco/index.html
xdg-open Muni-Emergencias/Muni-Emergencias/backend/ms-alerta/target/site/jacoco/index.html
```

**Si ya estás dentro de la carpeta del microservicio**, basta con:
```bash
# Windows
Start-Process target\site\jacoco\index.html

# Mac
open target/site/jacoco/index.html

# Linux
xdg-open target/site/jacoco/index.html
```

---

### 📁 3. Reportes estáticos ya guardados (sin necesidad de compilar)

Cada microservicio incluye una copia permanente y ya generada del reporte de cobertura en su carpeta `reporte-jacoco/`. Puedes abrirla directamente, sin ejecutar Maven:

**Desde la raíz del proyecto**

| Microservicio | Windows (PowerShell)                                                                      | Mac                                                                                  | Linux                                                                                |
|----------------|---------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| `ms-usuario` (~93%)  | `Start-Process "Muni-Emergencias\Muni-Emergencias\backend\ms-usuario\reporte-jacoco\index.html"` | `open Muni-Emergencias/Muni-Emergencias/backend/ms-usuario/reporte-jacoco/index.html` | `xdg-open Muni-Emergencias/Muni-Emergencias/backend/ms-usuario/reporte-jacoco/index.html` |
| `ms-reporte` (~97%)  | `Start-Process "Muni-Emergencias\Muni-Emergencias\backend\ms-reporte\reporte-jacoco\index.html"` | `open Muni-Emergencias/Muni-Emergencias/backend/ms-reporte/reporte-jacoco/index.html` | `xdg-open Muni-Emergencias/Muni-Emergencias/backend/ms-reporte/reporte-jacoco/index.html` |
| `ms-alerta` (~96%)   | `Start-Process "Muni-Emergencias\Muni-Emergencias\backend\ms-alerta\reporte-jacoco\index.html"`  | `open Muni-Emergencias/Muni-Emergencias/backend/ms-alerta/reporte-jacoco/index.html`  | `xdg-open Muni-Emergencias/Muni-Emergencias/backend/ms-alerta/reporte-jacoco/index.html`  |

**Desde la carpeta del microservicio:**
```bash
# Windows
Start-Process reporte-jacoco\index.html

# Mac
open reporte-jacoco/index.html

# Linux
xdg-open reporte-jacoco/index.html
```

---

### 📈 Resumen de Cobertura por Microservicio

| Microservicio | Tests | Instrucciones | Ramas | Estado     |
|---------------|-------|---------------|-------|------------|
| `ms-reporte`  | 10+   | **97%**       | 83%   | ✅ >80%    |
| `ms-alerta`   | 13    | **~96%**      | n/a*  | ✅ >80%    |
| `ms-usuario`  | 18    | **~93%**      | n/a*  | ✅ >80%    |

> *`n/a` en Branches indica que el código no tiene sentencias `if/else` — no afecta el porcentaje total de cobertura.

---

## 🧩 Patrones de Diseño Utilizados

| Patrón                        | Descripción                                                          |
|-------------------------------|------------------------------------------------------------------------|
| **API Gateway / BFF**         | Centralización de llamadas y punto de entrada único                  |
| **Database-per-Service**      | Independencia total de datos por microservicio                       |
| **DTO (Data Transfer Object)**| Separación entre entidades JPA y datos expuestos al cliente          |
| **Repository Pattern**        | Abstracción del acceso a datos vía Spring Data JPA                   |
| **Component-Based (React)**   | Desarrollo modular y reutilización de componentes en el frontend     |

---

## ⚙️ Calidad y Desarrollo

- **Branching Strategy:** Feature Branching para separar el desarrollo de funcionalidades y proteger la rama principal.
- **Pruebas Unitarias:** Cobertura >80% en todos los microservicios usando JUnit 5 + Mockito + JaCoCo, con validación de reglas de negocio reales (autenticación BCrypt, emisión de alertas, generación de reportes).
- **Código Limpio:** Principios SOLID, separación Controller → Service → Repository, sin anotaciones Lombok para compatibilidad con Java 21+.

---

## 👤 Autores

**Desarrollador:** Benjamin Espinoza Oñate  
**Docente:** Daniel Williams Concha Saavedra  
**Institución:** Duoc UC — Escuela de Informática y Telecomunicaciones
