package a.pz.a;
import b.a;
import b.xwriter;
final public class rom extends a{
	public void to(final xwriter x){
//		x.divo(this,"float panel");
		x.p("   znxr ci.. aaaa dddd ").ax(this,"clr","x").p("   ").nl();
		x.ul();
		int row=0;
		final String id=id();
		for(final int d:ints){
			x.li();
			x.p(acore.fld("00",Integer.toHexString(row)));
			x.tag("span",id+"-"+row+"-s").spc().tage("span");
			for(int k=0,bit=1;k<16;bit<<=1){
				x.p("<a href=\"javascript:$x('").p(id).p("  ").p(row).p(" ").p(k).p("')\" id=").p(id).p("-").p(row).p("-").p(k).p(">");
				if((d&bit)==bit)
					x.p("o");
				else
					x.p(".");
				x.p("</a>");
				if(++k%4==0)
					x.spc();
			}
			final String wid=id();
			final int rowint=ints[row];
			final String rowinthex=Integer.toHexString(rowint);
			x.tago("span").attr("id",wid+"-"+row).tagoe().p(acore.fld("0000",rowinthex)).tage("span").nl();
			row++;
			if(row>=disppagenrows)
				break;
		}
		x.ul_();
		x.script();
		xfocus_on_binary_location(x,last_focus_on_binary_location);
		x.script_();
//		x.div_();
	}
	private int last_focus_on_binary_location;
	void xfocus_on_binary_location(xwriter x,final int binary_location){
		x.p("var e=$('").p(id()).p("').getElementsByTagName('li')[").p(last_focus_on_binary_location).p("];e.className=e._oldcls;");
		last_focus_on_binary_location=binary_location;
		x.p("var e=$('").p(id()).p("').getElementsByTagName('li')[").p(binary_location).p("];e._oldcls=e.className;e.className='stp';");
		x.nl();
	}
	public void x_clr(xwriter x,String s)throws Throwable{
		for(int i=0;i<ints.length;i++)ints[i]=0;
		if(x==null)return;
		x.xu(this);
	}
	public void x_(xwriter x,String s){
		final String[]a=s.split(" ");
		final int row=Integer.parseInt(a[0]);
		final int bit=Integer.parseInt(a[1]);
		final int msk=1<<bit;
		int v=ints[row];
		final boolean on=(v&msk)==msk;
		if(on)v=v&~msk;
		else v|=msk;
		ints[row]=v;
		x.xu(id()+"-"+row+"-"+bit,on?".":"o");
		x.xu(id()+"-"+row,acore.fld("0000",Integer.toHexString(ints[row])));
	}
//	public int get(final int row){return ints[row];}
//	public void set(final int row,final int value){ints[row]=value;}
//	public void rst(){
//		last_focus_on_binary_location=0;
//		for(int i=0;i<ints.length;i++)ints[i]=0;
//	}	
	public int disppagenrows=128;
	public int[]ints;
	private static final long serialVersionUID=1;
}
