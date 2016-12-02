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

    public void addActivity() {
        //TODO Activity Übergabeparamter einlesen
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());

        Activity a=new Activity(1,sportType,amountDist,amountEx,date,amountCal,amountMin);

        if (a.getCalories() == 0) {
            a = calcActivityCal(a);
        }
        activityDataAccess.addNewActivity(a);
    }

    private void showEntries() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    public void showTimePickerDialog(View v) {

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "TimePicker");

    }

    public int calcCal(int heartRate, int timeStr) {
        //Time in Minutes
       // timeStr = 25;
        int age = 25;
        int weight = 60;
        boolean isWoman = true;
        if (!isWoman) {
            return (int) (((age * 0.2017) - (weight * 0.09036) + (heartRate * 0.6309) - 55.0969) * timeStr / 4.184);
        } else {
            return (int) (((age * 0.074) - (weight * 0.05741) + (heartRate * 0.4472) - 20.4022) * timeStr / 4.184);
        }

    }
    public Activity calcActivityCal(Activity a){
        int heartRate;
        switch(a.getSportType()) {
            case "Treadmill":
                //Calculation
                heartRate = 160;
                a.setCalories(calcCal(heartRate, a.getDurationPerActivity()));
                break;
            case "Cross Trainer":
                //Calculation
                heartRate = 160;
                a.setCalories(calcCal(heartRate, a.getDurationPerActivity()));
                break;
            case "Stair-Master":
                //Calculation
                heartRate = 160;
                a.setCalories(calcCal(heartRate, a.getDurationPerActivity()));
                break;
            case "Ergometer":
                //Calculation
                heartRate = 160;
                a.setCalories(calcCal(heartRate, a.getDurationPerActivity()));
                break;
            case "Push-Ups":
                //Calculation
                heartRate = 170;
                a.setCalories(calcCal(heartRate, a.getDurationPerActivity()));
                break;
            case "Sit-Ups":
                //Calculation
                heartRate = 170;
                a.setCalories(calcCal(heartRate, a.getDurationPerActivity()));
                break;
            case "Squats":
                //Calculation
                heartRate = 170;
                a.setCalories(calcCal(heartRate, a.getDurationPerActivity()));
                break;
            case "Pull-Ups":
                //Calculation
                heartRate = 170;
                a.setCalories(calcCal(heartRate, a.getDurationPerActivity()));
                break;
            case "Burpees":
                //Calculation
                heartRate = 170;
                a.setCalories(calcCal(heartRate, a.getDurationPerActivity()));
                break;
        }
        return a;
    }
}
