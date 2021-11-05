package com.springbootsecurityrest.rest;

import com.springbootsecurityrest.model.Query;
import com.springbootsecurityrest.model.User;
import com.springbootsecurityrest.resources.UserRepository;
import com.springbootsecurityrest.services.DBService;
import com.springbootsecurityrest.services.QueryService;
import com.springbootsecurityrest.services.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class QueryController {

    private QueryService service;

    public QueryController (QueryService service){
        this.service=service;
    }
    @GetMapping(path = "/queries", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Query> getAll() throws FileNotFoundException, SQLException {
        User currentUser = service.getLoggedUser();
        if(currentUser.getRole().equals("admin"))
            return this.service.getAll();
        else {
            return this.service.getByUserId(currentUser.getId());
        }
    }

    @PostMapping(path = "/createquery", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createQuery (@RequestBody String query) throws JSONException, FileNotFoundException, SQLException {

        JSONArray jsonArray = new JSONArray(query);
        JSONObject obj = jsonArray.getJSONObject(0);
        int user_id= service.getLoggedUser().getId();
        String header= obj.getString("header");
        String body= obj.getString("body");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String createQueryInfo = DBService.createQuery(user_id, header, body, date);
        return createQueryInfo;
    }
    @GetMapping(path = "/updquery", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updQuery (@RequestBody String query) throws JSONException, FileNotFoundException, SQLException {
        JSONArray jsonArray = new JSONArray(query);
        JSONObject obj = jsonArray.getJSONObject(0);
        int id = obj.getInt("id");
        String body= obj.getString("body");
        int user_id = service.getLoggedUser().getId();
        String insertInfo = DBService.updateQuery(id, body, user_id);
        return insertInfo;
    }
    @PostMapping(path = "/deletequery", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteQuery (@RequestBody String query) throws JSONException, FileNotFoundException, SQLException {
        JSONArray jsonArray = new JSONArray(query);
        JSONObject obj = jsonArray.getJSONObject(0);
        int id = obj.getInt("id");
        int user_id = service.getLoggedUser().getId();
        String deleteInfo = DBService.DeleteQuery(id, user_id);
        return deleteInfo;
    }
}
