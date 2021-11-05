package com.springbootsecurityrest.resources;

import com.springbootsecurityrest.model.User;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.io.File;
import java.util.Scanner;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<User>();
    private List<User> admins = new ArrayList<User>();

    File file = new File("src/main/resources/config.properties");
    Scanner sc = new Scanner(file);
    String url = sc.next();

    Connection con = DriverManager.getConnection(url);
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery("SELECT * from users");

    public UserRepository() throws SQLException, FileNotFoundException {

        while (rs.next()) {
           this.users.add(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"),
                          rs.getString("first_name"), rs.getString("last_name"),
                          rs.getInt("age"), rs.getString("address"), rs.getString("role")));

        }
        rs.close();

        ResultSet rsAdmin = st.executeQuery("SELECT * from users where role='admin'");
        while (rsAdmin.next()) {
            this.admins.add(new User(rsAdmin.getInt("id"), rsAdmin.getString("login"), rsAdmin.getString("password"),
                    rsAdmin.getString("first_name"), rsAdmin.getString("last_name"),
                    rsAdmin.getInt("age"), rsAdmin.getString("address"), rsAdmin.getString("role")));

        }
        rsAdmin.close();
        st.close();
        con.close();

    }


    public User getByLogin(String login) {
        return this.users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst()
                .orElse(null);
    }
    /*public User getById(Integer id) {
        return this.users.stream()
                .filter(user -> id.equals(user.getLogin()))
                .findFirst()
                .orElse(null);
    }*/

    public List<User> getAll() {
        return this.users;
    }
    public List<User> getAdmins(){
        return this.admins;
    }
}
