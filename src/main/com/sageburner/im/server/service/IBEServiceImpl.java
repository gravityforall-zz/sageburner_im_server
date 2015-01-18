package com.sageburner.im.server.service;

import com.sageburner.im.server.jpbc.IBE;
import com.sageburner.im.server.util.IBEWrapper;
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
public class IBEServiceImpl implements IBEService {

    private static final Logger logger = LoggerFactory.getLogger(IBEServiceImpl.class);
    private static Map<Integer, IBE> ibeMap = new HashMap<Integer, IBE>();

    @Override
    public IBEWrapper requestIBE() {
        IBE ibe = new IBE();
        IBEWrapper ibeWrapper = createIBEWrapper(ibe);

        logger.info("IBEServiceImpl::requestIBE: (new) key = " + ibeWrapper.getKey());
        return ibeWrapper;
    }

    @Override
    public IBEWrapper requestIBE(int key) {
        IBE ibe = getIBEFromMap(key);
        if (ibe == null) {
            return null;
        }

        IBEWrapper ibeWrapper = createIBEWrapper(key, ibe);

        logger.info("IBEServiceImpl::requestIBE: (existing) key = " + key);
        return ibeWrapper;
    }

    private IBEWrapper createIBEWrapper(IBE ibe) {
        SecureRandom sr = new SecureRandom();
        int key = sr.nextInt();

        addIBEToMap(key, ibe);

        IBEWrapper ibeWrapper = new IBEWrapper(key, ibe);

        logger.info("IBEServiceImpl::createIBEWrapper: ");
        return ibeWrapper;
    }

    private IBEWrapper createIBEWrapper(int key, IBE ibe) {
        IBEWrapper ibeWrapper = new IBEWrapper(key, ibe);

        logger.info("IBEServiceImpl::createIBEWrapper: ");
        return ibeWrapper;
    }

    private void addIBEToMap(int key, IBE ibe) {
        logger.info("IBEServiceImpl::addIBEToMap: add (pending) = " + key);
        ibeMap.put(key, ibe);
        logger.info("IBEServiceImpl::addIBEToMap: add (success) = " + key);
    }

    private IBE getIBEFromMap(int key) {
        logger.info("IBEServiceImpl::addIBEToMap: add (pending) = " + key);
        IBE ibe = ibeMap.get(key);
        logger.info("IBEServiceImpl::addIBEToMap: add (success) = " + key);
        return ibe;
    }
}
