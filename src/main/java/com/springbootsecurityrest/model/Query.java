package com.springbootsecurityrest.model;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Data
@AllArgsConstructor
public class  Query {

    private int id;
    private int user_id;

    private String header;

    private String body;

    private String createdDate;
    
    public int getUser_id() {
        return this.user_id;
    }
}
