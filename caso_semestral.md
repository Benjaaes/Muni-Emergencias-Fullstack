# Caso Semestral

| Sigla | Nombre Asignatura |
|---|---|
| **DSY1106** | **DESARROLLO FULLSTACK III** |

## Caso Semestral: "Municipalidad Valle del Sol – Plataforma inteligente para la gestión y prevención de incendios"

En diversas regiones del país en los últimos años han ocurrido incendios forestales y urbanos generando una amenaza constante a la seguridad de las personas, la infraestructura y el entorno natural. Las municipalidades cumplen un rol sumamente importante en la prevención, detección temprana y coordinación de emergencias.

La Municipalidad Valle del Sol, a través de su Subdirección de Gestión de Emergencias y Prevención de desastres desea tomar cartas en el asunto y ejecutar acciones preventivas, monitoreo y respuesta ante situaciones de emergencia en el territorio comunal.

Según las vivencias previas de las diversas catástrofes que han acontecido se ha identificado que la mayoría de los reportes se reciben a través de llamadas telefónicas, mensajes mediante redes sociales, informes de vecinos, aviso de bomberos o brigadistas forestales.

Con el objetivo de mejorar y preparar a la comunidad a futuros incidentes la Municipalidad Valle del Sol desea impulsar un desarrollo de una plataforma tecnológica basada en microservicios, que permita registrar reportes de incendios, visualizar información geográfica en tiempo real y facilitar la comunicación con la comunidad.

El desarrollo de este sistema se llevará a cabo en **tres etapas**, alineadas con las evaluaciones parciales del curso. Finalmente, en el **Examen Final Transversal**, los/as estudiantes consolidarán su solución integrando todos los módulos desarrollados en un sistema funcional.

---

## Sección 1: Diseño de Arquitectura y Patrones de Microservicios (Parcial 1)

### Contexto

La Subdirección de Gestión de Emergencias de la Municipalidad Valle del Sol tiene como misión prevenir, detectar, coordinar situaciones de riesgo y especializarse en catástrofes de tipo forestal y urbano.

Sin embargo, los actuales sistemas presentan diversas limitaciones ya que gran parte de la información se gestiona mediante herramientas aisladas o procesos manuales. Dentro de los cuales se destacan:

- Falta de herramientas digitales para reportar incendios
- Dificultad para visualizar la ubicación exacta de focos de incendio
- Escasa integración entre la municipalidad y organismos de emergencias
- Falta de mecanismos automatizados para alertar a la población cuando la emergencia escala y se transforma en una catástrofe
- Ausencia de una base de datos histórica que permita analizar patrones o recurrencia

Para mejorar esta situación, la municipalidad busca desarrollar una plataforma tecnológica que permita centralizar la información relacionada con incendios, emergencias, facilitando la detección temprana y la toma de decisiones.

La solución debe contemplar tres módulos principales:

- **Detección y reporte:** Permite que ciudadanos, brigadas, funcionarios o cualquier persona pueda reportar un foco de incendio utilizando su dispositivo móvil, incluyendo fotografías, videos y ubicación geográfica.
- **Monitoreo Geográfico:** Permite visualizar en mapa interactivo los focos de incendio reportados, brigadas activas, rutas de evacuación y zonas de riesgo dentro del territorio comunal.
- **Sistema de alertas a la comunidad:** Permitir enviar información sobre situaciones de emergencia mediante un canal oficial y mantener a la comunidad informada.

### Requerimientos Técnicos

Los/as estudiantes deberán diseñar una **arquitectura de microservicios escalable**, aplicando **patrones de diseño y arquetipos arquitectónicos** que permitan la modularización del sistema. Para ello, deberán:

- **Definir los microservicios clave**, asegurando separación de responsabilidades y escalabilidad.
- Diseñar una **API Gateway** que gestione la comunicación entre microservicios y el frontend.
- Implementar patrones como **Repository Pattern** para la persistencia de datos, **Factory Method** para la creación de instancias y **Circuit Breaker** para manejar fallos en la comunicación entre servicios.
- Asegurar que los servicios sean **escalables y desacoplados**, permitiendo futuras mejoras sin afectar el funcionamiento del sistema.
- Documentar las decisiones arquitectónicas y justificar la selección de patrones.

Al finalizar esta etapa, los equipos deberán presentar un informe con la propuesta de arquitectura, un diagrama detallado de los microservicios y una justificación de los patrones seleccionados.

---

## Sección 2: Desarrollo de Componentes Frontend y Backend (Parcial 2)

### Contexto

Después de definir la arquitectura del sistema, la Municipalidad Valle del Sol busca desarrollar una primera versión funcional que permita validar la propuesta tecnológica. La municipalidad requiere un sistema que cuente con una **interfaz de usuario intuitiva y responsiva**. El backend debe ser capaz de procesar volúmenes de datos sin comprometer el rendimiento.

### Requerimientos Técnicos

Los/as estudiantes deberán desarrollar la solución con los siguientes elementos clave:

- **Frontend:** Implementar una interfaz construida con un framework moderno (React, Angular o Vue.js), asegurando que la comunicación con el backend se realice vía API REST. Los componentes frontend deberán ser empaquetados como **módulos NPM** reutilizables.
- **Backend:** Construir al menos tres componentes backend utilizando **arquetipos Maven personalizados**:
  - Un **Backend For Frontend (BFF)** para gestionar la interacción entre el frontend y los microservicios.
  - Dos **microservicios independientes**, conectados a bases de datos mediante JPA.
- **Conexión con Bases de Datos:** Utilizar **JPA y entidades**, asegurando la persistencia de datos. También se podrán utilizar procedimientos almacenados (SPs) para optimizar operaciones.
- **Versionamiento del Código:** Todos los componentes deberán ser versionados en **Git**, utilizando estrategias de branching como Git Flow o GitHub Flow para facilitar el trabajo colaborativo.
- **Implementación de Patrones de Diseño:** Aplicar patrones adecuados para mejorar la organización y mantenibilidad del código, asegurando que los servicios sean modulares y fácilmente extensibles.

Como resultado de esta etapa, los/as estudiantes deberán entregar la primera versión funcional del sistema, acompañada de una presentación donde expliquen las decisiones técnicas, la implementación de los componentes y la integración entre frontend y backend.

---

## Sección 3: Integración, Pruebas Unitarias y Presentación Final (Parcial 3)

### Contexto

Con el sistema en funcionamiento, la Municipalidad Valle del Sol quiere asegurar que la solución sea robusta y confiable antes de su implementación definitiva. Para ello, se requiere que el código desarrollado cumpla con **estándares de calidad**, aplicando **pruebas unitarias** y validando la **cobertura del código** antes del lanzamiento.

En esta última fase, la empresa evaluará cómo se integran los diferentes módulos del sistema, garantizando que la plataforma sea escalable y esté lista para su despliegue en producción.

### Requerimientos Técnicos

En esta etapa, los/as estudiantes deberán:

- **Realizar Pruebas Unitarias:** Implementar pruebas para cada uno de los componentes del sistema, asegurando una cobertura mínima del **60% del código**. La validación de cobertura se realizará utilizando herramientas como **SonarQube**.
- **Refactorización y Mejora del Código:** Revisar el código desarrollado, identificar oportunidades de optimización y aplicar mejoras siguiendo buenas prácticas de desarrollo.
- **Despliegue y Ejecución de Pruebas:** Integrar las pruebas unitarias en un pipeline de **Integración Continua**, asegurando que se ejecuten automáticamente en cada actualización del código.
- **Documentación Final:** Completar la documentación del sistema, incluyendo diagramas actualizados, descripción de la arquitectura final y detalles sobre la implementación de pruebas.

### Presentación Final

Cada equipo presentará su solución ante el/la docente, abordando los siguientes puntos clave:

- **Explicación de la arquitectura del sistema y decisiones técnicas.**
- **Demostración del funcionamiento del software, mostrando la integración entre frontend y backend.**
- **Estrategia de pruebas implementada y validación de cobertura.**
- **Reflexión sobre los desafíos enfrentados y aprendizajes adquiridos.**

Esta presentación servirá como cierre del curso, permitiendo evaluar de manera integral la capacidad de los/as estudiantes para diseñar, desarrollar, integrar y validar una solución de software basada en **microservicios, patrones de diseño y pruebas automatizadas**.
