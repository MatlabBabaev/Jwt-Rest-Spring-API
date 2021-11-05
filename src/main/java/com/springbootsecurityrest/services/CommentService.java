package com.springbootsecurityrest.services;

import com.springbootsecurityrest.model.Comment;
import com.springbootsecurityrest.model.User;
import com.springbootsecurityrest.resources.CommentRepository;
import com.springbootsecurityrest.resources.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Service
public class CommentService {

    private CommentRepository repository;

    public CommentService(CommentRepository repository){
        this.repository=repository;
    }

    public List<Comment> getAll(){ return this.repository.getAll();}

    public Comment getById(int id){return this.repository.getById(id);}

    public List<Comment> getByApplicationId(int app_id){
        return this.repository.getByApplicationId(app_id);
    }

    public User getLoggedUser() throws FileNotFoundException, SQLException {
        String login;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            login = ((UserDetails)principal).getUsername();
        } else {
            login = principal.toString();
        }
        return new UserRepository().getByLogin(login);
    }

}
