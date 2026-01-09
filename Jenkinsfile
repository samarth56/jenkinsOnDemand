pipeline {
    agent any

    environment {
        LOG_API_URL = "http://localhost:8000/logs"
    }

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
            echo Collecting logs...
            type target\\surefire-reports\\*.txt > logs.txt

            curl -X POST %LOG_API_URL% ^
            -H "Content-Type: application/json" ^
            -d "{ \\"test_name\\": \\"Jenkins Build #%BUILD_NUMBER%\\", \\"raw_log\\": \\"Test failure from Jenkins\\" }"
            '''
        }
    }
}
