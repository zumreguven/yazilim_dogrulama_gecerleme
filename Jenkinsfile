pipeline {
  agent any

  environment {
    PIPELINE_NAME = "Java Maven CI Pipeline"
    COMPOSE_FILE  = "docker-compose.ci.yml"
    // İstersen Maven cache için:
    // MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
  }

  options {
    timestamps()
    ansiColor('xterm')
  }

  stages {

    stage('Checkout') {
      steps {
        echo '========================================'
        echo '📥 [CHECKOUT] Aşaması başladı'
        echo "📌 Pipeline: ${PIPELINE_NAME}"
        echo '📥 Git deposu indiriliyor...'
        checkout scm
        echo '✅ [CHECKOUT] Kod başarıyla indirildi'
        echo '========================================'
      }
    }

    stage('Tool Kontrol') {
      steps {
        echo '========================================'
        echo '🧰 [TOOLS] Java ve Maven kontrol ediliyor...'
        sh '''
          set -e
          echo "➡️ java -version"
          java -version
          echo "➡️ mvn -v"
          mvn -v
        '''
        echo '✅ [TOOLS] Java/Maven hazır'
        echo '========================================'
      }
    }

    stage('Build') {
      steps {
        echo '========================================'
        echo '🔨 [BUILD] mvn clean package (test hariç) başlıyor...'
        sh '''
          set -e
          echo "➡️ mvn -B clean package -DskipTests"
          mvn -B clean package -DskipTests
        '''
        echo '✅ [BUILD] Build tamam'
        echo '========================================'
      }
    }

    stage('Unit Tests') {
      steps {
        echo '========================================'
        echo '🧪 [UNIT TEST] mvn test başlıyor...'
        sh '''
          set -e
          echo "➡️ mvn -B test"
          mvn -B test
        '''
        echo '📄 [UNIT TEST] JUnit raporları toplanıyor...'
        junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
        echo '✅ [UNIT TEST] Tamam'
        echo '========================================'
      }
    }

    stage('CI Compose Up') {
      steps {
        echo '========================================'
        echo '🐳 [DOCKER] CI servisleri ayağa kaldırılıyor...'
        sh '''
          set -e
          echo "➡️ docker compose -f ${COMPOSE_FILE} up -d --build"
          docker compose -f ${COMPOSE_FILE} up -d --build

          echo "➡️ docker compose ps"
          docker compose -f ${COMPOSE_FILE} ps
        '''
        echo '✅ [DOCKER] Servisler ayakta'
        echo '========================================'
      }
    }

    stage('Integration / UI Tests (opsiyonel)') {
      steps {
        echo '========================================'
        echo '🧩 [INTEGRATION] (Opsiyonel) integration-test / e2e varsa çalıştır'
        echo '⚠️ Projende failsafe / e2e yoksa bu stage’i silebilirsin.'
        sh '''
          set -e
          echo "➡️ (Opsiyonel) mvn -B verify -Pintegration"
          # Eğer integration profili yoksa komutu yorumda bırak:
          # mvn -B verify -Pintegration

          echo "✅ (Opsiyonel) Integration aşaması geçildi"
        '''
        echo '========================================'
      }
    }
  }

  post {
    success {
      echo '🎉 PIPELINE BAŞARILI'
    }
    failure {
      echo '❌ PIPELINE HATASI'
    }
    always {
      echo '========================================'
      echo '🧹 [CLEANUP] docker compose down + logları göster'
      sh '''
        set +e
        echo "➡️ docker compose logs (son 200 satır)"
        docker compose -f ${COMPOSE_FILE} logs --tail=200

        echo "➡️ docker compose down -v"
        docker compose -f ${COMPOSE_FILE} down -v
      '''
      echo "🏁 Bitti: ${new Date()}"
      echo '========================================='
    }
  }
}
