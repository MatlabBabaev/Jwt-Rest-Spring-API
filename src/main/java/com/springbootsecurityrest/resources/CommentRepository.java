package com.springbootsecurityrest.resources;

import com.springbootsecurityrest.model.Comment;
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
public class CommentRepository {

    List<Comment> comments = new ArrayList<Comment>();

    File file = new File("src/main/resources/config.properties");
    Scanner sc = new Scanner(file);
    String url = sc.next();

    Connection con = DriverManager.getConnection(url);
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery("SELECT * from feedback order by id");

    public CommentRepository() throws FileNotFoundException, SQLException {
        while (rs.next()) {
            this.comments.add(new Comment(rs.getInt("id"), rs.getInt("application_id"), rs.getInt("admin_id"),
                    rs.getString("text"), rs.getString("date")));

        }
        rs.close();
        st.close();
        con.close();
    }

    public List<Comment> getAll() {
        return this.comments;
    }

    public Comment getById(Integer id){
        return this.comments.stream()
                .filter(comment -> id.equals(comment.getId()))
                .findFirst().orElse(null);
    }
    public List<Comment> getByApplicationId(Integer app_id){
        return this.comments.stream()
                .filter(comment -> app_id.equals(comment.getApplication_id()))
                .collect(Collectors.toList());
    }
}
