package guts.carpaltunnel.mobileapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import guts.carpaltunnel.mobileapp.util.FormManager;

public class BankActivity extends AppCompatActivity {

    Activity ctx = this;
    private Toolbar mTopToolbar;
    Button loanBtn;
    RadioGroup groupRadio;
    RadioButton radioButton1;
    RadioButton radioButton2;
    EditText accField;
    EditText sortField1;
    EditText sortField2;
    EditText sortField3;
    EditText borrowField;
    LinearLayout amount_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account);

        loanBtn = findViewById(R.id.loanBtn);
        accField = findViewById(R.id.accField);
        sortField1 = findViewById(R.id.sortField1);
        sortField2 = findViewById(R.id.sortField2);
        sortField3 = findViewById(R.id.sortField3);
        groupRadio = findViewById(R.id.groupRadio);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        borrowField = findViewById(R.id.borrowField);
        amount_entry = findViewById(R.id.amount_entry);

        loanBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PhotoIdActivity.class);
                FormManager formManager = ((HSApplication) getApplicationContext()).formManager;
                formManager.setField("bank_number", accField.getText());
                formManager.setField("borrow_amount", "£" + borrowField.getText());
                formManager.setField("sort_code", sortField1.getText().toString() + sortField2.getText().toString() + sortField3.getText().toString());
                startActivityForResult(myIntent, 0);
            }
        });

        int maxSortLength = 2;

        accField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        sortField1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxSortLength)});
        sortField2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxSortLength)});
        sortField3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxSortLength)});

        accField.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (accField.getText().toString().length() == 8)     //size as per your requirement
                {
                    sortField1.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        sortField1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (sortField1.getText().toString().length() == 2)     //size as per your requirement
                {
                    sortField2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void afterTextChanged(Editable s) {
            }

        });

        sortField2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (sortField2.getText().toString().length() == 2)     //size as per your requirement
                {
                    sortField3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void afterTextChanged(Editable s) {
            }

        });

        amount_entry.setVisibility(View.GONE);

        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton2) {
                    amount_entry.setVisibility(View.VISIBLE);
                    borrowField.setText("");
                } else {
                    amount_entry.setVisibility(View.GONE);
                    borrowField.setText("Maximum");
                }
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
