package translator;

import java.util.*;

public class Translator {
	
	private String message;
	private String select;
	private String move;
	private String unselect;
	private String rotate;
	private String zoom;
	private ArrayList<String> messages = new ArrayList<String>(); 
	
	public Translator(String messageType, String selectCommand, String moveCommand, String unselectCommand, 
			String rotateCommand, String zoomCommand) {
		
		this.message = messageType;
		this.select = selectCommand;
		this.move = moveCommand;
		this.unselect = messageType;
		this.rotate = selectCommand;
		this.zoom = moveCommand;
		
		messages.add(message);
		messages.add(select);
		messages.add(move);
		messages.add(unselect);
		messages.add(rotate);
		messages.add(zoom);
		
		
	}
	
	
	
}
