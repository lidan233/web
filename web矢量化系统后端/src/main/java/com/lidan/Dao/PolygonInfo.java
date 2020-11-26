package com.lidan.Dao;


import com.lidan.Model.Point;
import com.lidan.Model.Polygon;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PolygonInfo {
    public List<Integer> addPolygon(Polygon polygon) ;
    public int deleteByid(int id) ;
    public int AfterInsert(@Param("polygon") Polygon polygon,@Param("id")int id) ;
    public Polygon selectByPoint(@Param("point") Point point, @Param("distance") double distance) ;


}
