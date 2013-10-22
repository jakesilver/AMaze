package edu.wm.cs301.UI;


import java.util.Random;
import java.util.Vector;

import android.util.Log;





/**
 * This class has the responsibility to create a maze of given dimensions (width, height) together with a solution based on a distance matrix.
 * The Maze class depends on it. The MazeBuilder performs its calculations within its own separate thread such that communication between 
 * Maze and MazeBuilder operates as follows. Maze calls the build() method and provides width and height. Maze has a call back method newMaze that
 * this class calls to communicate a new maze and a BSP root node and a solution. 
 * 
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 */
public class MazeBuilder implements Runnable {
	
	public BSPNode root;

	// columns mean right, bottom, left, top (as implemented in getBit())
	// note that multiplication with -1 to a column switches directions
	public static int[] dirsx = { 1, 0, -1, 0 };
	public static int[] dirsy = { 0, 1, 0, -1 };
	int width, height, startx, starty;
	// conventional encoding of maze as a 2 dimensional integer array 
	// a single integer entry can hold information on walls, borders/bounds
	int[][] origdirs ;
	int[][] dists; // encodes the solution as distances towards the exit
	Cells cells ; // the internal representation of a maze as a matrix of cells

	Random random; // random number generator to make randomized decisions
	Maze maze; // the maze that is constructed
	int partiters = 0; // a counter for the number of iterations in partitioning
	Vector<Seg> seglist;

	Thread buildThread; // computations are performed in own separated thread with this.run()
	int rooms; // number of rooms
	int expected_partiters; // user given limit for partiters

	static final int map_unit = 128;
	int colchange;

	/**
	 * Constructor for a randomized maze generation
	 */
	public MazeBuilder(){
		random = new Random();
	}
	/**
	 * Constructor with option to make maze generation deterministic or random
	 */
	public MazeBuilder(boolean deterministic){
		if (true == deterministic)
		{
			// Control random number generation
			random = new Random();
			

		}
	}

	/**
	 * Generate an integer random number in interval [lo,hi] 
	 * @param lo
	 * @param hi
	 * @return random number within given range
	 */
	 int randNo(int lo, int hi) {
		//TODO: work on findbugs error message for negative min value where abs will not change sign!
		return (Math.abs(random.nextInt()) % (hi-lo+1)) + lo;
	}

	/**
	 * Method called in genNodes to determine the minimum of all such grades. 
	 * The method is static, i.e. it does not update internal attributes and just calculates the returned value.
	 * @param sl vector of segments
	 * @param pe particular segment
	 * @return undocumented
	 */
	private static int grade_partition(Vector<Seg> sl, Seg pe) {
		int x  = pe.x;
		int y  = pe.y;
		int dx = pe.dx;
		int dy = pe.dy;
		int lcount = 0, rcount = 0, splits = 0;
		int inc = 1;
		if (sl.size() >= 100)
			inc = sl.size() / 50;
		// check all segments
		for (int i = 0; i < sl.size(); i += inc) {
			Seg se = (Seg) sl.elementAt(i);
			int df1x = se.x-x;
			int df1y = se.y-y;
			int sendx = se.x + se.dx;
			int sendy = se.y + se.dy;
			int df2x = sendx - x;
			int df2y = sendy - y;
			int nx = dy;
			int ny = -dx;
			int dot1 = df1x * nx + df1y * ny;
			int dot2 = df2x * nx + df2y * ny;
			if (getSign(dot1) != getSign(dot2)) {
				if (dot1 == 0)
					dot1 = dot2;
				else if (dot2 != 0) {
					splits++;
					continue;
				}
			}
			if (dot1 > 0 ||
					(dot1 == 0 && se.getDir() ==  pe.getDir())) {
				rcount++;
			} else if (dot1 < 0 ||
					(dot1 == 0 && se.getDir() == -pe.getDir())) {
				lcount++;
			} else {
				dbg("grade_partition problem: dot1 = "+dot1+", dot2 = "+dot2);
			}
		}
		return Math.abs(lcount-rcount) + splits * 3;
	}


	static int getSign(int num) {
		return (num < 0) ? -1 : (num > 0) ? 1 : 0;
	}

	/**
	 * This method generates a maze.
	 * It computes distances, determines a start and exit position that are as far apart as possible. 
	 */
	protected void generate() {
		// generate paths in cells such that there is one strongly connected component
		// i.e. between any two cells in the maze there is a path to get from one to the other
		// the search algorithms starts at some random point
		generatePathways(); 

		// compute temporary distances for an (exit) point (x,y) = (width/2,height/2) 
		// which is located in the center of the maze
		computeDists(width/2, height/2);

		int[] remote = findRemotePoint();
		// recompute distances for an exit point (x,y) = (remotex,remotey)
		computeDists(remote[0], remote[1]);

		// identify cell with the greatest distance
		setStartPositionToCellWithMaxDistance();

		// make exit position at true exit 
		setExitPosition(remote[0], remote[1]);
	}

	/**
	 * This method generates pathways into the maze.
	 */
	// TODO: add meaningful comments to clarify what this method is truly doing
	// TODO: what kind of algorithm is this?
	protected void generatePathways() {
		int[][] origdirs = new int[width][height] ; // storage for locations visited
		// 
		int x = randNo(0, width-1) ; //random starting position
		int firstx = x ; // 
		int y = 0;       // 
		int dir = 0; 	 // 
		int origdir = dir; // 
		cells.setVirginToZero(x, y);
		while (true) {  //loops until maze is finished (break statement)
			int dx = dirsx[dir], dy = dirsy[dir];
			//  
			//  
			if (!cells.canGo(x, y, dx, dy)) { //if cell in that direction has been visited before
				dir = (dir+1) & 3; // loops through possible directions
				if (origdir == dir) { // 
					// .
					if (x == firstx && y == 0) //if we've backtracked to our original position, done.
						break; // 
					//  
					// 
					int odr = origdirs[x][y]; //add position to the array
					dx = dirsx[odr];
					dy = dirsy[odr];
					// move back one position
					x -= dx; 
					y -= dy;
					// 
					// 
					origdir = dir = randNo(0, 3); //pick a new direction at random to try
				}
			} else { //cell has not been visited
				// 
				cells.deleteWall(x, y, dx, dy); //delete wall
				// 
				x += dx; //move into that cell
				y += dy;
				// 
				cells.setVirginToZero(x, y); //set cell has been visited
				// 
				origdirs[x][y] = dir; //add position to array
				origdir = dir = randNo(0, 3);//pick a new random direction
			}
		}
	}
	/**
	 * find most remote point in maze somewhere on the border
	 * @return array of length 2 gives the location (x,y)=(array[0],array[1])
	 */
	protected int[] findRemotePoint() {
		int x;
		int y;
		// find most remote point in maze somewhere on the border
		int remotex = -1, remotey = -1, remotedist = 0;
		for (x = 0; x != width; x++) {
			if (dists[x][0] > remotedist) {
				remotex = x;
				remotey = 0;
				remotedist = dists[x][0];
			}
			if (dists[x][height-1] > remotedist) {
				remotex = x;
				remotey = height-1;
				remotedist = dists[x][height-1];
			}
		}
		for (y = 0; y != height; y++) {
			if (dists[0][y] > remotedist) {
				remotex = 0;
				remotey = y;
				remotedist = dists[0][y];
			}
			if (dists[width-1][y] > remotedist) {
				remotex = width-1;
				remotey = y;
				remotedist = dists[width-1][y];
			}
		}
		int[] remote = new int[2] ;
		remote[0] = remotex ;
		remote[1] = remotey ;
		return remote;
	}

	/**
	 * Establish valid exit position by breaking down wall to outside area.
	 * @param remotex
	 * @param remotey
	 */
	protected void setExitPosition(int remotex, int remotey) {
		int bit = 0;
		if (remotex == 0)
			bit = Cells.CW_LEFT;
		else if (remotex == width-1)
			bit = Cells.CW_RIGHT;
		else if (remotey == 0)
			bit = Cells.CW_TOP;
		else if (remotey == height-1)
			bit = Cells.CW_BOT;
		else
			dbg("Generate 1");
		cells.setBitToZero(remotex, remotey, bit);
		//System.out.println("exit position set to zero: " + remotex + " " + remotey + " " + bit + ":" + cells.hasMaskedBitsFalse(remotex, remotey, bit));
	}

	/**
	 * Sets the starting position (startx,starty) to 0,0. Used to bethe cell which is farthest away from the exit
	 */
	protected void setStartPositionToCellWithMaxDistance() {
		int x=0;
		int y=0;
		int d = 0;
		startx = x;
		starty = y;
		for (x = 0; x != width; x++)
			for (y = 0; y != height; y++) {
				if (dists[x][y] > d) {
					d = dists[x][y];
					 startx = x;  //if we're setting the starting point farthest away from solution
					 starty = y;
				}
			}
	}



	/**
	 * Computes distances to the exit position (ax,ay) for all cells in array dists.
	 * @param ax, exit position, x coordinate
	 * @param ay, exit position, y coordinate
	 */
	public void computeDists(int ax, int ay) {
		int x, y;
		int inf = 99999999; //TODO: bad programming practice: replace magic number of max int
		// initialize the distance array with a value for infinity 
		for (x = 0; x != width; x++)
			for (y = 0; y != height; y++)
				dists[x][y] = inf;
		// set the final distance at the exit position
		dists[ax][ay] = 1;
		int[] masks = Cells.getMasks() ;

		boolean done;
		// go over this array as long as we can find something to do
		// MEMO: there are likely to be much smarter ways to distribute distances in a breadth first manner...
		// why not push identified cells with infinite distance on a "work to do" heap
		do {
			done = true;
			// check all entries in the distance array
			for (x = 0; x != width; x++)
				for (y = 0; y != height; y++) 
				{
					int sx = x;
					int sy = y;
					int d = dists[sx][sy];
					if (d == inf) { // found work to do. 
						// Since the maze must have a way to the exit from any position the distance cannot be infinite
						done = false;
						continue;
					}
					// if the distance is not infinite, let's see if the cell has a neighbor that we can update and 
					// perform a depth first search on.
					
					int run = 0;
					while (true) {
						
						int n, nextn = -1;
						// check all four directions
						for (n = 0; n != 4; n++) {
							int nx = sx+dirsx[n];
							int ny = sy+dirsy[n];
							// if there is no wall in this direction and 
							// the reachable cell has a higher distance value
							// update the distance value and mark that cell as the next one
							if (cells.hasMaskedBitsFalse(sx, sy, masks[n]) &&
									dists[nx][ny] > d+1) {
								dists[nx][ny] = d+1;
								nextn = n;
							}
						}
						run++;
						if (nextn == -1)
							break; // exit the loop if we cannot find another cell to proceed with
						// update coordinates for next cell
						sx += dirsx[nextn];
						sy += dirsy[nextn];
						// update distance for next cell
						d++;
						// follow the nextn node on a depth-first-search path
						

					}
				}

		} while (!done);
	}


	/**
	 * Allocates space for a room of random dimensions in the maze.
	 * @return true if room is successfully placed, false otherwise
	 */
	private boolean placeRoom() {
		// get width and height of random size that are not too large
		// if too large return as a failed attempt
		int rw = randNo(3, 8);
		int rh = randNo(3, 8);
		if (rw >= width-4)
			return false;
		if (rh >= height-4)
			return false;
		// proceed for a given width and height
		// obtain a random position (rx,ry) such that room is located on as a rectangle with (rx,ry) and (rxl,ryl) as corner points
		// upper bound is chosen such that width and height of room fits maze area.
		int rx = randNo(1, width-rw-1);
		int ry = randNo(1, height-rh-1);
		int rxl = rx+rw-1;
		int ryl = ry+rh-1;
		// check all cells in this area if they already belong to a room
		// if this is the case, return false for a failed attempt
		if (cells.areaOverlapsWithRoom(rx, ry, rxl, ryl))
			return false ;
		// since the area is available, mark it for this room and remove all walls
		// from this on it is clear that we can place the room on the maze
		cells.markAreaAsRoom(rw, rh, rx, ry, rxl, ryl, random); 
		return true;
	}

	/**
	 * Identifies segments of continuous walls on the maze and fills the segment list 
	 * @return vector of segments
	 */
	private Vector<Seg> generateSegments() {
		int x, y;
		Vector<Seg> sl = new Vector<Seg>();

		// we search for horizontal walls, so for each column
		for (y = 0; y != height; y++) {
			// first round through rows
			x = 0;
			while (x < width) {
				// skip cells without wall on top
				if (cells.hasNoWallOnTop(x, y)) {
					x++;
					continue;
				} 
				// found one
				int startx = x;
				// follow segment with wall on top till
				// x is the first index of a cell that has no wall on top
				// stop at outer bound or when hitting a wall (cell has wall on left)
				// such that length of the segment is startx-x, which is a negative value btw
				while (cells.hasWallOnTop(x, y)) {
					x++;
					if (x == width)
						break;
					if (cells.hasWallOnLeft(x, y))
						break;
				}
				// create segment with (x,y) being the end positions, startx-x being the negative length
				// note the (x,y) is not part of the segment
				sl.addElement(new Seg(x*map_unit, y*map_unit,
						(startx-x)*map_unit, 0, dists[startx][y], colchange));
			}
			// second round through rows, same for bottom walls
			x = 0;
			while (x < width) {
				if (cells.hasNoWallOnBottom(x, y)) {
					x++;
					continue;
				} 
				int startx = x;
				while (cells.hasWallOnBottom(x, y)) {
					x++;
					if (x == width)
						break;
					if (cells.hasWallOnLeft(x, y))
						break;
				}
				// create segment with (startx,y+1) being one below the start position, x-startx being the positive length
				// so this may represent a bottom wall segment as a top wall segment one below
				sl.addElement(new Seg(startx*map_unit, (y+1)*map_unit,
						(x-startx)*map_unit, 0, dists[startx][y], colchange));
			}
		} 
		// we search for vertical walls, so for each row
		for (x = 0; x != width; x++) {
			y = 0;
			while (y < height) {
				if (cells.hasNoWallOnLeft(x, y)) {
					y++;
					continue;
				} 
				int starty = y;
				while (cells.hasWallOnLeft(x, y)) {
					y++;
					if (y == height)
						break;
					if (cells.hasWallOnTop(x, y))
						break;
				}
				// create segment with (x,starty) being being the actual start position of the segment, y-starty being the positive length
				sl.addElement(new Seg(x*map_unit, starty*map_unit,
						0, (y-starty)*map_unit, dists[x][starty], colchange));
			}
			y = 0;
			while (y < height) {
				if (cells.hasNoWallOnRight(x, y)) {
					y++;
					continue;
				} 
				int starty = y;
				while (cells.hasWallOnRight(x, y)) {
					y++;
					if (y == height)
						break;
					if (cells.hasWallOnTop(x, y))
						break;
				}
				// create segment with (x+1,y) being being one off in both directions from the last cell in this segment, starty-y being the negative length
				// since we are looking at right walls, one off in the right direction (x+1) are then cells that have this segment on its left hand side
				sl.addElement(new Seg((x+1)*map_unit, y*map_unit,
						0, (starty-y)*map_unit, dists[x][starty], colchange));
			}
		}
		// starting positions for segments seem to be chosen such that segments represent top or left walls
		return sl ;
	}
	

	/**
	 * Set the partition bit to true for segments on the border and the direction is 0
	 * @param sl
	 */
	private void setPartitionBitForCertainSegments(Vector<Seg> sl) {
		for (int i = 0; i != sl.size(); i++) {
			Seg se = sl.elementAt(i);
			if (((se.x == 0 || se.x == width ) && se.dx == 0) ||
					((se.y == 0 || se.y == height) && se.dy == 0))
				se.partition = true;
		}
	}


	/**
	 * Generates a data structure that creates a binary tree to partition the set of segments into groups that are allocated
	 * in same local regions on the maze. Current hypothesis: this is used to make the search for relevant segments in the FirstPersonDrawer more efficient.
	 * @return BSPnode
	 */
	public BSPNode genNodes() {
		return genNodes(seglist);
	}

	/**
	 * It generates the nodes. In every node, it has two section, left and right. It chooses the segment
	 * which has the minimum grade value and then split this node into two nodes through this segment.
	 * If all the segments in one node are partitioned, it will stop to split.
	 * @param sl
	 * @return
	 */
	private BSPNode genNodes(Vector<Seg> sl) {
		// if there is no segment with a partition bit set to false, there is nothing else to do and we are at a leaf node
		if (countNonPartitions(sl) == 0)
			return new BSPLeaf(sl);
		// from the ones that have a partition bit set to false, pick a candidate with a low grade
		Seg pe = findPartitionCandidate(sl);
		// work on segment pe
		// mark pe as partitioned
		pe.partition = true;
		int x  = pe.x;
		int y  = pe.y;
		int dx = pe.dx;
		int dy = pe.dy;
		Vector<Seg> lsl = new Vector<Seg>();
		Vector<Seg> rsl = new Vector<Seg>();
		for (int i = 0; i != sl.size(); i++) {
			Seg se = (Seg) sl.elementAt(i);
			int df1x = se.x - x;
			int df1y = se.y - y;
			int sendx = se.x + se.dx;
			int sendy = se.y + se.dy;
			int df2x = sendx - x;
			int df2y = sendy - y;
			int nx = dy;
			int ny = -dx;
			int dot1 = df1x * nx + df1y * ny;
			int dot2 = df2x * nx + df2y * ny;
			if (getSign(dot1) != getSign(dot2)) {
				if (dot1 == 0)
					dot1 = dot2;
				else if (dot2 != 0) {
					// we need to split this
					int spx = se.x;
					int spy = se.y;
					if (dx == 0)
						spx = x;
					else
						spy = y;
					Seg sps1 = new Seg(se.x, se.y, spx-se.x, spy-se.y, se.dist, colchange);
					Seg sps2 = new Seg(spx, spy, sendx-spx, sendy-spy, se.dist, colchange);
					if (dot1 > 0) {
						rsl.addElement(sps1);
						lsl.addElement(sps2);
					} else {
						rsl.addElement(sps2);
						lsl.addElement(sps1);
					}
					sps1.partition = sps2.partition = se.partition;
					continue;
				}
			}
			if (dot1 > 0 || (dot1 == 0 && se.getDir() == pe.getDir())) {
				rsl.addElement(se);
				if (dot1 == 0)
					se.partition = true;
			} else if (dot1 < 0 || (dot1 == 0 && se.getDir() == -pe.getDir())) { 
				lsl.addElement(se);
				if (dot1 == 0)
					se.partition = true;
			} else {
				dbg("error xx 1 "+dot1);
			}
		}
		if (lsl.size() == 0)
			return new BSPLeaf(rsl);
		if (rsl.size() == 0)
			return new BSPLeaf(lsl);
		return new BSPBranch(x, y, dx, dy, genNodes(lsl), genNodes(rsl)); // recursion on both branches
	}

	/**
	 * It finds the segment which has the minimum grade value.
	 * @param sl vector of segment
	 * @return Segment
	 */
	private Seg findPartitionCandidate(Vector<Seg> sl) {
		Seg pe = null ;
		int bestgrade = 5000; // used to compute the minimum of all observed grade values, set to some high initial value
		int maxtries = 50; // constant, only used to determine skip
		// consider a subset of segments proportional to the number of tries, here 50, seems to randomize the access a bit
		int skip = (sl.size() / maxtries);
		if (skip == 0)
			skip = 1;
		for (int i = 0; i < sl.size(); i += skip) {
			Seg pk = (Seg) sl.elementAt(i);
			if (pk.partition)
				continue;
			partiters++;
			if ((partiters & 31) == 0) {
				// During maze generation, the most time consuming part needs to occasionally update the current screen
				// 
				if (maze.increasePercentage(partiters*100/expected_partiters))
				{
					// give main thread a chance to process keyboard events
					try {
						Thread.currentThread().sleep(10);
					} catch (Exception e) { }
				}
			}
			int grade = grade_partition(sl, pk);
			if (grade < bestgrade) {
				bestgrade = grade;
				pe = pk; // determine segment with smallest grade
			}
		}
		return pe;
	}

	/**
	 * Counts how many elements in the segment vector have their partition bit set to false
	 * @param sl
	 * @return
	 */
	private int countNonPartitions(Vector<Seg> sl) {
		int result = 0 ;
		for (int i = 0; i != sl.size(); i++)
			if (!(sl.elementAt(i)).partition)
				result++;
		return result;
	}

	private static void dbg(String str) {
		System.out.println("MazeBuilder: "+str);
	}



	/**
	 * Fill the given maze object with a newly computed maze according to parameter settings
	 * @param mz maze to be filled
	 * @param w width of requested maze
	 * @param h height of requested maze
	 * @param roomct number of rooms
	 * @param pc number of expected partiters
	 */
	public void build(Maze mz, int w, int h, int roomct, int pc) {
		width = w;
		height = h;
		maze = mz;
		rooms = roomct;
		cells = new Cells(w,h) ;
		origdirs = new int[w][h];
		dists = new int[w][h];
		expected_partiters = pc;
		buildThread = new Thread(this);
		buildThread.start();
		//run();
		
		
	}
	
	

	/**
	 * Main method to run construction of a new maze with a MazeBuilder in a thread of its own.
	 * This method is implicitly called by the build method when it sets up and starts a new thread for this object.
	 */
	public void run() {
		Log.v("Mazebuilder","start");
		int tries = 250;

		colchange = randNo(0, 255);
		// create a maze where all walls and borders are up
		cells.initialize();
		// try to put as many rooms into the maze as requested but not more than the number of tries == 250
		while (tries > 0 && rooms > 0) {
			if (placeRoom())
				rooms--;
			else
				tries--;
		}
		// put pathways into the maze, determine its starting and end position and calculate distances
		generate();
		Log.v("Post","generate");
		// determine segments, i.e. walls over multiple cells in vertical or horizontal direction
		seglist = generateSegments();
		setPartitionBitForCertainSegments(seglist); // partition bit true means that those are not considered any further for node generation
		cells.setTopToOne(0, 0); // TODO: check why this is done. It creates a top wall on position (0,0). This may even corrupt a maze and block its exit!
		
		partiters = 0;
		BSPNode root = genNodes();
		// dbg("partiters = "+partiters);
		// communicate results back to maze object
		
		maze.newMaze(root, cells, dists, startx, starty);
		Log.v("MAZEBUILDER", "postNewMaze");

		if(GlobalItems.maze.firstpersondrawer == null){
			Log.v("NULL", "MAZEBUILDERFIRSTPERSONDRAWER");
		}
		maze.mazeh = height;
		maze.mazew = width;
	}

	// TODO: bring thread communication up to date, stop is deprecated forever
	public void Interrupt() {
		buildThread.stop();
	}

	


}
