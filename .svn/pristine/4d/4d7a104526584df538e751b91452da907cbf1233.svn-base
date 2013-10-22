/**
 * 
 */
package edu.wm.cs301.UI;




/** This class ManualDriver includes the methods for the keyListener and is the driver for the Basic Robot class that we have created. 
 * 
 * @author bsweaver
 *
 */
public class ManualDriver implements RobotDriver {
	
	Robot r;
	
	
	/**
	 * Assigns a robot platform to the driver. Not all robot configurations may be suitable such that the method 
	 * will throw an exception if the robot does not match minimal configuration requirements, e.g. providing a sensor
	 * to measure the distance to an object in a particular direction. 
	 * @param r robot to operate
	 * @throws UnsuitableRobotException if driver cannot operate the given robot
	 */
	@Override
	public void setRobot(Robot r) throws UnsuitableRobotException {
		this.r = r;
		
	}
	
	public ManualDriver(Maze maze)
	{
		
	}

	@Override
	/**
	 * Drives the robot towards the exit given it exists and given the robot's energy supply lasts long enough. 
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws exception if robot stopped due to an accident
	 */
	public boolean drive2Exit() throws Exception {
		if (r.isAtGoal()){
			return true;
		}
		else if(r.hasStopped()){
			throw new Exception();
		}
		else{
			return false;
		}
	}

	@Override
	/**
	 * Returns the total energy consumption of the journey
	 */
	public float getEnergyConsumption() {
		return (2500 - r.getCurrentBatteryLevel());
	}

	@Override
	/**
	 * Returns the total length of the journey in number of cells traversed. The initial position counts as 0. 
	 */
	public int getPathLength() {
		return ((TwoSensorRobot) r).getTraveled();
	}
	
		


	

}


