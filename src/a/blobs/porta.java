package a.blobs;
import static b.b.K;
import static b.b.pl;
import static b.b.stacktraceline;
import static b.b.strenc;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.Date;

import javax.imageio.ImageIO;
import a.pz.core;
import a.pz.foo.block;
import a.pz.foo.reader;
import a.pz.foo.xbin;
import b.threadedsock;
import b.websock;
final public class porta extends websock implements threadedsock{
	static final long serialVersionUID=1;
	private int frameno;
	final protected void onopened() throws Throwable{
	}
	public static String tostr(final ByteBuffer bb){
		try{
			return new String(bb.array(),bb.position(),bb.remaining(),strenc);
		}catch(Throwable t){
			throw new Error(t);
		}
	}
	protected void onmessage(final ByteBuffer bb) throws Throwable{
		switch(bb.get()){
		case '0'://keys
			final byte key=bb.get();
			frameno++;
//			co.ram[0x7fff]=key;
			//		final long t0=System.nanoTime();
			break;
		case '1'://compile
			final String src=tostr(bb);
			send_binary("1","frame #"+frameno+" "+new Date());
			break;
		default:
			throw new Error();
		}
		try{
		}catch(Throwable t){
			send_binary("2",stacktraceline(t));
			return;
		}
		send_binary("2"+frameno);
//		send_binary(refresh_display,);
	}
	private static final byte[]refresh_display=new byte[]{'0'};
}
