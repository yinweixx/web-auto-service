package com.anyun.cloud.web.service.common.context;

import com.anyun.cloud.web.service.common.entity.ClassDescription;
import com.anyun.cloud.web.service.runtime.YwazServiceClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DefaultApplicationContext implements ApplicationContext<YwazServiceClassLoader>{
    public static final String PROR_SERVICE_PERSISTENCE = "service.run.persistence";
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApplicationContext.class);
    private ClassDescription servicesDescription;
    private Map<String,String> systemEnvs;
    private YwazServiceClassLoader classLoader;

    public DefaultApplicationContext(Map<String,String> sysenvs){
        this.systemEnvs = sysenvs;
    }

    public ApplicationContext build(){
        String propFile = "application.properties";
//        classLoader =
        return null;
    }


    @Override
    public ClassDescription getServicesDescription() {
        return null;
    }

    @Override
    public YwazServiceClassLoader reloadApplicationClassLoader() {
        String appDir = systemEnvs.get(RuntimeContextBuilder.KEY_APP_DIR);

        return null;
    }

    public YwazServiceClassLoader buildServiceClassLoader(String jarPath) {
        return classLoader;
    }
}
