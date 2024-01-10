# Projekt PAP
## Wstępne założenia
### Temat
Aplikacja służąca do planowania zadań dla pracowników

### Technologie
Frontend: interfejs webowy (React)

Backend: Java (Spring + REST)

Baza danych: MySQL

### Funkcjonalności
Podstawowe funkcjonalności:

- wyświetlanie, dodawanie, edytowanie, usuwanie użytkowników i zadań
- przydzielanie zadań pracownikom

Dodatkowe funkcjonalności, na przykład:

- zintegrowanie się z kalendarzem Google
- możliwość zalogowania się Facebookiem

## Członkowie zespołu
- Piotr Lenczewski
- Michał Machnikowski
- Jakub Pęk
- Tomasz Truszkowski

## How to use it
### MySQL database
#### Credentials
<details>
<p><summary>Click to expand</summary></p>

address: `rekin.ddns.net:30306`

database name: `papdb`

jdbc: `jdbc:mysql://rekin.ddns.net:30306/papdb`

user: `myuser`

password: `2L9(4Evz,9`

</details>

#### Testing connection
```sh
pip install mysql-connector-python
cd src
python check_db_connection.py
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

#### Usage
Open `http://localhost:8080/api/projects/all`

### React frontend
#### Install dependencies
```sh
npm install
```

#### Run
```sh
npm start
```

#### Usage
Open `http://localhost:3000/`

