# Projekt PAP
## Wstępne założenia
### Temat
Aplikacja służąca do planowania zadań dla pracowników

### Technologie
Frontend: interfejs webowy – React

Backend: Java (prawdopodobnie Spring + REST)

Baza danych: prawdopodobnie Oracle

### Funkcjonalności
Podstawowe funkcjonalności:

- wyświetlanie, dodawanie, edytowanie, usuwanie zadań
- przydzielanie zadań pracownikom

Dodatkowe funkcjonalności, na przykład:

- zintegrowanie się z kalendarzem Google
- możliwość zalogowania się Facebookiem

## How to use it
### Building
```sh
mvn package
```

### Running
```sh
java -cp target/pap-app-0.0.0.jar pw.pap.Main
```

### Testing
```sh
mvn test
```

