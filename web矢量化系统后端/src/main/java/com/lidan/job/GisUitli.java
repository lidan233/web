package com.lidan.job;


import com.lidan.Model.Feature;
import com.lidan.Model.LineString;
import com.lidan.Model.Point;
import com.lidan.Model.Polygon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.LogManager;

public class GisUitli {
    LogManager logger  = LogManager.getLogManager() ;

    public static Feature parseString(String feature)
    {
        feature = feature.trim() ;
        String type = feature.substring(0,feature.indexOf('(')) ;
        if(type.equals("POINT"))
        {
            return new Point(Double.parseDouble(feature.substring(feature.indexOf('(')+1,feature.indexOf(' '))),Double.parseDouble(feature.substring(feature.indexOf(' ')+1,feature.indexOf(')')))) ;
        }else if(type.equals("LINESTRING"))
        {
            LinkedList<Point> points_store = new LinkedList<>() ;

            String[] points = feature.substring(feature.indexOf('(')+1,feature.indexOf(')')).split(" ") ;
            for(int i = 0 ;i<points.length/2;i++)
            {
                points_store.add(new Point(Double.parseDouble(points[0]),Double.parseDouble(points[1])));
            }

            LineString temp = new LineString(points_store) ;
            return temp ;
        }else if(type.equals("PolygonServiceDo"))
        {
            LinkedList<Point> points_store = new LinkedList<>() ;

            String[] points = feature.substring(feature.indexOf('(')+1,feature.indexOf(')')).split(" ") ;
            for(int i = 0 ;i<points.length/2;i++)
            {
                points_store.add(new Point(Double.parseDouble(points[0]),Double.parseDouble(points[1])));
            }

            LineString temp = new LineString(points_store) ;

            Polygon temp1 = new Polygon(temp) ;
            return temp1 ;
        }
        return null ;
    }


    public String featureToString(Feature feature  )
    {
        StringBuilder result = new StringBuilder() ;

        if(feature.getType().equals("POINT"))
        {
            Point temp = (Point)feature ;
            result.append("POINT(") ;
            result.append(temp.getX()+" ") ;
            result.append(temp.getY()+")") ;

        }else if(feature.getType().equals("LINESTRING"))
        {
            LineString temp = (LineString)feature ;
            result.append("LINESTRING(") ;
            for(int i = 0; i<temp.getSize()-1;i++)
            {
                Point point1 = temp.getPoint(i) ;
                result.append(point1.getX()+" ") ;
                result.append(point1.getY()+" ") ;
            }

            result.append(temp.getPoint(temp.getSize()-1).getX()+" "+temp.getPoint(temp.getSize()-1)+")") ;
        }else if(feature.getType().equals("POLYGON"))
        {
            Polygon temp = (Polygon) feature ;
            result.append("POLYGON(") ;
            for(int i = 0; i<temp.getSize()-1;i++)
            {
                Point point1 = temp.getPoint(i);
                result.append(point1.getX()+" ");
                result.append(point1.getY()+" ") ;
            }
            result.append(temp.getPoint(temp.getSize()-1).getX()+" "+temp.getPoint(temp.getSize()-1)+")") ;

        }

        return result.toString() ;
    }

}
