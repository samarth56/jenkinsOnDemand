stage('Test API Connectivity') {
    steps {
        sh '''
        echo "Testing FastAPI connectivity..."
        curl -v http://localhost:8000/health
        '''
    }
}
