package com.anyun.cloud.web.service.common.entity;

import java.lang.reflect.Method;
import java.util.List;

public class MethodParams {
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
