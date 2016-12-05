package at.fhj.appathlon.fitapp.fitappindoor.app.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import at.fhj.appathlon.fitapp.fitappindoor.R;


/**
 * Created by Patrik Holzer on 02.12.2016.
 */
public class Mail extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }



}
