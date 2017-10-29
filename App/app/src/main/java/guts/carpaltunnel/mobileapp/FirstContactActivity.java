package guts.carpaltunnel.mobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import guts.carpaltunnel.mobileapp.util.FormManager;

public class FirstContactActivity extends AppCompatActivity {

    public static final int CONTACT_REQUEST = 1;

    Button submit_form;
    EditText contact_name, contact_mobile;
    FloatingActionButton import_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_contact);

        import_contact = findViewById(R.id.import_contact);
        submit_form = findViewById(R.id.submit_form);
        contact_name = findViewById(R.id.contact_name);
        contact_mobile = findViewById(R.id.contact_mobile);

        //get the spinner from the xml.
        Spinner dropdown = (Spinner) findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] relation = {"Mother", "Father", "Daughter", "Son", "Brother", "Sister", "Uncle", "Auntie", "Grandfather", "Grandmother", "Other"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, relation);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        import_contact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri = Uri.parse("content://contacts");
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, CONTACT_REQUEST);
            }
        });

        submit_form.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), UniversityActivity.class);
                FormManager formManager = ((HSApplication) getApplicationContext()).formManager;
                formManager.setField("contact_name", contact_name.getText());
                formManager.setField("contact_mobile", contact_mobile.getText());
                startActivityForResult(myIntent, 0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CONTACT_REQUEST:
                Uri uri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);
                contact_mobile.setText(number);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);
                contact_name.setText(name);
        }

    }
}
