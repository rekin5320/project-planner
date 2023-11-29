package pw.pap;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "User")
public class User implements Serializable {
    @Id
    @Column (name = "UserID", unique = true)
    private int id;

    @Column (name = "Name", nullable = false)
    private String name;

    public int getId() {return id;}
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return id + "\t" + name;
    }
}

/*
-- Init database:
create database papdb;
create user myuser@'%' identified by 'passw'
GRANT ALL PRIVILEGES ON papdb.* TO 'myuser'@'%';

use papdb;
create table tasks_tmp (
    task_id INT primary key,
    name varchar(99) not null
);
show tables;
insert into tasks_tmp values (1, 'Pierwsze zadanie');
insert into tasks_tmp values (2, 'Drugie zadanie');
*/
