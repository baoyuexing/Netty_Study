package netty.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        // 用来接受和处理新连接
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            // 引导绑定和启动服务器
            ServerBootstrap b = new ServerBootstrap();
            // NioServerSocketChannel socket协议的通道
            b.group(group).channel(NioServerSocketChannel.class).localAddress(port)
                    // 利用chileHandler来执行所有的请求
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() + "started and listen on "
                    + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String agrs[]) throws Exception {
        new EchoServer(20000).start();
    }
}
