package com.anyun.cloud.web.service.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;


public class DiscardServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf in = (ByteBuf) msg;
//        try {
//            //System.out.println("<read start>");
//            System.out.print(in.toString(io.netty.util.CharsetUtil.US_ASCII));
//            //while (in.isReadable()) {
//            //    System.out.print((char) in.readByte());
//            //}
//            //System.out.println("<read end>");
//            ctx.writeAndFlush(Unpooled.copiedBuffer(new HashMap().toString(), CharsetUtil.UTF_8));
//        } finally {
//            //System.out.flush();
//            ReferenceCountUtil.release(msg);
//        }
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        result.readBytes(result1);
        String resultStr = new String(result1);
        // 接收并打印客户端的信息
        System.out.println("Client said:" + resultStr);
        // 释放资源，这行很关键
        result.release();

        // 向客户端发送消息
        String response = "I am ok!";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void handlerAdded(ChannelHandlerContext arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {
        // TODO Auto-generated method stub

    }
}
