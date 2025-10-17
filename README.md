# 🚀 Automated Delivery Card Tests

[![Java Tests](https://github.com/aliyavoronkina/selenide/actions/workflows/gradle.yml/badge.svg)](https://github.com/aliyavoronkina/selenide/actions/workflows/gradle.yml)


## 📋 Описание проекта
Автоматизированные тесты для формы доставки карты с использованием Selenide и JUnit 5.

## 🛠 Технологии
- **Java 11**
- **Selenide** - для автоматизации браузера
- **JUnit 5** - для тестирования
- **Gradle** - для сборки
- **GitHub Actions** - для CI/CD

## 🧪 Тесты
Проект содержит 14 тестов:
- 3 позитивных сценария
- 11 негативных сценариев валидации

## 🚀 Запуск тестов

### Локально
```bash
# Запустить приложение
java -jar ./artifacts/app-card-delivery.jar &

# Запустить тесты
./gradlew clean test
