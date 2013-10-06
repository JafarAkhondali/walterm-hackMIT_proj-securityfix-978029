package server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

public class ServerStartTest {
			


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		final int DEFAULT_PORT = 3003;
		ServerStart server = new ServerStart(DEFAULT_PORT);
		String message = " POST STATUS,X,Y POST STATUS,X,Y POST STATUS,X,Y";
//		server.processMessage(message);

	}

}
