package com.example.todolist.models;

import java.sql.*;

public class UserModel {
    private Connection connection;

    public UserModel() throws SQLException {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/to_do_app", "root", "mysqlrozki01");
        } catch (Exception e){
            System.out.println("Connection to database failed");
            e.printStackTrace();
        }
    }


    public User getUser(String username, String password){
        try {
            PreparedStatement prepStat = connection.prepareStatement(
                    "select id from users where username = ? and password = ?");
            prepStat.setString(1, username);
            prepStat.setString(2, password);
            ResultSet rsUser = prepStat.executeQuery();
            if (rsUser.next()) {
                return new User(rsUser.getInt("id"), username, password);
            }
        } catch (Exception e) {
            System.out.println("Importing user from database failed");
            e.printStackTrace();
        }
        return null;
    }
}
