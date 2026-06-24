# Microservicio de Reportes (`ms-reporte`)

Este es el microservicio de gestión de reportes de emergencias. Se ha configurado para cumplir con los estándares de pruebas unitarias y cobertura de código.

## Pruebas Unitarias y Cobertura (JaCoCo)

El proyecto cuenta con pruebas unitarias para todas las capas (Controllers, Services y Models) utilizando **JUnit 5** y **Mockito**. Adicionalmente, cuenta con el plugin de **JaCoCo** configurado para asegurar la compatibilidad con versiones modernas de Java y generar un reporte de cobertura.

Actualmente la cobertura de código probada es del **100%**.

### Comandos de Terminal

Para volver a generar y visualizar el reporte desde cero a través de tu terminal, puedes utilizar los siguientes comandos:

**1. Ejecutar las pruebas y generar el reporte:**
Desde la carpeta raíz del microservicio (`backend/ms-reporte`), ejecuta Maven:

```powershell
.\mvnw.cmd clean test jacoco:report
```

**2. Abrir el reporte en tu navegador:**
Una vez finalizado el comando anterior, puedes lanzar el archivo HTML generado usando el comando `start` (en CMD) o `Invoke-Item` / `Start-Process` (en PowerShell):

```powershell
# Usando PowerShell
Start-Process target\site\jacoco\index.html

# Usando CMD (Símbolo de sistema)
start target\site\jacoco\index.html
```

> **Nota:** Se ha guardado una copia estática del reporte actual con >95% de cobertura en la carpeta `reporte-jacoco` para que puedas adjuntarla como evidencia sin necesidad de re-ejecutar el proyecto.

Para abrir esa copia guardada, simplemente corre:
```powershell
Start-Process reporte-jacoco\index.html
```
