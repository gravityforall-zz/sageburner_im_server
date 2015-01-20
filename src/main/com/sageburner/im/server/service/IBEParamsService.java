package com.sageburner.im.server.service;

import com.sageburner.im.server.util.IBEParamsWrapper;

/**
 * Created by Ryan on 10/16/2014.
 */
public interface IBEParamsService {
    public IBEParamsWrapper requestIBEParams();
    public IBEParamsWrapper requestIBEParams(int key);
}
