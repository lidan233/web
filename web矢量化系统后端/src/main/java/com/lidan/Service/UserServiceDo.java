package com.lidan.Service;

import com.lidan.Model.User;

import java.util.List;

public interface UserServiceDo {

    public int addUser(User i,String requestcode) ;
    public void deleteUserByid(int i) ;
    public void updateUserById(User i) ;
    public User getUserById(int i ) ;
    public List<User> getAllUsers();
    public List<User> selectByAccount(String i) ;
    public List<User> selectByProjectid(String projectid) ;
    public User getProjectUserByid(int i) ;
    public User getRootUser() ;
    public User getProjectUserByaccount(String account) ;
    public int addProjectUser(User i) ;


}
