package com.springbootsecurityrest.services;

import com.springbootsecurityrest.model.Query;
import com.springbootsecurityrest.model.User;
import com.springbootsecurityrest.resources.QueryRepository;
import com.springbootsecurityrest.resources.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Service
public class QueryService {

    private QueryRepository repository;

    public QueryService(QueryRepository repository){
        this.repository=repository;
    }
    public List<Query> getAll() {
        return this.repository.getAll();
    }

    public List<Query> getById(Integer id) {
        return this.repository.getById(id);
    }

    public List<Query> getByUserId(Integer user_id){
        return this.repository.getByUserId(user_id);
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
