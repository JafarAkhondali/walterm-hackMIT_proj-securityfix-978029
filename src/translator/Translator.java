package translator;

import java.io.PrintWriter;
import java.util.*;

public class Translator {
	
	
	private final PrintWriter out;
	
	public Translator(PrintWriter out) {
		this.out = out;
	}
	
	public void translateCursor(String x, String y) {
		//where to move block!		
			out.println("{'response':{'x':"+ x +",'y':"+ y +"}}");
		
	}

	
	public void translateClick(String prevX, String prevY) {
		//where to paste the block!		
			out.println("{'response':{'x':"+ prevX +",'y':"+ prevY +"}}");
	
	}
	

	
	
	
	
}
