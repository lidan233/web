<?php

    $host        = "host=127.0.0.1";
    $port        = "port=5432";
    $dbname      = "dbname=gisdb";
    $credentials = "user=lidan password=";

    $db = pg_connect( "$host $port $dbname $credentials"  );
    if(!$db){
        echo "Error : Unable to open database\n";
    } else {
        
    }

    // $sql = "insert into temp(lidan) values('fuck'),('fuck1') returning id" ;  
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
      FROM (SELECT * FROM temp_points where projectid = $1 ) inputs) features; ;" ;

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
        FROM (SELECT * FROM temp_lines where projectid= $1) inputs) features; ;" ;



    $ret = pg_prepare($db,"lidan", $sql);
    $ret =pg_execute($db,"lidan",array(1)) ;
    if(!$ret){
       echo pg_last_error($db);
       exit;
    } 

    $result = array() ;
    while($row=pg_fetch_row($ret))
    {
        $result[] = $row[0];
        echo $row[0] ;
    }
    // pg_close($db);
    // $db = pg_connect( "$host $port $dbname $credentials"  );
    echo "fuck";
    $ret1 = pg_prepare($db,"lidan2", $sql1);
    $ret1 =pg_execute($db,"lidan2",array(1)) ;
    if(!$re1){
       echo pg_last_error($db);
       exit;
    } 
    while($row=pg_fetch_row($ret1))
    {
        $result[] = $row[0];
        echo $row[0];
    }
    echo json_encode($result);
    pg_close($db);

?>