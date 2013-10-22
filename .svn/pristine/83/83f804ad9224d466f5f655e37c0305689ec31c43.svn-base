package edu.wm.cs301.UI;


import java.util.Random;

public class CuriousGambler implements RobotDriver {
	Robot r;
	int[][] log;   //our 2D array to log all visited locations
	@Override
	public void setRobot(Robot r) throws UnsuitableRobotException {
		this.r = r;
		
	}
	
	public CuriousGambler(){
		
	}

	@Override
	/**
	 * Drives the robot towards the exit given it exists and given the robot's energy supply lasts long enough. 
	 * Curious Gambler has a bias to select an adjacent cell that was not visited as often as other (curious to explore new territories). 
	 * So it needs a memory to count how often it has visited particular cells. It uses an array to check for this, if it can only go forwards it does,
	 * same for left. If it can go both it goes to the cell it has been less times. If it can't go either, it rotates right.
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws exception if robot stopped due to an accident
	 */
	public boolean drive2Exit() throws Exception {
		Random rand = new Random();
		int leftChoice = 0;
		int forwardChoice = 0;
		log = new int[GlobalItems.maze.mazeh][GlobalItems.maze.mazew];
		
		while ( (!r.hasStopped()) && r.getCurrentBatteryLevel() > 0){
			
			int[] direction = r.getCurrentDirection();		
		
			
			if(r.distanceToObstacleOnLeft()==0 && r.distanceToObstacleAhead()!=0){
				log[GlobalItems.maze.px][GlobalItems.maze.py] += 1;
				r.move(1,true);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				
			}
			else if(r.distanceToObstacleOnLeft()!=0 && r.distanceToObstacleAhead()==0){
				r.rotate(90);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
				log[GlobalItems.maze.px][GlobalItems.maze.py] += 1;
				r.move(1,true);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
			}
			else if(r.distanceToObstacleOnLeft()==0 && r.distanceToObstacleAhead()==0){
				r.rotate(-90);
				GlobalItems.mapview.postInvalidate();
				Thread.sleep(250);
				
			}
			else{ //can go both directions
				//Check which direction has been accessed less times and go that way.
				
				if(direction[0] == 0){
					
					if(direction[1] == 1){
						leftChoice = log[GlobalItems.maze.px-1][GlobalItems.maze.py];
						forwardChoice = log[GlobalItems.maze.px][GlobalItems.maze.py+1];
					}
					if(direction[1] == -1){
						leftChoice = log[GlobalItems.maze.px+1][GlobalItems.maze.py];
						forwardChoice = log[GlobalItems.maze.px][GlobalItems.maze.py-1];
					}	
				}
				else{
					
					if(direction[0] == 1){
						leftChoice = log[GlobalItems.maze.px][GlobalItems.maze.py+1];
						forwardChoice = log[GlobalItems.maze.px+1][GlobalItems.maze.py];
					}
					if(direction[0] == -1){
						leftChoice = log[GlobalItems.maze.px][GlobalItems.maze.py-1];
						forwardChoice = log[GlobalItems.maze.px-1][GlobalItems.maze.py];
					}
				}
				
				if(leftChoice < forwardChoice){
					r.rotate(90);
					GlobalItems.mapview.postInvalidate();
					Thread.sleep(250);
					
					
					log[GlobalItems.maze.px][GlobalItems.maze.py] += 1;
					r.move(1,true);
					GlobalItems.mapview.postInvalidate();
					Thread.sleep(250);
					
					
				}
				else if(leftChoice > forwardChoice){
					log[GlobalItems.maze.px][GlobalItems.maze.py] += 1;
					r.move(1,true);
					GlobalItems.mapview.postInvalidate();
					Thread.sleep(250);
					
			
				
				}
				else{
					int decision = rand.nextInt()%2;
					if(decision == 0){
						r.rotate(90);
						GlobalItems.mapview.postInvalidate();
						Thread.sleep(250);
						
						log[GlobalItems.maze.px][GlobalItems.maze.py] += 1;
						r.move(1,true);
						GlobalItems.mapview.postInvalidate();
						Thread.sleep(250);
						
					}
					else{
						log[GlobalItems.maze.px][GlobalItems.maze.py] += 1;
						r.move(1,true);
						GlobalItems.mapview.postInvalidate();
						Thread.sleep(250);
						
					}
					
				}
			
		}
		
		if (r.hasStopped()){
			return false;
		}
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
