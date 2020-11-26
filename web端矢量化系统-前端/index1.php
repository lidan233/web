<?php
    header('Access-Control-Allow-Origin:*');
    header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");
    header('Content-Type:application/json; charset=utf-8');



    // if(!isset($_SESSION))
    // {
    //     session_start() ;
    // }


    $host        = "host=127.0.0.1";
    $port        = "port=5432";
    $dbname      = "dbname=gisdb";
    $credentials = "user=postgres password=123456";


    $db = pg_connect( "$host $port $dbname $credentials"  );
    if(!$db){
        echo "Error : Unable to open database\n";
        exit ;
    } else {

    }

    if($_POST['do']=="get_extent")
    {
        $sql = "select st_x(extent_min) as minx,st_y(extent_min) as miny,
                       st_x(extent_max) as maxx,st_y(extent_max) as maxy
                    from
                        project where id=".$_POST['projectid'].";";

         $ret = pg_query($db,$sql) ;
        if(!$ret)
        {
            echo pg_last_error($db);
            exit;
        }

        $result = pg_fetch_array($ret) ;
        echo json_encode($result) ;


    }else if($_POST['do']=="get_info_project")
    {
        $sql = "select id as id,st_x(center) as x,st_y(center) as y,source_type from project where id=".$_POST['projectid'].";" ;
        $ret = pg_query($db,$sql) ;
        if(!$ret)
        {
            echo pg_last_error($db);
            exit;
        }

        $result = pg_fetch_array($ret) ;
        echo json_encode($result) ;

    }if($_POST['do']=="create_project"){
        $source_kind = $_POST['source_kind'] ;
        $coordinate = $_POST['coordinate'] ;
        $extent = $_POST['extent'] ;

        $sql =  "insert into project(source_type,center,extent_max,extent_min)
                    values('".$source_kind.   //双引号会替换变量的值，而单引号会把它当做字符串输出.把变量和特殊字符在双引号内将导致打印出它们所表示的值，而不是它们的字面值。
                    "',st_setsrid(st_makepoint(".
                    $coordinate[0].",".$coordinate[1].
                    "),4326),st_setsrid(st_makepoint(".
                    $extent[0].",".$extent[1].
                    "),4326),st_setsrid(st_makepoint(".
                    $extent[2].",".$extent[3].
                    "),4326)) returning id;" ;
        $ret = pg_query($db,$sql) ;
        $ret = pg_fetch_array($ret) ;


        $suijicode = md5(time());
        $find_sql = "select * from requestcode where code='".$suijicode."';" ;
        $find_ret = pg_query($db,$find_sql) ;
        while($find = pg_fetch_array($find_ret))
        {
            $suijicode = md5(time());
        }

        $projectid = $ret['id'] ;
        $all_sql = "insert into requestcode(projectid,code) values(".$projectid.",'".$suijicode."');" ;
        $all_ret = pg_query($db,$all_sql) ;
        if(!$ret){
            echo pg_last_error($db);

            exit;
         }
         echo $suijicode ;


    }if($_POST['do']=="get_project"){
        $sql = "select a.id as projectid ,b.code as requestcode,a.source_type as source,st_astext(a.center) as center from project as a ,requestcode as b where a.id=b.projectid" ;
        $ret = pg_query($db,$sql) ;
        $give = pg_fetch_all($ret) ;
        echo json_encode($give) ;

    }else if($_POST['do']=="login")
    {

        $name = $_POST['name'] ;
        $passwd = $_POST['passwd'] ;

        if($name!="admin")
        {
            $sql = "select * from all_user where name='".$name."';" ;
            // echo $sql ;
            $ret = pg_query($db,$sql) ;

            if( $ret = pg_fetch_array($ret))
            {
                if($ret['passwd']==$passwd)
                {
                    echo json_encode($ret) ;
                    exit ;
                }else{
                    echo "passwd" ;
                    exit ;
                }
            }else{
                echo "no";
                exit ;
            }
        }else{
            $sql = "select * from admin where name='admin';" ;
            $ret = pg_query($db,$sql) ;

            if( $ret = pg_fetch_array($ret))
            {
                if($ret['passwd']==$passwd)
                {
                    echo "admin";

                    exit ;
                }else{
                    echo $ret['passwd'] ;
                    echo "passwd" ;
                    exit ;
                }
            }
        }


    }else if($_POST['do']=='register')
    {
        $name = $_POST['name'] ;
        $passwd = $_POST['passwd'] ;
        $requestcode = $_POST['requestcode'] ;
        $ret = pg_query($db,"select * from requestcode where code ='".$requestcode."';");

        if(!$ret)
        {
            echo pg_last_error($db);
            exit;
        }

        $temp = pg_fetch_array($ret) ;

        $find_code = $temp['code'] ;
        $projectid = $temp['projectid'] ;

        if($find_code==$requestcode)
        {
            $sql = "insert into all_user(projectid,name,passwd) values(".$projectid.",'".$name."','".$passwd."');" ;

            $ret = pg_query($db,$sql) ;
            if(!$ret){
                echo pg_last_error($db);
                exit;
            }
            echo "ok" ;
            exit ;
        }

        echo "no";
        // echo "mother fucjk";

    }else if($_POST['do']=='getjson')
    {
        $sql ="SELECT jsonb_build_object(
            'type',     'FeatureCollection',
            'features', jsonb_agg(features.feature)
        )
        FROM (
          SELECT jsonb_build_object(
            'type',       'Feature',
            'id',         id,
            'geometry',   ST_AsGeoJSON(geom)::jsonb,
            'properties', to_jsonb(inputs) - 'id' - 'geom'
          ) AS feature
          FROM (SELECT * FROM temp_points where projectid=".$_POST['projectid'].") inputs) features; ;" ;

            $sql1 ="SELECT jsonb_build_object(
                'type',     'FeatureCollection',
                'features', jsonb_agg(features.feature)
            )
            FROM (
            SELECT jsonb_build_object(
                'type',       'Feature',
                'id',         id,
                'geometry',   ST_AsGeoJSON(geom)::jsonb,
                'properties', to_jsonb(inputs) - 'id' - 'geom'
            ) AS feature
            FROM (SELECT * FROM temp_lines where projectid=".$_POST['projectid'].") inputs) features; ;" ;



        $ret = pg_query($db, $sql);
        if(!$ret){
           echo pg_last_error($db);
           exit;
        }

        $result = array() ;
        while($row=pg_fetch_row($ret))
        {
            $result[] = $row[0];
            echo $row[0];
        }
        pg_close($db);
        $db = pg_connect( "$host $port $dbname $credentials"  );
        $ret1 = pg_query($db, $sql1);
        if(!$re1){
           echo pg_last_error($db);
           exit;
        }
        while($row=pg_fetch_row($ret1))
        {
            $result[] = $row[0];
            echo $row[0];
        }
        // echo json_encode($result);
        pg_close($db);
    }else if($_POST['do']=='insert')
    {
        // echo "begin ";

        if($_POST['type']=="POINT")
        {
            $begindata = "with data as( select '".$_POST['geojson']."'::json AS fc) " ;
            $middledata = "insert into temp_points(geom) select  ST_SetSRID( ST_AsText(ST_GeomFromGeoJSON(feat->>'geometry')),4326) FROM (
            SELECT json_array_elements(fc->'features') AS feat
            FROM data) AS f returning id;";

            $stmt = pg_prepare($db,"myquery","update  temp_points set projectid=$1 where id =$2") ;
            $stmt1 = pg_prepare($db,"myquery1","update temp_points set user_id=$1 where id =$2") ;
            $sql = $begindata.$middledata;
            // echo $sql;
        }else if($_POST['type']=="LINESTRING")
        {
            $begindata = "with data as( select '".$_POST['geojson']."'::json AS fc) " ;
            $middledata = "insert into temp_lines(geom) select  ST_SetSRID( ST_AsText(ST_GeomFromGeoJSON(feat->>'geometry')),4326) FROM (
            SELECT json_array_elements(fc->'features') AS feat
            FROM data) AS f returning id;";
            $stmt = pg_prepare($db,"myquery","update  temp_lines set projectid=$1 where id =$2;") ;
            $stmt1 = pg_prepare($db,"myquery1","update temp_lines set user_id=$1 where id =$2;") ;
            $sql = $begindata.$middledata;
            // echo $sql;
        }else if($_POST['type']=="POLYGON")
        {
            $begindata = "with data as( select '".$_POST['geojson']."'::json AS fc) " ;
            $middledata = "insert into temp_polygon(geom) select  ST_SetSRID( ST_AsText(ST_GeomFromGeoJSON(feat->>'geometry')),4326) FROM (
            SELECT json_array_elements(fc->'features') AS feat
            FROM data) AS f returning id;";

            $stmt = pg_prepare($db,"myquery","update temp_polygon set projectid=$1 where id =$2;") ;
            $stmt1 = pg_prepare($db,"myquery1","update temp_polygon set user_id=$1 where id =$2;") ;
            $sql = $begindata.$middledata;
            // echo $sql;
        }else{
            echo "error";
            exit ;
        }

        $ret = pg_query($db, $sql);
        if(!$ret){
            echo pg_last_error($db);
            exit;
        }

        // $result = pg_fetch_all($ret) ;

        // echo json_encode($result) ;

        $rows = [] ;
        while($row = pg_fetch_array($ret))
        {
            $rows[] = $row[0];
            $end = pg_execute($db, "myquery",array($_POST['projectid'],$row[0]));
            $end1 = pg_execute($db, "myquery1",array($_POST['userid'],$row[0]));

        }
       echo json_encode($rows) ;
       pg_close($db);
    }else if($_POST['do']=='delete')
    {
        if($_POST['type']=="POINT")
        {
            $stmt = pg_prepare($db,"myquery","delete from temp_points where id = $1") ;
        }else if($_POST['type']=="LINESTRING")
        {
            $stmt = pg_prepare($db,"myquery","delete from temp_lines where id = $1") ;
        }else if($_POST['type']=="POLYGON")
        {
            $stmt = pg_prepare($db,"myquery","delete from temp_polygon where id = $1") ;
        }
        if($_POST['id'])
        {
            $ret = pg_execute($db, "myquery",array($_POST['id']));
        }else{
            echo "error" ;
            exit ;
        }

        if(!$ret){
           echo pg_last_error($db);
           exit;
        }
        pg_close($db);
    }else if($_POST['do']=='select')
    {
        $coordinate = $_POST['coordinate'] ;
        $distance = $_POST['radius'] ;

        $table = "" ;
        if($_POST['type']=="POINT")
        {
            $table = "temp_points" ;
        }else if($_POST['type']=="LINESTRING")
        {
            $table = "temp_lines" ;
        }else if($_POST['type']=="POLYGON")
        {
            $table = "temp_polygon";
        }else{
            echo "error";
            exit ;
        }
        // echo $table;

        $sql = "select a.id as id,a.projectid as projectid,st_astext(a.geom) as feature ,b.name as name
                    from ".$table." as a , all_user as b where
                            ST_Intersects(
                                ST_Buffer(
                                     CAST(
                               ST_SetSRID(
                             ST_MakePoint(".$coordinate[0].", ".$coordinate[1].")
                                       ,4326)
                                AS geography)
                                         ,".$distance.")
                                       ,geom)=true and a.user_id=b.id
                                       order by st_distance(geom, CAST(
                                                            ST_SetSRID(
                                                          ST_MakePoint(".$coordinate[0].", ".$coordinate[1].")
                                                                    ,4326)
                                                            AS geography)) limit 1;";
        // echo $sql ;

        $ret = pg_query($db,$sql);
        //  $stmt = pg_prepare($db,"myquery1",$sql) ;


        // $ret = pg_execute($db,"myquery1",array($coordinate[0],$coordinate[1],$distance,$coordinate[0],$coordinate[1])) ;
        $ret_array = pg_fetch_all($ret) ;



        echo json_encode($ret_array) ;
        pg_close($db);
    }


?>
