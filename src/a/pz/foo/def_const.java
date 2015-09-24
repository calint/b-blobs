package a.pz.foo;

import b.a;
import b.xwriter;

final public class def_const extends statement{
	private static final long serialVersionUID=1;
	final private String ws_trailing;
	final expression expr;
	public def_const(a pt,String nm,String name,reader r,block b){
		super(pt,nm,no_annotations,"",r,b);
		expr=new expression(this,"e",no_annotations,r,b);
		ws_trailing=r.next_empty_space();
		r.toc.put("const "+name,this);
	}
	@Override public void source_to(xwriter x){
		super.source_to(x);
		expr.source_to(x);
		x.p(ws_trailing);
	}
}