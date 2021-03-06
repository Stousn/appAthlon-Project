package at.fhj.appathlon.fitapp.fitappindoor.app.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.fhj.appathlon.fitapp.fitappindoor.R;
import at.fhj.appathlon.fitapp.fitappindoor.app.model.Activity;
import at.fhj.appathlon.fitapp.fitappindoor.app.model.ActivityDataAccess;

/**
 * Created by Viktoria Lind on 01.12.2016.
 */

public class Statistics extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityDataAccess activityDataAccess;
    private LinearLayout llChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Set plus button to add a new activity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity();
            }
        });

        //Sets toolbar and navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Get some GUI elements of the diary screen
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        llChart=(LinearLayout) findViewById(R.id.chartDuration);
        activityDataAccess=new ActivityDataAccess(this);
        drawdiagramm();
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

    /**If some activity is selected in the menu -> Start activity /w an intent*/
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

    /**Calls the page to add a new activity*/
    public void getActivity() {
        Intent i = new Intent(this, AddActivityActivity.class);
        startActivity(i);
    }

    /**Fancy diagramm on the statistics page :)*/
    public void drawdiagramm(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());
        XYSeries series = new XYSeries("Activities today");
        XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();

        List<String> disciplins=new ArrayList<String>();
   /*     disciplins.add("Treadmill");
        disciplins.add("Cross Trainer");
        disciplins.add("Stair-Master");
        disciplins.add("Ergometer");
        disciplins.add("Spinning Bike");
        disciplins.add("Rowing maschine");
        disciplins.add("Push-Ups");
        disciplins.add("Sit-Ups");
        disciplins.add("Squats");
        disciplins.add("Pull-Ups");
        disciplins.add("Burpees");*/

        int hour=0;

        List<Integer> act_list=new ArrayList<Integer>();
        for(Activity a : activityDataAccess.getAllActivitiesPerDay(date)) {
            Log.i("STATICTIC_ACT",a.getDurationPerActivity()+"");
            series.add(hour++,a.getCalories());
            disciplins.add(a.getSportType());

        }
        series.setTitle("Calories per Activity");
        dataset.addSeries(series);
        XYSeriesRenderer renderer=new XYSeriesRenderer();

        renderer.setLineWidth(4);
        renderer.setColor(R.color.colorPrimaryDark);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(5);

        XYMultipleSeriesRenderer mRenderer=new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        mRenderer.setPanEnabled(false, false);
     //   mRenderer.setYAxisMax(200);
     //   mRenderer.setXAxisMax(4000);
        mRenderer.setShowGrid(true);
        for(int i=0;i< disciplins.size();i++){
            mRenderer.addXTextLabel(i+1,disciplins.get(i));
        }
        mRenderer.setXLabelsAlign(Paint.Align.CENTER);
        mRenderer.setXLabels(0);
        mRenderer.setXLabelsColor(R.color.colorPrimaryText);
        mRenderer.setYTitle("Calories");
        mRenderer.setXTitle("Discipline");
        mRenderer.setBarWidth(100);
        mRenderer.setLegendTextSize(24);
      // mRenderer.setXAxisMin(100);
      //  mRenderer.setXAxisMax(100);
       /* mRenderer.setYAxisMin(50);
        mRenderer.setYAxisMax(500);*/
       // mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.VERTICAL);
        mRenderer.setGridColor(R.color.colorPrimaryText);
        mRenderer.setLabelsColor(R.color.colorPrimaryText);


       // GraphicalView chartView = ChartFactory.getLineChartView(this,dataset , mRenderer);
        GraphicalView chartView=ChartFactory.getBarChartView(this,dataset,mRenderer, BarChart.Type.DEFAULT);
        llChart.addView(chartView,0);
    }

    /**A list provides a detailed view of all workouts of the day.*/
    private void setWorkoutOfDay() {
        ActivityDataAccess adao = new ActivityDataAccess(this.getApplicationContext());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<Activity> al = new ArrayList<Activity>();
        al = adao.getAllActivitiesPerDay(sdf.format(new Date()));

        /* Find Tablelayout defined in main.xml */
        TableLayout tl = (TableLayout) findViewById(R.id.detail_day);

        for (Activity activity : al) {
            // Log.i("ACTIVITY", activity.toString());
            //Fancy Layouting stuff
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


            // Adding the activity name
            TextView sportTypeTV = new TextView(this);
            sportTypeTV.setText(activity.getSportType());
            sportTypeTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            sportTypeTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            sportTypeTV.setTextColor(Color.DKGRAY);
            tr.addView(sportTypeTV);

            // Adding the duration of the activity
            TextView durationTV = new TextView(this);
            durationTV.setText("Duration: " + activity.getDurationPerActivity());
            durationTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(durationTV);

            // Adding KCAL for the workout
            TextView calTV = new TextView(this);
            calTV.setText(activity.getCalories() + " kcal");
            calTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            calTV.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tr.addView(calTV);

            //Some space
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
