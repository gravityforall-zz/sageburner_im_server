package com.sageburner.im.server.service;

import com.sageburner.im.server.jpbc.IBE;
import com.sageburner.im.server.jpbc.IBEParams;
import com.sageburner.im.server.util.IBEParamsWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryan on 10/16/2014.
 */
@Service
public class IBEParamsServiceImpl implements IBEParamsService {

    private static final Logger logger = LoggerFactory.getLogger(IBEParamsServiceImpl.class);
    private static Map<Integer, IBEParams> ibeParamsMap = new HashMap<Integer, IBEParams>();

    @Override
    public IBEParamsWrapper requestIBEParams() {
        IBE ibe = new IBE();

        IBEParamsWrapper ibeParamsWrapper = createIBEParamsWrapper(ibe.getIbeParams());

        logger.info("IBEServiceImpl::requestIBE: (new) key = " + ibeParamsWrapper.getKey());
        return ibeParamsWrapper;
    }

    @Override
    public IBEParamsWrapper requestIBEParams(int key) {
        IBEParams ibeParams = getIBEParamsFromMap(key);
        if (ibeParams == null) {
            return null;
        }

        IBEParamsWrapper ibeParamsWrapper = createIBEParamsWrapper(key, ibeParams);

        logger.info("IBEServiceImpl::requestIBE: (existing) key = " + key);
        return ibeParamsWrapper;
    }

    private IBEParamsWrapper createIBEParamsWrapper(IBEParams ibeParams) {
        SecureRandom sr = new SecureRandom();
        int key = sr.nextInt();

        addIBEParamsToMap(key, ibeParams);

        IBEParamsWrapper ibeParamsWrapper = new IBEParamsWrapper(key, ibeParams);

        logger.info("IBEServiceImpl::createIBEWrapper: ");
        return ibeParamsWrapper;
    }

    private IBEParamsWrapper createIBEParamsWrapper(int key, IBEParams ibeParams) {
        IBEParamsWrapper ibeParamsWrapper = new IBEParamsWrapper(key, ibeParams);

        logger.info("IBEServiceImpl::createIBEWrapper: ");
        return ibeParamsWrapper;
    }

    private void addIBEParamsToMap(int key, IBEParams ibeParams) {
        logger.info("IBEServiceImpl::addIBEToMap: add (pending) = " + key);
        ibeParamsMap.put(key, ibeParams);
        logger.info("IBEServiceImpl::addIBEToMap: add (success) = " + key);
    }

    private IBEParams getIBEParamsFromMap(int key) {
        logger.info("IBEServiceImpl::addIBEToMap: add (pending) = " + key);
        IBEParams ibeParams = ibeParamsMap.get(key);
        logger.info("IBEServiceImpl::addIBEToMap: add (success) = " + key);
        return ibeParams;
    }
}
