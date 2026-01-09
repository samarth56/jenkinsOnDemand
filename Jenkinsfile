pipeline {
    agent any

    environment {
        LOG_API_URL = "http://localhost:8000/logs"
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "Checking out code from GitHub..."
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running Maven tests..."
                sh 'mvn test'
            }
        }
    }

    post {

        failure {
            echo "Build failed. Sending logs to GenAI system..."

            sh '''
            echo "Collecting test logs..."
            LOGS=$(cat target/surefire-reports/*.txt | tail -n 300)

            curl -X POST $LOG_API_URL \
            -H "Content-Type: application/json" \
            -d "{
              \\"test_name\\": \\"Jenkins Build #${BUILD_NUMBER}\\",
              \\"raw_log\\": \\"$LOGS\\"
            }"
            '''
        }

        success {
            echo "Build successful. No logs sent."
        }

        always {
            echo "Pipeline completed."
        }
    }
}
