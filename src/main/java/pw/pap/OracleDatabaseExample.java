package pw.pap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleDatabaseExample {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@//localhost:1521/FREEPDB1";
        String username = "admin";
        String password = "2L9(4Evz,9";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM tasks_tmp";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String task_id = resultSet.getString("task_id");
                    String name = resultSet.getString("name");

                    System.out.println(task_id + " " + name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/* create table tasks_tmp (
    task_id NUMBER(4) constraint task_pk primary key,
    name varchar2(50) not null
);

insert into tasks_tmp values (1, 'Pierwsze zadanie');
insert into tasks_tmp values (2, 'Drugie zadanie'); */

