package golfbot.robot.knowledgesources;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class KnowledgeSource<E> extends Thread {
	
	private Socket socket;
	private ObjectOutputStream oos;
	private E lastKnowledge;
	
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
			if(knowledge != null && (lastKnowledge == null || !knowledge.equals(lastKnowledge))) {
				try {
					oos.writeObject(knowledge);
					lastKnowledge = knowledge;
				} catch (IOException e) {
					closeConnection();
					break;
				}
			}
		}
	}
	
	protected abstract E getKnowledge();
	
	public void closeConnection() {
		try { oos.close(); } 
		catch (IOException e) {}
		
		try { socket.close(); } 
		catch (IOException e) {}
	}
	
}