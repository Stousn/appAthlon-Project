package at.fhj.appathlon.fitapp.fitappindoor.app.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vicky on 01.12.2016.
 */

public class ActivityDBHelper extends SQLiteOpenHelper {
    public static final String TYPE="sport_type";
    public static final String TABLE_NAME="FA_Activities";
    public static final String DISTANCE="distance";
    public static final String AMOUNT_P_E="amount_per_exercise";
    public static final String DATE_EXERC="date_exercise";
    public static final String DURATION="duration";
    public static final String CALOR="calories";
    private static final String DATABASE_CREATE="Create Table "+TABLE_NAME +
            "( id integer primary key autoincrement," +
            TYPE+" text not null," +
            DISTANCE+" integer," +
            AMOUNT_P_E+" integer," +
            DATE_EXERC+" date," +
            DURATION+" int," +
            CALOR+" integer)";

    private static final String DELETE_VALUES="DELETE FROM "+TABLE_NAME;

    public ActivityDBHelper(Context context) {
        super(context, "ACTIVITY_DB", null, 1);
    }

    public void deleteValues(SQLiteDatabase db){
        db.execSQL(DELETE_VALUES);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
