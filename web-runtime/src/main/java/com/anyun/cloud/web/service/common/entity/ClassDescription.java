package com.anyun.cloud.web.service.common.entity;

import java.util.ArrayList;
import java.util.List;

public class ClassDescription {
    public static final String TYPE_SERVICE = "YwAz";
    private String type;
    private List<String> packageNames = new ArrayList<>();
    private List<Class> allClasses = new ArrayList<>();

    public static String getTypeService() {
        return TYPE_SERVICE;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getPackageNames() {
        return packageNames;
    }

    public void setPackageNames(List<String> packageNames) {
        this.packageNames = packageNames;
    }

    public List<Class> getAllClasses() {
        return allClasses;
    }

    public void setAllClasses(List<Class> allClasses) {
        this.allClasses = allClasses;
    }
}
