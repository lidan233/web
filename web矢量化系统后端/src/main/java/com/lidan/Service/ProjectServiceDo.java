package com.lidan.Service;


import com.lidan.Model.Extent;
import com.lidan.Model.Point;
import com.lidan.Model.Project;
import com.lidan.Model.User;

import java.util.List;
import java.util.Map;

public interface ProjectServiceDo {

    public int addProject(String source_kind, Point center, Point extent_max, Point extent_min ,int features,String project_user_name) ;
    public boolean deleteProjectByid(int i) ;
    public boolean updateProjectById(Project i) ;
    public Project getProjectInfo(int id) ;

    public List<Project> getAllProjectInfo() ;
    public Project getProjectInfoByProjectUserid(int id) ;
    public Project getProjectInfoByProjectUserAccount(String account) ;
    public int getUserNum(int projectid) ;
    public Map<String,Integer> getLineProcessResult(int projectid) ;
    List<User> getUserByProjectid(int projectid) ;
    public Extent getExtent(int projectid) ;

}
