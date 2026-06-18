# Borrador de Persistencia: Muni-Emergencias

Este documento describe técnicamente la implementación de persistencia de datos en la arquitectura de microservicios del sistema **Muni-Emergencias**, utilizando el ecosistema de **Spring Data JPA**, **Hibernate**, y **PostgreSQL**.

## Arquitectura Database-per-Service

Para mantener un bajo nivel de acoplamiento, alta cohesión y asegurar la autonomía de cada dominio, el sistema implementa el patrón **Database-per-Service**. Cada microservicio se conecta a su propia base de datos PostgreSQL, las cuales se inicializan automáticamente mediante Docker Compose:
- `bd_usuario` (ms-usuario)
- `bd_reporte` (ms-reporte)
- `bd_alerta` (ms-alerta)

## 1. Mapeo Objeto-Relacional (ORM)

Spring Data JPA se apoya en Hibernate como proveedor de JPA para realizar el mapeo entre las clases Java (Entidades) y las tablas relacionales.

### Entidad `Usuario` (ms-usuario)
```java
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String rut;
    private String rol;
    private String email;
    private String password;
}
```
* **`@Entity` / `@Table`**: Indica a Hibernate que la clase está mapeada a la tabla `usuarios` en `bd_usuario`.
* **`@Id` / `@GeneratedValue`**: Define la llave primaria auto-incremental delegada a la base de datos (PostgreSQL usa secuencias `IDENTITY` en versiones modernas o `SERIAL` implícito).

### Entidad `Reporte` (ms-reporte)
```java
@Entity
@Table(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tipo_emergencia")
    private String tipoEmergencia;
    private String descripcion;
    private String estado;
}
```
* **`@Column`**: Se usa para mapear atributos camelCase a columnas en formato snake_case (`tipo_emergencia`), garantizando las convenciones de bases de datos relacionales.

### Entidad `Alerta` (ms-alerta)
Se mapea de manera idéntica hacia la tabla `alertas` para guardar notificaciones o alarmas tempranas con su respectivo estado booleano (`enviada`).

## 2. Patrón Repository

La interacción directa con la base de datos se maneja a través de interfaces que heredan de `JpaRepository<T, ID>`. Esto provee automáticamente métodos CRUD (Create, Read, Update, Delete) y capacidades de paginación sin necesidad de escribir sentencias SQL:

```java
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailAndPassword(String email, String password);
}
```

* **Query Methods (Derived Queries)**: Como se observa en `findByEmailAndPassword`, Spring Data JPA traduce automáticamente la firma del método en una sentencia SQL segura (`SELECT * FROM usuarios WHERE email = ? AND password = ?`), mitigando el riesgo de Inyección SQL.

## 3. Transaccionalidad y Configuración

Las propiedades de la conexión (JDBC URL, usuario, contraseña) están externalizadas usando variables de entorno inyectadas en los contenedores Docker (`SPRING_DATASOURCE_URL`, etc.). Durante la ejecución, el framework asegura que las operaciones de servicio que envuelven llamadas al repositorio se manejen dentro de un contexto transaccional, garantizando las propiedades ACID.
