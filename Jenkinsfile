pipeline {
    agent any

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }

    stages {
        stage('Temizlik (Port/Container)') {
            steps {
                script {
                    sh 'docker-compose -f docker-compose.ci.yml down -v || true'
                    sh 'docker system prune -af --volumes || true'
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Kodlari derle ama testleri simdilik atla
                sh './mvnw clean install -DskipTests'
            }
        }

        stage('Start Docker Containers') {
            steps {
                // Ortami ayaga kaldir
                sh 'docker-compose -f docker-compose.ci.yml up -d --build'
            }
        }

        stage('Wait for Services') {
            steps {
                script {
                    echo "Sistem hazirlaniyor, 3 dakika bekleniyor..."
                    sh 'sleep 180' 
                    echo "Bekleme tamamlandi!"
                }
            }
        }

        stage('Unit Tests') {
            steps {
                // Sadece Unit Testleri calistir
                sh './mvnw test -Dtest=*UnitTest,*ValidationTest,*DateCalculatorTest'
            }
        }

        // --- SELENIUM TEST SENARYOLARI (BONUSLAR İÇİN AYRI STAGE'LER) ---

        stage('Selenium 1: Smoke Test') {
            steps {
                // Uygulama aciliyor mu kontrolu
                sh './mvnw test -Dtest=SmokeTest'
            }
        }

        stage('Selenium 2: Giris Testi') {
            steps {
                // Gecerli giris yapilabiliyor mu
                sh './mvnw test -Dtest=GirisTesti'
            }
        }

        stage('Selenium 3: Hatali Giris') {
            steps {
                // Yanlis sifre kontrolu
                sh './mvnw test -Dtest=InvalidLoginTest'
            }
        }

        stage('Selenium 4: Ana Sayfa') {
            steps {
                // Ana sayfa elementleri yukleniyor mu
                sh './mvnw test -Dtest=HomePageTest'
            }
        }

        stage('Selenium 5: Ilan Testi') {
            steps {
                // Yeni ilan ekleme veya goruntuleme
                sh './mvnw test -Dtest=IlanTesti'
            }
        }

        stage('Selenium 6: Is Arama') {
            steps {
                // Arama fonksiyonu
                sh './mvnw test -Dtest=SearchJobTest'
            }
        }

        stage('Selenium 7: Kariyer Hedefi') {
            steps {
                // Kariyer hedefi ekleme
                sh './mvnw test -Dtest=CareerGoalTest'
            }
        }

        stage('Selenium 8: Panel Erişimi') {
            steps {
                // Panele giris sonrasi erisim
                sh './mvnw test -Dtest=PanelAccessTest'
            }
        }
        
        stage('Selenium 9: Girissiz Erisim Engeli') {
            steps {
                // Login olmadan panele girmeye calisma
                sh './mvnw test -Dtest=AccessWithoutLoginRedirectTest'
            }
        }

        stage('Selenium 10: Cikis Islemi') {
            steps {
                // Logout fonksiyonu
                sh './mvnw test -Dtest=LogoutTest'
            }
        }
    }

    post {
        always {
            // Test sonuclarini kaydet (Hocanin istedigi raporlama)
            junit '**/target/surefire-reports/*.xml'
            
            // Ortami temizle
            sh 'docker-compose -f docker-compose.ci.yml down -v'
        }
    }
}