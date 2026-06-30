# Manual de Despliegue - Sistema de Emergencias

Esta es una guía paso a paso, infalible y directa para levantar el proyecto desde cero absoluto. Sigue estas instrucciones al pie de la letra.

## Requisitos Previos

- **Docker Desktop** instalado y ejecutándose en tu computador.
- Java 17+ instalado (requerido para compilar el backend si no tienes los archivos `.jar`).

---

## Paso 1: Compilación obligatoria del Backend

Dado que Git ignora las carpetas `target/` de los proyectos Java, **DEBEMOS** generar los archivos compilados `.jar` antes de levantar Docker. Si omites este paso, Docker arrojará el error `lstat /target: no such file or directory`.

Abre una terminal en la raíz del proyecto y navega a cada microservicio para compilarlo ejecutando el siguiente comando:

```bash
.\mvnw.cmd clean package "-Dmaven.test.skip=true"
```

Debes ejecutar este comando dentro de cada una de las siguientes carpetas:
- `backend/ms-usuario`
- `backend/ms-reporte`
- `backend/ms-alerta`
- `backend/api-gateway`

*(Nota: Si estás en Mac/Linux, utiliza `./mvnw clean package "-Dmaven.test.skip=true"`).*

---

## Paso 2: Encender el proyecto con Docker

Una vez que todos los microservicios se hayan compilado exitosamente, asegúrate de estar en el directorio donde se encuentra el archivo `docker-compose.yml` y ejecuta el siguiente comando exacto:

```bash
docker compose up --build -d
```

Este comando descargará las imágenes necesarias, construirá los contenedores locales (incluyendo tu base de datos y tu gestor visual) y los dejará ejecutándose en segundo plano.

---

## Paso 3: Verificar el funcionamiento

Espera unos segundos a que todos los servicios arranquen completamente. Puedes verificar que el sistema funciona ingresando a las siguientes rutas desde tu navegador web:

- **Frontend (Aplicación Web):** [http://localhost:5173](http://localhost:5173)
- **API Gateway (Enrutador Backend):** [http://localhost:8090](http://localhost:8090)

---

## Paso 4: Guía de acceso y configuración de pgAdmin (Gestor de Base de Datos)

Para ver la base de datos de manera visual y en tiempo real, hemos integrado pgAdmin4.

1. **Ingresa a pgAdmin Web:** Abre tu navegador y dirígete a [http://localhost:5050](http://localhost:5050).
2. **Inicia Sesión:** 
   - Correo electrónico: `admin@duoc.cl`
   - Contraseña: `duoc`
3. **Registra el Servidor de Base de Datos:**
   - En la pantalla principal, haz clic en **"Add New Server"** (Agregar Nuevo Servidor).
   - En la pestaña **General**, ponle el nombre que quieras (ej: `Emergencias DB`).
   - En la pestaña **Connection** (Conexión), ingresa exactamente estos datos:
     - **Host name/address:** `postgres` *(esto es muy importante, debe decir postgres, NO localhost)*
     - **Port:** `5432`
     - **Username:** `postgres`
     - **Password:** `duoc`
   - Haz clic en **Save** (Guardar).
4. **Ver los datos en tiempo real:**
   - Despliega en el menú lateral izquierdo: `Servers > Emergencias DB > Databases`.
   - Allí verás tus bases de datos (`bd_usuario`, `bd_reporte`, `bd_alerta`).
   - Para ver los datos, despliega: `[Nombre BD] > Schemas > public > Tables`.
   - Haz clic derecho sobre cualquier tabla y selecciona **"View/Edit Data" > "All Rows"** para ver los registros guardados.
