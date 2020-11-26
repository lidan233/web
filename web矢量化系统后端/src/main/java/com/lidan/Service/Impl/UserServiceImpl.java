package com.lidan.Service.Impl;

import com.lidan.Dao.ProjectInfo;
import com.lidan.Dao.UserInfo;
import com.lidan.Model.User;
import com.lidan.Service.UserServiceDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@Service
public class UserServiceImpl implements UserServiceDo {
    @Autowired
    UserInfo users ;

    @Autowired
    ProjectInfo projects ;


    @Transactional
    @Override
    public int addUser(User i,String requestcode) {
        int projectid = users.getProjectIdByRequstcode(requestcode) ;
        if(projectid>0)
        {
            i.setProjectid(projectid);
            users.addUser(i) ;
            return projectid ;
        }

        return -1 ;
    }

    @Transactional
    public void addUserWithoutId(User i,String key )
    {

        users.addUserWithoutId(i) ;
    }

    @Override
    public void deleteUserByid(int i) {
        users.deleteUserByid(i) ;
    }

    @Override
    public void updateUserById(User i) {
        users.updateUserById(i) ;
    }


    @Override
    public User getUserById(int i ) {
        return users.getUserById(i) ;
    }

    @Override
    public List<User> getAllUsers() {
        return users.getAllUsers() ;
    }

    @Override
    public List<User> selectByAccount(String i) {
        System.out.println("comefing");
        return users.selectByAccount(i) ;
    }

    public List<User> selectByProjectid(String projectid)
    {
        return users.selectByProjectid(Integer.parseInt(projectid)) ;
    }

    @Override
    public User getProjectUserByid(int i) {
        return users.selectProjectUserByid(i) ;
    }

    @Override
    public User getRootUser() {

        User result = users.getRootUser() ;
        return result;
    }

    @Override
    public User getProjectUserByaccount(String account) {
        return users.getProjectUserByaccount(account) ;

    }

    @Override
    public int  addProjectUser(User i) {
        User k = users.getProjectUserByaccount(i.getAccount()) ;

        if(k!=null)
        {

            return -1 ;
        }
        return users.addProjectUser(i) ;
    }

}
