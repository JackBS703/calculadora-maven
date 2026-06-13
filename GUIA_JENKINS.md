# Guía de Configuración de Jenkins, SMTP y Blue Ocean

Esta guía proporciona las instrucciones detalladas para poner en marcha el pipeline de integración continua configurado en el `Jenkinsfile`. 

---

## 1. Configuración de Plugins Requeridos

Antes de comenzar, asegúrese de tener instalados los siguientes plugins en su instancia de Jenkins (desde **Administrar Jenkins** -> **Administrar Plugins** -> **Disponibles**):

1. **Pipeline** (Conjunto de plugins para pipelines declarativos).
2. **Blue Ocean** (Interfaz visual moderna e interactiva para pipelines).
3. **SonarQube Scanner for Jenkins** (Para enviar análisis y esperar el estado del Quality Gate).
4. **Email Extension Plugin** o usar el **Mailer** nativo de Jenkins para notificaciones de fallos.

---

## 2. Configuración Global de Correo (SMTP)

Para que Jenkins pueda enviar correos de notificación cuando una compilación falle o el Quality Gate de SonarQube no se supere:

1. Vaya a **Administrar Jenkins** (Manage Jenkins) -> **Configurar el Sistema** (Configure System).
2. Busque la sección **Localización de Jenkins** (Jenkins Location) y configure el **Dirección de correo electrónico del administrador del sistema** (System Admin E-mail Address). *Nota: Este correo debe coincidir con el correo desde el cual enviará las notificaciones SMTP.*
3. Desplácese hasta el final del formulario en la sección **Notificación por correo electrónico** (E-mail Notification):
   - **Servidor SMTP:** Introduzca el servidor de su proveedor.
     - *Ejemplo Gmail:* `smtp.gmail.com`
     - *Ejemplo Outlook:* `smtp.office365.com`
   - Haga clic en **Avanzado...** (Advanced...) para abrir los campos adicionales:
     - Marque la casilla **Usar autenticación SMTP** (Use SMTP Authentication).
     - **Nombre de usuario:** Su correo electrónico (ej. `tu-correo@gmail.com`).
     - **Contraseña:** Su contraseña de correo o una *Contraseña de aplicación* generada desde su cuenta de seguridad (especialmente requerida para Gmail con 2FA activo).
     - Marque **Usar SSL** (Use SSL) si el puerto es `465`. Si usa puerto `587`, desmarque y use TLS.
     - **Puerto SMTP:** `465` (para SSL/TLS) o `587` (para STARTTLS).
     - **Sufijo de dirección de correo electrónico por defecto:** `@gmail.com` (opcional).
4. Marque la casilla **Probar configuración enviando un correo de prueba** (Test configuration by sending test e-mail).
5. Escriba su dirección de correo en **Dirección de correo de prueba** y presione el botón **Probar configuración**. Debe recibir un mensaje en su bandeja de entrada confirmando el éxito.
6. Guarde los cambios presionando **Aplicar** y **Guardar**.

---

## 3. Configuración del Job en Jenkins

Para crear el Job que ejecutará de forma automática el pipeline al detectar cambios:

1. En la página principal de Jenkins, haga clic en **Nueva Tarea** (New Item).
2. Ingrese el nombre de la tarea (ej. `Calculadora-Maven-Pipeline`).
3. Seleccione el tipo de proyecto **Pipeline** (o **Multibranch Pipeline**) y haga clic en **OK**.
4. En la configuración de la tarea, desplácese a la sección **Disparadores de ejecuciones** (Build Triggers):
   - Seleccione la casilla **Consultar el repositorio (Poll SCM)**.
   - En el cuadro de texto **Programación**, ingrese la expresión:
     ```cron
     H/1 * * * *
     ```
     *Esta expresión indica a Jenkins que consulte el repositorio de GitHub cada minuto en busca de nuevos commits. Esto cumple con el requisito de detección en menos de 1 minuto.*
5. En la sección **Pipeline**:
   - En **Definición** (Definition), seleccione **Pipeline script from SCM**.
   - En **SCM**, seleccione **Git**.
   - En **Repository URL**, introduzca la URL HTTPS de su repositorio de GitHub:
     `https://github.com/JackBS703/calculadora-maven.git`
   - En **Credentials**, seleccione sus credenciales de acceso a GitHub (en caso de ser un repositorio privado).
   - En **Branches to build**, configure la rama que desea compilar (ej. `*/main` o `*/develop`).
   - En **Script Path**, asegúrese de que esté escrito `Jenkinsfile` (coincidiendo con el archivo en la raíz).
6. Haga clic en **Guardar**.

---

## 4. Configuración del Servidor SonarQube en Jenkins

Para que la directiva `withSonarQubeEnv('SonarQube')` funcione correctamente, configure el servidor SonarQube en Jenkins:

1. Vaya a **Administrar Jenkins** -> **Configurar el Sistema** (Configure System).
2. Busque la sección **SonarQube servers**.
3. Haga clic en **Add SonarQube**.
4. Configure los campos:
   - **Name:** `SonarQube` (Debe ser idéntico al del Jenkinsfile).
   - **Server URL:** `http://localhost:9000` (o la URL de su servidor SonarQube).
   - **Server authentication token:** Añada un Jenkins credential de tipo *Secret Text* utilizando el token generado en SonarQube para el usuario admin.
5. Guarde la configuración.

---

## 5. Visualización y Uso en Blue Ocean

Jenkins Blue Ocean proporciona una interfaz moderna, limpia y gráfica para ver las ejecuciones:

1. En el menú de la izquierda de la tarea en Jenkins, haga clic en el botón **Open Blue Ocean** (Abrir Blue Ocean).
2. Verá una lista de las ejecuciones del pipeline. Haga clic en la ejecución más reciente para ver su progreso interactivo.
3. Blue Ocean representará el flujo de trabajo en forma de etapas secuenciales:
   - **Checkout** ➔ **Build** ➔ **SonarQube Analysis** ➔ **Quality Gate** ➔ **Package & Archive**.
4. **Interacción:**
   - Puede hacer clic en cualquier círculo de etapa para desplegar en tiempo real los logs detallados del proceso de Maven, pruebas unitarias y análisis de calidad.
   - En caso de fallo (marcado en rojo), haga clic en la etapa fallida para inspeccionar inmediatamente el error específico.
   - En la pestaña **Artifacts** en la esquina superior derecha, podrá descargar directamente el archivo `.jar` generado en caso de que la ejecución haya sido exitosa.
