package com.jtfr.chapter3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * TimeServerHandler 继承 ChannelHandlerAdapter 
 * 用于对网络事件进行读写操作，通常只要关注 channelRead和exceptionCaught方法
 *
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	    ByteBuf buf = (ByteBuf) msg; // msg 转换成 ByteBuf 对象，类似JDK的 ByteBuffer，更加强大就是
	    byte[] req = new byte[buf.readableBytes()]; // 获取缓冲区的字节数，根据字节数创建数组。
	    buf.readBytes(req); // 通过readBytes 复制到新建的byte数组 req 中
	    String body = new String(req, "UTF-8"); // 通过 new String 构造函数获取请求信息。
	    System.out.println("The time server receive ordeer : " + body); // 打印请求信息
	    
	    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
	        System.currentTimeMillis()).toString() : "BAD ORDER"; // 判断是 QUERY TIME ORDER 返回当前时间
	    currentTime = currentTime + System.getProperty("line.separator");
	    ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes()); // 写入缓冲区
	    ctx.writeAndFlush(resp); // 发送消息给客户端。
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	    ctx.flush(); // 将消息发送队列的消息写入到 SocketChannel 发送给对对方。考虑性能，netty的write方法不直接将消息写入SocketChannel中。
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	    ctx.close(); // 发生异常的时候，关闭ChannelHandlerContext，释放和ChannelHandlerContext相关的资源。
	}
}
