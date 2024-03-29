package guts.carpaltunnel.mobileapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import guts.carpaltunnel.mobileapp.util.FormManager;

/**
 * Created by tom21 on 28/10/17.
 */

public class UniversityActivity extends AppCompatActivity {

    Activity ctx = this;
    private Toolbar mTopToolbar;

    String[] universities = {"University of Glasgow", "University of Edinburgh", "University of Aberdeen",
            "Abertay University", "Bangor University", "University of Bath",
            "University of Birmingham", "University of Bristol", "University of Cambridge",
            "University of Dundee", "Imperial College London", "University of Leeds",
            "University of Leicester", "University of London", "University of Liverpool",
            "University of Manchester", "University of Nottingham", "University of Oxford",
            "The Robert Gordon University", "University of St Andrews", "University of Stirling",
            "University of Strathclyde", "University of Sunderland", "University of the Arts London",
            "University of Wales", "University of the West of Scotland", "University of Wolverhampton"};

    String[] nationalities = {"Scottish", "English", "Welsh", "Northern Irish", "Irish national", "EU national", "National of another country"};

    String[] courses = {"Accounting and Finance", "Adult and Continuing Education", "American Studies",
            "Archaeology", "Arts and Media", "Informatics Astronomy", "Biology and Biomedical Sciences",
            "Celtic Civilisation", "Central and East European Studies",
            "Centre for Cultural Research Policy", "Chemistry", "Classics", "Comparative Literature",
            "Computing Science", "Creative Writing", "Czech", "Dentistry", "Earth Sciences",
            "Economic and Social History", "Economics", "Education", "Engineering",
            "English Language and Linguistics", "English Literature", "Film and Television Studies",
            "French", "Gaelic", "Geography", "German", "Greek", "Hispanic Studies", "History",
            "History of Art", "Italian", "Latin", "Law", "Management", "Mathematics", "Medicine",
            "Modern Languages", "Music", "Nankai University Collaboration", "Nursing and Healthcare",
            "Philosophy", "Physics", "Polish", "Politics", "Psychology", "Public Policy", "Russian",
            "Scottish Literature", "Slavonic Studies", "Social and Political Sciences", "Sociology",
            "Statistics", "Theatre Studies", "Theology and Religious Studies", "Urban Studies",
            "Veterinary Bioscience", "Veterinary Med and Surgery"};
    /**
     * Called when the activity is first created.
     */


    Button submit_uni;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);


        //// universities adapter
        //Create Array Adapter
        final ArrayAdapter<String> adapter_universities = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, universities);
        //Find TextView control
        final AutoCompleteTextView acTextViewUniversities = (AutoCompleteTextView) findViewById(R.id.universities);
        //Set the number of characters the user must type before the drop down list is shown
        acTextViewUniversities.setThreshold(1);
        //Set the adapter
        acTextViewUniversities.setAdapter(adapter_universities);

        //// courses adapter
        //Create Array Adapter
        final ArrayAdapter<String> adapter_courses = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, courses);
        //Find TextView control
        final AutoCompleteTextView acTextViewCourses = (AutoCompleteTextView) findViewById(R.id.courses);
        //Set the number of characters the user must type before the drop down list is shown
        acTextViewCourses.setThreshold(1);
        //Set the adapter
        acTextViewCourses.setAdapter(adapter_courses);

        //// nationalities adapter
        //Create Array Adapter
        final ArrayAdapter<String> adapter_nationalities = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, nationalities);
        //Find TextView control
        final AutoCompleteTextView acTextViewNationalities = (AutoCompleteTextView) findViewById(R.id.nationalities);
        //Set the number of characters the user must type before the drop down list is shown
        acTextViewNationalities.setThreshold(1);
        //Set the adapter
        acTextViewNationalities.setAdapter(adapter_nationalities);

        //get the spinner from the xml.
        final Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String [] yearOfStudy = {"1st", "2nd", "3rd", "4th", "5th"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, yearOfStudy);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        submit_uni = findViewById(R.id.submit_uni);

        submit_uni.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), BankActivity.class);
                FormManager formManager = ((HSApplication) getApplicationContext()).formManager;
                formManager.setField("university", acTextViewUniversities.getText().toString());
                formManager.setField("nationality", acTextViewNationalities.getText().toString());
                formManager.setField("course", acTextViewCourses.getText().toString());
                formManager.setField("degree_year",dropdown.getSelectedItem().toString());
                startActivityForResult(myIntent, 0);
            }
        });

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent myIntent = new Intent(ctx, ChatbotActivity.class);
            startActivityForResult(myIntent, 0);
//            Toast.makeText(WelcomeActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

