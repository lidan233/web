package com.lidan.Service.Impl;

import com.lidan.Dao.ProjectInfo;
import com.lidan.Model.Extent;
import com.lidan.Model.Point;
import com.lidan.Model.Project;
import com.lidan.Model.User;
import com.lidan.Sercurity.Contants.GeoMap;
import com.lidan.Service.ProjectServiceDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Service
public class ProjectServiceImpl implements ProjectServiceDo {

    @Autowired
    ProjectInfo projectinfo ;

    @Transactional
    @Override
    public int addProject(String source_kind,Point center,Point extent_max,Point extent_min,int features ,String project_user_name)
    {

        if(!GeoMap.set.contains(source_kind))
        {
            return -1 ;
        }
        Project project = new Project(center,
                        new Extent(extent_max.getX(),extent_max.getY(),
                                    extent_min.getX(),extent_min.getY()),source_kind,features) ;

        int result = projectinfo.addProject(project) ;
        return project.id  ;

    }

    @Override
    public boolean deleteProjectByid(int i) {

       return  projectinfo.deleteProjectByid(i) >0 ;

    }
    @Override
    public boolean updateProjectById(Project i) {


        return false ;
    }

    @Override
    public Project getProjectInfo(int id) {
       return  projectinfo.getProjectByid(id) ;

    }

    @Override
    public List<Project> getAllProjectInfo() {

       return projectinfo.getAllProjectInfo() ;
    }

    @Override
    public Project getProjectInfoByProjectUserid(int id) {
        return projectinfo.getProjectInfoByProjectUserid(id) ;
    }

    @Override
    public Project getProjectInfoByProjectUserAccount(String account) {
        return projectinfo.getProjectInfoByProjectUserAccount(account) ;
    }

    @Override
    public int getUserNum(int projectid) {
        return projectinfo.getUserNum(projectid) ;
    }


    @Transactional
    public Map<String,Integer> getLineProcessResult(int projectid)
    {
        int lines = projectinfo.getProjectLineNum(projectid) ;
        int points = projectinfo.getProjectPointNum(projectid) ;
        int polygons = projectinfo.getProjectPolygonNum(projectid) ;
        Map<String,Integer> result = new HashMap<>() ;
        result.put("lines",lines) ;
        result.put("points",points) ;
        result.put("polygons",polygons) ;
        return result ;
    }

    @Transactional
    public List<User> getUserByProjectid(int id)
    {
        int result = projectinfo.getProjectIdFromUserid(id) ;
        return projectinfo.getUserByProjectid(result) ;
    }
    public Extent getExtent(int projectid)
    {
        return projectinfo.getExtent(projectid) ;
    }
}
