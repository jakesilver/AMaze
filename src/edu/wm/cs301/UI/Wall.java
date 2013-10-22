package edu.wm.cs301.UI;
/**
 * Basic Class used to hold wall coordinates for Prims Maze Generation
 */
public class Wall {
	public int x;
	public int y;
	public int dx;
	public int dy;
	/**
	 * Constructor
	 * @param x x position of the wall
	 * @param y y position of the wall
	 * @param dx x direction of the wall
	 * @param dy y direction of the wall
	 */
	public Wall(int xx, int yy, int dxx, int dyy)
	{
		x=xx;
		y=yy;
		dx=dxx;
		dy=dyy;
	}

}
