package com.jtfr.chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 时间服务器 Netty版本
 * 
 * @author chenkangming
 *
 */
public class TimeServer {

    public void bind(int port) {
        // 配置服务端的NIO线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(); // 接收客户端连接
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(); // SocketChannel网络读写
        try {
            ServerBootstrap b = new ServerBootstrap();// 启动NIO服务端的辅助启动类
            b.group(bossGroup, workerGroup) // NIO 线程组当作入参传递到 ServerBootstrap
                .channel(NioServerSocketChannel.class) // 创建 channel为 NioServerSocketChannel
                .option(ChannelOption.SO_BACKLOG, 1024) // Backlog 设置为 1024，三次握手最大队列长度，默认50
                .childHandler(new ChildChannelHandler()); // 绑定IO事件处理类。例如记录日志、对消息进行编解码。
            // 绑定端口，同步等待成功，返回一个 ChannelFuture对象。
            ChannelFuture f = b.bind(port).sync();
            // 等待服务端监听端口关闭，然后main方法才推出。
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            // 增加两个解码器LineBasedFrameDecoder和StringDecoder
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new TimeServerHandler());
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
        new TimeServer().bind(port);
    }
}
