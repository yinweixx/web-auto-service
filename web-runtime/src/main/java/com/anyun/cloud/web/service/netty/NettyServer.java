package com.anyun.cloud.web.service.netty;

import com.anyun.cloud.web.service.service.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public void run() throws Exception {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup,workGroup);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
                public void initChannel(SocketChannel ch){
                    ch.pipeline().addLast(new DiscardServerHandler());

                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8081).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
