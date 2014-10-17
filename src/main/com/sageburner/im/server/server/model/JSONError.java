package com.sageburner.im.server.server.model;

import java.io.Serializable;

/**
 * Created by Ryan on 10/15/2014.
 */
public class JSONError implements Serializable {

    private String error;

    public JSONError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
