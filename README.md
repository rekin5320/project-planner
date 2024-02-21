# Projekt PAP – ProjectPlanner
![](dokumentacja/images/improvements1.png)

![](dokumentacja/images/improvements3.png)

## Założenia
### Temat
Aplikacja służąca do planowania zadań dla pracowników

### Technologie
Frontend: interfejs webowy (React)

Backend: Java (Spring + REST)

Baza danych: MySQL

### Funkcjonalności
Podstawowe funkcjonalności:

- wyświetlanie, dodawanie, edytowanie, usuwanie użytkowników, projektów i zadań
- dodawanie użytkowników do projektów i przydzielanie im zadań
- ustawianie opisów projektów i zadań
- odznaczanie wykonanych zadań

Dodatkowe funkcjonalności:

- możliwość zalogowania się kontem Google

## Członkowie zespołu
- Piotr Lenczewski
- Michał Machnikowski
- Jakub Pęk
- Tomasz Truszkowski

## How to use it?
### MySQL database
#### Set environment variables – credentials
```sh
export DB_HOST=… DB_PORT=… DB_NAME=… DB_USER=… DB_PASSWORD=…
```

#### Testing connection
```sh
pip install mysql-connector-python
```

```sh
python src/check_db_connection.py
```

### Spring backend
#### Build
```sh
mvn package
```

#### Run
```sh
java -jar target/pap-app-1.0.0.jar
```

API is available at <http://localhost:8080/api/>

### React frontend
#### Install dependencies
```sh
cd src/my-react-frontend
npm install
```

#### Run
```sh
cd src/my-react-frontend
npm start
```

Open <http://localhost:3000/>

