package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final int PORT_NUMBER = 4444;
		int port = PORT_NUMBER;
		
		ServerStart server = new ServerStart(port);
		server.serve();
	}

}
