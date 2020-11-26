<?php


include_once './Server.php';

session_start();

if(!$_REQUEST["request"]){
    $ret = array(
        "success" => false,
        "message" => "Request invalid"
    );
    
    echo json_encode($ret);
    exit(1);
}


$reqjson = json_decode($_REQUEST["request"]);
if(!$reqjson){
    $ret = array(
        "success" => false,
        "message" => "invalid json string!"
    );
    exit(1);
}
$reqtype = explode("_", $reqjson->type);
$reqtype = $reqtype[0];
$sv = false;
switch ($reqtype){
    case "USER":
        $sv = new ServerUser();
        break;
    case "FEATURE":
        $sv = new ServerFeature();
        break;
    default :
        $sv = false;
        break;
}

if(!$sv){
    echo json_encode(array(
        "success" => false,
        "message" => "Invalid request !"
    ));
    return;
}

$sv->setRequest($reqjson);
$sv->run();

echo json_encode($sv->getResponse());