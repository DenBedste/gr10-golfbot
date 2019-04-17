package golfbot.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Scanner;

import golfbot.server.communication.LidarReceiver;
import golfbot.server.communication.CommandTransmitter;

public class RemoteMain {

	public static void main(String[] args) throws IOException {
		
		CommandTransmitter transmitter = new CommandTransmitter();
		transmitter.connect();
		transmitter.robotCollectBall();
		/*
		LidarReceiver lr = new LidarReceiver();
		lr.run();
		
		HashMap<Double,Double> scan = null;
		do {
			scan = lr.getScan();
		} while(scan == null);
		
		System.out.println(scan);
		*/
		/*int port = 3000;
		ServerTransmitter st = new ServerTransmitter(port++);
		
		ServerReceiver sr = null;
		Scanner sc = null;
		
		if(st.connect()) {
			st.start();
			sr = startReceiver(port);
			sc = new Scanner(System.in);
			
			while(true) {
				String input = sc.nextLine();
				
				if(input.toLowerCase().equals("exit")) {
					break;
				}
				
				if(input != null) {
					st.sendObject(input);
				}
			}
			
			sc.close();
			sr.closeConnections();
			st.closeConnection();
		}*/
	}
	/*
	public static ServerReceiver startReceiver(int port) {
		ServerReceiver sr = new ServerReceiver();
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		for(int i = 0 ; i < 3 ; i++) {
			try {
				serverSocket = new ServerSocket(port++);
				socket = serverSocket.accept();
				sr.addSocket(serverSocket, socket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sr.start();
		return sr;
	}
*/
}
