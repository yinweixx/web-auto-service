package com.anyun.cloud.web.service.common;

import com.anyun.cloud.web.common.TestService;

@Service(name="test-service")
public class SecondTestService implements TestService {
    @Override
    public void test() {
        System.out.println("second");
    }
}
