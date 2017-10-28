package guts.carpaltunnel.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BankActivity extends AppCompatActivity {

    Button loanBtn;
    RadioGroup groupRadio;
    RadioButton radioButton1;
    RadioButton radioButton2;
    EditText accField;
    EditText sortField1;
    EditText sortField2;
    EditText sortField3;
    EditText borrowField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account);

        loanBtn = findViewById(R.id.loanBtn);
        accField = findViewById(R.id.accField);
        sortField1 = findViewById(R.id.sortField1);
        sortField2 = findViewById(R.id.sortField2);
        sortField3 = findViewById(R.id.sortField3);

        loanBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PhotoIdActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        int maxSortLength = 2;

        accField.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
        sortField1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxSortLength)});
        sortField2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxSortLength)});
        sortField3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxSortLength)});

        sortField1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(sortField1.getText().toString().length()==2)     //size as per your requirement
                {
                    sortField2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        sortField2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(sortField2.getText().toString().length()==2)     //size as per your requirement
                {
                    sortField3.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        /* groupRadio Handler */

        groupRadio = findViewById(R.id.groupRadio);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton1);
        borrowField = findViewById(R.id.borrowField);
        borrowField.setVisibility(View.INVISIBLE);

        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.radioButton2)
                    borrowField.setVisibility(View.VISIBLE);
                else
                    borrowField.setVisibility(View.INVISIBLE);
            }
        });
    }

}
