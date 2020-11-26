package com.lidan.Sercurity.Contants;


import java.util.HashMap;
import java.util.HashSet;

public  class GeoMap {

    public static String  GOOGLE_SATELLITE_CHINA =  "GOOGLE_SATELLITE_CHINA" ;
    public static String  GOOGLE_ROADMAP_CHINA = "GOOGLE_ROADMAP_CHINA" ;
    public static String  GOOGLE_SATELLITE = " GOOGLE_SATELLITE" ;
    public static String  GOOGLE_ROADMAP = "GOOGLE_ROADMAP" ;
    public static String  AMAP_ROAD = "AMAP_ROAD" ;
    public static String  AMAP_SATELLITE = "AMAP_SATELLITE";
    public static String  TIANDITU_ROAD = "TIANDITU_ROAD" ;
    public static String  BING_ROAD = "BING_ROAD" ;
    public static String  BING_SATELLITE = "BING_SATELLITE" ;
    public static String  OSM = "OSM" ;
    public static String  NOTHING ="NOTHING" ;


    public static HashSet<String> set = new HashSet<String>() {
        {
            add("GOOGLE_SATELLITE_CHINA");
            add( "GOOGLE_ROADMAP_CHINA");
            add("GOOGLE_SATELLITE") ;
            add("GOOGLE_ROADMAP") ;
            add("AMAP_ROAD") ;
            add("TIANDITU_ROAD") ;
            add("BING_ROAD") ;
            add("BING_SATELLITE") ;
            add("OSM") ;
            add("NOTHING") ;
        }
    };
}
