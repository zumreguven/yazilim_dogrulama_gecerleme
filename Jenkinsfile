pipeline {
    agent any
    options {
        timeout(time: 30, unit: 'MINUTES')
    }
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
                sh './mvnw clean install'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test -Dgroups=unit -Dselenium.remote.url=http://selenium-hub:4444/wd/hub || true'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Integration Tests') {
            steps {
                sh 'mvn verify -Dgroups=integration -Dselenium.remote.url=http://selenium-hub:4444/wd/hub || true'
                junit '**/target/failsafe-reports/*.xml'
            }
        }
        stage('Start Docker Containers') {
            steps {
                sh 'docker-compose up -d'
            }
        }
        stage('Wait for Services') {
            steps {
                sh 'bash scripts/wait-for-services.sh http://app:8080/actuator/health http://selenium-hub:4444/status 30'
            }
        }
        stage('Selenium - CareerGoalTest') {
            environment {
                APP_BASE_URL = 'http://app:8080'
                SELENIUM_REMOTE_URL = 'http://selenium-hub:4444/wd/hub'
            }
            steps {
                sh './mvnw test -Dtest=com.example.careermanagement.selenium.CareerGoalTest -Dselenium.remote.url=$SELENIUM_REMOTE_URL'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Selenium - InvalidLoginTest') {
            environment {
                APP_BASE_URL = 'http://app:8080'
                SELENIUM_REMOTE_URL = 'http://selenium-hub:4444/wd/hub'
            }
            steps {
                sh './mvnw test -Dtest=com.example.careermanagement.selenium.InvalidLoginTest -Dselenium.remote.url=$SELENIUM_REMOTE_URL'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Selenium - LogoutTest') {
            environment {
                APP_BASE_URL = 'http://app:8080'
                SELENIUM_REMOTE_URL = 'http://selenium-hub:4444/wd/hub'
            }
            steps {
                sh './mvnw test -Dtest=com.example.careermanagement.selenium.LogoutTest -Dselenium.remote.url=$SELENIUM_REMOTE_URL'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Selenium - IlanTesti') {
            environment {
                APP_BASE_URL = 'http://app:8080'
                SELENIUM_REMOTE_URL = 'http://selenium-hub:4444/wd/hub'
            }
            steps {
                sh './mvnw test -Dtest=com.example.careermanagement.selenium.IlanTesti -Dselenium.remote.url=$SELENIUM_REMOTE_URL'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Selenium - PanelAccessTest') {
            environment {
                APP_BASE_URL = 'http://app:8080'
                SELENIUM_REMOTE_URL = 'http://selenium-hub:4444/wd/hub'
            }
            steps {
                sh './mvnw test -Dtest=com.example.careermanagement.selenium.PanelAccessTest -Dselenium.remote.url=$SELENIUM_REMOTE_URL'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Selenium - GirisTesti') {
            environment {
                APP_BASE_URL = 'http://app:8080'
                SELENIUM_REMOTE_URL = 'http://selenium-hub:4444/wd/hub'
            }
            steps {
                sh './mvnw test -Dtest=com.example.careermanagement.selenium.GirisTesti -Dselenium.remote.url=$SELENIUM_REMOTE_URL'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Selenium - SmokeTest') {
            environment {
                APP_BASE_URL = 'http://app:8080'
                SELENIUM_REMOTE_URL = 'http://selenium-hub:4444/wd/hub'
            }
            steps {
                sh './mvnw test -Dtest=com.example.careermanagement.selenium.SmokeTest -Dselenium.remote.url=$SELENIUM_REMOTE_URL'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Selenium - SearchJobTest') {
            environment {
                APP_BASE_URL = 'http://app:8080'
                SELENIUM_REMOTE_URL = 'http://selenium-hub:4444/wd/hub'
            }
            steps {
                sh './mvnw test -Dtest=com.example.careermanagement.selenium.SearchJobTest -Dselenium.remote.url=$SELENIUM_REMOTE_URL'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Selenium - AccessWithoutLoginRedirectTest') {
            environment {
                APP_BASE_URL = 'http://app:8080'
                SELENIUM_REMOTE_URL = 'http://selenium-hub:4444/wd/hub'
            }
            steps {
                sh './mvnw test -Dtest=com.example.careermanagement.selenium.AccessWithoutLoginRedirectTest -Dselenium.remote.url=$SELENIUM_REMOTE_URL'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Selenium - HomePageTest') {
            environment {
                APP_BASE_URL = 'http://app:8080'
                SELENIUM_REMOTE_URL = 'http://selenium-hub:4444/wd/hub'
            }
            steps {
                sh './mvnw test -Dtest=com.example.careermanagement.selenium.HomePageTest -Dselenium.remote.url=$SELENIUM_REMOTE_URL'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        // Python ile yazılmış senaryoları da çalıştırmak isterseniz aşağıdaki gibi bırakabilirsiniz
        stage('Selenium Test Scenario 1') {
            steps {
                sh 'python scripts/selenium_test1.py'
            }
        }
        stage('Selenium Test Scenario 2') {
            steps {
                sh 'python scripts/selenium_test2.py'
            }
        }
        stage('Selenium Test Scenario 3') {
            steps {
                sh 'python scripts/selenium_test3.py'
            }
        }
        stage('Selenium Test Scenario 4') {
            steps {
                sh 'python scripts/selenium_test4.py'
            }
        }
        stage('Selenium Test Scenario 5') {
            steps {
                sh 'python scripts/selenium_test5.py'
            }
        }
        stage('Selenium Test Scenario 6') {
            steps {
                sh 'python scripts/selenium_test6.py'
            }
        }
        stage('Selenium Test Scenario 7') {
            steps {
                sh 'python scripts/selenium_test7.py'
            }
        }
        stage('Selenium Test Scenario 8') {
            steps {
                sh 'python scripts/selenium_test8.py'
            }
        }
        stage('Selenium Test Scenario 9') {
            steps {
                sh 'python scripts/selenium_test9.py'
            }
        }
        stage('Selenium Test Scenario 10') {
            steps {
                sh 'python scripts/selenium_test10.py'
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