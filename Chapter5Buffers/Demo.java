/**
 * 
 * @author baoyx
 */
package netty.example.Chapter5Buffers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author baoyx
 *
 */
public class Demo {
    public static void main(String[] agrs) {

        // ByteBuf buf = Unpooled.buffer(16);
        // while (buf.isReadable()) {
        // System.out.println(buf.readByte());
        // }
        Demo demo = new Demo();
        demo.name1();


    }

    public void name() {
        // create a ByteBuf of capacity is 16
        ByteBuf buf = Unpooled.buffer(16);
        // write data to buf
        for (int i = 0; i < 16; i++) {
            buf.writeByte(i + 1);
        }
        // read data from buf
        for (int i = 0; i < buf.capacity(); i++) {
            System.out.println(buf.getByte(i));
        }
    }

    public void name1() {
        ByteBuf buf = Unpooled.buffer(16);
        buf.writeByte(123456);
        int i = buf.readableBytes();
        System.out.println(buf.readerIndex());
        System.out.println(buf.readerIndex());
        System.out.println(buf.readerIndex());

        // while (buf.isReadable()) {
        // System.out.println(String.valueOf(buf.getByte(0)));
        // }



    }
}
