package edu.wm.cs301.UI;

import android.util.Log;



public class WallFollower implements RobotDriver {

	Robot r;

	
	/** creates a robot to be used by this class
	 * 
	 */
	@Override
	public void setRobot(Robot r) throws UnsuitableRobotException {
		this.r = r;
		
	}
	/**Constructor for WallFollower with no parameters
	 * 
	 */
	public WallFollower()
	{
		
	}
	
	
	/**
	 * Drives the robot towards the exit given it exists and given the robot's energy supply lasts long enough. 
	 * the wall follower is a classic solution technique. 
	 * The robot needs a distance sensor at the front and at one side (here: pick left) to perform. 
	 * It follows the wall on its left hand side.  
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws exception if robot stopped due to an accident
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		Log.v("WALLFOLLOWER","IdontLIKEyou");
		Log.v("WALLFOLLOWER","TURNLEFT");
		Log.v("WALLFOLLOWER","LEFT");
		while ( (!r.hasStopped()) && r.getCurrentBatteryLevel() > 0){
			if(r.distanceToObstacleOnLeft()!=0 && r.distanceToObstacleAhead()==0){
				r.rotate(90);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				r.move(1,true);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				}
			else if(r.distanceToObstacleOnLeft()==0 && r.distanceToObstacleAhead()!=0){
				r.move(1,true);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
			}
			else if(r.distanceToObstacleOnLeft()!=0 && r.distanceToObstacleAhead()!= 0){
				r.rotate(90);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				
				r.move(1,true);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
			}
			else{
				r.rotate(-90);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				
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
		
		return (2500 - r.getCurrentBatteryLevel());
	}

	@Override
	public int getPathLength() {
		
		return ((TwoSensorRobot) r).getTraveled();
	}




}
	

