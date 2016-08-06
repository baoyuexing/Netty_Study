/**
 * 
 * @author baoyx
 */
package netty.example;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author baoyx
 *
 */
public class EchoClient {
    private final int port;
    private String host;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        // EventLoopGroup可以理解为一个线程池，用来处理连接、接受数据、发送数据
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 引导客户端程序
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    // 客户端成功连接服务器之后，会被执行
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            // Netty程序都是基于ChannelPipeline
                            // ChannelPipeline的作用我们可以理解为用来管理ChannelHandler的一个容器
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            // Bootstarp.connect来连接服务器
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] agrs) throws InterruptedException {
        new EchoClient("127.0.0.1", 20000).start();
    }


}
