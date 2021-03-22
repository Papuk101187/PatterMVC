package org.example.projectjspandjstl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.projectjspandjstl.entity.Applications;
import org.example.projectjspandjstl.entity.User;
import org.example.project2mvc.annotations.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


@Component
public class ApplicationsServiceCOPY {

    String userbas = "postgres";
    String pass = "10n11m87g";
    String dsn = "jdbc:postgresql://localhost:5432/application_base";
    ObjectMapper objectMapper = new ObjectMapper();



    public void postApplications(Applications applications, User user) throws SQLException {

        Connection connection = DriverManager.getConnection(dsn, userbas, pass);

        LocalDate date = LocalDate.now(); // получаем текущую дату

        String ss = String.valueOf(date);


        String sql = "INSERT INTO application (name_application,description_application,date_application,id_user) " +
                "VALUES (?,?,?,(SELECT id_user FROM users WHERE FIO_user=? AND login_password=? AND date_born_user=? ))";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, applications.getName());
        preparedStatement.setString(2, applications.getDescription());
        preparedStatement.setString(3, ss);
        preparedStatement.setString(4, user.getFio());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setString(6, user.getDate());
        preparedStatement.executeUpdate();
    }


    public ArrayList<Applications> getpostApplications(User user) throws SQLException {


        ArrayList<Applications> applicat = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(dsn, userbas, pass);

            String sql = "SELECT date_application,name_application,description_application " +
                    "FROM application INNER JOIN users " +
                    "ON users.id_user=application.id_user " +
                    "WHERE fio_user=? AND login_password=?";

            String sql2 = "SELECT date_application,description_application,name_application FROM application " +
                    "INNER JOIN users ON users.id_user=application.id_user " +
                    "WHERE fio_user=? AND login_password=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1, user.getFio());
            preparedStatement.setString(2, user.getPassword());


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Applications applications = new Applications()
                        .setName(resultSet.getString("name_application"))
                        .setDescription(resultSet.getString("description_application"))
                        .setDate(resultSet.getString("date_application"));
                applicat.add(applications);
//
            }

        } catch (SQLException es) {
            es.printStackTrace();
        }

        return applicat;

    }

    public ArrayList<Applications> getAll() throws SQLException {


        ArrayList<Applications> applicat = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(dsn, userbas, pass);

            String sql = "SELECT date_application,name_application,description_application " +
                    "FROM application INNER JOIN users " +
                    "ON users.id_user=application.id_user " +
                    "WHERE fio_user=? AND login_password=?";

            String sql2 = "SELECT date_application,description_application,name_application FROM application " +
                    "INNER JOIN users ON users.id_user=application.id_user ";

            PreparedStatement preparedStatement = connection.prepareStatement(sql2);


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Applications applications = new Applications()
                        .setName(resultSet.getString("name_application"))
                        .setDescription(resultSet.getString("description_application"))
                        .setDate(resultSet.getString("date_application"));
                applicat.add(applications);
//
            }

        } catch (SQLException es) {
            es.printStackTrace();
        }

        return applicat;

    }


    public void deleteApplicationsForId(String idApplication) {

        String s = idApplication;
        System.out.println(s);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            Connection connection = DriverManager.getConnection(dsn, userbas, pass);
            PreparedStatement statement1 = connection.prepareStatement(
                    "DELETE FROM application WHERE id_user="+idApplication+";");
            statement1.executeUpdate();


        } catch (SQLException es) {
            es.printStackTrace();
        }
    }






}









