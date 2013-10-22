/**
 * 
 */
package edu.wm.cs301.UI;

import java.util.ArrayList;
import java.util.Random;





/**
* This class has the responsibility to create a maze of given dimensions (width, height) together with a solution based on a distance matrix.
* The Maze class depends on it. The MazeBuilder performs its calculations within its own separate thread such that communication between 
* Maze and MazeBuilder operates as follows. Maze calls the build() method and provides width and height. Maze has a call back method newMaze that
* this class calls to communicate a new maze and a BSP root node and a solution. 
* 
* This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
* Paul Falstad granted permission to modify and use code for teaching purposes.
* Refactored by Peter Kemper   
* @author Weaver, Ben and Silver, Jake
*/


public class MazeBuilderKruskal extends MazeBuilder implements Runnable{
	public MazeBuilderKruskal() {
		super();
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}
	public MazeBuilderKruskal(boolean det) {
		super(det);
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}
	/**
	 * Creates a wall list and a disjointed set for each cell, each containing just that one cell.
	 * For each wall, picked randomly, if the cells bordering that wall are in distinct cells,
	 * remove the wall from both cells and join the sets of the formerly divided cells.
	 * This is done until there is one set remaining and all the cells are connected by a path.
	 * There will be one exit and the start will be the farthest possible distance from the start.
	 */
	@Override
	public void generate(){
		// pick position (x,y) with x being random, y being 0
		// pick position (x,y) with x being random, y being 0
		//int i = randNo(0, width-1) ;
		//int j = randNo(0, height - 1);
		ArrayList<Wall> walls = new ArrayList<Wall>();
		DisjointSet set = new DisjointSet(width*height);
		int identifier1 = 0;
		int identifier2 = 0;
		
		
		
		
		
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				
				// add all interior walls to the wall list
				if (cells.canGo(x, y, 0, 1))
				{
					walls.add(new Wall(x, y, 0, 1));
				}
				if (cells.canGo(x, y, 0, -1))
				{
					walls.add(new Wall(x, y, 0, -1));
				}
				if (cells.canGo(x, y, 1, 0))
				{
					walls.add(new Wall(x, y, 1, 0));
				}
				if (cells.canGo(x, y, -1, 0))
				{
					walls.add(new Wall(x, y, -1, 0));
				}
		
			}
		}
		
		Wall curWall;
		while( set.getNumTrees()>1){
			int r = randNo(0, walls.size()-1);
			curWall = walls.get(r);
			
			int otherCellX = curWall.x+curWall.dx;
			int otherCellY = curWall.y+curWall.dy;
			
			identifier1 = (height*curWall.x) + curWall.y;
			identifier2 = (height*otherCellX)+ otherCellY;
			
			if (set.find(identifier1) != set.find(identifier2)){
				cells.deleteWall(curWall.x, curWall.y, curWall.dx, curWall.dy);
				cells.deleteWall(otherCellX, otherCellY, -curWall.dx, -curWall.dy);
				set.union(set.find(identifier1),set.find(identifier2));
			
			}
			walls.remove(r);
			
			
			
		}
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
	
	public void build(Maze mz, int w, int h, int roomct, int pc) {
		random = new Random();
		width = w;
		height = h;
		maze = mz;
		rooms = 0;
		cells = new Cells(w,h) ;
		origdirs = new int[w][h];
		dists = new int[w][h];
		expected_partiters = pc;
		buildThread = new Thread(this);
		buildThread.start();
		}
		
		
		
		
	}
	
						


