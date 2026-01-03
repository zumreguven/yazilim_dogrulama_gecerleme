# yazilim_dogrulama_gecerleme
Yazılım Doğrulama Projesi

## CI / Jenkins

Bu repo için hazırlanan Jenkins pipeline şu aşamaları içerir:

1. Checkout (GitHub)
2. Build (maven package)
3. Docker Compose ile servisleri ayağa kaldır (Postgres, App, Selenium Hub/Node)
4. Unit Testler (Surefire) — raporlar `target/surefire-reports/` içinde toplanır
5. Entegrasyon Testleri (Failsafe) — raporlar `target/failsafe-reports/` içinde toplanır
6. Selenium senaryoları ayrı stage'lerde çalıştırılır (`GirisTesti`, `IlanTesti`, vb.) ve surefire raporları yayınlanır

### Lokal Test ve CI ipuçları

- Lokal olarak test çalıştırmadan önce bir Postgres instance çalıştırın veya `DB_HOST` sistem değişkenini uygun şekilde ayarlayın:
  mvn -DDB_HOST=localhost -DskipITs=true test

- CI (Jenkins) için ajanınızın Docker ve Docker Compose çalıştırabildiğinden emin olun.
- Selenium Grid için kullanılan image'lar (Selenium hub / node) mimari uyumsuzluğu (Apple Silicon) gösterebilir; CI ajanınızda uygun platform (linux/amd64) veya multi-arch destekli görüntü kullanın.
- Not: Jenkins varsayılan olarak 8080 portunda çalışır; `docker-compose.ci.yml` dosyasında uygulama host portuna publish edilmez (port çatışmasını önlemek için). Geliştirme `docker-compose.yml` dosyasında ise uygulama 8082'ye yönlendirilmiştir, böylece yerel Jenkins kurulumu ile çakışma olmaz.

