package guts.carpaltunnel.mobileapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * Created by tom21 on 28/10/17.
 */

public class UniversityActivity extends AppCompatActivity {
    String[] universities = { "University of Glasgow" ,"University of Edinburgh", "University of Aberdeen",
                            "Abertay University", "Bangor University", "University of Bath",
                            "University of Birmingham","University of Bristol","University of Cambridge",
                            "University of Dundee", "Imperial College London", "University of Leeds",
                            "University of Leicester", "University of London", "University of Liverpool",
                            "University of Manchester", "University of Nottingham", "University of Oxford",
                            "The Robert Gordon University", "University of St Andrews", "University of Stirling",
                            "University of Strathclyde", "University of Sunderland", "University of the Arts London",
                            "University of Wales", "University of the West of Scotland", "University of Wolverhampton"};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);
        //Create Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, universities);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.universities);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);
    }
}

