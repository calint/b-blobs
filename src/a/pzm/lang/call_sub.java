package a.pzm.lang;

import java.util.LinkedHashMap;

final public class call_sub extends call{
	final public static int op=0x20;
	private static final long serialVersionUID=1;
	public call_sub(statement parent,LinkedHashMap<String,String>annot,reader r){
		super(parent,annot,"sub",r);
	}
	@Override public void binary_to(xbin x){
		ensure_arg_count(2);
		final int rai=x.vspc().get_register_index(this,arguments.get(0).token);
		final int rdi=x.vspc().get_register_index(this,arguments.get(1).token);
		x.write_op(this,op,rai,rdi);
//		final int i=op|(rai&63)<<8|(rdi&63)<<14;
//		x.write(apply_znxr_annotations_on_instruction(i),this);
	}
}