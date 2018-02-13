package com.anyun.cloud.web.service.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class YwazServiceClassLoader extends URLClassLoader{
    private static final Logger LOGGER = LoggerFactory.getLogger(YwazServiceClassLoader.class);
    public static final String PREFIX_SERVICE_PKG = "service.package.";
    public static final String PREFIX_TASK_PKG = "task.package.";

    public YwazServiceClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public YwazServiceClassLoader(URL[] urls) {
        super(urls);
    }

    public YwazServiceClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public YwazServiceClassLoader(String name, URL[] urls, ClassLoader parent) {
        super(name, urls, parent);
    }

    public YwazServiceClassLoader(String name, URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(name, urls, parent, factory);
    }
}
