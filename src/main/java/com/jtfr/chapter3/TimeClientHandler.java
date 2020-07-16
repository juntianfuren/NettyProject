/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2020. All Rights Reserved.
 */

package com.jtfr.chapter3;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

    private final ByteBuf firstMessage;

    public TimeClientHandler() {
        byte[] req = "QUERY TIME ORDER".getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }
    
    /**
     * 客户端和服务端 TCP 链路建立成功之后，Netty的NIO线程会调用channelActive方法，发送查询指令给服务端。
     * 
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // ChannelHandlerContext 的 writeAndFlush 方法将请求消息发送给服务端。
        ctx.writeAndFlush(firstMessage);
    }
    
    /**
     * channelRead方法 读取服务端返回的应答消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Now is : "+body);
    }
    
    /**
     * 出现异常，打印异常日志，释放客户端资源
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 释放资源
        logger.warning("Unexpected exception from downstream : "+cause.getMessage());
        ctx.close();
    }
}

