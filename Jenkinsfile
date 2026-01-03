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
                sh 'echo "JENKINS WORKSPACE: $WORKSPACE"'
                sh 'pwd'
                sh 'ls -l $WORKSPACE/scripts/ || echo "$WORKSPACE/scripts klasörü yok"'
                sh 'ls -l scripts/ || echo "scripts klasörü yok"'
                script {
                    def workspacePath = readFile('workspace_path.txt').trim()
                    sh "sed -i 's|/var/jenkins_home/workspace/kariyer/scripts|${workspacePath}/scripts|g' docker-compose.ci.yml"
                }
                sh 'echo "JENKINS_WORKSPACE=$WORKSPACE" > .env'
                sh 'cat $WORKSPACE/scripts/wait-for-services.sh || echo "$WORKSPACE/scripts/wait-for-services.sh yok"'
                sh 'cat scripts/wait-for-services.sh || echo "scripts/wait-for-services.sh yok"'
                sh 'ls -l scripts/ || echo "scripts klasörü yok"'
                sh 'cat scripts/wait-for-services.sh || echo "wait-for-services.sh yok"'
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
            }
        }
        stage('Selenium Test: LogoutTest') {
            steps {
                sh 'mvn -Dtest=LogoutTest test -Pselenium'
            }
        }
        stage('Selenium Test: SearchJobTest') {
            steps {
                sh 'mvn -Dtest=SearchJobTest test -Pselenium'
            }
        }
        stage('Selenium Test: RegisterTest') {
            steps {
                sh 'mvn -Dtest=RegisterTest test -Pselenium'
            }
        }
        stage('Selenium Test: ProfileTest') {
            steps {
                sh 'mvn -Dtest=ProfileTest test -Pselenium'
            }
        }
        stage('Selenium Test: NotificationTest') {
            steps {
                sh 'mvn -Dtest=NotificationTest test -Pselenium'
            }
        }
        stage('Selenium Test: SettingsTest') {
            steps {
                sh 'mvn -Dtest=SettingsTest test -Pselenium'
            }
        }
        stage('Selenium Test: MessageTest') {
            steps {
                sh 'mvn -Dtest=MessageTest test -Pselenium'
            }
        }
        stage('Selenium Test: ApplicationTest') {
            steps {
                sh 'mvn -Dtest=ApplicationTest test -Pselenium'
            }
        }
        stage('Selenium Test: AdminTest') {
            steps {
                sh 'mvn -Dtest=AdminTest test -Pselenium'
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
