package guts.carpaltunnel.mobileapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.tenmiles.helpstack.HSHelpStack;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setIcon(R.color.hs_transparent_color);*/
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            rootView.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    HSHelpStack.getInstance(getActivity()).showHelp(getActivity());
                }
            });
/*            rootView.findViewById(R.id.btnClearCache).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    HSHelpStack.getInstance(getActivity()).clear(getActivity());
                }
            });*/
            return rootView;
        }

    }

}
