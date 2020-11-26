package com.lidan.Model;

public class Extent {

    public double extent_max_x = 0 ;
    public double extent_max_y = 0 ;
    public double extent_min_x = 0 ;
    public double extent_min_y = 0 ;

    public Extent(double max_x,double max_y,double min_x,double min_y)
    {
        this.extent_max_x = max_x;
        this.extent_max_y = max_y ;
        this.extent_min_x = min_x ;
        this.extent_min_y = min_y ;
    }

    public  boolean isIn(Point i )
    {
        if(i.getX()>extent_min_x&&i.getX()<extent_max_x&&i.getY()<extent_max_y&&i.getY()>extent_min_y)
        {
            return true ;
        }

        return false ;

    }

}
