package com.springbootsecurityrest.rest;

import com.springbootsecurityrest.model.Comment;
import com.springbootsecurityrest.model.User;
import com.springbootsecurityrest.resources.CommentRepository;
import com.springbootsecurityrest.services.CommentService;
import com.springbootsecurityrest.services.DBService;
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
public class CommentController {

    private CommentService service;

    public CommentController(CommentService service){
        this.service=service;
    }

    @PostMapping(path = "/addcomment", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addComment (@RequestBody String comment) throws JSONException, FileNotFoundException, SQLException {

        JSONArray jsonArray = new JSONArray(comment);
        JSONObject obj = jsonArray.getJSONObject(0);
        int appclication_id = obj.getInt("application_id");
        int admin_id= service.getLoggedUser().getId();
        String text= obj.getString("text");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String addCommentInfo = DBService.addComment(appclication_id, admin_id, text, date);
        return addCommentInfo;
    }

    @GetMapping(path = "/admin/getallcomments", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getAll() throws FileNotFoundException, SQLException {
        if(service.getLoggedUser().getRole().equals("admin"))
            return this.service.getAll().toString();
        return "You do not have permission";
    }

    @GetMapping (path = "/user/applications/getcomments", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getCommentsByAppId(@RequestBody String application) throws FileNotFoundException, SQLException, JSONException {

        int application_id = Integer.parseInt(application);
        int user_id= service.getLoggedUser().getId();
        JSONArray s = DBService.getCommentsByAppId(application_id, user_id);
        return s.toString();
    }
}
