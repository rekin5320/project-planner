# Projekt PAP
## Wstępne założenia
### Temat
Aplikacja służąca do planowania zadań dla pracowników

### Technologie
Frontend: interfejs webowy – React

Backend: Java (prawdopodobnie Spring + REST)

Baza danych: MySQL

### Funkcjonalności
Podstawowe funkcjonalności:

- wyświetlanie, dodawanie, edytowanie, usuwanie zadań
- przydzielanie zadań pracownikom

Dodatkowe funkcjonalności, na przykład:

- zintegrowanie się z kalendarzem Google
- możliwość zalogowania się Facebookiem

## How to use it
### MySQL database config
address: `rekin.ddns.net:30306`

database name: `papdb`

jdbc: `jdbc:mysql://rekin.ddns.net:30306/papdb`

user: `myuser`

password: `2L9(4Evz,9`

### Java backend
#### Build
```sh
mvn compile
```

#### Test
```sh
mvn test
```

#### Build & Test & Package at once
```sh
mvn package
```

#### Run
```sh
java -cp target/pap-app-0.0.0.jar pw.pap.Main
java -cp target/pap-app-0.0.0.jar pw.pap.DatabaseExample
```

