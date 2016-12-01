package at.fhj.appathlon.fitapp.fitappindoor.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.fhj.appathlon.fitapp.fitappindoor.R;
import at.fhj.appathlon.fitapp.fitappindoor.app.model.Activity;
import at.fhj.appathlon.fitapp.fitappindoor.app.model.ActivityDataAccess;

public class AddActivityActivity extends AppCompatActivity {
    private ActivityDataAccess activityDataAccess;
    private int sumCalPerDay,sumActPerDay, sumDistPerDay,sumAmountExPerDay;
    private Spinner spiSportType;
    private String sportType;
    private EditText edtCal,edtMin,edtAmo;
    private Button butSave;
    private String amountMin;
    private int amountCal,amountEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spiSportType=(Spinner)findViewById(R.id.activityType_spinner);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        edtAmo=(EditText) findViewById(R.id.activityAmountAmount);
        edtCal=(EditText) findViewById(R.id.activityAmountKcal);
        edtMin=(EditText) findViewById(R.id.activityAmountMinutes);
        butSave=(Button) findViewById(R.id.activitySave);

        activityDataAccess=new ActivityDataAccess(this);

        spiSportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item=parent.getItemAtPosition(position);
                sportType=item.toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sportType != null){
                    amountCal=Integer.parseInt(edtCal.getText().toString());
                    amountEx=Integer.parseInt(edtAmo.getText().toString());
                    amountMin=edtMin.getText().toString();
                    addActivity();
                }

                showEntries();

            }
        });

        }

    public void addActivity(){
        //TODO Activity Übergabeparamter einlesen
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());

        Activity a=new Activity(1,sportType,100,amountEx,date,amountCal,amountMin);
        activityDataAccess.addNewActivity(a);
    }

    private void showEntries(){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
