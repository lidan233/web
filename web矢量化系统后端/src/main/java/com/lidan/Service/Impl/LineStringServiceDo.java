package com.lidan.Service.Impl;

import com.lidan.Dao.LineStringInfo;
import com.lidan.Model.Feature;
import com.lidan.Model.LineString;
import com.lidan.Model.Point;
import com.lidan.Service.FeatureServiceDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Primary
@Service
public class LineStringServiceDo implements FeatureServiceDo {

    @Autowired
    LineStringInfo lineStringInfo ;

    @Override
    public List<Integer> addFeature(Feature feature) {
        if(!(feature instanceof LineString))
        {
            return null;
        }
        LineString lineString = (LineString)feature;
        List<Integer> allinsert = lineStringInfo.addLineString(lineString) ;
        for(int k = 0; k< allinsert.size();k++)
        {
           lineStringInfo.AfterInsert(lineString,allinsert.get(k)) ;
        }



        return allinsert;
    }

    @Override
    public int deleteFeature(Feature i) {
        if(!(i instanceof LineString))
        {
            return -1;
        }

        return 0;
    }

    @Override
    public int updateFeature(Feature i) {
        if(!(i instanceof LineString))
        {
            return -1;
        }
        return 0;
    }

    @Override
    public Feature selectFeature(Point point,double distance ) {

        LineString lineString = lineStringInfo.selectByPoint(point,distance) ;
        return lineString ;
    }

    public int DeleteFeatureById(int id)
    {
        int result = lineStringInfo.deleteLineStringByid(id) ;
        return result ;
    }
}
