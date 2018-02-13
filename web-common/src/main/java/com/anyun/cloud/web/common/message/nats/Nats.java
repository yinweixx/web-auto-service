package com.anyun.cloud.web.common.message.nats;

import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;

import java.io.IOException;

public interface Nats {

    void connect() throws IOException;

    Connection getConnection();

    ConnectionFactory getConnectionFactory();

    NatsConfig getNatsConfig();
}
