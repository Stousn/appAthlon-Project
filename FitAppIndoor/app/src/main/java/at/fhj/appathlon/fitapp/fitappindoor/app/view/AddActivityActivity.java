package at.fhj.appathlon.fitapp.fitappindoor.app.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import at.fhj.appathlon.fitapp.fitappindoor.R;
import at.fhj.appathlon.fitapp.fitappindoor.app.helper.TimePickerFragment;
import at.fhj.appathlon.fitapp.fitappindoor.app.model.Activity;
import at.fhj.appathlon.fitapp.fitappindoor.app.model.ActivityDataAccess;

/**
 * Created by Stefan Reip on 01.12.2016.
 */
public class AddActivityActivity extends AppCompatActivity {
    private ActivityDataAccess activityDataAccess;
    private int sumCalPerDay, sumActPerDay, sumDistPerDay, sumAmountExPerDay;
    private Spinner spiSportType;
    private String sportType;
    private EditText edtCal,edtMin,edtAmo,edtDist;
    private Button butSave;
    private int amountCal,amountEx,amountDist,amountMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set some GUI Views
        setContentView(R.layout.activity_add_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spiSportType = (Spinner) findViewById(R.id.activityType_spinner);

        edtAmo=(EditText) findViewById(R.id.activityAmountAmount);
        edtCal=(EditText) findViewById(R.id.activityAmountKcal);
        edtMin=(EditText) findViewById(R.id.activityAmountMinutes);
        edtDist=(EditText) findViewById(R.id.activityAmountKm);
        butSave=(Button) findViewById(R.id.activitySave);

        activityDataAccess = new ActivityDataAccess(this);

        //Dropdown of sport type. Get's the selected item
        spiSportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                sportType = item.toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /**Listens on save button and gets data if they are set correctly. If not -> Toast with error message*/
        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sportType != null){
                    try {
                        amountCal = Integer.parseInt(edtCal.getText().toString());
                        amountEx = Integer.parseInt(edtAmo.getText().toString());
                        amountMin = Integer.parseInt(edtMin.getText().toString());
                        amountDist = Integer.parseInt(edtDist.getText().toString());
                        addActivity();
                    } catch (Exception e) {
                        Toast.makeText(AddActivityActivity.this,
                                "No element was added because of an invalid input", Toast.LENGTH_LONG).show();
                        Log.e("ERROR", e.toString());
                    }
                }

                showEntries();

            }
        });

    }

    /**Creates a new activity and sets all parameters from the input data + current date*/
    public void addActivity() {
        //TODO Activity Übergabeparamter einlesen
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());

        Activity a=new Activity(1,sportType,amountDist,amountEx,date,amountCal,amountMin);

        //If no calories where added they will be calculated
        if (a.getCalories() == 0) {
            a = calcActivityCal(a);
        }
        activityDataAccess.addNewActivity(a);
    }

    /**After adding an activity -> return to MainActivity (Diary)*/
    private void showEntries() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    // OLD. Can this be removed?
    public void showTimePickerDialog(View v) {

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "TimePicker");

    }

    /**Calculates Calories
     * ATTENTION: Some hardcoded values are there by now. They need to be less hardcoded ;)
     * @values: heartRate, timeString in minutes
     * */
    public int calcCal(int heartRate, int timeStr) {
        int age = 25; //TODO: Change age to the age that could be set from Date of Birth from Log-In
        int weight = 60; //TODO: Change weight to the weight that could be set in WeightLog
        boolean isWoman = true; //TODO: Change isWoman to the gender that could be set by Log-In
        /*Fancy calculation from somewhere in the Internet. I'm sorry but I don't know the source anymore*/
        if (!isWoman) {
            return (int) (((age * 0.2017) - (weight * 0.09036) + (heartRate * 0.6309) - 55.0969) * timeStr / 4.184);
        } else {
            return (int) (((age * 0.074) - (weight * 0.05741) + (heartRate * 0.4472) - 20.4022) * timeStr / 4.184);
        }

    }

    /**Calculates Calories for an activity
     * By now the activities are selected by their name. Maybe it could be set via an ID in the
     * Database in the future.
     * ATTENTION: heartRate values are hardcoded. Maybee there are some values that would be more
     * accurate. */
    public Activity calcActivityCal(Activity a){
        int heartRate = 0;
        //Changes heartRate for each sport type
        switch(a.getSportType()) {
            case "Treadmill":
                heartRate = 160;
                break;
            case "Cross Trainer":
                heartRate = 160;
                break;
            case "Stair-Master":
                heartRate = 160;
                break;
            case "Ergometer":
                heartRate = 160;
                break;
            case "Push-Ups":
                heartRate = 170;
                break;
            case "Sit-Ups":
                heartRate = 170;
                break;
            case "Squats":
                heartRate = 170;
                break;
            case "Pull-Ups":
                heartRate = 170;
                break;
            case "Burpees":
                heartRate = 170;
                break;
        }
        a.setCalories(calcCal(heartRate, a.getDurationPerActivity()));
        return a;
    }
}
