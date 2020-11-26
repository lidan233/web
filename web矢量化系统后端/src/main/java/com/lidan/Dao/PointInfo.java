package com.lidan.Dao;

import com.lidan.Model.Extent;
import com.lidan.Model.Point;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public  interface PointInfo {
    public List<Integer> addPoint(Point insert) ;
    public int deleteByid(int id) ;
    public int deleteByextent(Extent extent) ;
    public int deleteByPointArea(Point point,int length) ;
    public int AfterInsert(@Param("point") Point point, @Param("id")int id) ;

    public Point selectByPoint(@Param("point")Point point,@Param("distance")double distance) ;


}
