package com.lidan.Dao;

import com.lidan.Model.Extent;
import com.lidan.Model.Project;
import com.lidan.Model.User;
import com.lidan.Sercurity.Contants.GeoMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProjectInfo {
    Project getProjectByid(int id) ;
    int addProject(Project project) ;
    int deleteProjectByid(int id) ;
    List<Project> getAllProjectInfo() ;
    Project getProjectInfoByProjectUserid(int id) ;
    Project getProjectInfoByProjectUserAccount(String account) ;
    int getUserNum(int id) ;
    int getProjectLineNum(int projectid) ;
    int getProjectPointNum(int projectid) ;
    int getProjectPolygonNum(int projectid) ;

    List<User> getUserByProjectid(int id) ;
    int getProjectIdFromUserid(int id) ;
    Extent getExtent(int id) ;
}
