/**
 * 
 */
package edu.wm.cs301.UI;
import android.graphics.*;
import android.util.Log;

/**
 * @author bsweaver
 *
 */
public class GraphicsWrapper  {
	 
	
	int color;
	Paint paint = new Paint();
	int walkstep;
	Path path = new Path();
	Point point;
	Rect rectangle;
	Canvas canvas;	
	
	
	
		// TODO Auto-generated constructor stub

	public void setCanvas(Canvas canvas){
		this.canvas = canvas;
	}
	
	public Canvas getCanvas(){
		return canvas;
	}
	
	public int makeColor(int r, int g, int b){
		color = Color.rgb(r,g,b);
		return color;
	}
	public void setColor(int color){
		paint.setColor(color);
	}
	
	public int getColor(){
		return color;
	}
	
	public void WrapperFillRect(int a, int b, int c, int d){
		rectangle = new Rect(a,b,c,d);
		Log.v("fill", "rect");
		canvas.drawRect(rectangle,paint);
	}
	
	public Point pointMaker(int x1, int x2) {
		Point p = new Point(x1, x2);
		return p;
	}
	public int getPX(int x1i, int x2) {
		return pointMaker(x1i, x2).x;
	}
	public int getPY(int x1i, int x2) {
		return pointMaker(x1i, x2).y;
	}

	public void fillPolygon(int[] xps, int yps[], int c){
		path = new Path();
		int x; int y;
		for (int n = 0; n < c; n++)
		{
			x=xps[n];
			y=yps[n];
			if(n==0)
				path.moveTo(x, y);
			else
				path.lineTo(x, y);
		}
		canvas.drawPath(path, paint);
			
			
	}
	
	public void drawLine(int a, int b, int c, int d){
		canvas.drawLine(a, b, c, d, paint);
	}
	
	public void fillOval(int a, int b, int circleSize, int circleSize2){
		//canvas.drawOval(oval, paint);
	}
	
	public void setPoint(Point point){
		this.point = point;
	}
	
	public Point getPoint(){
		return point;
	}
	
	public Point makePoint(int a, int b){
		point = new Point(a,b);
		return point;
	}
	
	
	
	

}
