package com.lidan.Dao;

import com.lidan.Model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserInfo {
    int addUser(User i) ;
    int addUserWithoutId(User i) ;
    int deleteUserByid(int i) ;
    int updateUserById(User i) ;
    User getUserById(int i ) ;
    List<User> getAllUsers();
    List<User> selectByAccount(@Param("account") String i ) ;
    List<User> selectByProjectid(@Param("projectid") Integer projectid) ;
    User selectProjectUserByid(int id) ;
    User getRootUser() ;
    User  getProjectUserByaccount(String account) ;
    int  addProjectUser(User user) ;
    int getProjectIdByRequstcode(String requestcode) ;
    int getProjectIdByUserid(int id) ;


}
