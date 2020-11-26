package com.lidan.Model;

import javax.sound.sampled.Line;
import java.util.LinkedList;

public class LineString implements Feature {

    LinkedList<Point> linestring =  null ;
    public final String type="LINESTRING" ;
    int size = 0 ;
    private int id = 0 ;
    private int projectid =  -1 ;
    private int userid = -1 ;
    private String json = "" ;
    private String  name = "" ;

    public LineString()
    {

    }

    public LineString(String json){
        this.json = json ;

    }

    public LineString(LinkedList<Point> points)
    {
        this.linestring = points;
        this.size = points.size() ;

    }
    public Point getPoint(int index)
    {
        if(index<linestring.size())
        {
            return linestring.get(index);
        }
        return null ;
    }


    public String getType()
    {
        return type ;
    }


    public LinkedList<Point> getLinestring() {
        return linestring;
    }

    public void setLinestring(LinkedList<Point> linestring) {
        this.linestring = linestring;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
