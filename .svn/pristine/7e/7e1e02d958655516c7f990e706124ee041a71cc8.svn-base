package edu.wm.cs301.UI;


import java.util.Random;

public class Gambler implements RobotDriver{
	
	Robot r;


	/** creates a robot to be used by this class
	 * 
	 */
	@Override
	public void setRobot(Robot r) throws UnsuitableRobotException {
		this.r = r;
		
	}
	
	/**Constructor for Gambler with no parameters
	 * 
	 */
	public Gambler(){
		
	}

	@Override
	/**
	 * Drives the robot towards the exit given it exists and given the robot's energy supply lasts long enough. 
	 * a simple random search algorithm with no memory. 
	 * Once started it looks moves a step, checks its options by asking its available distance sensors, 
	 * randomly picks a direction, moves a stop and so on. 
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws exception if robot stopped due to an accident
	 */
	public boolean drive2Exit() throws Exception {
		Random rand = new Random();
		while ( (!r.hasStopped()) && r.getCurrentBatteryLevel() >0){
			
			
			if(r.distanceToObstacleOnLeft()==0 && r.distanceToObstacleAhead()!=0){

				r.move(1,true);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				if (GlobalItems.maze.isEndPosition(r.getCurrentPosition()[0], r.getCurrentPosition()[1])){
					return true;	
				}
			}
			else if(r.distanceToObstacleOnLeft()!=0 && r.distanceToObstacleAhead()==0){
				
				r.rotate(90);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				r.move(1,true);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				if (GlobalItems.maze.isEndPosition(r.getCurrentPosition()[0], r.getCurrentPosition()[1])){
					return true;	
				}
				
			}
			else if(r.distanceToObstacleOnLeft()==0 && r.distanceToObstacleAhead()==0){
				r.rotate(-90);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
			}
			else{
				int decision = rand.nextInt()%2;
				if(decision == 0){
					r.rotate(90);
					GlobalItems.mapview.postInvalidate();
					Thread.sleep(250);
					
					r.move(1,true);
					GlobalItems.mapview.postInvalidate();
					Thread.sleep(250);
					
					if (GlobalItems.maze.isEndPosition(r.getCurrentPosition()[0], r.getCurrentPosition()[1])){
						return true;	
					}
				}
				else{
					r.move(1,true);
					GlobalItems.mapview.postInvalidate();
					Thread.sleep(250);
					
					if (GlobalItems.maze.isEndPosition(r.getCurrentPosition()[0], r.getCurrentPosition()[1])){
						return true;	
					}
				}
				
			}
		
			
		}
		
		if (r.hasStopped()){
			return false;
		}
		return true;
}


	/**This method gives the current energy consumption
	 * @return the energy consumption
	 */
	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return (2500 - r.getCurrentBatteryLevel());
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return ((TwoSensorRobot) r).getTraveled();
	}
}