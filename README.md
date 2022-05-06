
### Задание 2 (Автоматизация API)

#### Требование к среде для запуска тестов

- Установлен JDK 1.8 и выше. 
- Установлен Maven 3.8 и выше, команда `mvn` доступна из командной строки.  
- Установлен Allure commandline от версии 2.17 и команда `allure` доступна из командной строки.
  [Инструкция к установке](https://docs.qameta.io/allure/#_installing_a_commandline)


#### Запуск тестов

1. Получить API Key на сайте <https://thecatapi.com>
2. Скачать и распакавать проект из репозитария на ваш ПК в отделюную директорию. 
3. Для Windows, открыть Command Prompt и перейти в корень проекта - директория .\TheCatApiTests
4. Выполнить комманду 
 `mvn clean test -Dapi_key=<ваш_API_Key>` чтобы выполнить тесты.

#### Создать отчет о запуске тестов

5. Выполнить последовательно команды <br>
   `allure generate --clean --report-dir ./target/allure-report  ./target/allure-results`  
   `allure open --host localhost --port 8899 ./target/allure-report`
6. Смотреть Allure отчет о результатах тестового прогона в открывшемся окне веб-браузера.

