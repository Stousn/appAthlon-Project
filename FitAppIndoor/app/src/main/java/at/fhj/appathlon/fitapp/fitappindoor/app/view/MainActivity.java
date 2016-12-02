package at.fhj.appathlon.fitapp.fitappindoor.app.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
    private int sumCalPerDay,sumActPerDay, sumDistPerDay,sumAmountExPerDay, sumMinutes,sumHours;
    private TextView txtDate,txtCal,txtAmAct,txtDist,txtExec,txtDura;


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
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                getActivity();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtAmAct= (TextView) findViewById(R.id.txtAmAct);
        txtCal=(TextView) findViewById(R.id.txtCal);
        txtDate=(TextView) findViewById(R.id.txtDate);
        txtDist=(TextView) findViewById(R.id.txtDist);
        txtExec=(TextView) findViewById(R.id.txtExec);
        txtDura=(TextView) findViewById(R.id.txtDurat);

        activityDataAccess=new ActivityDataAccess(this);
        getAllActivitiesOfDay();
       // activityDataAccess.deleteData();

        setWorkoutOfDay();

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

            startActivity(
                    new Intent(Settings.ACTION_SETTINGS));
        }
            else if (id == R.id.nav_share) {
            String url = "https://www.facebook.com/fitappofficial";
            Intent intent;
            try {
                getPackageManager().getPackageInfo("com.facebook.katana", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url));
            } catch (Exception e) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            }

            startActivity(intent);

        } else if (id == R.id.nav_feeback) {

            Intent i=new Intent(this,Mail.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





  /*  public void addActivity(){
        Intent i = new Intent(this, AddActivityActivity.class);
        startActivity(i);
        //TODO Activity Übergabeparamter einlesen
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());
    //    Activity a=new Activity(1,"Test",100,5,date,200,"01:00");
        //activityDataAccess.NewActivity(a);
    }*/


    public List<String> getAllActivitiesOfDay(){
        //TODO Datum als Input
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());

        txtDate.setText(date);

        sumActPerDay=0;
        sumAmountExPerDay=0;
        sumCalPerDay=0;
        sumDistPerDay=0;
        List<String> act_list=new ArrayList<String>();
        for(Activity a : activityDataAccess.getAllActivitiesPerDay(date)) {
            act_list.add(a.toString());
            sumCalPerDay = sumCalPerDay + a.getCalories();
            sumDistPerDay = sumDistPerDay + a.getDistance();
            sumAmountExPerDay = sumAmountExPerDay + a.getAmountPerExercise();
            sumMinutes=sumMinutes+a.getDurationPerActivity();
            sumActPerDay++;
            Log.i("DATA: ", a.toString() + "!!!!!!!!!!!!");
        }
        Log.i("KALORIEN: ",sumCalPerDay+"");
        Log.i("DISTANZ: ",sumDistPerDay+"");
        Log.i("EXERCISES: ",sumAmountExPerDay+"");
        Log.i("ACTIVITIES: ",sumActPerDay+"");
        txtCal.setText(sumCalPerDay+"");
        txtAmAct.setText(sumActPerDay+"");
        txtDist.setText(sumDistPerDay+"");
        txtExec.setText(sumAmountExPerDay+"");
        sumHours=sumMinutes/60;
        sumMinutes=sumMinutes%60;
        txtDura.setText(sumHours+":"+sumMinutes);
        return act_list;
    }

    public void getActivity() {
        Intent i = new Intent(this, AddActivityActivity.class);
        startActivity(i);
    }

    private void setWorkoutOfDay() {
        ActivityDataAccess adao = new ActivityDataAccess(this.getApplicationContext());

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        List<Activity> al = new ArrayList<Activity>();

        al =  adao.getAllActivitiesPerDay(sdf.format(new Date()));

        /* Find Tablelayout defined in main.xml */
        TableLayout tl = (TableLayout) findViewById(R.id.detail_day);

        for(Activity activity : al) {
            Log.i("ACTIVITY", activity.toString());
            /* Create a new row to be added. */
            TableRow trLine = new TableRow(this);
            trLine.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            View line = new View(this);

            line.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 2));
            line.setBackgroundColor(Color.DKGRAY);
            tl.addView(line);

            TableRow trSpace = new TableRow(this);
            trSpace.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            View space = new View(this);

            space.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 15));
            tl.addView(space);


            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            // Activity Name
            TextView sportTypeTV = new TextView(this);
            sportTypeTV.setText(activity.getSportType());
            sportTypeTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            sportTypeTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            sportTypeTV.setTextColor(Color.DKGRAY);
            tr.addView(sportTypeTV);

            // Duration
            TextView durationTV = new TextView(this);
            durationTV.setText("Duration: "+ activity.getDuration());
            //if(activity.getDuration() == null){
            //    durationTV.setText("Durartion: " + "0");
            //}
            durationTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(durationTV);


            // KCAL
            TextView calTV = new TextView(this);
            calTV.setText(activity.getCalories()+" kcal");
            calTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            calTV.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tr.addView(calTV);

            //Space 2
            TableRow trSpace2 = new TableRow(this);
            trSpace2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            View space2 = new View(this);

            space2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 20));
            tl.addView(space2);


            /* Add row to TableLayout. */
            //tr.setBackgroundResource(R.drawable.sf_gradient_03);
            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        }


    }
}
