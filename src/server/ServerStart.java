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
	
	public ServerStart(int port) throws IOException {
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
	 * Takes in the socket and reads the message. Sends the message to the translator
	 * each term in the message should be separated by the '\n' character
	 * 1st line - the message type, {POST, GET, DELETE, etc or something like that}
	 * 2nd line - selects the block object specified by coordinates
	 * 3rd line - coordinates of where to move it {from(x,y), to(x,y)}
	 * 4th line - unselects the block obj specified by coordinates
	 * 5th line - coordinates on how much to rotate by; either in format (x,y) or (r, theta) you choose
	 * 6th line - coordinates on how to zoom. 
	 * @param socket
	 * @throws IOException 
	 */
	public void handleConnection(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		//add them to a list
		String[] messages = new String[6];
		String messageType = in.readLine();
		String selectCommand = in.readLine();
		String moveCommand = in.readLine();
		String unselectCommand = in.readLine();
		String rotateCommand = in.readLine();
		String zoomCommand = in.readLine();
		
		messages[0] = messageType;
		messages[1] = selectCommand;
		messages[2] = moveCommand;
		messages[3] = unselectCommand;
		messages[4] = rotateCommand;
		messages[5] = zoomCommand;
		
		
		
		if (validMessage(messages)) {
			Translator tm = new Translator(messageType, selectCommand, moveCommand, unselectCommand, rotateCommand, zoomCommand);
		}
	}
	
	public boolean validMessage(String[] messages) {
		boolean valid = false;
		
		for (int i=0; i<messages.length; i++) {
			if (i == 0) {
				if (!(messages[i] == "POST" ||  messages[i] == "GET")) {
					valid = false;
				}
			}
			else {
				if (messages[i].matches("")){
					valid = true;
				}
				else {
					valid = false;
				}
			}
		}
		
		return valid;
		
	}
	

}
