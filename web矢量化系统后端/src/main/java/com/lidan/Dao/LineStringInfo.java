package com.lidan.Dao;

import com.lidan.Model.LineString;
import com.lidan.Model.Point;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.sound.sampled.Line;
import java.util.List;

@Mapper
@Repository
public interface LineStringInfo {
    public List<Integer> addLineString(LineString lineString) ;
    public int deleteLineStringByid(int id) ;
    public int AfterInsert(@Param("linestring")LineString linestring, @Param("id") int id ) ;
    public LineString selectByPoint(@Param("point") Point point, @Param("distance") double distance) ;
}
