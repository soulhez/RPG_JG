package jg.rpg.test.uitls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.msgpack.core.ExtensionTypeHeader;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

public class MsgPackTest {

	@Test
	public void testDefUse() throws IOException {
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
	        MessagePacker packer = MessagePack.newDefaultPacker(out);
	        packer
	                .packInt(1)
	                .packInt(2)
	                .packString("leo")
	                .packArrayHeader(2)
	                .packString("xxx-xxxx")
	                .packString("yyy-yyyy");
	        packer.close();

	        // Deserialize with MessageUnpacker
	        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(out.toByteArray());
	        
	        int id = unpacker.unpackInt();    
	        int num2 = unpacker.unpackInt(); // 1
	        String name = unpacker.unpackString();     // "leo"
	        int numPhones = unpacker.unpackArrayHeader();  // 2
	        String[] phones = new String[numPhones];
	        for (int i = 0; i < numPhones; ++i) {
	            phones[i] = unpacker.unpackString();   // phones = {"xxx-xxxx", "yyy-yyyy"}
	        }
	        unpacker.close();

	        System.out.println(String.format("id:%d, name:%s, phone:[%s]", id, name, join(phones)));
	        System.out.println(num2);
	}


    private static String join(String[] in)
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < in.length; ++i) {
            if (i > 0) {
                s.append(", ");
            }
            s.append(in[i]);
        }
        return s.toString();
    }
    
    
	@Test
	public void testDefUsePro() throws IOException {
		// Create a MesagePacker (encoder) instance
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MessagePacker packer = MessagePack.newDefaultPacker(out);

        // pack (encode) primitive values in message pack format
/*        packer.packBoolean(true);
        packer.packShort((short) 34);
        packer.packInt(1);
        packer.packLong(33000000000L);

        packer.packFloat(0.1f);
        packer.packDouble(3.14159263);
        packer.packByte((byte) 0x80);

        packer.packNil();

        // pack strings (in UTF-8)
        packer.packString("hello message pack!");

        // [Advanced] write a raw UTF-8 string
        byte[] s = "utf-8 strings".getBytes(MessagePack.UTF8);
        packer.packRawStringHeader(s.length);
        packer.writePayload(s);

        // pack arrays
        int[] arr = new int[] {3, 5, 1, 0, -1, 255};
        packer.packArrayHeader(arr.length);
        for (int v : arr) {
            packer.packInt(v);
        }

        // pack map (key -> value) elements
        packer.packMapHeader(2); // the number of (key, value) pairs
        // Put "apple" -> 1
        packer.packString("apple");
        packer.packInt(1);
        // Put "banana" -> 2
        packer.packString("banana");
        packer.packInt(2);

        // pack binary data
        byte[] ba = new byte[] {1, 2, 3, 4};
        packer.packBinaryHeader(ba.length);
        packer.writePayload(ba);*/

        // Write ext type data: https://github.com/msgpack/msgpack/blob/master/spec.md#ext-format-family
        byte[] extData = "custom data type".getBytes(MessagePack.UTF8);
        packer.packExtensionTypeHeader((byte) 1, 10);  // type number [0, 127], data byte length
        packer.writePayload(extData);

        // Succinct syntax for packing
        packer
                .packInt(1)
                .packString("leo")
                .packArrayHeader(2)
                .packString("xxx-xxxx")
                .packString("yyy-yyyy");
        packer.close();
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(out.toByteArray());
      //  String msg = ""+unpacker.unpackBoolean()+","+unpacker;
      //  String msg = ""+unpacker.unpackBinaryHeader() + ","+unpacker.unpackByte();
        String msg = ""+unpacker.getTotalReadBytes()+",";
        ExtensionTypeHeader headInfo = unpacker.unpackExtensionTypeHeader();
        headInfo.getType();
        byte[] buff = unpacker.readPayload(headInfo.getLength());
        msg = msg + new String(buff , "utf-8");
        
        unpacker.close();
        System.out.println(msg);
	}
}