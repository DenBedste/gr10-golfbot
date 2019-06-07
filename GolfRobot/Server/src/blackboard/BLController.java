package blackboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import blackboard.BlackboardController;
import blackboard.BlackboardSample;
import communication.CommandTransmitter;
import communication.LegoReceiver;
import communication.LidarReceiver;
import mapping.LidarScan;
import objects.LidarSample;

public class BLController implements BlackboardListener {
	private enum State {
		GET_SAMPLES,
		EXPLORE,
		VALIDATE_BALL,
		PLAN_ROUTE,
		RUN_ROUTE,
		FETCH_BALL,
		FIND_GOAL,
		GO_TO_GOAL,
		COLLISION_AVOIDANCE,
		COMPLETED
	}
	private State state;
	private boolean trigger;
	private int ballcounter;
	private int ballsDelivered;
	private BlackboardSample bbSample;


	public BLController() {
		state = State.EXPLORE;
		trigger = false;
		ballcounter = 0;
		ballsDelivered = 0;
	}

	public void main(){
		startup();
		FSM();
	}

	public void FSM() {
		while(!trigger) {
			switch(state) {
				case GET_SAMPLES:
					/* getLidarSamples();
					 * state = State.PLAN_ROUTE;
					 */
					break;

				case EXPLORE:
					/* Drive around until ball located
					 * while (locateBall() == false) travelAlongWall();
					 * state = State.VALIDATE_BALL;
					 * */


					commandTransmitter.robotTravel(0, 1000);
					state = State.VALIDATE_BALL;
					break;

				case VALIDATE_BALL:
					/* turnToBall();
					 * if( stillABall() ){
					 * 	state = state.PLAN_ROUTE;
				 	 * } else state = state.EXPLORE;
					 * */
					state = State.PLAN_ROUTE;
					break;

				case PLAN_ROUTE:
					/* planRoute();
					 * state = State.RUN_ROUTE;
					 *
					 */
				 	state = State.RUN_ROUTE;
					break;

				case RUN_ROUTE:
					/* driveToNearestPoint();
					 * state = State.FETCH_BALL;
					 */

					// MOVE ACCORDING TO ROUTEPLANNER
					commandTransmitter.robotTravel(90, 0);
					commandTransmitter.robotTravel(0, 1000);

					state = State.FETCH_BALL;
					break;

				case FETCH_BALL:
					/* pickUpBall();
					 * ballcounter++;
					 * if (ballcounter == 5)
					 * 		state = State.GO_TO_GOAL;
					 * else
					 * 		if (routeUpdated())
					 * 			state = State.PLAN_ROUTE;
					 * 		else
					 * 			state = State.RUN_ROUTE;
					 */

				 	commandTransmitter.robotCollectBall();
					ballcounter++;
					if (ballcounter == 10)
						state = State.FIND_GOAL;
					else
						state = State.RUN_ROUTE;
					break;

				case COLLISION_AVOIDANCE:
					/* robotEmergencyBrake();
					 * ?moveAwayFromWall();
					 * state = State.GET_SAMPLES;
					 */
					break;

				case FIND_GOAL:
					/* if (locateNearestGoal())
					 * 		state = State.GO_TO_GOAL;
					 * else
					 * 		travelAlongWall();
					 *
					 */

					state = State.GO_TO_GOAL;
					break;

				case GO_TO_GOAL:
					/* deliverBalls();
					 * ballsDelivered += ballcounter;
					 * ballcounter = 0;
					 * if ballsDelivered == 10
					 * 		state = State.COMPLETED
					 * else
					 * 		state = State.EXPLORE;
					 */

					 deliverBalls();

 					 state = State.COMPLETED;

					break;

				case COMPLETED:
					/* finished();
					 * trigger = TRUE;
					 */
					break;

				default:
					state = State.EXPLORE;
					break;
			}
		}
	}

	public void wallCollisionISR() {
		state = State.COLLISION_AVOIDANCE;
	}

	public void blackboardUpdated(BlackboardSample bbSample) {
		this.bbSample = bbSample;
	}

	public void startup() {
		// Very important boolean
		boolean YesRobotRunYesYes = true;

		// Build Lidar receiver
		System.out.println("Building Lidar Receiver...");
		LidarReceiver lidarReceiver = new LidarReceiver();
		if(YesRobotRunYesYes && lidarReceiver.bindSocket(5000)) {
			lidarReceiver.start();
			System.out.println("Lidar Receiver succes");
		} else {
			YesRobotRunYesYes = false;
			System.out.println("Lidar Receiver failed");
		}

		// Build Lego Receiver
		System.out.println("Building Lego Receiver...");
		LegoReceiver legoReceiver = new LegoReceiver();
		if(YesRobotRunYesYes && legoReceiver.connect(3000, 3001, 3002)) {
			legoReceiver.start();
			System.out.println("Lego Receiver succes");
		} else {
			YesRobotRunYesYes = false;
			System.out.println("Lego Receiver failed");
		}

		// Command Transmitter
		System.out.println("Building Command Transmitter...");
		CommandTransmitter commandTransmitter = new CommandTransmitter();
		if(YesRobotRunYesYes) {
			YesRobotRunYesYes = commandTransmitter.connect(3003);
			System.out.println("Command Transmitter succes");
		} else {
			YesRobotRunYesYes = false;
			System.out.println("Command Transmitter failed");
		}

		// Blackboard Controller
		System.out.println("Building blackboard...");
		BlackboardController bController = new BlackboardController(null, legoReceiver, lidarReceiver);
		bController.registerListener(commandTransmitter);
		if(YesRobotRunYesYes) {
			bController.start();
			System.out.println("Blackboard succes");
		} else {
			System.out.println("Blackboard not started");
		}
	}

	public void getLidarSamples() {

	}

	public void locateBall() {

	}

	public void travelAlongWall() {

	}

	public void turnToBall() {

	}

	public void planRoute() {
		// route planner magic
	}

	public void driveToNearestPoint() {

	}

	public void pickUpBall() {

	}

	public void routeUpdated() {

	}

	public void robotEmergencyBrake() {

	}

	public void locateNearestGoal() {

	}

	public void deliverBalls() {
		//commandTransmitter.deliverBalls();
	}

	public void finished() {
		// MAKE A FINISHING SOUND
	}

}
