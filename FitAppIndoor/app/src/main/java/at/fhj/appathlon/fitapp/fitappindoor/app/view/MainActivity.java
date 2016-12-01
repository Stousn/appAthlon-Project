package at.fhj.appathlon.fitapp.fitappindoor.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.fhj.appathlon.fitapp.fitappindoor.R;
import at.fhj.appathlon.fitapp.fitappindoor.app.model.Activity;
import at.fhj.appathlon.fitapp.fitappindoor.app.model.ActivityDataAccess;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityDataAccess activityDataAccess;
    private int sumCalPerDay,sumActPerDay, sumDistPerDay,sumAmountExPerDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        activityDataAccess=new ActivityDataAccess(this);
        testDB();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_diary) {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_statistics) {
            Intent i=new Intent(this,Statistics.class);
            startActivity(i);

        } else if (id == R.id.nav_weight) {
            Intent i=new Intent(this,WeightLog.class);
            startActivity(i);

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_feeback) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void testDB(){
        addActivity();
        getAllActivitiesOfDay();

    }

    public void addActivity(){
        //TODO Activity Übergabeparamter einlesen
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());
        Activity a=new Activity(1,"Test",100,5,date,200,"01:00");
        activityDataAccess.addNewActivity(a);
    }

    public List<String> getAllActivitiesOfDay(){
        //TODO Datum als Input
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());

        sumActPerDay=0;
        sumAmountExPerDay=0;
        sumCalPerDay=0;
        sumDistPerDay=0;
        List<String> act_list=new ArrayList<String>();
        for(Activity a : activityDataAccess.getAllActivitiesPerDay(date)){
            act_list.add(a.toString());
            sumCalPerDay=sumCalPerDay+a.getCalories();
            sumDistPerDay=sumDistPerDay+a.getDistance();
            sumAmountExPerDay=sumAmountExPerDay+a.getAmountPerExercise();
            sumActPerDay++;
            Log.i("DATA: ",a.toString()+"!!!!!!!!!!!!");
        }
        Log.i("KALORIEN: ",sumCalPerDay+"");
        Log.i("DISTANZ: ",sumDistPerDay+"");
        Log.i("EXERCISES: ",sumAmountExPerDay+"");
        Log.i("ACTIVITIES: ",sumActPerDay+"");
        return act_list;
    }
}
