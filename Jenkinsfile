pipeline {
    agent any
// Prueba Jenkins
    // Disparador para revisar cambios en el repositorio cada minuto (SCM Polling)
    triggers {
        pollSCM('H/1 * * * *')
    }

    // Definición de herramientas requeridas (deben estar configuradas en Jenkins)
    tools {
        maven 'maven' // Nombre configurado en Administrar Jenkins -> Global Tool Configuration
        jdk 'java'    // Nombre del JDK configurado en Jenkins (Java 17)
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Descargando código de GitHub...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compilando el proyecto y ejecutando pruebas unitarias...'
                script {
                    if (isUnix()) {
                        sh 'mvn clean compile test'
                    } else {
                        bat 'mvn clean compile test'
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Ejecutando análisis estático con SonarQube...'
                // 'SonarQube' debe coincidir con el nombre del servidor en la configuración global de Jenkins
                withSonarQubeEnv('SonarQube') {
                    script {
                        if (isUnix()) {
                            sh 'mvn sonar:sonar'
                        } else {
                            bat 'mvn sonar:sonar'
                        }
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo 'Esperando respuesta del Quality Gate de SonarQube...'
                timeout(time: 10, unit: 'MINUTES') {
                    script {
                        // Espera que SonarQube termine de procesar el reporte y devuelva el estado
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "El Pipeline falló debido a que no se superó el Quality Gate. Estado: ${qg.status}"
                        }
                        echo "Quality Gate superado con éxito! Estado: ${qg.status}"
                    }
                }
            }
        }

        stage('Package & Archive') {
            steps {
                echo 'Calidad de código aprobada. Empaquetando y archivando el artefacto...'
                script {
                    if (isUnix()) {
                        sh 'mvn package -DskipTests'
                    } else {
                        bat 'mvn package -DskipTests'
                    }
                }
                // Archivar el archivo .jar generado en la carpeta target
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        failure {
            echo 'El pipeline falló. Enviando notificación por correo SMTP...'
            // Envío de correo utilizando el servidor SMTP global configurado en Jenkins
            mail to: 'esteban.cano@example.com, yeimy.herrera@example.com, mateo.berrio@example.com, mariana.montoya@example.com',
                 subject: "Pipeline Fallido: ${currentBuild.fullDisplayName}",
                 body: """Atención Equipo,

El pipeline de CI/CD para el proyecto 'calculadora-maven' ha fallado en la ejecución del build o no ha superado las reglas de calidad (Quality Gate).

Detalles de la ejecución:
- Job: ${env.JOB_NAME}
- Build: #${env.BUILD_NUMBER}
- Estado: FAILURE
- URL del Build: ${env.BUILD_URL}

Por favor, revisen el log de consola del build en Jenkins y los resultados de SonarQube en http://localhost:9000 para solucionar los problemas de duplicación o errores blocker.

Atentamente,
Servidor de Integración Continua (Jenkins)
"""
        }
        always {
            echo 'Limpiando el espacio de trabajo...'
            cleanWs()
        }
    }
}
