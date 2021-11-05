package com.springbootsecurityrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    private int id;
    private int application_id;
    private int admin_id;
    private String text;
    private String date;
}
