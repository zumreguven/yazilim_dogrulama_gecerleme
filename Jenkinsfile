pipeline {
    agent any

    environment {
        // Maven repository ayari (Gerekirse)
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }

    stages {
        stage('Temizlik (Port/Container)') {
            steps {
                script {
                    // Sadece konteynerleri durduruyoruz, komple her seyi silmiyoruz!
                    // Boylece indirme 40 dakika surmeyecek.
                    sh 'docker-compose -f docker-compose.ci.yml down -v || true'
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Start Docker Containers') {
            steps {
                // Ortami ayaga kaldir (Build onceden yapilacak)
                sh 'docker-compose -f docker-compose.ci.yml up -d --build'
            }
        }

        stage('Wait for Services') {
            steps {
                script {
                    // Veritabanı ve Selenium'un tam acilmasi icin bekliyoruz
                    echo "Sistem hazirlaniyor, 3 dakika bekleniyor..."
                    sh 'sleep 180' 
                    echo "Bekleme tamamlandi!"
                }
            }
        }

        stage('Unit Tests') {
            steps {
                // DIKKAT: Testi disarida degil, 'maven' konteynerinin ICINDE calistiriyoruz.
                // -T parametresi Jenkins icin gereklidir.
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=*UnitTest,*ValidationTest,*DateCalculatorTest'
            }
        }

        // --- SELENIUM TEST SENARYOLARI (HEPSI KONTEYNER ICINDE CALISACAK) ---

        stage('Selenium 1: Smoke Test') {
            steps {
                // Ag hatasi almamak icin exec kullaniyoruz
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=SmokeTest'
            }
        }

        stage('Selenium 2: Giris Testi') {
            steps {
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=GirisTesti'
            }
        }

        stage('Selenium 3: Hatali Giris') {
            steps {
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=InvalidLoginTest'
            }
        }

        stage('Selenium 4: Ana Sayfa') {
            steps {
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=HomePageTest'
            }
        }

        stage('Selenium 5: Ilan Testi') {
            steps {
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=IlanTesti'
            }
        }

        stage('Selenium 6: Is Arama') {
            steps {
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=SearchJobTest'
            }
        }

        stage('Selenium 7: Kariyer Hedefi') {
            steps {
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=CareerGoalTest'
            }
        }

        stage('Selenium 8: Panel Erişimi') {
            steps {
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=PanelAccessTest'
            }
        }
        
        stage('Selenium 9: Girissiz Erisim Engeli') {
            steps {
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=AccessWithoutLoginRedirectTest'
            }
        }

        stage('Selenium 10: Cikis Islemi') {
            steps {
                sh 'docker-compose -f docker-compose.ci.yml exec -T maven mvn test -Dtest=LogoutTest'
            }
        }
    }

    post {
        always {
            // Test sonuclarini kaydet
            // Not: Raporlar konteyner icinde olustugu icin once disari kopyalamak gerekebilir
            // Ama simdilik hata gormemek icin dogrudan calistiriyoruz.
            
            // Konteynerleri kapat (Siteyi incelemek istersen bu satiri // ile yorum yap)
             sh 'docker-compose -f docker-compose.ci.yml down -v'
        }
    }
}