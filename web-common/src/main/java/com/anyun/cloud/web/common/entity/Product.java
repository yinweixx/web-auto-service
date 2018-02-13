package com.anyun.cloud.web.common.entity;

import com.iciql.Iciql;

@Iciql.IQView(name = "Product")
public class Product {

    @Iciql.IQColumn
    public int id;

    @Iciql.IQColumn
    public String name;
}
