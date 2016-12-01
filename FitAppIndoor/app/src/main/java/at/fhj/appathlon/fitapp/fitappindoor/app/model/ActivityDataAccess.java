package at.fhj.appathlon.fitapp.fitappindoor.app.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

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
    }
}
