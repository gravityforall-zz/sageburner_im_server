package com.sageburner.im.server.service;

import com.sageburner.im.server.util.IBEWrapper;

/**
 * Created by Ryan on 10/16/2014.
 */
public interface IBEService {
    public IBEWrapper requestIBE();
    public IBEWrapper requestIBE(int key);
}
