package com.lidan.Service;

import com.lidan.Model.Feature;
import com.lidan.Model.Point;

import java.util.List;

public interface FeatureServiceDo {

    public List<Integer> addFeature(Feature i) ;
    public int deleteFeature(Feature i) ;
    public int updateFeature(Feature i) ;
    public Feature selectFeature(Point i ,double distance) ;

}
