package org.usfirst.frc.team1573.robot;

/**
 * Example program that connects to the data stream tcp server and prints the `i` key
 * @author david
 *
 */
public class Main {
	
	static final int imageWidth = 500;

	public static void main(String[] args) throws InterruptedException {
		// Replace with IP or remove to connect to default address
		TcpConnection con = new TcpConnection("192.168.43.27"); 
		while (true) {
			System.out.println(con.getInt("i"));
			Thread.sleep(100);
		}
	}

}
