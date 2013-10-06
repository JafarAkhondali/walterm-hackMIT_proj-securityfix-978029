package server;

import translator.Translator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerStart {
	
	private final ServerSocket serverSocket;
	private String previousState = "nothing"; 
	
	public ServerStart(int port) throws IOException {
		System.out.println("hi");
		serverSocket = new ServerSocket(port);
	}
	
	/**
	 * grabs the client socket and calls the connection handler
	 * @throws IOException
	 */
	public void serve() throws IOException {
		 while (true) {
	            // block until a client connects
	            final Socket socket = serverSocket.accept();
	            Thread thread = new Thread(new Runnable() {

	            	public void run() {
	            		try {
	            			handleConnection(socket);
	            		} catch (IOException e) {
							e.printStackTrace();
						} finally {
	            			try {
	            				socket.close();
	            			} catch (IOException e) {
	            				e.printStackTrace();
	            			}
	            		}
	            	}
	            });
	            thread.start();
		 }
		
	}
	
	/**
	 * Takes in the socket and reads the message. Sends the message to the translator and based on response
	 * send it to either web, or opencv
	 * @param socket
	 * @throws IOException 
	 */
	public void handleConnection(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		//grab message from client
		String message = in.readLine();
		
		//process it
		Map<String, String[]> processedMessage = processMessage(message);
	
		
		if (isValid(processedMessage)) {//if it's valid...
			//translate that shit and respond!
			Translator tm = new Translator(out);
			for (int i=0; i<processedMessage.size(); i++) {
//				System.out.println(processedMessage.get(String.valueOf(i))[0]);
				switch (processedMessage.get(String.valueOf(i))[0]) { // based on the status type, do one of the following
					case "cursor": tm.translateCursor(processedMessage.get(String.valueOf(i)));
						break;
					case "move": tm.translateMove(processedMessage.get(String.valueOf(i)));
						break;
					case "rotate": tm.translateRotate(processedMessage.get(String.valueOf(i)));
						break;
					case "zoom": tm.translateZoom(processedMessage.get(String.valueOf(i)));
						break;
					case "camera-change": tm.translateCameraChange(processedMessage.get(String.valueOf(i)));
						break;
					default: out.println("Invalid action! Closing the socket"); //maybe i shouldn't close it. that could fuck
					//everything up...
						socket.close();
						break;
				}
			}

		} else {
			System.out.println("failed");
		}

	}

	/*
	 * METHODS TO PROCESS AND CHECK VALIDITY OF THE INPUT
	 */
	
	public Map<String, String[]> processMessage(String messages) { //temporarily returns nothing
		String[] arrOfFrames = messages.split("\\s*POST\\s*");
		List<String> finalFrames = new ArrayList<String>();
		
		//remove that damn leading empty string in the array
		for (String frame : arrOfFrames) {
			if (!(frame.equals(""))) {
				finalFrames.add(frame);
			}
		}
		
		//collect the commands and put them in a map
		Map<String, String[]> processedCommands = new LinkedHashMap<String, String[]>();
		for (int i=0; i<finalFrames.size(); i++) {
			String[] commands = finalFrames.get(i).split(",");
			processedCommands.put(String.valueOf(i), commands);
		}
		
		return processedCommands;
		
	}
	
	public boolean isValid(Map<String, String[]> mapOfCommands) {
		boolean valid = false;
		ArrayList<String> validCommands = new ArrayList<String>();
		validCommands.add("cursor");
		validCommands.add("move");
		validCommands.add("rotate");
		validCommands.add("zoom");
		validCommands.add("camera-change");
		for (int i=0; i<mapOfCommands.size(); i++) {
			if (validCommands.contains(mapOfCommands.get(String.valueOf(i))[0])) {
//				System.out.println("here");
//				System.out.println(mapOfCommands.get(String.valueOf(i))[0]);
				valid = true;
			}
		}
		
		return valid;
	}
	

}
