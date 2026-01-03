pipeline {
    agent any

    tools {
        maven 'Maven 3.9.9'
    }

    environment {
        SELENIUM_HEADLESS = 'true'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                sh 'ls -l $WORKSPACE/scripts/ || echo "scripts klasörü yok"'
                sh 'cat $WORKSPACE/scripts/wait-for-services.sh || echo "wait-for-services.sh yok"'
            }
        }
        stage('Clean Docker & Workspace') {
            steps {
                sh 'cd $WORKSPACE && docker-compose -f docker-compose.ci.yml down --volumes --remove-orphans || true'
                sh 'docker system prune -af --volumes || true'
                sh 'mvn clean'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests=true clean package'
            }
        }
        stage('Start Services (Docker Compose)') {
            steps {
                retry(3) {
                    sh 'cd $WORKSPACE && docker-compose -f docker-compose.ci.yml build --no-cache'
                }
                sh 'cd $WORKSPACE && docker-compose -f docker-compose.ci.yml up -d --force-recreate'
                sh "cd $WORKSPACE && docker-compose -f docker-compose.ci.yml run --rm maven ls -l /workspace/scripts/wait-for-services.sh || echo 'wait-for-services.sh dosyası yok'"
                sh "cd $WORKSPACE && docker-compose -f docker-compose.ci.yml run --rm maven cat /workspace/scripts/wait-for-services.sh || echo 'wait-for-services.sh içeriği okunamadı'"
                sh "cd $WORKSPACE && docker-compose -f docker-compose.ci.yml run --rm maven bash -lc '/workspace/scripts/wait-for-services.sh http://app:8080/actuator/health http://selenium-hub:4444/status 120'"
            }
        }
        stage('Wait for Services') {
            steps {
                sh 'bash scripts/wait-for-services.sh http://app:8080/actuator/health http://selenium-hub:4444/status 30'
            }
        }
        // Diğer Selenium ve rapor aşamalarını buraya ekleyebilirsin
    }

    post {
        always {
            sh 'docker-compose -f docker-compose.ci.yml down --volumes --remove-orphans || true'
            sh 'docker system prune -af --volumes || true'
        }
    }
}
