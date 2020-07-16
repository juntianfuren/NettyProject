/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2020. All Rights Reserved.
 */

package com.jtfr.chapter3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

    public void connect(int port, String host) {
        // 配置客户端 NIO 线程组
        NioEventLoopGroup group = new NioEventLoopGroup();// 创建 IO读写的 线程组
        try {
            Bootstrap b = new Bootstrap(); // 创建客户端辅助启动类 Bootstrap
            b.group(group).channel(NioSocketChannel.class) // 设置Channel 为 NioSocketChannel
            .option(ChannelOption.TCP_NODELAY, true) // 设置 TCP 等待
            .handler(new ChannelInitializer<SocketChannel>() {  
                /**
                 * 匿名内部类，实现initChannel方法
                 * 作用：创建 NioSocketChannel 成功后，初始化她的ChannelHandler设置到ChannelPieline中。
                 */
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }});
            // 发起异步连接操作。客户端启动辅助类设置完成之后，调用 connect 方法发起异步连接，调用同步方法等待连接成功，返回ChannelFuture对象。
            ChannelFuture f = b.connect(host, port).sync();
            // 等待客户端链路关闭。
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            // 优雅退出，释放 NIO 线程组
            group.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {
                // 采用默认值
            }
        }
        new TimeClient().connect(port, "127.0.0.1");
    }
    
}
