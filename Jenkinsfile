pipeline {
    agent any
    options {
        timeout(time: 30, unit: 'MINUTES')
    stages {
        stage('Temizlik (Port/Container)') {
            steps {
                sh 'docker compose -f docker-compose.ci.yml down -v || true'
                sh 'docker system prune -af --volumes || true'
            }
        }
        stage('Checkout') {
            steps {
                checkout scm
                sh 'echo "JENKINS WORKSPACE: $WORKSPACE"'
                sh 'pwd'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test -Dgroups=unit || true'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Integration Tests') {
            steps {
                sh 'mvn verify -Dgroups=integration || true'
                junit '**/target/failsafe-reports/*.xml'
            }
        }
        stage('Docker Up') {
            steps {
                sh 'docker compose -f docker-compose.ci.yml up -d --build'
            }
        }
        stage('Wait for Services') {
            steps {
                sh 'bash scripts/wait-for-services.sh http://app:8080/actuator/health http://selenium-hub:4444/status 30'
            }
        }
        stage('Selenium Test: GirisTesti') {
            steps {
                sh 'mvn -Dtest=GirisTesti test -Pselenium'
                junit '**/target/selenium-reports/*.xml'
            }
        }
        stage('Selenium Test: LogoutTest') {
            steps {
                sh 'mvn -Dtest=LogoutTest test -Pselenium'
                junit '**/target/selenium-reports/*.xml'
            }
        }
        stage('Selenium Test: SearchJobTest') {
            steps {
                sh 'mvn -Dtest=SearchJobTest test -Pselenium'
                junit '**/target/selenium-reports/*.xml'
            }
        }
    }
    post {
        always {
            sh 'docker-compose -f docker-compose.ci.yml down --volumes --remove-orphans || true'
            sh 'docker system prune -af --volumes || true'
        }
    }
}
        always {
            sh 'docker-compose -f docker-compose.ci.yml down --volumes --remove-orphans || true'
            sh 'docker system prune -af --volumes || true'
        }
    }
}



