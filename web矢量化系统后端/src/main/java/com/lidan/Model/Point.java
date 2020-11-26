package com.lidan.Model;

public class Point implements  Feature{
    private int id ;
    private int orderid = 0 ;
    private double x ;
    private double  y ;
    private int coordinate_system = 4326 ;
    public final String type = "POINT" ;


    private int projectid =  -1 ;
    private int userid = -1 ;
    private String json = "" ;
    private String  name = "" ;


    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Point(double x,double y)
    {
        this.x = x ;
        this.y = y ;

    }

    public Point()
    {

    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getCoordinate_system() {
        return coordinate_system;
    }

    public void setCoordinate_system(int coordinate_system) {
        this.coordinate_system = coordinate_system;
    }


    public String getType()
    {
        return type ;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public String getName() {
        return name;
    }

    public void  setName(String  name) {
        this.name = name;
    }

}
