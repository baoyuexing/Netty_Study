package netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 
 * @author baoyx
 *
 */

// 只处理有消息过来的。
// 当程序需要返回消息时可以在ChannelOutboundHandler中write/flush
// 业务逻辑都是在这个里面完成的，业务逻辑的生命周期也是在这个地方完成的
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /*
     * (non-Javadoc)
     * 
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    // 客户端连接服务器之后被调用
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(Unpooled.copiedBuffer("Netty rocks", CharsetUtil.UTF_8));
        ctx.flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    // 从服务器接收到数据后被调用
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println(
                "Client received: " + ByteBufUtil.hexDump(msg.readBytes(msg.readableBytes())));
    }


    @Override
    // 发生异常之后
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
