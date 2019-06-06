package src.communication;

import golfbot.server.blackboard.BlackboardListener;
import golfbot.server.blackboard.BlackboardSample;

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

	public void FSM() {
		while(!trigger) {
			switch(state) {
				case GET_SAMPLES:
					/* getLidarSamples();
					 * state = State.PLAN_ROUTE;
					 */
					break;

				case EXPLORE:
					/* while (locateBall() == false) travelAlongWall();
					 * state = State.VALIDATE_BALL;
					 * */
					break;

				case VALIDATE_BALL:
					/* turnToBall();
					 * if( stillABall() ){
					 * 	state = state.PLAN_ROUTE;
				 	 * } else state = state.EXPLORE;
					 * */
					break;

				case PLAN_ROUTE:
					/* planRoute();
					 * state = State.RUN_ROUTE;
					 *
					 */
					break;

				case RUN_ROUTE:
					/* driveToNearestPoint();
					 * state = State.FETCH_BALL;
					 */
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

	public void getLidarSamples() {

	}

	public void locateBall() {

	}

	public void travelAlongWall() {

	}

	public void turnToBall() {

	}

	public void planRoute() {

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

	}

	public void finished() {

	}


}
