#!/usr/bin/env python3

import mysql.connector


credentials = {
    "host": "rekin.ddns.net",
    "port": 30306,
    "user": "myuser",
    "password": "2L9(4Evz,9",
    "database": "papdb",
}

with mysql.connector.connect(**credentials) as connection, connection.cursor() as cursor:
    cursor.execute("SELECT * FROM users;")
    for row in cursor.fetchall():
        print(row)
