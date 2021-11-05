package com.springbootsecurityrest.resources;

import com.springbootsecurityrest.model.Query;
import com.springbootsecurityrest.model.User;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Repository
public class QueryRepository {
    private List<Query> queries = new ArrayList<Query>();

    File file = new File("src/main/resources/config.properties");
    Scanner sc = new Scanner(file);
    String url = sc.next();

    Connection con = DriverManager.getConnection(url);
    Statement st = con.createStatement();

    public QueryRepository() throws SQLException, FileNotFoundException {

        ResultSet rs = st.executeQuery("SELECT * from queries");
        while (rs.next()) {
            this.queries.add(new Query(rs.getInt("id"), rs.getInt("user_id"), rs.getString("header"),
                    rs.getString("body"), rs.getString("created_date")));

        }
        rs.close();
        con.close();
    }

    public List<Query> getByUserId(Integer user_id) {
        return this.queries.stream()
                .filter(query -> user_id.equals(query.getUser_id()))
                .collect(Collectors.toList());
    }

    public List<Query> getById(Integer id) {
        return (List<Query>) this.queries.stream()
                .filter(query -> id.equals(query.getId()));
    }
    public List<Query> getAll() {
        return this.queries;
    }
}
