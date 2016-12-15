package org.usfirst.frc.team1573.robot;

import java.io.*;
import java.net.*;

public class TcpConnection {
	Socket sock;
	public boolean isConnected = false;

	public TcpConnection(String host, int port) {
		init(host, port);
	}

	public TcpConnection(String host) {
		init(host, 5800);
	}

	public TcpConnection() {
		init("1573vision.local", 5800);
	}

	private void init(String host, int port) {
		RecieveThread p = new RecieveThread(host, port);
		p.start();
	}

	class RecieveThread extends Thread {
		String host;
		int port;

		RecieveThread(String host, int port) {
			this.host = host;
			this.port = port;
		}

		public void run() {
			// compute primes larger than minPrime
			while (true) {
				try {
					Socket sock = new Socket(host, port);
					BufferedReader inFromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					isConnected = true;
					String message = "";
					while (true) {
						int inp = inFromServer.read();
						if (inp == 126) {
							break;
						}
						if (inp != -1) {
							message += (char) inp;
						}
					}
					sock.close();
					System.out.println(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isConnected = false;
				}
			}
		}
	}
}
