package com.sageburner.im.server.util;

import com.sageburner.im.server.jpbc.IBEParams;

/**
 * Created by Ryan on 1/17/2015.
 */
public class IBEParamsWrapper {
    private int key;
    private IBEParams ibeParams;

    public IBEParamsWrapper(int key, IBEParams ibeParams) {
        this.key = key;
        this.ibeParams = ibeParams;
    }

    public int getKey() {
        return this.key;
    }

    public IBEParams getIBE() {
        return this.ibeParams;
    }
}
