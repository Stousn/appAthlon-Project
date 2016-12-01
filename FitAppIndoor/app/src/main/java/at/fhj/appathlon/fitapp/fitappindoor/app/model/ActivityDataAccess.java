package at.fhj.appathlon.fitapp.fitappindoor.app.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicky on 01.12.2016.
 */

public class ActivityDataAccess {
    ActivityDBHelper dbHelper;
    SQLiteDatabase database;
    Context context;

    public ActivityDataAccess(Context c){
        this.context=c;
        dbHelper=new ActivityDBHelper(this.context);
    }

    /**
     * @param activity Activity which should be added to the database
     * adds a Activity
     */
    public void addNewActivity(Activity activity){
        database=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(ActivityDBHelper.TYPE,activity.getSportType());
        values.put(ActivityDBHelper.DISTANCE,activity.getDistance());
        values.put(ActivityDBHelper.AMOUNT_P_E,activity.getAmountPerExercise());
        values.put(ActivityDBHelper.DATE_EXERC,activity.getDate());
        values.put(ActivityDBHelper.CALOR,activity.getCalories());
        values.put(ActivityDBHelper.DURATION,activity.getDuration());

        database.insert(ActivityDBHelper.TABLE_NAME,null,values);

        database.close();
    }

    public List<Activity> getAllActivities(){
        List<Activity> activityList=new ArrayList<Activity>();
        String sqlStr = "SELECT * FROM " +ActivityDBHelper.TABLE_NAME;
        database=dbHelper.getWritableDatabase();

        Cursor act_Cursor=database.rawQuery(sqlStr,null);
        Activity tmpActivity=null;
        if(act_Cursor.moveToFirst()){
            do{
                tmpActivity=new Activity();
                tmpActivity.setId(act_Cursor.getInt(0));
                tmpActivity.setSportType(act_Cursor.getString(1));
                tmpActivity.setDistance(act_Cursor.getInt(2));
                tmpActivity.setAmountPerExercise(act_Cursor.getInt(3));
                tmpActivity.setDate(act_Cursor.getString(4));
                tmpActivity.setDuration(act_Cursor.getString(5));
                tmpActivity.setCalories(act_Cursor.getInt(6));
                activityList.add(tmpActivity);
            }while(act_Cursor.moveToNext());
        }
        database.close();
        return activityList;
    }
}
