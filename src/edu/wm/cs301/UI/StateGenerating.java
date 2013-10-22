/**
 * 
 */
package edu.wm.cs301.UI;



import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.os.Handler;
import android.view.KeyEvent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.os.AsyncTask;
import android.os.SystemClock;





/**
 * Class which implements the StateGenerating Activity from our Android application.
 * Uses a thread to simulate the progress bar to supply a delay. If the back button is pressed on the emulator,
 * returns to StateTitle
 * 
 * @author bsweaver
 *
 */
public class StateGenerating extends Activity implements View.OnKeyListener {
	
	
    
	
	ProgressBar primary;
	public static int skill;
	public int algorithm;
	public int mode;
	public int mazeFile;
	public boolean preGenerated;
	
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			primary.incrementProgressBy(5);
		}
	};
	
 
	/**
	 * Sets layout for second screen. Uses thread for simulating progress bar.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    Bundle b = this.getIntent().getExtras();
	    skill = b.getInt("skill");
	    mode = b.getInt("mode");
	    algorithm = b.getInt("algorithm");
	    mazeFile = b.getInt("mazeFile");
	    
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.main2);
	    Button backButton = (Button)findViewById(R.id.backButton);
	    backButton.setOnClickListener(backListener);
	    
	    primary=(ProgressBar)findViewById(R.id.progressBar1);
	    primary.setProgress(0);
	    //GlobalItems.maze.manualDriver = new ManualDriver(GlobalItems.maze);
	    /**try {
			GlobalItems.maze.manualDriver.setRobot(br);
		} catch (UnsuitableRobotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}**/		
	    Log.v("SKILLLEVEL","" + skill);
	    
	    new BuildMazeTask().execute();
	   

	    
	    
	    
	}
	private OnClickListener backListener = new OnClickListener() {
    	public void onClick(View v) {
    		Log.v("State Generating", "onClick() called back button");
    		
    		Log.v("State Generating", "onClick() ended restart button");
    		GlobalItems.maze.mazebuilder.buildThread.interrupt();
    		
    		Intent myIntent = new Intent(v.getContext(), StateTitle.class);
    		startActivity(myIntent);
    	}
    };
		

	//Separate Thread for building maze
	class BuildMazeTask extends AsyncTask<Integer, Void, Void>{
		
		
		@Override
		protected Void doInBackground(Integer... params) {
			
			GlobalItems.maze = new Maze();
			Log.v("doInBackground", "create maze");
			publishProgress();
			Log.v("doInBackground", "publish maze");
			GlobalItems.maze.init();
			Log.v("doInBackground", "init maze");
			publishProgress();
			Log.v("doInBackground", "publish maze");
			
			InputStream is;
			MazeFileReader mfr; 
			
			switch(mazeFile) 
		    {
		    case 1: 
		    	is= (getResources().openRawResource(R.raw.one));
		    	mfr = new MazeFileReader(is);
				GlobalItems.maze.mazeh = mfr.getHeight() ; //get attributes for newMaze
				GlobalItems.maze.mazew = mfr.getWidth() ;
				preGenerated = true;
				skill = 10;
				//create newMaze
				GlobalItems.maze.newMaze(mfr.getRootNode(),mfr.getCells(),mfr.getDistances(),mfr.getStartX(), mfr.getStartY()) ;
		    break;
		    case 2: 
		    	is= (getResources().openRawResource(R.raw.two));
		    	mfr = new MazeFileReader(is);
				GlobalItems.maze.mazeh = mfr.getHeight() ; //get attributes for newMaze
				GlobalItems.maze.mazew = mfr.getWidth() ;
				preGenerated = true;
				skill = 11;
				//create newMaze
				GlobalItems.maze.newMaze(mfr.getRootNode(),mfr.getCells(),mfr.getDistances(),mfr.getStartX(), mfr.getStartY()) ;
			break;
		    case 3: 
		    	is= (getResources().openRawResource(R.raw.six));
		    	mfr = new MazeFileReader(is);
				GlobalItems.maze.mazeh = mfr.getHeight() ; //get attributes for newMaze
				GlobalItems.maze.mazew = mfr.getWidth() ;
				preGenerated = true;
				skill = 12;
				//create newMaze
				GlobalItems.maze.newMaze(mfr.getRootNode(),mfr.getCells(),mfr.getDistances(),mfr.getStartX(), mfr.getStartY()) ;
			break;
		    case 4: 
		    	is= (getResources().openRawResource(R.raw.seven));
		    	mfr = new MazeFileReader(is);
				GlobalItems.maze.mazeh = mfr.getHeight() ; //get attributes for newMaze
				GlobalItems.maze.mazew = mfr.getWidth() ;
				preGenerated = true;
				skill = 13;
				//create newMaze
				GlobalItems.maze.newMaze(mfr.getRootNode(),mfr.getCells(),mfr.getDistances(),mfr.getStartX(), mfr.getStartY()) ;
			break;
		    case 5: 
		    	is= (getResources().openRawResource(R.raw.eight));
		    	mfr = new MazeFileReader(is);
				GlobalItems.maze.mazeh = mfr.getHeight() ; //get attributes for newMaze
				GlobalItems.maze.mazew = mfr.getWidth() ;
				preGenerated = true;
				skill = 14;
				//create newMaze
				GlobalItems.maze.newMaze(mfr.getRootNode(),mfr.getCells(),mfr.getDistances(),mfr.getStartX(), mfr.getStartY()) ;
			break;
			default:
				preGenerated = false;
				GlobalItems.maze.build(skill, algorithm);
				break;
		    }
			Log.v("doInBackground", "build maze");
			if(GlobalItems.maze.mapdrawer == null)
				Log.v("Null", "MAPDRAWER");
			publishProgress();
			Log.v("State Generating","width" + GlobalItems.maze.mazew);
			
			return (null);
		}
	
		@Override
		protected void onProgressUpdate(Void... unused){
			primary.incrementProgressBy(20);
			
		}
		
		@Override
		protected void onPostExecute(Void unused) {
			 if(preGenerated == false){
				try {
					GlobalItems.maze.mazebuilder.buildThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			Intent i = new Intent(StateGenerating.this,StatePlay.class);
			Bundle b = new Bundle();
			b.putInt("mode",mode);
			i.putExtras(b);
			startActivity(i);
		}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Log.v("State Generating", "OnClick() called back button" );
            this.cancel(true);
            GlobalItems.maze.mazebuilder.buildThread.interrupt();
            
            Intent i = new Intent(StateGenerating.this, StateTitle.class);
            startActivity(i);
            return true;
        }

        return onKeyDown(keyCode, event);
    }
	}
			

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}