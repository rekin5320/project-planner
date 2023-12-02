package pw.pap;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
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
