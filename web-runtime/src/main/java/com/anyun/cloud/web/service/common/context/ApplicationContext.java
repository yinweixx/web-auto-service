package com.anyun.cloud.web.service.common.context;

import com.anyun.cloud.web.service.common.entity.ClassDescription;

public interface ApplicationContext<T extends ClassLoader> {
    ClassDescription getServicesDescription();

    T reloadApplicationClassLoader();
}
