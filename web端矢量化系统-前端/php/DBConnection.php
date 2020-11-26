<?php

/**
 * Description of DBConnection
 *
 * @author pcwang
 */
class DBConnection {

    /**
     *
     * @param string $host
     * @param string $port
     * @param string $dbname
     * @param string $username
     * @param string $password
     * 带参数调用形式：
     * new DBConnection($host, $port, $dbname, $username, $password)
     */
    public function __construct($host, $port, $dbname, $username, $password) {
        $this->_host = $host;
        $this->_port = $port;
        $this->_dbname = $dbname;
        $this->_user = $username;
        $this->_password = $password;
    }

    public function __destruct() {
        $this->close();
    }

    /**
     *
     * @return mixed, 连接成功返回连接，连接不成功，返回null
     */
    public function open() {
        $connstr = "host={$this->_host} "
                . "port={$this->_port} "
                . "dbname={$this->_dbname} "
                . "user={$this->_user} "
                . "password={$this->_password} ";
        $this->_connection = @pg_connect($connstr);
        if (!$this->_connection) {
            $this->_errorMessage = "Can't connect to database!";
        }

        return $this->_connection;
    }

    /**
     * 关闭数据库连接
     */
    public function close() {
        if ($this->_connection) {
            pg_close($this->_connection);
        }
        $this->_connection = false;
    }

    /**
     *
     * @return boolean,数据库连接是否打开
     */
    public function isOpened() {
        return $this->_connection !== null && $this->_connection !== false;
    }

    /**
     *
     * @return string 出错信息
     */
    public function errorMessage() {
        return $this->_errorMessage;
    }

    /**
     * 开始新的事务
     */
    public function beginTransaction() {
        $result = pg_query($this->_connection, "begin");
        pg_free_result($result);
    }

    /**
     * 关闭事务
     */
    public function endTransaction() {
        $result = pg_query($this->_connection, "end");
        pg_free_result($result);
    }

    /**
     *
     * @return boolean
     * example:
     *      query($sql)
     *      query($sql,$paramsarray)
     */
    public function query() {

        $this->clearResult();
        $nargs = func_num_args();
        if ($nargs === 0) {
            return;
        }

        $sql = "";
        $params = array();

        if ($nargs >= 1) {
            $sql = func_get_arg(0);
        }
        if ($nargs >= 2) {
            $params = func_get_arg(1);
        }

        $this->_result = @pg_query_params($this->_connection, $sql, $params);
        if (!$this->_result) {
            $this->_hasError = true;
            $this->_errorMessage = @pg_last_error($this->_connection);
            $p = strpos($this->_errorMessage,":");
            $q = strpos($this->_errorMessage,"CONTEXT",$p);
            $this->_errorMessage = trim(substr($this->_errorMessage, $p+1, $q-$p-1));
            return false;
        }


        return true;
    }


    /**
     *
     * @return json, 返回查询结构的json形式
     */
    public function getResultAsJson() {
        $nfields = pg_num_fields($this->_result);
        $fieldnames = array();
        for ($i = 0; $i < $nfields; $i++) {
            $fieldnames[$i] = pg_field_name($this->_result, $i);
        }

        $json = array();


        for ($j = 0; $j < pg_num_rows($this->_result); $j++) {
            $row = pg_fetch_row($this->_result);
            $json[$j] = array();
            for ($i = 0; $i < $nfields; $i++) {
                $json[$j][$fieldnames[$i]] = $row[$i];
            }
        }
        return $json;
    }


    /**
     * 清除数据库查询结果
     */
    public function clearResult() {
        if ($this->_result) {
            pg_free_result($this->_result);
        }
        $this->_result = NULL;
    }

    public function hasError(){
        return $this->_hasError;
    }

    protected $_host = "";
    protected $_port = "";
    protected $_dbname = "";
    protected $_user = "";
    protected $_password = "";
    protected $_connection = false;
    protected $_errorMessage = "";
    protected $_result = null;
    protected $_hasError = false;

}
