package a.pz.a;
import java.util.HashSet;
import java.util.Set;
import a.pz.foo.block;
import a.pz.foo.reader;
import b.a;
import b.xwriter;
final public class crun_source_editor extends a{
	int focusline;
	private int lstfocusline;
	//	public a brkpts;
	private Set<Integer> brkptsset=new HashSet<Integer>();
	public a src;
	//	public a resrc;
	public a sts;
	public line_numbers ln;
	public void to(final xwriter x) throws Throwable{
		x.style("def","font-weight:bold");//a name
//		x.style("fc","font-style: italic");//function name refered
		x.style("fc","font-weight:bold");//a name
		x.style("ac","color: gray");//assembler
//		x.style("ac","font-weight:bold");//a name
//		x.style("dr","font-style: italic");//data refered
		x.style("dr","font-weight:bold");//a name
		x.spanh(sts,"","width:5em;color:#800;font-weight:bold").ax(this,"f3",""," crun ","a").nl();
		x.table().tr().td("","text-align:right;padding-right:.5em");
		x.el(ln);
		ln.to(x);
		x.el_();
		x.td();
		x.inptxtarea(src);
		//		x.td().spaned(resrc);
		x.table_();
	}
	public boolean isonbrkpt(final int srclno){
		return brkptsset.contains(srclno);
	}
	//	synchronized public void x_brk(xwriter x,String s)throws Throwable{
	//		final int lno=Integer.parseInt(s);
	//		if(brkptsset.contains(lno)){
	//			brkptsset.remove(lno);
	//			brkpts.set(brkptsset.toString());
	//			x.pl("var e=$('"+id()+"').getElementsByTagName('ol')[0].getElementsByTagName('li')["+(lno-1)+"];e.className=e._oldcls;");
	//			return;
	//		}
	//		brkptsset.add(lno);
	//		brkpts.set(brkptsset.toString());
	//		x.pl("var e=$('"+id()+"').getElementsByTagName('ol')[0].getElementsByTagName('li')["+(lno-1)+"];e._oldcls=e.className;if(!e._oldcls)e._oldcls='';e.className='brk';");
	//	}
	public final static boolean ommit_compiling_source_from_disassembler=false;
	synchronized public void x_f3(xwriter x,String s) throws Throwable{
		final reader r=new reader(src.reader());
		try{
			final block el=new block(this,"b",r,block.no_declarations);
			final xwriter src=new xwriter();
			el.source_to(src);
//			resrc.set(src.toString());
			if(x==null) return;
			x.xu(sts.clr());
			//			el.source_to(x.xub(resrc,true,true));x.xube();
			ev(x,this,new prog(r.toc,el));
		}catch(Throwable t){
			b.b.log(t);
			if(x==null) return;
			x.pl("{var e=$('"+src.id()+"');e.selectionStart="+r.bm_nchar+";e.selectionEnd="+r.nchar+";}");
			x.xu(sts.set("line "+r.bm_line+": "+t.getMessage()));
			//			x.xalert(t.getMessage());
		}
		//		final program p;
		//		try{
		//			p=new program(src.str());
		//			p.build();
		//			sts.set(p.program_length+" ");
		//			final int prev_focus_line=ln.focus_line;
		//			ln.focus_line=0;
		//			if(x!=null){
		//				if(prev_focus_line!=ln.focus_line) x.xu(ln);
		//				x.xu(sts);
		//			}
		//		}catch(Throwable t){
		//			log(t);
		//			sts.set(t.toString()+"\n");
		//			if(t instanceof compiler_error) ln.focus_line=((compiler_error)t).source_location_line();
		//			if(x!=null) x.xu(ln).xu(sts);
		//			return;
		//		}
		//		if(!ommit_compiling_source_from_disassembler){
		//			try{
		//				final program p2=new program(p.toString());
		//				p2.build();
		//				if(!p.is_binary_equal(p2)) throw new Error("not binary equivalent");
		//			}catch(Throwable t){
		//				log(t);
		//				if(x!=null) x.xalert("reverse compilation failed: "+t.toString());
		//				return;
		//			}
		//		}
		//		ev(x,this,p);
	}
	public void xfocusline(xwriter x){
		if(lstfocusline!=0) x.pl("var e=$('"+id()+"').getElementsByTagName('ol')[0].getElementsByTagName('li')["+lstfocusline+"];e.className=e._oldcls;");
		x.pl("var e=$('"+id()+"').getElementsByTagName('ol')[0].getElementsByTagName('li')["+(focusline-1)+"];e._oldcls=e.className;e.className='stp';");
		lstfocusline=focusline;
	}
	private static final long serialVersionUID=11;
}
