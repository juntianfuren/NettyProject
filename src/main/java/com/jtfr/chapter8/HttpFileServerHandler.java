/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2020. All Rights Reserved.
 */

package com.jtfr.chapter8;

import java.io.File;

import org.omg.CORBA.Request;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String url;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.decoderResult().isSuccess()) {
            sendError(ctx, "BAD_REQUEST");
            return ;
        }
//        if (request.method() ) {
//        }
        String uri = request.uri();
        String path = sanitizeUri(uri);
        if (path == null) {
            sendError(ctx, "FORBIDDEN");
        }
        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, "NOT_FOUND");
            return;
        }
        if (file.isDirectory()) {
//            if (uri.endsWith(suffix)) {
//                
//            }
        }
    }

    private String sanitizeUri(String uri) {
        // TODO Auto-generated method stub
        return null;
    }

    private void sendError(ChannelHandlerContext ctx, String string) {
        // TODO Auto-generated method stub
        
    }
}
