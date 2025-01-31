package com.example.demo.entity;

import lombok.Data;

@Data
public class RecordRes extends Record{
    private String username;
    private String adminname;
    private String securityname;
    private String securityclassname;
    private String securitytypename;
}
