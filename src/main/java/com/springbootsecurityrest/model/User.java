package com.springbootsecurityrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Iterator;

@Data
@AllArgsConstructor
public class User {

    private int id;
    private String login;

    private String password;

    private String firstname;

    private String lastname;

    private int  age;

    private String address;

    private String role;

}
