package at.fhj.appathlon.fitapp.fitappindoor.app.model;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by vicky on 01.12.2016.
 */

public class Activity {
    private String sportType;
    private int distance, calories;
    private int amountPerExercise, amountActivities;
    private String duration, durationPerActivity;
    private String date;

    public Activity(String sportType, int distance, int amountPerExercise,String date, int calories, String duration_p_A ){
        this.sportType=sportType;
        this.distance=distance;
        this.amountPerExercise=amountPerExercise;
        this.date=date;
        this.calories=calories;
        this.durationPerActivity=duration_p_A;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getAmountPerExercise() {
        return amountPerExercise;
    }

    public void setAmountPerExercise(int amountPerExercise) {
        this.amountPerExercise = amountPerExercise;
    }

    public int getAmountActivities() {
        return amountActivities;
    }

    public void setAmountActivities(int amountActivities) {
        this.amountActivities = amountActivities;
    }

    public String getDurationPerActivity() {
        return durationPerActivity;
    }

    public void setDurationPerActivity(String durationPerActivity) {
        this.durationPerActivity = durationPerActivity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
