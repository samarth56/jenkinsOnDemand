pipeline {
    agent any

    stages {

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Test API Connectivity') {
            steps {
                echo "Testing FastAPI connectivity"
                bat 'curl http://localhost:8000/health'
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
            echo "Build failed. Collecting logs and sending to AI..."

            // Collect TestNG logs
            bat '''
            echo Collecting TestNG logs...
            IF EXIST target\\surefire-reports\\*.txt (
                type target\\surefire-reports\\*.txt > logs.txt
            ) ELSE (
                echo No TestNG logs found > logs.txt
            )
            '''

            // Send logs to FastAPI AI analyzer
            bat '''
            echo Sending logs to AI system...
            curl -X POST http://localhost:8000/logs/ ^
                 -H "Content-Type: text/plain" ^
                 --data-binary @logs.txt ^
                 > ai_response.json
            '''

            // Pretty-print AI response in Jenkins console
            powershell '''
            Write-Host ""
            Write-Host "================ AI ANALYSIS ================" -ForegroundColor Cyan

            $json = Get-Content ai_response.json | ConvertFrom-Json

            Write-Host ""
            Write-Host "ROOT CAUSE:" -ForegroundColor Yellow
            Write-Host $json.root_cause -ForegroundColor Red

            Write-Host ""
            Write-Host "SOLUTION:" -ForegroundColor Yellow
            Write-Host $json.solution -ForegroundColor Green

            Write-Host "============================================" -ForegroundColor Cyan
            '''
        }
    }
}
