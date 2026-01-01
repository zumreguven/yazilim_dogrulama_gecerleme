pipeline {
    agent any

    tools {
        maven 'Maven 3.9.9'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '1. Checking out code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '2. Building...'
                sh 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                echo '3. Running unit tests...'
                sh 'mvn test'
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
        }
    }
}