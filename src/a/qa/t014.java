package a.qa;
import b.*;
final public class t014 extends a{static final long serialVersionUID=1;
	public a s;
	public void to(final xwriter x)throws Throwable{
		if(req.get().query().equals("rst")){
			s.set("");
			req.get().session().path("test.txt").rm();
		}
		x.p(req.get().session().href()).br();
		x.p("ĸoö: ");
		x.inptxt(s,this,"a",null).spc().ax(this,"a","post");
	}
	public void x_a(final xwriter x,final String q)throws Throwable{
		req.get().session().path("test.txt").append(s.toString(),"\n");
//        x.xu(s.clr());
		x.xfocus(s);
	}
}
