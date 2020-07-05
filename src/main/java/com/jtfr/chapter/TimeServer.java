package com.jtfr.chapter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 时间服务器 Netty版本
 * 
 * @author chenkangming
 *
 */
public class TimeServer {

	public void bind(int port) {
		// 创建两个 NioEventLoopGroup 实例
		// boss线程组用于接受客户端的连接
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		// work线程组用于 SocketChannel的网路读写
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			// ServerBootstrap 用于启动 netty 服务端的辅助启动类
			ServerBootstrap b = new ServerBootstrap();
			// 传入 线程组
			b.group(bossGroup, workGroup)
				// 设置创建的 Channel 为 NioServerSocketChannel
				.channel(NioServerSocketChannel.class)
				// 配置 NioServerSocketChannel 的 TCP 将 backlog 设置为 1024
				.option(ChannelOption.SO_BACKLOG, 1024)
				// 绑定事件处理类。作用 处理网络 I/O 事件，列如 记录日志、对消息进行编解码
				.childHandler(new ChildChannelHandler());
			// 绑定端口，同步等待成功，返回 ChannelFuture 对象，用于 异步操作的通知回调
			ChannelFuture f = b.bind(port).sync();
			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 优雅退出，释放线程池资源。
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	/**
	 * 处理网络 I/O 事件
	 * @author MSI
	 *
	 */
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel sc) throws Exception {
			sc.pipeline().addLast(new TimeServerHandler());
		}
		
	}
	
	public static void main(String[] args) {
		int port = 8080;
		new TimeServer().bind(port);
	}

}
