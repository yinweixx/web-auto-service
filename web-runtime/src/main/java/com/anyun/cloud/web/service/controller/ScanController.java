package com.anyun.cloud.web.service.controller;

import com.anyun.cloud.web.common.task.YwAz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ScanController {
    private static Logger LOGGER = LoggerFactory.getLogger(ScanController.class);

    /**
     * 根据value获得对应的类
     */
    public void scanMethod(ClassLoader classLoader, List<Class> classList,String annotationClass){
        classList.stream().forEach(aClass -> {
//            YwAz annotation
        });
    }
}
