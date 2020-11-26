package com.lidan.Model;

import com.lidan.Sercurity.Contants.GeoMap;

public class Project {
    public int id = 0 ;
    public String center_str ;
    public String source_kind = GeoMap.NOTHING;
    public String Extent_max_str ;


    public String Extent_min_str ;


    public int features = 0 ;
    public int now_features = 0;

    public Point center = new Point(0,0);
    public Extent extent ;


    public String project_user_name = "" ;



    public Project()
    {

    }

    public Project(Point center,Extent want_extent,String source_kind,int features)
    {
        this.source_kind = source_kind ;
        this.center.setX(center.getX() );
        this.center.setY(center.getY());
        this.extent = want_extent ;
        this.features = features  ;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource_kind() {
        return source_kind;
    }

    public void setSource_kind(String source_kind) {
        this.source_kind = source_kind;
    }

    public Point getCenter() { return center; }

    public void setCenter(Point center) { this.center = center; }

    public Extent getExtent() {
        return extent;
    }

    public void setExtent(Extent extent) {
        this.extent = extent;
    }



    public int getFeatures() {
        return features;
    }

    public void setFeatures(int features) {
        this.features = features;
    }

    public int getNow_features() {
        return now_features;
    }

    public void setNow_features(int now_features) {
        this.now_features = now_features;
    }

    public String getProject_user_name() {
        return project_user_name;
    }

    public void setProject_user_name(String project_user_name) {
        this.project_user_name = project_user_name;
    }

    public String getCenter_str() {
        return center_str;
    }

    public void setCenter_str(String center_str) {
        this.center_str = center_str;
    }

    public String getExtent_max_str() {
        return Extent_max_str;
    }

    public void setExtent_max_str(String extent_max_str) {
        Extent_max_str = extent_max_str;
    }

    public String getExtent_min_str() {
        return Extent_min_str;
    }

    public void setExtent_min_str(String extent_min_str) {
        Extent_min_str = extent_min_str;
    }

}
