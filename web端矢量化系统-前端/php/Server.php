<?php

include_once './Configurations.php';
include_once './DBConnection.php';

/**
 * Description of Server
 *
 * @author pcwang
 */
class Server {

    protected $_connection = false;
    protected $_requestJson = null;
    protected $_responseJson = null;
    protected $_type = "";
    protected $_data=null;


    public function __construct() {
        $this->_responseJson = array(
            "success" => false,
            "message" => "Invalid request type!"
        );
    }

    public function __destruct() {
        if ($this->_connection) {
            $this->_connection->close();
        }
    }

    public function run() {
        $this->_connection = new DBConnection(
                HOST,PORT,DBNAME , USER, PASSWORD);
        if(!$this->_connection->open()){
            $this->_responseJson = array(
                "success" => false,
                "message" => $this->_connection->errorMessage()
            );
            return false;
        }

        $this->_type = $this->_requestJson->type;
        if(isset($this->_requestJson->data)){
            $this->_data = $this->_requestJson->data;
        }
        $this->process();

        $this->_connection->close();
        return true;
    }

    public function setRequest($reqjson) {
        
        $this->_requestJson = $reqjson;
    }

    public function getMessage() {
        return $this->_message;
    }

    public function getResponse() {
        return $this->_responseJson;
    }

    public function openDB() {

    }

    
    public function process(){
        
    }
    
    public function makeSuccessResponse($message,$data){
        $this->_responseJson = array(
            "success" => true,
            "message" => $message,
            "data" => $data
        );
    }
    
    
    public function makeFailureResponse($message){
        $this->_responseJson = array(
            "success" => false,
            "message" => $message
        );
    }
    
//    public function beginTransaction(){
//        $this->_connection->beginTransaction();
//    }
//    
//    public function endTransaction(){
//        $this->_connection->endTransaction();
//    }
    
    public function query(){
        $nargs = func_num_args();
        if ($nargs === 0) {
            return;
        }

        $sql = "";
        $params = array();

        if ($nargs === 1) {
            $sql = func_get_arg(0);
            $this->_connection->query($sql);
        }
        if ($nargs === 2) {
            $sql = func_get_arg(0);
            $params = func_get_arg(1);
            $this->_connection->query($sql, $params);
        }
        
        
        if ($this->_connection->hasError()) {
            $this->makeFailureResponse($this->_connection->errorMessage());
            $this->_connection->endTransaction();
            return false;
        }
        
        $rows = $this->_connection->getResultAsJson();
        $this->_connection->clearResult();

        return $rows;
    }
    
}
