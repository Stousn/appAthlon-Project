package at.fhj.appathlon.fitapp.fitappindoor.app.helper;

/**
 * Created by Stefan on 01.12.16.
 */

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.TimePicker;
import at.fhj.appathlon.fitapp.fitappindoor.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        //TextView tv = (TextView) getActivity().findViewById(R.id.tv);
        Log.i("TIME","------------");
        //Set a message for user
        //tv.setText("Your chosen time is...\n\n");
        //Display the user changed time on TextView
        //tv.setText(tv.getText()+ "Hour : " + String.valueOf(hourOfDay)
        //        + "\nMinute : " + String.valueOf(minute) + "\n");
        Log.i("TIME",String.valueOf(hourOfDay) +" : "+String.valueOf(minute));
        TextView tv = (TextView) getActivity().findViewById(R.id.activityTime);
        tv.setText(String.valueOf(hourOfDay) +":"+String.valueOf(minute));
    }
}