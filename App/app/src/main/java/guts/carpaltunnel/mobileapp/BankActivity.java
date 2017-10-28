package guts.carpaltunnel.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BankActivity extends AppCompatActivity {

    Button loanBtn;
    EditText accField;
    EditText sortField1;
    EditText sortField2;
    EditText sortField3;

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
                Intent myIntent = new Intent(view.getContext(), SignatureActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        int maxSortLength = 2;
        int maxAccLength = 8;

        accField.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxAccLength)});
        sortField1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxSortLength)});
        sortField2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxSortLength)});
        sortField3.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxSortLength)});

    }

}
