

create table source_from(
    source_type varchar(100) primary key 
);
insert into source_from values('GOOGLE_SATELLITE_CHINA'),('GOOGLE_ROADMAP_CHINA'),( 'GOOGLE_SATELLITE'),('GOOGLE_ROADMAP'),(
'AMAP_ROAD'),('AMAP_SATELLITE'),('TIANDITU_ROAD'),('BING_ROAD'),('BING_SATELLITE'),('OSM');
// 项目表 用来给项目确定 中心点 山歌图层
create table project(
    id serial  primary key,
    source_type varchar(100) ,
    center geometry(POINT,4326),
    extent_min geometry(POINT,4326) ,
    extent_max geometry(POINT,4326)s
);
alter table project add column source_type varchar(100) ;
alter table project drop column source_type  ;
alter table project add constraint source_limit foreign key(source_type) REFERENCES source_from(source_type) ;

create table all_user(
    id serial primary key,
    projectid int ,
    name varchar(255) ,
    passwd  varchar(255),
    constraint user_foreign_id foreign key(projectid) REFERENCES project(id) 
);
alter table all_user add primary key(id);
insert into all_user values(1,1,'lidan','123456');

create table requestcode(
    id serial primary key ,
    projectid int ,
    code varchar(255) ,
    constraint requestcode_id foreign key(projectid) REFERENCES project(id) 
) ; 

create table admin(
    name varchar(255) ,
    passwd varchar(255) ,
);

create table temp_points(
    id serial primary key ,
    geom geometry(POINT ,4326) ,
    projectid int ,
    user_id int ,
    constraint point_foreign_id foreign key(projectid) REFERENCES project(id) 
);
alter table temp_points add column projectid int default 1 ;
alter table temp_points add constraint point_foreign_id foreign key(projectid) REFERENCES project(id);
alter table temp_points add column user_id int default 1 ;
alter table temp_points add constraint point_foreign_user_id foreign key(user_id) REFERENCES all_user(id) ;


create table temp_lines(
    id serial primary key,
    geom geometry(LINESTRING,4326),
    projectid int ,
    user_id int ,
    constraint line_foreign_id foreign key(projectid) REFERENCES project(id) ,
);
alter table temp_lines add column projectid int default 1 ;
alter table temp_lines add constraint line_foreign_id foreign key(projectid) REFERENCES project(id);
alter table temp_lines add column user_id int default 1 ;
alter table temp_lines add constraint line_foreign_user_id foreign key(user_id) REFERENCES all_user(id) ;

create table temp_polygon(
    id serial primary key,
    geom geometry(POLYGON,4326),
    projectid int ,
    user int ,
    constraint polygon_foreign_id foreign key(projectid) REFERENCES project(id) ,
) ;

alter table temp_polygon add column projectid int default 1 ;
alter table temp_polygon add constraint polygon_foreign_id foreign key(projectid) REFERENCES project(id);
alter table temp_polygon add column user_id int default 1 ;
alter table temp_polygon add constraint line_foreign_user_id foreign key(user_id) REFERENCES all_user(id) ;




select * from temp_points where 
    ST_Intersects(
            ST_Buffer(
                CAST(
                    ST_SetSRID(
                    ST_MakePoint(112.90636304776851, 28.205074019810965)
                            ,4326) AS geography)
                            ,4000),geom)=true order by st_distance(geom,                
                                                        CAST(
                                                            ST_SetSRID(
                                                                ST_MakePoint(112.90636304776851, 28.205074019810965)
                                                                    ,4326) AS geography)) limit 1;
                                    
SELECT ST_AsText(ST_Buffer(CAST(ST_SetSRID(ST_Point(-74.005847, 40.716862),4326) AS geography),10));


ST_Buffer(
    ST_SetSRID(
            ST_MakePoint(112.90636304776851, 28.205074019810965)
              ,4326)
,4000)

SELECT ST_Intersects(’POINT(0 0)’::geometry, ’LINESTRING ( 2 0, 0 2 )’::geometry);

SELECT ST_Buffer(ST_MakePoint(21.304116745663165, 38.68607570952619)::geography, 4000);






 $sql = "select id,projectid,st_astext(geom) as feature 
                    from temp_lines where 
                            ST_Intersects(
                                ST_Buffer(
                                     CAST(
                               ST_SetSRID(
                             ST_MakePoint(112.90636304776851, 28.205074019810965)
                                       ,4326) 
                                AS geography)
                                         ,400)
                                       ,geom)=true 
                                       order by st_distance(geom, CAST(
                                                            ST_SetSRID(
                                                          ST_MakePoint(112.90636304776851, 28.205074019810965)
                                                                    ,4326) 
                                                            AS geography)) limit 1;";

select a.id as projectid ,b.code as requestcode,a.source_type as source,st_astext(a.center) from project as a ,requestcode as b where a.id=b.projectid ;
        

insert into project(source_type,center) values('BING_ROAD',st_setsrid(st_makepoint(112,29),4326))  returning id ;
insert into requestcode(id,projectid,code) values(2,2,'asfddsdfgdsfgfdsfgfdsafgd') ;