/**
 * 
 */
package edu.wm.cs301.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author bsweaver
 *
 */
public class MapView extends View {
	//public static int recordMoves;
	//public static int recordEnergy;
	public static int energy = 2500;
	public static int moves = 0;
	public MapView(Context context){
		super(context);
		setFocusableInTouchMode(true);
		setFocusable(true);
	}
	public MapView(Context context, AttributeSet attSet)
	{
		super(context,attSet);
		setFocusableInTouchMode(true);
		setFocusable(true);
		
	}
	
	
	
	@Override protected void onDraw(Canvas canvas) {
		if(GlobalItems.maze.state == 4){
			Log.v("stateFinished", "activity in mapview");

			//SharedPreferences prefs;
		    //SharedPreferences.Editor edit;
		    //final String MYPREFS = "MyPreferences_000";

		    
		    
			//////////////////Activity host = (Activity) this.getContext();
			//prefs = host.getSharedPreferences(MYPREFS, 0);
			/**edit = prefs.edit();
			
			int recordMoves = prefs.getInt("moves"+Integer.toString(StateGenerating.skill), 99999999);
			Log.v("recordMoves", "" + recordMoves);
			Log.v("curMoves","" + moves);
			//edit.putInt("moves"+Integer.toString(StateGenerating.skill),Math.min(recordMoves, moves));
			
			int recordEnergy = prefs.getInt("energy"+Integer.toString(StateGenerating.skill), 0);
			//edit.putInt("energy"+Integer.toString(StateGenerating.skill), Math.max(recordEnergy, energy));
			
			int successes = prefs.getInt("successes"+Integer.toString(StateGenerating.skill), 0);
			//edit.putInt("successes"+Integer.toString(StateGenerating.skill), 0);
			
			//edit.commit();
			**/
			
			///////////////////////////Intent i = new Intent(host, StateFinish.class);
			//Bundle b = new Bundle();
			
			//b.putInt("energy", recordEnergy);
			//b.putInt("moves", recordMoves);
			//i.putExtras(b);
			//////////////////////////host.startActivity(i);
		}
		else{
			
			GlobalItems.wrapper.setCanvas(canvas);
			GlobalItems.wrapper.setColor(Color.DKGRAY);
			canvas.drawRect(0,200,400,400,GlobalItems.wrapper.paint);
			
			
			if(GlobalItems.maze.firstpersondrawer == null){
			}
			else{
				GlobalItems.maze.start();
			}
			this.invalidate();
		}
	}
}
