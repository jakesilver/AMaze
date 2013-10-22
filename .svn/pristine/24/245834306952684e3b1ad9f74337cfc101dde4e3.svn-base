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
 * @author Jones.Andrew
 */

public class MazeBuilderPrim extends MazeBuilder implements Runnable{
	public MazeBuilderPrim() {
		super();
		System.out.println("MazeBuilderPrim uses Prim's algorithm to generate maze.");
	}
	public MazeBuilderPrim(boolean det) {
		super(det);
		System.out.println("MazeBuilderPrim uses Prim's algorithm to generate maze.");
	}

	/** 
	 * Creates a wall list and it knows it's done when the wall list is empty.
	 * Picks cells and walls randomly. If the cell on the opposite side is not a virgin it removes the wall from the list but does not
	 * delete the wall. If it is virgin it deletes the wall, sets the virgin flag of this cell, deletes the other wall, and then adds all the new walls
	 * to the list from the integrated cell. Repeats with the newly integrated cell as the new current cell.
	 * Cango makes sure it's within 1 space, does not go outside the border, and the cell is a virgin.
	 */ 
	@Override
	public void generate() {
		// pick position (x,y) with x being random, y being 0
		// pick position (x,y) with x being random, y being 0
		int x = randNo(0, width-1) ;
		int y = randNo(0, height - 1);
		ArrayList<Wall> walls = new ArrayList<Wall>();
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
		Wall curWall;
		while(!walls.isEmpty()){
			
			int r = randNo(0, walls.size()-1);
			curWall = walls.get(r);
			if (cells.canGo(curWall.x, curWall.y, curWall.dx, curWall.dy))
			{
				cells.deleteWall(curWall.x, curWall.y, curWall.dx, curWall.dy);
				x = curWall.x + curWall.dx;
				y = curWall.y + curWall.dy;
				cells.deleteWall(x, y, -curWall.dx, -curWall.dy);
				cells.setVirginToZero(x, y);
				/*checks to see if it has the walls, if it does it adds them to the list*/
				if (cells.hasWallOnBottom(x, y));
				{
					walls.add(new Wall(x, y, 0, 1));
				}
				if (cells.hasWallOnTop(x, y));
				{
					walls.add(new Wall(x, y, 0, -1));
				}
				if (cells.hasWallOnLeft(x, y));
				{
					walls.add(new Wall(x, y, -1, 0));
				}
				if (cells.hasWallOnRight(x, y));
				{
					walls.add(new Wall(x, y, 1, 0));
				}
				walls.remove(r);
			}
			else
			{
				walls.remove(r);
			}
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