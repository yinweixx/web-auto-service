package com.anyun.service;

import com.anyun.cloud.web.common.TestService;
import com.anyun.cloud.web.common.task.YwAz;


@YwAz(name="yw")
public class DefaultTestService implements TestService {
    @Override
    public void test() {
        System.out.println("test");
    }
}
