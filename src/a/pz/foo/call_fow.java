package a.pz.foo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import b.a;
import b.xwriter;

final public class call_fow extends statement{
	private static final long serialVersionUID=1;
	final private ArrayList<expression> arguments=new ArrayList<>();
	final private String ws_after_expression_open,ws_after_expression_closed;
	final private block loop_code;
	public call_fow(a pt,String nm,LinkedHashMap<String,String> annotations,reader r,block b){
		super(pt,nm,annotations,"",r,b);
		ws_after_expression_open=r.next_empty_space();
		int i=0;
		while(true){
			if(r.is_next_char_expression_close()) break;
			final expression arg=new expression(this,"e"+i++,no_annotations,r,b);
			arguments.add(arg);
		}
		ws_after_expression_closed=r.next_empty_space();
		final ArrayList<String>declarations=new ArrayList<>();
		arguments.forEach(e->declarations.add(e.token));
		loop_code=new block(this,"b",r,declarations,b);
	}
	@Override public void binary_to(xbin x){
		final String table_name=arguments.get(0).token;
		final def_struct dt=(def_struct)x.toc.get("struct "+table_name);
		if(dt==null) throw new Error("struct not found: "+table_name);
		final ArrayList<expression>args;
		final ArrayList<String>allocated_registers=new ArrayList<>();
		final ArrayList<String>aliases=new ArrayList<>();
		if(arguments.size()==1){//select *
			args=new ArrayList<>();
			args.addAll(arguments);
			final String struct_name=args.get(0).token;
			final def_struct stc=(def_struct)x.toc.get("struct "+struct_name);
			if(stc==null)throw new compiler_error(this,"struct not declared yet",struct_name);
			for(def_struct_field col:stc.arguments){
				final String reg=x.allocate_register(this);
				allocated_registers.add(reg);
				x.alias_register(col.token,reg);
				aliases.add(col.token);
				final expression e=new expression(col.token);
				args.add(e);
			}
		}else{
			args=arguments;
			if(args.size()-1!=dt.arguments.size()) throw new Error("argument count does not match table: "+table_name);
		}
		args.subList(1,args.size()).forEach(e->blk.declarations.add(e.token));
		final String ra=x.allocate_register(this);
		allocated_registers.add(ra);
		final int rai=register_index(ra);
		final String rb=x.allocate_register(this);
		allocated_registers.add(rb);
		final int rbi=register_index(rb);
		final String rc=x.allocate_register(this);
		final int rci=register_index(rc);
		allocated_registers.add(rc);
		x.write(0|0x0000|(rai&15)<<12);//li(a dots)
		x.link_li(arguments.get(0).token);
		x.write(0);
		x.write(0|0x00c0|(rai&15)<<8|(rci&15)<<12);//ldc(c a)
		x.write(0|0x0100|(rci&15)<<12);//lp(c)
		x.write(0|0x00e0|(rai&15)<<8|(rbi&15)<<12);//tx(b a)
		for(expression e:args.subList(1,args.size())){
			final String reg=e.token;
			final int regi=x.register_index_for_alias(this,reg);//reg.charAt(0)-'a';
			x.write(0|0x00c0|(rai&15)<<8|(regi&15)<<12);//ldc(c regi)
		}
		loop_code.binary_to(x);
		x.write(0|0x00e0|(rbi&15)<<8|(rai&15)<<12);//tx(a b)
		for(expression e:args.subList(1,args.size())){
			final String reg=e.token;
			final int regi=x.register_index_for_alias(this,reg);//reg.charAt(0)-'a';
//			final int regi=reg.charAt(0)-'a';
			x.write(0|0x0040|(rai&15)<<8|(regi&15)<<12);//stc(a reg)
		}
		aliases.forEach(e->x.unalias_register(this,e));
		allocated_registers.forEach(e->x.free_register(e));
		x.write(4);//nxt
	}
		private int register_index(String reg_a){
		return reg_a.charAt(0)-'a';
	}

	@Override public void source_to(xwriter x){
		x.p("fow");
		super.source_to(x);
		x.p("(");
		x.p(ws_after_expression_open);
		x.tag("dr");
		arguments.get(0).source_to(x);
		x.tage("dr");
		arguments.subList(1,arguments.size()).forEach(e->e.source_to(x));
		x.p(")");
		x.p(ws_after_expression_closed);
		loop_code.source_to(x);
	}
}