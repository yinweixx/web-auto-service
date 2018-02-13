package com.anyun.cloud.web.service.common.context;

import java.util.Map;

public interface RuntimeContextBuilder {
    String KEY_APP_DIR = "ANYUN_CLOUD_APP_DIR";

    RuntimeContext build(Map<String,String> sysenv);
}
