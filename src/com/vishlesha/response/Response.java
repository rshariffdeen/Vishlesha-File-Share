package com.vishlesha.response;

import com.vishlesha.app.GlobalConstant;

/**
 * Created by ridwan on 1/1/16.
 */

abstract public class Response {
    protected String response;
    protected GlobalConstant globalConstant;
    protected boolean error;

    public Response(){
        globalConstant = new GlobalConstant();
    }

    public String getResponse(){
        return response;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

}
