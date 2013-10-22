/**
 * 
 */
package edu.wm.cs301.UI;

import java.util.Vector;

/**
 * A leaf node for a tree of BSPNodes. It carries a list of segments. 
 * 
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 */
public class BSPLeaf extends BSPNode {
	Vector<Seg> slist;

	/**
	 * Constructor
	 */
	BSPLeaf(Vector<Seg> sl) {
		slist = sl;
		xl = yl =  1000000; // TODO: poor programming, supposed to be largest possible integer
		xu = yu = -1000000; // TODO: poor programming, supposed to be smallest possible integer
		isleaf = true;
		for (int i = 0; i != sl.size(); i++) {
			Seg se = (Seg) slist.elementAt(i);
			fix_bounds(se.x, se.y);
			fix_bounds(se.x + se.dx, se.y + se.dy);
		}
	}
	
	/**
	 * Updates internal fields for upper and lower bounds of (x,y) coordinates
	 * @param x
	 * @param y
	 */
	private void fix_bounds(int x, int y) {
		xl = Math.min(xl, x);
		yl = Math.min(yl, y);
		xu = Math.max(xu, x);
		yu = Math.max(yu, y);
	}
	
}

