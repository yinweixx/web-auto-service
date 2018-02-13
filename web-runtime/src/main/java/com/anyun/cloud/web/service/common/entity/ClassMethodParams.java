package com.anyun.cloud.web.service.common.entity;

import java.lang.reflect.Method;
import java.util.List;

public class ClassMethodParams {
    public Class clazz;
    public List<Methods> methodList;

    public static class Methods{
        public Method method;
        public List<String> params;

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public List<String> getParams() {
            return params;
        }

        public void setParams(List<String> params) {
            this.params = params;
        }
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public List<Methods> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<Methods> methodList) {
        this.methodList = methodList;
    }
}
