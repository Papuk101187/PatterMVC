package org.example.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.User;

import java.io.IOException;
import java.sql.*;

public class UserService {

    String URL = "jdbc:postgresql://localhost:5432/application_base";
    String USER = "postgres";
    String PASS = "10n11m87g";


    public boolean checkUser(User user) throws SQLException, IOException {

        String usersname = user.getFio();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            String sql = "SELECT * FROM users;";

            PreparedStatement preparedStatement2 = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement2.executeQuery();
            while (resultSet.next()) {
                if (user.getFio().equals(resultSet.getString("fio_user"))
                        && user.getPassword().equals(resultSet.getString("login_password"))) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException es) {
            es.printStackTrace();
        }
        return false;
    }

    public void addUser(User user) throws SQLException {


        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {

            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            String sql = "INSERT INTO users (FIO_user,login_password,date_born_user) " +
                    "VALUES (?,?,?)";

            PreparedStatement preparedStatement2 = conn.prepareStatement(sql);
            preparedStatement2.setString(1, user.getFio());
            preparedStatement2.setString(2, user.getPassword());
            preparedStatement2.setString(3, user.getDate());
            preparedStatement2.executeUpdate();

        } catch (SQLException es) {
            es.printStackTrace();
        }

    }


}












