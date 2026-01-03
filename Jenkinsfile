pipeline {
    agent any

    tools {
        maven 'Maven 3.9.9'
    }

    environment {
        // Default to headless Selenium runs
        SELENIUM_HEADLESS = 'true'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests=true clean package'
            }
        }

        stage('Start Services (Docker Compose)') {
            steps {
                sh 'docker-compose -f docker-compose.ci.yml up -d --build'
                // Wait for the application inside the compose network to be healthy by using the maven container
                sh "docker-compose -f docker-compose.ci.yml run --rm maven bash -lc '/workspace/scripts/wait-for-services.sh http://app:8080/actuator/health http://selenium-hub:4444/status 120'"
            }
        }

        stage('Unit Tests') {
            steps {
                // Run unit tests inside the maven container (so reports are written to host volume)
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -B -DskipITs=true test -Dselenium.headless=${SELENIUM_HEADLESS} -Dapp.baseUrl=http://app:8080 -Dselenium.remote.url=http://selenium-hub:4444/wd/hub"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Integration Tests') {
            steps {
                // Run integration tests (failsafe) inside maven container
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -B -DskipTests=true verify -Dselenium.headless=${SELENIUM_HEADLESS} -Dapp.baseUrl=http://app:8080 -Dselenium.remote.url=http://selenium-hub:4444/wd/hub"
            }
            post {
                always {
                    junit 'target/failsafe-reports/*.xml'
                }
            }
        }

        stage('Selenium: Login') {
            steps {
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -Dtest=GirisTesti test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=${SELENIUM_HEADLESS}"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Selenium: Create Ad') {
            steps {
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -Dtest=IlanTesti test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=${SELENIUM_HEADLESS}"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Selenium: Home Page') {
            steps {
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -Dtest=HomePageTest test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=${SELENIUM_HEADLESS}"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Selenium: Career Goal') {
            steps {
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -Dtest=CareerGoalTest test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=${SELENIUM_HEADLESS}"
            }
            post {
                always { junit 'target/surefire-reports/*.xml' }
            }
        }

        stage('Selenium: Logout') {
            steps {
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -Dtest=LogoutTest test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=${SELENIUM_HEADLESS}"
            }
            post {
                always { junit 'target/surefire-reports/*.xml' }
            }
        }

        stage('Selenium: Invalid Login') {
            steps {
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -Dtest=InvalidLoginTest test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=${SELENIUM_HEADLESS}"
            }
            post {
                always { junit 'target/surefire-reports/*.xml' }
            }
        }

        stage('Selenium: Search Job') {
            steps {
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -Dtest=SearchJobTest test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=${SELENIUM_HEADLESS}"
            }
            post {
                always { junit 'target/surefire-reports/*.xml' }
            }
        }

        stage('Selenium: Smoke') {
            steps {
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -Dtest=SmokeTest test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=${SELENIUM_HEADLESS}"
            }
            post {
                always { junit 'target/surefire-reports/*.xml' }
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh "docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -B allure:report -Dselenium.headless=${SELENIUM_HEADLESS} -Dapp.baseUrl=http://app:8080 -Dselenium.remote.url=http://selenium-hub:4444/wd/hub"
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/site/allure-maven-plugin/**', allowEmptyArchive: true
                }
            }
        }

        stage('Publish Reports') {
            steps {
                junit 'target/surefire-reports/*.xml'
                archiveArtifacts artifacts: 'target/selenium-screenshots/**/*, target/site/allure-maven-plugin/**/*', allowEmptyArchive: true
            }
        }

        // Add more Selenium stages here (max up to 10) if desired
    }

    post {
        always {
            sh 'docker-compose -f docker-compose.ci.yml down --volumes --remove-orphans || true'
        }
    }
}
