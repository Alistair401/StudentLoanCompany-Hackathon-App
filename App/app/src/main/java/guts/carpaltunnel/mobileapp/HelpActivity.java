package guts.carpaltunnel.mobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Application;
import android.widget.Button;
import android.view.View;

import com.tenmiles.helpstack.HSHelpStack;
import com.tenmiles.helpstack.gears.HSEmailGear;

public class HelpActivity extends AppCompatActivity {
    HSHelpStack helpStack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        HSHelpStack helpStack = HSHelpStack.getInstance(this);

        HSEmailGear emailGear = new HSEmailGear(
                "alexjhmail@gmail.com",
                R.xml.articles);

        helpStack.setGear(emailGear); // Set the Gear

        Button helpButton= (Button) findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
         //       HSHelpStack.getInstance(getActivity()).showHelp(getActivity());
            }
        });
    }
}
