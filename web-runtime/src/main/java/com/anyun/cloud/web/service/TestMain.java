package com.anyun.cloud.web.service;

import com.anyun.cloud.web.service.service.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.Socket;

public class TestMain {
    public static void main(String[] args) throws Exception {
//        URL[] urls = new URL[]{
//                new File("/home/yw/IdeaProjects/anyun-cloud-web-main/web-manage/target/anyun-cloud-web-main-manage-1.0.0.jar").toURI().toURL()
//        };
//        URLClassLoader classLoader = new URLClassLoader(urls,TestMain.class.getClassLoader());
//        Class testServiceImplClass = Class.forName("com.anyun.service.DefaultTestService",false,classLoader);
//        System.out.println(testServiceImplClass);
//        TestService service = (TestService)testServiceImplClass.getDeclaredConstructor().newInstance();
//        service.test();


        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup,workGroup);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
                public void initChannel(SocketChannel ch){
//                    ch.pipeline().addLast("decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
//                            0,4,0,4));
//                    ch.pipeline().addLast("encoder",new LengthFieldPrepender(4,
//                            false));
                    ch.pipeline().addLast(new DiscardServerHandler());
                    ch.pipeline().addLast("key",new DiscardServerHandler());
                    ch.pipeline().addLast("may",new DiscardServerHandler());
//                    ch.pipeline().addLast(new KMCHandler());

                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8081).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

        Socket socket = new Socket();
    }
}
