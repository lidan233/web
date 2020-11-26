<?php

include_once './Server.php';

/**
 * Description of ServerUser
 *
 * @author pcwang
 */
class UserServer extends Server {

    public function process() {

        switch ($this->_requestJson->type) {
            case "USER_LOGIN":
                $this->login();
                break;
            case "USER_REGISTER":
                $this->register();
                break;
        }
    }

    protected function login() {
        $this->_connection->beginTransaction();
//        $sql = "select count(1) from ce_users where username = $1 and password = $2 ";
        $sql = "select * from  ce_login($1,$2) as a(userid varchar,loginkey varchar)";
        $rows = $this->query($sql, array(
            $this->_data->username,
            $this->_data->password
        ));
        if(!$rows){
            return;
        }
        $this->makeSuccessResponse("OK", array(
            "userid" => $rows[0]["userid"],
            "loginkey" => $rows[0]["loginkey"]
        ));
        $this->_connection->endTransaction();
    }

    /**
     * 用户注册
     * 请求信息为：
     * {
     *  type:"USER_LOGIN",
     *  data:{
     *      username:"aaaa",
     *      password:"aaaafsasf",
     *      email:"aasdsdffdsf@163.com",
     *      mobile:"asdfasffs",
     *      verifycode:"aaaaa"
     *  }
     * }
     */
    protected function register() {

        if (!isset($this->_data->mobile)) {
            $mobile = "";
        } else {
            $mobile = $this->_data->mobile;
        }

        $this->_connection->beginTransaction();

        $sql = "select ce_create_user($1,$2,$3,$4,$5)";
        $this->_connection->query($sql, array(
            $this->_data->username,
            $this->_data->password,
            $this->_data->email,
            $mobile,
            $this->_data->verifycode
        ));
        if ($this->_connection->hasError()) {
            $this->makeFailureResponse($this->_connection->errorMessage());
            $this->_connection->endTransaction();
            return;
        }
        $rows = $this->_connection->getResultAsJson();
        $this->_connection->clearResult();

        $this->_connection->endTransaction();

        $this->_responseJson = array(
            "success" => true,
            "message" => "您已注册成功，请记住您的用户名和密码。如忘记，可通过电子信箱找回"
        );
    }

}
