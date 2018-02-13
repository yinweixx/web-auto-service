package com.anyun.cloud.web.common.message.nats;

import com.google.inject.Inject;
import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import io.nats.client.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DefaultNats implements Nats{
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNats.class);
    private NatsConfig natsConfig;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private MessageHandler messageHandler;

    @Inject
    public DefaultNats(NatsConfig natsConfig,MessageHandler messageHandler){
        this.natsConfig = natsConfig;
        this.messageHandler = messageHandler;
        connectionFactory = new ConnectionFactory(natsConfig.getUrl());
    }

    @Override
    public void connect() throws IOException {
        connection = connectionFactory.createConnection();
        connection.subscribe(natsConfig.getSubject(),natsConfig.getSubject(),messageHandler);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public NatsConfig getNatsConfig() {
        return natsConfig;
    }
}
