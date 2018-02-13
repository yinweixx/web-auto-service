package com.anyun.web.manage.service;

import com.anyun.cloud.web.common.task.YwAz;
import com.anyun.cloud.web.common.task.YwMethod;
import com.anyun.cloud.web.common.task.YwParams;

@YwAz(name="ywTest")
public class IndexController {

    @YwMethod(name="ywMethod")
    public void index_Test(@YwParams("a") String a, @YwParams("b") int b){
        System.out.println("a is " + a);
        System.out.println("b is " + b);
        System.out.println("method.test");
    }
}
