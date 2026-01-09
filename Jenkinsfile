pipeline {
    agent any

    stages {

        stage('Test API Connectivity') {
            steps {
                echo 'Testing FastAPI connectivity'
                bat 'curl http://localhost:8000/health'
            }
        }

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }
    }

    post {
        failure {
            echo 'Build failed. Sending logs to AI system'

            bat '''
            echo Collecting TestNG logs...

            IF EXIST target\\surefire-reports\\*.txt (
                type target\\surefire-reports\\*.txt > logs.txt
            ) ELSE (
                echo No TestNG logs found > logs.txt
            )

            curl -X POST http://localhost:8000/logs/ ^
              -H "Content-Type: text/plain" ^
              --data-binary @logs.txt
            '''
        }
    }
}
