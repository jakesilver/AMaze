/**
 * 
 */
package edu.wm.cs301.UI;

import java.io.File;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar;
import android.widget.CheckBox;


/**
 * 
 * This class implements the Activity for the Title screen of our android application. Supplies the user with buttons for 
 * maze type selection, difficulty, map view, generate from file/save to file, and driver/manual solver choice. Moves to StateGenerating when
 * the generate button is pressed.
 * 
 * 
 * @author Ben Weaver and Jake Silver 
 *
 */
public class StateTitle extends Activity {
	
	
	 int skill; 
	 int algorithm; 
	 int mode; 
	 int mazeFile;
	 
	 
	 /** For mode:
	  * manual = 0
	  * gambler = 1
	  * CGamb = 2
	  * WF = 3
	  * WZRD = 4
	  */

	/** Called when the activity is first created.
	 * initializes buttons and listeners for Title screen.
	 * Sets default method to Falstad's algorithm and Manual solver.
	 *  
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    Button generateButton = (Button)findViewById(R.id.generateButton);
	    Button resetButton = (Button)findViewById(R.id.resetButton);
	    //Button storeButton = (Button)findViewById(R.id.storeButton);
	    //Button openButton = (Button)findViewById(R.id.openButton);
	    
	    Button mazeOne = (Button)findViewById(R.id.mazeOne);
	    Button mazeTwo = (Button)findViewById(R.id.mazeTwo);
	    Button mazeThree = (Button)findViewById(R.id.mazeThree);
	    Button mazeFour = (Button)findViewById(R.id.mazeFive);
	    Button mazeFive = (Button)findViewById(R.id.mazeFour);
	    
	    
	
	    
	    Button kruskalButton = (Button)findViewById(R.id.kruskalButton);
	    Button primButton = (Button)findViewById(R.id.primButton);
	    Button falstadButton = (Button)findViewById(R.id.falstadButton);
	    ((CompoundButton) falstadButton).setChecked(true);
	    
	    
	    Button curiousGamblerButton = (Button)findViewById(R.id.curiousGamblerButton);
	    Button gamblerButton = (Button)findViewById(R.id.gamblerButton);
	    Button wizardButton = (Button)findViewById(R.id.wizardButton);
	    Button manualButton = (Button)findViewById(R.id.manualDriverButton);
	    Button wallFollowerButton = (Button)findViewById(R.id.wallFollowerButton);
	    ((CompoundButton) manualButton).setChecked(true);
	    
	    
	    SeekBar difficultySlider = (SeekBar)findViewById(R.id.Difficulty);
	    
	    
	    
	    generateButton.setOnClickListener(generateListener);
	    resetButton.setOnClickListener(resetListener);
	    //storeButton.setOnClickListener(storeListener);
	    kruskalButton.setOnClickListener(kruskalListener);
	   //openButton.setOnClickListener(openListener);
	    primButton.setOnClickListener(primListener);
	    falstadButton.setOnClickListener(falstadListener);
	    curiousGamblerButton.setOnClickListener(curiousGamblerListener);
	    gamblerButton.setOnClickListener(gamblerListener);
	    wizardButton.setOnClickListener(wizardListener);
	    manualButton.setOnClickListener(manualListener);
	    wallFollowerButton.setOnClickListener(wallFollowerListener);
	    
	    mazeOne.setOnClickListener(mazeOneListener);
	    mazeTwo.setOnClickListener(mazeTwoListener);
	    mazeThree.setOnClickListener(mazeThreeListener);
	    mazeFour.setOnClickListener(mazeFourListener);
	    mazeFive.setOnClickListener(mazeFiveListener);
	    
	    difficultySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
		    	Log.v("State Title", "onClick() called difficulty button");
		    	skill = seekBar.getProgress();
		    	Log.v("State Title", "onClick() ended difficulty button");
		    	}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
				
			
		});
	    
	}
	
	/**
	 * Method to change all user selections on Title screen back to their defaults. Used when the reset button is pressed.
	 */
	public void setDefaults(){
		
		
	    
	    Button kruskalButton = (Button)findViewById(R.id.kruskalButton);
	    Button primButton = (Button)findViewById(R.id.primButton);
	    Button falstadButton = (Button)findViewById(R.id.falstadButton);
	    
	    
	    Button curiousGamblerButton = (Button)findViewById(R.id.curiousGamblerButton);
	    Button gamblerButton = (Button)findViewById(R.id.gamblerButton);
	    Button wizardButton = (Button)findViewById(R.id.wizardButton);
	    Button manualButton = (Button)findViewById(R.id.manualDriverButton);
	    Button wallFollowerButton = (Button)findViewById(R.id.wallFollowerButton);
	    
	    Button mazeOne = (Button)findViewById(R.id.mazeOne);
	    Button mazeTwo = (Button)findViewById(R.id.mazeTwo);
	    Button mazeThree = (Button)findViewById(R.id.mazeThree);
	    Button mazeFour = (Button)findViewById(R.id.mazeFive);
	    Button mazeFive = (Button)findViewById(R.id.mazeFour);
	    
	    
	    SeekBar difficultySlider = (SeekBar)findViewById(R.id.Difficulty);
	    
	    
	    
		((CompoundButton) kruskalButton).setChecked(false);
		((CompoundButton) primButton).setChecked(false);
		((CompoundButton) falstadButton).setChecked(true);
		((CompoundButton) manualButton).setChecked(true);
		((CompoundButton) wallFollowerButton).setChecked(false);
		((CompoundButton) gamblerButton).setChecked(false);
		((CompoundButton) curiousGamblerButton).setChecked(false);
		((CompoundButton) wizardButton).setChecked(false);
		difficultySlider.setProgress(0);
		
		((CompoundButton) mazeOne).setChecked(false);
		((CompoundButton) mazeTwo).setChecked(false);
		((CompoundButton) mazeThree).setChecked(false);
		((CompoundButton) mazeFour).setChecked(false);
		((CompoundButton) mazeFive).setChecked(false);
	}
	
	
	
	private OnClickListener generateListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called generate button");
	    		
	    		Log.v("State Title", "onClick() ended generate button");
	    		Intent myIntent = new Intent(v.getContext(), StateGenerating.class);
	    		Bundle b = new Bundle();
	    		b.putInt("skill", skill);
	    		b.putInt("algorithm", algorithm);
	    		b.putInt("mode", mode);
	    		b.putInt("mazeFile", mazeFile);
	    		myIntent.putExtras(b);
	    		startActivity(myIntent);
	    	}
	    };
	    
	private OnClickListener resetListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called reset button");
	    		
	    		Log.v("State Title", "onClick() ended reset button");
	    		setDefaults();
	    	}
	    };    
	 private OnClickListener storeListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called store button");

	    		Log.v("State Title", "onClick() ended store button");
	    	}
	    };
	   private OnClickListener kruskalListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called kruskal button");
	    		algorithm = 2;
	    		Log.v("State Title", "onClick() ended kruskal button");
	    	}
	    };
	   private OnClickListener openListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called open button");

	    		Log.v("State Title", "onClick() ended open button");
	    	}
	    };
	    private OnClickListener primListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called prim button");
	    		algorithm = 1;
	    		Log.v("State Title", "onClick() ended prim button");
	    	}
	    };
	    private OnClickListener falstadListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called falstad button");
	    		algorithm = 0;
	    		Log.v("State Title", "onClick() ended falstad button");
	    	}
	    };
	    
	    private OnClickListener manualListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called manual button");
	    		mode = 0;
	    		Log.v("State Title", "onClick() ended manual button");
	    	}
	    };
	    private OnClickListener gamblerListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called gambler button");
	    		mode = 1;
	    		Log.v("State Title", "onClick() ended gambler button");
	    	}
	    };
	    private OnClickListener curiousGamblerListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called curious gambler button");
	    		mode = 2;
	    		Log.v("State Title", "onClick() ended curious gambler button");
	    	}
	    };

	    private OnClickListener wallFollowerListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called wall Follower button");
	    		mode = 3;
	    		Log.v("State Title", "onClick() ended wall follower button");
	    	}
	    };
	    private OnClickListener wizardListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "onClick() called wizard button");
	    		mode = 4;
	    		Log.v("State Title", "onClick() ended wizard button");
	    	}
	    };
	   
	    
	    
	    private OnClickListener mazeOneListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "mazeOne");
	    	
	    		mazeFile = 1;
	    		
	    	}
	    };
	    
	    private OnClickListener mazeTwoListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "mazeTwo");
	    		mazeFile = 2;
	    	}
	    };

	    private OnClickListener mazeThreeListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "mazeThree");
	    		mazeFile = 3;
	    	}
	    };
	    
	    private OnClickListener mazeFourListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "mazeFour");
	    		mazeFile = 4;
	    	}
	    };
	    
	    private OnClickListener mazeFiveListener = new OnClickListener() {
	    	public void onClick(View v) {
	    		Log.v("State Title", "mazeFive");
	    		mazeFile = 5;
	    	}
	    };
	    
	    
	    @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event)  {
	        if (keyCode == KeyEvent.KEYCODE_BACK ) {
	            Log.v("State Finish", "OnClick() called back button" );

	            Intent i = new Intent(StateTitle.this, StateTitle.class);
	            startActivity(i);
	            return true;
	        }

	        return super.onKeyDown(keyCode, event);
	    }
	    
	
	protected void onStart() {
		super.onStart();
	}
	
	protected void onResume() {
		super.onResume();
	}
	
	protected void onPause() {
		super.onPause();
	}
	
	protected void onStop(){
		super.onStop();
	}
	
	protected void onDestroy(){
		super.onDestroy();
	}

	    
	    
	    
	    
	    // TODO Auto-generated method stub
	

}
