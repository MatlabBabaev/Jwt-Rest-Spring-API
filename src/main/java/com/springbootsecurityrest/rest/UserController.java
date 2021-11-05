package com.springbootsecurityrest.rest;
import com.springbootsecurityrest.rest.AuthController;

import com.springbootsecurityrest.services.DBService;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import com.springbootsecurityrest.model.User;
import com.springbootsecurityrest.services.UserService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<User> getAll() {
        if(service.getLoggedUser().getRole().equals("admin"))
            return this.service.getAll();
        System.out.println("No permission");
        return null;
    }

    @PostMapping(path = "/adduser", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addUser (@RequestBody String user) throws JSONException, FileNotFoundException, SQLException {

        JSONArray jsonArray = new JSONArray(user);
        JSONObject obj = jsonArray.getJSONObject(0);
        String firstname= obj.getString("firstname");
        String lastname= obj.getString("lastname");
        String password = obj.getString("password");
        String login = obj.getString("login");
        int age = obj.getInt("age");
        String address = obj.getString("address");
        String role = obj.getString("role");
        String insertInfo = DBService.addUser(login, password, firstname, lastname, age, address, role);
        return insertInfo;
    }

    @PostMapping(path = "/upduser", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updUser (@RequestBody String user) throws JSONException, FileNotFoundException, SQLException {

        JSONArray jsonArray = new JSONArray(user);
        JSONObject obj = jsonArray.getJSONObject(0);
        int id = obj.getInt("id");
        String firstname= obj.getString("firstname");
        String lastname= obj.getString("lastname");
        String password = obj.getString("password");
        String login = obj.getString("login");
        int age = obj.getInt("age");
        String address = obj.getString("address");
        String role = obj.getString("role");
        String insertInfo = DBService.updateUser(id,login, password, firstname, lastname, age, address, role);
        return insertInfo;
    }

}
