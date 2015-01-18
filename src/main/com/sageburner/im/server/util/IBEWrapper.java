package com.sageburner.im.server.util;

import com.sageburner.im.server.jpbc.IBE;

/**
 * Created by Ryan on 1/17/2015.
 */
public class IBEWrapper {
    private int key;
    private IBE ibe;

    public IBEWrapper(int key, IBE ibe) {
        this.key = key;
        this.ibe = ibe;
    }

    public int getKey() {
        return this.key;
    }

    public IBE getIBE() {
        return this.ibe;
    }
}
