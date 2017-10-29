package guts.carpaltunnel.mobileapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.facebook.CallbackManager;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import guts.carpaltunnel.mobileapp.util.FormManager;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    private static final int TERM_ADDRESS = 1;
    private static final int PERM_ADDRESS = 0;

    Activity ctx = this;

    CallbackManager callbackManager;
    LocationManager mLocationManager;

    RadioGroup groupRadio;

    EditText termAddress;
    EditText termCity;
    EditText termCountry;
    EditText termPostcode;

    EditText permAddress;
    EditText permCity;
    EditText permCountry;
    EditText permPostcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_location);
        final LocationListener thisClass = this;
        final Context thisCtx = this;

        groupRadio = findViewById(R.id.groupRadio);

        termAddress = findViewById(R.id.t_address);
        termCity = findViewById(R.id.t_address_city);
        termCountry = findViewById(R.id.t_address_country);
        termPostcode = findViewById(R.id.t_address_postcode);

        permAddress = findViewById(R.id.p_address);
        permCity = findViewById(R.id.p_address_city);
        permCountry = findViewById(R.id.p_address_country);
        permPostcode = findViewById(R.id.p_address_postcode);

        setTermAddressVisibility(false);

        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setTermAddressVisibility(checkedId == R.id.noBtn);
            }
        });

        FloatingActionButton button = findViewById(R.id.location_button);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(View v) {
                CharSequence colors[] = new CharSequence[]{"Permanent", "Term"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Which address?");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        if ((Build.VERSION.SDK_INT < 23) || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {
                            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (mLocationManager != null) {
                                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, thisClass);
                            } else {
                                // TODO: gps is broke?
                                return;
                            }
                            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location == null) {
                                // TODO: maybe show the user a toast
                                return;
                            }

                            Geocoder geocoder = new Geocoder(thisCtx, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                        location.getLongitude(), 1);

                                fillAddress(which, addresses.get(0));
                            } catch (IOException e) {
                                // TODO: toast again
                            }
                        }
                    }
                });
                builder.show();
            }
        });

        Button submit_form = findViewById(R.id.submit_form);
        submit_form.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), FirstContactActivity.class);
                FormManager formManager = ((HSApplication) getApplicationContext()).formManager;
                formManager.setField("perm_address", permAddress.getText() + ", " + permCity.getText() + ", " + permCountry.getText() + ", " + permPostcode.getText());
                formManager.setField("term_address", termAddress.getText() + ", " + termCity.getText() + ", " + termCountry.getText() + ", " + termPostcode.getText());
                startActivityForResult(myIntent, 0);
            }
        });
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
            mLocationManager.removeUpdates(this);
        }
    }

    // Required functions
    public void onProviderDisabled(String arg0) {
    }

    public void onProviderEnabled(String arg0) {
    }

    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    }

    private void setTermAddressVisibility(boolean visible) {
        if (visible) {
            termAddress.setVisibility(View.VISIBLE);
            termCity.setVisibility(View.VISIBLE);
            termCountry.setVisibility(View.VISIBLE);
            termPostcode.setVisibility(View.VISIBLE);
        } else {
            termAddress.setVisibility(View.GONE);
            termCity.setVisibility(View.GONE);
            termCountry.setVisibility(View.GONE);
            termPostcode.setVisibility(View.GONE);
        }
    }

    private void fillAddress(int addressType, Address address) {
        switch (addressType) {
            case TERM_ADDRESS:
                termAddress.setText(address.getAddressLine(0).split(",")[0]);
                termCity.setText(address.getLocality());
                termCountry.setText(address.getCountryName());
                termPostcode.setText(address.getPostalCode());
                break;
            case PERM_ADDRESS:
                permAddress.setText(address.getAddressLine(0).split(",")[0]);
                permCity.setText(address.getLocality());
                permCountry.setText(address.getCountryName());
                permPostcode.setText(address.getPostalCode());
                break;
        }
    }
}

