pipeline {
    agent any

    tools {
        environment {
            // Default to headless Selenium runs
            SELENIUM_HEADLESS = 'true'
        }
                sh 'cat $WORKSPACE/scripts/wait-for-services.sh || echo "wait-for-services.sh yok"'
            }
            stage('Checkout') {
                steps {
                    checkout scm
                    sh 'ls -l $WORKSPACE/scripts/ || echo "scripts klasörü yok"'
                    sh 'cat $WORKSPACE/scripts/wait-for-services.sh || echo "wait-for-services.sh yok"'
                }
            }
        }

    stages {
        stage('Clean Docker & Workspace') {
            steps {
                // Tüm container, network, volume ve eski build kalıntılarını temizle
                sh 'cd $WORKSPACE && docker-compose -f docker-compose.ci.yml down --volumes --remove-orphans || true'
                sh 'docker system prune -af --volumes || true'
                sh 'mvn clean'
            }
        }
        stage('Checkout') {
            steps {
                checkout scm
                sh 'ls -l $WORKSPACE/scripts/ || echo "scripts klasörü yok"'
                sh 'cat $WORKSPACE/scripts/wait-for-services.sh || echo "wait-for-services.sh yok"'
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
        post {
            always {
                sh 'docker-compose -f docker-compose.ci.yml down --volumes --remove-orphans || true'
                sh 'docker system prune -af --volumes || true'
            }
        }
                        }
                }
                sh 'cd $WORKSPACE && docker-compose -f docker-compose.ci.yml up -d --force-recreate'
                // wait-for-services.sh dosyasının varlığını ve içeriğini kontrol et
                sh "cd $WORKSPACE && docker-compose -f docker-compose.ci.yml run --rm maven ls -l /workspace/scripts/wait-for-services.sh || echo 'wait-for-services.sh dosyası yok'"
                sh "cd $WORKSPACE && docker-compose -f docker-compose.ci.yml run --rm maven cat /workspace/scripts/wait-for-services.sh || echo 'wait-for-services.sh içeriği okunamadı'"
                // Wait for the application inside the compose network to be healthy by using the maven container
                sh "cd $WORKSPACE && docker-compose -f docker-compose.ci.yml run --rm maven bash -lc '/workspace/scripts/wait-for-services.sh http://app:8080/actuator/health http://selenium-hub:4444/status 120'"
            }
        }
                always {
                    junit 'target/surefire-reports/*.xml'
                                    stage('Checkout') {
                                        steps {
                                            checkout scm
                                            sh 'ls -l $WORKSPACE/scripts/ || echo "scripts klasörü yok"'
                                            sh 'cat $WORKSPACE/scripts/wait-for-services.sh || echo "wait-for-services.sh yok"'
                                        }
                                    }
                                    stage('Clean Docker & Workspace') {
                                        steps {
                                            // Tüm container, network, volume ve eski build kalıntılarını temizle
                                            sh 'cd $WORKSPACE && docker-compose -f docker-compose.ci.yml down --volumes --remove-orphans || true'
                                            sh 'docker system prune -af --volumes || true'
                                            sh 'mvn clean'
                                        }
                                    }

        stage('Selenium: Login') {
            steps {
                sh "cd $WORKSPACE && docker-compose -f docker-compose.ci.yml run --rm -e DB_HOST=postgres maven mvn -Dtest=GirisTesti test -Dselenium.remote.url=http://selenium-hub:4444/wd/hub -Dapp.baseUrl=http://app:8080 -Dselenium.headless=${SELENIUM_HEADLESS}"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
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
            sh 'docker system prune -af --volumes || true'
        }
    }
}
