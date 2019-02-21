package golfbot.robot;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class KnowledgeSource<E> extends Thread {
	
	private Socket socket;
	private ObjectOutputStream oos;
	
	public boolean connect(String ip, int port) {
		try {
			socket = new Socket(ip,port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			this.start();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void run() {
		while(!socket.isClosed() && socket.isConnected()) {
			E knowledge = getKnowledge();
			if(knowledge != null) {
				try {
					oos.writeObject(knowledge);
				} catch (IOException e) {
					e.printStackTrace();
					closeConnection();
					break;
				}
			}
		}
	}
	
	protected abstract E getKnowledge();
	
	public void closeConnection() {
		try { oos.close(); } 
		catch (IOException e) { e.printStackTrace(); }
		
		try { socket.close(); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
}
