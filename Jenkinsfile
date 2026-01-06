pipeline {
    agent any

    environment {
        // Maven'in kendi repository'sini kullanmasini saglar (hiz kazandirir)
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }

    stages {
        stage('Temizlik (Port/Container)') {
            steps {
                script {
                    // Onceki calismadan kalan container'lari ve volume'leri temizle
                    // "|| true" ifadesi, eger container yoksa hatayi yoksaymasini saglar
                    sh 'docker-compose -f docker-compose.ci.yml down -v || true'
                    sh 'docker system prune -af --volumes || true'
                }
            }
        }

        stage('Checkout') {
            steps {
                // Kodlari cek
                checkout scm
                sh 'echo JENKINS WORKSPACE: $WORKSPACE'
                sh 'pwd'
            }
        }

        stage('Build') {
            steps {
                // ONEMLI DUZELTME: -DskipTests parametresi eklendi.
                // Bu sayede build sirasinda testler calismaz (cunku DB henuz hazir degil).
                sh './mvnw clean install -DskipTests'
            }
        }

        stage('Start Docker Containers') {
            steps {
                // Test veritabanini ve Selenium'u ayaga kaldir
                sh 'docker-compose -f docker-compose.ci.yml up -d --build'
            }
        }

        stage('Wait for Services') {
                    steps {
                        // Servislerin tam olarak acilmasini bekle
                        sh 'chmod +x ./scripts/wait-for-services.sh'

                        // Uygulamaya uyanmasi icin 60 saniye ekstra sure taniyalim
                        sh 'sleep 60'

                        sh './scripts/wait-for-services.sh'
                    }
        }

        stage('Unit Tests') {
            steps {
                // Sadece Birim Testlerini calistir
                sh './mvnw test -Dtest=*UnitTest,*ValidationTest'
            }
        }

        stage('Integration Tests') {
            steps {
                // Entegrasyon Testlerini calistir
                sh './mvnw test -Dtest=*IntegrationTest'
            }
        }

        // Selenium Testleri
        stage('Selenium Tests') {
            steps {
                // Tum Selenium testlerini calistir
                // Eger tek tek stage yapmak isterseniz burayi ayirabilirsiniz
                sh './mvnw test -Dtest=*Selenium*,*SmokeTest'
            }
        }
    }

    post {
        always {
            // Islem bitince veya hata alinca ortamı temizle
            sh 'docker-compose -f docker-compose.ci.yml down -v'
            junit '**/target/surefire-reports/*.xml'
        }
    }
}