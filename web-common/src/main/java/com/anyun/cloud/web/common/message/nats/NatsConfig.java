package com.anyun.cloud.web.common.message.nats;

public class NatsConfig {
    public static final String DEFAULT_SUBJECT_SERVICE = "core-service-container";
    private String url;
    private String subject;

    public NatsConfig(String url){
        this.url = url;
        this.subject = DEFAULT_SUBJECT_SERVICE;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
