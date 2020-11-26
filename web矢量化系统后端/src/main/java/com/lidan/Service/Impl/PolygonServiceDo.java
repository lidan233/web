package com.lidan.Service.Impl;

import com.lidan.Dao.PolygonInfo;
import com.lidan.Dao.UserInfo;
import com.lidan.Model.Feature;
import com.lidan.Model.Point;
import com.lidan.Model.Polygon;
import com.lidan.Model.User;
import com.lidan.Service.FeatureServiceDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Primary
@Service
public class PolygonServiceDo implements FeatureServiceDo {
    @Autowired
    PolygonInfo polygonInfo ;
    @Autowired
    UserInfo userInfo ;

    @Override
    public List<Integer> addFeature(Feature i) {
        if(!(i instanceof Polygon))
        {
            return null;
        }
        Polygon polygon = (Polygon)i ;
        List<Integer> allinsert = polygonInfo.addPolygon(polygon) ;
        for(int k = 0; k< allinsert.size();k++)
        {
            polygonInfo.AfterInsert(polygon,allinsert.get(k)) ;
        }
        return allinsert;
    }

    @Override
    public int deleteFeature(Feature i) {
        if(!(i instanceof Polygon))
        {
            return -1;
        }
        return 0;
    }

    @Override
    public int updateFeature(Feature i) {
        if(!(i instanceof Polygon))
        {
            return -1;
        }
        return 0;
    }

    @Override
    public Feature selectFeature(Point point ,double distance) {

        Polygon result = polygonInfo.selectByPoint(point,distance) ;
        if(result == null)
        {
            return null ;
        }

        User temp = userInfo.getUserById(result.getUserid()) ;
        result.setName(temp.getName()) ;

        return result ;
    }

    public int DeleteFeatureById(int id)
    {
        int result = polygonInfo.deleteByid(id) ;
        return result ;
    }

}
