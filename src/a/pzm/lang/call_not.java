package a.pzm.lang;

import java.util.LinkedHashMap;

final public class call_not extends call{
	private static final long serialVersionUID=1;
	final public static int op=0x60;
	public call_not(statement parent,LinkedHashMap<String,String>annotations,reader r){
		super(parent,annotations,"not",r);
	}
	@Override public void binary_to(xbin x){
		ensure_arg_count(1);
		final String rd=arguments.get(0).token;
//		if(!x.vspc().is_declared(rd))
//			throw new compiler_error(arguments.get(0),"var '"+rd+"' not declared",x.toString());
		final int rdi=x.vspc().get_register_index(this,rd);
//		final int zni=apply_znxr_annotations_on_instruction(op|(rdi&63)<<14);
//		x.write(zni,this);
		x.write_op(this,op,0,rdi);
	}
}