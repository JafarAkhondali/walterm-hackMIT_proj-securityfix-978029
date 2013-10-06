package translator;

import java.io.PrintWriter;
import java.util.*;

public class Translator {
	
	
	private final PrintWriter out;
	
	public Translator(PrintWriter out) {
		this.out = out;
	}
	
	public void translateCursor(String[] commands) {
		// TODO Auto-generated method stub
		System.out.println(commands[0] + ' ' + commands[1] + ' ' + commands[2]);
		out.println("<html><head><title>Testing</title></head><body><p>"+ commands[0] + ' ' + commands[1] + ' ' + commands[2] +"</p></body></html>");
	}

	
	public void translateMove(String[] commands) {
		// TODO Auto-generated method stub
		
	}

	public void translateRotate(String[] commands) {
		// TODO Auto-generated method stub
		
	}

	public void translateZoom(String[] commands) {
		// TODO Auto-generated method stub
		
	}
	
	public void translateCameraChange(String[] commands) {
		//construct a string to send to java
		
		
	}



	

	
	
	
	
}
