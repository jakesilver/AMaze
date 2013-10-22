/**
 * 
 */
package edu.wm.cs301.UI;

import android.graphics.Color;
import android.util.Log;



/**
 * @author bsweaver
 *
 */
public class Wizard implements RobotDriver{
	
	Robot r;


	/** creates a robot to be used by this class
	 * 
	 */
	@Override
	public void setRobot(Robot r) throws UnsuitableRobotException {
		this.r = r;
		
	}
	
	/**Constructor for Wizard with no parameters
	 * 
	 */
	public Wizard(){
	
	}

	@Override
	/**
	 * Drives the robot towards the exit given it exists and given the robot's energy supply lasts long enough. 
	 * As there is little magic in algorithms, the wizard is in fact a cheater who knows the distance matrix and uses it to find the exit. 
	 * The wizard is intended to work as a baseline algorithm
	 *  to see who the most efficient algorithm can perform in terms of energy consumption and path length.
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws exception if robot stopped due to an accident
	 */
	public boolean drive2Exit() throws Exception {
		// TODO Auto-generated method stub
		//this.setRobot(r);
		Log.v("WIZARD","merlin!");
		Log.v("WIZARD","harry.");
		while ( (!r.hasStopped()) && r.getCurrentBatteryLevel() > 0){
			/**if (((TwoSensorRobot) r).energy < 3){
				System.out.print("died");
				return false;
				}
				**/
			/*
			int oldDx = GlobalItems.maze.dx; int oldDy = GlobalItems.maze.dy;
			//GlobalItems.maze.solveStep();
			int d = GlobalItems.maze.mazedists[GlobalItems.maze.px][GlobalItems.maze.py];
			GlobalItems.wrapper.setColor(Color.YELLOW);
			// case 1: we are not directly next to the final position
			if (d > 1) {
				int n = GlobalItems.maze.getDirectionIndexTowardsSolution(GlobalItems.maze.px,GlobalItems.maze.py,d);
				
				rotateTo(n);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				
				
				r.move(1,true);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				return true;
			}
			// case 2: we are one step close to the final position
			int n;
			int[] masks = Cells.getMasks() ;
			for (n = 0; n < 4; n++) {
				// skip this direction if there is a wall or border
				if (GlobalItems.maze.mazecells.hasMaskedBitsGTZero(GlobalItems.maze.px, GlobalItems.maze.py, masks[n]))
					continue;
				// stop if position in this direction is end position
				if (GlobalItems.maze.isEndPosition(GlobalItems.maze.px+MazeBuilder.dirsx[n], GlobalItems.maze.py+MazeBuilder.dirsy[n]))
					rotateTo(n);
					GlobalItems.mapview.postInvalidate();
					Thread.sleep(250);
					
					r.move(1,true);
					GlobalItems.mapview.postInvalidate();
					Thread.sleep(250);
					
			
			((TwoSensorRobot ) r).traveled += 1;
			if (GlobalItems.maze.dx != oldDx){
				if (GlobalItems.maze.dy != oldDy){
					((TwoSensorRobot) r).energy -= 3;
				}
				else{((TwoSensorRobot) r).energy -= 6;}
			
					
			}
			((TwoSensorRobot) r).energy -= 5;
			if (GlobalItems.maze.isEndPosition(r.getCurrentPosition()[0], r.getCurrentPosition()[1])){
				return true;
			}
					
			}*/
		GlobalItems.maze.solveStep();
		GlobalItems.mapview.postInvalidate();
		Thread.sleep(350);
		if (r.hasStopped()){
			
			return false;
			
		}
		}
			
		
		return true;
		
	}
	
	/**Private helper method used by solve2Exit which rotates the robot
	 * 
	 * @param n
	 * @throws Exception
	 */
	private void rotateTo(int n) throws Exception {
		
		int a = GlobalItems.maze.ang/90;
		if (n == a)
			;
		else if (n == ((a+2) & 3)) {
			r.rotate(90);
			r.rotate(90);
		} else if (n == ((a+1) & 3)) {
			r.rotate(90);
		} else
			r.rotate(-90);
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
