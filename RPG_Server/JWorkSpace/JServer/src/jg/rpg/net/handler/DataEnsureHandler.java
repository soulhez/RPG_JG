package jg.rpg.net.handler;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import jg.rpg.entity.MsgEntity;
import jg.rpg.utils.MsgUtils;

public class DataEnsureHandler extends SimpleChannelInboundHandler<Object> {
	private Logger logger = Logger.getLogger(getClass());

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws InterruptedException, UnsupportedEncodingException{
		ByteBuf in = (ByteBuf) msg;
		logger.debug(in.readableBytes());
		while(in.isReadable()){
			System.out.print((char)in.readByte());
		}
		MsgEntity _msg = new MsgEntity();
		StringBuffer sb = new StringBuffer();
		
		for(int i=0 ; i < 1024*1024*20; i++){
			sb.append("h");
		}
		_msg.setCotent(sb.toString());
		_msg.setType(12);
		ByteBuf buff = MsgUtils.serializerMsg(_msg);
		
		
		String str = "���Ѿ���������������"+" "+new Date()+" "+ctx.channel().localAddress();
		ByteBuf buf = Unpooled.buffer(str.getBytes().length);
		buf.writeBytes(str.getBytes("UTF-8"));
		ctx.writeAndFlush(buff);

	}

}