package a.pz.a;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import javax.imageio.ImageIO;
import b.a;
import b.xwriter;
final public class display extends a{
	public int[]ints;
	public int scl=2,wi=256,hi=128;
	public void to(final xwriter x)throws Throwable{
		x.p("<canvas class=\"display:block\" id=").p(id()).p(" width=").p(wi*scl).p(" height=").p(hi*scl).p("></canvas>");
		x.script();
		xupd(x);
		x.script_();
	}
	public void xupd(final xwriter x)throws Throwable{// refresh ram ui
		final int bytes_per_pixel=4;
		final ByteArrayOutputStream baos=new ByteArrayOutputStream(wi*hi*bytes_per_pixel);
		final BufferedImage bi=new BufferedImage(wi,hi,BufferedImage.TYPE_INT_ARGB);
		int k=0;
		for(int i=0;i<hi;i++){
			for(int j=0;j<wi;j++){
				final int d=ints[k++];
				final int b= (d    &0xf)*0xf;
				final int g=((d>>4)&0xf)*0xf;
				final int r=((d>>8)&0xf)*0xf;
//				final int a=(d>>12)&0xf;
				final int a=0xff;//? unused transparency
				final int argb=(a<<24)|(r<<16)|(g<<8)|b;
				bi.setRGB(j,i,argb);
			}
		}
		ImageIO.write(bi,"png",baos);
		final ByteBuffer bb_png=ByteBuffer.wrap(baos.toByteArray());
		final ByteBuffer bb_png_base64=Base64.getEncoder().encode(bb_png);
		final String str_png_base64=new String(bb_png_base64.array(),bb_png_base64.position(),bb_png_base64.limit());
		x.p("var c=$('").p(id()).p("');if(c){var d=c.getContext('2d');var i=new Image;i.onload=function(){d.drawImage(i,0,0,c.width,c.height);};i.src='data:image/png;base64,").p(str_png_base64).p("';}");
	}
//	public int get(final int addr){
//		final int a;
////		if(addr>=ram.length){
////			a=addr%ram.length;
////		}else
//			a=addr;
//		return bits[a];
//	}
//	xwriter x;// if set updates to ram display are written as js
//	public void set(final int addr,final int value){
//		final int a;
////		if(addr>=ram.length){
////			a=addr%ram.length;
////		}else
//			a=addr;
//		ints[a]=value;
//		if(x==null)return;
//		final int argb=value;
//		final String hex=Integer.toHexString(argb);
//		final String id=id();
//		x.p("{var c=$('").p(id).p("');if(c){var d=c.getContext('2d');");
//		x.p("d.fillStyle='#"+acore.fld("000",hex)+"';");
//		final int yy=a/wi;
//		final int xx=a%wi;
//		final int scl=2;
//		x.p("d.fillRect("+xx*scl+","+yy*scl+","+scl+","+scl+");");				
//		x.pl("}}");
//	}

	private static final long serialVersionUID=1;
}
