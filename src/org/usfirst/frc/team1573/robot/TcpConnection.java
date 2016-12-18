package org.usfirst.frc.team1573.robot;

import java.io.*;
import java.net.*;
import java.util.Hashtable;

/***
 * Handles the connection with the raspberry pi using a tcp connection
 * The protocol is a stream of key=value pairs, which are assignments to a hashtable on the client
 * 
 * Example stream:
 * i=5
 * j=2
 * h=4
 * j=3
 * 
 * In the end the hashtable contains the following:
 * {i=3, j=2, h=4}
 * 
 * Notes:
 * The characters `=` and `\n` are not allowed inside the key's value or name
 * Keys can be overriden (see example)
 * Empty lines and incorrectly formatted lines are ignored
 * Key names are case-sensitive
 * 
 * @author david
 *
 */
public class TcpConnection {
	public boolean isConnected = false;
	
	/**
	 * Contains all the stored data
	 */
	Hashtable<String, String> data = new Hashtable<String, String>();

	/**
	 * Full constructor
	 * @param host IP/Address to connect to
	 * @param port Port number
	 */
	public TcpConnection(String host, int port) {
		init(host, port);
	}

	/**
	 * Constructor with default port
	 * @param host IP/Address to connect to
	 */
	public TcpConnection(String host) {
		init(host, 5800);
	}
	
	/**
	 * Constructor to connect to the PI
	 */
	public TcpConnection() {
		init("1573vision.local", 5800);
	}

	/**
	 * Initialization method called from the constructors
	 * @param host IP/Address to connect to
	 * @param port Port number
	 */
	private void init(String host, int port) {
		RecieveThread p = new RecieveThread(host, port);
		p.start();
	}
	
	/**
	 * Gets a value and converts it to an Integer
	 * @param key The key's name
	 * @return The value
	 */
	public int getInt(String key) {
		try {
			return Integer.parseInt(data.get(key));
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * Gets a value and converts it to a Float
	 * @param key The key's name
	 * @return The value
	 */
	public float getFloat(String key) {
		// Stub
		try {
			return Float.parseFloat(data.get(key));
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * Gets a raw value (no conversion)
	 * @param key The key's name
	 * @return The value
	 */
	public String getString(String key) {
		// Stub
		try {
			return data.get(key);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * This thread handles the recieving of data in the background, as to not block the logic code
	 * @author david
	 *
	 */
	class RecieveThread extends Thread {
		String host;
		int port;

		/**
		 * Full constructor
		 * @param host IP/Address to connect to
		 * @param port Port number
		 */
		RecieveThread(String host, int port) {
			this.host = host;
			this.port = port;
		}

		public void run() {
			while (true) { // For each connection
				try {
					@SuppressWarnings("resource")
					Socket sock = new Socket();
					
					// Start the TCP connection
					sock.connect(new InetSocketAddress(host, port), 2000);
					BufferedReader inFromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					
					// Print the `connected` message
					if (!isConnected)
						System.out.println("Connected to " + host + ":" + port);
					isConnected = true;
					
					while (true) { // For each line
						String line = "";
						while (true) { // For each character
							int inp = inFromServer.read(); // Get a character
							if (inp == 10) { // 10 is ASCII for end of line, aka \n
								break;
							}
							if (inp != -1) { // If a char is available from the buffer
								line += (char) inp;
							}
						}
						String[] parts = line.split("="); // Split the part before the `=` and after
						if (parts.length == 2) { // Line format is correct and not an empty line
							data.put(parts[0], parts[1]); // Save the key and value in the hashtable
						}
					}
				} catch (IOException e) { // Disconnected from the server
					// Print the `disconnected` message
					if (isConnected)
						System.out.println("Disconnected from " + host + ":" + port);
					isConnected = false;
				}
			}
		}
	}
}
