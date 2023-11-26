package pw.pap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleDatabaseExample {

    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@//ora4.ii.pw.edu.pl:1521/pdb1.ii.pw.edu.pl";
        String username = "plenczew";
        String password = "plenczew";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT * FROM Projects";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String project_id = resultSet.getString("project_id");
                    String name = resultSet.getString("name");
                    String status = resultSet.getString("status");
                    String owner = resultSet.getString("owner");
                    String date_start = resultSet.getString("date_start");
                    String date_end = resultSet.getString("date_end");

                    System.out.println(project_id+", "+name+", "+status+", "+owner+", "+date_start+", "+date_end);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
