package golfbot.server.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.ProcessBuilder.Redirect;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import golfbot.samples.DrivingMotorSample;
import golfbot.samples.GyroSample;
import golfbot.samples.IRSample;
import golfbot.samples.SonicSample;
import golfbot.samples.TouchSample;
import golfbot.server.blackboard.Blackboard;
import golfbot.server.blackboard.BlackboardSample;
import lejos.robotics.navigation.Pose;

public class LegoReceiver extends Thread {
	private final int L_PORT = 3010;
	private final int N_PORT = 3011;
	private final int B_PORT = 3012;
	
	private ServerSocket lServerSocket;
	private ServerSocket nServerSocket;
	private ServerSocket bServerSocket;
	
	private Socket lSocket;
	private Socket nSocket;
	private Socket bSocket;
	
	private ObjectInputStream lStream;
	private ObjectInputStream nStream;
	private ObjectInputStream bStream;
	
	private boolean switcher = false;
	public boolean newData = false;

	private Pose pose1;
	private Pose pose2;
	private Boolean isMoving1;
	private Boolean isMoving2;
	private Boolean isCollecting1;
	private Boolean isCollecting2;
	
	public LegoReceiver() {
		pose1 = new Pose();
		isMoving1 = false;
		isCollecting1 = false;
		pose2 = new Pose();
		isMoving2 = false;
		isCollecting2 = false;
	}
	
	public boolean connect() {
		try {
			lServerSocket = new ServerSocket(L_PORT);
			lSocket = lServerSocket.accept();
			lStream = new ObjectInputStream(lSocket.getInputStream());
			nServerSocket = new ServerSocket(N_PORT);
			nSocket = nServerSocket.accept();
			nStream = new ObjectInputStream(nSocket.getInputStream());
			bServerSocket = new ServerSocket(B_PORT);
			bSocket = bServerSocket.accept();
			bStream = new ObjectInputStream(bSocket.getInputStream());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void run() {
		while(socketsWorking()) {
			boolean gotNewData = false;
			if(readLocalization() && !gotNewData)
				gotNewData = true;
			if(readNavigator() && !gotNewData)
				gotNewData = true;
			if(readBallCollector() && !gotNewData)
				gotNewData = true;
			if(gotNewData) {
				newData = true;
			}
		}
	}
	
	private boolean socketsWorking() {
		boolean working = true;
		if(working)
			working = (!lSocket.isClosed() && lSocket.isConnected());
		if(working)
			working = (!nSocket.isClosed() && nSocket.isConnected());
		if(working)
			working = (!bSocket.isClosed() && bSocket.isConnected());
		return working;
	}
	
	private void readLocalization() {
		Object obj = null;
		try { obj = lStream.readObject(); } 
		catch (ClassNotFoundException | IOException e) { e.printStackTrace(); }
		if(obj != null) {
			Pose newPose = (Pose) obj;
			if(newPose != pose) {
				newData = true;
				pose = newPose;
			}
		}
	}
	
	private void readNavigator() {
		Object obj = null;
		try { obj = nStream.readObject(); } 
		catch (ClassNotFoundException | IOException e) { e.printStackTrace(); }
		if(obj != null) {
			Boolean newIsMoving = (Boolean) obj;
			if(switcher) {
				if(newIsMoving.equals(isMoving2)) {
					newData = true;
					isMoving = newIsMoving;
				}
			} else {
				
			}
			if(newIsMoving.equals(isMoving)) {
				newData = true;
				isMoving = newIsMoving;
			}
		}
	}
	
	private void readBallCollector() {
		Object obj = null;
		try { obj = bStream.readObject(); } 
		catch (ClassNotFoundException | IOException e) { e.printStackTrace(); }
		if(obj != null) {
			Boolean newIsCollecting = (Boolean) obj;
			if(newIsCollecting.equals(isCollecting)) {
				newData = true;
				isMoving = newIsCollecting;
			}
		}
	}
	
	public Pose getPose() {
		if(switcher)
			return pose1;
		else
			return pose2;
	}

	public Boolean getIsMoving() {
		if(switcher)
			return isMoving1;
		else
			return isMoving2;
	}

	public Boolean getIsCollecting() {
		if(switcher)
			return isCollecting1;
		else
			return isCollecting2;
	}
}