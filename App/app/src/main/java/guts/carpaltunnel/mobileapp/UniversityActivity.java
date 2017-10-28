package guts.carpaltunnel.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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

    String [] nationalities = {"Scottish","English","Welsh","Northern Irish", "Irish national", "EU national", "National of another country"};

    String [] courses = {"Accounting and Finance","Adult and Continuing Education","American Studies",
                        "Archaeology","Arts and Media","Informatics Astronomy","Biology and Biomedical Sciences",
                        "Celtic Civilisation","Central and East European Studies",
                        "Centre for Cultural Research Policy","Chemistry","Classics","Comparative Literature",
                        "Computing Science","Creative Writing","Czech","Dentistry","Earth Sciences",
                        "Economic and Social History","Economics","Education","Engineering",
                        "English Language and Linguistics","English Literature","Film and Television Studies",
                        "French","Gaelic","Geography","German","Greek","Hispanic Studies","History",
                        "History of Art","Italian","Latin","Law","Management","Mathematics","Medicine",
                        "Modern Languages","Music","Nankai University Collaboration","Nursing and Healthcare",
                        "Philosophy","Physics","Polish","Politics","Psychology","Public Policy","Russian",
                        "Scottish Literature","Slavonic Studies","Social and Political Sciences","Sociology",
                        "Statistics","Theatre Studies","Theology and Religious Studies","Urban Studies",
                        "Veterinary Bioscience","Veterinary Med and Surgery"};
    /** Called when the activity is first created. */

    Button submit_uni;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);



        //// universities adapter
        //Create Array Adapter
        ArrayAdapter<String> adapter_universities = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, universities);
        //Find TextView control
        AutoCompleteTextView acTextViewUniversities = (AutoCompleteTextView) findViewById(R.id.universities);
        //Set the number of characters the user must type before the drop down list is shown
        acTextViewUniversities.setThreshold(1);
        //Set the adapter
        acTextViewUniversities.setAdapter(adapter_universities);

        //// courses adapter
        //Create Array Adapter
        ArrayAdapter<String> adapter_courses = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, courses);
        //Find TextView control
        AutoCompleteTextView acTextViewCourses = (AutoCompleteTextView) findViewById(R.id.courses);
        //Set the number of characters the user must type before the drop down list is shown
        acTextViewCourses.setThreshold(1);
        //Set the adapter
        acTextViewCourses.setAdapter(adapter_courses);

        //// nationalities adapter
        //Create Array Adapter
        ArrayAdapter<String> adapter_nationalities = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, nationalities);
        //Find TextView control
        AutoCompleteTextView acTextViewNationalities = (AutoCompleteTextView) findViewById(R.id.nationalities);
        //Set the number of characters the user must type before the drop down list is shown
        acTextViewNationalities.setThreshold(1);
        //Set the adapter
        acTextViewNationalities.setAdapter(adapter_nationalities);

        submit_uni = findViewById(R.id.submit_uni);

        submit_uni.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SignatureActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}

