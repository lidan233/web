package com.lidan.Model;

public class Response {
    /**
     * 响应码
     */
    private String code = null;

    /**
     * 提示信息
     */
    private String message= null;



    /**
     * 结果
     */
    private Object result = null;

    public Response()
    {

    }

    public Response(String code ,String message,Object result)
    {
        this.code = code ;
        this.message = message ;
        this.result = result ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
