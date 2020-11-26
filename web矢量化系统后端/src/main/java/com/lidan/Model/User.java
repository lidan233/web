package com.lidan.Model;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id ;
    private Integer projectid ;
    private String name ;
    private String passwd ;
    private String account ;
    private String permission ;
    private int features ;

    public User(){}


    public String getPermission(){return permission ;}
    public void setPermission(String permission){this.permission = permission ;}
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getProjectid() {
        return projectid;
    }
    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public int getFeatures() {
        return features;
    }

    public void setFeatures(int features) {
        this.features = features;
    }

}
