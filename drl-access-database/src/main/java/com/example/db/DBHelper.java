package com.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    public static String DRIVER = "org.h2.Driver";
    public static String URL = "jdbc:h2:mem:MyDB;DB_CLOSE_DELAY=-1";
    public static String USER = "sa";
    public static String PASSWORD = "";

    public static void initialize() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        statement.executeUpdate("create table Employee(id varchar(10) primary key, name varchar(20))");
        statement.executeUpdate("insert into Employee values('001', 'employe001')");
        statement.executeUpdate("insert into Employee values('002', 'employe002')");
        statement.executeUpdate("insert into Employee values('003', 'employe003')");
        statement.executeUpdate("insert into Employee values('004', 'employe004')");
        statement.executeUpdate("insert into Employee values('005', 'employe005')");
        statement.executeUpdate("insert into Employee values('006', 'employe006')");
        statement.executeUpdate("insert into Employee values('007', 'employe007')");
        statement.close();
        connection.close();
        System.out.println("DB initialize is done");
    }

    public static List<Person> loadFacts() throws SQLException {
        List<Person> result = new ArrayList<Person>();
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select id, name from Employee");
        while (rs.next()) {
            Person person = new Person(rs.getString("id"), rs.getString("name"));
            result.add(person);
            System.out.println("Loaded " + person);
        }
        rs.close();
        statement.close();
        connection.close();
        System.out.println("Loaded " + result.size() + " Fact(s) from database");
        return result;
    }
}
