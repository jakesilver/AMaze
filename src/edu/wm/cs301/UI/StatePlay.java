package edu.wm.cs301.UI;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;



/**
 * This class implements the StatePlay screen for our android application.
 * Responds to user input on the d-pad by toasting and outputting using log.v
 * has options to toggle map/solution visibility
 * pressing the back button returns to the StateTitle screen
 * @author bsweaver
 *
 */
public class StatePlay extends Activity implements View.OnKeyListener{
	
	int mode;
    static MapView mapview;
    static TextView energyText;
    static TextView moveText;
    TwoSensorRobot tsr;
    RobotDriver driver;
    private final Handler myHandler = new Handler();
    
    
    
	
	/** Called when the activity is first created. Sets appropriate buttons and listeners */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main3);

	    
	    Bundle b = this.getIntent().getExtras();
	    mode = b.getInt("mode");
	    //progress = (ProgressBar)findViewById(R.id.progressBar1);
	    Log.v("Oncreate", ""+ energyText);
	    Log.v("StatePlay","" + mode );
	    mapview = (MapView)findViewById(R.id.mapView) ;
	    GlobalItems.mapview =(MapView)findViewById(R.id.mapView) ;
	    Button mapButton = (Button)findViewById(R.id.mapButton);
	    Button visibleButton = (Button)findViewById(R.id.visibleButton);
	    Button solutionButton = (Button)findViewById(R.id.solutionButton);
	    //progress.setProgress(progress.getMax());
	    energyText = (TextView)findViewById(R.id.textView2);
	    moveText = (TextView)findViewById(R.id.textView4);
	    MapView.energy = 2500;
	    MapView.moves = 0;
	    energyText.setText(""+MapView.energy);
	    moveText.setText("" + MapView.moves);
	    Log.v("State Play","" + GlobalItems.maze.mazecells);
	    
	    Button zoomIn = (Button) findViewById(R.id.zoomIn);
	    Button zoomOut = (Button) findViewById(R.id.zoomOut);
	    Button robotButton = (Button) findViewById(R.id.button1);
	    
	    zoomIn.setOnClickListener(zoomInListener);
	    zoomOut.setOnClickListener(zoomOutListener);
	    mapButton.setOnClickListener(mapListener);
	    visibleButton.setOnClickListener(visibleListener);
	    solutionButton.setOnClickListener(solutionListener);
	    robotButton.setOnClickListener(robotListener);
	    
	    
	   
	    
	    
	    
	    
	    
	}
	private OnClickListener mapListener = new OnClickListener() {
    	public void onClick(View v) {
    		Log.v("State Play", "onClick() called map button");
    		GlobalItems.maze.map_mode = !GlobalItems.maze.map_mode; 
    		GlobalItems.maze.showMaze = !GlobalItems.maze.showMaze; 
    		GlobalItems.maze.redraw();
    		Log.v("State Play", "onClick() ended map button");
    	}
    };    
    private OnClickListener visibleListener = new OnClickListener() {
    	public void onClick(View v) {
    		Log.v("State Play", "onClick() called visible button");
    		GlobalItems.maze.map_mode = !GlobalItems.maze.map_mode; 
    		GlobalItems.maze.redraw();
    		Log.v("State Play", "onClick() ended visible button");
    	}
    };
    private OnClickListener solutionListener = new OnClickListener() {
    	public void onClick(View v) {
    		Log.v("State Play", "onClick() called solution button");    		 
    		GlobalItems.maze.showSolution = !GlobalItems.maze.showSolution;
    		GlobalItems.maze.redraw();

    	}
    };
    
    private OnClickListener zoomInListener = new OnClickListener() {
    	public void onClick(View v) {
    		Log.v("State Play", "onClick() called map button");
    		if (GlobalItems.maze.mapdrawer != null)
			{
				GlobalItems.maze.mapdrawer.incrementMapScale() ;
				GlobalItems.maze.redraw() ;
			}

    	}
    };
    
    private OnClickListener zoomOutListener = new OnClickListener() {
    	public void onClick(View v) {
    		Log.v("State Play", "onClick() called zoom out button");
    		if (GlobalItems.maze.mapdrawer != null)
			{
				GlobalItems.maze.mapdrawer.decrementMapScale() ;
				GlobalItems.maze.redraw() ;
			}
    	}
    };
    
    final Runnable updateRunnable = new Runnable() {
    	
        public void run() {
        	
            //call the activity method that updates the UI
            updateUI();
        }
    };
    
    private void updateUI()
    {
      // ... update the UI  
    	
    	GlobalItems.mapview.postInvalidate();
    	mapview = (MapView)findViewById(R.id.mapView) ;
	    GlobalItems.mapview =(MapView)findViewById(R.id.mapView) ;
	    energyText.setText(""+MapView.energy);
        moveText.setText("" + MapView.moves);
    	
    }
    
    private void doSomeHardWork()
    {
         //.... hard work
    	try {
    		while (!tsr.hasStopped() && tsr.getCurrentBatteryLevel() > 0){
			driver.drive2Exit();
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
         //update the UI using the handler and the runnable
    	try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
         myHandler.postDelayed(updateRunnable, 1000);

    }
    
    private OnClickListener robotListener = new OnClickListener() {
    	public void onClick(View v) {
    		
    		/*new Thread(new Runnable() {
    		
    			call RobotTask to do Drive2Exit one step at a time
    			
    		*/
    		
    		/*RobotTask will be outside listener and will have the while loop and go through the drive2exit
    		 * after each step, it will call handler.postdelay(updateRunnable,1000)
    		 */
    		
    		/*updateRunnable will be a final Runnable that has one method, run() which calls updateUI
    		 * UpdateUI invalidates the mapview and sleeps the thread
    		 */
    		
    		
    		
    		Log.v("StatePlay","" + mode );
    		Log.v("State Play", "onClick() called robot button");
    		Log.v("StatePlay","" + mode );
    		switch(mode){
	    	case 0: break; //Manual Driver
	    	case 1: 
	    		GlobalItems.mapview =(MapView)findViewById(R.id.mapView) ;
	    	    GlobalItems.mapview.invalidate();
	    	    energyText.setText("Robot");
	            moveText.setText("Running");
	    		tsr = new TwoSensorRobot(GlobalItems.maze);
				driver = new Gambler();
				try {
					driver.setRobot(tsr);
				} catch (UnsuitableRobotException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//	new RobotTask().execute();

			break; //Gambler
	    	case 2:
	    		GlobalItems.mapview =(MapView)findViewById(R.id.mapView) ;
	    	    GlobalItems.mapview.invalidate();
	    	    energyText.setText("Robot");
	            moveText.setText("Running");
	    		tsr = new TwoSensorRobot(GlobalItems.maze);
				driver = new CuriousGambler();
				try {
					driver.setRobot(tsr);
				} catch (UnsuitableRobotException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//new RobotTask().execute();
	    	//curious gambler
				break;
	    	case 3:
	    		GlobalItems.mapview =(MapView)findViewById(R.id.mapView) ;
	    	    GlobalItems.mapview.invalidate();
	    	    energyText.setText("Robot");
	            moveText.setText("Running");
	    		tsr = new TwoSensorRobot(GlobalItems.maze);
				driver = new WallFollower();
				try {
					driver.setRobot(tsr);
				} catch (UnsuitableRobotException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//	new RobotTask().execute();
	    	//wall follower
				break;
	    	case 4:
	    		GlobalItems.mapview =(MapView)findViewById(R.id.mapView) ;
	    	    GlobalItems.mapview.invalidate();
	    	    energyText.setText("Robot");
	            moveText.setText("Running");
	    		tsr = new TwoSensorRobot(GlobalItems.maze);
				driver = new Wizard();
				try {
					driver.setRobot(tsr);
				} catch (UnsuitableRobotException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//	new RobotTask().execute();
				break;
				
	
    		

				
	    	//wizard
	    }
    		new Thread(new Runnable() { 
                
                

				@Override
				public void run() {
					doSomeHardWork();
					
					if(GlobalItems.maze.state == 4){
			        	
			        Intent i = new Intent(StatePlay.this, StateFinish.class);
			        startActivity(i);
			        	
			        }
					
				}

}).start();
    	}	
    };
//    class RobotTask extends AsyncTask<Integer, Void, Void> {
//		@Override
//		protected Void doInBackground(Integer... params) {
//			try {
//				Log.v("drive", "2exit");
//				if(driver.drive2Exit() == false){
//					Intent i = new Intent(StatePlay.this, StateTitle.class);
//					startActivity(i);
//				}
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return (null);
//		}
//	}
    
    
    
    
    /**
     * Overrides the d-pad and arrow keys to provide toasts/log.v output to the user. Will eventually be used to move
     * manually through the maze.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
    	mapview = (MapView)findViewById(R.id.mapView) ;
	    GlobalItems.mapview =(MapView)findViewById(R.id.mapView) ;
	    GlobalItems.mapview.invalidate();
	    
    	//GlobalItems.maze.redrawPlay();
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP ) {
            Log.v("State Play", "OnClick() called up button" );
            GlobalItems.maze.walk(1);
           
            energyText.setText(""+MapView.energy);
            moveText.setText("" + MapView.moves);
            Log.v("ONKEYDOWNUP", ""+ GlobalItems.mapview);
            GlobalItems.mapview.invalidate();
            //GlobalItems.maze.redrawPlay();
            Log.v("CURRENT POSITION", "" + GlobalItems.maze.px + GlobalItems.maze.py);
            

            //return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN ) {
            Log.v("State Play", "OnClick() called down button" );
			GlobalItems.maze.walk(-1);
			energyText.setText(""+MapView.energy);
			moveText.setText("" + MapView.moves);
            GlobalItems.mapview.invalidate();
            //GlobalItems.maze.redrawPlay();
            Log.v("CURRENT POSITION", "" + GlobalItems.maze.px + GlobalItems.maze.py);

            //return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT ) {
            Log.v("State Play", "OnClick() called right button" );
            GlobalItems.maze.rotate(-1);
            energyText.setText(""+MapView.energy);
            moveText.setText("" + MapView.moves);
            GlobalItems.mapview.invalidate();
            //GlobalItems.maze.redrawPlay();
            Log.v("CURRENT POSITION", "" + GlobalItems.maze.px + GlobalItems.maze.py);

            //return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT ) {
            Log.v("State Play", "OnClick() called left button" );
            GlobalItems.maze.rotate(1);
            energyText.setText(""+MapView.energy);
            moveText.setText("" + MapView.moves);
            GlobalItems.mapview.invalidate();
            //GlobalItems.maze.redrawPlay();
            Log.v("CURRENT POSITION", "" + GlobalItems.maze.px + GlobalItems.maze.py);

            //return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Log.v("State Play", "OnClick() called back button" );
    
            Intent i = new Intent(StatePlay.this, StateTitle.class);
            this.finish();
            startActivity(i);
            //return true;
        }
        
        if(GlobalItems.maze.state == 4){
        	
        	Intent i = new Intent(StatePlay.this, StateFinish.class);
        	this.startActivity(i);
        	
        }
        

        return super.onKeyDown(keyCode, event);
    }
    
    
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
    
}



	