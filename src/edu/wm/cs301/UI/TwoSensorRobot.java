/**
 * 
 */
package edu.wm.cs301.UI;
/**
 * @author bsweaver and jasilver
 *
 */
public class TwoSensorRobot implements Robot{

	/**This constructs a BasicRobot implementing the Robot interface. It sets an initial energy level to 2500 and the current direction 0. 
	 * We are referring to north, east, south, and west as 90, 0, 270 and 180 respectively.
	 */
	public int energy;
	Maze myMaze;
	public int traveled;
	public TwoSensorRobot(Maze maze){
		
		myMaze = maze;
		energy = 2500;
		
		
	}
	
	//returns the private variable traveled which refers to the distance traveled.
	public int getTraveled(){
		return traveled;
	}
	
	public void reset(){
		energy = 2500;
		traveled = 0;
	}
	
	@Override
	/**
	 * Turn robot on the spot. If given degree is not supported by existing robot, method throws a corresponding exception. 
	 * For example, a robot may only be able to turn left (90) degrees or right (-90) degrees. The angle is measured in a counterclockwise manner
	 * as it is common for polar coordinates.
	 * @param degree specifies in which direction to turn (negative values turn right, positive values turn left), actual implementation may be limited to a few discrete value settings. 
	 * @throws UnsupportedArgumentException if the robot does not support a given degree value. 
	 */
	public void rotate(int degree) throws UnsupportedArgumentException {
		if(energy >2){
			if (degree != 90 && degree != -90){
				throw new UnsupportedArgumentException();
			}
			else if(degree == 90){
				GlobalItems.maze.rotate(1);
			}
			else{
				GlobalItems.maze.rotate(-1);
			}
			energy -= 3;
		}	
	}

	@Override
	
	/**
	 * Moves robot forward or backward a given number of steps. A step matches a single cell.
	 * Since a robot may only have a distance sensor in its front, driving backwards may happen blindly as distance2Obstacle may not provide values for that direction.
	 * If the robot runs out of energy somewhere on its way, it stops, which can be checked by hasStopped() and by checking the battery level. 
	 * @param distance is the number of cells to move according to the robots current direction if forward = true, opposite direction if forward = false
	 * @param forward specifies if the robot should move forward (true) or backward (false)
	 * @throws HitObstacleException if robot hits an obstacle like a wall or border, which also make the robot stop, i.e. hasStopped() = true 
	 */
	public void move(int distance, boolean forward) throws HitObstacleException {
		if(energy >4){
			for (int n = 0; n < distance; n++) {
				int oldx = GlobalItems.maze.px;
				int oldy = GlobalItems.maze.py;
				
				if (forward){
					GlobalItems.maze.walk(1);
				}
				else{
					GlobalItems.maze.walk(-1);
				}
				if (GlobalItems.maze.px != oldx || GlobalItems.maze.py != oldy) { // if we moved, subtract energy
					energy -= 5;
					traveled += 1;}
			}
		}
		
		
	}

	@Override
	/**
	 * Provides the current position as (x,y) coordinates for the maze cell as an array of length 2 with [x,y].
	 * Note that 0 <= x < width, 0 <= y < height of the maze. 
	 * @return array of length 2, x = array[0], y=array[1]
	 */
	public int[] getCurrentPosition() {
		int[] position =  {GlobalItems.maze.px, GlobalItems.maze.py};
		return position;
	}

	@Override
	/**
	 * Tells if current position is at the goal. Used to recognize termination of a search.
	 * Note that goal recognition is limited by the sensing functionality of robot such that isAtGoal returns false
	 * even if it is positioned directly at the exit but has no distance sensor towards the exit direction. 
	 * @return true if robot is at the goal and has a distance sensor in the direction of the goal, false otherwise
	 */
	public boolean isAtGoal() {
		if (GlobalItems.maze.mazebuilder.dists[GlobalItems.maze.px][GlobalItems.maze.py] == 1){
			try {
				if (this.canSeeGoalOnLeft() || this.canSeeGoalAhead()){ 
					return true;
				}
			} catch (UnsupportedMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		return false;
		
	}

	@Override
	/**
	 * Provides the current direction as (dx,dy) values for the robot as an array of length 2 with [dx,dy].
	 * Note that dx,dy are elements of {-1,0,1} and as in bitmasks masks in Cells.java and dirsx,dirsy in MazeBuilder.java.
	 * 
	 * @return array of length 2, dx = array[0], dy=array[1]
	 */	
	public int[] getCurrentDirection() {
		
		int[] direction = {GlobalItems.maze.dx, GlobalItems.maze.dy};
		return direction;
	}

	@Override
	/**
	 * The robot has a given battery level (energy level) that it draws energy from during operations. 
	 * The particular energy consumption is device dependent such that a call for distance2Obstacle may use less energy than a move forward operation.
	 * If battery level <= 0 then robot stops to function and hasStopped() is true.
	 * @return current battery level, level is > 0 if operational. 
	 */
	public float getCurrentBatteryLevel() {
		
		return energy;
	}

	@Override
	/**
	 * Gives the energy consumption for a full 360 degree rotation.
	 * Scaling by other degrees approximates the corresponding consumption. 
	 * @return energy for a full rotation
	 */
	public float getEnergyForFullRotation() {
		
		return 12;
	}

	@Override
	/**
	 * Gives the energy consumption for moving 1 step forward.
	 * For simplicity, we assume that this equals the energy necessary to move 1 step backwards and that scaling by a larger number of moves is 
	 * approximately the corresponding multiple.
	 * @return energy for a single step forward
	 */
	public float getEnergyForStepForward() {
		
		
		return 5;
	}

	@Override
	/**
	 * Tells if the robot has stopped for reasons like lack of energy, hitting an obstacle, etc.
	 * @return true if the robot has stopped, false otherwise
	 */
	public boolean hasStopped() {
		if (energy <= 3 || GlobalItems.maze.isEndPosition(GlobalItems.maze.px, GlobalItems.maze.py)) {
			
			return true;
		}
		return false;
	}
	/**
	 * Tells if a sensor can identify the goal in the robot's current forward direction from the current position.
	 * @return true if the goal (here: exit of the maze) is visible in a straight line of sight
	 * @throws UnsupportedMethodException if robot has no sensor in this direction
	 */
	
	@Override
	public boolean canSeeGoalAhead() throws UnsupportedMethodException {
		int curX = this.getCurrentPosition()[0]; //current location
		int curY = this.getCurrentPosition()[1];
		int[] direction = this.getCurrentDirection();		
		while(!GlobalItems.maze.isEndPosition(curX, curY)){ //if curX, curY is in maze
			if (direction[0] == 0){
				if (direction[1] == 1){
					if (GlobalItems.maze.mazecells.hasWallOnBottom(curX, curY)){
						return false;
					}
					else{
						curY += 1;
					}
				}
				else{
					if (GlobalItems.maze.mazecells.hasWallOnTop(curX, curY)){
						return false;
					}
					else{
						curY -= 1;
					}
				}
				
			}
			if (direction[1] == 0){
				if (direction[0] == 1){
					if (GlobalItems.maze.mazecells.hasWallOnRight(curX, curY)){
						return false;
					}
					else{
						curX += 1;
					}
				}
				else{
					if (GlobalItems.maze.mazecells.hasWallOnLeft(curX, curY)){
						return false;
					}
					else{
						curX -= 1;
					}
				}
				
			}			
			
		}
		return true;
	}
	

	@Override
	/**
	 * Methods analogous to canSeeGoalAhead but for the robot's current left direction (left relative to forward)
	 * @return true if the goal (here: exit of the maze) is visible in a straight line of sight
	 * @throws UnsupportedMethodException if robot has no sensor in this direction
	 */
	public boolean canSeeGoalOnLeft() throws UnsupportedMethodException {
		
		int curX = this.getCurrentPosition()[0]; //current location
		int curY = this.getCurrentPosition()[1];
		int[] direction = this.getCurrentDirection();		
		while(!GlobalItems.maze.isEndPosition(curX, curY)){ //if curX, curY is in maze
			if (direction[0] == 0){
				if (direction[1] == 1){
					if (GlobalItems.maze.mazecells.hasWallOnLeft(curX, curY)){
						return false;
					}
					else{
						curX -= 1;
					}
				}
				else{
					if (GlobalItems.maze.mazecells.hasWallOnRight(curX, curY)){
						return false;
					}
					else{
						curX += 1;
					}
				}
				
			}
			if (direction[1] == 0){
				if (direction[0] == 1){
					if (GlobalItems.maze.mazecells.hasWallOnBottom(curX, curY)){
						return false;
					}
					else{
						curY += 1;
					}
				}
				else{
					if (GlobalItems.maze.mazecells.hasWallOnTop(curX, curY)){
						return false;
					}
					else{
						curY -= 1;
					}
				}
				
			}			
			
		}
		return true;
	}

	

	@Override
	/**
	 * Tells the distance to an obstacle (a wall or border) for a the robot's current forward direction.
	 * Distance is measured in the number of cells towards that obstacle, e.g. 0 if current cell has a wall in this direction
	 * @return number of steps towards obstacle if obstacle is visible in a straight line of sight, Integer.MAX_VALUE otherwise
	 * @throws UnsupportedArgumentException if not supported by robot
	 */
	public int distanceToObstacleAhead() throws UnsupportedMethodException {

		energy -=1;
		int curX = this.getCurrentPosition()[0]; //current location
		int curY = this.getCurrentPosition()[1];
		int distance=0;
		int[] direction = this.getCurrentDirection();		
		while(!GlobalItems.maze.isEndPosition(curX, curY)){ //if curX, curY is in maze
			if (direction[0] == 0){
				if (direction[1] == 1){
					if(this.canSeeGoalAhead()){
						return Integer.MAX_VALUE;
						
					}
					else{
						if(GlobalItems.maze.mazecells.hasNoWallOnBottom(curX, curY))
						{
							distance+=1;
							curY += 1;
						}
						else{
							return distance;
						}
						
					}
					
				}
				else{
					if(this.canSeeGoalAhead()){
						return Integer.MAX_VALUE;
					}
					else{
						if(GlobalItems.maze.mazecells.hasNoWallOnTop(curX, curY)){
							distance+=1;
							curY -= 1;
						}
						else{
							return distance;
						}
					}
				}
			}
			if (direction[1] == 0){
				if (direction[0] == 1){
					if(this.canSeeGoalAhead()){
						return Integer.MAX_VALUE;
					}
					else{
						if(GlobalItems.maze.mazecells.hasNoWallOnRight(curX, curY)){
							distance+=1;
							curX +=1;
						}
						else{
							return distance;
						}
					}
				}
				else{
					if(this.canSeeGoalAhead()){
						return Integer.MAX_VALUE;
					}
					else{
						if(GlobalItems.maze.mazecells.hasNoWallOnLeft(curX, curY)){
							distance+=1;
							curX -= 1;
						}
						else{
							return distance;
						}
					}
				}
					
				}
			}
				

		return distance;
	}
	

	@Override
	/**
	 * Methods analogous to distanceToObstacleAhead but for the robot's current left direction (left relative to forward)
	 * @return number of steps towards obstacle if obstacle is visible in a straight line of sight, Integer.MAX_VALUE otherwise
	 * @throws UnsupportedArgumentException if not supported by robot
	 */
	public int distanceToObstacleOnLeft() throws UnsupportedMethodException {
		
		energy -=1;
		int curX = this.getCurrentPosition()[0]; //current location
		int curY = this.getCurrentPosition()[1];
		int distance=0;
		int[] direction = this.getCurrentDirection();		
		while(!GlobalItems.maze.isEndPosition(curX, curY)){ //if curX, curY is in maze
			if (direction[0] == 0){
				if (direction[1] == 1){
					if(this.canSeeGoalOnLeft()){
						return Integer.MAX_VALUE;
						
					}
					else{
						if(GlobalItems.maze.mazecells.hasNoWallOnLeft(curX, curY))
						{
							distance+=1;
							curX -= 1;
						}
						else{
							return distance;
						}
						
					}
					
				}
				else{
					if(this.canSeeGoalOnLeft()){
						return Integer.MAX_VALUE;
					}
					else{
						if(GlobalItems.maze.mazecells.hasNoWallOnRight(curX, curY)){
							distance+=1;
							curX += 1;
						}
						else{
							return distance;
						}
					}
				}
			}
			if (direction[1] == 0){
				if (direction[0] == 1){
					if(this.canSeeGoalOnLeft()){
						return Integer.MAX_VALUE;
					}
					else{
						if(GlobalItems.maze.mazecells.hasNoWallOnBottom(curX, curY)){
							distance+=1;
							curY +=1;
						}
						else{
							return distance;
						}
					}
				}
				else{
					if(this.canSeeGoalOnLeft()){
						return Integer.MAX_VALUE;
					}
					else{
						if(GlobalItems.maze.mazecells.hasNoWallOnTop(curX, curY)){
							distance+=1;
							curY -= 1;
						}
						else{
							return distance;
						}
					}
				}
					
				}
			}
				

		return distance;
	}

	@Override
	public boolean canSeeGoalBehind() throws UnsupportedMethodException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeeGoalOnRight() throws UnsupportedMethodException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int distanceToObstacleOnRight() throws UnsupportedMethodException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int distanceToObstacleBehind() throws UnsupportedMethodException {
		// TODO Auto-generated method stub
		return 0;
	}
	

	

}
