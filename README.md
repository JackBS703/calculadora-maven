# Calculadora Maven — Pipeline CI/CD

**Politécnico Colombiano Jaime Isaza Cadavid**  
Ingeniería de Sistemas — ING01201 Pruebas y Gestión de la Configuración  
Docente: David Fernando Mejia Tabares  
Parcial Final — Laboratorio Práctico (25% de la nota)

**Equipo:**
- Mateo Berrío Cardona
- Mariana Montoya Sepúlveda
- Esteban Cano Ramírez
- Yeimy Daniela Herrera Bedoya

**Repositorio:** [https://github.com/JackBS703/calculadora-maven/tree/feature/pom-sonar](https://github.com/JackBS703/calculadora-maven/tree/feature/pom-sonar)

---

## Objetivo del Laboratorio

Implementar un flujo de integración continua y despliegue (CI/CD) que permita detectar automáticamente cambios en el código fuente, analizar su calidad con SonarQube bajo reglas definidas, notificar al equipo ante fallos y preparar el artefacto para producción cuando el código cumple los estándares de calidad establecidos.

---

## Descripción del Proyecto

Calculadora en Java con Maven que implementa operaciones aritméticas básicas con validación de entradas y manejo explícito de errores. Incluye:

- Suma, resta, multiplicación y división
- Validación de división por cero con lanzamiento de excepción controlada
- Clase `Historial` de operaciones para enriquecer el código y facilitar el análisis de calidad
- Clase `Main` que ejecuta un flujo demostrativo
- Pruebas unitarias con JUnit 5 en `CalculadoraTest.java`

### Estructura del Proyecto

```
calculadora-maven/
├── pom.xml
├── Jenkinsfile
├── evidencias/
│   ├── 01-tests-maven.png
│   ├── 02-sonarqube-analysis-success.png
│   ├── 03-quality-gate-passed.png
│   └── 04-issues-accepted.png
└── src/
    ├── main/
    │   └── java/com/equipo/calculadora/
    │       ├── Main.java
    │       ├── Calculadora.java
    │       └── Historial.java
    └── test/
        └── java/com/equipo/calculadora/
            └── CalculadoraTest.java
```

---

## Herramientas y Versiones

| Herramienta | Versión |
|-------------|---------|
| Java | 17 LTS |
| Maven | 3.9.x |
| SonarQube | 10.x Community Edition |
| Jenkins | 2.x LTS |
| GitHub | — |

---

## Comandos Utilizados

### Compilar el proyecto
```bash
mvn clean compile
```

### Ejecutar pruebas unitarias
```bash
mvn clean test
```

### Ejecutar análisis con SonarQube
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=calculadora-maven \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<token>
```

### Empaquetar el artefacto `.jar`
```bash
mvn package -DskipTests
```

---

## Reglas de Calidad — Quality Gate `QG_Laboratorio`

La Quality Gate personalizada del proyecto evalúa dos condiciones:

| Regla | Métrica | Condición de fallo |
|-------|---------|-------------------|
| Duplicated Lines (%) | `duplicated_lines_density` | Mayor al 3% |
| Blocker Issues | `blocker_violations` | 1 o más |

---

## Pipeline CI/CD — Jenkinsfile

El pipeline contiene cuatro stages en orden:

| Stage | Acción |
|-------|--------|
| Checkout | Descarga el código desde GitHub |
| Build | `mvn clean compile` |
| SonarQube | `mvn sonar:sonar` — análisis y evaluación de Quality Gate |
| Notify / Package | Si falla: envía correo. Si pasa: `mvn package` y archiva el `.jar` |

Jenkins detecta cambios en el repositorio con **SCM Polling** usando la expresión cron `H/1 * * * *` (cada minuto).

---

## Resumen de Resultados

### ✅ Completado

- **Mateo Berrío Cardona** — Código Java: `Calculadora.java`, `Historial.java`, `Main.java`, manejo de excepciones y pruebas unitarias en `CalculadoraTest.java`.
- **Mariana Montoya Sepúlveda** — Configuración de `pom.xml` con plugin SonarQube y JaCoCo, configuración del proyecto en la interfaz de SonarQube y Quality Gate asociada.

Las pruebas unitarias se ejecutan correctamente (0 errores, 0 fallos). El análisis de SonarQube termina con `ANALYSIS SUCCESSFUL`. El proyecto pasa la Quality Gate con las condiciones de duplicación e issues configuradas.

### 🔄 Pendiente

- **Esteban Cano Ramírez** — Jenkinsfile completo, configuración del job en Jenkins, visualización en Blue Ocean, configuración de correo SMTP.
- **Yeimy Daniela Herrera Bedoya** — Configuración del repositorio en GitHub (ramas `main` y `develop`), webhook o SCM Polling activo en Jenkins, prueba end-to-end del flujo completo.

---

## Evidencias

Las capturas de pantalla se encuentran en la carpeta `evidencias/`:

| Archivo | Descripción |
|---------|-------------|
| `01-tests-maven.png` | Ejecución exitosa de pruebas con Maven (`Tests run: X, Failures: 0`) |
| `02-sonarqube-analysis-success.png` | Terminal con `BUILD SUCCESS` y `ANALYSIS SUCCESSFUL` |
| `03-quality-gate-passed.png` | Dashboard de SonarQube con `Quality Gate: Passed` |
| `04-issues-accepted.png` | Issues detectados marcados como Accepted en SonarQube |

---

## Nota sobre Issues Aceptados

Los issues detectados por SonarQube fueron revisados por el equipo. Se tomó la decisión de marcarlos como **Accepted** (Won't Fix) debido a que el objetivo principal de esta práctica es demostrar la integración de las herramientas del pipeline CI/CD (Maven, SonarQube, Jenkins, GitHub), y no realizar refactorización exhaustiva del código. Esta decisión queda registrada en la herramienta como evidencia de revisión consciente y criterio de gestión de calidad.

---

## Criterios de Aceptación del Laboratorio

- [x] El código Java compila correctamente con Maven
- [x] SonarQube muestra el análisis del proyecto con las dos reglas evaluadas
- [x] La Quality Gate `QG_Laboratorio` está configurada y asociada al proyecto
- [ ] Jenkins detecta un nuevo commit en menos de 1 minuto (pendiente configuración)
- [ ] Ante código con duplicados > 3% o con un Blocker Issue, Jenkins envía correo de notificación
- [ ] Ante código limpio, Jenkins empaqueta el `.jar` y lo archiva
- [ ] El pipeline es visible y navegable desde Blue Ocean
