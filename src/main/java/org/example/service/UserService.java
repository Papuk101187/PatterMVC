package org.example.service;

import org.example.entity.Applications;
import org.example.entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    public List<User> getAllUsers() {

        ArrayList<User> userscontains = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            String sql = "SELECT FIO_user,login_password,date_born_user,id_user FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                User user = new User()
                        .setFio(resultSet.getString("fio_user"))
                        .setPassword(resultSet.getString("login_password"))
                        .setDate(resultSet.getString("date_born_user"))
                        .setId(resultSet.getString("id_user"));
                userscontains.add(user);
//
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return userscontains;
    }



}












