/**
 * 
 */
package edu.wm.cs301.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * This class implements the Activity for the Finish screen in our android application.
 * gives user option to restart the program. Displays battery level and moves used.
 * pressing the back button also returns to StateTitle
 * @author bsweaver
 *
 */
public class StateFinish extends Activity {

	/** Called when the activity is first created. Sets up appropriate buttons and listeners */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main4);
	    Bundle b = this.getIntent().getExtras();
	    
	    Button restartButton = (Button)findViewById(R.id.button1);
	    TextView solves = (TextView)findViewById(R.id.solves);
	    TextView energy = (TextView)findViewById(R.id.textView5);
	    TextView moves = (TextView)findViewById(R.id.moves);
	    TextView curEnergy = (TextView)findViewById(R.id.textView8);
	    TextView curMoves = (TextView)findViewById(R.id.textView9);
	    
	    restartButton.setOnClickListener(restartListener);
	    SharedPreferences prefs;
	    SharedPreferences.Editor edit;

	    final String MYPREFS = "MyPreferences_000";

	    
	    prefs = getSharedPreferences(MYPREFS, 0);
	    edit = prefs.edit();
		
		int recordMoves = prefs.getInt("moves"+Integer.toString(StateGenerating.skill), 99999999);
		Log.v("recordMoves", "" + recordMoves);
		Log.v("curMoves","" + MapView.moves);
		edit.putInt("moves"+Integer.toString(StateGenerating.skill),Math.min(recordMoves, MapView.moves));
		Log.v("recordMoves",""+ Math.min(recordMoves,MapView.moves));
		int recordEnergy = prefs.getInt("energy"+Integer.toString(StateGenerating.skill), 0);
		Log.v("recordEnergy",""+recordEnergy);
		edit.putInt("energy"+Integer.toString(StateGenerating.skill), Math.max(recordEnergy, MapView.energy));
		Log.v("recordEnergy after edit",""+ Math.max(recordEnergy, MapView.energy));
		int successes = prefs.getInt("successes"+Integer.toString(StateGenerating.skill), 1);
		edit.putInt("successes"+Integer.toString(StateGenerating.skill), (successes+1));
		edit.commit();
	    
	    
	    
	    int lowestMoves = prefs.getInt("moves"+Integer.toString(StateGenerating.skill),Math.min(recordMoves, MapView.moves));
	    int mostEnergy = prefs.getInt("energies"+Integer.toString(StateGenerating.skill),Math.max(recordEnergy, MapView.energy));

	    solves.setText("      " + successes);
	    moves.setText("              " + lowestMoves);
	    energy.setText("" + mostEnergy);
	    curEnergy.setText("" + MapView.energy);
	    curMoves.setText("                          " + MapView.moves);
	   
	    
	    
	    
	}
	
	private OnClickListener restartListener = new OnClickListener() {
    	public void onClick(View v) {
    		Log.v("State Finish", "onClick() called restart button");
    		
    		Log.v("State finish", "onClick() ended restart button");
    		Intent myIntent = new Intent(v.getContext(), StateTitle.class);
    		startActivity(myIntent);
    	}
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Log.v("State Finish", "OnClick() called back button" );
           
            Intent i = new Intent(StateFinish.this, StateTitle.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    
}