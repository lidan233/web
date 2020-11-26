package com.lidan.Service.Impl;

import com.lidan.Dao.PointInfo;
import com.lidan.Dao.UserInfo;
import com.lidan.Model.Feature;
import com.lidan.Model.Point;
import com.lidan.Model.User;
import com.lidan.Service.FeatureServiceDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Primary
@Service
public class PointServiceDo implements FeatureServiceDo {
    @Autowired
    PointInfo pointInfo ;
    @Autowired
    UserInfo userInfo ;


    @Override
    @Transactional
    public List<Integer> addFeature(Feature i) {
        if(!(i instanceof Point))
        {
            return null;
        }
        Point temp = (Point)i ;

        List<Integer> a = pointInfo.addPoint(temp) ;
        for(int k = 0; k< a.size();k++)
        {
            pointInfo.AfterInsert(temp,a.get(k)) ;
        }

        return a;
    }

    @Override
    public int deleteFeature(Feature i) {
        if(!(i instanceof Point))
        {
            return -1 ;
        }
        Point point = (Point)i ;

        int result = pointInfo.deleteByid(point.getId()) ;

        return result ;
    }

    @Override
    public int updateFeature(Feature i) {
        if(!(i instanceof Point)) {
            return -1;
        }
        return 0;
    }

    @Override
    @Transactional
    public Feature selectFeature(Point point,double distance) {

        Point result = pointInfo.selectByPoint(point,distance) ;
        if(result == null)
        {
            return null ;
        }

        User temp = userInfo.getUserById(result.getUserid()) ;
        result.setName(temp.getName()) ;

        return result ;
    }

    public int DeleteFeatureByid(int id)
    {
        int result = pointInfo.deleteByid(id) ;
        return result ;
    }

}
