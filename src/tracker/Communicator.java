package tracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Communicator {
	static String HOST = "localhost";
	static int PORT = 4444;
	
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Socket sock = new Socket(HOST, PORT);
		PrintWriter toServer = new PrintWriter(sock.getOutputStream(), true);
		final ColorTracker tracker = new ColorTracker();
		String message;
		Thread thread = new Thread(new Runnable(){
			public void run(){
				try {
					tracker.run();
				} catch (InterruptedException e) {
					tracker.stop();
					e.printStackTrace();
				}
			}
		});
		thread.start();
		try{
			while(!sock.isClosed()){
				message = tracker.update();
				toServer.println(message);
			}
		} finally {
			sock.close();
			toServer.close();
			tracker.stop();
		}
	}

}
