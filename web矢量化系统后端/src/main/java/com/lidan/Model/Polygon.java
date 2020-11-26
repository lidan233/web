package com.lidan.Model;

import java.util.LinkedList;

public class Polygon implements  Feature {

    private int id ;
    LinkedList<Point> points = null ;
    public final String type = "POLYGON" ;
    private int size = 0 ;
    private int coordinate_system = 4326 ;
    private int projectid =  -1 ;
    private int userid = -1 ;
    private String json = "" ;
    private String name = "" ;

    public Polygon()
    {

    }
    public Polygon(String json)
    {
        this.json = json ;
    }


    public Polygon(LineString lineString)
    {
        this.points = lineString.getLinestring() ;
        this.size = this.points.size();
    }


    public String getType()
    {
        return type ;
    }

    public LinkedList<Point> getPoints() {
        return points;
    }

    public void setPoints(LinkedList<Point> points) {
        this.points = points;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public Point getPoint(int index)
    {
        if(index<points.size())
        {
            return points.get(index);
        }
        return null ;
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


    public int getCoordinate_system() {
        return coordinate_system;
    }

    public void setCoordinate_system(int coordinate_system) {
        this.coordinate_system = coordinate_system;
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

